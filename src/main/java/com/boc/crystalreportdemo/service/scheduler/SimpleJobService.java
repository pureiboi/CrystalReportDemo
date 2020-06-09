package com.boc.crystalreportdemo.service.scheduler;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.crystalreportdemo.constant.CrystalReportType;
import com.boc.crystalreportdemo.constant.DateConstant;
import com.boc.crystalreportdemo.domain.Document;
import com.boc.crystalreportdemo.domain.User;
import com.boc.crystalreportdemo.domain.report.cr.ParamFieldObj;
import com.boc.crystalreportdemo.service.DocumentService;
import com.boc.crystalreportdemo.service.UserService;
import com.boc.crystalreportdemo.service.report.ReportService;
import com.boc.crystalreportdemo.service.report.cr.CrystalReportServiceImpl;
import com.ibm.icu.util.Calendar;

@Service
public class SimpleJobService {
	public static final long EXECUTION_TIME = 5000L;

	private static final Logger logger = LogManager.getLogger(SimpleJobService.class);

	@Autowired
	private ReportService reportService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private UserService userService;

	private AtomicInteger count = new AtomicInteger();

	public void executeSimpleJob(Object pUserName, Object pDuration) {

		logger.info("The simple job is starting...");
		try {

			reportService.setReportPath(CrystalReportServiceImpl.REPORT_ACCOUNT_SUMMARY);

			String userName = (String) pUserName;

			Integer durDay = Integer.parseInt((String) pDuration);
			Date dateParamTo = new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -durDay);
			Date dateParamFrom = cal.getTime();

			ParamFieldObj field1 = new ParamFieldObj("userName", userName);
			ParamFieldObj field2 = new ParamFieldObj("dateFrom", dateParamFrom);
			ParamFieldObj field3 = new ParamFieldObj("dateTo", dateParamTo);
			logger.info("parsed dateFrom: " + dateParamFrom);
			logger.info("parsed dateTo: " + dateParamFrom);

			List<Object> paramList = new ArrayList<>();
			paramList.add(field1);
			paramList.add(field2);
			paramList.add(field3);

			reportService.setParamList(paramList);

			User user = userService.getUserByUsername(userName);
			
			Document document = new Document();
			
			ByteArrayInputStream byteArrayInputStream = reportService.generateReport();
			byte[] data = IOUtils.toByteArray(byteArrayInputStream);
			
			CrystalReportType reportType = (CrystalReportType) reportService.getReportType();
			
			document.setUser(user);
			document.setContentType(reportType.getContentType());
			document.setData(data);
			document.setCreateDt(new Timestamp(dateParamTo.getTime()));
			
			SimpleDateFormat sf = new SimpleDateFormat(DateConstant.DATE_FORMAT_YYYYMMDD);
			String rel = sf.format(new Date());
			// default: report-yyyymmdd-#############.pdf
			String reportOutputName = "report-" + rel + "-" + System.currentTimeMillis() + "." + reportType.getNameLower();
			logger.info("report output name: " + reportOutputName);
			
			document.setDocName(reportOutputName);
			
			documentService.saveDocument(document);
			

			Thread.sleep(EXECUTION_TIME);
		} catch (InterruptedException ex) {
			logger.error("Error with Simple job", ex);
		} catch (NumberFormatException ex) {
			
		}
		catch (Exception ex) {
			logger.error("Unknown Error with Simple job", ex);
		} finally {
			count.incrementAndGet();
			logger.info("Simple job has finished...");
		}
	}

	public int getNumberOfInvocations() {
		return count.get();
	}
}
