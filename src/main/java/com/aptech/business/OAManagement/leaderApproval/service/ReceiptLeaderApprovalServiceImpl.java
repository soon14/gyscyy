package com.aptech.business.OAManagement.leaderApproval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.OAManagement.leaderApproval.dao.ReceiptLeaderApprovalDao;
import com.aptech.business.OAManagement.leaderApproval.domain.ReceiptLeaderApprovalEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation; 

@Service("receiptLeaderApprovalService")
@Transactional
public class ReceiptLeaderApprovalServiceImpl extends AbstractBaseEntityOperation<ReceiptLeaderApprovalEntity> implements ReceiptLeaderApprovalService {

    @Autowired
    private ReceiptLeaderApprovalDao receiptLeaderApprovalDao;

   	@Override
	public IBaseEntityOperation<ReceiptLeaderApprovalEntity> getDao() {
		return receiptLeaderApprovalDao;
	}
}