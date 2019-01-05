package com.aptech.business.OAManagement.receivingUnit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.OAManagement.receivingUnit.dao.ReceiptReceivingUnitDao;
import com.aptech.business.OAManagement.receivingUnit.domain.ReceiptReceivingUnitEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation; 

@Service("receiptReceivingUnitService")
@Transactional
public class ReceiptReceivingUnitServiceImpl extends AbstractBaseEntityOperation<ReceiptReceivingUnitEntity> implements ReceiptReceivingUnitService {

    @Autowired
    private ReceiptReceivingUnitDao receiptReceivingUnitDao;

   	@Override
	public IBaseEntityOperation<ReceiptReceivingUnitEntity> getDao() {
		return receiptReceivingUnitDao;
	}
}