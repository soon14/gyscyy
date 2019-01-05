package com.aptech.business.OAManagement.leaderApproval.dao;
 
import org.springframework.stereotype.Repository;

import com.aptech.business.OAManagement.leaderApproval.domain.ReceiptLeaderApprovalEntity;
import com.aptech.framework.orm.AncestorDao;

@Repository("receiptLeaderApprovalDao")
public class ReceiptLeaderApprovalDaoImpl extends AncestorDao<ReceiptLeaderApprovalEntity> implements ReceiptLeaderApprovalDao {
    @Override
	public String getNameSpace() {
		return "com.aptech.business.OAManagement.leaderApproval.ReceiptLeaderApproval";
	}
    
}