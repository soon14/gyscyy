package com.aptech.business.technical.technicalPlandetail.service;

import com.aptech.business.technical.technicalPlandetail.domain.TechnicalPlandetailEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 技术监督详细计划应用管理服务接口
 *
 * @author 
 * @created 2017-11-13 16:16:12
 * @lastModified 
 * @history
 *
 */
public interface TechnicalPlandetailService  extends IBaseEntityOperation<TechnicalPlandetailEntity> {

	ResultObj update(TechnicalPlandetailEntity t, Long id);
	
}