package com.aptech.business.OAManagement.receivingUnit.dao;
 
import org.springframework.stereotype.Repository;

import com.aptech.business.OAManagement.receivingUnit.domain.ReceiptReceivingUnitEntity;
import com.aptech.framework.orm.AncestorDao;

@Repository("receiptReceivingUnitDao")
public class ReceiptReceivingUnitDaoImpl extends AncestorDao<ReceiptReceivingUnitEntity> implements ReceiptReceivingUnitDao {
    @Override
	public String getNameSpace() {
		return "com.aptech.business.OAManagement.receivingUnit.ReceiptReceivingUnit";
	}
    
}