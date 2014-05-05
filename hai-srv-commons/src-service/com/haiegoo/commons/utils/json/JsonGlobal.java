package com.haiegoo.commons.utils.json;

import net.sf.json.JsonConfig;


public class JsonGlobal {

	public static final JsonConfig config = new JsonConfig();
	
	static{
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor());
		config.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor());
	}
}
