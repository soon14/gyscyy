package com.aptech.business.overhaul.overhaulWork.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.overhaulWork.domain.OverhaulWorkEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 检修工作应用数据类
 *
 * @author 
 * @created 2017-08-11 09:27:00
 * @lastModified 
 * @history
 *
 */
@Repository("overhaulWorkDao")
public class OverhaulWorkDaoImpl extends AncestorDao<OverhaulWorkEntity> implements OverhaulWorkDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.overhaulWork";
	}
}
