package com.aptech.business.defectManage.defect.service;

import java.io.Serializable;
import java.util.Map;

import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * ȱ�ݹ���Ӧ�ù������ӿ�
 *
 * @author 
 * @created 2017-06-02 13:18:00
 * @lastModified 
 * @history
 *
 */
public interface DefectService  extends IBaseEntityOperation<DefectEntity> {
	/**
	 * 添加实体
	 * 
	 * @param t
	 */
	ResultObj add(DefectEntity t);

	/**
	 * 修改实体
	 * 
	 * @param t
	 */
	ResultObj update(DefectEntity t);

	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	ResultObj delete(Serializable id);
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
	ResultObj approve(DefectEntity t,Map<String, Object> params);
	String findByEquipId(Long id);
}