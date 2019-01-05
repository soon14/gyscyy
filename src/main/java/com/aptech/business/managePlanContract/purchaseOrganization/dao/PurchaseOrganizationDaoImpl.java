package com.aptech.business.managePlanContract.purchaseOrganization.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.purchaseOrganization.domain.PurchaseOrganizationEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 物资采购计划组织机构列表应用数据类
 *
 * @author 
 * @created 2018-07-25 17:08:06
 * @lastModified 
 * @history
 *
 */
@Repository("purchaseOrganizationDao")
public class PurchaseOrganizationDaoImpl extends AncestorDao<PurchaseOrganizationEntity> implements PurchaseOrganizationDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.purchaseOrganization";
	}
}
