package com.aptech.business.overhaul.overhaulWorkTask.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.business.overhaul.overhaulWorkTask.domain.OverhaulWorkTaskEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修工作任务应用管理服务接口
 *
 * @author 
 * @created 2017-12-22 10:15:21
 * @lastModified 
 * @history
 *
 */
public interface OverhaulWorkTaskService  extends IBaseEntityOperation<OverhaulWorkTaskEntity> {
	/**
	 * @Description:   检修日志-工作任务-新增
	 * @author         wangcc 
	 * @Date           2018年1月3日 上午10:50:32 
	 * @throws         Exception
	 */
	public @ResponseBody <T>  ResultObj overhaulWorkTaskAdd(OverhaulWorkTaskEntity overhaulWorkTaskEntity);
	/**
	 * @Description:   检修日志-工作任务-删除
	 * @author         wangcc 
	 * @Date           2018年1月3日 上午10:50:32 
	 * @throws         Exception
	 */
	public @ResponseBody <T>  ResultObj delete(HttpServletRequest request, @PathVariable Long id);
	/**
	 * @Description:   检修日志-工作任务-新增设备
	 * @author         wangcc 
	 * @Date           2018年1月3日 上午10:50:32 
	 * @throws         Exception
	 */
	public @ResponseBody <T>  ResultObj addEquipInfo(HttpServletRequest request,Map<String, Object> params, @PathVariable Long overhaulArrangeId);
	/**
	 * @Description:   检修日志-工作任务-取消勾选后删除数据
	 * @author         wangcc 
	 * @Date           2018年10月9日 上午09:30:32 
	 * @throws         Exception
	 */
	public @ResponseBody <T>  ResultObj deleteUnCheckDate(HttpServletRequest request,Map<String, Object> params);
}