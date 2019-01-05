package com.aptech.business.technical.eventNotification.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.technical.eventNotification.domain.EventNotificationEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 事件通报应用数据类
 *
 * @author 
 * @created 2018-07-30 11:42:47
 * @lastModified 
 * @history
 *
 */
@Repository("eventNotificationDao")
public class EventNotificationDaoImpl extends AncestorDao<EventNotificationEntity> implements EventNotificationDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.eventNotification";
	}
}
