package com.aptech.business.overhaul.power.service;

import java.io.Serializable;
import java.util.Map;

import com.aptech.business.overhaul.power.domain.PowerEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 停送电管理应用管理服务接口
 *
 * @author 
 * @created 2017-07-31 14:17:20
 * @lastModified 
 * @history
 *
 */
public interface PowerService  extends IBaseEntityOperation<PowerEntity> {
	/**
	 * 添加实体
	 * 
	 * @param t
	 */
	ResultObj add(PowerEntity t);

	/**
	 * 修改实体
	 * 
	 * @param t
	 */
	ResultObj update(PowerEntity t);
	/**
	 * 提交之前校验
	 * 
	 * @param t
	 */
	ResultObj beforeSubmit(PowerEntity t);
	/**
	 * 提交实体
	 * 
	 * @param  id
	 */
	ResultObj submit(Serializable id, Map<String, Object> params);
	/**
	 * 审批：流程
	 * 
	 * @param  id
	 */
	ResultObj approve(PowerEntity t,Map<String, Object> params);
}