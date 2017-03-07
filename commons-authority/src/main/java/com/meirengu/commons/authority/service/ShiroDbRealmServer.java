/**
 * @Copyright:Copyright (c) 2014  
 * @Company:UQSOFT 
 */
package com.meirengu.commons.authority.service;

import org.apache.shiro.authz.AuthorizationInfo;

/**
 * @Title:  
 * @Description:  
 * @Author:Administrator  
 * @Since:2015-4-17  
 * @Version:1.1.0  
 */
public interface ShiroDbRealmServer {
    AuthorizationInfo getRoleAndPerm(String loginName);

}
