package com.aptech.business.managePlanContract.supplierManage.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.supplierManage.domain.SupplierManageEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 供应商管理应用数据类
 *
 * @author 
 * @created 2018-07-26 17:15:46
 * @lastModified 
 * @history
 *
 */
@Repository("supplierManageDao")
public class SupplierManageDaoImpl extends AncestorDao<SupplierManageEntity> implements SupplierManageDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.supplierManage";
	}
}
