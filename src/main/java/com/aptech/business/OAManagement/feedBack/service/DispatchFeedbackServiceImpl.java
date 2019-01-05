package com.aptech.business.OAManagement.feedBack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.OAManagement.feedBack.dao.DispatchFeedbackDao;
import com.aptech.business.OAManagement.feedBack.domain.DispatchFeedbackEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation; 

@Service("dispatchFeedbackService")
@Transactional
public class DispatchFeedbackServiceImpl extends AbstractBaseEntityOperation<DispatchFeedbackEntity> implements DispatchFeedbackService {

    @Autowired
    private DispatchFeedbackDao dispatchFeedbackDao;

   	@Override
	public IBaseEntityOperation<DispatchFeedbackEntity> getDao() {
		return dispatchFeedbackDao;
	}
}