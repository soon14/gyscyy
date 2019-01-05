package com.aptech.business.planManage.planDetail.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.defectManage.defect.exception.DefectException;
import com.aptech.business.planManage.plan.domain.PlanEntity;
import com.aptech.business.planManage.plan.service.PlanService;
import com.aptech.business.planManage.planDetail.dao.PlanDetailDao;
import com.aptech.business.planManage.planDetail.domain.PlanDetailEntity;
import com.aptech.business.planManage.planWhole.domain.PlanWholeEntity;
import com.aptech.business.planManage.planWhole.service.PlanWholeService;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 详细计划应用管理服务实现类
 *
 * @author 
 * @created 2017-11-13 15:10:26
 * @lastModified 
 * @history
 *
 */
@Service("planDetailService")
@Transactional
public class PlanDetailServiceImpl extends AbstractBaseEntityOperation<PlanDetailEntity> implements PlanDetailService {
	
	@Autowired
	private PlanDetailDao planDetailDao;
	@Autowired
	private PlanWholeService planWholeService;
	@Autowired
	private PlanService planService;
	@Override
	public IBaseEntityOperation<PlanDetailEntity> getDao() {
		return planDetailDao;
	}
	/**
	 * 添加实体
	 * 
	 * @param t
	 */
	@Override
	public ResultObj add(PlanDetailEntity planDetailEntity){
		PlanWholeEntity wEntity = planWholeService.findById(planDetailEntity.getPlanWholeId());
		PlanEntity t=null;
		if(wEntity!=null){
			 t = planService.findById(wEntity.getPlanId());
		}
		if(planService.validate(t)){
			if(planDetailEntity.getId()==null){
				planDetailDao.addEntity(planDetailEntity);
			}else{
				PlanDetailEntity tt= planDetailDao.findById(planDetailEntity.getId());
				planDetailEntity.setUuidWhole(tt.getUuidWhole());
				planDetailEntity.setPlanWholeId(tt.getPlanWholeId());
				planDetailDao.updateEntity(planDetailEntity);
			}
		}
		PlanWholeEntity planWholeEntity=updatePlanDetailEntity(planDetailEntity.getPlanWholeId());
		if(planWholeEntity!=null){
			planWholeService.updateEntity(planWholeEntity);
		}
		ResultObj resultObj = new ResultObj();
		resultObj.setData(planDetailEntity);
		return resultObj;
	}
	/**
	 * 删除实体
	 * 
	 * @param t
	 */
	@Override
	 public void deleteEntity(Serializable id){
		PlanDetailEntity planDetailEntity=planDetailDao.findById(id);
		if(planDetailEntity==null){
			throw new DefectException("详细计划已删除");
		}
		PlanWholeEntity wEntity = planWholeService.findById(planDetailEntity.getPlanWholeId());
		if(wEntity==null){
			throw new DefectException("整体计划已删除");
		}
		PlanEntity t = planService.findById(wEntity.getPlanId());
		if(planService.validateSubmit(t)){
			planDetailDao.deleteEntity(id);
			//投资总额
			PlanWholeEntity planWholeEntity=updatePlanDetailEntity(planDetailEntity.getPlanWholeId());
			planWholeService.updateEntity(planWholeEntity);
		}
	}
	//重计算
	public   PlanWholeEntity updatePlanDetailEntity(Long planWholeId){
		PlanWholeEntity planWholeEntity=planWholeService.findById(planWholeId);
		if(planWholeEntity!=null){
			//查询详细计划
			List<Condition> conditions =new ArrayList<Condition>();
			conditions.add(new Condition("C_PLAN_WHOLE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,planWholeEntity.getId()));
			List <PlanDetailEntity> detailList=planDetailDao.findByCondition(conditions, null);
			BigDecimal planTotal=new BigDecimal("0");
			String stratTime="";
			String endTime="";
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM");
			for (PlanDetailEntity p:detailList) {
				planTotal=planTotal.add(new BigDecimal(p.getPlanTotal()));
				if(stratTime.equals("")||stratTime.compareTo(df.format(p.getStratTime()))>0){
					stratTime=df.format(p.getStratTime());
				}
				if(endTime.equals("")||endTime.compareTo(df.format(p.getEndTime()))<0){
					endTime=df.format(p.getEndTime());
				}
			}
			planWholeEntity.setPlanTotal(planTotal.toString());
			planWholeEntity.setStratTime(stratTime);
			planWholeEntity.setEndTime(endTime);
			planWholeService.updateEntity(planWholeEntity);
			//查询整体计划
			PlanEntity planEntity=planService.findById(planWholeEntity.getPlanId());
			if(planEntity!=null){
				conditions.clear();
				conditions.add(new Condition("C_PLAN_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,planEntity.getId()));
				List <PlanWholeEntity> wholeList=planWholeService.findByCondition(conditions, null);
				BigDecimal planSum=new BigDecimal("0");
				for (PlanWholeEntity p:wholeList) {
					if(StringUtil.isNotEmpty(p.getPlanTotal())){
						planSum=planSum.add(new BigDecimal(p.getPlanTotal()));
					}
				}
				planEntity.setPlanSum(planSum.toString());
				planService.updateEntity(planEntity);
			}
		}
		return  planWholeEntity;
	}
}