package com.aptech.business.run.workPlan.timeTask;

import org.springframework.beans.factory.InitializingBean;

import com.aptech.framework.quartz.QuartzUtil;

public class WorkPlanComponent implements InitializingBean{
	
	private String realCronTime;//= "0/60 * * * * ?";
	
	public String getRealCronTime() {
		return realCronTime;
	}

	public void setRealCronTime(String realCronTime) {
		this.realCronTime = realCronTime;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		QuartzUtil.addJob("OperationalAnals",WorkPlanComponentQuartzJobDetail.class,realCronTime, null);
		QuartzUtil.startJobs();
	}

}
