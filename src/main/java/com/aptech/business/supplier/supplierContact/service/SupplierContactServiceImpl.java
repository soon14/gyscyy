package com.aptech.business.supplier.supplierContact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.supplier.supplierContact.dao.SupplierContactDao;
import com.aptech.business.supplier.supplierContact.domain.SupplierContactEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 供应商联系人关联表应用管理服务实现类
 *
 * @author 
 * @created 2017-11-02 14:14:40
 * @lastModified 
 * @history
 *
 */
@Service("supplierContactService")
@Transactional
public class SupplierContactServiceImpl extends AbstractBaseEntityOperation<SupplierContactEntity> implements SupplierContactService {
	
	@Autowired
	private SupplierContactDao supplierContactDao;
	
	@Override
	public IBaseEntityOperation<SupplierContactEntity> getDao() {
		return supplierContactDao;
	}
}