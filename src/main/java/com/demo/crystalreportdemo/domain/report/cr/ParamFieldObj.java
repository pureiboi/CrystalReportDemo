package com.demo.crystalreportdemo.domain.report.cr;

public class ParamFieldObj {

	private String subReportName;
	
	private String paramName;
	
	private Object paramValue;
	
	public ParamFieldObj(String paramName, Object paramValue) {
		this("", paramName, paramValue);
	}
	
	public ParamFieldObj(String subReportName, String paramName, Object paramValue) {
		this.subReportName = subReportName;
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
	
	public String getSubReportName() {
		return subReportName;
	}

	public void setSubReportName(String subReportName) {
		this.subReportName = subReportName;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Object getParamValue() {
		return paramValue;
	}

	public void setParamValue(Object paramValue) {
		this.paramValue = paramValue;
	}

}
