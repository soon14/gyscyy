package com.aptech.business.safeManage.safeActivity.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.safeManage.safeActivity.dao.SafeActivityDao;
import com.aptech.business.safeManage.safeActivity.domain.SafeActivityEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.StringUtil;

/**
 * 
 * 安全活动应用管理服务实现类
 *
 * @author 
 * @created 2018-03-28 16:39:53
 * @lastModified 
 * @history
 *
 */
@Service("safeActivityService")
@Transactional
public class SafeActivityServiceImpl extends AbstractBaseEntityOperation<SafeActivityEntity> implements SafeActivityService {
	
	@Autowired
	private SafeActivityDao safeActivityDao;
	
	@Override
	public void addEntity(SafeActivityEntity t) {
			safeActivityDao.addEntity(t);
		
	}
	
	@Override
	public void updateEntity(SafeActivityEntity t) {
		if(StringUtil.isEmpty(t.getFileAddress())){
			safeActivityDao.updateEntity(t);
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
			safeActivityDao.updateEntity(t);
		}
		
	}
	
	@Override
	public IBaseEntityOperation<SafeActivityEntity> getDao() {
		return safeActivityDao;
	}
}