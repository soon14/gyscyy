package com.aptech.business.annualProductionJob.service;

import java.util.Map;

import com.aptech.business.annualProductionJob.domain.AnnualProductionJobEntity;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 年度生产工作计划应用管理服务接口
 *
 * @author 
 * @created 2018-09-20 13:26:33
 * @lastModified 
 * @history
 *
 */
public interface AnnualProductionJobService  extends IBaseEntityOperation<AnnualProductionJobEntity> {

	void submit(String id, String selectUser);

	void approveTicketAgree(String taskId, String procInstId,
			Map<String, Object> variables,
			AnnualProductionJobEntity annualProductionJobEntity,
			SysUserEntity userEntity);

	void updateSpnrAgree(AnnualProductionJobEntity t, SysUserEntity userEntity);

	void approveTicketDisagree(String taskId, String procInstId,
			Map<String, Object> variables,
			AnnualProductionJobEntity annualProductionJobEntity,
			SysUserEntity userEntity);

	void updateSpnrDisagree(AnnualProductionJobEntity t,
			SysUserEntity userEntity);
	ResultObj saveInvalid(AnnualProductionJobEntity annualProductionJobEntity);
}