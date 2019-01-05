/*	
 * Web 5.0 Copyright 2016 Aptech, Co.ltd. All rights reserved.
 * 		 
 * FileName: SysRoleService.java
 *
 */

package com.aptech.business.system.unit.service;

import java.util.List;

import com.aptech.business.system.unit.domain.UnitEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/** 
 * 系统组织机构服务接口
 *
 * @author zhangjx
 * @created 2016年11月14日 下午3:54:59 
 * @lastModified 
 * @history
 * 
 */
public interface UnitService  extends IBaseEntityOperation<UnitEntity> {
	/**
     * 获取treeNode
     * @param id
     * id is menu id
     * @return
     */
    public List<UnitEntity> getUnitTreeNodeList();
    
    public List<UnitEntity> findSubUnitsFromList(List<UnitEntity> UnitEntityList, Long id);

	public List<UnitEntity> getUnitTreeNodeListByLevel(int level);

	public List<UnitEntity> getUnitComboxListByLevel(int level);
}
