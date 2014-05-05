package org.jasig.cas.authentication.handler.support;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.BadCredentialsAuthenticationException;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;


/**
 * 自定义用户名密码认证
 * @author Linpn
 */
public class UsernamePasswordAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

	@Resource
	private JdbcTemplate jdbcTemplate;
	private Md5PasswordEncoder md5 = new Md5PasswordEncoder();	

    public boolean authenticateUsernamePasswordInternal(final UsernamePasswordCredentials credentials) 
    		throws AuthenticationException {
		try{
			//获取用户
			Map<String, Object> user = null;
			List<Map<String, Object>> list = jdbcTemplate.queryForList(
				"select * from user where username=?", 
				new Object[]{
					credentials.getUsername()
				}
			);
			if(list!=null && list.size()>0){
				user = list.get(0);
			}
	    	
			//账号存在
	    	if(user!=null){
	            credentials.setUsername(user.get("username").toString());            
	    		if(user.get("state").equals(1)){
	    			if(user.get("password").equals(md5.encodePassword(credentials.getPassword(), credentials.getUsername())) ){
		                //成功
		                log.debug("User [" + credentials.getUsername() + "] was successfully authenticated.");
		                return true;
	    			}else{
	    				//密码错误
		                log.debug("User [" + credentials.getUsername() + "] failed authentication, the user or password is bad.");
		        		throw new BadCredentialsAuthenticationException("error.authentication.credentials.bad");
	    			}
	    		}else       
	    		if(user.get("state").equals(2)){         
	                //被禁用
	                log.debug("User [" + credentials.getUsername() + "] failed authentication, the user has been disabled.");
	        		throw new BadCredentialsAuthenticationException("error.authentication.credentials.disable");
	    		}else{
	    			//被删除
	    			log.debug("User [" + credentials.getUsername() + "] failed authentication, the user has been deleted.");
	        		throw new BadCredentialsAuthenticationException("error.authentication.credentials.delete");
	    		}
	    	}       	
	
	    	//账号不存在
	        log.debug("User [" + credentials.getUsername() + "] failed authentication, the user does not exist.");
			throw new BadCredentialsAuthenticationException("error.authentication.credentials.notexist");
			
		}catch(Exception e){
			log.error("User [" + credentials.getUsername() + "] failed authentication", e);
			if(e instanceof BadCredentialsAuthenticationException){
				throw (BadCredentialsAuthenticationException)e;
			}else{
				throw new BadCredentialsAuthenticationException("error.authentication.credentials.unsupported",e);
			}
		}
    }

}
