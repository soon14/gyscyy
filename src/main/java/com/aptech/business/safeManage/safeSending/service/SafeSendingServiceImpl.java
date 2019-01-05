package com.aptech.business.safeManage.safeSending.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.safeManage.safeSending.dao.SafeSendingDao;
import com.aptech.business.safeManage.safeSending.domain.SafeSendingEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全发文应用管理服务实现类
 *
 * @author 
 * @created 2018-04-02 09:52:16
 * @lastModified 
 * @history
 *
 */
@Service("safeSendingService")
@Transactional
public class SafeSendingServiceImpl extends AbstractBaseEntityOperation<SafeSendingEntity> implements SafeSendingService {
	
	@Autowired
	private SafeSendingDao safeSendingDao;
	
	@Override
	public IBaseEntityOperation<SafeSendingEntity> getDao() {
		return safeSendingDao;
	}

	@Override
	public ResultObj add(SafeSendingEntity safeSendingEntity) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		safeSendingEntity.setUserId(userEntity.getId().toString());
		safeSendingDao.addEntity(safeSendingEntity);	
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeSendingEntity);
 		return resultObj;
	}

	@Override
	public ResultObj update(SafeSendingEntity safeSendingEntity) {
		safeSendingDao.updateEntity(safeSendingEntity);
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeSendingEntity);
 		return resultObj;
	}
}