package com.asuscloud.bean;

import java.util.Date;

import org.tmatesoft.svn.core.SVNLogEntry;

public class Commit
{
	private String project;
	private Date dateTime;
	private String revision;
	private String message;
	private String author;
	private int filesChanged;
	private String avatarUrl; 

	public Commit(String project,SVNLogEntry logEntry){
		  this.project=project;
		  this.dateTime=logEntry.getDate();
		  this.revision=Long.toString(logEntry.getRevision());
		  this.message=logEntry.getMessage();
		  this.author=logEntry.getAuthor();
		  this.filesChanged=logEntry.getChangedPaths().size();
		  this.avatarUrl="";
		}

	public String getProject()
	{
		return project;
	}

	public void setProject(String project)
	{
		this.project = project;
	}

	public Date getDateTime()
	{
		return dateTime;
	}

	public void setDateTime(Date dateTime)
	{
		this.dateTime = dateTime;
	}

	public String getRevision()
	{
		return revision;
	}

	public void setRevision(String revision)
	{
		this.revision = revision;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public int getFilesChanged()
	{
		return filesChanged;
	}

	public void setFilesChanged(int filesChanged)
	{
		this.filesChanged = filesChanged;
	}

	public String getAvatarUrl()
	{
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl)
	{
		this.avatarUrl = avatarUrl;
	}
	
}
