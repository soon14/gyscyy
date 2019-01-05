package com.aptech.business.safeManage.safeReport.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.safeManage.safeReport.dao.SafeReportDao;
import com.aptech.business.safeManage.safeReport.domain.SafeReportEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全月报应用管理服务实现类
 *
 * @author 
 * @created 2018-03-28 11:18:00
 * @lastModified 
 * @history
 *
 */
@Service("safeReportService")
@Transactional
public class SafeReportServiceImpl extends AbstractBaseEntityOperation<SafeReportEntity> implements SafeReportService {
	
	@Autowired
	private SafeReportDao safeReportDao;
	
	@Override
	public IBaseEntityOperation<SafeReportEntity> getDao() {
		return safeReportDao;
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
	@Override
	public void updateEntity(SafeReportEntity t) {
		safeReportDao.updateEntity(t);
	}
	
	@Override
	public ResultObj add(SafeReportEntity safeReportEntity) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		safeReportEntity.setUserId(userEntity.getId().toString());
		safeReportDao.addEntity(safeReportEntity);	
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeReportEntity);
 		return resultObj;
	}
	
	@Override
	public ResultObj update(SafeReportEntity t) {
		// TODO Auto-generated method stub
		SafeReportEntity safeReportEntity = this.findById(t.getId());
		safeReportEntity.setTime(t.getTime());
		safeReportEntity.setYearNum(t.getYearNum());
		safeReportEntity.setFileId(t.getFileId());
		safeReportEntity.setName(t.getName());
		safeReportEntity.setUnitId(t.getUnitId());
		safeReportDao.updateEntity(safeReportEntity);
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeReportEntity);
 		return resultObj;
	}
}