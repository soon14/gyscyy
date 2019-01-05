package com.aptech.business.managePlanContract.purchaseType.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.purchaseType.domain.PurchaseTypeEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 采购类型应用数据类
 *
 * @author 
 * @created 2018-07-30 16:49:32
 * @lastModified 
 * @history
 *
 */
@Repository("purchaseTypeDao")
public class PurchaseTypeDaoImpl extends AncestorDao<PurchaseTypeEntity> implements PurchaseTypeDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.purchaseType";
	}
}
