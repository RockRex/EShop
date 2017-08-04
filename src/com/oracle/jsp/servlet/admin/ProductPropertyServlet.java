package com.oracle.jsp.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.oracle.jsp.bean.ProductPropertyBean;
import com.oracle.jsp.bean.ProductTypeBean;
import com.oracle.jsp.dao.ProductPropertyDao;
import com.oracle.jsp.dao.ProductTypeDao;
import com.oracle.jsp.util.StringUtil;

@SuppressWarnings("serial")
public class ProductPropertyServlet extends HttpServlet
{

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		req.setCharacterEncoding("utf-8");
		// 区分操作类型
		String method = req.getParameter("method");
		if ("toAdd".equals(method))
		{
			toAdd(req, resp);
		} else if ("add".equals(method))
		{
			add(req, resp);
		} else if ("list".equals(method))
		{
			list(req, resp);
		} else if ("getType".equals(method))
		{
			getType(req, resp);
		} else if ("getProperty".equals(method))
		{
			getProperty(req, resp);
		} else if ("update".equals(method))
		{
			update(req, resp);
		} else if ("delete".equals(method))
		{
			delete(req, resp);
		}
	}

	/**
	 * 获取属性
	 * 
	 * @param resp
	 * @param req
	 * @throws IOException
	 */
	private void getProperty(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{
		int typeId = StringUtil.StringToInt(req.getParameter("id"));
		ProductPropertyDao productPropertyDao = new ProductPropertyDao();
		List<ProductPropertyBean> propertyList = productPropertyDao
				.getListByType(typeId);
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(propertyList));
		out.flush();
		out.close();
	}

	/**
	 * 显示商品属性查询
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void list(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		ProductTypeDao productTypeDao = new ProductTypeDao();
		String sta = req.getParameter("status");
		List<ProductTypeBean> typeList = productTypeDao.getTypeList(0);
		req.setAttribute("productTypeList", typeList);
		if (sta != null && "1".equals(sta))
		{
			req.getRequestDispatcher("list.jsp?status=1").forward(req, resp);
		} else
		{
			req.getRequestDispatcher("list.jsp").forward(req, resp);
		}
	}

	/**
	 * 携带分类数据跳转到分类属性添加界面
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void getType(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{
		int parentId = StringUtil.StringToInt(req.getParameter("id"));
		ProductTypeDao productTypeDao = new ProductTypeDao();
		List<ProductTypeBean> typeList = productTypeDao.getTypeList(parentId);
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(typeList));
		out.flush();
		out.close();
	}

	/**
	 * 查询出根分类集合返回，跳转到添加
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toAdd(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		ProductTypeDao productTypeDao = new ProductTypeDao();
		String sta = req.getParameter("status");
		List<ProductTypeBean> typeList = productTypeDao.getTypeList(0);
		req.setAttribute("productTypeList", typeList);
		if (sta != null && "1".equals(sta))
		{
			req.getRequestDispatcher("add.jsp?status=1").forward(req, resp);
		} else
		{
			req.getRequestDispatcher("add.jsp").forward(req, resp);
		}
	}

	/**
	 * 添加商品属性
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{
		int id = StringUtil.StringToInt(req.getParameter("id"));
		String name = req.getParameter("name");
		int sort = StringUtil.StringToInt(req.getParameter("sort"));
		String[] parIds = req.getParameterValues("productTypeId");
		int productTypeId = 0;
		for (String parId : parIds)
		{
			productTypeId = Math.max(productTypeId,
					StringUtil.StringToInt(parId));
		}
		ProductPropertyDao productPropertyDao = new ProductPropertyDao();
		boolean f;
		if (id == 0)
		{
			// 增加
			ProductPropertyBean productPropertyBean = new ProductPropertyBean(
					sort, productTypeId, name);
			f = productPropertyDao.add(productPropertyBean);
			if (f)
			{
				resp.sendRedirect("productPropertyServlet?method=toAdd&status=1");
			} else
			{
				System.out.println("添加属性写入数据库失败");
			}
		} else
		{
			// 修改
			ProductPropertyBean productPropertyBean = new ProductPropertyBean(
					id, sort, productTypeId, name);
			f = productPropertyDao.update(productPropertyBean);
			if (f)
			{
				resp.sendRedirect("productPropertyServlet?method=list&status=1");
			} else
			{
				System.out.println("修改属性写入数据库失败");
			}
		}
	}

	/**
	 * 跳转到修改商品类型界面
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void update(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		int id = StringUtil.StringToInt(req.getParameter("id"));
		ProductPropertyDao productPropertyDao = new ProductPropertyDao();
		ProductPropertyBean productPropertyBean = productPropertyDao
				.getPropertyById(id);
		req.setAttribute("productPropertyBean", productPropertyBean);
		ProductTypeDao productTypeDao = new ProductTypeDao();
		List<ProductTypeBean> productTypeList = productTypeDao.getTypeList(0);
		req.setAttribute("productTypeList", productTypeList);
		req.getRequestDispatcher("add.jsp").forward(req, resp);
	}

	/**
	 * 删除分类
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{
		int id = StringUtil.StringToInt(req.getParameter("id"));
		ProductPropertyDao productPropertyDao = new ProductPropertyDao();
		productPropertyDao.delete(id);
		resp.sendRedirect("productPropertyServlet?method=list");
	}

}