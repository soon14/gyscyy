package com.aptech.business.technical.eventAnalysis.service;

import com.aptech.business.technical.eventAnalysis.domain.EventAnalysisEntity;
import com.aptech.business.technical.eventNotification.domain.EventNotificationEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 事件分析应用管理服务接口
 *
 * @author 
 * @created 2018-09-07 17:36:53
 * @lastModified 
 * @history
 *
 */
public interface EventAnalysisService  extends IBaseEntityOperation<EventAnalysisEntity> {
	void submit(String id, String selectUser);
	void updateSpnrAgree(EventAnalysisEntity eventAnalysisEntity,
			SysUserEntity userEntity);
	void updateSpnrDisagree(EventAnalysisEntity eventAnalysisEntity,
			SysUserEntity userEntity);
	void updateSpnrDisagreeUp(EventAnalysisEntity eventAnalysisEntity,
			SysUserEntity userEntity);
}