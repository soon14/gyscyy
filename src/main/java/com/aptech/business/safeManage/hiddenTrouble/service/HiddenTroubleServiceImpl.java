package com.aptech.business.safeManage.hiddenTrouble.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.safeManage.hiddenTrouble.dao.HiddenTroubleDao;
import com.aptech.business.safeManage.hiddenTrouble.domain.HiddenTroubleEntity;
import com.aptech.business.safeManage.organization.domain.OrganizationEntity;
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

/**
 * 
 * 隐患排查应用管理服务实现类
 *
 * @author 
 * @created 2018-03-31 12:52:23
 * @lastModified 
 * @history
 *
 */
@Service("hiddenTroubleService")
@Transactional
public class HiddenTroubleServiceImpl extends AbstractBaseEntityOperation<HiddenTroubleEntity> implements HiddenTroubleService {
	
	@Autowired
	private HiddenTroubleDao hiddenTroubleDao;
	
	@Override
	public IBaseEntityOperation<HiddenTroubleEntity> getDao() {
		return hiddenTroubleDao;
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
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	@Override
	public void addEntity(HiddenTroubleEntity t) {
		t.setCreateDate(new Date());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		
//		JSONArray jSONArray=JSONArray.fromObject(t.getFileId());
//	
//			for (int i = 0; i < jSONArray.size(); i++) {
//				JSONArray array=new JSONArray();
//				JSONObject josnObject= (JSONObject) jSONArray.get(i);
//				array.add(jSONArray.get(i));
//				t.setFileName(String.valueOf(josnObject.get("name")));
//			
//		}
		hiddenTroubleDao.addEntity(t);
 }
	
	@Override
	public void updateEntity(HiddenTroubleEntity t) {
		
		hiddenTroubleDao.updateEntity(t);
//		JSONArray jSONArray=JSONArray.fromObject(t.getFileId());
//		for (int i = 0; i < jSONArray.size(); i++) {
//			JSONArray array=new JSONArray();
//			JSONObject josnObject= (JSONObject) jSONArray.get(i);
//			array.add(jSONArray.get(i));
//			t.setFileName(String.valueOf(josnObject.get("name")));
//			hiddenTroubleDao.updateEntity(t);
//		}
	}
}