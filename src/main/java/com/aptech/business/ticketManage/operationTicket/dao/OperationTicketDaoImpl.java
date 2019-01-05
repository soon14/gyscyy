package com.aptech.business.ticketManage.operationTicket.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.operationTicket.domain.OperationTicketEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 操作票应用数据类
 *
 * @author 
 * @created 2017-07-12 15:53:44
 * @lastModified 
 * @history
 *
 */
@Repository("operationTicketDao")
public class OperationTicketDaoImpl extends AncestorDao<OperationTicketEntity> implements OperationTicketDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.com.aptech.business.ticketManage";
	}
}
