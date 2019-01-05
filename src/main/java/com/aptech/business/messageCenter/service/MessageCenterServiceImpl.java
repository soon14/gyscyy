package com.aptech.business.messageCenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.messageBox.domain.MessageBoxEntity;
import com.aptech.business.messageBox.service.MessageBoxService;
import com.aptech.business.messageCenter.dao.MessageCenterDao;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;

/**
 * 
 * 消息中心应用管理服务实现类
 *
 * @author 
 * @created 2017-08-10 17:12:09
 * @lastModified 
 * @history
 *
 */
@Service("messageCenterService")
@Transactional
public class MessageCenterServiceImpl extends AbstractBaseEntityOperation<MessageCenterEntity> implements MessageCenterService {
	
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private SysDutiesDetailService sysdutiesDetailService;
	@Autowired
	private MessageBoxService messageBoxService;
	@Autowired
	private MessageCenterDao messageCenterDao;
	
	@Override
	public IBaseEntityOperation<MessageCenterEntity> getDao() {
		return messageCenterDao;
	}
	
	/**
	 * 发送信息接口
	 */
	public void addMessage(MessageCenterEntity messageEntity){
		if(messageEntity!=null){
			//存储信息主体
			this.addEntity(messageEntity);
			
			MessageBoxEntity messageBoxEntity = new MessageBoxEntity();
			String receivePersonString = messageEntity.getReceivePerson();
			messageBoxEntity.setContextId(messageEntity.getId());
			messageBoxEntity.setStatus("0");
			//人对人
			if(messageEntity.getType().equals("private")){
				//人对多人
				if(receivePersonString.contains(",")){
					String[] receivePersonArray = receivePersonString.split(",");
					for(int i=0;i<receivePersonArray.length;i++){
						messageBoxEntity.setReceivePerson(receivePersonArray[i]);
						messageBoxService.addEntity(messageBoxEntity);
					}
				}else{
					//人对个人
					messageBoxEntity.setReceivePerson(receivePersonString);
					messageBoxService.addEntity(messageBoxEntity);
				}
			}
//			else if(messageEntity.getType().equals("public")){
//				//人对职务
//				String messageGroupTemp = messageEntity.getGroup();
//				//整理多选下拉框返回的值
//				if(messageGroupTemp.startsWith(",")){
//					messageGroupTemp = messageGroupTemp.substring(1, messageGroupTemp.length());
//				}
//				if(messageGroupTemp.contains(",")){
//					//向多个职务发送
//					String[] messageGroupArray = messageGroupTemp.split(",");
//					for(int i=0;i<messageGroupArray.length;i++){
//						this.addSingleEntity(messageGroupArray[i], messageBoxEntity);
//					}
//				}else{
//					//向单个职务发送
//					this.addSingleEntity(messageGroupTemp, messageBoxEntity);
//				}
//			}
		}
	}
	
	public void addSingleEntity(String messageGroupTemp, MessageBoxEntity messageBoxEntity){
		List<Condition> list_condition = new ArrayList<Condition>();
		list_condition.add(new Condition("C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,messageGroupTemp));
		List<SysDutiesEntity> sysDutiesEntities = sysDutiesService.findByCondition(list_condition, null);
		SysDutiesEntity sysDutiesEntity = sysDutiesEntities.get(0);
		String sysDutiesUserId = sysdutiesDetailService.findByDutiesId(String.valueOf(sysDutiesEntity.getId()));
		String sysUserIdString = sysDutiesUserId.substring(1,sysDutiesUserId.length()-2);
		String[] sysUserIdArray = sysUserIdString.split(",");
		for(int i=0;i<sysUserIdArray.length;i++){
			messageBoxEntity.setReceivePerson(sysUserIdArray[i]);
			messageBoxService.addEntity(messageBoxEntity);
		}
	}
	
	/**
	 * 
	 * 查询登录人未读信息的数量
	 * 
	 * @param @return
	 * @return int
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月16日 下午7:15:02
	 * @lastModified
	 */
	public int searchMessageCount(){
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		List<Condition> list_condition = new ArrayList<Condition>();
		list_condition.add(new Condition("C_STATUS",FieldTypeEnum.STRING,MatchTypeEnum.EQ,0));
		list_condition.add(new Condition("C_RECEIVE_PERSON",FieldTypeEnum.STRING,MatchTypeEnum.EQ,sysUserEntity.getId()));
		List<MessageBoxEntity> messageBoxEntities = messageBoxService.findByCondition(list_condition, null);
		return messageBoxEntities.size();
	}
	
	
}