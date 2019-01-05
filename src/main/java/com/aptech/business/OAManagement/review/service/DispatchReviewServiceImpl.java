package com.aptech.business.OAManagement.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.OAManagement.review.dao.DispatchReviewDao;
import com.aptech.business.OAManagement.review.domain.DispatchReviewEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation; 

@Service("dispatchReviewService")
@Transactional
public class DispatchReviewServiceImpl extends AbstractBaseEntityOperation<DispatchReviewEntity> implements DispatchReviewService {

    @Autowired
    private DispatchReviewDao dispatchReviewDao;

   	@Override
	public IBaseEntityOperation<DispatchReviewEntity> getDao() {
		return dispatchReviewDao;
	}
}