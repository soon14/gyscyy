package com.aptech.business.overhaul.overhaulSafe.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.overhaul.overhaulSafe.dao.OverhaulSafeDao;
import com.aptech.business.overhaul.overhaulSafe.domain.OverhaulSafeEntity;
import com.aptech.common.system.unit.service.SysUnitService;
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
 * 安全交底应用管理服务实现类
 *
 * @author 
 * @created 2017-08-11 09:28:01
 * @lastModified 
 * @history
 *
 */
@Service("overhaulSafeService")
@Transactional
public class OverhaulSafeServiceImpl extends AbstractBaseEntityOperation<OverhaulSafeEntity> implements OverhaulSafeService {
	
	@Autowired
	private OverhaulSafeDao overhaulSafeDao;
	@Autowired
	private SysUnitService sysUnitService;
	@Override
	public IBaseEntityOperation<OverhaulSafeEntity> getDao() {
		return overhaulSafeDao;
	}
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		
   		long overhualId = Long.valueOf((int)params.get("overhaulRecordId"));
   		String logType = (String)params.get("logType");

		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_OVERHAUL_RECORD_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, overhualId));
   		if(logType!=null && logType!=""){
   			conditions.add(new Condition("C_LOG_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, logType));
   		}
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	@Override
	public void deleteEntityByLogId(long overhaulLogId) {
		// TODO Auto-generated method stub
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_OVERHAUL_ARRANGE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, overhaulLogId));
		List<OverhaulSafeEntity> list = super.findByCondition(conditions, null);
		if(list!=null && !list.isEmpty()){
			for(OverhaulSafeEntity overhaulSafeEntity:list){
				this.deleteEntity(overhaulSafeEntity.getId());
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
		OverhaulSafeEntity overhaulSafeEntity  =this.findById(id);
		overhaulSafeEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(overhaulSafeEntity);
	}
	
	@Override
	public void updateEntity(OverhaulSafeEntity t) {
		OverhaulSafeEntity entity = this.findById(t.getId());
		entity.setDanger(t.getDanger());
		entity.setWayCheck(t.getWayCheck());
		super.updateEntity(entity);
	}
	
	@Override
	public void addEntity(OverhaulSafeEntity t) {
		t.setCreateDate(new Date());
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		super.addEntity(t);
	}
	@Override
	public void update(OverhaulSafeEntity overhaulSafeEntity) {
		super.updateEntity(overhaulSafeEntity);
	}
	
}