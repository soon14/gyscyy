package com.aptech.business.OAManagement.dispatchManagement.dao;
 
import org.springframework.stereotype.Repository;

import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchManagementEntity;
import com.aptech.framework.orm.AncestorDao;

@Repository("dispatchManagementDao")
public class DispatchManagementDaoImpl extends AncestorDao<DispatchManagementEntity> implements DispatchManagementDao {
    @Override
	public String getNameSpace() {
		return "com.aptech.business.OAManagement.dispatchManagement.DispatchManagement";
	}

	@Override
	public void updateReviewInfo(DispatchManagementEntity entity) {
		this.sqlSession.update(createSqlKeyName("updateReviewInfo"), entity);
	}

	@Override
	public void updateJointlySignInfo(DispatchManagementEntity entity) {
		this.sqlSession.update(createSqlKeyName("updateJointlySignInfo"), entity);
	}

	@Override
	public void updateLeaderApprovalInfo(DispatchManagementEntity entity) {
		this.sqlSession.update(createSqlKeyName("updateLeaderApprovalInfo"), entity);
	}
	
	@Override
	public void updateComposingApprovalInfo(DispatchManagementEntity entity) {
		this.sqlSession.update(createSqlKeyName("updateComposing"), entity);
	}

	@Override
	public void updateFeedBackInfo(DispatchManagementEntity entity) {
		this.sqlSession.update(createSqlKeyName("updateFeedBackInfo"), entity);
	}

	@Override
	public void releaseDispatch(DispatchManagementEntity entity) {
		this.sqlSession.update(createSqlKeyName("releaseDispatch"), entity);		
	}
    
}