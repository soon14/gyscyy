package com.aptech.business.safeManage.safeReport.service;

import com.aptech.business.safeManage.safeReport.domain.SafeReportEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全月报应用管理服务接口
 *
 * @author 
 * @created 2018-03-28 11:18:00
 * @lastModified 
 * @history
 *
 */
public interface SafeReportService  extends IBaseEntityOperation<SafeReportEntity> {
	ResultObj update(SafeReportEntity t);
	ResultObj add(SafeReportEntity t);
}