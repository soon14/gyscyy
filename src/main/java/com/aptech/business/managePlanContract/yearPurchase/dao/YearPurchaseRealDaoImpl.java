package com.aptech.business.managePlanContract.yearPurchase.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.yearPurchase.domain.YearPurchaseRealEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 年度采购计划实际填报应用数据类
 *
 * @author 
 * @created 2018-09-04 13:38:00
 * @lastModified 
 * @history
 *
 */
@Repository("yearPurchaseRealDao")
public class YearPurchaseRealDaoImpl extends AncestorDao<YearPurchaseRealEntity> implements YearPurchaseRealDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.yearPurchaseReal";
	}
}
