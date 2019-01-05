package com.aptech.business.messageCenter.service;

import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 消息中心应用管理服务接口
 *
 * @author 
 * @created 2017-08-10 17:12:09
 * @lastModified 
 * @history
 *
 */
public interface MessageCenterService  extends IBaseEntityOperation<MessageCenterEntity> {
	
	/**
	 * 
	 * 发送消息接口
	 * 必须字段：title、context、sendTime、sendPerson、type、status
	 * 人对人发送消息需要字段：receivePerson 单人为字符串，多人为字符串数组
	 * 人对职务发送消息需要字段：category（消息分类，如职务）、group（消息分组，如班长）
	 * @param @param messageEntity
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月16日 下午5:35:41
	 * @lastModified
	 */
	public void addMessage(MessageCenterEntity messageEntity);
	
	/**
	 * 
	 * 查询登录人未读信息数量接口
	 * 
	 * @param @return
	 * @return int
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月16日 下午7:17:44
	 * @lastModified
	 */
	public int searchMessageCount();
}