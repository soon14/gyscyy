package com.aptech.business.ticketManage.workTicketTest.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workTicketTest.domain.WorkTicketTestEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 可燃易爆实验应用数据类
 *
 * @author 
 * @created 2017-08-22 10:11:16
 * @lastModified 
 * @history
 *
 */
@Repository("workTicketTestDao")
public class WorkTicketTestDaoImpl extends AncestorDao<WorkTicketTestEntity> implements WorkTicketTestDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workTicketTest";
	}
}
