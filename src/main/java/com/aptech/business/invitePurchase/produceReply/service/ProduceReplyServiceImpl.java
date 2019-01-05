package com.aptech.business.invitePurchase.produceReply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.invitePurchase.produceReply.dao.ProduceReplyDao;
import com.aptech.business.invitePurchase.produceReply.domain.ProduceReplyEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 立项批复应用管理服务实现类
 *
 * @author 
 * @created 2018-09-07 14:52:40
 * @lastModified 
 * @history
 *
 */
@Service("produceReplyService")
@Transactional
public class ProduceReplyServiceImpl extends AbstractBaseEntityOperation<ProduceReplyEntity> implements ProduceReplyService {
	
	@Autowired
	private ProduceReplyDao produceReplyDao;
	
	@Override
	public IBaseEntityOperation<ProduceReplyEntity> getDao() {
		return produceReplyDao;
	}
}