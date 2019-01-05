package com.aptech.business.OAManagement.receiptManagement.dao;
 
import org.springframework.stereotype.Repository;

import com.aptech.business.OAManagement.receiptManagement.domain.ReceiptManagementEntity;
import com.aptech.framework.orm.AncestorDao;

@Repository("receiptManagementDao")
public class ReceiptManagementDaoImpl extends AncestorDao<ReceiptManagementEntity> implements ReceiptManagementDao {
    @Override
	public String getNameSpace() {
		return "com.aptech.business.OAManagement.receiptManagement.ReceiptManagement";
	}
    
}