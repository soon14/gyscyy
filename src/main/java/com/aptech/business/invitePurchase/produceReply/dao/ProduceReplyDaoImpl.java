package com.aptech.business.invitePurchase.produceReply.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.invitePurchase.produceReply.domain.ProduceReplyEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 立项批复应用数据类
 *
 * @author 
 * @created 2018-09-07 14:52:40
 * @lastModified 
 * @history
 *
 */
@Repository("produceReplyDao")
public class ProduceReplyDaoImpl extends AncestorDao<ProduceReplyEntity> implements ProduceReplyDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.produceReply";
	}
}
