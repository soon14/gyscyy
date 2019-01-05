package com.aptech.business.ticketManage.interventionTicket.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.interventionTicket.domain.InterventionTicketEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 工作票应用应用数据类
 *
 * @author 
 * @created 2017-06-29 18:50:39
 * @lastModified 
 * @history
 *
 */
@Repository("interventionTickeDao")
public class InterventionTicketImpl extends AncestorDao<InterventionTicketEntity> implements InterventionTicketDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.interventionTicket";
	}
}
