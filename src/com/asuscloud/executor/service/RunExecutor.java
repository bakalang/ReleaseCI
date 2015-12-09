
package com.asuscloud.executor.service;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;

public class RunExecutor
{
	int iExitValue;

	public String runScript(String command)
	{
		CollectingLogOutputStream clos = new CollectingLogOutputStream();
		CommandLine cl = CommandLine.parse(command);
		DefaultExecutor de = new DefaultExecutor();
		PumpStreamHandler psh = new PumpStreamHandler(clos);
		de.setExitValue(0);
		de.setStreamHandler(psh);
		try
		{
			iExitValue = de.execute(cl);
		}
		catch ( ExecuteException e )
		{
			// TODO Auto-generated catch block
			System.err.println("Execution failed.");
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			System.err.println("permission denied.");
			e.printStackTrace();
		}
		return String.valueOf(iExitValue);
	}

	public static void main(String args[])
	{
//		Map<String, String> env = System.getenv();

		RunExecutor re = new RunExecutor();
		// System.out.println("return = "+testScript.runScript("echo !!!"));
		// System.out.println("return = "+testScript.runScript("docker stats 7fb1b511f405"));
		System.out.println("return = " + re.runScript("ant -f /root/文件/build_source/one.serviceportal/portalapi/build.xml"));
		//System.out.println("return = " + testScript.runScript("cd /home/ecareme"));
		// System.out.println("return = "+testScript.runScript("docker ps -a"));
	}
}
