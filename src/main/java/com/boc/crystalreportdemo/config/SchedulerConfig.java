package com.boc.crystalreportdemo.config;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.boc.crystalreportdemo.scheduler.SimpleJob;

@Configuration
@EnableAutoConfiguration
public class SchedulerConfig {

	private static final Logger logger = LogManager.getLogger(SchedulerConfig.class);

	@Autowired
	private ApplicationContext applicationContext;


	@Bean
	public SpringBeanJobFactory springBeanJobFactory() {
		AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
		logger.info("Configuring Job factory");

		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory) throws SchedulerException {
		logger.info("Getting a handle to the Scheduler");
		Scheduler scheduler = factory.getScheduler();
		scheduler.scheduleJob(job, trigger);

		logger.info("Starting Scheduler threads");
		scheduler.start();
		return scheduler;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setJobFactory(springBeanJobFactory());
		factory.setQuartzProperties(quartzProperties());
		return factory;
	}

	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean
	public JobDetail jobDetail() {

		return newJob().ofType(SimpleJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_Job_Detail"))
				.withDescription("Invoke Sample Job service...").build();
	}

	@Bean
	public Trigger trigger(JobDetail job) {

		return newTrigger().build();
//		int frequencyInSec = 10;
//		logger.info("Configuring trigger to fire every {} seconds", frequencyInSec);
//
//		return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_Trigger"))
//				.withDescription("Simple trigger")
//				.withSchedule(simpleSchedule().withIntervalInSeconds(frequencyInSec)).build();
	}

}