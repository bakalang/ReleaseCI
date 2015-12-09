package com.asuscloud.bean;

	
public class Login
{
	private String name;
	private String password;
//	private String repositery;
//	private String medule;
	
	public Login(String name, String password)
	{
		this.name = name;
		this.password = password;
//		this.repositery = repositery;
//		this.medule = medule;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
//	public String getRepositery()
//	{
//		return repositery;
//	}
//	public void setRepositery(String repositery)
//	{
//		this.repositery = repositery;
//	}
//	public String getMedule()
//	{
//		return medule;
//	}
//	public void setMedule(String medule)
//	{
//		this.medule = medule;
//	}

	
}
