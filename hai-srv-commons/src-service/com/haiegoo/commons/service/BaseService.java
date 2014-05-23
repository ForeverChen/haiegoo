package com.haiegoo.commons.service;

import java.io.Serializable;
import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ApplicationObjectSupport;

import com.haiegoo.commons.utils.ConvertUtils;

/**
 * service基类，主要编写service公共方法
 * @author Linpn
 */
public abstract class BaseService extends ApplicationObjectSupport implements Serializable {

	private static final long serialVersionUID = -2489358585140025260L;

	/**
	 * log4j对象
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	//-------------------------------------- service 公共方法 -------------------------------------------//


	/**
	 * 获取Memcached持久层(ibatis)缓存
	 * @return 返回MemcachedClient对象
	 */
	public MemcachedClient getMemcachedClient() {
		return this.getApplicationContext().getBean("memcachedClient", MemcachedClient.class);
	}
	


	/**
	 * 将排序的map参数转成sql中order by所需要的格式。
	 * 还会将domain对象中的字段转成数据库字段。 如： paramsName 转成 params_name
	 * @param sorters service方法中的排序参数
	 * @return 返回sql语句中order by所需要的格式
	 */
	public String getDbSort(Map<String, String> sorters){
		if(sorters==null)
			return "";
		
		StringBuilder sbSort = new StringBuilder();	
		for(Map.Entry<String, String> entry : sorters.entrySet()){
			if(sbSort.length()>0)
				sbSort.append(",");
			
			String sort = ConvertUtils.toDatabaseField(entry.getKey());
			String dir = entry.getValue();
			
			if(!StringUtils.isBlank(sort)){
				sbSort.append(sort);			
				if(!StringUtils.isBlank(dir)){	
					sbSort.append(" ");
					sbSort.append(entry.getValue());
				}
			}else{
				return "";
			}
		}
		
		return sbSort.toString();
	}
	
	
	/**
	 * 将排序的map参数转成sql中order by所需要的格式
	 * @param sorters service方法中的排序参数
	 * @return 返回sql语句中order by所需要的格式
	 */
	public String getDbSortNoConvert(Map<String, String> sorters){
		if(sorters==null)
			return "";
		
		StringBuilder sbSort = new StringBuilder();	
		for(Map.Entry<String, String> entry : sorters.entrySet()){
			if(sbSort.length()>0)
				sbSort.append(",");
			
			String sort = entry.getKey();
			String dir = entry.getValue();
			
			if(!StringUtils.isBlank(sort)){
				sbSort.append(sort);			
				if(!StringUtils.isBlank(dir)){	
					sbSort.append(" ");
					sbSort.append(entry.getValue());
				}
			}else{
				return "";
			}
		}	
		return sbSort.toString();
	}
	
}
