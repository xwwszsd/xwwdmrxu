package com.xww.user.ser.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace="http://ymxp.com/wsdl")
public interface IUserService
{
	//��ѯ��ɫ������
     @WebMethod
     public String queryRoleData();
     
  // �ҳ�ѧ�������ְ���ѧ��������ְ������
 	@WebMethod
 	public String  queryGroupByRoleCount();
 	
 	//-- **ѧ����ѧ�γ̵�����
 	@WebMethod
 	public String  queryStuAndkmCount(String name);
 	
 	// ��¼
 	@WebMethod
 	public String  checklogin(String name,String pwd);
 	
 	//ע��
 	@WebMethod
 	public void zhuceinfo(String name,String pwd);
 	
 	//�Ź�����Ϣ
 	@WebMethod
 	public String menu();
 	
 	//������Ϣ
 	@WebMethod
 	public String antv1();
}
