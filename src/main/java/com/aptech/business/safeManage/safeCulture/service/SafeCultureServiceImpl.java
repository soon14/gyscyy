package com.aptech.business.safeManage.safeCulture.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.safeManage.safeCulture.dao.SafeCultureDao;
import com.aptech.business.safeManage.safeCulture.domain.SafeCultureEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.StringUtil;

/**
 * 
 * 安全文化应用管理服务实现类
 *
 * @author 
 * @created 2018-03-28 09:56:01
 * @lastModified 
 * @history
 *
 */
@Service("safeCultureService")
@Transactional
public class SafeCultureServiceImpl extends AbstractBaseEntityOperation<SafeCultureEntity> implements SafeCultureService {
	
	@Autowired
	private SafeCultureDao safeCultureDao;
	
	@Override
	public void addEntity(SafeCultureEntity t) {
//		t.setCreateDate(new Date());
//		t.setSignUnitName(sysUnitService.findById(Long.valueOf(t.getSignUnitId())).getName());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setUserId(userEntity.getId().toString());
//		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
			safeCultureDao.addEntity(t);
		}
		
	
	@Override
	public void updateEntity(SafeCultureEntity t) {
		if(StringUtil.isEmpty(t.getFileAddress())){
			safeCultureDao.updateEntity(t);
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
			safeCultureDao.updateEntity(t);
		}
		
	}

	@Override
	public IBaseEntityOperation<SafeCultureEntity> getDao() {
		return safeCultureDao;
	}
}