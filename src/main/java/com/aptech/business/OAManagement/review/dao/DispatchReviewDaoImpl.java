package com.aptech.business.OAManagement.review.dao;
 
import org.springframework.stereotype.Repository;

import com.aptech.business.OAManagement.review.domain.DispatchReviewEntity;
import com.aptech.framework.orm.AncestorDao;

@Repository("dispatchReviewDao")
public class DispatchReviewDaoImpl extends AncestorDao<DispatchReviewEntity> implements DispatchReviewDao {
    @Override
	public String getNameSpace() {
		return "com.aptech.business.OAManagement.review.DispatchReview";
	}
    
}