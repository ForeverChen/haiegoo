package com.haiegoo.shopping.enums;

/**
 * 静态枚举： 性别
 * @author pinian.lpn
 */
public enum SaleState {

	/** 待销售 */
	NO_SALE("NO_SALE", 0, "待销售"),

	/** 销售中 */
	SELLING("SELLING", 1, "销售中"),

	/** 已售完  */
	SOLD_OUT("SOLD_OUT", 2, "已售完"),

	/** 手动下架  */
	HAND_OFF_SHELF("HAND_OFF_SHELF", 3, "手动下架"),

	/** 系统下架  */
	SYS_OFF_SHELF("SYS_OFF_SHELF", 4, "系统下架");
	
	
	
	// ------------------------------------- 枚举通用模版 ------------------------------------- //

	/**
	 * 枚举构造函数
	 * @param key 枚举键,英文
	 * @param code 枚举编码,数字
	 * @param text 枚举名称,一般为中文，用于显示
	 */
	private SaleState(String key, int code, String text) {
		this.key = key;
		this.code = code;
		this.text = text;
	}


	/**
	 * 枚举键,英文
	 */
	private String key;
	/**
	 * 枚举编码,数字
	 */
	private int code;
	/**
	 * 枚举名称,一般为中文，用于显示
	 */
	private String text;
	

	/**
	 * 枚举键,英文
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 枚举编码,数字
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 枚举名称,一般为中文，用于显示
	 */
	public String getText() {
		return text;
	}

	/**
	 * 通过枚举key获取枚举
	 */
	public static SaleState getByKey(String key) {
		for (SaleState e : values()) {
			if (e.getKey().equals(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 通过枚举code获取枚举
	 */
	public static SaleState getByCode(int code) {
		for (SaleState e : values()) {
			if (e.getCode()==code) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 通过枚举text获取枚举
	 */
	public static SaleState getByText(String text) {
		for (SaleState e : values()) {
			if (e.getText().equals(text)) {
				return e;
			}
		}
		return null;
	}

	// ------------------------------------- 枚举通用模版 ------------------------------------- //
	
}