package com.aptech.business.ticketManage.workTicketLineTwo.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workTicketLineTwo.domain.WorkTicketLineTwoEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 工作票应用应用数据类
 *
 * @author zzq
 * @created 2017-10-18 11:50:39
 * @lastModified 
 * @history
 *
 */
@Repository("workTicketLineTwoDao")
public class WorkTicketLineTwoDaoImpl extends AncestorDao<WorkTicketLineTwoEntity> implements WorkTicketLineTwoDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.workTicketLineTwo";
	}
}
