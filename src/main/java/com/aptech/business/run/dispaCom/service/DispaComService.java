package com.aptech.business.run.dispaCom.service;

import java.util.List;
import java.util.Map;

import com.aptech.business.run.dispaCom.domain.DispaComEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 调度命令应用管理服务接口
 *
 * @author 
 * @created 2017-06-07 11:31:01
 * @lastModified 
 * @history
 *
 */
public interface DispaComService  extends IBaseEntityOperation<DispaComEntity> {
	/**
	 * 
	 * 根据dispaComEntity属性查找实体的集合
	 * 
	 * @param @param time
	 * @param @return
	 * @return List<DispaComEntity>
	 * @throws 
	 * @author sunliang
	 * @created 2017年6月7日 上午10:42:45
	 * @lastModified
	 */
	public List<DispaComEntity> finddispaComMapByCondition(String startTime,String endTime);
	/**
	 * @Description:   调度新增
	 * @author         wangcc 
	 * @Date           2017年12月12日 上午10:32:51 
	 * @throws         Exception
	 */
	public ResultObj add(DispaComEntity dispaComEntity);
	/**
	 * @Description:   验证是否可以新增
	 * @author         wangcc 
	 * @Date           2017年12月12日 上午10:32:51 
	 * @throws         Exception
	 */
	public Object isAdd();
	public List<DispaComEntity> searchDis(Map<String, Object> params);
}