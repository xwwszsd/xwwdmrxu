package com.xww.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xww.model.Data;
import com.xww.model.Menu;
import com.xww.model.Role;
import com.xww.model.StuAndRole;

import file.FilePropertiesUtils;


public class DB
{
    static String  urlimg="";
	
	
	static
	{
		urlimg=FilePropertiesUtils.getImageUtilPath();
	}
	Connection conn;
    public DB()
    {
    	
    	try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ymxp","root","123456");
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public List queryRoleData()
    {
    	String sql="SELECT  *  FROM   t_role";
    	List<Role> list=new ArrayList<Role>();
    	try
		{
			PreparedStatement pst=conn.prepareStatement(sql);
			
			ResultSet rs=pst.executeQuery();
			
			
			while(rs.next())
			{
				Role ro=new Role();
				ro.setRid(rs.getInt(1));
				ro.setRname(rs.getString(2));
				
				list.add(ro);
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally
		{
			if(null!=conn)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    	
    	System.out.println(list);
    	return list;
    }
    
    public  List   queryRoleGroupCount()
	{
    String sql="SELECT  rname,COUNT(sjob)    FROM  t_stus  RIGHT  JOIN  t_role ON sjob=rid  GROUP BY  rname";
		
		List<StuAndRole>  lists  = new ArrayList<StuAndRole>();
		
		try {
			PreparedStatement  pstmt=conn.prepareStatement(sql);
		 
			ResultSet  rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				StuAndRole  crole  = new StuAndRole();
				crole.setRname(rs.getString(1));
				crole.setRcount(rs.getInt(2));
				
				lists.add(crole);
			}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(null!=conn)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return lists;
	}
	
	public  String   queryStuAndkmCount(String  stuName)
	{
       String sql="SELECT COUNT(kid),sname  FROM (SELECT   * FROM  t_stus  WHERE  sname=?) tmp INNER  JOIN  " +
    		" t_score  ON tmp.sid=t_score.sid  GROUP  BY sname";
		
       String data="";
		
		try {
			PreparedStatement  pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,stuName);
			ResultSet  rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				data=rs.getInt(1)+","+rs.getString(2);
			}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(null!=conn)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return data;
	}
	
	// 检查用户名，密码是否正确
		public boolean checkusername(String name, String pwd)
		{
			String sql = "SELECT COUNT(*) FROM t_stus WHERE sname=? AND spwd=?";
			String[] datas = null;

			try
			{
				PreparedStatement sptm = conn.prepareStatement(sql);
				sptm.setString(1, name);
				sptm.setString(2, pwd);
				ResultSet rs = sptm.executeQuery();

				while (rs.next())
				{
					int count = rs.getInt(1);
					System.out.println("count-------->" + count);
					if (count > 0)
						return true;
				}

			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				if(null!=conn)
				{
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return false;
		}
		
		// 向数据库提交注册的数据
		public void zhuce(String name, String pwd)
		{
			String sql = "INSERT  INTO  t_stus(sname, spwd)VALUES(?,?)";
			//Object[][] datas = null;
	
			try
			{
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, name);
				pst.setString(2, pwd);
				

				pst.executeUpdate();

			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				if(null!=conn)
				{
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
		//九宫格信息
		public List<Menu> menuinfo()
		{
			String sql="select * from t_km";
			List<Menu> list=new ArrayList<Menu>();
			try
			{
				PreparedStatement pst=conn.prepareStatement(sql);
				
				ResultSet rs=pst.executeQuery();
				
				while(rs.next())
				{
					Menu m=new Menu();
					m.setTid(rs.getInt(1));
					m.setTname(rs.getString(2));
					m.setTurl(rs.getString(3));
					m.setImgpath(urlimg+rs.getString(5)); 
					
					list.add(m);
				}
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				if(null!=conn)
				{
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return list;
		}
		
		//报表  水果产地
		public List queryfruitadd()
		{
			
			String sql="SELECT tyfadd,COUNT(*) FROM t_type GROUP BY tyfadd";
			List<Data> list= new ArrayList<Data>();
			try
			{
				PreparedStatement pst=conn.prepareStatement(sql);
				ResultSet rs=pst.executeQuery();
				
				while(rs.next())
				{
					Data da=new Data();
					da.setTyfadd(rs.getString(1));
					da.setCount(rs.getInt(2));
					
					list.add(da);
				}
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
}
