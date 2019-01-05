package com.aptech.business.ticketManage.workControlCard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.ticketManage.workControlCard.dao.WorkControlCardDao;
import com.aptech.business.ticketManage.workControlCard.domain.WorkControlCardEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 工作票危险因素控制卡应用管理服务实现类
 *
 * @author 
 * @created 2017-06-05 17:12:09
 * @lastModified 
 * @history
 *
 */
@Service("workControlCardService")
@Transactional
public class WorkControlCardServiceImpl extends AbstractBaseEntityOperation<WorkControlCardEntity> implements WorkControlCardService {
	
	@Autowired
	private WorkControlCardDao workControlCardDao;
	
	@Override
	public IBaseEntityOperation<WorkControlCardEntity> getDao() {
		return workControlCardDao;
	}
}