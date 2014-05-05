package com.haiegoo.framework.web.wrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;



/**
 * Zookeeper Session 装饰类
 * @author Linpn
 */
@SuppressWarnings("deprecation")
public class RedisHttpSessionWrapper implements HttpSession {
	
	protected RedisTemplate<String, Object> cache;
	protected ServletContext servletContext;
	protected String jsessionid;
	protected boolean isNew = false;
	
	private BoundHashOperations<String, String, Object> redisHashOps;
	private String SESSION_ID;
	private String META_DATA;
	
	
	/**
	 * 创建session
	 * @param cache RedisTemplate 缓存对象
	 * @param session HttpSession 对象
	 * @return 返回 Redis HttpSession
	 */
	public static HttpSession create(RedisTemplate<String, Object> cache, HttpSession session){
		if(session!=null){
			RedisHttpSessionWrapper httpSession = new RedisHttpSessionWrapper(cache, session.getServletContext(), session.getId(), session.getMaxInactiveInterval(), true);
			return httpSession;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取session
	 * @param cache RedisTemplate 缓存对象
	 * @param servletContext ServletContext 对象 
	 * @param jsessionid SESSION的ID
	 * @return 返回 Redis HttpSession
	 */
	public static HttpSession get(RedisTemplate<String, Object> cache, ServletContext servletContext, String jsessionid){
		RedisHttpSessionWrapper httpSession = new RedisHttpSessionWrapper(cache, servletContext, jsessionid, 0, false);
		if (httpSession.cache.hasKey(httpSession.SESSION_ID)) {
			return httpSession;
		} else {
			return null;
		}
	}

	/**
	 * RedisHttpSessionWrapper 构造函数
	 * @param cache RedisTemplate 缓存对象
	 * @param servletContext ServletContext 对象 
	 * @param jsessionid SESSION的ID
	 * @param interval 超时时间, 单位：秒
	 * @param isNew 是否是新创建, 新创建的会设置一些值
	 */
	protected RedisHttpSessionWrapper(RedisTemplate<String, Object> cache, ServletContext servletContext, String jsessionid, int interval, boolean isNew) {
		if(cache==null)
			throw new NullPointerException("cache参数不能为空!");

		if(servletContext==null)
			throw new NullPointerException("servletContext参数不能为空!");

		if(jsessionid==null)
			throw new NullPointerException("jsessionid参数不能为空!");			
		
		try {
			this.cache = cache;
			this.servletContext = servletContext;
			this.jsessionid = jsessionid;
			this.isNew = isNew;

			// 设置Session Id和Session meta data的 path
			SESSION_ID = "SESSION/" + this.getId();
			META_DATA = "META_DATA";

			// redis针对hashmap类型的数据操作
			redisHashOps = cache.boundHashOps(SESSION_ID);

			// 设置session过期间隔
			if (interval != 0) {
				this.setMaxInactiveInterval(interval);
			}
			
			// 设置创建时间
			if (isNew) {
				this.setCreationTime(System.currentTimeMillis());
			}

			// 最后访问时间
			this.setLastAccessedTime(System.currentTimeMillis());
			cache.getExpire(SESSION_ID);

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	
	@Override
	public String getId() {
		return jsessionid;
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public boolean isNew() {
		return this.isNew;
	}	
	
	
	@Override
	public Object getAttribute(String name) {
		return redisHashOps.get(name);
	}
	
	@Override
	public void setAttribute(String name, Object value) {
		redisHashOps.put(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		redisHashOps.delete(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		Set<String> keys = redisHashOps.keys();
    	return Collections.enumeration(keys);	
	}
	

	@Override
	public int getMaxInactiveInterval() {
		Integer interval = 0;		
		Object value = redisHashOps.get(META_DATA + "/interval");
		if(value!=null)
			interval = Integer.valueOf(value.toString());		
		return interval;
	}
	
	@Override
	public void setMaxInactiveInterval(int interval) {
		redisHashOps.put(META_DATA + "/interval", interval);
	}

	@Override
	public void invalidate() {
		cache.delete(SESSION_ID);
	}
	
	
	@Override
	public long getCreationTime() {
		Long creationTime = 0L;		
		Object value = redisHashOps.get(META_DATA + "/creationTime");
		if(value!=null)
			creationTime = Long.valueOf(value.toString());		
		return creationTime;
	}

	@Override
	public long getLastAccessedTime() {
		Long lastAccessedTime = 0L;		
		Object value = redisHashOps.get(META_DATA + "/lastAccessedTime");
		if(value!=null)
			lastAccessedTime = Long.valueOf(value.toString());		
		return lastAccessedTime;
	}
	
	public void setCreationTime(long creationTime) {
		redisHashOps.put(META_DATA + "/creationTime", System.currentTimeMillis());
	}

	public void setLastAccessedTime(long lastAccessedTime) {
		redisHashOps.put(META_DATA + "/lastAccessedTime", System.currentTimeMillis());
		redisHashOps.expire(this.getMaxInactiveInterval(), TimeUnit.SECONDS);
	}
	
	
	
	@Deprecated
	public Object getValue(String name) {
		return this.getAttribute(name);
	}
	@Deprecated
	public String[] getValueNames() {
		List<String> list = Collections.list(this.getAttributeNames());
		return list.toArray(new String[0]);
	}
	@Deprecated
	public void putValue(String name, Object value) {
		this.setAttribute(name, value);
	}
	@Deprecated
	public void removeValue(String name) {
		this.removeAttribute(name);
	}
	@Deprecated
	public HttpSessionContext getSessionContext() {
		return null;
	}
	
}
