package com.oracle.jsp.servlet.admin; 
 
import java.io.IOException; 
import java.io.PrintWriter; 
import java.util.List; 
 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
 
import com.alibaba.fastjson.JSON; 
import com.oracle.jsp.bean.ProductTypeBean; 
import com.oracle.jsp.dao.ProductTypeDao; 
import com.oracle.jsp.util.StringUtil; 
 
 
@SuppressWarnings("serial") 
public class ProductTypeServlet extends HttpServlet {
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
			      req.setCharacterEncoding("utf-8"); 
			      String method = req.getParameter("method"); 
			      if ("toAdd".equals(method)) { 
			        toAdd(req, resp); 
			      } else if ("add".equals(method)) { 
			        add(req, resp); 
			      }else if ("list".equals(method)) { 
			          list(req, resp); 
			      }else if ("update".equals(method)) { 
			          update(req, resp); 
			      }else if ("delete".equals(method)) { 
			    	  delete(req, resp); 
			      }else if("getType".equals(method)){ 
			        getType(req,resp); 
			      } 
			    } 
	  /** 
	   * 显示最高级分类 
	   * @param req 
	   * @param resp 
	   */ 
	  private void list(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException { 
	    int parentId = StringUtil.StringToInt(req.getParameter("id")); 
	    ProductTypeDao productTypeDao = new ProductTypeDao(); 
	    ProductTypeBean productTypeBean = productTypeDao.getType(parentId); 
	    req.setAttribute("productTypeBean", productTypeBean); 
	    req.getRequestDispatcher("list.jsp").forward(req, resp); 
	  } 
			    /** 
			     * 获取分类 
			     * @param req 
			     * @param resp 
			     * @throws IOException  
			     */ 
			  private void getType(HttpServletRequest req, HttpServletResponse 
			  resp) throws IOException { 
			      int parentId = StringUtil.StringToInt(req.getParameter("id")); 
			      ProductTypeDao productTypeDao = new ProductTypeDao(); 
			      List<ProductTypeBean>typeList = 
			      productTypeDao.getTypeList(parentId); 
			      resp.setCharacterEncoding("utf-8"); 
			      PrintWriter out = resp.getWriter(); 
			      out.print(JSON.toJSONString(typeList)); 
			      out.flush(); 
			      out.close(); 
			    } 
			   
			    /** 
			     * 携带分类数据跳转到分类添加界面 
			     *  
			     * @param req 
			     * @param resp 
			     * @throws IOException 
			     * @throws ServletException 
			     */ 
			    private void toAdd(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException { 
			      ProductTypeDao productTypeDao = new ProductTypeDao(); 
			      List<ProductTypeBean>typeList  = productTypeDao.getTypeBeans(0);
			      req.setAttribute("productTypeList", typeList); 
			      req.getRequestDispatcher("add.jsp").forward(req, resp); 
			    } 
			   
			    /** 
			     * 添加商品分类 
			     *  
			     * @param req 
			     * @param resp 
			     * @throws IOException 
			     */ 
			  private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException { 
			   
			      String name = req.getParameter("name"); 
			      int sort = StringUtil.StringToInt(req.getParameter("sort")); 
			      String intro = req.getParameter("desc"); 
			      int id = StringUtil.StringToInt(req.getParameter("id")); 
			      String[] parIds = req.getParameterValues("parentId"); 
			      int parentId = 0; 
			      for(String parId:parIds){ 
			        parentId = Math.max(parentId, 
			  StringUtil.StringToInt(parId)); 
			      } 
			      ProductTypeDao productTypeDao = new ProductTypeDao(); 
			      boolean f; 
			      if (id == 0) { 
			        // 添加新纪录 
			        ProductTypeBean productTypeBean = new ProductTypeBean(sort, 
			  parentId, name, intro); 
			        f = productTypeDao.add(productTypeBean); 
			      } else { 
			        // 修改记录 
			        ProductTypeBean productTypeBean = new ProductTypeBean(id, 
			  sort, parentId, name, intro); 
			        f = productTypeDao.update(productTypeBean); 
			      } 
			      if (f) { 
			     
			    resp.sendRedirect("productTypeServlet?method=toAdd&status=true"); 
			      } else { 
			     
			    resp.sendRedirect("productTypeServlet?method=toAdd&status=false"); 
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
			  private void update(HttpServletRequest  req,  HttpServletResponse  resp) 
			throws ServletException, IOException { 
			    int id = StringUtil.StringToInt(req.getParameter("id")); 
			    ProductTypeDao productTypeDao = new ProductTypeDao(); 
			    ProductTypeBean productTypeBean = productTypeDao.getTypeById(id); 
			    req.setAttribute("productTypeBean", productTypeBean); 
			    List<ProductTypeBean>typeList = productTypeDao.getTypeList(0); 
			    req.setAttribute("productTypeList", typeList); 
			    req.getRequestDispatcher("add.jsp").forward(req, resp); 
			  } 
			  
			  /** 
			   * 删除分类 
			   *  
			   * @param req 
			   * @param resp 
			   * @throws IOException 
			   */ 
			  private void delete(HttpServletRequest  req,  HttpServletResponse  resp) 
			throws IOException { 
			    int id = StringUtil.StringToInt(req.getParameter("id")); 
			    ProductTypeDao productTypeDao = new ProductTypeDao(); 
			    productTypeDao.delete(id); 
			    resp.sendRedirect("productTypeServlet?method=list"); 
			  } 
			 
} 
 