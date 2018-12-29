package com.xww.user.ser.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace="http://ymxp.com/wsdl")
public interface IUserService
{
	//查询角色的数据
     @WebMethod
     public String queryRoleData();
     
  // 找出学生表各个职务的学生数量和职务名称
 	@WebMethod
 	public String  queryGroupByRoleCount();
 	
 	//-- **学生所学课程的数量
 	@WebMethod
 	public String  queryStuAndkmCount(String name);
 	
 	// 登录
 	@WebMethod
 	public String  checklogin(String name,String pwd);
 	
 	//注册
 	@WebMethod
 	public void zhuceinfo(String name,String pwd);
 	
 	//九宫格信息
 	@WebMethod
 	public String menu();
 	
 	//报表信息
 	@WebMethod
 	public String antv1();
}
