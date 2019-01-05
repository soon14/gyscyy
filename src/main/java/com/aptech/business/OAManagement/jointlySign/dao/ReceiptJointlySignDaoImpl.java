package com.aptech.business.OAManagement.jointlySign.dao;
 
import org.springframework.stereotype.Repository;

import com.aptech.business.OAManagement.jointlySign.domain.ReceiptJointlySignEntity;
import com.aptech.framework.orm.AncestorDao;

@Repository("receiptJointlySignDao")
public class ReceiptJointlySignDaoImpl extends AncestorDao<ReceiptJointlySignEntity> implements ReceiptJointlySignDao {
    @Override
	public String getNameSpace() {
		return "com.aptech.business.OAManagement.jointlySign.ReceiptJointlySign";
	}
    
}