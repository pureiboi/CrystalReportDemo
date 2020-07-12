package com.demo.crystalreportdemo.service.business.scheduler;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.demo.crystalreportdemo.constant.DateConstant;
import com.demo.crystalreportdemo.constant.MessageBundle;
import com.demo.crystalreportdemo.domain.scheduler.SchedulerForm;

@Service
public class ScheduleService {

	private static final Logger logger = LogManager.getLogger(ScheduleService.class);


	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private MessageSource messageSource;

	public List<String> getAllTriggerGroupList() throws SchedulerException {

		//Scheduler scheduler = schedulerFactory.getScheduler();
		
		List<String> triggerGroupList = scheduler.getTriggerGroupNames();

		logger.info("size: {}, trigger groups: {}", triggerGroupList.size(), triggerGroupList.toString());

		return scheduler.getTriggerGroupNames();
	}

	public List<TriggerKey> getAllTriggerKeyByGroupName(String groupName) throws SchedulerException {

		//Scheduler scheduler = schedulerFactory.getScheduler();

		logger.info("searching for group name: {}", groupName);
		List<TriggerKey> triggerList = new ArrayList<>();

		for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(groupName))) {
			triggerList.add(triggerKey);
		}
		logger.info("total trigger count: {}", triggerList.size());

		return triggerList;
	}

	public Trigger getTriggerByTriggerKey(TriggerKey triggerKey) throws SchedulerException {

		//Scheduler scheduler = schedulerFactory.getScheduler();

		logger.info("searching for trigger key name: {}, group name: {}, toString: {} ", triggerKey.getName(),
				triggerKey.getGroup(), triggerKey.toString());

		return scheduler.getTrigger(triggerKey);
	}

	public List<SchedulerForm> getTriggerDetail(String groupName) throws SchedulerException {

		List<TriggerKey> triggerKeyList = getAllTriggerKeyByGroupName(groupName);

		List<SchedulerForm> formList = new ArrayList<>();

		for (TriggerKey triggerKey : triggerKeyList) {
			Trigger trigger = getTriggerByTriggerKey(triggerKey);
			SchedulerForm form = prepareData(triggerKey, trigger);
			formList.add(form);
		}
		return formList;
	}

	public List<SchedulerForm> getAllTriggerDetail() throws SchedulerException {

		List<String> groupList = getAllTriggerGroupList();
		List<SchedulerForm> formListGlobal = new ArrayList<>();

		for (String str : groupList) {
			List<SchedulerForm> formList = getTriggerDetail(str);
			formListGlobal.addAll(formList);
		}

		return formListGlobal;
	}

	private SchedulerForm prepareData(TriggerKey triggerKey, Trigger trigger) {

		SchedulerForm form = new SchedulerForm();

		SimpleDateFormat sf = new SimpleDateFormat(DateConstant.DATE_FORMAT_DD_MM_YYYY_HH_MM_SS);

		Date triggerStartDate = new Date(trigger.getJobDataMap().getLongValue("settingDate"));
		form.setOwner(triggerKey.getGroup());
		form.setInitialRun(sf.format(triggerStartDate));

		Date nextFireDate = trigger.getNextFireTime();

		if (nextFireDate != null) {
			form.setNextRun(sf.format(trigger.getNextFireTime()));
		} else {
			form.setNextRun(messageSource.getMessage(MessageBundle.LABEL_TASK_NEXT_STATUS, null,
					LocaleContextHolder.getLocale()));
		}

		Date finalRun = trigger.getFinalFireTime();
		if (finalRun != null) {
			form.setFinalRun(sf.format(finalRun));
		} else {

			form.setFinalRun(messageSource.getMessage(MessageBundle.LABEL_TASK_END_STATUS, null,
					LocaleContextHolder.getLocale()));
		}

		return form;
	}

	public void scheduleJob(SchedulerForm sForm) throws SchedulerException {

		Date jobStartDate = new Date();
		String triggerId = sForm.getUserName() + System.currentTimeMillis();
		SimpleScheduleBuilder ssb = simpleSchedule();
		String frequency = sForm.getFrequency();
		switch (frequency) {
		case "minute":
			ssb.withIntervalInMinutes(1);
			//jobStartDate = DateBuilder.futureDate(1, IntervalUnit.MINUTE);
			break;
		case "daily":
			ssb.withIntervalInHours(24);
			jobStartDate = DateBuilder.futureDate(1, IntervalUnit.DAY);
			break;
		case "monthly":
			ssb.withIntervalInHours(720);
			jobStartDate = DateBuilder.futureDate(1, IntervalUnit.MONTH);
			break;
		}

		if (!sForm.getEndCondition().equals("0")) {
			if (!sForm.getEndCondition().equalsIgnoreCase("")) {
				try {
					Integer.parseInt(sForm.getEndCondition());
				} catch (NumberFormatException ex) {
					sForm.setEndCondition("1");
				}
				ssb.withRepeatCount(Integer.parseInt(sForm.getEndCondition()) - 1);
			} else {
				ssb.withRepeatCount(0);
			}
		} else {
			ssb.repeatForever();
		}

		String duration = sForm.getReportDuration();

		if (duration.equalsIgnoreCase("")) {
			duration = "30";
		}

		Trigger newTrigger = newTrigger().forJob("Qrtz_Job_Detail").usingJobData("duration", duration)
				.usingJobData("userName", sForm.getUserName()).usingJobData("settingDate", new Date().getTime())
				.withIdentity(TriggerKey.triggerKey(triggerId, sForm.getUserName())).withDescription("Report Scheduler")
				.withSchedule(ssb).startAt(jobStartDate).build();
		
		logger.info("is scheduler started? {}", scheduler.isStarted());

		scheduler.scheduleJob(newTrigger);
		
		//scheduler.scheduleJob(simpleJobDetail, newTrigger);


	}
}
