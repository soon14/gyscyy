package com.aptech.business.OAManagement.jointlySign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.OAManagement.jointlySign.dao.ReceiptJointlySignDao;
import com.aptech.business.OAManagement.jointlySign.domain.ReceiptJointlySignEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation; 

@Service("receiptJointlySignService")
@Transactional
public class ReceiptJointlySignServiceImpl extends AbstractBaseEntityOperation<ReceiptJointlySignEntity> implements ReceiptJointlySignService {

    @Autowired
    private ReceiptJointlySignDao receiptJointlySignDao;

   	@Override
	public IBaseEntityOperation<ReceiptJointlySignEntity> getDao() {
		return receiptJointlySignDao;
	}
}