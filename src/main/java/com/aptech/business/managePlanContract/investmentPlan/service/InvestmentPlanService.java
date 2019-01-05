package com.aptech.business.managePlanContract.investmentPlan.service;

import java.io.Serializable;
import java.util.Map;

import com.aptech.business.managePlanContract.investmentPlan.domain.InvestmentPlanEntity;
import com.aptech.business.overhaul.overhaulPlan.domain.OverhaulPlanEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 投资计划应用管理服务接口
 *
 * @author 
 * @created 2018-04-08 13:31:35
 * @lastModified 
 * @history
 *
 */
public interface InvestmentPlanService  extends IBaseEntityOperation<InvestmentPlanEntity> {
	
	/**
	 * 提交检修计划
	 * @param id
	 * @param params
	 * @return
	 */
	ResultObj submit(Serializable id, Map<String, Object> params);
	
	/**
	 * 审核
	 * @param t
	 * @param params
	 * @return
	 */
	ResultObj approve(InvestmentPlanEntity t,Map<String,Object> params);
	
}