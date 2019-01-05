package com.aptech.business.technical.testReport.service;

import com.aptech.business.technical.eventNotification.domain.EventNotificationEntity;
import com.aptech.business.technical.testReport.domain.TestReportEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 试验报告应用管理服务接口
 *
 * @author 
 * @created 2018-09-05 14:35:08
 * @lastModified 
 * @history
 *
 */
public interface TestReportService  extends IBaseEntityOperation<TestReportEntity> {
	void submit(String id, String selectUser);
	void updateSpnrAgree(TestReportEntity testReportEntity,
			SysUserEntity userEntity);
	void updateSpnrDisagree(TestReportEntity testReportEntity,
			SysUserEntity userEntity);
	void updateSpnrDisagreeUp(TestReportEntity testReportEntity,
			SysUserEntity userEntity);
}