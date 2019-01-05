package com.aptech.business.wareHouse.wareHouse.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 仓库管理应用数据类
 *
 * @author 
 * @created 2017-11-03 16:51:10
 * @lastModified 
 * @history
 *
 */
@Repository("wareHouseDao")
public class WareHouseDaoImpl extends AncestorDao<WareHouseEntity> implements WareHouseDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.wareHouse.wareHouse";
	}

	@Override
	public int findUnitTotal(Long unitId) {
		int selectList = sqlSession.selectOne("unitTotal", unitId);
		return selectList;
	}
}
