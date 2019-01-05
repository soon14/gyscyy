package com.aptech.business.ticketManage.typicalTicket.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.typicalTicket.domain.TypicalTicketEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 典型票应用数据类
 *
 * @author 
 * @created 2017-07-20 15:55:55
 * @lastModified 
 * @history
 *
 */
@Repository("typicalTicketDao")
public class TypicalTicketDaoImpl extends AncestorDao<TypicalTicketEntity> implements TypicalTicketDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.defectManage.typicalTicket";
	}
}
