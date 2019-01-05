package com.aptech.business.OAManagement.outDepartment.dao;
 
import org.springframework.stereotype.Repository;

import com.aptech.business.OAManagement.outDepartment.domain.ReceiptOutDepartmentEntity;
import com.aptech.framework.orm.AncestorDao;

@Repository("receiptOutDepartmentDao")
public class ReceiptOutDepartmentDaoImpl extends AncestorDao<ReceiptOutDepartmentEntity> implements ReceiptOutDepartmentDao {
    @Override
	public String getNameSpace() {
		return "com.aptech.business.OAManagement.outDepartment.ReceiptOutDepartment";
	}
    
}