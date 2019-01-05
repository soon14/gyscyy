package com.aptech.business.defectManage.check.service;

import java.util.Map;

import com.aptech.business.defectManage.check.domain.CheckEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

public interface CheckService  extends IBaseEntityOperation<CheckEntity> {
	/**
	 * 审批:流程
	 * 
	 * @param t
	 */
	ResultObj approve(CheckEntity t, Map<String, Object> params);
	ResultObj solve(CheckEntity t);
}