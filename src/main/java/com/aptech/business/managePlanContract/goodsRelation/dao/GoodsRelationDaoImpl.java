package com.aptech.business.managePlanContract.goodsRelation.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.managePlanContract.goodsRelation.domain.GoodsRelationEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 物资关联应用数据类
 *
 * @author 
 * @created 2018-04-19 09:23:45
 * @lastModified 
 * @history
 *
 */
@Repository("goodsRelationDao")
public class GoodsRelationDaoImpl extends AncestorDao<GoodsRelationEntity> implements GoodsRelationDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.goodsRelation";
	}
}
