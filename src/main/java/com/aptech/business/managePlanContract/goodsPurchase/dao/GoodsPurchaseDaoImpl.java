package com.aptech.business.managePlanContract.goodsPurchase.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.goodsPurchase.domain.GoodsPurchaseEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 物资采购应用数据类
 *
 * @author 
 * @created 2018-04-17 15:00:02
 * @lastModified 
 * @history
 *
 */
@Repository("goodsPurchaseDao")
public class GoodsPurchaseDaoImpl extends AncestorDao<GoodsPurchaseEntity> implements GoodsPurchaseDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.goodsPurchase";
	}
}
