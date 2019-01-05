package com.aptech.business.OAManagement.review.dao;
 
import org.springframework.stereotype.Repository;

import com.aptech.business.OAManagement.review.domain.ReceiptReviewEntity;
import com.aptech.framework.orm.AncestorDao;

@Repository("receiptReviewDao")
public class ReceiptReviewDaoImpl extends AncestorDao<ReceiptReviewEntity> implements ReceiptReviewDao {
    @Override
	public String getNameSpace() {
		return "com.aptech.business.OAManagement.review.ReceiptReview";
	}
    
}