package com.demo.crystalreportdemo.controller;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.demo.crystalreportdemo.constant.CrystalReportType;
import com.demo.crystalreportdemo.constant.DateConstant;
import com.demo.crystalreportdemo.constant.MessageBundle;
import com.demo.crystalreportdemo.domain.Document;
import com.demo.crystalreportdemo.domain.User;
import com.demo.crystalreportdemo.domain.report.ReportForm;
import com.demo.crystalreportdemo.domain.report.cr.ParamFieldObj;
import com.demo.crystalreportdemo.domain.scheduler.SchedulerForm;
import com.demo.crystalreportdemo.service.DocumentService;
import com.demo.crystalreportdemo.service.business.AuthenticationService;
import com.demo.crystalreportdemo.service.report.ReportService;
import com.demo.crystalreportdemo.service.scheduler.ScheduleService;

@Controller
@SessionAttributes("userInfo")
public class ReportController extends BaseController {

	private static final Logger logger = LogManager.getLogger(ReportController.class);

	@Autowired
	private ReportService reportService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private AuthenticationService authService;

	@RequestMapping(value = "/report/accountsummary", method = RequestMethod.GET)
	public String prepareSummary(Model model) {
		logger.info("navigated to account summary for report");

		SimpleDateFormat sf = new SimpleDateFormat(DateConstant.DATE_FORMAT_DD_MM_YYYY);
		ReportForm rf = new ReportForm();
		User user = (User) model.getAttribute("userInfo");

		Date today = new Date();
		rf.setDateFrom(sf.format(today));
		rf.setDateTo(sf.format(today));
		rf.setUserName(user.getUserName());
		model.addAttribute("reportForm", rf);

		prepareDocumentData(model);

		return "accountSummary";
	}

	@RequestMapping(value = "/report/download", method = RequestMethod.GET)
	public ResponseEntity<Resource> prepareSummary(@RequestParam String docId) {
		logger.info("perform download file id: " + docId);

		Document document = documentService.getDocumentById(docId);

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(document.getData());
		Resource resource = new InputStreamResource(byteArrayInputStream);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(document.getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getDocName())
				.body(resource);

	}

	@RequestMapping(value = "/report/generate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Resource> doGenerate(@ModelAttribute ReportForm reportForm, Model model) {
		logger.info("ReportController - doGenerate() - GET");
		CrystalReportType reportType = CrystalReportType.PDF;

		SimpleDateFormat sf = new SimpleDateFormat(DateConstant.DATE_FORMAT_YYYYMMDD);
		String rel = sf.format(new Date());
		// default: report-yyyymmdd-#############.pdf
		String reportOutputName = "report-" + rel + "-" + System.currentTimeMillis() + "." + reportType.getNameLower();
		logger.info("report output name: {} ", reportOutputName);
		User user = (User) model.getAttribute("userInfo");
		logger.info("user data {}", user);

		SimpleDateFormat sfReport = new SimpleDateFormat(DateConstant.DATE_FORMAT_DD_MM_YYYY);

		if (!user.isAdmin() || StringUtils.isEmpty(reportForm.getUserName())) {
			reportForm.setUserName(user.getUserName());
		}

		try {

			logger.info("date from entered: {}", reportForm.getDateFrom());
			logger.info("date to entered: {}", reportForm.getDateTo());

			Date dateParamFrom = sfReport.parse(reportForm.getDateFrom());
			Date dateParamTo = sfReport.parse(reportForm.getDateTo());
			ParamFieldObj field1 = new ParamFieldObj("userName", reportForm.getUserName());
			ParamFieldObj field2 = new ParamFieldObj("dateFrom", dateParamFrom);
			ParamFieldObj field3 = new ParamFieldObj("dateTo", dateParamTo);
			logger.info("parsed dateFrom: {}", dateParamFrom);
			logger.info("parsed dateTo: {}", dateParamFrom);

			List<Object> paramList = new ArrayList<>();
			paramList.add(field1);
			paramList.add(field2);
			paramList.add(field3);
			reportService.setParamList(paramList);
			ByteArrayInputStream byteArrayInputStream = reportService.generateReport();
			if (byteArrayInputStream != null) {
				Resource resource = new InputStreamResource(byteArrayInputStream);
				return ResponseEntity.ok().contentType(MediaType.parseMediaType(reportType.getContentType()))
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reportOutputName)
						.body(resource);
			}
		} catch (ParseException ex) {
			logger.error("date parsing exception ", ex);
		}

		return null;
	}

	@RequestMapping(value = "/report/scheduler", method = RequestMethod.GET)
	public String prepareScheduler(Model model) {
		logger.info("ReportController - prepareScheduler() - GET");

		prepareSchedulerData(model);

		SchedulerForm form = new SchedulerForm();
		form.setEndCondition("2");
		form.setReportDuration("30");
		form.setFrequency("monthly");
		model.addAttribute("reportForm", form);

		return "scheduleSummary";
	}

	@RequestMapping(value = "/report/scheduler", method = RequestMethod.POST)
	public String doScheduler(@ModelAttribute SchedulerForm form, Model model) {

		model.addAttribute("reportForm", form);

		User user = (User) model.getAttribute("userInfo");

		try {

			if (!user.isAdmin()) {
				form.setUserName(user.getUserName());
			}

			if (!authService.isUserExists(form.getUserName())) {
				model.addAttribute("errMsg", getBuddleMessage(MessageBundle.ERR_INVALID_USER_VERIFY));
				prepareSchedulerData(model);

				return "scheduleSummary";
			}

			User userQuery = authService.getUserAccount(form.getUserName());

			form.setUserName(userQuery.getUserName());

			try {

				scheduleService.scheduleJob(form);

			} catch (SchedulerException ex) {
				logger.error("SchedulerException on creating schedule", ex);
			}

			prepareSchedulerData(model);
			model.addAttribute("respMsg", getBuddleMessage(MessageBundle.SUCC_TASK_CREATED));
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex); 
		}

		return "scheduleSummary";
	}

	private void prepareSchedulerData(Model model) {
		List<SchedulerForm> formList = new ArrayList<SchedulerForm>();
		try {
			User user = (User) model.getAttribute("userInfo");

			if (user.isAdmin()) {
				formList = scheduleService.getAllTriggerDetail();
			} else {

				formList = scheduleService.getTriggerDetail(user.getUserName());

			}

		} catch (SchedulerException ex) {
			logger.error("SchedulerException while getting trigger data", ex);
		}

		model.addAttribute("data", formList);
	}

	private void prepareDocumentData(Model model) {
		User user = (User) model.getAttribute("userInfo");
		List<Document> docList = new ArrayList<>();
		if (user.isAdmin()) {
			docList = (List<Document>) documentService.getAllDocument();
		} else {
			docList = documentService.getDocumentByUserId(user.getUserId());
		}

		model.addAttribute("data", docList);
	}

}
