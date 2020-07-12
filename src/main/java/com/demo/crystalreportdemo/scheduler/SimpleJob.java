package com.demo.crystalreportdemo.scheduler;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.crystalreportdemo.service.business.scheduler.SimpleJobService;

@Component
public class SimpleJob implements Job{

    private static final Logger logger = LogManager.getLogger(SimpleJob.class);

    @Autowired
    private SimpleJobService jobService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Trigger ID ** {} ** Job ** {} ** fired @ {}", context.getTrigger().getKey(), context.getJobDetail().getKey().getName(), context.getFireTime());

        JobDataMap dataMap = context.getTrigger().getJobDataMap();
        
        dataMap.forEach((k, v) -> {logger.info("key: {}, val: {} ", k, v);});
        
        jobService.executeSimpleJob(dataMap.get("userName") ,dataMap.get("duration"));
        

        logger.info("Next job scheduled @ {}", context.getNextFireTime());		
	}
}
