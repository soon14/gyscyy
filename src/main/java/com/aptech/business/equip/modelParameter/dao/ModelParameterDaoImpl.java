package com.aptech.business.equip.modelParameter.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.equip.modelParameter.domain.ModelParameterEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 模版参数应用数据类
 *
 * @author 
 * @created 2017-06-20 15:59:01
 * @lastModified 
 * @history
 *
 */
@Repository("modelParameterDao")
public class ModelParameterDaoImpl extends AncestorDao<ModelParameterEntity> implements ModelParameterDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.modelParameter";
	}
}
