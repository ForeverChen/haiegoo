package com.haiegoo.ucenter.enums;

public enum SysAdmEnum {
		
	    /** 应用支撑系统 */
		casass("casass", "应用支撑系统"),

	    /** 电商管理系统 */
		shopmng("shopmng", "电商管理后台"),

	    /** 企业管理系统 */
		erpcrm("erpcrm", "企业资源计划");

	    private String code;
	    private String name;

	    private SysAdmEnum(String code, String name) {
	        this.code = code;
	        this.name = name;
	    }

	    public String getCode() {
	        return code;
	    }

	    public String getName() {
	        return name;
	    }
	    
	    /**
	     * 通过枚举code获得枚举。
	     */
	    public static SysAdmEnum getByCode(String code) {
	        for (SysAdmEnum e : values()) {
	            if (e.getCode().equals(code)) {
	                return e;
	            }
	        }
	        return null;
	    }

	    /**
	     * 通过枚举name获得枚举
	     */
	    public static SysAdmEnum getByName(String name) {
	        for (SysAdmEnum e : values()) {
	            if (e.getCode().equals(name)) {
	                return e;
	            }
	        }
	        return null;
	    }
	}