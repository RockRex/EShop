package com.oracle.jsp.servlet.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.jsp.bean.AdminBean;
import com.oracle.jsp.bean.PagingBean;
import com.oracle.jsp.dao.AdminDao;
import com.oracle.jsp.util.Constants;
import com.oracle.jsp.util.MD5;
import com.oracle.jsp.util.StringUtil;

/**
 * admin管理
 * 
 * @author wjxing
 *
 */
public class AdminServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String method = req.getParameter("method");
		if ("login".equals(method)) {
			login(req, resp);//登录校验
		} else if ("addUser".equals(method)) { 
		      addUser(req, resp);// 添加管理员 
	    } else if ("list".equals(method)) { 
	        listUsers(req, resp); //查看管理员信息
	    } else if ("toUpdate".equals(method)) { 
	        toUpdate(req, resp); //修改管理员信息
	    } else if ("delete".equals(method)) { 
	        delete(req, resp); //删除管理员
	    }else if ("end".equals(method)) {
			end(req, resp);//退出登录
		}
	}

	/**
	 * 退出登录
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void end(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String status = req.getParameter("status");
		if (status != null && "1".equals(status)) {
			req.getSession().invalidate();
			resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
		}
	}

	/**
	 * 登录
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String username = req.getParameter("username");
		String password = MD5.GetMD5Code(req.getParameter("password"));

		AdminDao adminDao = new AdminDao();
		AdminBean adminBean = adminDao.checkLogin(username, password);
		if (adminBean != null) {
			// 登录成功
			req.getSession().setAttribute(Constants.SESSION_ADMIN_BEAN, adminBean);
			// req.getRequestDispatcher("main.jsp").forward(req, resp);
			resp.sendRedirect(req.getContextPath() + "/admin/main.jsp");
		} else {
			resp.sendRedirect(req.getContextPath() + "/admin/login.jsp?status=1");
		}
	}
	/** 
	  *  添加管理员 
	  *   
	  */
	  private  void  addUser(HttpServletRequest  req,  HttpServletResponse  resp)  throws  ServletException, 
	  IOException { 
	      req.setCharacterEncoding("utf-8"); 
	      String updateId = req.getParameter("updateId"); 
	      AdminDao adminDao = new AdminDao(); 
	      String username = req.getParameter("username"); 
	      String password = req.getParameter("password"); 
	      AdminBean adminBean = new AdminBean(); 
	       
	      adminBean.setUsername(username); 
	      String salt = StringUtil.getRandomString(10); 
	      String md5Pwd = MD5.GetMD5Code(MD5.GetMD5Code(password)+salt); 
	      adminBean.setSalt(salt); 
	      adminBean.setPassword(md5Pwd); 
	      SimpleDateFormat createDate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	      adminBean.setCreateDate(createDate1.format(new Date()));  
	      /**
	       *  updateId 空时为添加，不为空为修改 
	       *  */
	      if (!updateId.equals("")) { 
	    	  int id = StringUtil.StringToInt(updateId); 
	    	  adminBean.setId(id); 
	    	  boolean flag = true; 
	    	  if (!(username.equals(adminDao.getById(id).getUsername()))) { 
	    	    flag = adminDao.checkReg(username); 
	    	  } 
	    	  if (flag) { 
	    	    //  修改成功，写入数据库 
	    	    adminDao.update(adminBean); 
	    	    resp.sendRedirect("adminServlet?method=list&status=2"); 
	    	  } else { 
	    	    //  修改失败，跳回 
	    	    resp.sendRedirect("addUser.jsp?status=2"); 
	    	  } 
	    	} else { 
		      boolean flag = adminDao.checkReg(username); 
		      if (flag) { 
		        //  注册成功，写入数据库 
		        adminDao.save(adminBean); 
		        resp.sendRedirect("addUser.jsp?status=1"); 
		      } else { 
		        //  注册失败，跳回 
		        resp.sendRedirect("addUser.jsp?status=2"); 
		      } 
	    	}
	    } 
	  /** 
	   *  
	   * 查看管理员 
	   */
	  private void listUsers(HttpServletRequest req, HttpServletResponse resp) 
			  throws ServletException, IOException { 
			      // TODO Auto-generated method stub 
			      req.setCharacterEncoding("utf-8"); 
			   
			      String status = req.getParameter("status"); 
			      AdminDao adminDao = new AdminDao(); 
			      int currentPage = StringUtil.StringToInt(req.getParameter("currentPage"));//当前页
			      //System.out.println(currentPage);
			      int countSize = adminDao.getCount();//获取admin表中记录总条数
			      PagingBean pagingBean = new PagingBean(currentPage, countSize, Constants.PAGE_SIZE_1); 
			      List<AdminBean>adminBeans = adminDao.getListByPage(currentPage * Constants.PAGE_SIZE_1, Constants.PAGE_SIZE_1); 
			      pagingBean.setPrefixUrl(req.getContextPath() + 
			  "/admin/adminServlet?method=list"); 
			      pagingBean.setAnd(true); 
			      req.setAttribute(Constants.SESSION_ADMIN_BEANS, adminBeans); 
			      req.setAttribute("pagingBean", pagingBean); 
			      if (status != null) { 
			        req.getRequestDispatcher("listUsers.jsp?status=" + 
			  status).forward(req, resp); 
			      } else { 
			        req.getRequestDispatcher("listUsers.jsp").forward(req, resp); 
			      } 
	  }
	  /** 
	   *  
	   * 修改管理员信息 
	   */
	  private void toUpdate(HttpServletRequest req, HttpServletResponse resp) 
			  throws ServletException, IOException { 
			      // TODO Auto-generated method stub 
			      req.setCharacterEncoding("utf-8"); 
			      String updateId = req.getParameter("id"); 
			      int id = StringUtil.StringToInt(updateId); 
			      
			      if(id==1)
			      {
			    	  resp.sendRedirect(req.getContextPath() + "/admin/adminServlet?method=list&status=1"); 
			    	  return;
			      }
			      AdminDao adminDao = new AdminDao(); 
			      AdminBean adminBean = adminDao.getById(id); 
			      
			      req.setAttribute(Constants.SESSION_UPDATE_BEAN, adminBean); 
			      
			      req.getRequestDispatcher("addUser.jsp").forward(req, resp); 
			      
	  }
	  /**
	   * 删除管理员
	   * */
	  private  void  delete(HttpServletRequest  req,  HttpServletResponse  resp)  throws  ServletException, 
	  IOException { 
		  //System.out.println("删除");
	      // TODO Auto-generated method stub 
	      req.setCharacterEncoding("utf-8"); 
	      int id = StringUtil.StringToInt(req.getParameter("id")); 
	      if (id > 1) { 
	        AdminDao adminDao = new AdminDao(); 
	        adminDao.delete(id); 
	        resp.sendRedirect(req.getContextPath() + "/admin/adminServlet?method=list&status=3"); 
	      } else if (id == 1) { 
	        resp.sendRedirect(req.getContextPath() + "/admin/adminServlet?method=list&status=1"); 
	      } else { 
	        resp.sendRedirect(req.getContextPath() + "/admin/adminServlet?method=list&status=2"); 
	      } 
	    } 
}
