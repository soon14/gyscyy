package com.aptech.business.wareHouse.wareHouse.service;

import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 仓库管理应用管理服务接口
 *
 * @author 
 * @created 2017-11-03 16:51:10
 * @lastModified 
 * @history
 *
 */
public interface WareHouseService  extends IBaseEntityOperation<WareHouseEntity> {
	
	/**
	 * 
	 * 获取仓库类型
	 * 
	 * @param @return
	 * @return ComboboxVO
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月3日 下午6:28:19
	 * @lastModified
	 */
	public ComboboxVO getWareHouseType();
	
	/**
	 * 
	 * 获取库管员
	 * 
	 * @param @return
	 * @return ComboboxVO
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月3日 下午6:29:45
	 * @lastModified
	 */
	public ComboboxVO getStoreKeeper();
	
	/**
	 * 
	 * 获取启停用
	 * 
	 * @param @return
	 * @return ComboboxVO
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月3日 下午6:31:00
	 * @lastModified
	 */
	public ComboboxVO getStatus();
	
	/**
	 * 
	 * 获取父仓库
	 * 
	 * @param @return
	 * @return ComboboxVO
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月3日 下午7:04:43
	 * @lastModified
	 */
	public ComboboxVO getParentWareHouse();
	
	/**
	 * 
	 * 查询所选场站启用状态条数
	 * 
	 * @param @param farmId
	 * @param @return
	 * @return int
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月4日 下午2:00:49
	 * @lastModified
	 */
	public int findUnitTotal(Long unitId);
}