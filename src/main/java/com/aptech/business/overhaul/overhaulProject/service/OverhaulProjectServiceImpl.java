package com.aptech.business.overhaul.overhaulProject.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.overhaul.overhaulPlanMember.domain.OverhaulPlanMemberEntity;
import com.aptech.business.overhaul.overhaulProject.dao.OverhaulProjectDao;
import com.aptech.business.overhaul.overhaulProject.domain.OverhaulProjectEntity;
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

/**
 * 
 * 检修项目应用管理服务实现类
 *
 * @author 
 * @created 2017-06-12 18:48:28
 * @lastModified 
 * @history
 *
 */
@Service("overhaulProjectService")
@Transactional
public class OverhaulProjectServiceImpl extends AbstractBaseEntityOperation<OverhaulProjectEntity> implements OverhaulProjectService {
	
	@Autowired
	private OverhaulProjectDao overhaulProjectDao;
	
	@Override
	public IBaseEntityOperation<OverhaulProjectEntity> getDao() {
		return overhaulProjectDao;
	}
	@SuppressWarnings("unchecked")
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.asc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		long overhualPlanId = Long.valueOf((int)params.get("overhualPlanId"));
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_OVERHUAL_PLAN_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, overhualPlanId));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteEntity(Serializable id){
		OverhaulProjectEntity overhaulProjectEntity  =this.findById(id);
		overhaulProjectEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
		this.updateEntity(overhaulProjectEntity);
	}
	
	@Override
	public void addEntity(OverhaulProjectEntity t) {
		t.setCreateDate(new Date());
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		overhaulProjectDao.addEntity(t);
	}
//	@Override
//	public void updateEntity(OverhaulProjectEntity t) {
//		OverhaulProjectEntity entity = this.findById(t.getId());
//	
//			t.setCreateDate(entity.getCreateDate());
//			t.setStatus(entity.getStatus());
//			super.updateEntity(t);
//		
//	}
	@Override
	public void deleteEntityByPlanId(long overhaulPlanId) {
		// TODO Auto-generated method stub
		List<Condition> conditions = new ArrayList<Condition>();
		
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_OVERHUAL_PLAN_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, overhaulPlanId));
		List<OverhaulProjectEntity> resultList = this.findByCondition(conditions, null);
		if(resultList!=null && resultList.size()>0){
			for(OverhaulProjectEntity entity:resultList){
				this.deleteEntity(entity.getId());
			}
		}
		
	}
}