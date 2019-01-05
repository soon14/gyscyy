package com.aptech.business.safeCheckProblem.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeCheckProblem.domain.SafeCheckProblemEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全检查问题应用数据类
 *
 * @author 
 * @created 2018-09-03 10:31:57
 * @lastModified 
 * @history
 *
 */
@Repository("safeCheckProblemDao")
public class SafeCheckProblemDaoImpl extends AncestorDao<SafeCheckProblemEntity> implements SafeCheckProblemDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.safeCheckProblem";
	}
}
