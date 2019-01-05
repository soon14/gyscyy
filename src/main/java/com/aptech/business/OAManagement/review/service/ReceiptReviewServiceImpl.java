package com.aptech.business.OAManagement.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.OAManagement.review.dao.ReceiptReviewDao;
import com.aptech.business.OAManagement.review.domain.ReceiptReviewEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation; 

@Service("receiptReviewService")
@Transactional
public class ReceiptReviewServiceImpl extends AbstractBaseEntityOperation<ReceiptReviewEntity> implements ReceiptReviewService {

    @Autowired
    private ReceiptReviewDao receiptReviewDao;

   	@Override
	public IBaseEntityOperation<ReceiptReviewEntity> getDao() {
		return receiptReviewDao;
	}
}