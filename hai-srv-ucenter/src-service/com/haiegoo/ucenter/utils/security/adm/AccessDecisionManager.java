package com.haiegoo.ucenter.utils.security.adm;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import com.haiegoo.ucenter.model.admin.Admin;
import com.haiegoo.ucenter.model.admin.Role;

public class AccessDecisionManager implements org.springframework.security.access.AccessDecisionManager {

	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
		if (configAttributes == null) {
			return;
		}
		
		for(ConfigAttribute ca : configAttributes) {
			String needRole = ((SecurityConfig) ca).getAttribute();
			
			if(authentication.getPrincipal() instanceof Admin){			
				List<Role> roleList = ((Admin)authentication.getPrincipal()).getRoles();
				for(Role role : roleList){
					if (needRole.equals(role.getName())) {
						return;
					}
				}
			}
		}
		
		throw new AccessDeniedException("没有权限");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
}