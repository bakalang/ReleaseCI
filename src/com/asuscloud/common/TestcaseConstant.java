
package com.asuscloud.common;

import java.text.SimpleDateFormat;

import com.asuscloud.bean.Login;

public interface TestcaseConstant
{

	public static final String KEY_CONFIG = "com.asuscloud.testcase.config".intern();
	public static final String KEY_COMPONENT = "testcase".intern();
	public static final String KEY_CLASS = "class".intern();
	public static final String KEY_USERID = "userId".intern();
	public static final String KEY_PASSWORD = "password".intern();
	public static final String KEY_COMMERCIALID = "commercialId".intern();
	public static final String KEY_U2 = "u2".intern();
	public static final String KEY_P2 = "p2".intern();
	public static final String KEY_U3 = "u3".intern();
	public static final String KEY_P3 = "p3".intern();
	public static final String BTN_REG = "reg".intern();

	public static final String SUB_PANEL = "sub".intern();
	public static final String MAIN_PANEL = "main".intern();
	public static final String LOG_PANEL = "outlog".intern();

	public static final String NUMBER_SIGN = "#".intern();

	//Svnkit
	public static Login login = new Login("sky_hu", "ji3g48d93");
	
	//DateFormat
	public final SimpleDateFormat DATETIME_REMARK = new SimpleDateFormat("yyyyMMddhhmmss");
	public final SimpleDateFormat DATETIME = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public enum CaseType
	{
		VERSION, NORMAL, SERVICEGATEWAY
	}
}
