
package com.asuscloud.doTestcase.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.ecareme.http.api.APIException;
import com.ecareme.qed.bean.RegisterBean;
import com.ecareme.qed.bean.ResultBean;
import com.ecareme.qed.bean.TeamMember;
import com.ecareme.qed.bean.TestUserInfo;
import com.ecareme.qed.bean.Token;
import com.ecareme.qed.bean.UserState;
import com.ecareme.qed.client.http.EeeStorageCookie;
import com.ecareme.qed.core.Field;
import com.ecareme.test.api.ManagerstudioApi;
import com.ecareme.test.api.ServiceGateWayApi;
import com.ecareme.test.api.ServicePortalApi;
import com.ecareme.test.constant.TestConstantCollection;
import com.ecareme.test.util.AuthHeader;

public class APICallerService implements TestConstantCollection
{
	private int sid;
	private Field authorization;
	private EeeStorageCookie cookie = new EeeStorageCookie();
	// private final String AREA_2 = "2";
	private final String ZH_TW = "zh_TW";

	public APICallerService(int sid, String progkey) throws UnsupportedEncodingException
	{
		this.sid = sid;
		// this.progkey = progkey;
		this.authorization = AuthHeader.getAuthHeader(progkey);
		// this.cookie = new EeeStorageCookie();
		this.cookie.setSid(String.valueOf(sid));
	}

	public RegisterBean newregisteruser(String sp) throws APIException, IOException
	{
		RegisterBean bean = ServicePortalApi.getRegisterBean(false, null, true, true);
		EeeStorageCookie cookie = new EeeStorageCookie();
		cookie.setSid(String.valueOf(sid));
		return ( ServicePortalApi.newregisteruser(sp, bean, null, cookie, null, true, false, authorization, "1").getStatus() == 0 )? bean: null;
	}

	public RegisterBean managerstudioregister(String sp, String ms, String productType) throws APIException, IOException
	{

		RegisterBean bean = ServicePortalApi.getRegisterBean(false, null, true, true);
		ServicePortalApi.managerstudioregister(sp, bean.getUserid(), bean.getHashpwd(), bean.getEmail(), productType, AREA_1, ZH_TW, true);
		return ( ManagerstudioApi.isuserexist(ms, bean.getUserid(), true) == 0 )? bean: null;
	}

	public String managerstudioregister(String ms, String userId) throws APIException, IOException
	{

		ResultBean result = ManagerstudioApi.enableteam(ms, userId, true);
		return ( result.getStatus() == 0 )? result.get("commercialid"): null;
	}

	public String getEnableTeamCommercialId(String ms, String userId) throws APIException, IOException
	{
		ResultBean result = ManagerstudioApi.enableteam(ms, userId, true);
		return result.get("commercialid");
	}

	public String getTeamCommercialId(String sg, String userId, String password) throws APIException, IOException
	{
		Token token = ServiceGateWayApi.requsetToken(sg, userId, password, false, false);
		TeamMember info = token.getMemberinfo();
		return info.getCommercialid();
	}

	@SuppressWarnings("unused")
	public void deleteTeammember(String sg, String ms, String adminUserId, String memberUserId, String commercialId) throws APIException, IOException
	{
		// update user state
		UserState userState = ServiceGateWayApi.updateuserstate(sg, ServiceGateWayApi.getAccountInfo(sg, memberUserId).getAccountstr(), Service_Omnistore,
				AccountStatus_NORMAL_Str, AccountStatus_SUSPEND_Str, false);
		ResultBean rsBean = ManagerstudioApi.deleteTeammember(ms, adminUserId, commercialId, memberUserId, true, cookie, authorization);
		return;
	}

	public void addTeammember(String ms, String commercialId, String diskquota) throws APIException, IOException
	{
		RegisterBean bean_member = ServicePortalApi.getRegisterBean(false, null, true, true);
		bean_member.setLanguage(ZH_TW);
		ManagerstudioApi
				.addteammember(ms, bean_member.getUserid(), bean_member.getHashpwd(), bean_member.getEmail(), null, diskquota, null, commercialId, true);
		return;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public List<Vector> getTeaminfo(String ms, String sg, String userId, String commercialId) throws APIException, IOException
	{
//		ArrayList<TeamMember> listTeamMember = (ArrayList<TeamMember>)ManagerstudioApi.getteaminfo(ms, commercialId, true).getObject("member");
		List<TeamMember> listTeamMember =  (List<TeamMember>)ManagerstudioApi.listTeamMembers(ms, userId, commercialId, true).getObject(TAG_member);
		TestUserInfo userinfo = null;
		List<Vector> _row = new ArrayList<Vector>();
		if ( !listTeamMember.isEmpty() )
		{
			for ( TeamMember teamMember : listTeamMember )
			{
				userinfo = ServiceGateWayApi.getAccountInfo(sg, teamMember.getUserid());

				Vector<String> _column = new Vector<String>();
				// _column.add(userinfo.getAccountstr());
				_column.add(teamMember.getIsadministrator().equals("1")? "A": "");
				_column.add(teamMember.getUserid());
				_column.add(String.valueOf(userinfo.getUsedcapacity()));
				_column.add(String.valueOf(userinfo.getPackageinfo().getCapacity()));
				_column.add(teamMember.getState());
				_row.add(_column);
			}
		}

		return _row;
	}

	@SuppressWarnings("unused")
	public static void main(String args[]) throws APIException, IOException	
	{
		APICallerService rg = new APICallerService(10014, "94C3C2CEDDD345AFA269643B0AA27A29");
		RegisterBean rsbean = rg.newregisteruser("192.168.1.225:8443");
		rsbean = rg.managerstudioregister("192.168.1.225:8443", "192.168.1.226:443", "WebStorage500G");

	}
}
