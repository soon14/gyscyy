package com.aptech.business.ticketManage.operationItem.service;

import com.aptech.business.ticketManage.operationItem.domain.OperationItemEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 操作票项目表应用管理服务接口
 *
 * @author 
 * @created 2017-07-12 17:27:36
 * @lastModified 
 * @history
 *
 */
public interface OperationItemService  extends IBaseEntityOperation<OperationItemEntity> {
	
	void update(OperationItemEntity t);
}