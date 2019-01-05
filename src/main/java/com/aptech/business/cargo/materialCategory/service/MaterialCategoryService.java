package com.aptech.business.cargo.materialCategory.service;

import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 物资类别应用管理服务接口
 *
 * @author 
 * @created 2017-07-14 11:59:28
 * @lastModified 
 * @history
 *
 */
public interface MaterialCategoryService  extends IBaseEntityOperation<MaterialCategoryEntity> {
	
	/**
	 * 
	 * 生成物资类别编码
	 * 
	 * @param @param prefix
	 * @param @param sequenceLength
	 * @param @return
	 * @return String
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 下午1:21:06
	 * @lastModified
	 */
	public String generateMaterialCode(String prefix,int sequenceLength);
}