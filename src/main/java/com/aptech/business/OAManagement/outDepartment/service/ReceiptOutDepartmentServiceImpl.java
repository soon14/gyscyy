package com.aptech.business.OAManagement.outDepartment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.OAManagement.outDepartment.dao.ReceiptOutDepartmentDao;
import com.aptech.business.OAManagement.outDepartment.domain.ReceiptOutDepartmentEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

@Service("receiptOutDepartmentService")
@Transactional
public class ReceiptOutDepartmentServiceImpl extends AbstractBaseEntityOperation<ReceiptOutDepartmentEntity> implements ReceiptOutDepartmentService {

    @Autowired
    private ReceiptOutDepartmentDao receiptOutDepartmentDao;

   	@Override
	public IBaseEntityOperation<ReceiptOutDepartmentEntity> getDao() {
		return receiptOutDepartmentDao;
	}
}