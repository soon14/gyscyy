package com.aptech.business.safeManage.safeStandard.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.safeCheck.domain.SafeCheckEntity;
import com.aptech.business.safeManage.safeStandard.dao.SafeStandardDao;
import com.aptech.business.safeManage.safeStandard.domain.SafeStandardEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 安全标准化建设应用管理服务实现类
 *
 * @author 
 * @created 2018-04-02 13:58:10
 * @lastModified 
 * @history
 *
 */
@Service("safeStandardService")
@Transactional
public class SafeStandardServiceImpl extends AbstractBaseEntityOperation<SafeStandardEntity> implements SafeStandardService {
	
	@Autowired
	private SafeStandardDao safeStandardDao;
	
	/**
	 * 操作日志
	 */
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<SafeStandardEntity> getDao() {
		return safeStandardDao;
	}
	@Override
	public void addEntity(SafeStandardEntity t) {
//		t.setCreateDate(new Date());
//		t.setSignUnitName(sysUnitService.findById(Long.valueOf(t.getSignUnitId())).getName());
//		SysUserEntity userEntity = RequestContext.get().getUser();
//		t.setCreateUserId(userEntity.getId().toString());
//		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		
		JSONArray jSONArray=JSONArray.fromObject(t.getFileAddress());
		for (int i = 0; i < jSONArray.size(); i++) {
			JSONArray array=new JSONArray();
			JSONObject josnObject= (JSONObject) jSONArray.get(i);
			array.add(jSONArray.get(i));
			t.setFileName(String.valueOf(josnObject.get("name")));
			safeStandardDao.addEntity(t);
			SysUserEntity userEntity = RequestContext.get().getUser();
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.SAFESPRODUCTIONTANDARDCONSTRUCTION.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
		}
	}
	@Override
	public void updateEntity(SafeStandardEntity t) {
//		t.setCreateDate(new Date());
//		t.setSignUnitName(sysUnitService.findById(Long.valueOf(t.getSignUnitId())).getName());
//		SysUserEntity userEntity = RequestContext.get().getUser();
//		t.setCreateUserId(userEntity.getId().toString());
//		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		
		JSONArray jSONArray=JSONArray.fromObject(t.getFileAddress());
		for (int i = 0; i < jSONArray.size(); i++) {
			JSONArray array=new JSONArray();
			JSONObject josnObject= (JSONObject) jSONArray.get(i);
			array.add(jSONArray.get(i));
			t.setFileName(String.valueOf(josnObject.get("name")));
			safeStandardDao.updateEntity(t);
			SysUserEntity userEntity = RequestContext.get().getUser();
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.SAFESPRODUCTIONTANDARDCONSTRUCTION.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		}
	}
}