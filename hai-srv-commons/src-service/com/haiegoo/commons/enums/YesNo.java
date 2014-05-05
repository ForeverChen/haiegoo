package com.haiegoo.commons.enums;

/**
 * 静态枚举： 是和否
 * @author pinian.lpn
 */
public enum YesNo {

	/** 是 */
	YES("YES", 1, "是"),

	/** 否 */
	NO("NO", 0, "否");
	
	
	
	// ------------------------------------- 枚举通用模版 ------------------------------------- //

	/**
	 * 枚举构造函数
	 * @param key 枚举键,英文
	 * @param code 枚举编码,数字
	 * @param text 枚举名称,一般为中文，用于显示
	 */
	private YesNo(String key, int code, String text) {
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
	public static YesNo getByKey(String key) {
		for (YesNo e : values()) {
			if (e.getKey().equals(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 通过枚举code获取枚举
	 */
	public static YesNo getByCode(int code) {
		for (YesNo e : values()) {
			if (e.getCode()==code) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 通过枚举text获取枚举
	 */
	public static YesNo getByText(String text) {
		for (YesNo e : values()) {
			if (e.getText().equals(text)) {
				return e;
			}
		}
		return null;
	}

	// ------------------------------------- 枚举通用模版 ------------------------------------- //
	
}