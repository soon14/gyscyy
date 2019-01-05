package com.aptech.business.ticketManage.workFireTwo.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workElectric.domain.WorkElectricEntity;
import com.aptech.business.ticketManage.workFire.domain.WorkFireEntity;
import com.aptech.business.ticketManage.workFireTwo.domain.WorkFireTwoEntity;
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
@Repository("workFireTwoDao")
public class WorkFireTwoDaoImpl extends AncestorDao<WorkFireTwoEntity> implements WorkFireTwoDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workFireTwo";
	}
}
