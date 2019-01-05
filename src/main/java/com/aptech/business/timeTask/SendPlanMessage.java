package com.aptech.business.timeTask;

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
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;


@Service("sendPlanMessage")
public class SendPlanMessage {
	
	@Autowired
	private OverhaulProjectService overhaulProjectService;
	@Autowired
	private OverhaulPlanService overhaulPlanService;
	@Autowired
	private OverhaulPlanMemberService overhaulPlanMemberService;
	/**
	 * 发送消息入口
	 */
	public void startSend() {
		
		List<Condition> conditions = new ArrayList<Condition>();
		 conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		 conditions.add(new Condition("C_APPROVE_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, OverhaulPlanStatusEnum.FINISH.getCode()));
		 List<OverhaulPlanEntity> projectList =overhaulPlanService.findByCondition(conditions, null);
		 
		 Calendar calendar = Calendar.getInstance();
		 Date nowDate = new Date();
		 Calendar toCalendar = Calendar.getInstance();
		 toCalendar.setTime(nowDate);
		 
		 if (projectList!=null && projectList.size()>0) {
			for (OverhaulPlanEntity projectEntity : projectList) {
				conditions.clear();
				conditions.add(new Condition("O.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
				conditions.add(new Condition("O.C_OVERHUAL_PLAN_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, projectEntity.getId()));
				 List<OverhaulProjectEntity> pList =overhaulProjectService.findByCondition(conditions, null);
				if (pList.size()>0) {
					for (OverhaulProjectEntity overhaulProjectEntity : pList) {
						calendar.setTime(overhaulProjectEntity.getStartDate());
						calendar.add(Calendar.DATE, -30);
//						calendar.add(Calendar.MONTH, -1);
						int result=calendar.compareTo(toCalendar);
						if(result==-1 || result==0){
							conditions.clear();
							conditions.add(new Condition("p.C_OVERHAUL_PLAN_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, projectEntity.getId()));
							List<OverhaulPlanMemberEntity> planMemberList = overhaulPlanMemberService.findByCondition(conditions, null);
							if(planMemberList != null && planMemberList.size()>0){
								for(OverhaulPlanMemberEntity t : planMemberList){
									overhaulPlanService.insertMemberAuto(t, projectEntity);
								}
							}
						}
					}
				}
		
			}
		}
		
	}
	
}
