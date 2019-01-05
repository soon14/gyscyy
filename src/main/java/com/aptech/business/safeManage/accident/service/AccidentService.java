package com.aptech.business.safeManage.accident.service;

import java.util.List;

import com.aptech.business.safeManage.accident.domain.AccidentEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 技术监督总结应用管理服务接口
 *
 * @author 
 * @created 2018-04-02 09:02:22
 * @lastModified 
 * @history
 *
 */
public interface AccidentService  extends IBaseEntityOperation<AccidentEntity> {
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
	ResultObj update(AccidentEntity t, Long id);
	
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
}