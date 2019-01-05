package com.aptech.business.ticketManage.workTicketFire.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicketFire.domain.WorkTicketFireEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 工作票应用应用数据类
 *
 * @author 
 * @created 2017-06-02 11:50:39
 * @lastModified 
 * @history
 *
 */
@Repository("workTicketFireDao")
public class WorkTicketFireDaoImpl extends AncestorDao<WorkTicketFireEntity> implements WorkTicketFireDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workTicketFire";
	}
}
