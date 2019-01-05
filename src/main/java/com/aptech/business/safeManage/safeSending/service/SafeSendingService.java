package com.aptech.business.safeManage.safeSending.service;

import com.aptech.business.safeManage.safeReport.domain.SafeReportEntity;
import com.aptech.business.safeManage.safeSending.domain.SafeSendingEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全发文应用管理服务接口
 *
 * @author 
 * @created 2018-04-02 09:52:16
 * @lastModified 
 * @history
 *
 */
public interface SafeSendingService  extends IBaseEntityOperation<SafeSendingEntity> {
	ResultObj add(SafeSendingEntity safeSendingEntity);
	ResultObj update(SafeSendingEntity safeSendingEntity);
}