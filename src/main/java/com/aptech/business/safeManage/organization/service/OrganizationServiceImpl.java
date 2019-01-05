package com.aptech.business.safeManage.organization.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.safeManage.organization.dao.OrganizationDao;
import com.aptech.business.safeManage.organization.domain.OrganizationEntity;
import com.aptech.business.safeManage.targetManage.domain.TargetManageEntity;
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

/**
 * 
 * 组织机构应用管理服务实现类
 *
 * @author 
 * @created 2018-03-27 16:31:43
 * @lastModified 
 * @history
 *
 */
@Service("organizationService")
@Transactional
public class OrganizationServiceImpl extends AbstractBaseEntityOperation<OrganizationEntity> implements OrganizationService {
	
	@Autowired
	private OrganizationDao organizationDao;
	@Autowired
	private SysUnitService sysUnitService;
	
	@Override
	public IBaseEntityOperation<OrganizationEntity> getDao() {
		return organizationDao;
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
	public void addEntity(OrganizationEntity t) {
		if (t.getUnitId()!=null) {
			t.setUnitName(sysUnitService.findById(Long.valueOf(t.getUnitId())).getName());
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		
//		JSONArray jSONArray=JSONArray.fromObject(t.getFileId());
//
//		   for (int i = 0; i < jSONArray.size(); i++) {
//			   JSONArray array=new JSONArray();
//			   JSONObject josnObject= (JSONObject) jSONArray.get(i);
//			   array.add(jSONArray.get(i));
//			   t.setName(String.valueOf(josnObject.get("name")));
//			   t.setCode(IdUtil.creatUUID());
////			   t.setFileId(array.toString());
//		   }
		   organizationDao.addEntity(t);
 }
}