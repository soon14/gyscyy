package com.aptech.business.ticketManage.workEarth.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workEarth.domain.WorkEarthEntity;
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
@Repository("workEarthDao")
public class WorkEarthDaoImpl extends AncestorDao<WorkEarthEntity> implements WorkEarthDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workEarth";
	}
}
