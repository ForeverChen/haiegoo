package com.haiegoo.shopping.enums;

/**
 * 静态枚举： 性别
 * @author pinian.lpn
 */
public enum InputType {

	/** 文本框 */
	TEXT_BOX("TEXT_BOX", 0, "文本框"),

	/** 可编辑下拉框 */
	COMBO_EDIT("COMBO_EDIT", 1, "可编辑下拉框"),

	/** 不可编辑下拉框  */
	COMBO_UN_EDIT("COMBO_UN_EDIT", 2, "不可编辑下拉框"),

	/** 单选框  */
	RADIO_BOX("RADIO_BOX", 3, "单选框"),

	/** 复选框  */
	CHECK_BOX("CHECK_BOX", 4, "复选框");
	
	
	
	// ------------------------------------- 枚举通用模版 ------------------------------------- //

	/**
	 * 枚举构造函数
	 * @param key 枚举键,英文
	 * @param code 枚举编码,数字
	 * @param text 枚举名称,一般为中文，用于显示
	 */
	private InputType(String key, int code, String text) {
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
	public static InputType getByKey(String key) {
		for (InputType e : values()) {
			if (e.getKey().equals(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 通过枚举code获取枚举
	 */
	public static InputType getByCode(int code) {
		for (InputType e : values()) {
			if (e.getCode()==code) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 通过枚举text获取枚举
	 */
	public static InputType getByText(String text) {
		for (InputType e : values()) {
			if (e.getText().equals(text)) {
				return e;
			}
		}
		return null;
	}

	// ------------------------------------- 枚举通用模版 ------------------------------------- //
	
}