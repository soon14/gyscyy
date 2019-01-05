package com.aptech.business.safeManage.targetManage.service;

import com.aptech.business.safeManage.safeSending.domain.SafeSendingEntity;
import com.aptech.business.safeManage.targetManage.domain.TargetManageEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 目标管理应用管理服务接口
 *
 * @author 
 * @created 2018-03-23 14:32:07
 * @lastModified 
 * @history
 *
 */
public interface TargetManageService  extends IBaseEntityOperation<TargetManageEntity> {
	ResultObj addPro(TargetManageEntity t);
}