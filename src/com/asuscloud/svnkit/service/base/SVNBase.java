package com.asuscloud.svnkit.service.base;

	

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;

import com.asuscloud.bean.Login;
	
public class SVNBase
{
	public SVNRepository repository;
	public BasicAuthenticationManager authManager;
	public SVNClientManager cm;

	public final static String SVN_URL = "https://isurfcc-svn.cvsdude.com/";

	public void setup(String url, Login lo) throws SVNException
	{
		DAVRepositoryFactory.setup();
		repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(SVN_URL + url));
		authManager = BasicAuthenticationManager.newInstance(lo.getName(), lo.getPassword().toCharArray());
		repository.setAuthenticationManager(authManager);
		cm = SVNClientManager.newInstance();
		cm.setAuthenticationManager(authManager);
	}

	public SVNRepository getRepository()
	{
		return repository;
	}

	public BasicAuthenticationManager getAuthManager()
	{
		return authManager;
	}
}
