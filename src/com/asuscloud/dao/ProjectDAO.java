
package com.asuscloud.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import util.db.DbMysqlUtil;
import util.log.Log4JUtils;

import com.asuscloud.bean.Project;
import com.asuscloud.svnkit.service.LoadSvnkitConfig;

public class ProjectDAO
{
	private DbMysqlUtil dbUtil = new DbMysqlUtil(LoadSvnkitConfig.conf);
	private static Logger log = Log4JUtils.getClassnameLogger();

	@SuppressWarnings("unused")
	public void updateVersion(String projectNo, String lastVersion) throws Exception
	{
		List<String> arrayData = new ArrayList<String>();
		StringBuffer stSQL = new StringBuffer();
		stSQL.append(" update project set ");
		addUpdatePara(stSQL, arrayData, "last_version", lastVersion);
		stSQL.append(" where project_no = '" + projectNo + "'");
		dbUtil.getConnection();

		if ( arrayData.size() > 0 )
			dbUtil.doUpdate(stSQL.toString());
		return;
	}
	
	public Integer getProjectVersion(String projectNo) throws java.sql.SQLException, IOException
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT last_version FROM project WHERE project_no=? ");
        Object[] oValue = new Object[]{projectNo};
		dbUtil.getConnection();
        List<?> queryResult = (List<?>)dbUtil.doQuery(sql.toString(), oValue);		
		
        return ( (Map<String, Integer>)queryResult.get(0) ).get("last_version");
        
	}

	@SuppressWarnings("unchecked")
	public Project getProjectNo(String module) throws java.sql.SQLException, IOException
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT project_no, module, repository, last_version FROM project WHERE 1=1 ");
		sql.append("AND module = '" + module + "' ");
		dbUtil.getConnection();
		List<?> queryResult = (List<?>)dbUtil.doQuery(sql.toString());
		Project project = new Project((Map<String, Object>)queryResult.get(0) );
		return project;
	}

	private void addUpdatePara(StringBuffer stSQL, List<String> arrayData, String key, String val)
	{
		if ( StringUtils.isNotBlank(val) )
		{
			if ( arrayData.size() != 0 )
				stSQL.append(" , ");
			stSQL.append(" " + key + "='" + val + "'");
			arrayData.add(key);
		}
		return;
	}
}
