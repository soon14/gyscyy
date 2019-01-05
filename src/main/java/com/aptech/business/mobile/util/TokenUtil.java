package com.aptech.business.mobile.util;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.cache.Cache;
import com.aptech.framework.spring.SpringContextHolder;
import com.aptech.framework.util.StringUtil;

public class TokenUtil implements InitializingBean{

	private static Cache cache = SpringContextHolder.getBean(Cache.class);
	
	private static String CODE_CACHE_NAME = "token_cache";
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static boolean containsType(String type){
		String[] cacheKeys = {type};
		return cache.isCacheExist(CODE_CACHE_NAME, cacheKeys);
	}
	public static void clearAll(){
		cache.removeAll(CODE_CACHE_NAME);
	}
	public static void clearUserEntity(String token){
		cache.remove(CODE_CACHE_NAME,token);
	}
	/**
	 * 添加token和用户信息
	 * @Title: addDictionaries 
	 * @Description: TODO
	 * @param type
	 * @param codes
	 */
	public static void addUserEntity(String type, Map<String, SysUserEntity> users){
		if(!StringUtil.isEmpty(type)&&!users.isEmpty()){
			cache.put(CODE_CACHE_NAME, type, users);
		}
	}
	
	/**
	 * 根据Token获取用户信息
	 * @Title: getCodes 
	 * @Description: TODO
	 * @param type
	 * @return
	 */
	public static Map<String, SysUserEntity> getUserEntity(String token){
		return cache.get(CODE_CACHE_NAME, token);
	}
}
