package com.aptech.business.safeManage.accident.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.accident.domain.AccidentEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 技术监督总结应用数据类
 *
 * @author 
 * @created 2018-04-02 09:02:22
 * @lastModified 
 * @history
 *
 */
@Repository("accidentDao")
public class AccidentImpl extends AncestorDao<AccidentEntity> implements AccidentDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.safeManage.accident";
	}
}
