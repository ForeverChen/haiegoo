package com.haiegoo.commons.enums;

/**
 * 静态枚举： 数据状态
 * @author pinian.lpn
 */
public enum State {

	/** 启用 */
	ENABLE("ENABLE", 1, "启用"),

	/** 禁用 */
	DISABLE("DISABLE", 2, "禁用"),

	/** 删除 */
	DELETED("DELETED", 3, "删除");
	
	
	
	// ------------------------------------- 枚举通用模版 ------------------------------------- //

	/**
	 * 枚举构造函数
	 * @param key 枚举键,英文
	 * @param code 枚举编码,数字
	 * @param text 枚举名称,一般为中文，用于显示
	 */
	private State(String key, int code, String text) {
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
	public static State getByKey(String key) {
		for (State e : values()) {
			if (e.getKey().equals(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 通过枚举code获取枚举
	 */
	public static State getByCode(int code) {
		for (State e : values()) {
			if (e.getCode()==code) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 通过枚举text获取枚举
	 */
	public static State getByText(String text) {
		for (State e : values()) {
			if (e.getText().equals(text)) {
				return e;
			}
		}
		return null;
	}

	// ------------------------------------- 枚举通用模版 ------------------------------------- //
	
}