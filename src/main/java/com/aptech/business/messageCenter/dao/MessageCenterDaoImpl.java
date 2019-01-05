package com.aptech.business.messageCenter.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 消息中心应用数据类
 *
 * @author 
 * @created 2017-08-10 17:12:09
 * @lastModified 
 * @history
 *
 */
@Repository("messageCenterDao")
public class MessageCenterDaoImpl extends AncestorDao<MessageCenterEntity> implements MessageCenterDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.messageCenter";
	}
}
