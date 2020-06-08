package com.demo.crystalreportdemo.service.report;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ReportService {

	public ByteArrayInputStream generateReport();
	
	public void setReportType(Object type);
	
	public Object getReportType();

	
	public void setReportPath(String reportPath);
	
	public void setParamList(List<Object> paramList);
}
