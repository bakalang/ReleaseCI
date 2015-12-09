
package com.asuscloud.doTestcase.service.listener;

import java.util.ArrayList;
import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;

public class GuiTestListener implements IInvokedMethodListener
{
	private List<String> skipList = new ArrayList<String>();
	private boolean isRunning = true;

	public GuiTestListener()
	{}
	
	public GuiTestListener(List<String> skipList)
	{
		this.skipList = skipList;
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult itr)
	{
		if ( method.isTestMethod() && !itr.isSuccess() )
		{
			System.out.println(method.getTestMethod().getMethodName());
		}
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult itr)
	{
		if(!isRunning || skipList.contains(method.getTestMethod().getMethodName())){
			throw new SkipException("skip case");
		}				
	}
	
	public void stopThread()
	{
		this.isRunning  = false;
	}

}
