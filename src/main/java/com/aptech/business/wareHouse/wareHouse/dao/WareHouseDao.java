package com.aptech.business.wareHouse.wareHouse.dao;

import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 仓库管理应用数据接口
 *
 * @author 
 * @created 2017-11-03 16:51:10
 * @lastModified 
 * @history
 *
 */
public interface WareHouseDao  extends IBaseEntityOperation<WareHouseEntity>{
	
	/**
	 * 
	 * 查询所选场站启用状态条数
	 * 
	 * @param @param unitId
	 * @param @return
	 * @return int
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月4日 下午1:57:37
	 * @lastModified
	 */
	public int findUnitTotal(Long unitId);
}