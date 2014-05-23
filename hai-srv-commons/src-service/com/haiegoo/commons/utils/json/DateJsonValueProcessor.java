package com.haiegoo.commons.utils.json;

import java.sql.Timestamp;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import com.haiegoo.commons.utils.DateUtils;

/**
 * 
 * Json日期转换处理器
 * @author shawn.du
 *
 */
public class DateJsonValueProcessor implements JsonValueProcessor {
	
	/**
	 * 定义 1000-01-01 00:00:00 为空时间，当JSON字符串中碰到空时间时，刚转化为空串。
	 */
	private final static String NULL_DATE = "1000-01-01 00:00:00";
	
	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value, jsonConfig);  
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return process(value, jsonConfig);  
	}
	
	private Object process( Object value, JsonConfig jsonConfig ) {
		if(value==null)
			return "";
		
		String str = "";
        if (value instanceof Date) {  
            str = DateUtils.format((Date) value);  
        } else if (value instanceof Timestamp) {  
            str = DateUtils.format((Timestamp)value); 
        }
        
        if(str.equals(NULL_DATE))
        	return "";
        else
        	return str;
    }  

}
