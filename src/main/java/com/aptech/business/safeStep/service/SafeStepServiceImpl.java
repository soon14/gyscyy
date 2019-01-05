package com.aptech.business.safeStep.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.safeStep.dao.SafeStepDao;
import com.aptech.business.safeStep.domain.SafeStepEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;

/**
 * 
 * 安全技术措施计划应用管理服务实现类
 *
 * @author 
 * @created 2018-09-14 14:40:53
 * @lastModified 
 * @history
 *
 */
@Service("safeStepService")
@Transactional
public class SafeStepServiceImpl extends AbstractBaseEntityOperation<SafeStepEntity> implements SafeStepService {
	
	@Autowired
	private SafeStepDao safeStepDao;
	
	@Override
	public IBaseEntityOperation<SafeStepEntity> getDao() {
		return safeStepDao;
	}
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("id"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		return findByCondition(conditions, page);
	}
}