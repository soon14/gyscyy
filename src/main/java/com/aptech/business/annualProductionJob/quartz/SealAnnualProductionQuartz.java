package com.aptech.business.annualProductionJob.quartz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.business.annualProductionJob.domain.AnnualProductionJobEntity;
import com.aptech.business.annualProductionJob.service.AnnualProductionJobService;
import com.aptech.framework.orm.search.Condition;

@Service("sealAnnualProductionJobQuartz")
public class SealAnnualProductionQuartz {
	
	@Autowired
	private AnnualProductionJobService annualProductionJobService;
	
	public void sealManualProductionJob(){
		Calendar calendar = Calendar.getInstance();
		List<Condition> conditions = new ArrayList<Condition>();
		List<AnnualProductionJobEntity> annualProductionJobList = annualProductionJobService.findByCondition(conditions, null);
		for(AnnualProductionJobEntity annualProductionJobEntity : annualProductionJobList){ 
			if(annualProductionJobEntity.getCompleteDate()!=null){
				calendar.setTime(annualProductionJobEntity.getCompleteDate());
				calendar.add(Calendar.MONTH, 1);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				Date nextMonth = calendar.getTime();
				Date today = new Date();
				if(today.compareTo(nextMonth)>=0){
					annualProductionJobEntity.setSealStatus("1");
					annualProductionJobService.updateEntity(annualProductionJobEntity);
				}
			}
		}
	}
}
