
package com.asuscloud.common;

import java.util.List;

import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import com.ecareme.utils.ConfigUtils;

public interface LoadMonitorConfig
{
	// load config for monitor test
	public static final XMLConfiguration conf = ConfigUtils.loadClassPathXML("omnistore_monitor.xml");

//	public static final XMLConfiguration conf = ConfigUtils.loadClassPathXML("omnistore_lab_config.xml");
//	public static final List<SubnodeConfiguration> testPackageList = ( (List<SubnodeConfiguration>)conf.configurationsAt("tng.testPackage") );
	public static final List<SubnodeConfiguration> conponentList = ( (List<SubnodeConfiguration>)conf.configurationsAt("tng.components") );

	public static final String testcase = conf.getString("tng.testcase");
	public static final String klass = conf.getString("tng.class");
//	public static final String sp = conf.getString("tng.sp");
//	public static final String sg = conf.getString("tng.sg");
//	public static final String bk = conf.getString("tng.bk");
//	public static final String ir = conf.getString("tng.ir");
//	public static final String wr = conf.getString("tng.wr");
//	public static final String md = conf.getString("tng.md");
//	public static final String amos = conf.getString("tng.amos");
//	public static final String amosp = conf.getString("tng.amosp");
//	public static final String cl = conf.getString("tng.cl");
//	public static final String ms = conf.getString("tng.ms");
//	public static final String ss = conf.getString("tng.ss");
//	public static final String wow = conf.getString("tng.wow");
//	public static final String ou = conf.getString("tng.ou");
//	public static final String eh = conf.getString("tng.eh");
//	public static final String sc = conf.getString("tng.sc");
//	public static final String isHomeCloud = conf.getString("tng.isHomeCloud");
	public static final String outputPath = conf.getString("tng.outputPath");

}
