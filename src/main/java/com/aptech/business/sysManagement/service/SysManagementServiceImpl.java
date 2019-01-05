package com.aptech.business.sysManagement.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.SysManagementStatusEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.sysManagement.dao.SysManagementDao;
import com.aptech.business.sysManagement.domain.SysManagementEntity;
import com.aptech.common.fourcode.service.FourCodeService;
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
 * 制度管理应用管理服务实现类
 *
 * @author 
 * @created 2018-03-14 16:26:37
 * @lastModified 
 * @history
 *
 */
@Service("sysManagementService")
@Transactional
public class SysManagementServiceImpl extends AbstractBaseEntityOperation<SysManagementEntity> implements SysManagementService {
	
	@Autowired
	private SysManagementDao sysManagementDao;
	@Autowired
	private FourCodeService fourCodeService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<SysManagementEntity> getDao() {
		return sysManagementDao;
	}
	
	
	
	@Override
	public void addEntity(SysManagementEntity t) {
		t.setStatus(SysManagementStatusEnum.YES.getCode());
		Map<String, Object> codeparams=new HashMap<String, Object> ();
		codeparams.put("year", new Date());
		String code=fourCodeService.getBusinessCode("制度编码", codeparams);
		t.setCode(code);
		super.addEntity(t);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.REGULATIONS.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
	}
	@SuppressWarnings("unchecked")
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("materialDate"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<SysManagementEntity> list=(List<SysManagementEntity>) sysManagementDao.findByCondition(conditions, page);
		for (SysManagementEntity sysManagementEntity : list) {
			if(sysManagementEntity.getYearNum()!=null){
				SimpleDateFormat sf=new SimpleDateFormat("yyyy");
				sysManagementEntity.setYearNumString(sf.format(sysManagementEntity.getYearNum()));
			}
		}
		return  (List<O>) list;
	}
	@Override
	public ResultObj update(SysManagementEntity t, Long id) {
		SysManagementEntity sysManagementEntity = this.findById(id);
		sysManagementEntity.setApplyUserId(t.getApplyUserId());
		sysManagementEntity.setFileId(t.getFileId());
		sysManagementEntity.setMaterialDate(t.getMaterialDate());
		sysManagementEntity.setSysName(t.getSysName());
		sysManagementEntity.setType(t.getType());
		sysManagementEntity.setUnitId(t.getUnitId());
		sysManagementEntity.setYearNum(t.getYearNum());
		sysManagementEntity.setCentralizedUnitId(t.getCentralizedUnitId());
		sysManagementEntity.setCentralizedUnitName(t.getCentralizedUnitName());;
		super.updateEntity(sysManagementEntity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.REGULATIONS.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		
		return new ResultObj();
	}
}