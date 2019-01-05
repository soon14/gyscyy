package com.aptech.business.sysManagement.service;

import com.aptech.business.sysManagement.domain.SysManagementEntity;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 制度管理应用管理服务接口
 *
 * @author 
 * @created 2018-03-14 16:26:37
 * @lastModified 
 * @history
 *
 */
public interface SysManagementService  extends IBaseEntityOperation<SysManagementEntity> {
	ResultObj update(SysManagementEntity t, Long id);
}