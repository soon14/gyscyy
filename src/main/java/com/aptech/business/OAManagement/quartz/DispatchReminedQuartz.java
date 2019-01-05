package com.aptech.business.OAManagement.quartz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchApprovalStatusEnum;
import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchManagementEntity;
import com.aptech.business.OAManagement.dispatchManagement.service.DispatchManagementService;
import com.aptech.business.OAManagement.feedBack.domain.DispatchFeedbackEntity;
import com.aptech.business.OAManagement.feedBack.service.DispatchFeedbackService;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;


@Service("dispatchReminded")
public class DispatchReminedQuartz {
	
	@Autowired
	private DispatchManagementService dispatchManagementService;
	
	/**
	 * 消息服务对象
	 */
	@Autowired
	private MessageCenterService messageCenterService;
	/**
	 * user服务对象
	 */
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 反馈服务对象
	 */
	@Autowired
	private DispatchFeedbackService dispatchFeedbackService;
	
	
	public void getDispatchReminded() {
		
		DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
		
		Calendar startCal = Calendar.getInstance();
		
		Calendar ednCal = Calendar.getInstance();
		ednCal.add(Calendar.MINUTE, +45);
		
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_FEEDBACK", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
		conditions.add(new Condition("C_FEEDBACK_REMIND_TIME", FieldTypeEnum.STRING, MatchTypeEnum.GE, dfu.format(startCal.getTime())));
		conditions.add(new Condition("C_FEEDBACK_REMIND_TIME", FieldTypeEnum.STRING, MatchTypeEnum.LE, dfu.format(ednCal.getTime())));
		conditions.add(new Condition("C_APPROVAL_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, DispatchApprovalStatusEnum.RESULTS.getCode()));
		List<DispatchManagementEntity> dataList = dispatchManagementService.findByCondition(conditions, null);
		
		List<SysUserEntity> useList = this.sysUserService.findAll();
		
		if (dataList != null && dataList.size() > 0) {
			//取出登录用户可以接收的发文
			for (DispatchManagementEntity entity : dataList) {
				//发布状态 1全部挂网，2指定接收人
				String releaseStatus = entity.getReleaseStatus();
				if ("1".equals(releaseStatus)) {
					//取出登录用户反馈过的发文
					for (SysUserEntity userEntity :useList) {
						conditions.clear();
						conditions.add(new Condition("C_DISPATCH_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, entity.getId()));
						conditions.add(new Condition("C_FEEDBACK_PERSION_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getUnitId().toString()));
						List<DispatchFeedbackEntity> feedbackDataList = dispatchFeedbackService.findByCondition(conditions, null);
						if (feedbackDataList != null && feedbackDataList.size() > 0) {
							MessageCenterEntity messageEntity =new MessageCenterEntity();
							messageEntity.setTitle("发文: " + entity.getTitle());
							messageEntity.setSendTime(new Date());
							messageEntity.setReceivePerson(userEntity.getId().toString());
							messageEntity.setSendPerson(entity.getDrafterName());
						 	messageEntity.setType("private");
						 	messageCenterService.addMessage(messageEntity);
						}
					}
				} else {
					//指定接收人
					//接受人类型 1单位，2个人
//					String recipientType = entity.getRecipientType();
					String recipientId = entity.getRecipientId();
//					if ("1".equals(recipientType)) {
//						//取出登录用户反馈过的发文
//						for (SysUserEntity userEntity :useList) {
//							if (recipientId.equals(userEntity.getUnitId().toString())){
//								conditions.clear();
//								conditions.add(new Condition("C_DISPATCH_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, entity.getId()));
//								conditions.add(new Condition("C_FEEDBACK_PERSION_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getUnitId().toString()));
//								List<DispatchFeedbackEntity> feedbackDataList = dispatchFeedbackService.findByCondition(conditions, null);
//								if (feedbackDataList != null && feedbackDataList.size() > 0) {
//									MessageCenterEntity messageEntity =new MessageCenterEntity();
//									messageEntity.setTitle("发文: " + entity.getTitle());
//									messageEntity.setSendTime(new Date());
//									messageEntity.setReceivePerson(String.valueOf(userEntity.getId().intValue()));
//									messageEntity.setSendPerson(String.valueOf(userEntity.getId().intValue()));
//									messageEntity.setSendPerson(entity.getDrafterName());
//								 	messageEntity.setType("private");
//								 	messageCenterService.addMessage(messageEntity);
//								}
//							}
//						}
//					} else if ("2".equals(recipientType)) {
							String [] uids = recipientId.split(",");
							String [] names = entity.getDrafterName().split(",");
							for (int i=0 ;i<uids.length; i++) {
								String uid = uids[i].replace("U", "");
								MessageCenterEntity messageEntity =new MessageCenterEntity();
								messageEntity.setTitle("发文: " + entity.getTitle());
								messageEntity.setSendTime(new Date());
								messageEntity.setReceivePerson(uid);
								messageEntity.setSendPerson(uid);
								messageEntity.setSendPerson(names[i]);
								messageEntity.setType("private");
								messageCenterService.addMessage(messageEntity);
							}
//					}
				}
			}
		}
	}
}
