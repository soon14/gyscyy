package com.aptech.business.overhaul.overhaulWork.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.overhaul.overhaulWork.dao.OverhaulWorkDao;
import com.aptech.business.overhaul.overhaulWork.domain.OverhaulWorkEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;

/**
 * 
 * 检修工作应用管理服务实现类
 *
 * @author 
 * @created 2017-08-11 09:27:00
 * @lastModified 
 * @history
 *
 */
@Service("overhaulWorkService")
@Transactional
public class OverhaulWorkServiceImpl extends AbstractBaseEntityOperation<OverhaulWorkEntity> implements OverhaulWorkService {
	
	@Autowired
	private OverhaulWorkDao overhaulWorkDao;
	
	@Override
	public IBaseEntityOperation<OverhaulWorkEntity> getDao() {
		return overhaulWorkDao;
	}
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		long overhualId = Long.valueOf((int)params.get("overhualLogId"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_OVERHAUL", FieldTypeEnum.LONG, MatchTypeEnum.EQ, overhualId));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}

	@Override
	public void deleteEntityByLogId(long overhaulLogId) {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_OVERHAUL", FieldTypeEnum.LONG, MatchTypeEnum.EQ, overhaulLogId));
		List<OverhaulWorkEntity> list = super.findByCondition(conditions, null);
		if(list!=null && !list.isEmpty()){
			for(OverhaulWorkEntity overhaulWorkEntity:list){
				this.deleteEntity(overhaulWorkEntity.getId());
			}
		}		
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Override
	public void deleteEntity(Serializable id){
		OverhaulWorkEntity overhaulWorkEntity  =this.findById(id);
		overhaulWorkEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(overhaulWorkEntity);
	}
	
	@Override
	public void updateEntity(OverhaulWorkEntity t) {
		OverhaulWorkEntity entity = this.findById(t.getId());
		entity.setCheckUserId(t.getCheckUserId());
		entity.setCheckUserName(t.getCheckUserName());
		entity.setDutyUserId(t.getDutyUserId());
		entity.setDutyUserName(t.getDutyUserName());
		entity.setWork(t.getWork());
		super.updateEntity(entity);
	}
	
	@Override
	public void addEntity(OverhaulWorkEntity t) {
		t.setCreateDate(new Date());
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		super.addEntity(t);
	}
}