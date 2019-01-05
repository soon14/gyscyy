package com.aptech.business.invitePurchase.cancelHistory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.invitePurchase.cancelHistory.dao.CancelHistoryDao;
import com.aptech.business.invitePurchase.cancelHistory.domain.CancelHistoryEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 撤回历史应用管理服务实现类
 *
 * @author 
 * @created 2018-09-10 13:41:56
 * @lastModified 
 * @history
 *
 */
@Service("cancelHistoryService")
@Transactional
public class CancelHistoryServiceImpl extends AbstractBaseEntityOperation<CancelHistoryEntity> implements CancelHistoryService {
	
	@Autowired
	private CancelHistoryDao cancelHistoryDao;
	
	@Override
	public IBaseEntityOperation<CancelHistoryEntity> getDao() {
		return cancelHistoryDao;
	}
}