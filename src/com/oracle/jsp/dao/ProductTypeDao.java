package com.oracle.jsp.dao; 
 
import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.ArrayList; 
import java.util.List; 
 
import com.oracle.jsp.bean.ProductTypeBean; 
import com.oracle.jsp.util.DBUtil;

/** 
 * 商品分类DAO 
 * @author ly
 * 
 */ 

public class ProductTypeDao { 
	 
	  /** 
	   * 通过父分类id获取子分类列表 
	   * ProductTypeBean 只封装id,name, 
	   * @return 
	   */ 
	  public List<ProductTypeBean>getTypeList(int parentId) { 
		    String sql = "select * from product_type where parent_id='"+parentId+"'"; 
		    	    List<ProductTypeBean>list = new ArrayList<ProductTypeBean>(); 
		    	    Connection conn = DBUtil.getConn(); 
		    	    Statement state = null; 
		    	    ResultSet rs = null; 
		    	    try { 
		    	      state = conn.createStatement(); 
		    	      rs = state.executeQuery(sql); 
		    	      ProductTypeBean productTypeBean = null; 
		    	      while (rs.next()) { 
		    	        int id = rs.getInt("id"); 
		    	        String name = rs.getString("name"); 
		    	        int sort = rs.getInt("sort"); 
		    	        String intro = rs.getString("intro"); 
		    	        String createDate = rs.getString("create_date"); 
		    	        productTypeBean = new ProductTypeBean(id, 
		    	name,sort,intro,createDate); 
		    	        list.add(productTypeBean); 
		    	      } 
		    	    } catch (SQLException e) { 
		    	      e.printStackTrace(); 
		    	    } finally { 
		    	      DBUtil.close(rs, state, conn); 
		    	    } 
		    	    return list; 
		    	  } 
	  /** 
	   * 通过父分类id获取子分类列表 
	   * ProductTypeBean 只封装id,name, 
	   * @return 
	   */ 
	  public List<ProductTypeBean>getTypeBeans(int parentId) { 
	    String sql = "select * from product_type where parent_id='"+parentId+"'"; 
	    List<ProductTypeBean>typeBeans = new ArrayList<ProductTypeBean>(); 
	    Connection conn = DBUtil.getConn(); 
	    Statement state = null; 
	    ResultSet rs = null; 
	    try { 
	      state = conn.createStatement(); 
	      rs = state.executeQuery(sql); 
	      ProductTypeBean productTypeBean = null; 
	      while (rs.next()) { 
	        int id = rs.getInt("id"); 
	        String name = rs.getString("name"); 
	        productTypeBean = new ProductTypeBean(id, name); 
	        typeBeans.add(productTypeBean); 
	      } 
	    } catch (SQLException e) { 
	      e.printStackTrace(); 
	    } finally { 
	      DBUtil.close(rs, state, conn); 
	    } 
	    return typeBeans; 
	  } 
	  /** 
	   * 添加分类 
	   */ 
	  public boolean add(ProductTypeBean productTypeBean) { 
	    String sql = "insert into product_type(name,parent_id,sort,intro,create_date) values('" +productTypeBean.getName() 
	        +"','"+productTypeBean.getParentId()+"','" +productTypeBean.getSort()+"','" 
	        +productTypeBean.getIntro()+"',now())"; 
	    Connection conn = DBUtil.getConn(); 
	    Statement state = null; 
	    boolean f = false; 
	    int a = 0; 
	    try { 
	      state = conn.createStatement(); 
	      a = state.executeUpdate(sql); 
	      
	    } catch (Exception e) { 
	      e.printStackTrace(); 
	    } finally { 
	      DBUtil.close(state, conn); 
	    } 
	    if (a > 0) { 
	      f = true; 
	    } 
	    return f; 
	  } 
	   
	  /** 
	   * 通过id得到分类类型 
	   * @param id 
	   * @return 
   */ 
  public ProductTypeBean getTypeById(int typeId) { 
    String sql  =  "select * from  product_type where id='"  + typeId +  "'"; 
    Connection conn = DBUtil.getConn(); 
    Statement state = null; 
    ResultSet rs = null; 
    ProductTypeBean productTypeBean = null; 
    try { 
      state = conn.createStatement(); 
      rs = state.executeQuery(sql); 
      while (rs.next()) { 
        int id = rs.getInt("id"); 
        int sort = rs.getInt("sort"); 
        int parentId = rs.getInt("parent_id"); 
        String name = rs.getString("name"); 
        String intro = rs.getString("intro"); 
        String createDate = rs.getString("create_date"); 
        ProductTypeDao productTypeDao = new ProductTypeDao(); 
        ProductTypeBean parentBean = productTypeDao.getTypeById(parentId); 
        productTypeBean = new ProductTypeBean(id, sort, parentBean, name, intro, createDate); 
      } 
    } catch (SQLException e) { 
      e.printStackTrace(); 
    } finally { 
      DBUtil.close(rs, state, conn); 
    } 
    return productTypeBean; 
  } 
	   /** 
	   * 获取一个分类 
	   * 包含所有的父类 
	   * 包含一级子类 list 
	   * @param i 
	   * @param j 
	   * @param id 
	   * @return 
	   */ 
	 public ProductTypeBean getType(int id) { 
	    List<ProductTypeBean> list = getTypeList(id); 
	    ProductTypeBean productTypeBean; 
	    if(id==0){ 
	      productTypeBean = new ProductTypeBean(); 
	      productTypeBean.setId(0); 
	    }else{ 
	      productTypeBean = getTypeById(id); 
	    } 
	    productTypeBean.setChildBeans(list); 
	    return productTypeBean; 
	  } 
	 
	 
	  /** 
	   * 更新分类数据 
	   *  
	   * @param productTypeBean 
	   * @return 
	   */ 
	  public boolean update(ProductTypeBean productTypeBean) { 
		    String sql = "update product_type set name='" + productTypeBean.getName() + "', sort='" + productTypeBean.getSort() 
		        + "', intro='" + productTypeBean.getIntro() + "' where id='" + productTypeBean.getId() + "'"; 
		    Connection conn = DBUtil.getConn(); 
		    Statement state = null; 
		    boolean f = false; 
		    int a = 0; 
		    try { 
		      state = conn.createStatement(); 
		      a = state.executeUpdate(sql); 
		 
		    } catch (Exception e) { 
		      e.printStackTrace(); 
		    } finally { 
		      DBUtil.close(state, conn); 
		    } 
		    if (a > 0) { 
		      f = true; 
		    } 
		    return f; 
		  } 
	  /** 
	   * 删除商品类型 
	   *  
	   * @param id 
	   * @return 
	   */ 
	  public boolean delete(int id) { 
	    boolean f = true; 
	    //删除子分类 
	    List<ProductTypeBean>typeList = getTypeList(id); 
	    for(ProductTypeBean typeBean : typeList){ 
	      boolean f1 = delete(typeBean.getId()); 
	      if(!f1){ 
	        f = false; 
	      } 
	    } 
	    String sql = "delete from product_type where id='" + id + "'"; 
	    Connection conn = DBUtil.getConn(); 
	    Statement state = null; 
	    int a = 0; 
	    try { 
	      state = conn.createStatement(); 
	      a = state.executeUpdate(sql); 
	    } catch (Exception e) { 
	      e.printStackTrace(); 
	    } finally { 
	      DBUtil.close(state, conn); 
	    } 
	    if (a == 0) { 
	        f = false; 
	    } 
	    return f; 
	  } 

		 
} 
	 