package com.aptech.business.safeManage.safeCheck.service;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.safeManage.safeCheck.dao.SafeCheckDao;
import com.aptech.business.safeManage.safeCheck.domain.SafeCheckEntity;
import com.aptech.business.safeManage.targetManage.domain.TargetManageEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.IdUtil;

/**
 * 
 * 安全检查应用管理服务实现类
 *
 * @author 
 * @created 2018-03-28 10:22:17
 * @lastModified 
 * @history
 *
 */
@Service("safeCheckService")
@Transactional
public class SafeCheckServiceImpl extends AbstractBaseEntityOperation<SafeCheckEntity> implements SafeCheckService {
	
	@Autowired
	private SafeCheckDao safeCheckDao;
	
	@Override
	public IBaseEntityOperation<SafeCheckEntity> getDao() {
		return safeCheckDao;
	}
	
	@Override
	public void addEntity(SafeCheckEntity t) {
		t.setCreateDate(new Date());
			safeCheckDao.addEntity(t);
	}
	@Override
	public void updateEntity(SafeCheckEntity t) {
			safeCheckDao.updateEntity(t);
	}
}