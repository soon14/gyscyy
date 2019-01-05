package com.aptech.business.supplier.supplier.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.supplier.supplier.domain.SupplierEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 供应商管理应用数据类
 *
 * @author 
 * @created 2017-11-02 10:30:36
 * @lastModified 
 * @history
 *
 */
@Repository("supplierDao")
public class SupplierDaoImpl extends AncestorDao<SupplierEntity> implements SupplierDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.supplier.supplier";
	}
}
