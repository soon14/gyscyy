package com.aptech.business.fileLearn.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.aptech.business.cargo.inStock.domain.InstockEntity;
import com.aptech.business.fileLearn.domain.FileLearnEntity;
import com.aptech.business.fileLearn.domain.FileTreeEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 文件学习应用管理服务接口
 *
 * @author 
 * @created 2018-04-02 16:50:12
 * @lastModified 
 * @history
 *
 */
public interface FileLearnService  extends IBaseEntityOperation<FileLearnEntity> {

	/**
	 * 
	 * 提交实体，开始流程
	 * 
	 * @param @param id
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月31日 下午1:21:36
	 * @lastModified
	 */
	public ResultObj submit(Serializable id, Map<String, Object> params);
	
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
	 * @created 2017年7月31日 下午1:21:57
	 * @lastModified
	 */
	public ResultObj approve(FileLearnEntity t,Map<String, Object> params);
	
	 public List<FileTreeEntity> getRecipientTree();
	
}