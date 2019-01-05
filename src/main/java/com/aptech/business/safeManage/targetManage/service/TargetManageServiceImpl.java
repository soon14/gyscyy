package com.aptech.business.safeManage.targetManage.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.safeManage.safeSending.domain.SafeSendingEntity;
import com.aptech.business.safeManage.targetManage.dao.TargetManageDao;
import com.aptech.business.safeManage.targetManage.domain.TargetManageEntity;
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
 * 目标管理应用管理服务实现类
 *
 * @author 
 * @created 2018-03-23 14:32:07
 * @lastModified 
 * @history
 *
 */
@Service("targetManageService")
@Transactional
public class TargetManageServiceImpl extends AbstractBaseEntityOperation<TargetManageEntity> implements TargetManageService {
	
	@Autowired
	private TargetManageDao targetManageDao;
	@Autowired
	private SysUnitService sysUnitService;
	
	@Override
	public IBaseEntityOperation<TargetManageEntity> getDao() {
		return targetManageDao;
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
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	@Override
	public void addEntity(TargetManageEntity t) {
		t.setCreateDate(new Date());
//		t.setSignUnitName(sysUnitService.findById(Long.valueOf(t.getSignUnitId())).getName());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		t.setCode(IdUtil.creatUUID());
			targetManageDao.addEntity(t);
	}

	@Override
	public ResultObj addPro(TargetManageEntity t) {
		// TODO Auto-generated method stub
		t.setCreateDate(new Date());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		t.setCode(IdUtil.creatUUID());
		SysUnitEntity sysUnitEntity = sysUnitService.findById(t.getSignUnitId());
		t.setSignUnitName(sysUnitEntity.getName());
		targetManageDao.addEntity(t);
		return new ResultObj();
	}

	
}