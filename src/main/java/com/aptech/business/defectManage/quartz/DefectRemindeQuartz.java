/*	
 * Web 5.0 Copyright 2016 Aptech, Co.ltd. All rights reserved.
 * 		 
 * FileName: package-info.java
 *
 */

/** 
 * {该处请说明该class的含义和作用}
 *
 * @author zhangjx
 * @created 2018年11月21日 下午5:14:37 
 * @lastModified 
 * @history
 * 
 */

package com.aptech.business.defectManage.quartz;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;

@Service("defectRemindeQuartz")
public class DefectRemindeQuartz{
	@Autowired
	private DefectService defectService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Autowired
	private SysUserService sysUserService;
	
	public void sendMessageDefect(){
		List<Condition> conditions = new ArrayList<Condition>();
		List<DefectEntity> defectList = defectService.findByCondition(conditions, null);
		Date sysDate = new Date();
		for(DefectEntity defectEntity : defectList){
			Date defectTime = defectEntity.getFindTime();
			long newDate = sysDate.getTime()-defectTime.getTime();
			int days = (int) newDate/(1000*3600*24);
			if(days>=5){
				conditions.clear();
				conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "85"));
				List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(conditions, null);
				List<Long> userUnitIds = new ArrayList<Long>();
				for(SysDutiesDetailEntity sysDutiesDetailEntity : sysDutiesDetailList){
					userUnitIds.add(Long.valueOf(sysDutiesDetailEntity.getUserUnitRelId()));
				}
				conditions.clear();
				conditions.add(new Condition("C_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, userUnitIds.toArray()));
				List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(conditions, null);
				if(defectEntity.getSendMessage()==null){
					for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
						SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(userUnitRelEntity.getUserId()));
						MessageCenterEntity messageEntity =new MessageCenterEntity();
						messageEntity.setTitle("缺陷已消缺！");
						messageEntity.setSendTime(new Date());
						messageEntity.setReceivePerson(sysUserEntity.getId().toString());
						messageEntity.setSendPerson(defectEntity.getFindUserId().toString());
					 	messageEntity.setType("private");
					 	messageCenterService.addMessage(messageEntity);
					 	defectEntity.setSendMessage("yes");
					 	defectService.update(defectEntity);
					}
				}
			}
		}
	}
}
