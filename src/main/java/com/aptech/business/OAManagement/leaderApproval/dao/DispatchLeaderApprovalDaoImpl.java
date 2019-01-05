package com.aptech.business.OAManagement.leaderApproval.dao;
 
import org.springframework.stereotype.Repository;

import com.aptech.business.OAManagement.leaderApproval.domain.DispatchLeaderApprovalEntity;
import com.aptech.framework.orm.AncestorDao;

@Repository("dispatchLeaderApprovalDao")
public class DispatchLeaderApprovalDaoImpl extends AncestorDao<DispatchLeaderApprovalEntity> implements DispatchLeaderApprovalDao {
    @Override
	public String getNameSpace() {
		return "com.aptech.business.OAManagement.leaderApproval.DispatchLeaderApproval";
	}
    
}