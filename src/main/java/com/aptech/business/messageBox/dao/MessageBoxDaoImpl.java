package com.aptech.business.messageBox.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.messageBox.domain.MessageBoxEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 收件箱应用数据类
 *
 * @author 
 * @created 2017-08-16 09:59:57
 * @lastModified 
 * @history
 *
 */
@Repository("messageBoxDao")
public class MessageBoxDaoImpl extends AncestorDao<MessageBoxEntity> implements MessageBoxDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.messageBox";
	}
}
