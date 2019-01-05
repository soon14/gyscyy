package com.aptech.business.ticketManage.workTicketUserRel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.business.ticketManage.workTicketUserRel.dao.WorkTicketUserRelDao;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 电气工作票应用管理服务实现类
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Service("workTicketUserRelService")
@Transactional
public class WorkTicketUserRelServiceImpl extends AbstractBaseEntityOperation<WorkTicketUserRelEntity> implements WorkTicketUserRelService {
	
	
	@Autowired
	private WorkTicketUserRelDao workTicketUserRelDao;
	@Override
	public IBaseEntityOperation<WorkTicketUserRelEntity> getDao() {
		return workTicketUserRelDao;
	}
	
	public void addEntity(WorkTicketUserRelEntity workTicketUserRelEntity){
		try {
			
			workTicketUserRelDao.addEntity(workTicketUserRelEntity);;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public void updateSpnrAgree(WorkTicketUserRelEntity workElectricEntity,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSpnrDisagree(WorkTicketUserRelEntity workElectricEntity,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submit(String id, String selectUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultObj update(WorkTicketUserRelEntity t, Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}