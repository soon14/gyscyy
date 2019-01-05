package com.aptech.business.overhaul.overhaulFile.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aptech.business.overhaul.overhaulFile.domain.OverhaulFileEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修文件包应用管理服务接口
 *
 * @author 
 * @created 2017-08-04 14:04:07
 * @lastModified 
 * @history
 *
 */
public interface OverhaulFileService  extends IBaseEntityOperation<OverhaulFileEntity> {
	/**
	 * 添加实体
	 * 
	 * @param t
	 */
	ResultObj add(OverhaulFileEntity t);
	
	/**
	 *  批量删除
	 * @param ids
	 */
	ResultObj deleteBulk(List<Integer> ids);
	
	
	boolean checkUpload();

}