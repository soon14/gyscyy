package com.aptech.business.invitePurchase.cancelHistory.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.invitePurchase.cancelHistory.domain.CancelHistoryEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 撤回历史应用数据类
 *
 * @author 
 * @created 2018-09-10 13:41:56
 * @lastModified 
 * @history
 *
 */
@Repository("cancelHistoryDao")
public class CancelHistoryDaoImpl extends AncestorDao<CancelHistoryEntity> implements CancelHistoryDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.cancelHistory";
	}
}
