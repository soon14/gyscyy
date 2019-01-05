package com.aptech.business.overhaul.overhaulProject.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.overhaulProject.domain.OverhaulProjectEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 检修项目应用数据类
 *
 * @author 
 * @created 2017-06-12 18:48:28
 * @lastModified 
 * @history
 *
 */
@Repository("overhaulProjectDao")
public class OverhaulProjectDaoImpl extends AncestorDao<OverhaulProjectEntity> implements OverhaulProjectDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.overhaul.overhaulProject";
	}
}
