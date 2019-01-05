/*	
 * Web 5.0 Copyright 2016 Aptech, Co.ltd. All rights reserved.
 * 		 
 * FileName: SysRoleDaoImpl.java
 *
 */

package com.aptech.business.system.unit.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.system.unit.domain.UnitEntity;
import com.aptech.framework.orm.AncestorDao;

/** 
 * 系统管理组织机构操作接口实现类
 *
 * @author zhangjx
 * @created 2016年11月14日 下午3:55:43 
 * @lastModified 
 * @history
 * 
 */

@Repository("UnitDao")
public class UnitDaoImpl  extends AncestorDao<UnitEntity> implements UnitDao {

	/* (非 Javadoc) 
	 * <p>Title: getNameSpace</p> 
	 * <p>Description: </p> 
	 * @return 
	 * @see com.aptech.framework.orm.AncestorDao#getNameSpace() 
	 */
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.unit";
	}

}
