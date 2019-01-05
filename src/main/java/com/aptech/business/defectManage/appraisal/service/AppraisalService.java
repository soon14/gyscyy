package com.aptech.business.defectManage.appraisal.service;

import java.util.Map;

import com.aptech.business.defectManage.appraisal.domain.AppraisalEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

public interface AppraisalService  extends IBaseEntityOperation<AppraisalEntity> {
	/**
	 * 审批:流程
	 * 
	 * @param t
	 */
	ResultObj approve(AppraisalEntity t , Map<String, Object> params);
	/**
	 * 审批:流程(无信息)
	 * 
	 * @param t
	 */
	ResultObj approveStatus(AppraisalEntity t , Map<String, Object> params);
}