package com.aptech.business.overhaul.overhaulLog.service;

import java.util.List;

import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
import com.aptech.business.overhaul.power.domain.PowerEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修日志应用管理服务接口
 *
 * @author 
 * @created 2017-06-30 18:28:04
 * @lastModified 
 * @history
 *
 */
public interface OverhaulLogService  extends IBaseEntityOperation<OverhaulLogEntity> {
	/**
	 * @Description:   修改
	 * @author         wangcc 
	 * @Date           2017年12月28日 下午5:41:49 
	 * @throws         Exception
	 */
	public ResultObj updEntity(OverhaulLogEntity overhaulLogEntity);
	/**
	 * @Description:   处理负责人、完成状态
	 * @author         wangcc 
	 * @Date           2017年12月28日 下午5:41:49 
	 * @throws         Exception
	 */
	public OverhaulLogEntity executeSomeThing(OverhaulLogEntity overhaulLogEntity);
	/**
	 * @Description:   批量删除
	 * @author         wangcc 
	 * @Date           2017年12月28日 下午5:41:49 
	 * @throws         Exception
	 */
	public ResultObj batchDelete(List<Long> ids);
	/**
	 * @Description:   删除
	 * @author         wangcc 
	 * @Date           2017年12月28日 下午5:41:49 
	 * @throws         Exception
	 */
	public ResultObj deleteOnlyOne(Long id);
	
	//添加验证
	public ResultObj addValidate(OverhaulLogEntity t);
	

}