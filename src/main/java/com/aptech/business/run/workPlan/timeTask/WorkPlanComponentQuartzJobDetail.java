package com.aptech.business.run.workPlan.timeTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.aptech.business.run.workPlan.service.WorkPlanService;
import com.aptech.framework.log.Log;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.quartz.ISchedulerJobDetail;
import com.aptech.framework.util.DateFormatUtil;

public class WorkPlanComponentQuartzJobDetail implements ISchedulerJobDetail{

	private static Log logger = Log.getInstance(WorkPlanComponentQuartzJobDetail.class);
	
//	@Autowired
	private WorkPlanService workPlanService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String jobName = String.valueOf(context.get("jobName"));
		logger.info("************Job " + jobName + " Start **************");
		DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
		Calendar nowCal = Calendar.getInstance();
		logger.info("开始时间:" + dfu.format(nowCal.getTime())); 
		List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("a.C_CHECK_STATE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"10"));
	//	List<WorkPlanEntity> workPlanEntity=workPlanService.findByCondition("findByCondition", conditions, null);
	//	operationalAnalysisService=SpringContextHolder.getBean("operationalAnalysisService");
	//	operationalAnalysisService.renovateCurrentMonthData();
		//new OperationalAnalysisServiceImpl().renovateCurrentMonthData();
		logger.info("结束时间:" + dfu.format(new Date()));
	}

	@Override
	public void prepareData(JobExecutionContext jobExecutionContext) {
		
	}

}
