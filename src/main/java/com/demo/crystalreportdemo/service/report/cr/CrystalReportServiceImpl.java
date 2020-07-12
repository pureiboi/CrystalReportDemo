package com.demo.crystalreportdemo.service.report.cr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.demo.crystalreportdemo.constant.CrystalReportType;
import com.demo.crystalreportdemo.domain.report.cr.ParamFieldObj;
import com.demo.crystalreportdemo.service.report.ReportService;
import com.crystaldecisions.reports.sdk.DatabaseController;
import com.crystaldecisions.reports.sdk.ISubreportClientDocument;
import com.crystaldecisions.reports.sdk.ParameterFieldController;
import com.crystaldecisions.reports.sdk.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfoKind;
import com.crystaldecisions.sdk.occa.report.data.IConnectionInfo;
import com.crystaldecisions.sdk.occa.report.data.ITable;
import com.crystaldecisions.sdk.occa.report.data.Tables;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.IStrings;
import com.crystaldecisions.sdk.occa.report.lib.PropertyBag;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;

@Service
public class CrystalReportServiceImpl implements ReportService {

	private static final Logger logger = LogManager.getLogger(CrystalReportServiceImpl.class);

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private Environment env;

	public static final String REPORT_ACCOUNT_SUMMARY = "rpt/account_report.rpt";

	private String reportPath;

	private CrystalReportType crystalReportType;

	private List<Object> paramList;

	public CrystalReportServiceImpl() {

		setReportType(CrystalReportType.PDF);
		setReportPath(REPORT_ACCOUNT_SUMMARY);
	}

	@Override
	public ByteArrayInputStream generateReport() {
		logger.info("generateReport starting");

		ByteArrayInputStream byteArrayInputStream = null;

		try {
			Path reportFile = processFilePath(reportPath);

			logger.info("report location: {}", reportFile);
			logger.info("report type: {}", crystalReportType);
			logger.info("report content type: {}", crystalReportType.getContentType());
			logger.info("report exists: {}", Files.exists(reportFile));

			// Open report.
			ReportClientDocument reportClientDoc = new ReportClientDocument();
			reportClientDoc.open(reportFile.toString(), 0);

			// reportClientDoc.getDatabaseController().logon("APP_USER", "APP_USER_1234");

			// The DatabaseController will be used to modify the connection properties of
			// the database before
			// viewing the report. This method iterates through each table in the report and
			// changes the
			// connection info properties.

			// Switch all tables on the main report. See utility method below.
			switch_tables(reportClientDoc.getDatabaseController());

			// Perform the same operation against all tables in the subreport as well.
			IStrings subreportNames = reportClientDoc.getSubreportController().getSubreportNames();

			// Set the datasource for all the subreports.
			for (int i = 0; i < subreportNames.size(); i++) {
				ISubreportClientDocument subreportClientDoc = reportClientDoc.getSubreportController()
						.getSubreport(subreportNames.getString(i));

				// Switch tables for each subreport in the report using the same connection
				// information. See utility
				// method below.
				switch_tables(subreportClientDoc.getDatabaseController());
			}

			ParameterFieldController paramFieldController = reportClientDoc.getDataDefController()
					.getParameterFieldController();

			for (Object obj : paramList) {
				ParamFieldObj param = (ParamFieldObj) obj;
				logger.info("subreport: {}", param.getSubReportName());
				logger.info("param name: {}", param.getParamName());
				logger.info("param value: {}", param.getParamValue());
				paramFieldController.setCurrentValue(param.getSubReportName(), param.getParamName(),
						param.getParamValue());
			}

			// NOTE: If parameters or database login credentials are required, they need to
			// be set before.
			// calling the export() method of the PrintOutputController.

			// Export report and obtain an input stream that can be written to disk.
			// See the Java Reporting Component Developer's Guide for more information on
			// the supported export format enumerations
			// possible with the JRC.
			byteArrayInputStream = (ByteArrayInputStream) reportClientDoc.getPrintOutputController()
					.export(ReportExportFormat.PDF);

			// Release report.
			reportClientDoc.close();

			// These utility methods below demonstrate how to use the Java I/O libraries to
			// write the input stream content
			// directly to the browser, or the server's file system. Note: We are now
			// working with APIs completely outside of
			// Crystal at this point:
		} catch (ReportSDKException ex) {
			logger.error("ReportSDKException error", ex);
		} catch (Exception ex) {
			logger.error("Unknown exception error", ex);
		}
		return byteArrayInputStream;
	}

