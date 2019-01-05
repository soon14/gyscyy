package com.aptech.business.teamMemApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.dutyOrder.domain.DutyOrderEntity;
import com.aptech.business.teamMemApp.dao.TeamMemAppDao;
import com.aptech.business.teamMemApp.domain.TeamMemAppEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;

/**
 * 
 * 班次应用管理服务实现类
 *
 * @author 
 * @created 2017-09-13 17:15:08
 * @lastModified 
 * @history
 *
 */
@Service("teamMemAppService")
@Transactional
public class TeamMemAppServiceImpl extends AbstractBaseEntityOperation<TeamMemAppEntity> implements TeamMemAppService {
	
	@Autowired
	private TeamMemAppDao teamMemAppDao;
	@Autowired
	private SysUserService sysUserService;
	@Override
	public IBaseEntityOperation<TeamMemAppEntity> getDao() {
		return teamMemAppDao;
	}
	
	
	/**
	 * 查询列表 zzq 20170605
	 */
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		List<Sort> orderList=new ArrayList<Sort>();//page.getOrders();
   		
   		orderList.addAll(OrmUtil.changeMapToOrders(params));
		//默认计划时间 倒叙
		page.setOrders(orderList);
   		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<O> resultList =  super.findByCondition(conditions, page);
		return  resultList;
	}
}