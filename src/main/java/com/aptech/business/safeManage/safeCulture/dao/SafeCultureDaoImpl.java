package com.aptech.business.safeManage.safeCulture.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.safeCulture.domain.SafeCultureEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全文化应用数据类
 *
 * @author 
 * @created 2018-03-28 09:56:01
 * @lastModified 
 * @history
 *
 */
@Repository("safeCultureDao")
public class SafeCultureDaoImpl extends AncestorDao<SafeCultureEntity> implements SafeCultureDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.safeCultureActivity.safeCulture";
	}
}