	private void switch_tables(DatabaseController databaseController) throws ReportSDKException {

		// Declare the new connection properties that report's datasource will be
		// switched to.
		// NOTE: These are specific to using JDBC against a particular MS SQL Server
		// database. Be sure to use the
		// DisplayConnectionInfo sample to determine what your own connection properties
		// need to be set to.
//		final String TABLE_NAME_QUALIFIER = env.getProperty("spring.datasource.username") + ".";
//		final String DBUSERNAME = env.getProperty("spring.datasource.username");
//		final String DBPASSWORD = env.getProperty("spring.datasource.password");
//		final String SERVERNAME = "locahost";
//		final String DATABASE_NAME = "xe";
//		final String DATABASE_DLL = "crdb_jdbc.dll";
//		final String URI = "!oracle.jdbc.driver.OracleDriver!jdbc:oracle:thin:{userid}/{password}@localhost:1521:xe!ServerType=5!QuoteChar=\\\"";
//		final String CONNECTION_STRING = "Use JDBC=b(true);Connection URL=s(jdbc:oracle:thin:@localhost:1521:xe);Database Class Name=s(oracle.jdbc.driver.OracleDriver);JNDI Datasource Name=s();Server=s(localhost);User ID=s({userid});Password=;Trusted_Connection=b(false);JDBC Connection String=s(!oracle.jdbc.driver.OracleDriver!jdbc:oracle:thin:{userid}/{password}@localhost:1521:xe!ServerType=5!QuoteChar=\\\");Generic JDBC Driver Behavior=s(No)";

		// for oracle
//		final String TABLE_NAME_QUALIFIER = env.getProperty("spring.datasource.username")  + ".";
//		final String DBUSERNAME = env.getProperty("spring.datasource.username");
//		final String DBPASSWORD = env.getProperty("spring.datasource.password");
//		final String SERVERNAME = "localhost"; 
//		final String CONNECTION_STRING = "Use JDBC=b(true);Connection URL=s(jdbc:oracle:thin:@localhost:1521:xe);Database Class Name=s(oracle.jdbc.driver.OracleDriver);Server=s(localhost);User ID=s(userapp);Password=;Trusted_Connection=b(false);JDBC Connection String=s(!oracle.jdbc.driver.OracleDriver!jdbc:oracle:thin:{userid}/{password}@localhost:1521:xe)";
//		final String DATABASE_NAME = "xe";
//		final String DATABASE_DLL = "crdb_jdbc.dll";
//		final String URI = "!oracle.jdbc.driver.OracleDriver!jdbc:oracle:thin:{userid}/{password}@localhost:1521:xe";

		// for H2
//		final String TABLE_NAME_QUALIFIER = env.getProperty("spring.datasource.username")  + ".";
//		final String DBUSERNAME = env.getProperty("spring.datasource.username");
//		final String DBPASSWORD = env.getProperty("spring.datasource.password");
//		final String SERVERNAME = "localhost"; 
//		final String CONNECTION_STRING = "Use JDBC=b(true);Connection URL=s(jdbc:h2:tcp://localhost:9092/mem:testdb);Database Class Name=s(org.h2.Driver);Server=s(localhost);User ID=s(sa);Password=;Trusted_Connection=b(false);JDBC Connection String=s(!org.h2.Driver!jdbc:h2:tcp://localhost:9092/mem:testdb;user=sa;)";
//		final String DATABASE_NAME = "xe";
//		final String DATABASE_DLL = "crdb_jdbc.dll";
//		final String URI = "!org.h2.Driver!jdbc:h2:tcp://localhost:9092/mem:testdb;user=sa;";

		final String TABLE_NAME_QUALIFIER = env.getProperty("spring.datasource.username") + ".";
		final String DBUSERNAME = env.getProperty("spring.datasource.username");
		final String DBPASSWORD = env.getProperty("spring.datasource.password");
		final String SERVERNAME = "localhost";
		String CONNECTION_STRING = "";
		String DATABASE_NAME = "";
		String DATABASE_DLL = "";
		String URI = "";

		boolean isH2env = Arrays.stream(env.getActiveProfiles()).anyMatch("h2"::equalsIgnoreCase);

		if (isH2env) {
			CONNECTION_STRING = "Use JDBC=b(true);Connection URL=s(jdbc:h2:tcp://localhost:9092/mem:testdb);Database Class Name=s(org.h2.Driver);Server=s(localhost);User ID=s(sa);Password=;Trusted_Connection=b(false);JDBC Connection String=s(!org.h2.Driver!jdbc:h2:tcp://localhost:9092/mem:testdb;user=sa;)";
			DATABASE_NAME = "xe";
			DATABASE_DLL = "crdb_jdbc.dll";
			URI = "!org.h2.Driver!jdbc:h2:tcp://localhost:9092/mem:testdb;user=sa;";

		} else {
			CONNECTION_STRING = "Use JDBC=b(true);Connection URL=s(jdbc:oracle:thin:@localhost:1521:xe);Database Class Name=s(oracle.jdbc.driver.OracleDriver);Server=s(localhost);User ID=s(userapp);Password=;Trusted_Connection=b(false);JDBC Connection String=s(!oracle.jdbc.driver.OracleDriver!jdbc:oracle:thin:{userid}/{password}@localhost:1521:xe)";
			DATABASE_NAME = "xe";
			DATABASE_DLL = "crdb_jdbc.dll";
			URI = "!oracle.jdbc.driver.OracleDriver!jdbc:oracle:thin:{userid}/{password}@localhost:1521:xe";

		}

		// Obtain collection of tables from this database controller.
		Tables tables = databaseController.getDatabase().getTables();

		// Set the datasource for all main report tables.
		for (int i = 0; i < tables.size(); i++) {

			ITable table = tables.getTable(i);

			// Keep existing name and alias.
			table.setName(table.getName());
			table.setAlias(table.getAlias());

			// Change properties that are different from the original datasource.
			table.setQualifiedName(TABLE_NAME_QUALIFIER + table.getName());

			// Change connection information properties.
			IConnectionInfo connectionInfo = table.getConnectionInfo();

			// Set new table connection property attributes.
			PropertyBag propertyBag = new PropertyBag();

			// Overwrite any existing properties with updated values.
			propertyBag.put("Trusted_Connection", "false");
			propertyBag.put("Server Name", SERVERNAME); // Optional property.
			propertyBag.put("Connection String", CONNECTION_STRING);
			propertyBag.put("Database Name", DATABASE_NAME);
			propertyBag.put("Server Type", "JDBC (JNDI)");
			propertyBag.put("URI", URI);
			propertyBag.put("Use JDBC", "true");
			propertyBag.put("Database DLL", DATABASE_DLL);

			connectionInfo.setAttributes(propertyBag);

			// Set database username and password.
			// NOTE: Even if these the username and password properties don't change when
			// switching databases, the
			// database password is *not* saved in the report and must be set at runtime if
			// the database is secured.
			connectionInfo.setUserName(DBUSERNAME);
			connectionInfo.setPassword(DBPASSWORD);
			connectionInfo.setKind(ConnectionInfoKind.SQL);

			table.setConnectionInfo(connectionInfo);

			// Update old table in the report with the new table.
			databaseController.setTableLocation(table, tables.getTable(i));

		}
	}

