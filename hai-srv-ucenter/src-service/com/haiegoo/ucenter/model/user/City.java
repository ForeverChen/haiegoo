package com.haiegoo.ucenter.model.user;

import java.io.Serializable;

public class City implements Serializable {

	private static final long serialVersionUID = 2575202477549417145L;

	/**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column city.id
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column city.pid
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    private Integer pid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column city.code
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    private String code;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column city.name
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    private String name;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column city.pinyin
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    private String pinyin;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column city.remark
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    private String remark;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column city.id
     *
     * @return the value of city.id
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column city.id
     *
     * @param id the value for city.id
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column city.pid
     *
     * @return the value of city.pid
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column city.pid
     *
     * @param pid the value for city.pid
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column city.code
     *
     * @return the value of city.code
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column city.code
     *
     * @param code the value for city.code
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column city.name
     *
     * @return the value of city.name
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column city.name
     *
     * @param name the value for city.name
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column city.pinyin
     *
     * @return the value of city.pinyin
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column city.pinyin
     *
     * @param pinyin the value for city.pinyin
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column city.remark
     *
     * @return the value of city.remark
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column city.remark
     *
     * @param remark the value for city.remark
     *
     * @ibatorgenerated Wed Oct 16 16:09:13 CST 2013
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}