
package com.asuscloud.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.db.DbMysqlUtil;
import util.log.Log4JUtils;

import com.asuscloud.bean.Project;
import com.asuscloud.svnkit.service.LoadSvnkitConfig;


public class ProjectDependenceDAO
{
	private DbMysqlUtil dbUtil = new DbMysqlUtil(LoadSvnkitConfig.conf);
	private static Logger log = Log4JUtils.getClassnameLogger();
	
	@SuppressWarnings("unchecked")
	public List<Project> getDependence(Integer projectNo) throws java.sql.SQLException, IOException
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.depend_on, b.project_no, b.module, b.repository, b.last_version ");
		sql.append(" FROM project_dependence a ");
		sql.append(" LEFT OUTER JOIN project b ON a.depend_on = b.project_no ");
		sql.append(" WHERE a.project_no = ? ");

		Object[] oValue = new Object[]{projectNo.toString()};		
		dbUtil.getConnection();
		List<?> queryResult = (List<?>)dbUtil.doQuery(sql.toString(), oValue);

		List<Project> rsList = null;
		Project project = null;
		if ( queryResult.size() > 0 )
		{
			rsList = new ArrayList<Project>();
			for ( int i = 0; i < queryResult.size(); i++ )
			{
				project = new Project((Map<String, Object>)queryResult.get(i));
				rsList.add(project);
			}
		}
		return rsList;
	}
}
