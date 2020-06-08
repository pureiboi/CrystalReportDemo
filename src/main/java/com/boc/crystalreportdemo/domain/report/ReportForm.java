package com.boc.crystalreportdemo.domain.report;

public class ReportForm {

	private String dateFrom;

	private String dateTo;

	private String userName;

	public ReportForm() {
		this("", "", "");
	}

	public ReportForm(String userName, String dateFrom, String dateTo) {
		this.userName = userName;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

}
