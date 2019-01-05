package com.aptech.business.safeManage.safeMeetings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.safeManage.safeMeetings.dao.SafeMeetingsDao;
import com.aptech.business.safeManage.safeMeetings.domain.SafeMeetingsEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全会议应用管理服务实现类
 *
 * @author 
 * @created 2018-03-29 10:03:15
 * @lastModified 
 * @history
 *
 */
@Service("safeMeetingsService")
@Transactional
public class SafeMeetingsServiceImpl extends AbstractBaseEntityOperation<SafeMeetingsEntity> implements SafeMeetingsService {
	
	@Autowired
	private SafeMeetingsDao safeMeetingsDao;
	
	@Override
	public IBaseEntityOperation<SafeMeetingsEntity> getDao() {
		return safeMeetingsDao;
	}

	@Override
	public ResultObj add(SafeMeetingsEntity safeMeetingsEntity) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		safeMeetingsEntity.setUserId(userEntity.getId().toString());
		safeMeetingsDao.addEntity(safeMeetingsEntity);	
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeMeetingsEntity);
 		return resultObj;
	}

	@Override
	public ResultObj update(SafeMeetingsEntity safeMeetingsEntity) {
		safeMeetingsDao.updateEntity(safeMeetingsEntity);
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeMeetingsEntity);
 		return resultObj;
	}
}