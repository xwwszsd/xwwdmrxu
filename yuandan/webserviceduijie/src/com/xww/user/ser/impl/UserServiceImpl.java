package com.xww.user.ser.impl;

import java.util.List;

import javax.jws.WebService;

import com.xww.model.Data;
import com.xww.model.Menu;
import com.xww.model.Role;
import com.xww.model.StuAndRole;
import com.xww.user.dao.DB;
import com.xww.user.ser.interfaces.IUserService;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebService(portName = "userservice", serviceName = "UserServiceImpl", targetNamespace = "http://ymxp.com/wsdl", endpointInterface = "com.xww.user.ser.interfaces.IUserService")
public class UserServiceImpl implements IUserService
{

	@Override
	public String queryRoleData()
	{
		// TODO Auto-generated method stub

		System.out.println("UserServiceImpl  is queryRoleData  start...  ");

		DB db = new DB();

		List<Role> list = db.queryRoleData();

		System.out.println("--->" + list.size());

		// webservice发布的数据应该是各个平台和语言统一能够解析的数据格式:
		// json [{},{},{}]

		JSONArray ja = new JSONArray();

		for (Role role : list)
		{
			JSONObject jobj = new JSONObject();
			jobj.put("id", role.getRid());
			jobj.put("name", role.getRname());

			ja.add(jobj);
		}

		System.out.println("JSON-->" + ja.toString());
		return ja.toString();
	}

	@Override
	public String queryGroupByRoleCount()
	{
		// TODO Auto-generated method stub
		System.out.println("UserServiceImpl  is queryGroupByRoleCount  start...  ");

		DB db = new DB();

		List<StuAndRole> lists = db.queryRoleGroupCount();

		System.out.println("--->" + lists.size());
		JSONArray array = new JSONArray();
		for (StuAndRole crole : lists)
		{

			JSONObject obj = new JSONObject();
			obj.put("rname", crole.getRname());
			obj.put("rcount", crole.getRcount());
			array.add(obj);
		}
		System.out.println("JSON-->" + array.toString());

		return array.toString();
	}

	@Override
	public String queryStuAndkmCount(String name)
	{
		// TODO Auto-generated method stub
		System.out.println("UserServiceImpl  is queryGroupByRoleCount  start...  ");

		DB db = new DB();

		String data = db.queryStuAndkmCount(name);

		System.out.println("data-->" + data);
		return data;
	}

	@Override
	public String checklogin(String name, String pwd)
	{
		// TODO Auto-generated method stub
		System.out.println("用户名：" + name + "密码：" + pwd);
		DB db = new DB();
		boolean flag = db.checkusername(name, pwd);
		if (flag)
		{
			return "成功";
		}
		else
		{
			return "用户名或密码错误";
		}
	}

	@Override
	public void zhuceinfo(String name, String pwd)
	{
		// TODO Auto-generated method stub
		System.out.println("zhuceinfo------------>start" + name);
		DB db = new DB();
		db.zhuce(name,pwd);
		
	}

	@Override
	public String menu()
	{
		// TODO Auto-generated method stub
		System.out.println("menu------------>start" );
		DB db = new DB();
		List<Menu> list=db.menuinfo();
		
		//alibaba
		String strJson = com.alibaba.fastjson.JSONArray.toJSONString(list);
        //JSONArray arr =new JSONArray();
        
		System.out.println("strJson-->" + strJson);

		return strJson;
	}

	@Override
	public String antv1()
	{
		// TODO Auto-generated method stub
		
		System.out.println("antv1------------>start" );
		DB db=new DB();
		List<Data>list=db.queryfruitadd();
		String strJson = com.alibaba.fastjson.JSONArray.toJSONString(list);
		System.out.println("antv1----strJson"+strJson);
		return strJson;
	}

//	public static void main(String[] args)
//	{
//		UserServiceImpl usi=new UserServiceImpl();
//		usi.antv1();
//		System.out.println(usi.antv1());
//	}
	
}
