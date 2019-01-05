package com.aptech.business.technical.technical.service;

import java.util.List;

import com.aptech.business.technical.technical.domain.TechnicalEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 技术监督应用管理服务接口
 *
 * @author 
 * @created 2017-11-13 16:15:05
 * @lastModified 
 * @history
 *
 */
public interface TechnicalService  extends IBaseEntityOperation<TechnicalEntity> {
	/**
	 * @Description:   修改时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	ResultObj updateValidate(Long id);
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	ResultObj update(TechnicalEntity t, Long id);
	/** 批量删除
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	ResultObj deleteBulk(List<Integer> ids);
	/**
	 * @Description:   单个删除时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	ResultObj deleteValidate(Long id);
	/**
	 * @Description:   提交时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	ResultObj tijiaoValidate(Long id);
	
	void submit(String id, String selectUser);
	
	void updateSpnrAgree(TechnicalEntity technicalEntity,
			SysUserEntity userEntity);
	
	void updateSpnrDisagree(TechnicalEntity technicalEntity,
			SysUserEntity userEntity);
	
	void updateSpnrDisagreeUp(TechnicalEntity technicalEntity,
			SysUserEntity userEntity);
	
	ResultObj saveValidate(String unitId, String jdzyId, String time,String technicalId,String uuid);
	
	ResultObj approveValidate(String technicalId);
	
	ResultObj cexiaoValidate(Long id);
}