package com.aptech.business.managePlanContract.budgetManage.service;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.IdentifyEnum;
import com.aptech.business.component.dictionary.OperationStatusEnum;
import com.aptech.business.component.dictionary.WorkFireStatusEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.managePlanContract.budgetManage.dao.BudgetManageDao;
import com.aptech.business.managePlanContract.budgetManage.domain.BudgetManageEntity;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.overhaul.overhaulRecord.domain.OverhaulRecordEntity;
import com.aptech.business.ticketManage.operationTicket.domain.OperationTicketEntity;
import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsEnum;
import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsItem;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.domain.UnitLevelEnum;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;

/**
 * 
 * 预算管理应用管理服务实现类
 *
 * @author 
 * @created 2018-07-25 13:17:36
 * @lastModified 
 * @history
 *
 */
@Service("budgetManageService")
@Transactional
public class BudgetManageServiceImpl extends AbstractBaseEntityOperation<BudgetManageEntity> implements BudgetManageService {
	
	@Autowired
	private BudgetManageDao budgetManageDao;
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<BudgetManageEntity> getDao() {
		return budgetManageDao;
	}
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.asc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	@Override
	public void addEntity(BudgetManageEntity t) {
		t.setCreateDate(new Date());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		super.addEntity(t);
		
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.BUDGETMANAGE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
	@Override
	public void updateEntity(BudgetManageEntity t) {
		
		BudgetManageEntity budgetManageEntity = budgetManageDao.findById(t.getId());
		t.setYear(budgetManageEntity.getYear());
		t.setCreateDate(budgetManageEntity.getCreateDate());
		t.setCreateUserId(budgetManageEntity.getCreateUserId());
		budgetManageDao.updateEntity(t);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.BUDGETMANAGE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	
	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.BUDGETMANAGE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Override
	public void deleteEntity(Serializable id){
		BudgetManageEntity recordEntity  =this.findById(id);
		budgetManageDao.deleteEntity(recordEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.BUDGETMANAGE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	
	
	@Override
	public List<BudgetManageEntity>  getDataList(String searchYear) {
		List<Condition> conditions = new ArrayList<Condition>();
	
		Calendar searchCal = Calendar.getInstance();
		if (searchYear !=null && !"".equals(searchYear)) {
			searchCal.set(Integer.valueOf(searchYear), Calendar.JANUARY, 1, 0, 0, 0);
		} else {
			searchCal.set(searchCal.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
		}
		
		//年开始时间
		Calendar yearStartCal =  Calendar.getInstance();
		yearStartCal.setTime(searchCal.getTime());
		//年结束时间
		Calendar yearEndCal =  Calendar.getInstance();
		yearEndCal.setTime(searchCal.getTime());
		yearEndCal.add(Calendar.YEAR, 1);
		yearEndCal.add(Calendar.SECOND, -1);
		System.out.println(yearStartCal.getTime());
		System.out.println(yearEndCal.getTime());
		conditions.clear();
		
		conditions.add(new Condition("C_YEAR", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartCal.getTime()));
		conditions.add(new Condition("C_YEAR", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndCal.getTime()));
		List<BudgetManageEntity> resultList = budgetManageDao.findByCondition(conditions, null);
		
		return resultList;
	}
}