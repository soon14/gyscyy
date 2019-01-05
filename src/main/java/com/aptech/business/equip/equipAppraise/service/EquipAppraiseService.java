package com.aptech.business.equip.equipAppraise.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aptech.business.equip.equipAbnormalReport.domain.EquipAbnormalReportEntity;
import com.aptech.business.equip.equipAppraise.domain.EquipAppraiseEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 设备评价应用管理服务接口
 *
 * @author 
 * @created 2017-09-18 16:41:54
 * @lastModified 
 * @history
 *
 */
public interface EquipAppraiseService  extends IBaseEntityOperation<EquipAppraiseEntity> {
	/**
	 * 设备评级新增
	 * @param equipAppraise
	 * @param request
	 * @return
	 */
	public ResultObj addEquipAppraise(EquipAppraiseEntity equipAppraiseEntity,HttpServletRequest request);
	/**
	 * 设备评级修改
	 * @param equipAppraise
	 * @param request
	 * @return
	 */
	public ResultObj updateEquipAppraise(EquipAppraiseEntity equipAppraiseEntity,HttpServletRequest request);
	/**
	 * 设备评级删除(多选)
	 * @param equipAppraise
	 * @param request
	 * @return
	 */
	public ResultObj delSelectInfo(List<Long> ids);
	/**
	 * 设备评级删除
	 * @param equipAppraise
	 * @param request
	 * @return
	 */
	public ResultObj deleteOnlyOne(Long ids);
}