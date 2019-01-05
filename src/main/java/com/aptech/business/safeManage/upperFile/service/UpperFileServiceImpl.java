package com.aptech.business.safeManage.upperFile.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.UpperFileTypeEnum;
import com.aptech.business.safeManage.organization.domain.OrganizationEntity;
import com.aptech.business.safeManage.targetManage.domain.TargetManageEntity;
import com.aptech.business.safeManage.upperFile.dao.UpperFileDao;
import com.aptech.business.safeManage.upperFile.domain.UpperFileEntity;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.IdUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 上级文件应用管理服务实现类
 *
 * @author 
 * @created 2018-03-28 10:18:07
 * @lastModified 
 * @history
 *
 */
@Service("upperFileService")
@Transactional
public class UpperFileServiceImpl extends AbstractBaseEntityOperation<UpperFileEntity> implements UpperFileService {
	
	@Autowired
	private UpperFileDao upperFileDao;
	@Autowired
	private SysUnitService sysUnitService;
	@Override
	public IBaseEntityOperation<UpperFileEntity> getDao() {
		return upperFileDao;
	}
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	@Override
	public void addEntity(UpperFileEntity t) {
		t.setCreateDate(new Date());
//		t.setUnitName(sysUnitService.findById(Long.valueOf(t.getUnitId())).getName());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		
		
//		JSONArray jSONArray=JSONArray.fromObject(t.getFileId());
//			
//			for (int i = 0; i < jSONArray.size(); i++) {
//				JSONArray array=new JSONArray();
//				JSONObject josnObject= (JSONObject) jSONArray.get(i);
//				array.add(jSONArray.get(i));
//				t.setName(String.valueOf(josnObject.get("name")));
////				t.setFileId(array.toString());
//			}
			upperFileDao.addEntity(t);
			
//	     if (t.getDocId()!=null) {
//				
//	        	JSONArray jSONArray2=JSONArray.fromObject(t.getDocId());
//	        	for (int i = 0; i < jSONArray2.size(); i++) {
//	        		JSONArray array=new JSONArray();
//	        		JSONObject josnObject= (JSONObject) jSONArray2.get(i);
//	        		array.add(jSONArray2.get(i));
//	        		t.setDocName(String.valueOf(josnObject.get("name")));
////	        		t.setDocId(array.toString());
//	        		upperFileDao.updateEntity(t);
//	        	}
//			}
	        
//	        if (t.getSpiritDocId()!=null) {
//	        	
//	        	JSONArray jSONArray3=JSONArray.fromObject(t.getSpiritDocId());
//	        	for (int i = 0; i < jSONArray3.size(); i++) {
//	        		JSONArray array=new JSONArray();
//	        		JSONObject josnObject= (JSONObject) jSONArray3.get(i);
//	        		array.add(jSONArray3.get(i));
//	        		t.setSpiritName(String.valueOf(josnObject.get("name")));
////	        		t.setSpiritDocId(array.toString());
//	        		upperFileDao.updateEntity(t);
//	        	}
//	        }
	}
	
	@Override
	public void updateEntity(UpperFileEntity t) {
		
		
		
//		JSONArray jSONArray=JSONArray.fromObject(t.getFileId());
//		for (int i = 0; i < jSONArray.size(); i++) {
//			JSONArray array=new JSONArray();
//			JSONObject josnObject= (JSONObject) jSONArray.get(i);
//			array.add(jSONArray.get(i));
//			t.setName(String.valueOf(josnObject.get("name")));
////			t.setFileId(array.toString());
//			upperFileDao.updateEntity(t);
//		}
		
//		JSONArray jSONArray2=JSONArray.fromObject(t.getDocId());
//		for (int i = 0; i < jSONArray2.size(); i++) {
//			JSONArray array=new JSONArray();
//			JSONObject josnObject= (JSONObject) jSONArray2.get(i);
//			array.add(jSONArray2.get(i));
//			t.setDocName(String.valueOf(josnObject.get("name")));
//			upperFileDao.updateEntity(t);
//		}
//		JSONArray jSONArray1=JSONArray.fromObject(t.getSpiritDocId());
//		for (int i = 0; i < jSONArray1.size(); i++) {
//			JSONArray array=new JSONArray();
//			JSONObject josnObject= (JSONObject) jSONArray1.get(i);
//			array.add(jSONArray1.get(i));
//			t.setSpiritName(String.valueOf(josnObject.get("name")));
////			t.setSpiritDocId(array.toString());
//			upperFileDao.updateEntity(t);
//		}
		upperFileDao.updateEntity(t);
	}

	@Override
	public ResultObj add(UpperFileEntity t) {
		// TODO Auto-generated method stub
		t.setUnitName(sysUnitService.findById(t.getUnitId()).getName());
		t.setType(Long.valueOf(UpperFileTypeEnum.TYPE3.getId()));
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		upperFileDao.addEntity(t);
		return new ResultObj();
	}
}