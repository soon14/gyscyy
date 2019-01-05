package com.aptech.business.ticketManage.workLine.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workLine.domain.WorkLineEntity;
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
@Repository("workLineDao")
public class WorkLineDaoImpl extends AncestorDao<WorkLineEntity> implements WorkLineDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workLine";
	}
}
