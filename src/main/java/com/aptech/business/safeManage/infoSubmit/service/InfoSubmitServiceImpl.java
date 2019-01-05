package com.aptech.business.safeManage.infoSubmit.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.infoSubmit.dao.InfoSubmitDao;
import com.aptech.business.safeManage.infoSubmit.domain.InfoSubmitEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.StringUtil;

/**
 * 
 * 信息报送应用管理服务实现类
 *
 * @author 
 * @created 2018-03-28 18:05:15
 * @lastModified 
 * @history
 *
 */
@Service("infoSubmitService")
@Transactional
public class InfoSubmitServiceImpl extends AbstractBaseEntityOperation<InfoSubmitEntity> implements InfoSubmitService {
	
	@Autowired
	private InfoSubmitDao infoSubmitDao;
	
	/**
	 * 操作日志
	 */
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public void addEntity(InfoSubmitEntity t) {
		if(StringUtil.isEmpty(t.getFileAddress())){
			infoSubmitDao.addEntity(t);
		}else{
			JSONArray jSONArray=JSONArray.fromObject(t.getFileAddress());
			String fileName="";
			for (int i = 0; i < jSONArray.size(); i++) {
				JSONArray array=new JSONArray();
				JSONObject josnObject= (JSONObject) jSONArray.get(i);
				array.add(jSONArray.get(i));
				fileName+=String.valueOf(josnObject.get("name"));
				if(fileName.length()>30){
					fileName+="...";
					break;
				}
				if(i<jSONArray.size()-1){
					fileName+=",";
				}else{
					break;
				}
			}
			t.setFileName(fileName);
		}
		infoSubmitDao.addEntity(t);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.INFORMATIONSUBMISSION.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
		
	}
	
	@Override
	public void updateEntity(InfoSubmitEntity t) {
		if(StringUtil.isEmpty(t.getFileAddress())){
			infoSubmitDao.updateEntity(t);
		}else{
			JSONArray jSONArray=JSONArray.fromObject(t.getFileAddress());
			String fileName="";
			for (int i = 0; i < jSONArray.size(); i++) {
				JSONArray array=new JSONArray();
				JSONObject josnObject= (JSONObject) jSONArray.get(i);
				array.add(jSONArray.get(i));
				fileName+=String.valueOf(josnObject.get("name"));
				if(fileName.length()>30){
					fileName+="...";
					break;
				}
				if(i<jSONArray.size()-1){
					fileName+=",";
				}else{
					break;
				}
			}
			t.setFileName(fileName);
			infoSubmitDao.updateEntity(t);
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.INFORMATIONSUBMISSION.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
	}
	
	
	@Override
	public IBaseEntityOperation<InfoSubmitEntity> getDao() {
		return infoSubmitDao;
	}
}