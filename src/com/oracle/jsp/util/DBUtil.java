package com.oracle.jsp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库的工具类
 * 
 * @author zm
 * 
 */
public class DBUtil
{
	// eshop为数据库名称，db_user为数据库用户名db_password为数据库密码
	public static String db_url = "jdbc:mysql://localhost:3306/shoppingonline?useUnicode=true&characterEncoding=UTF-8";
	public static String db_user = "root";
	public static String db_password = "zxc123456";

	public static Connection getConn()
	{
		Connection conn = null;
		//System.out.println("尝试连接");
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(db_url, db_user, db_password);
		} catch (Exception e)
		{
			System.out.println("数据库链接失败");
			e.printStackTrace();
		}
		System.out.println("连接成功");
		return conn;
	}

	public static void close(Statement state, Connection conn)
	{
		if (state != null)
		{
			try
			{
				state.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs, Statement state, Connection conn)
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if (state != null)
		{
			try
			{
				state.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}
