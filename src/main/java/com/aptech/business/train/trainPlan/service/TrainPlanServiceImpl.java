package com.aptech.business.train.trainPlan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.train.trainPlan.dao.TrainPlanDao;
import com.aptech.business.train.trainPlan.domain.TrainPlanEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 培训计划应用管理服务实现类
 *
 * @author 
 * @created 2018-03-19 10:04:50
 * @lastModified 
 * @history
 *
 */
@Service("trainPlanService")
@Transactional
public class TrainPlanServiceImpl extends AbstractBaseEntityOperation<TrainPlanEntity> implements TrainPlanService {
	
	@Autowired
	private TrainPlanDao trainPlanDao;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public IBaseEntityOperation<TrainPlanEntity> getDao() {
		return trainPlanDao;
	}
	@Override
	public void saveAddPage(TrainPlanEntity t) {
		t.setStatus("0");
		this.addEntity(t);
		
	}
	
	@Override
	public void saveEditPage(TrainPlanEntity trainPlanEntity) {
//		trainPlanEntity.setStatus("0");
		this.updateEntity(trainPlanEntity);


	}
}