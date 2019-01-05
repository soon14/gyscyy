package com.aptech.business.operation.userOperateLog.service;

import com.aptech.business.operation.userOperateLog.domain.OperateLogEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 用户操作应用管理服务接口
 *
 * @author 
 * @created 2018-04-09 10:36:54
 * @lastModified 
 * @history
 *
 */
public interface OperateLogService  extends IBaseEntityOperation<OperateLogEntity> {
	/**
	 * 添加业务日志
	 * @param userEntity 登录用户信息
	 * @param operateModule  UserOperateModuleEnum枚举中每个模块对应的name值
	 * @param operate UserOperateEnum枚举中每个模块对应的操作name值
	 * @param code 相关操作的code或者name title之类的用于区分操作的是哪条记录
	 */
	public void addOperateLog(SysUserEntity userEntity,String operateModule,String operate,String code,int operateUser);
}