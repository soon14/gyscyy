package com.aptech.business.companyTrends.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.aptech.business.companyTrends.domain.CompanyTrendsEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 公司动态应用管理服务接口
 *
 * @author 
 * @created 2018-04-03 11:20:52
 * @lastModified 
 * @history
 *
 */
public interface CompanyTrendsService  extends IBaseEntityOperation<CompanyTrendsEntity> {

	ResultObj deleteBulk(List<Integer> ids);

	ResultObj submit(Serializable id, Map<String, Object> params);

	ResultObj approve(CompanyTrendsEntity companyTrendsEntity,
			Map<String, Object> params);
	
}