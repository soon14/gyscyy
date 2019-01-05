package com.aptech.business.ticketManage.workTicketEarth.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workTicketEarth.domain.WorkTicketEarthEntity;
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
@Repository("workTicketEarthDao")
public class WorkTicketEarthDaoImpl extends AncestorDao<WorkTicketEarthEntity> implements WorkTicketEarthDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workTicketEarth";
	}
}
