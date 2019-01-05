package com.aptech.business.OAManagement.feedBack.dao;
 
import org.springframework.stereotype.Repository;

import com.aptech.business.OAManagement.feedBack.domain.DispatchFeedbackEntity;
import com.aptech.framework.orm.AncestorDao;

@Repository("dispatchFeedbackDao")
public class DispatchFeedbackDaoImpl extends AncestorDao<DispatchFeedbackEntity> implements DispatchFeedbackDao {
    @Override
	public String getNameSpace() {
		return "com.aptech.business.OAManagement.feedBack.DispatchFeedback";
	}
    
}