	private Path processFilePath(String reportPath) throws IOException {

		Resource res = resourceLoader.getResource(formatClassPath(reportPath));

		logger.info("resource location: {}", res.toString());
		Path rel = null;
		if (res.isFile()) {
			logger.info("path is a file, no handling");
			rel = res.getFile().toPath();
		} else {

			logger.info("copy file from classpath to temp folder");
			InputStream classPathInput = res.getInputStream();

			String[] pathSplit = reportPath.split("/");

			Path tempPath = Files.createTempDirectory("rpt");
			Path destPath = Paths.get(tempPath.toString(), pathSplit[pathSplit.length - 1]);
			Files.copy(classPathInput, destPath);
			destPath = Paths.get(destPath.toString().replace("\\\\", "/").replace("\\", "/"));
			rel = destPath;

		}
		logger.info("path of report: {}", rel);
		return rel;
	}

	private String formatClassPath(String name) {
		return "classpath:" + name;
	}

	public CrystalReportType getReportType() {
		return crystalReportType;
	}

	@Override
	public void setReportType(Object crystalReportType) {
		this.crystalReportType = (CrystalReportType) crystalReportType;
	}

	public String getReportPath() {
		return reportPath;
	}

	@Override
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	@Override
	public void setParamList(List<Object> paramList) {
		this.paramList = paramList;
	}

}