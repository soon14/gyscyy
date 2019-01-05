package com.aptech.business.technical.technicalWork.service;

import com.aptech.business.technical.technicalWork.domain.TechnicalWorkEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 技术监督工作表应用管理服务接口
 *
 * @author 
 * @created 2017-11-13 16:16:04
 * @lastModified 
 * @history
 *
 */
public interface TechnicalWorkService  extends IBaseEntityOperation<TechnicalWorkEntity> {

	ResultObj update(TechnicalWorkEntity t, Long id);
	
}