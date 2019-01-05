package com.aptech.business.defectManage.solve.service;

import java.util.Map;

import com.aptech.business.defectManage.solve.domain.SolveEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

public interface SolveService  extends IBaseEntityOperation<SolveEntity> {
	/**
	 * 审批
	 * 
	 * @param t
	 */
	ResultObj approve(SolveEntity t ,Map<String, Object> params);
	/**
	 * 审批:流程(无信息)
	 * 
	 * @param t
	 */
	ResultObj approveStatus(SolveEntity t , Map<String, Object> params);
}