package com.aptech.business.ticketManage.operationItem.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.OperationStatusEnum;
import com.aptech.business.ticketManage.operationItem.dao.OperationItemDao;
import com.aptech.business.ticketManage.operationItem.domain.OperationItemEntity;
import com.aptech.business.ticketManage.operationItem.exception.OperationItemException;
import com.aptech.business.ticketManage.operationItem.exception.OperationItemExceptionType;
import com.aptech.business.ticketManage.operationTicket.domain.OperationTicketEntity;
import com.aptech.business.ticketManage.operationTicket.service.OperationTicketService;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 操作票项目表应用管理服务实现类
 * 
 * @author
 * @created 2017-07-12 17:27:36
 * @lastModified
 * @history
 * 
 */
@Service("operationItemService")
@Transactional
public class OperationItemServiceImpl extends
		AbstractBaseEntityOperation<OperationItemEntity> implements
		OperationItemService {

	@Autowired
	private OperationItemDao operationItemDao;
	@Autowired
	private OperationTicketService operationTicketService;

	@Override
	public IBaseEntityOperation<OperationItemEntity> getDao() {
		return operationItemDao;
	}

	public void addEntity(OperationItemEntity t) {
		OperationTicketEntity tEntity = operationTicketService.findById(t
				.getOperationId());
		if (validateTicket(tEntity)) {
			getDao().addEntity(t);
		}
	}

	public void updateEntity(OperationItemEntity t) {
		OperationItemEntity itemEntity = operationItemDao.findById(t.getId());
		OperationTicketEntity tEntity = operationTicketService.findById(t
				.getOperationId());
		if (validateTicket(tEntity) && validate(itemEntity)) {
			getDao().updateEntity(t);
		}
	}
	public void update(OperationItemEntity t) {
			getDao().updateEntity(t);
	}
	public void deleteEntity(Serializable id) {
		OperationItemEntity t = operationItemDao.findById(id);
		OperationTicketEntity tEntity = operationTicketService.findById(t
				.getOperationId());
		if (validateTicket(tEntity) && validate(t)) {
			getDao().deleteEntity(id);
		}
	}

	// 基本验证(主表)
	public boolean validateTicket(OperationTicketEntity t) {
		if (t == null)
			return true;
		if (!(String.valueOf(t.getStatus()).equals(
				OperationStatusEnum.PENDING.getCode())
				|| String.valueOf(t.getStatus()).equals(
						OperationStatusEnum.REJECT.getCode())
				|| String.valueOf(t.getStatus()).equals(
						OperationStatusEnum.IMPLEMENT.getCode()) || String
				.valueOf(t.getStatus()).equals(
						OperationStatusEnum.FOREMANREJECT.getCode()))) {
			throw new OperationItemException(OperationItemExceptionType.OPERATIONITEM_CODE_STATUS);
		}
		return true;
	}

	// 基本验证
	public boolean validate(OperationItemEntity t) {
		if (t == null) {
			throw new OperationItemException(OperationItemExceptionType.OPERATIONITEM_CODE_NULL);
		}
		return true;
	}
}