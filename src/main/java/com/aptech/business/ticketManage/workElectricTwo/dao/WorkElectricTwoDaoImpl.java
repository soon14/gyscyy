package com.aptech.business.ticketManage.workElectricTwo.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workElectricTwo.domain.WorkElectricTwoEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 电气工作票应用数据类
 *
 * @author 
 * @created 2017-06-29 17:12:46
 * @lastModified 
 * @history
 *
 */
@Repository("workElectricTwoDao")
public class WorkElectricTwoDaoImpl extends AncestorDao<WorkElectricTwoEntity> implements WorkElectricTwoDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.workElectricTwo";
	}
}
