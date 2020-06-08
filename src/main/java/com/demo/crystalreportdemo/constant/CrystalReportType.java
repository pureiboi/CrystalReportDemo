package com.demo.crystalreportdemo.constant;

import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;

public enum CrystalReportType {
	PDF(ReportExportFormat.PDF, "application/pdf");
	private ReportExportFormat reportVal;
	private String contentType;

	CrystalReportType(ReportExportFormat val, String contentType) {
		this.reportVal = val;
		this.contentType = contentType;
	}

	public String getNameLower() {
		return this.name().toLowerCase();
	}

	public String getName() {
		return this.name();
	}

	public ReportExportFormat getValue() {
		return this.reportVal;
	}

	public String getContentType() {
		return this.contentType;
	}
}
