
package com.asuscloud.doTestcase.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.asuscloud.common.LoadMonitorConfig;
import com.asuscloud.common.TestcaseConstant.CaseType;
import com.asuscloud.doTestcase.service.listener.GuiTestListener;
import com.asuscloud.doTestcase.service.listener.TestCaseListener;
import com.ecareme.utils.DateUtils;
import com.google.gson.Gson;

public class LoadCaseService extends Thread
{
	private static List<Map<String, Object>> testngParams = new ArrayList<Map<String, Object>>();
	private static List<String> skipList = new ArrayList<String>();
	private GuiTestListener guiTestListener = null;
	private String logPath = null;
	private CaseType caseType;

	public LoadCaseService(List<Map<String, Object>> testngParams, String logPath)
	{
		this.testngParams = testngParams;
		this.logPath = logPath;
		this.guiTestListener = new GuiTestListener();
	}

	public LoadCaseService(List<Map<String, Object>> testngParams, String logPath, List<String> skipList)
	{
		this.testngParams = testngParams;
		this.logPath = logPath;
		this.guiTestListener = new GuiTestListener(skipList);
	}

	public LoadCaseService(CaseType caseType, String logPath)
	{
		this.caseType = caseType;
		this.logPath = logPath;
	}

	@SuppressWarnings("unchecked")
	public void run()
	{
		TestNG tng = new TestNG();
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		for ( Map<String, Object> _params : testngParams )
		{
			XmlSuite suite = new XmlSuite();
			System.out.println("testcase : "+_params.get("testcase").toString());
			suite.setName(_params.get("testcase").toString());
			XmlTest test = new XmlTest(suite);

			List<XmlClass> classes = new ArrayList<XmlClass>();
			System.out.println("class : "+_params.get("class").toString());
			classes.add(new XmlClass(_params.get("class").toString()));
			test.setXmlClasses(classes);
			test.setParameters((Map<String, String>)_params.get("params"));
			test.setVerbose(2);

			suites.add(suite);
		}
		tng.setXmlSuites(suites);
		tng.setOutputDirectory(logPath + "/" + DateUtils.formatDate(DateUtils.getCurrentTimestamp(), "yyyyMMddhhmm"));
		tng.addListener(new TestCaseListener());
		tng.addListener(guiTestListener);
		tng.run();
	}

	public void stopThread()
	{
		guiTestListener.stopThread();
	}

	private static List<Map<String, Object>> loadNormalParam()
	{
		List<Map<String, Object>> testngParams = new ArrayList<Map<String, Object>>();
		for ( Iterator<SubnodeConfiguration> it = TestcaseLoadConfig.testPackageList.iterator(); it.hasNext(); )
		{
			Map<String, Object> testPackage = null;
			Map<String, String> _params = new HashMap<String, String>();
			HierarchicalConfiguration sub = (HierarchicalConfiguration)it.next();
			testPackage = new HashMap<String, Object>();
			testPackage.put("testcase", sub.getString("testcase"));
			testPackage.put("class", sub.getString("class"));
			_params.put("isHomeCloud", TestcaseLoadConfig.isHomeCloud);
			if(TestcaseLoadConfig.sg != null)
				_params.put("sg", TestcaseLoadConfig.sg);
			if(TestcaseLoadConfig.wr != null)
				_params.put("wr", TestcaseLoadConfig.wr);
			if(TestcaseLoadConfig.ir != null)
				_params.put("ir", TestcaseLoadConfig.ir);
			List<SubnodeConfiguration> userList = ( (List<SubnodeConfiguration>)sub.configurationsAt("users") );

			Map<String, String> eee = new HashMap<String, String>();
			for ( Iterator<SubnodeConfiguration> itt = userList.iterator(); itt.hasNext(); )
			{
				HierarchicalConfiguration subb = (HierarchicalConfiguration)itt.next();
				eee.put(subb.getString("userId"), subb.getString("password"));
			}
			_params.put("users", new Gson().toJson(eee));

			testPackage.put("params", _params);
			testngParams.add(testPackage);
		}
		return testngParams;
	}

	private static List<Map<String, Object>> loadVersionParam()
	{
		List<Map<String, Object>> testngParams = new ArrayList<Map<String, Object>>();
		Map<String, String> _Params = new HashMap<String, String>();
		Map testPackage = new HashMap<String, Object>();
		testPackage.put("testcase", LoadMonitorConfig.testcase);
		testPackage.put("class", LoadMonitorConfig.klass);
		Map<String, String> eee = new HashMap<String, String>();
		for ( Iterator<SubnodeConfiguration> it = LoadMonitorConfig.conponentList.iterator(); it.hasNext(); )
		{
			HierarchicalConfiguration sub = (HierarchicalConfiguration)it.next();
			eee.put(sub.getString("name"), sub.getString("site"));
		}
		_Params.put("conponents", new Gson().toJson(eee));
		testPackage.put("params", _Params);
		testngParams.add(testPackage);
		return testngParams;

	}
	
	private static List<Map<String, Object>> getTestngParams(CaseType caseType) throws IOException
	{
		List<Map<String, Object>> testngParams = new ArrayList<Map<String, Object>>();

		switch ( caseType )
		{
			case VERSION :
				return loadVersionParam();
			case NORMAL :
				return loadNormalParam();
			default :
				break;
		}

		return testngParams;
	}


	public static void main(String args[]) throws InterruptedException, IOException
	{
//		 LoadCaseService tmpLoadTestCase = new LoadCaseService(getTestngParams(CaseType.VERSION), LoadConfig.outputPath);
		LoadCaseService tmpLoadTestCase = new LoadCaseService(getTestngParams(CaseType.NORMAL), TestcaseLoadConfig.outputPath);
		tmpLoadTestCase.start();
	}	
}
