package com.xww.application.service;

import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

import com.xww.user.ser.impl.UserServiceImpl;

//ʹ��webserviceͳһ�������ݷ��ʽӿ��м��
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class UserDataService
{
    public static void main(String[] args)
	{
    	System.out.println("webservice  service  is  start...");
    	
    	Endpoint.publish("http://127.0.0.1:8100/userdataservice/user", new UserServiceImpl());
    	
    	System.out.println("UserDataService  ���񷢲��ɹ�....");
	}
}
