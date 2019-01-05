package com.aptech.business.equip.equipAbnormalReport.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.equip.equipAbnormalReport.domain.EquipAbnormalReportEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 设备异动报告应用管理服务接口
 *
 * @author 
 * @created 2018-09-14 13:48:29
 * @lastModified 
 * @history
 *
 */
public interface EquipAbnormalReportService  extends IBaseEntityOperation<EquipAbnormalReportEntity> {
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
	 * @created 2018年9月18日 下午11:13:36
	 * @lastModified
	 */
	public ResultObj submit(Serializable id, Map<String, Object> params);
	/**
	 * 设备异动报告新增
	 * @param equipAbnormalReportEntity
	 * @param request
	 * @return
	 */
	public ResultObj addEquipAbnormalReport(EquipAbnormalReportEntity equipAbnormalReportEntity,HttpServletRequest request);
	/**
	 * 设备异动报告修改
	 * @param equipAbnormalReportEntity
	 * @param request
	 * @return
	 */
	public ResultObj updateEquipAbnormalReport(EquipAbnormalReportEntity equipAbnormalReportEntity,HttpServletRequest request);
	/**
	 * 设备异动报告修改
	 * @param id
	 * @param request
	 * @return
	 */
	public ResultObj deleteOnlyOne(HttpServletRequest request,Long id);
	/**
	 * 设备异动报告修改
	 * @param ids
	 * @param request
	 * @return
	 */
	public ResultObj delSelectInfo(List<Long> ids);
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
	public ResultObj approve(EquipAbnormalReportEntity equipAbnormalReportEntity,Map<String, Object> params);
}