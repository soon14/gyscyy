package com.aptech.business.cargo.stockStatictisPrint.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.stockStatictisPrint.dao.StockStatictisPrintDao;
import com.aptech.business.cargo.stockStatictisPrint.domain.StockStatictisPrintEntity;
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
 * 库存统计打印表应用管理服务实现类
 *
 * @author 
 * @created 2018-09-08 16:00:12
 * @lastModified 
 * @history
 *
 */
@Service("stockStatictisPrintService")
@Transactional
public class StockStatictisPrintServiceImpl extends AbstractBaseEntityOperation<StockStatictisPrintEntity> implements StockStatictisPrintService {
	
	@Autowired
	private StockStatictisPrintDao stockStatictisPrintDao;
	
	@Override
	public IBaseEntityOperation<StockStatictisPrintEntity> getDao() {
		return stockStatictisPrintDao;
	}
	/**
	 * 查询列表 zzq 20170605
	 */
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
			page.setPageSize(Integer.MAX_VALUE);
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		SysUserEntity sysUserEntity =RequestContext.get().getUser();
		conditions.add(new Condition("C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		conditions.add(new Condition("C_MONTH_STOCK", FieldTypeEnum.STRING, MatchTypeEnum.GE, "0"));
		conditions.add(new Condition("C_LAST_MONTH_STOCK", FieldTypeEnum.STRING, MatchTypeEnum.GE, "0"));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
}