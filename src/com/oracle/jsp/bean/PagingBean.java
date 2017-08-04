package com.oracle.jsp.bean; 
 
public class PagingBean { 
	//总数量 
	private int totalCount; 
	
	//总页数 
	private int pageCount; 
	//当前页 
	private int currentPage; 
	//一页多少条数据 
	private int countPerPage; 
	
	private String prefixUrl; 
	//true的时候是&否则是？ 
	private boolean isAnd; 

	public PagingBean(int currentPage, int totalCount, int countPerPage) { 
		this.countPerPage = countPerPage; 
		pageCount = (totalCount - 1) / countPerPage + 1; //计算总页数
	
		if (currentPage>= pageCount) 
		{ 
			currentPage = pageCount - 1; 
		} 
		if (currentPage< 0) 
		{ 
			currentPage = 0; 
		} 
	 
		this.totalCount = totalCount; 
		this.currentPage = currentPage;  
	} 

	public PagingBean() { 
		   
	} 

	public int getCurrentPage() { 
		return currentPage; 
	} 

	public void setCurrentPage(int currentPage) { 
		this.currentPage = currentPage; 
	} 

	  /** 
     * @return Returns the prefixUrl. 
     */ 
	public String getPrefixUrl() { 
			return prefixUrl; 
	    } 
	/** 
	 * @param prefixUrl The prefixUrl to set. 
	 */

	public int getTotalCount()
	{
		return totalCount;
	}

	public int getPageCount()
	{
		return pageCount;
	}

	public int getCountPerPage()
	{
		return countPerPage;
	}

	public boolean isAnd()
	{
		return isAnd;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}

	public void setCountPerPage(int countPerPage)
	{
		this.countPerPage = countPerPage;
	}

	public void setPrefixUrl(String prefixUrl)
	{
		this.prefixUrl = prefixUrl;
	}

	public void setAnd(boolean isAnd)
	{
		this.isAnd = isAnd;
	} 
	
	@Override 
	public String toString() { 
	  return"PagingBean [totalCount=" + totalCount + ", totalPageCount=" 
	      + pageCount + ", currentPage=" + currentPage 
	      + ", prefixUrl=" + prefixUrl + ", countPerPage=" + countPerPage 
	      + ", isAnd=" + isAnd + "]"; 
	} 
} 
