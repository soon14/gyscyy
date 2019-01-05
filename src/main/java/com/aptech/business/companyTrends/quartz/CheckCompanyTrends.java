package com.aptech.business.companyTrends.quartz;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.business.companyTrends.domain.CompanyTrendsEntity;
import com.aptech.business.companyTrends.service.CompanyTrendsService;
import com.aptech.business.train.trainPlan.domain.TrainPlanEntity;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.util.DateFormatUtil;


@Service("checkCompanyTrends")
public class CheckCompanyTrends {
	
	@Autowired
	private CompanyTrendsService companyTrendsService;
	
	
	public void checkCompanyTrendsTime() {
		
		List<Condition> conditions = new ArrayList<Condition>();
		 List<CompanyTrendsEntity> companyTrendsEntityList =companyTrendsService.findByCondition(conditions, null);
		 
		 DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd");
		 Date nowTime = new Date();
		 if (companyTrendsEntityList!=null && companyTrendsEntityList.size()>0) {
			for (CompanyTrendsEntity companyTrendsEntity : companyTrendsEntityList) {
				try {
					Date nowDate = dfu.parse(dfu.format(nowTime));
					Date publishDate = dfu.parse(dfu.format(companyTrendsEntity.getPublishTime()));
					Date endDate = dfu.parse(dfu.format(companyTrendsEntity.getEndTime()));
					String status = companyTrendsEntity.getStatus();
					if(nowDate.before(endDate)&&nowDate.after(publishDate)&&"8".equals(status)){
						companyTrendsEntity.setStatus("2");
					}else if(nowDate.equals(publishDate)&&nowDate.before(endDate)&&"8".equals(status)){
						companyTrendsEntity.setStatus("2");
					}else if(nowDate.equals(publishDate)&&nowDate.equals(endDate)&&"8".equals(status)){
						companyTrendsEntity.setStatus("3");
					}else if(nowDate.after(endDate)&&"2".equals(status)){
						companyTrendsEntity.setStatus("3");
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				companyTrendsService.updateEntity(companyTrendsEntity);	
			}
		}
		
	}
	
}
