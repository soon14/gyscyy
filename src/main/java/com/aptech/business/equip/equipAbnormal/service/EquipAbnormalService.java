package com.aptech.business.equip.equipAbnormal.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.aptech.business.equip.equipAbnormal.domain.EquipAbnormalEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 设备异动应用管理服务接口
 *
 * @author 
 * @created 2017-06-22 14:52:35
 * @lastModified 
 * @history
 *
 */
public interface EquipAbnormalService  extends IBaseEntityOperation<EquipAbnormalEntity> {
	/**
	 * 
	 * 提交实体，开始流程
	 * 
	 * @param @param id
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangcc
	 * @created 2017年8月17日 下午1:21:36
	 * @lastModified
	 */
	public ResultObj submit(Serializable id, Map<String, Object> params);
	
	/**
	 * 
	 * 继续下一布流程
	 * 
	 * @param @param t
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月18日 下午1:21:36
	 * @lastModified
	 */
	public ResultObj approve(EquipAbnormalEntity equipAbnormalEntity,Map<String, Object> params);
	/**
	 * 
	 * 新增
	 * 
	 * @param @param t
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangcc
	 * @created 2018年9月10日 下午17:45:36
	 * @lastModified
	 */
	public ResultObj addEquipAbnormal(EquipAbnormalEntity equipAbnormalEntity);
	/**
	 * 
	 * 修改
	 * 
	 * @param @param t
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangcc
	 * @created 2018年9月10日 下午17:45:36
	 * @lastModified
	 */
	public ResultObj updateEquipAbnormal(EquipAbnormalEntity equipAbnormalEntity);
	/**
	 * 
	 * 删除(多选)
	 * 
	 * @param @param ids
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangcc
	 * @created 2018年9月25日 下午17:45:36
	 * @lastModified
	 */
	public ResultObj deleteMore(List<Integer> ids);
	/**
	 * 
	 * 删除
	 * 
	 * @param @param id
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangcc
	 * @created 2018年9月25日 下午17:45:36
	 * @lastModified
	 */
	public ResultObj deleteEquipAbnormal(Long id);
	
	
}