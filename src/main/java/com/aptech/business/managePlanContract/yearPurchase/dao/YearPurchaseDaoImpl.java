package com.aptech.business.managePlanContract.yearPurchase.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.yearPurchase.domain.YearPurchaseEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 年度采购应用数据类
 *
 * @author 
 * @created 2018-04-12 13:45:42
 * @lastModified 
 * @history
 *
 */
@Repository("yearPurchaseDao")
public class YearPurchaseDaoImpl extends AncestorDao<YearPurchaseEntity> implements YearPurchaseDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.yearPurchase";
	}
}
