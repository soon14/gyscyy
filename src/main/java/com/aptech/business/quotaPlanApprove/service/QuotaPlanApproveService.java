package com.aptech.business.quotaPlanApprove.service;

import java.util.Map;

import com.aptech.business.managePlanContract.annualProdutionPlan.domain.AnnualProdutionPlanEntity;
import com.aptech.business.quotaPlanApprove.domain.QuotaPlanApproveEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 指标计划流程表应用管理服务接口
 *
 * @author 
 * @created 2018-09-21 10:10:36
 * @lastModified 
 * @history
 *
 */
public interface QuotaPlanApproveService  extends IBaseEntityOperation<QuotaPlanApproveEntity> {

	void submit(String id, String selectUser);

	void approveTicketAgree(String taskId, String procInstId,
			Map<String, Object> variables,
			QuotaPlanApproveEntity quotaPlanApproveEntity,
			SysUserEntity userEntity);

	void updateSpnrAgree(QuotaPlanApproveEntity t, SysUserEntity userEntity);

	void approveTicketDisagree(String taskId, String procInstId,
			Map<String, Object> variables,
			QuotaPlanApproveEntity quotaPlanApproveEntity,
			SysUserEntity userEntity);

	void updateSpnrDisagree(QuotaPlanApproveEntity t, SysUserEntity userEntity);

	
}