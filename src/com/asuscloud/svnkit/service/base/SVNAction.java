
package com.asuscloud.svnkit.service.base;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

import util.log.Log4JUtils;

import com.asuscloud.common.TestcaseConstant;
import com.asuscloud.dao.ProjectCheckoutDAO;
import com.asuscloud.dao.ProjectConfigurationHistoryDAO;
import com.asuscloud.dao.ProjectDAO;
import com.asuscloud.executor.service.RunExecutor;
import com.asuscloud.svnkit.service.LoadSvnkitConfig;

public class SVNAction extends SVNBase implements TestcaseConstant
{
//	private final static String SOURCE_PATH = "/root/文件/build_source/";
//	private final static String ETC_PATH = "etc";
	private final static String SOURCE_PATH = LoadSvnkitConfig.sourcePath;
	private final static String ETC_PATH = LoadSvnkitConfig.etcPath;
	
	private static ProjectDAO projectDAO = new ProjectDAO();
	private static ProjectCheckoutDAO projectCheckoutDAO = new ProjectCheckoutDAO();
	private static ProjectConfigurationHistoryDAO projectConfigurationHistoryDAO = new ProjectConfigurationHistoryDAO();
	private static final Logger log = Log4JUtils.getClassnameLogger();

	public void checkout(int projectNo, String path, boolean mainProject) throws Exception
	{
		if ( cm == null )
			return;

		File destDir = new File(SOURCE_PATH + path);
		SVNUpdateClient uc = cm.getUpdateClient();
		uc.setIgnoreExternals(false);
		SVNDirEntry entry = repository.info(".", -1);
		String remark = null;
		long lastVersion = entry.getRevision();
		long dbVersion = projectDAO.getProjectVersion(String.valueOf(projectNo));
		System.out.println("no = " + String.valueOf(projectNo) + ", path = " + path + ",  dbVersion = " + dbVersion + ", lastVersion = " + lastVersion);
		log.info("no = " + String.valueOf(projectNo) + ", path = " + path + ",  dbVersion = " + dbVersion + ", lastVersion = " + lastVersion);
		if ( lastVersion > dbVersion )
		{
			Calendar c = Calendar.getInstance();
			remark = DATETIME_REMARK.format(c.getTime());
			// do checkout
			uc.doCheckout(repository.getLocation(), destDir, SVNRevision.create(lastVersion), SVNRevision.create(lastVersion), SVNDepth.INFINITY, true);

			// do check config changed
			if ( mainProject )
			{
				projectCheckoutDAO.insertCheckout(DATETIME.format(c.getTime()), String.valueOf(projectNo), String.valueOf(lastVersion), remark);

				Map<String, String> configChangedPathMap = getModifiedConfiguration(repository.log(new String[]{""}, null, dbVersion, lastVersion, true, true));
				System.out.println("currentVersionWithModifiedConfiguration size=" + configChangedPathMap.size());
				log.info("currentVersionWithModifiedConfiguration size=" + configChangedPathMap.size());

				if ( remark != null && configChangedPathMap.size() > 0 )
				{
					for ( String mapKey : configChangedPathMap.keySet() )
					{
						projectConfigurationHistoryDAO.insertConfigurationHistory(String.valueOf(projectNo), remark, mapKey, configChangedPathMap.get(mapKey));
					}
				}
			}

			// do update version
			projectDAO.updateVersion(String.valueOf(projectNo), String.valueOf(lastVersion));

			System.out.println("checkout " + path + " done!");
			// do build
			RunExecutor re = new RunExecutor();
			System.out.print(", return = " + re.runScript("ant -f " + destDir.getPath() + "/build.xml -Dtoday=" + remark));
			System.out.print(", build " + path + " done!");
		}
		return;
	}

	@SuppressWarnings("rawtypes")
	public void checkHistory() throws SVNException
	{

		SVNDirEntry entry = repository.info(".", -1);
		long lastVersion = entry.getRevision();

		Collection logEntries = repository.log(new String[]{""}, null, lastVersion, lastVersion, true, true);

		for ( Iterator entries = logEntries.iterator(); entries.hasNext(); )
		{
			SVNLogEntry logEntry = (SVNLogEntry)entries.next();
			System.out.println("---------------------------------------------");
			System.out.println("revision: " + logEntry.getRevision());
			System.out.println("author: " + logEntry.getAuthor());
			System.out.println("date: " + logEntry.getDate());
			System.out.println("log message: " + logEntry.getMessage());

			if ( logEntry.getChangedPaths().size() > 0 )
			{
				System.out.println();
				System.out.println("changed paths:");
				Set changedPathsSet = logEntry.getChangedPaths().keySet();

				for ( Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext(); )
				{
					SVNLogEntryPath entryPath = (SVNLogEntryPath)logEntry.getChangedPaths().get(changedPaths.next());
					System.out
							.println(" "
									+ entryPath.getType()
									+ " "
									+ entryPath.getPath()
									+ ( ( entryPath.getCopyPath() != null )? " (from " + entryPath.getCopyPath() + " revision " + entryPath.getCopyRevision()
											+ ")": "" ));
				}
			}
		}
		return;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, String> getModifiedConfiguration(Collection logEntries) throws SVNException, SQLException, IOException
	{
		Map<String, String> changedPathMap = new HashMap<String, String>();
		for ( Iterator entries = logEntries.iterator(); entries.hasNext(); )
		{
			SVNLogEntry logEntry = (SVNLogEntry)entries.next();

			if ( logEntry.getChangedPaths().size() > 0 )
			{
				Set changedPathsSet = logEntry.getChangedPaths().keySet();

				for ( Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext(); )
				{
					SVNLogEntryPath entryPath = (SVNLogEntryPath)logEntry.getChangedPaths().get(changedPaths.next());
					if ( entryPath.getPath().contains(ETC_PATH) )
						changedPathMap.put(entryPath.getPath(), String.valueOf(entryPath.getType()));
				}
			}
		}
		return changedPathMap;
	}
}
