package com.aptech.business.technical.technicalPlandetail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.technical.technicalPlandetail.dao.TechnicalPlandetailDao;
import com.aptech.business.technical.technicalPlandetail.domain.TechnicalPlandetailEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 技术监督详细计划应用管理服务实现类
 *
 * @author 
 * @created 2017-11-13 16:16:12
 * @lastModified 
 * @history
 *
 */
@Service("technicalPlandetailService")
@Transactional
public class TechnicalPlandetailServiceImpl extends AbstractBaseEntityOperation<TechnicalPlandetailEntity> implements TechnicalPlandetailService {
	
	@Autowired
	private TechnicalPlandetailDao technicalPlandetailDao;
	
	@Override
	public IBaseEntityOperation<TechnicalPlandetailEntity> getDao() {
		return technicalPlandetailDao;
	}
	
	/**
	 * 查询列表 zzq 20170605
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.asc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		String orderSeq=(String)params.get("orderSeq");
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<TechnicalPlandetailEntity> list=new ArrayList<TechnicalPlandetailEntity>();
		list=(List<TechnicalPlandetailEntity>) super.findByCondition(conditions, page);
		int index=0;
		for (TechnicalPlandetailEntity technicalPlandetailEntity : list) {
			index+=1;
			technicalPlandetailEntity.setOrderSeq(orderSeq+"."+index);
		}
		return (List<O>) list;
	}
	
	
	@Override
	public void addEntity(TechnicalPlandetailEntity t) {
		String orderSeqWork=t.getOrderSeqWork();//第二个表的序号
		String orderSeq=t.getOrderSeq();//第三个表的序号
	    t.setOrderSeq(orderSeqWork+"."+orderSeq);
	    t.setStatus(Long.valueOf(DataStatusEnum.NORMAL.ordinal()));
		super.addEntity(t);
	}
	@Override
	public ResultObj update(TechnicalPlandetailEntity t, Long id) {
		TechnicalPlandetailEntity technicalPlandetailEntity=this.findById(id);
		technicalPlandetailEntity.setPlanName(t.getPlanName());
		technicalPlandetailEntity.setDjzq(t.getDjzq());
		technicalPlandetailEntity.setNowTime(t.getNowTime());
		technicalPlandetailEntity.setWcTime(t.getWcTime());
		technicalPlandetailEntity.setWcStatus(t.getWcStatus());
		technicalPlandetailEntity.setWcqk(t.getWcqk());
		technicalPlandetailEntity.setDanger(t.getDanger());
		super.updateEntity(technicalPlandetailEntity);
		return new ResultObj();
	}
}