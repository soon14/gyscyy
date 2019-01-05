package com.aptech.business.OAManagement.dispatchManagement.util;

import java.text.ParseException;
import java.util.Comparator;

import com.aptech.business.OAManagement.review.domain.DispatchReviewEntity;
import com.aptech.framework.util.DateFormatUtil;

public class SortUtil implements Comparator<Object> {
	
	@Override
	public int compare(Object o1, Object o2) {
		DispatchReviewEntity s1 = (DispatchReviewEntity) o1;
		DispatchReviewEntity s2 = (DispatchReviewEntity) o2;
		DateFormatUtil dfuYMdHm = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		try {
			 return dfuYMdHm.parse(s1.getReviewTime()).compareTo(dfuYMdHm.parse(s2.getReviewTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

}
