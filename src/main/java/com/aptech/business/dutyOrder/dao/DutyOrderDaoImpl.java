package com.aptech.business.dutyOrder.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.dutyOrder.domain.DutyOrderEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 值次应用数据类
 *
 * @author 
 * @created 2017-08-02 15:20:06
 * @lastModified 
 * @history
 *
 */
@Repository("dutyOrderDao")
public class DutyOrderDaoImpl extends AncestorDao<DutyOrderEntity> implements DutyOrderDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.dutyOrder";
	}
}
