package com.haiegoo.ucenter.enums;

/**
 * 静态枚举： 性别
 * @author pinian.lpn
 */
public enum Gender {

	/** 男*/
	MALE("MALE", 1, "男"),

	/** 女 */
	FEMALE("FEMALE", 2, "女");
	
	
	
	// ------------------------------------- 枚举通用模版 ------------------------------------- //

	/**
	 * 枚举构造函数
	 * @param key 枚举键,英文
	 * @param code 枚举编码,数字
	 * @param text 枚举名称,一般为中文，用于显示
	 */
	private Gender(String key, int code, String text) {
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
	public static Gender getByKey(String key) {
		for (Gender e : values()) {
			if (e.getKey().equals(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 通过枚举code获取枚举
	 */
	public static Gender getByCode(int code) {
		for (Gender e : values()) {
			if (e.getCode()==code) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 通过枚举text获取枚举
	 */
	public static Gender getByText(String text) {
		for (Gender e : values()) {
			if (e.getText().equals(text)) {
				return e;
			}
		}
		return null;
	}

	// ------------------------------------- 枚举通用模版 ------------------------------------- //
	
}