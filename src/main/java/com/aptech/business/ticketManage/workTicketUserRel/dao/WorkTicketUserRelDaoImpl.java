package com.aptech.business.ticketManage.workTicketUserRel.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workElectric.domain.WorkElectricEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 电气工作票应用数据类
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Repository("workTicketUserRelDao")
public class WorkTicketUserRelDaoImpl extends AncestorDao<WorkTicketUserRelEntity> implements WorkTicketUserRelDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workTicketUserRel";
	}
}
