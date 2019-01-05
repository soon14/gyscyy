package com.aptech.business.planManage.planWhole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.defectManage.defect.exception.DefectException;
import com.aptech.business.planManage.plan.domain.PlanEntity;
import com.aptech.business.planManage.plan.service.PlanService;
import com.aptech.business.planManage.planDetail.domain.PlanDetailEntity;
import com.aptech.business.planManage.planDetail.service.PlanDetailService;
import com.aptech.business.planManage.planWhole.dao.PlanWholeDao;
import com.aptech.business.planManage.planWhole.domain.PlanWholeEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 整体计划应用管理服务实现类
 *
 * @author 
 * @created 2017-11-13 15:10:22
 * @lastModified 
 * @history
 *
 */
@Service("planWholeService")
@Transactional
public class PlanWholeServiceImpl extends AbstractBaseEntityOperation<PlanWholeEntity> implements PlanWholeService {
	
	@Autowired
	private PlanWholeDao planWholeDao;

	@Autowired
	private PlanService planService;
	
	@Autowired
	private  PlanDetailService planDetailService;
	@Override
	public IBaseEntityOperation<PlanWholeEntity> getDao() {
		return planWholeDao;
	}
	/**
	 * 添加实体
	 * 
	 * @param t
	 */
	@Override
	public ResultObj add(PlanWholeEntity planWholeEntity){
		PlanEntity t = planService.findById(planWholeEntity.getPlanId());
		if(planService.validate(t)){
			if(planWholeEntity.getId()==null){
				planWholeDao.addEntity(planWholeEntity);
				List<Condition> conditions =new ArrayList<Condition>();
				conditions.add(new Condition("C_UUID_WHOLE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,planWholeEntity.getUuidWhole()));
				List<PlanDetailEntity> planDetailList=planDetailService.findByCondition(conditions, null);
				for (PlanDetailEntity p:planDetailList) {
					p.setPlanWholeId(planWholeEntity.getId());
					planDetailService.updateEntity(p);
				}
			}else{
				PlanWholeEntity tt=planWholeDao.findById(planWholeEntity.getId());
				planWholeEntity.setPlanId(tt.getPlanId());
				planWholeEntity.setUuid(tt.getUuid());
				planWholeEntity.setUuidWhole(tt.getUuidWhole());
				planWholeDao.updateEntity(planWholeEntity);
			}
		}
		planDetailService.updatePlanDetailEntity(planWholeEntity.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(planWholeEntity);
		return resultObj;
	}
	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	@Override
	public void deleteEntity(Serializable id) {
		PlanWholeEntity planWholeEntity = this.findById(id);
		if(planWholeEntity==null){
			throw new DefectException("整体计划已删除");
		}
		PlanEntity t = planService.findById(planWholeEntity.getPlanId());
		if(planService.validateSubmit(t)){
			//查询明细（删除明细）
			List<Condition> conditions =new ArrayList<Condition>();
			conditions.add(new Condition("C_PLAN_WHOLE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,planWholeEntity.getId()));
			List <PlanDetailEntity> detailList=planDetailService.findByCondition(conditions, null);
			for (PlanDetailEntity p:detailList) {
				planDetailService.deleteEntity(p.getId());
			}
			planWholeDao.deleteEntity(id);
		}
	}
}