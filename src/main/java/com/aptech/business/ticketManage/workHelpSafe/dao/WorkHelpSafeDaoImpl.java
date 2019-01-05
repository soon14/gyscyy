package com.aptech.business.ticketManage.workHelpSafe.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workHelpSafe.domain.WorkHelpSafeEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 工作票应用应用数据类
 *
 * @author 
 * @created 2017-10-26 11:50:39
 * @lastModified 
 * @history
 *
 */
@Repository("workHelpSafeDao")
public class WorkHelpSafeDaoImpl extends AncestorDao<WorkHelpSafeEntity> implements WorkHelpSafeDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.workHelpSafe";
	}
}
