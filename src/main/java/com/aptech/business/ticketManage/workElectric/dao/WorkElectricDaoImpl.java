package com.aptech.business.ticketManage.workElectric.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workElectric.domain.WorkElectricEntity;
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
@Repository("workElectricDao")
public class WorkElectricDaoImpl extends AncestorDao<WorkElectricEntity> implements WorkElectricDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workElectric";
	}
}
