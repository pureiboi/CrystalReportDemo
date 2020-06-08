package com.demo.crystalreportdemo.domain.scheduler;

public class SchedulerForm {

	private String owner;
	private String initialRun;
	private String nextRun;
	private String finalRun;
	
	private String reportDuration;
	private String frequency;
	private String endCondition;

	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReportDuration() {
		return reportDuration;
	}

	public void setReportDuration(String reportDuration) {
		this.reportDuration = reportDuration;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getEndCondition() {
		return endCondition;
	}

	public void setEndCondition(String endCondition) {
		this.endCondition = endCondition;
	}

	public String getNextRun() {
		return nextRun;
	}

	public void setNextRun(String nextRun) {
		this.nextRun = nextRun;
	}


	public String getInitialRun() {
		return initialRun;
	}


	public void setInitialRun(String initialRun) {
		this.initialRun = initialRun;
	}


	public String getFinalRun() {
		return finalRun;
	}


	public void setFinalRun(String finalRun) {
		this.finalRun = finalRun;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}

}
