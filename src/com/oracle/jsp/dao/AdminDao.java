package com.oracle.jsp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.oracle.jsp.bean.AdminBean;
import com.oracle.jsp.util.DBUtil;
import com.oracle.jsp.util.MD5;

/**
 * admin表数据库操作
 * 
 * @author wjxing
 *
 */
public class AdminDao {

	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public AdminBean checkLogin(String username, String password) {
		Connection conn = DBUtil.getConn();
		AdminBean adminBean = null;
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("select * from admin where username='" + username + "'");
			if (rs.next()) {
				// 如果有结果，是认为是通过验证了
				if (rs.getString("password").equals(MD5.GetMD5Code(password+rs.getString("salt")))) {
					adminBean = new AdminBean();
					adminBean.setId(rs.getInt("id"));
					adminBean.setUsername(rs.getString("username"));
					adminBean.setPassword(rs.getString("password"));
					adminBean.setSalt(rs.getString("salt"));
					adminBean.setCreateDate(rs.getString("create_date"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adminBean;
	}
	/**
	 * 检查是否存在此用户
	 */
	public boolean checkReg(String name) {
		boolean flag = true;
		// 查询用户是否已存在
		Connection connection = DBUtil.getConn();
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("select username from admin");
			while (rs.next()) {
				if (name.equals(rs.getString("username"))) {

					flag = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, statement, connection);
		}
		return flag;
	}
	
	/** 
	  *  添加管理员 
	  **/ 
	  public void save(AdminBean adminBean) { 
		    // TODO Auto-generated method stub 
		    String  sql  =  "insert  into  admin(username,password,salt,create_date)  values('"  
		+ adminBean.getUsername() + "','" 
		+ adminBean.getPassword()  +  "','"
		+ adminBean.getSalt()  +  "','"  
		+ adminBean.getCreateDate() + "')"; 
		    Connection conn = DBUtil.getConn(); 
		    Statement state = null; 
		    try { 
		      state = conn.createStatement(); 
		      state.executeUpdate(sql); 
		    } catch (Exception e) { 
		        e.printStackTrace(); 
		      } finally { 
		        DBUtil.close(state, conn); 
		      } 
		    } 
	  /** 
	   *  查看管理员 
	   *   
	   * @return 
	   */ 
	  public List<AdminBean> list() { 
		    // TODO Auto-generated method stub 
		    String sql = "select * from admin"; 
		    Connection connection = DBUtil.getConn(); 
		    Statement statement = null; 
		    ResultSet resultSet = null; 
		    List<AdminBean> adminBeans = new ArrayList<AdminBean>(); 
		    try { 
		      statement = connection.createStatement(); 
		      resultSet = statement.executeQuery(sql); 
		      AdminBean adminBean; 
		      while (resultSet.next()) { 
		        adminBean = new AdminBean(); 
		        adminBean.setId(resultSet.getInt("id")); 
		        adminBean.setUsername(resultSet.getString("username")); 
		        adminBean.setPassword(resultSet.getString("password")); 
		        adminBean.setSalt(resultSet.getString("salt")); 
		        adminBean.setCreateDate(resultSet.getString("create_date")); 
		        adminBeans.add(adminBean); 
		      } 
		 
		    } catch (Exception e) { 
		      e.printStackTrace(); 
		    } finally { 
		      DBUtil.close(resultSet, statement, connection); 
		    } 
		    return adminBeans; 
		  } 
	  /**
		 * 通过 id 获取 adminBean 对象
		 * */
	  public AdminBean getById(int id) { 
		    // TODO Auto-generated method stub 
		    String sql = "select * from admin where id =" + id; 
		    Connection connection = DBUtil.getConn(); 
		    Statement statement = null; 
		    ResultSet resultSet = null; 
		    AdminBean adminBean = null; 
		    try { 
		      statement = connection.createStatement(); 
		      resultSet = statement.executeQuery(sql); 
		      while (resultSet.next()) { 
		        adminBean = new AdminBean(); 
		        adminBean.setId(resultSet.getInt("id")); 
		        adminBean.setUsername(resultSet.getString("username")); 
		        adminBean.setPassword(resultSet.getString("password")); 
		        adminBean.setSalt(resultSet.getString("salt")); 
		        adminBean.setCreateDate(resultSet.getString("create_date")); 
		      }
		    } catch (Exception e) { 
		        e.printStackTrace(); 
		      } finally { 
		        DBUtil.close(resultSet, statement, connection); 
		      } 
		      return adminBean; 
		    } 
	  /** 
	   *  修改管理员 
	   *   
	   **/
	  public void update(AdminBean adminBean) { 
		    // TODO Auto-generated method stub 
		    String  sql  =  "update  admin  set  username='"  +  adminBean.getUsername()  +  "',password='"  + 
		adminBean.getPassword() 
		        + "' where id='" + adminBean.getId() + "'"; 
		    Connection conn = DBUtil.getConn(); 
		    Statement state = null; 
		    try { 
		      state = conn.createStatement(); 
		      state.executeUpdate(sql); 
		    } catch (Exception e) { 
		        e.printStackTrace(); 
		    } finally { 
		      DBUtil.close(state, conn); 
		    } 
		  } 
	  /** 
	    *  通过 id 删除 
	    *   
	    * @param id 
	    */ 
	  public void delete(int id) { 
	    // TODO Auto-generated method stub 
	    String sql = "delete from admin where id = " + id; 
	    Connection conn = DBUtil.getConn(); 
	    Statement state = null; 
	    try { 
	      state = conn.createStatement(); 
	      state.executeUpdate(sql); 
	    } catch (Exception e) { 
	      e.printStackTrace(); 
	    } finally { 
	      DBUtil.close(state, conn); 
	    } 
	  } 
	  /** 
	   *  获取数据表中数据总量 
	   * @return 
	   */ 
	  public int getCount() { 
		    ResultSet rs = null; 
		    Statement state = null; 
		    Connection conn = null; 
		    int size = 0; 
		    try { 
		      conn = DBUtil.getConn(); 
		      state = conn.createStatement(); 
		      rs = state.executeQuery("select count(*) count from admin"); 
		 
		      if (rs.next()) { 
		        size = rs.getInt("count"); 
		      } 
		    } catch (SQLException e) { 
		      e.printStackTrace(); 
		    } finally { 
		      DBUtil.close(rs, state, conn); 
		    } 
		    return size; 
		  } 
	  /** 
	   *  获取每一个分页的数据 
	   */
	  public List<AdminBean> getListByPage(int start, int size) { 
		    String sql = "select * from admin limit " + start + " , " + size; 
		    Connection connection = DBUtil.getConn(); 
		    Statement statement = null; 
		    ResultSet resultSet = null; 
		    List<AdminBean> adminBeans = new ArrayList<AdminBean>(); 
		    try { 
		      statement = connection.createStatement(); 
		      resultSet = statement.executeQuery(sql); 
		      AdminBean adminBean; 
		      while (resultSet.next()) { 
		        adminBean = new AdminBean(); 
		        adminBean.setId(resultSet.getInt("id")); 
		        adminBean.setUsername(resultSet.getString("username")); 
		        adminBean.setPassword(resultSet.getString("password")); 
		        adminBean.setSalt(resultSet.getString("salt")); 
		        adminBean.setCreateDate(resultSet.getString("create_date")); 
		        adminBeans.add(adminBean); 
		      } 
		 
		    } catch (Exception e) { 
		      e.printStackTrace(); 
		    } finally { 
		      DBUtil.close(resultSet, statement, connection); 
		    } 
		    return adminBeans; 
		  } 
}

