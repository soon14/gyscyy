package com.aptech.business.managePlanContract.goodsRelation.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.business.managePlanContract.goodsRelation.domain.GoodsRelationEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 物资关联应用管理服务接口
 *
 * @author 
 * @created 2018-04-19 09:23:45
 * @lastModified 
 * @history
 *
 */
public interface GoodsRelationService  extends IBaseEntityOperation<GoodsRelationEntity> {
	
	
	/**
	 * @Description:  关联计划
	 * @author         
	 * @Date          
	 * @throws         Exception
	 */
	public @ResponseBody <T>  ResultObj addGoodsInfo(HttpServletRequest request,Map<String, Object> params);
	
	public <T> ResultObj delete(HttpServletRequest request, Long id);
	
}