package com.company.test.run;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomTestListener implements ITestListener {

    private BufferedWriter logWriter;
    private ExtentReports extent;
    private ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        // Create the TestReports folder and log file
        String reportsRootFolder = System.getProperty("user.dir") + "/TestReports";

        // Timestamped subfolder for each execution
        String timeStamp = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(LocalDateTime.now());
        String reportsFolder = reportsRootFolder + "/" + timeStamp;

        File reportDir = new File(reportsFolder);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        // Set up the log file
        File logFile = new File(reportDir, "TestExecutionLog.txt");
        try {
            logWriter = new BufferedWriter(new FileWriter(logFile));
            logWriter.write("Test Execution Log - " + LocalDateTime.now());
            logWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set up ExtentReports
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportDir + "/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        System.out.println("Test Execution Started. Logs will be saved in: " + reportDir.getAbsolutePath());
    }

    @Override
    public void onTestStart(ITestResult result) {
        try {
            logWriter.write("Started Test: " + result.getMethod().getMethodName());
            logWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        test = extent.createTest(result.getMethod().getMethodName());
        test.log(Status.INFO, "Test started: " + result.getMethod().getMethodName());
        System.out.println("Started Test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            logWriter.write("Test Passed: " + result.getMethod().getMethodName());
            logWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        test.log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
        System.out.println("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            logWriter.write("Test Failed: " + result.getMethod().getMethodName());
            logWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        test.log(Status.FAIL, "Test failed: " + result.getMethod().getMethodName());
        test.fail(result.getThrowable());
        System.out.println("Test Failed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            logWriter.write("Test Skipped: " + result.getMethod().getMethodName());
            logWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        test.log(Status.SKIP, "Test skipped: " + result.getMethod().getMethodName());
        System.out.println("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            logWriter.write("Test Execution Completed.");
            logWriter.newLine();
            logWriter.write("Total Tests Passed: " + context.getPassedTests().size());
            logWriter.newLine();
            logWriter.write("Total Tests Failed: " + context.getFailedTests().size());
            logWriter.newLine();
            logWriter.write("Total Tests Skipped: " + context.getSkippedTests().size());
            logWriter.newLine();
            logWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        extent.flush();
        System.out.println("Test Execution Finished. Extent report generated.");
    }
}
