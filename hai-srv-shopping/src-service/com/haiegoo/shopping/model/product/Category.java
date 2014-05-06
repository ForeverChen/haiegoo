package com.haiegoo.shopping.model.product;

import java.io.Serializable;

public class Category implements Serializable {
	
	private static final long serialVersionUID = -4676762723556937409L;

	/**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column category.id
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column category.pid
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    private Integer pid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column category.path
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    private String path;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column category.name
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    private String name;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column category.url
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    private String url;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column category.description
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column category.seo_title
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    private String seoTitle;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column category.seo_keywords
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    private String seoKeywords;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column category.seo_description
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    private String seoDescription;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column category.sort
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    private Integer sort;

	private Integer productCount;
	

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column category.id
     *
     * @return the value of category.id
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column category.id
     *
     * @param id the value for category.id
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column category.pid
     *
     * @return the value of category.pid
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column category.pid
     *
     * @param pid the value for category.pid
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column category.path
     *
     * @return the value of category.path
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column category.path
     *
     * @param path the value for category.path
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column category.name
     *
     * @return the value of category.name
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column category.name
     *
     * @param name the value for category.name
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column category.url
     *
     * @return the value of category.name
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column category.url
     *
     * @param name the value for category.name
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column category.description
     *
     * @return the value of category.description
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column category.description
     *
     * @param description the value for category.description
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column category.seo_title
     *
     * @return the value of category.seo_title
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public String getSeoTitle() {
        return seoTitle;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column category.seo_title
     *
     * @param seoTitle the value for category.seo_title
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column category.seo_keywords
     *
     * @return the value of category.seo_keywords
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public String getSeoKeywords() {
        return seoKeywords;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column category.seo_keywords
     *
     * @param seoKeywords the value for category.seo_keywords
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column category.seo_description
     *
     * @return the value of category.seo_description
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public String getSeoDescription() {
        return seoDescription;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column category.seo_description
     *
     * @param seoDescription the value for category.seo_description
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column category.sort
     *
     * @return the value of category.sort
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column category.sort
     *
     * @param sort the value for category.sort
     *
     * @ibatorgenerated Wed Nov 07 18:31:23 CST 2012
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

}