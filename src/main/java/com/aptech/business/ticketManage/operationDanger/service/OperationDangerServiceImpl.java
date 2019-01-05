package com.aptech.business.ticketManage.operationDanger.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.OperationStatusEnum;
import com.aptech.business.ticketManage.operationDanger.dao.OperationDangerDao;
import com.aptech.business.ticketManage.operationDanger.domain.OperationDangerEntity;
import com.aptech.business.ticketManage.operationDanger.exception.OperationDangerException;
import com.aptech.business.ticketManage.operationDanger.exception.OperationDangerExceptionType;
import com.aptech.business.ticketManage.operationTicket.domain.OperationTicketEntity;
import com.aptech.business.ticketManage.operationTicket.service.OperationTicketService;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 操作票危险因素情况表应用管理服务实现类
 *
 * @author 
 * @created 2017-07-12 17:27:40
 * @lastModified 
 * @history
 *
 */
@Service("operationDangerService")
@Transactional
public class OperationDangerServiceImpl extends AbstractBaseEntityOperation<OperationDangerEntity> implements OperationDangerService {
	
	@Autowired
	private OperationDangerDao operationDangerDao;
	@Autowired
	private OperationTicketService operationTicketService;
	
	@Override
	public IBaseEntityOperation<OperationDangerEntity> getDao() {
		return operationDangerDao;
	}
	public void addEntity(OperationDangerEntity t) {
		OperationTicketEntity tEntity = operationTicketService.findById(t
				.getOperationId());
		if (validateTicket(tEntity)) {
			getDao().addEntity(t);
		}
	}

	public void updateEntity(OperationDangerEntity t) {
		OperationDangerEntity itemEntity=operationDangerDao.findById(t.getId());
		OperationTicketEntity tEntity = operationTicketService.findById(t
				.getOperationId());
		if (validateTicket(tEntity)&&validate(itemEntity)) {
		getDao().updateEntity(t);
		}
	}

	public void deleteEntity(Serializable id) {
		OperationDangerEntity t=operationDangerDao.findById(id);
		OperationTicketEntity tEntity = operationTicketService.findById(t
				.getOperationId());
		if (validateTicket(tEntity)&&validate(t)) {
		getDao().deleteEntity(id);
		}
	}
	// 基本验证
		public boolean validateTicket(OperationTicketEntity t) {
			if(t==null)return  true;
			if (!(String.valueOf(t.getStatus()).equals(
					OperationStatusEnum.PENDING.getCode()) || String.valueOf(
					t.getStatus()).equals(OperationStatusEnum.REJECT.getCode()))) {
				throw new OperationDangerException(OperationDangerExceptionType.OPERATIONDANGER_CODE_STATUS);
			}
			return true;
		}
		// 基本验证
		public boolean validate(OperationDangerEntity t) {
			if (t == null) {
				throw new OperationDangerException(OperationDangerExceptionType.OPERATIONDANGER_CODE_NULL);
			}
			return true;
		}
}