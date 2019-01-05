package com.aptech.business.supplier.supplierContact.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.supplier.supplierContact.domain.SupplierContactEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 供应商联系人关联表应用数据类
 *
 * @author 
 * @created 2017-11-02 14:14:40
 * @lastModified 
 * @history
 *
 */
@Repository("supplierContactDao")
public class SupplierContactDaoImpl extends AncestorDao<SupplierContactEntity> implements SupplierContactDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.supplier.supplier.supplierContact";
	}
}
