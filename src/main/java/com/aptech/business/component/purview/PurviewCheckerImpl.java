package com.aptech.business.component.purview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.aptech.common.system.function.domain.SysFunctionEntity;
import com.aptech.common.system.function.service.SysFunctionService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.purview.PurviewChecker;
import com.aptech.framework.spring.SpringContextHolder;

/**
 * 
 * @author zhangjx
 * 
 */
@Component("purviewCheckerImpl")
public class PurviewCheckerImpl implements PurviewChecker, InitializingBean{

	private Map<String, String> functionMap = new HashMap<String, String>();
	
	public boolean check(String code) {
		SysUserEntity user = RequestContext.get().getUser();
		String functionId = functionMap.get(code);
		return user.isFunctionCodeExist(functionId);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		SysFunctionService sysFunctionService = SpringContextHolder.getBean("sysFunctionService");
		List<SysFunctionEntity> sysFunctionEntities = sysFunctionService.findAll();
		for(SysFunctionEntity sysFunctionEntity: sysFunctionEntities){
			functionMap.put(sysFunctionEntity.getCode(), sysFunctionEntity.getId().toString());
		}
	}


}
