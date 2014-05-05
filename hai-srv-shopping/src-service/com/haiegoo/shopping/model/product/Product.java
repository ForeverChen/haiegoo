package com.haiegoo.shopping.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Product implements Serializable {

	private static final long serialVersionUID = 8630442726493594192L;

	/**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.id
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.brand_id
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Integer brandId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.cate_id
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Integer cateId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.name
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private String name;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.sub_name
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private String subName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.image
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private String image;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.price
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private BigDecimal price;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.weight
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private BigDecimal weight;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.stock_number
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Integer stockNumber;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.min_buy_number
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Integer minBuyNumber;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.max_buy_number
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Integer maxBuyNumber;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.sell_count
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Long sellCount;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.unit
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private String unit;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.pc_desc
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private String pcDesc;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.mobile_desc
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private String mobileDesc;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.link_comment
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Long linkComment;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.seller_note
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private String sellerNote;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.seo_title
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private String seoTitle;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.seo_keywords
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private String seoKeywords;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.seo_description
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private String seoDescription;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.create_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Date createTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.update_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Date updateTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.delete_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Date deleteTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.publish_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Date publishTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.up_shelf_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Date upShelfTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.off_shelf_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Date offShelfTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.sale_state
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Byte saleState;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column product.state
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    private Byte state;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.id
     *
     * @return the value of product.id
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.id
     *
     * @param id the value for product.id
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.brand_id
     *
     * @return the value of product.brand_id
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Integer getBrandId() {
        return brandId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.brand_id
     *
     * @param brandId the value for product.brand_id
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.cate_id
     *
     * @return the value of product.cate_id
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Integer getCateId() {
        return cateId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.cate_id
     *
     * @param cateId the value for product.cate_id
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.name
     *
     * @return the value of product.name
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.name
     *
     * @param name the value for product.name
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.sub_name
     *
     * @return the value of product.sub_name
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public String getSubName() {
        return subName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.sub_name
     *
     * @param subName the value for product.sub_name
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setSubName(String subName) {
        this.subName = subName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.image
     *
     * @return the value of product.image
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public String getImage() {
        return image;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.image
     *
     * @param image the value for product.image
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.price
     *
     * @return the value of product.price
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.price
     *
     * @param price the value for product.price
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.weight
     *
     * @return the value of product.weight
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.weight
     *
     * @param weight the value for product.weight
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.stock_number
     *
     * @return the value of product.stock_number
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Integer getStockNumber() {
        return stockNumber;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.stock_number
     *
     * @param stockNumber the value for product.stock_number
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.min_buy_number
     *
     * @return the value of product.min_buy_number
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Integer getMinBuyNumber() {
        return minBuyNumber;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.min_buy_number
     *
     * @param minBuyNumber the value for product.min_buy_number
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setMinBuyNumber(Integer minBuyNumber) {
        this.minBuyNumber = minBuyNumber;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.max_buy_number
     *
     * @return the value of product.max_buy_number
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Integer getMaxBuyNumber() {
        return maxBuyNumber;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.max_buy_number
     *
     * @param maxBuyNumber the value for product.max_buy_number
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setMaxBuyNumber(Integer maxBuyNumber) {
        this.maxBuyNumber = maxBuyNumber;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.sell_count
     *
     * @return the value of product.sell_count
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Long getSellCount() {
        return sellCount;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.sell_count
     *
     * @param sellCount the value for product.sell_count
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setSellCount(Long sellCount) {
        this.sellCount = sellCount;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.unit
     *
     * @return the value of product.unit
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.unit
     *
     * @param unit the value for product.unit
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.pc_desc
     *
     * @return the value of product.pc_desc
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public String getPcDesc() {
        return pcDesc;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.pc_desc
     *
     * @param pcDesc the value for product.pc_desc
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setPcDesc(String pcDesc) {
        this.pcDesc = pcDesc;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.mobile_desc
     *
     * @return the value of product.mobile_desc
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public String getMobileDesc() {
        return mobileDesc;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.mobile_desc
     *
     * @param mobileDesc the value for product.mobile_desc
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setMobileDesc(String mobileDesc) {
        this.mobileDesc = mobileDesc;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.link_comment
     *
     * @return the value of product.link_comment
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Long getLinkComment() {
        return linkComment;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.link_comment
     *
     * @param linkComment the value for product.link_comment
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setLinkComment(Long linkComment) {
        this.linkComment = linkComment;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.seller_note
     *
     * @return the value of product.seller_note
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public String getSellerNote() {
        return sellerNote;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.seller_note
     *
     * @param sellerNote the value for product.seller_note
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setSellerNote(String sellerNote) {
        this.sellerNote = sellerNote;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.seo_title
     *
     * @return the value of product.seo_title
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public String getSeoTitle() {
        return seoTitle;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.seo_title
     *
     * @param seoTitle the value for product.seo_title
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.seo_keywords
     *
     * @return the value of product.seo_keywords
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public String getSeoKeywords() {
        return seoKeywords;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.seo_keywords
     *
     * @param seoKeywords the value for product.seo_keywords
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.seo_description
     *
     * @return the value of product.seo_description
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public String getSeoDescription() {
        return seoDescription;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.seo_description
     *
     * @param seoDescription the value for product.seo_description
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.create_time
     *
     * @return the value of product.create_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.create_time
     *
     * @param createTime the value for product.create_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.update_time
     *
     * @return the value of product.update_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.update_time
     *
     * @param updateTime the value for product.update_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.delete_time
     *
     * @return the value of product.delete_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.delete_time
     *
     * @param deleteTime the value for product.delete_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.publish_time
     *
     * @return the value of product.publish_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.publish_time
     *
     * @param publishTime the value for product.publish_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.up_shelf_time
     *
     * @return the value of product.up_shelf_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Date getUpShelfTime() {
        return upShelfTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.up_shelf_time
     *
     * @param upShelfTime the value for product.up_shelf_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setUpShelfTime(Date upShelfTime) {
        this.upShelfTime = upShelfTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.off_shelf_time
     *
     * @return the value of product.off_shelf_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Date getOffShelfTime() {
        return offShelfTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.off_shelf_time
     *
     * @param offShelfTime the value for product.off_shelf_time
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setOffShelfTime(Date offShelfTime) {
        this.offShelfTime = offShelfTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.sale_state
     *
     * @return the value of product.sale_state
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Byte getSaleState() {
        return saleState;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.sale_state
     *
     * @param saleState the value for product.sale_state
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setSaleState(Byte saleState) {
        this.saleState = saleState;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column product.state
     *
     * @return the value of product.state
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public Byte getState() {
        return state;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column product.state
     *
     * @param state the value for product.state
     *
     * @ibatorgenerated Tue Apr 22 17:48:07 GMT+08:00 2014
     */
    public void setState(Byte state) {
        this.state = state;
    }
}