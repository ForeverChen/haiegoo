package com.haiegoo.framework.web.wrapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.RedisTemplate;


/**
 * Zookeeper request 装饰类
 * @author Administrator
 *
 */
public class RedisHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private RedisTemplate<String, Object> cache;
	
	public RedisHttpServletRequestWrapper(HttpServletRequest request, RedisTemplate<String, Object> cache) {
		super(request);
	    this.cache = cache;
	}
	
	
    /**
     * The default behavior of this method is to return getSession()
     * on the wrapped request object.
     */
    public HttpSession getSession() {
    	return this.getSession(true);
    }
    
	
    /**
     * The default behavior of this method is to return getSession(boolean create)
     * on the wrapped request object.
     * 当你设置了<Context sessionCookieDomain=".mydomain.com" sessionCookiePath="/"></Context>(tomcat7) 时，可以实现同系统集群和不同系统集群；
     * 如果不设置，则只能同系统集群。
     */
    public HttpSession getSession(boolean create) {
    	HttpSession session = null;
    	String jsessionid = this.getJsessionId();    	

		//按cookie中的jsessionid获取集群session
    	if(jsessionid!=null){
			session =  RedisHttpSessionWrapper.get(cache, this.getServletContext(), jsessionid);
    	}

		//如果获取不集群session，则创建集群session
    	if(session==null){
			session =  RedisHttpSessionWrapper.create(cache, super.getSession(create));
    	}
    	
		return session;
    }
	
    
    /**
     * 从Cookie或url中获取JSESSIONID
     * @return
     */
    private String getJsessionId(){
    	String jsessionid = null;
    	Cookie[] cookies = super.getCookies();
    	if(cookies!=null){
    		for(Cookie cookie : cookies){
    			if(cookie.getName().equals("JSESSIONID")){
    				jsessionid = cookie.getValue();
    				break;
    			}
    		}
    	}
    	return jsessionid;
    }

}
