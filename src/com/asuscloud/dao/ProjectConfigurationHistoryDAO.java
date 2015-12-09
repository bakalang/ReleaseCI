
package com.asuscloud.dao;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import util.db.DbMysqlUtil;
import util.log.Log4JUtils;

import com.asuscloud.svnkit.service.LoadSvnkitConfig;

public class ProjectConfigurationHistoryDAO
{
	private DbMysqlUtil dbUtil = new DbMysqlUtil(LoadSvnkitConfig.conf);
	private static Logger log = Log4JUtils.getClassnameLogger();
	
	public void insertConfigurationHistory(String projectNo, String remark, String path, String action) throws java.sql.SQLException, IOException
	{
		StringBuffer sql = new StringBuffer();
		ArrayList<Object[]> arrayData = new ArrayList<Object[]>();
		sql.append(" INSERT INTO project_configuration_history ");
		sql.append(" (project_no, remark, path, action) ");
		sql.append(" values (?, ?, ?, ?) ");

		Object[] oValue = new Object[]{( projectNo == null )? StringUtils.EMPTY: projectNo,
				( remark == null )? StringUtils.EMPTY: remark,
				( path == null )? StringUtils.EMPTY: path,
				( action == null )? StringUtils.EMPTY: action};
		arrayData.add(oValue);
		dbUtil.getConnection();
		dbUtil.doInsert(sql.toString(), arrayData);
		return;
	}
}
