package com.aptech.business.safeManage.emergencyManage.service;

import com.aptech.business.safeManage.emergencyManage.domain.EmergencyManageEntity;
import com.aptech.business.safeManage.safeReport.domain.SafeReportEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 应急管理应用管理服务接口
 *
 * @author 
 * @created 2018-03-28 16:27:06
 * @lastModified 
 * @history
 *
 */
public interface EmergencyManageService  extends IBaseEntityOperation<EmergencyManageEntity> {
	ResultObj update(EmergencyManageEntity t);
}