package com.boc.crystalreportdemo.service.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.boc.crystalreportdemo.constant.DateConstant;
import com.boc.crystalreportdemo.domain.scheduler.SchedulerForm;

@Service
public class ScheduleService {

	private static final Logger logger = LogManager.getLogger(ScheduleService.class);
	
	@Autowired
	private SchedulerFactoryBean schedulerFactory;

	public List<String> getAllTriggerGroupList() throws SchedulerException {
		
		Scheduler scheduler = schedulerFactory.getScheduler();
		List<String> triggerGroupList = scheduler.getTriggerGroupNames();
		
		logger.info("size: {}, trigger groups: {}", triggerGroupList.size(), triggerGroupList.toString());
		
		return scheduler.getTriggerGroupNames();
	}
	
	public List<TriggerKey> getAllTriggerKeyByGroupName(String groupName) throws SchedulerException {
		
		Scheduler scheduler = schedulerFactory.getScheduler();
		
		logger.info("searching for group name: {}",groupName);
		List<TriggerKey> triggerList = new ArrayList<>();
		
		for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(groupName))) {
			triggerList.add(triggerKey);
		}
		logger.info("total trigger count: {}",triggerList.size());

		return triggerList;
	}
	
	public Trigger getTriggerByTriggerKey(TriggerKey triggerKey) throws SchedulerException {
		
		Scheduler scheduler = schedulerFactory.getScheduler();
		
		logger.info("searching for trigger key name: {}, group name: {}, toString: {} ", triggerKey.getName(), triggerKey.getGroup(), triggerKey.toString());

		return scheduler.getTrigger(triggerKey);
	}
	
	public List<SchedulerForm> getTriggerDetail(String groupName) throws SchedulerException {
		
		List<TriggerKey> TriggerKeyList = getAllTriggerKeyByGroupName(groupName);
		
		List<SchedulerForm> formList = new ArrayList<>();
		
		for(TriggerKey triggerKey: TriggerKeyList) {
			Trigger trigger = getTriggerByTriggerKey(triggerKey);
			SchedulerForm form = prepareData(triggerKey, trigger);
			formList.add(form);
		}
		return formList;
	}
	
	public List<SchedulerForm> getAllTriggerDetail() throws SchedulerException {
		
		List<String> groupList = getAllTriggerGroupList();
		List<SchedulerForm> formListGlobal = new ArrayList<>();
		
		for(String str: groupList) {
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
		
		if(nextFireDate != null) {
			form.setNextRun(sf.format(trigger.getNextFireTime()));
		}else
		{
			form.setNextRun("has no next");
		}

		Date finalRun = trigger.getFinalFireTime();
		if(finalRun != null) {
			form.setFinalRun(sf.format(finalRun));
		}
		else {
			form.setFinalRun("repeat forver");
		}
		
		return form;
	}
}
