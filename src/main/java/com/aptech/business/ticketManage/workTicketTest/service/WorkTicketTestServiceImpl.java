package com.aptech.business.ticketManage.workTicketTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.ticketManage.workTicketTest.dao.WorkTicketTestDao;
import com.aptech.business.ticketManage.workTicketTest.domain.WorkTicketTestEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 可燃易爆实验应用管理服务实现类
 *
 * @author 
 * @created 2017-08-22 10:11:16
 * @lastModified 
 * @history
 *
 */
@Service("workTicketTestService")
@Transactional
public class WorkTicketTestServiceImpl extends AbstractBaseEntityOperation<WorkTicketTestEntity> implements WorkTicketTestService {
	
	@Autowired
	private WorkTicketTestDao workTicketTestDao;
	
	@Override
	public IBaseEntityOperation<WorkTicketTestEntity> getDao() {
		return workTicketTestDao;
	}
}