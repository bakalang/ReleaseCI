
package com.asuscloud.svnkit.service;

import org.apache.commons.configuration.Configuration;
import util.ConfigUtils;

public interface LoadSvnkitConfig
{

	public static Configuration conf = ConfigUtils.loadClassPathXML("svnkit_config.xml");
	
//	public static final int extractThread = conf.getInt("csvData.extractThread");
//	public static final int skipLine = conf.getInt("csvData.skipLine");
//	
//	public static final String className = conf.getString("db.className");
//	public static final String address = conf.getString("db.address");	
//	public static final String port = conf.getString("db.port");	
//	public static final String account = conf.getString("db.account");	
//	public static final String passwd = conf.getString("db.passwd");	
//	public static final String databaseName = conf.getString("db.databaseName");
//	public static final String tableName = conf.getString("db.table");
//	
//
	public static final String sourcePath = conf.getString("sourcePath");
	public static final String etcPath = conf.getString("etcPath");
//
//	public static final String sourceURL = conf.getString("jsonData.sourceURL");
//	public static final String[] columnJsonList = conf.getStringArray("jsonData.column");
//	public static final String[] subStringJsonList = conf.getStringArray("jsonData.subString");
}
