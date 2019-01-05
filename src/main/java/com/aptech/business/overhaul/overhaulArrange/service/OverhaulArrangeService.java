package com.aptech.business.overhaul.overhaulArrange.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 工作安排应用管理服务接口
 *
 * @author 
 * @created 2017-12-20 16:24:01
 * @lastModified 
 * @history
 *
 */
public interface OverhaulArrangeService  extends IBaseEntityOperation<OverhaulArrangeEntity> {
	/**
	 * @Description:   修改
	 * @author         wangcc 
	 * @Date           2017年12月28日 下午5:41:49 
	 * @throws         Exception
	 */
	public ResultObj updEntity(OverhaulArrangeEntity overhaulArrangeEntity,Long id);
	/**
	 * 
	 * 工作安排提交
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
	public ResultObj aloneSubmit(Long overhaulLogId,Long overhaulArrangeId,String taskId,String procInstId,Map<String, Object> params);
	/**
	 * @Description:   批量删除
	 * @author         wangcc 
	 * @Date           2017年12月28日 下午5:41:49 
	 * @throws         Exception
	 */
	public ResultObj batchDelete(List<Long> ids);
	/**
	 * @Description:   单独删除
	 * @author         wangcc 
	 * @Date           2017年12月28日 下午5:41:49 
	 * @throws         Exception
	 */
	public ResultObj deleteOnlyOne(HttpServletRequest request, Long id);
	/**
	 * @Description:   修改并保存到历史
	 * @author         wangcc 
	 * @Date           2017年12月28日 下午5:41:49 
	 * @throws         Exception
	 */
	public ResultObj updEntityHis(OverhaulArrangeEntity overhaulArrangeEntity,Long id);
}