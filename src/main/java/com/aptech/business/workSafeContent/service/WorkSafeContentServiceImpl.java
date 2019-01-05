package com.aptech.business.workSafeContent.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;

import com.aptech.business.workSafeContent.dao.WorkSafeContentDao;
import com.aptech.business.workSafeContent.domain.WorkSafeContentEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;

/**
 * 
 * 安全措施应用管理服务实现类
 *
 * @author 
 * @created 2018-04-03 13:17:33
 * @lastModified 
 * @history
 *
 */
@Service("workSafeContentService")
@Transactional
public class WorkSafeContentServiceImpl extends AbstractBaseEntityOperation<WorkSafeContentEntity> implements WorkSafeContentService {
	
	@Autowired
	private WorkSafeContentDao workSafeContentDao;
	/**
	 * 操作日志
	 */
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<WorkSafeContentEntity> getDao() {
		return workSafeContentDao;
	}
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	@Override
	public void addEntity(WorkSafeContentEntity t) {
		t.setCreateDate(new Date());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		workSafeContentDao.addEntity(t);	
		
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.WORK_SAFE_CONTENT.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
	}
	
}