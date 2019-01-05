package com.aptech.business.ticketManage.workSituation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.ticketManage.workSituation.dao.WorkSituationDao;
import com.aptech.business.ticketManage.workSituation.domain.WorkSituationEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 开工和收工情况应用管理服务实现类
 *
 * @author 
 * @created 2017-06-05 17:12:39
 * @lastModified 
 * @history
 *
 */
@Service("workSituationService")
@Transactional
public class WorkSituationServiceImpl extends AbstractBaseEntityOperation<WorkSituationEntity> implements WorkSituationService {
	
	@Autowired
	private WorkSituationDao workSituationDao;
	
	@Override
	public IBaseEntityOperation<WorkSituationEntity> getDao() {
		return workSituationDao;
	}
	/**
	 * @Description:  自己写的修改方法 
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午4:50:53 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkSituationEntity t, Long id) {
		WorkSituationEntity workSituationEntity=this.findById(id);
		workSituationEntity.setStartDate(t.getStartDate());
		workSituationEntity.setStartPicName(t.getStartPicName());
		workSituationEntity.setStartAllowName(t.getStartAllowName());
		workSituationEntity.setEndDate(t.getEndDate());
		workSituationEntity.setEndPicName(t.getEndPicName());
		workSituationEntity.setEndAllowName(t.getEndAllowName());
		super.updateEntity(workSituationEntity);
		return new ResultObj();
	}
}