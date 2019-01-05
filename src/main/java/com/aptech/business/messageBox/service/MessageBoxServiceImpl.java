package com.aptech.business.messageBox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.messageBox.dao.MessageBoxDao;
import com.aptech.business.messageBox.domain.MessageBoxEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 收件箱应用管理服务实现类
 *
 * @author 
 * @created 2017-08-16 09:59:57
 * @lastModified 
 * @history
 *
 */
@Service("messageBoxService")
@Transactional
public class MessageBoxServiceImpl extends AbstractBaseEntityOperation<MessageBoxEntity> implements MessageBoxService {
	
	@Autowired
	private MessageBoxDao messageBoxDao;
	
	@Override
	public IBaseEntityOperation<MessageBoxEntity> getDao() {
		return messageBoxDao;
	}
}