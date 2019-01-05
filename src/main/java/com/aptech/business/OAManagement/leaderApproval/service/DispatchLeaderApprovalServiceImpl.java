package com.aptech.business.OAManagement.leaderApproval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.OAManagement.leaderApproval.dao.DispatchLeaderApprovalDao;
import com.aptech.business.OAManagement.leaderApproval.domain.DispatchLeaderApprovalEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation; 

@Service("dispatchLeaderApprovalService")
@Transactional
public class DispatchLeaderApprovalServiceImpl extends AbstractBaseEntityOperation<DispatchLeaderApprovalEntity> implements DispatchLeaderApprovalService {

    @Autowired
    private DispatchLeaderApprovalDao dispatchLeaderApprovalDao;

   	@Override
	public IBaseEntityOperation<DispatchLeaderApprovalEntity> getDao() {
		return dispatchLeaderApprovalDao;
	}
}