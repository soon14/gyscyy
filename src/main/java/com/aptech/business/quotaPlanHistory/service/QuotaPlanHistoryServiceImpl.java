package com.aptech.business.quotaPlanHistory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.annualProductionCapacity.domain.AnnualProductionCapacityEntity;
import com.aptech.business.quotaPlanHistory.dao.QuotaPlanHistoryDao;
import com.aptech.business.quotaPlanHistory.domain.QuotaPlanHistoryEntity;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;

/**
 * 
 * 指标类计划历史数据应用管理服务实现类
 *
 * @author 
 * @created 2018-09-19 18:20:50
 * @lastModified 
 * @history
 *
 */
@Service("quotaPlanHistoryService")
@Transactional
public class QuotaPlanHistoryServiceImpl extends AbstractBaseEntityOperation<QuotaPlanHistoryEntity> implements QuotaPlanHistoryService {
	
	@Autowired
	private QuotaPlanHistoryDao quotaPlanHistoryDao;
	
	@Override
	public IBaseEntityOperation<QuotaPlanHistoryEntity> getDao() {
		return quotaPlanHistoryDao;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
//   		page.addOrder(Sort.desc("C_ID"));
		String type = params.get("type").toString();
		List<Condition> conditions = new ArrayList<Condition>();
		List<QuotaPlanHistoryEntity>  list =null;
		if(type.equals("3")){
			//对应年度生产指标计划
			conditions=OrmUtil.changeMapToCondition(params);
			//取数据库中数据
			list =(List<QuotaPlanHistoryEntity>) this.findByCondition("findQuotaByCondition", conditions, page);
			
		}else{
			//对应年度生产量计划
			conditions=OrmUtil.changeMapToCondition(params);
			//取数据库中数据
	  		list=(List<QuotaPlanHistoryEntity>)super.findByCondition(params, page);
			
		}
		
		return (List<O>) list;
	}
	
}