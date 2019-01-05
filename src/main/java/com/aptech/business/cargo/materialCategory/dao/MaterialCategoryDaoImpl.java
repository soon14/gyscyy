package com.aptech.business.cargo.materialCategory.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 物资类别应用数据类
 *
 * @author 
 * @created 2017-07-14 11:59:28
 * @lastModified 
 * @history
 *
 */
@Repository("materialCategoryDao")
public class MaterialCategoryDaoImpl extends AncestorDao<MaterialCategoryEntity> implements MaterialCategoryDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.cargo.materialCategory";
	}
}
