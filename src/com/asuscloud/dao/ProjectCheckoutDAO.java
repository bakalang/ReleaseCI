
package com.asuscloud.dao;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.asuscloud.svnkit.service.LoadSvnkitConfig;

import util.db.DbMysqlUtil;
import util.log.Log4JUtils;

public class ProjectCheckoutDAO
{

	private DbMysqlUtil dbUtil = new DbMysqlUtil(LoadSvnkitConfig.conf);
	private static Logger log = Log4JUtils.getClassnameLogger();

	public void insertCheckout(String time, String projectNo, String version, String remark) throws java.sql.SQLException, IOException
	{
		StringBuffer sql = new StringBuffer();
		ArrayList<Object[]> arrayData = new ArrayList<Object[]>();
		sql.append(" INSERT INTO project_checkout ");
		sql.append(" (checkout_time, project_no, version, remark) ");
		sql.append(" values (?, ?, ?, ?) ");

		Object[] oValue = new Object[]{time, ( projectNo == null )? StringUtils.EMPTY: projectNo, ( version == null )? StringUtils.EMPTY: version,
				( remark == null )? StringUtils.EMPTY: remark};
		arrayData.add(oValue);
		dbUtil.getConnection();
		dbUtil.doInsert(sql.toString(), arrayData);
		return;
	}
}
