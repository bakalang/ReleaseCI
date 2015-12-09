
package com.asuscloud.svnkit.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.log.Log4JUtils;

import com.asuscloud.bean.Login;
import com.asuscloud.bean.Project;
import com.asuscloud.dao.ProjectDAO;
import com.asuscloud.dao.ProjectDependenceDAO;
import com.asuscloud.svnkit.service.base.SVNAction;


public class SvnRepositoryService extends SVNAction 
{
	private static ProjectDAO projectDAO = new ProjectDAO();
	private static ProjectDependenceDAO projectDependenceDAO = new ProjectDependenceDAO();
	private static final Logger log = Log4JUtils.getClassnameLogger();

	public static void main(String[] args) throws Exception
	{
//		 String moduleName = "portalapi";
		// String moduleName = "otm";
		// String moduleName = "inforelayapi";
		 String moduleName = "filerelayapi";
//		 String moduleName = "MetadataServerAPI";
//		String moduleName = "servicegatewayapi";
		// String moduleName = "ManagerStudio-FETnet";
		// String moduleName = "dao.omnistore";
		// String moduleName = "servicegatewayapi";

		checkoutProject(login, moduleName);
	}

	private static void checkoutProject(Login login, String moduleName) throws Exception
	{
		// declare
		Map<Integer, String> dc = new LinkedHashMap<Integer, String>();

		// get relate project
		Project project = projectDAO.getProjectNo(moduleName);
		getDependenceLoop(project.getProjectNo(), dc);
		dc.put(project.getProjectNo(), project.getRepository() + "/" + project.getModule());

		for ( int projectNo : dc.keySet() )
		{
			String path = dc.get(projectNo);
			SVNAction svnaction = new SVNAction();
			svnaction.setup(path, login);
			
			// chechout
			System.out.println("start  " + path + " checkout!");
			log.info("start  " + path + " checkout!");
			svnaction.checkout(projectNo, path, projectNo==project.getProjectNo() );		
			System.out.println("----------------------------------------------");
			log.info("----------------------------------------------");
		}
		return;
	}

	private static void getDependenceLoop(Integer projectNo, Map<Integer, String> dc) throws SQLException, IOException
	{
		List<Project> tmpDepList = projectDependenceDAO.getDependence(projectNo);
		if ( tmpDepList != null )
		{
			for ( Project project : tmpDepList )
			{
				dc.put(project.getProjectNo(), project.getRepository() + "/" + project.getModule());
				getDependenceLoop(project.getProjectNo(), dc);
			}
		}
	}
}
