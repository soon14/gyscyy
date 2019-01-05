package com.aptech.business.train.trainPlan.quartz;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.business.component.dictionary.OverhaulPlanStatusEnum;
import com.aptech.business.overhaul.overhaulPlan.domain.OverhaulPlanEntity;
import com.aptech.business.overhaul.overhaulPlan.service.OverhaulPlanService;
import com.aptech.business.overhaul.overhaulPlanMember.domain.OverhaulPlanMemberEntity;
import com.aptech.business.overhaul.overhaulPlanMember.service.OverhaulPlanMemberService;
import com.aptech.business.overhaul.overhaulProject.domain.OverhaulProjectEntity;
import com.aptech.business.overhaul.overhaulProject.service.OverhaulProjectService;
import com.aptech.business.train.trainPlan.domain.TrainPlanEntity;
import com.aptech.business.train.trainPlan.service.TrainPlanService;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;


@Service("checkTrainStatus")
public class CheckTrainStatus {
	
	@Autowired
	private TrainPlanService trainPlanService;
	
	/**
	* 培训计划状态时间判断定时任务方法
	* @author ly
	* @date 2018年4月16日 下午1:57:37 
	* @return void
	*/
	public void checkStatus() {
		
		List<Condition> conditions = new ArrayList<Condition>();
		List<TrainPlanEntity> trainPlanEntityList =trainPlanService.findByCondition(conditions, null);
		DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
		Date nowTime = new Date();
		if (trainPlanEntityList!=null && trainPlanEntityList.size()>0) {
			for (TrainPlanEntity trainPlanEntity : trainPlanEntityList) {
				try {
					Date nowDate = dfu.parse(dfu.format(nowTime));
					Date trainDate = trainPlanEntity.getTime();
					if(nowDate.before(trainDate)){
						trainPlanEntity.setStatus("0");
					}else{
						trainPlanEntity.setStatus("1");
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				trainPlanService.updateEntity(trainPlanEntity);	
			}
		}
		
	}
	
}
