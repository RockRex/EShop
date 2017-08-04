package com.oracle.jsp.bean;

public class ProductPropertyBean
{
	private int id;
	private int sort;
	private int productTypeId;
	private String name;
	private String createDate;

	public ProductPropertyBean()
	{
	}

	public ProductPropertyBean(int sort, int productTypeId, String name)
	{
		this.setSort(sort);
		this.setProductTypeId(productTypeId);
		this.setName(name);
	}

	public ProductPropertyBean(int id, int sort, int productTypeId, String name)
	{
		this.setId(id);
		this.setSort(sort);
		this.setProductTypeId(productTypeId);
		this.setName(name);
	}

	public ProductPropertyBean(int id, int sort, int productTypeId,
			String name, String createDate)
	{
		this.setId(id);
		this.setSort(sort);
		this.setProductTypeId(productTypeId);
		this.setName(name);
		this.setCreateDate(createDate);
	}

	public ProductPropertyBean(int id, String name)
	{
		this.setId(id);
		this.setName(name);
	}

	public int getId()
	{
		return id;
	}

	public int getSort()
	{
		return sort;
	}

	public void setSort(int sort)
	{
		this.sort = sort;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}

	public int getProductTypeId()
	{
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId)
	{
		this.productTypeId = productTypeId;
	}
}