
package com.asuscloud.doTestcase.service;

import java.util.List;

import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import com.ecareme.utils.ConfigUtils;

public interface TestcaseLoadConfig
{
	public static final XMLConfiguration conf = ConfigUtils.loadClassPathXML("omnistore_lab_config.xml");
	@SuppressWarnings("unchecked")
	public static final List<SubnodeConfiguration> testPackageList = ( (List<SubnodeConfiguration>)conf.configurationsAt("tng.testPackage") );

	public static final String className = conf.getString("db.className");
	public static final String dbPath = conf.getString("db.path");
	public static final String sp = conf.getString("tng.sp");
	public static final String sg = conf.getString("tng.sg");
	public static final String wr = conf.getString("tng.wr");
	public static final String ir = conf.getString("tng.ir");
	public static final String isHomeCloud = conf.getString("tng.isHomeCloud");
	public static final String outputPath = conf.getString("tng.outputPath");
}
