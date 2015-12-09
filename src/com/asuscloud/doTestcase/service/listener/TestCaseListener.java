
package com.asuscloud.doTestcase.service.listener;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import util.log.Log4JUtils;


public class TestCaseListener implements ITestListener
{
	private static Logger log = Log4JUtils.getClassnameLogger();	

	@Override
	public void onFinish(ITestContext tr)
	{
		int passedNb = tr.getPassedTests().size();
	    int failedNb = tr.getFailedTests().size();
	    int skippedNb = tr.getSkippedTests().size();
		log.info(tr.getName() + " Success, passedNb="+passedNb+", failedNb="+failedNb+", skippedNb="+skippedNb);
	}

	@Override
	public void onStart(ITestContext tr)
	{
		log.info(tr.getName() + " Start");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult tr)
	{
		log.info(tr.getName() + " TestFailedButWithinSuccessPercentage");
	}

	@Override
	public void onTestFailure(ITestResult tr)
	{
		log.info(tr.getName() + " TestFailure");
	}

	@Override
	public void onTestSkipped(ITestResult tr)
	{
		log.info(tr.getName() + " TestSkipped");
	}

	@Override
	public void onTestStart(ITestResult tr)
	{
		log.info(tr.getName() + " TestStart");
	}

	@Override
	public void onTestSuccess(ITestResult tr)
	{
		log.info(tr.getName() + " Success");
	}

}
