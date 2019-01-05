/*	
 * Web 5.0 Copyright 2016 Aptech, Co.ltd. All rights reserved.
 * 		 
 * FileName: SysRoleServiceImpl.java
 *
 */

package com.aptech.business.system.unit.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aptech.business.system.unit.dao.UnitDao;
import com.aptech.business.system.unit.domain.UnitEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.PropertyUtil;

/** 
 * 系统组织机构服务接口实现类
 *
 * @author zhangjx
 * @created 2016年11月14日 下午3:55:12 
 * @lastModified 
 * @history
 * 
 */
@Service("UnitService")
@Transactional
public class UnitServiceImpl extends AbstractBaseEntityOperation<UnitEntity> implements UnitService {

	@Autowired
	private UnitDao UnitDao;
	
	@Override
	public IBaseEntityOperation<UnitEntity> getDao() {
		return UnitDao;
	}
	
	@Override
	public void addEntity(UnitEntity t){
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		super.addEntity(t);
	}
	
	@Override
	public void updateEntity(UnitEntity t) {
		UnitEntity UnitEntity = UnitDao.findById(t.getId());
		PropertyUtil.copyProperties(UnitEntity, t);
		t.setDeleteFlag("N");
		super.updateEntity(t);
	}
	
	@Override
	public void deleteEntity(Serializable id) {
		UnitEntity UnitEntity = super.findById(id);
		UnitEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(UnitEntity);
	}
	
	@Override
	public List<UnitEntity> getUnitTreeNodeList() {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));	
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.NE, "3"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		List<UnitEntity> sysUnitEntities = this.findByCondition(conditions, null);
		return sysUnitEntities;
	}
	
	@Override
	public List<UnitEntity> getUnitTreeNodeListByLevel(int level) {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));	
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.INT, MatchTypeEnum.LE, level));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		List<UnitEntity> sysUnitEntities = this.findByCondition(conditions, null);
		return sysUnitEntities;
	}
	
	@Override
	public List<UnitEntity> getUnitComboxListByLevel(int level) {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));	
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.INT, MatchTypeEnum.EQ, level));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		List<UnitEntity> sysUnitEntities = this.findByCondition(conditions, null);
		return sysUnitEntities;
	}
	
	/**
	 * 递归查询树节点
	 */
	public List<UnitEntity> findSubUnitsFromList(List<UnitEntity> UnitEntityList, Long id) {
		List<UnitEntity> resultList = new ArrayList<UnitEntity>();
		if(UnitEntityList!=null){
			for (UnitEntity UnitEntity : UnitEntityList) {
				if(UnitEntity.getId().equals(id)){
					resultList.add(UnitEntity);
				}
				if (UnitEntity.getParentId().equals(id)) {
					resultList.add(UnitEntity);
					if (hasSub(UnitEntityList, UnitEntity.getId())) {
						resultList.addAll(findSubUnitsFromList(UnitEntityList, UnitEntity.getId()));
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 
	 * 查询节点有无子节点
	 * 
	 * @param @param sysMenuItemEntityList
	 * @param @param id
	 * @param @return
	 * @return boolean
	 * @throws 
	 * @author liweiran
	 * @created 2017年5月3日 下午7:46:29
	 * @lastModified
	 */
	private static boolean hasSub(List<UnitEntity> UnitEntityList, Long id) {
		boolean hasSub = false;
		for (UnitEntity UnitEntity : UnitEntityList) {
			if (UnitEntity.getParentId().equals(id)) {
				hasSub = true;
				break;
			}
		}
		return hasSub;
	}
}
