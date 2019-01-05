package com.aptech.business.technical.eventNotification.service;

import java.util.List;

import com.aptech.business.technical.eventNotification.domain.EventNotificationEntity;
import com.aptech.business.technical.summary.domain.SummaryEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 事件通报应用管理服务接口
 *
 * @author 
 * @created 2018-07-30 11:42:47
 * @lastModified 
 * @history
 *
 */
public interface EventNotificationService  extends IBaseEntityOperation<EventNotificationEntity> {
	void submit(String id, String selectUser);
	void updateSpnrAgree(EventNotificationEntity eventNotificationEntity,
			SysUserEntity userEntity);
	void updateSpnrDisagree(EventNotificationEntity eventNotificationEntity,
			SysUserEntity userEntity);
	void updateSpnrDisagreeUp(EventNotificationEntity eventNotificationEntity,
			SysUserEntity userEntity);
	/**
	 * 流程结束时需要通知人登录名称的List
	 * @param dispatchType 发文类型
	 * @return
	 */
	public List<SysUserEntity> getPerson4TrainAndRepair(String taskId);
}