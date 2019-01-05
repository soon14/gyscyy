package com.aptech.business.safeStep.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeStep.domain.SafeStepEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全技术措施计划应用数据类
 *
 * @author 
 * @created 2018-09-14 14:40:53
 * @lastModified 
 * @history
 *
 */
@Repository("safeStepDao")
public class SafeStepDaoImpl extends AncestorDao<SafeStepEntity> implements SafeStepDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.safeStep";
	}
}
