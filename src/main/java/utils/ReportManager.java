package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager implements ITestListener {

	public ExtentSparkReporter reporter;
	public ExtentReports reports;
	public ExtentTest test;

	String reportName;

	public void onStart(ITestContext testContext) {

		Instant now = Instant.now();
		try {
			reportName = FileManager.loadProperties("trello_main.properties").getProperty("extent") + "-"
					+ now.toString().replace(":", "") + ".html";
		} catch (FileNotFoundException e) {
			System.out.println("Properties file 'trello_main.properties' not found!");
			reportName = "report.html";
		} catch (IOException e) {
			System.out.println("Error while loading properties file: " + e.getMessage());
		}

		String userDir = System.getProperty("user.dir");
		String file = userDir + File.separator + "reports" + File.separator + reportName;

		reporter = new ExtentSparkReporter(file);
		reporter.config().setDocumentTitle("Trello API Rest-Assured Automation Testing");

		reports = new ExtentReports();
		reports.attachReporter(reporter);
		reports.setSystemInfo("Application", "Trello API Rest-Assured Automation");
		reports.setSystemInfo("Author", "Kwazi Zwane");

	}

	public void onTestSuccess(ITestResult result) {

		test = reports.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());

		test.log(Status.PASS, "Test Passed !!");
	}

	public void onTestFailure(ITestResult result) {

		test = reports.createTest(result.getName());
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.FAIL, "Test Failed :(");
		test.log(Status.FAIL, result.getThrowable().getMessage());
	}

	public void onTestSkipped(ITestResult result) {

		test = reports.createTest(result.getName());
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.SKIP, "Test Skipped");
		test.log(Status.SKIP, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext testContext) {

		reports.flush();
	}
}
