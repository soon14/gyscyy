package com.aptech.business.contractManage.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.Istypicaleum;
import com.aptech.business.contractManage.dao.ContractManageDao;
import com.aptech.business.contractManage.domain.ContractManageEntity;
import com.aptech.business.managePlanContract.supplierManage.domain.SupplierManageEntity;
import com.aptech.business.managePlanContract.supplierManage.service.SupplierManageService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
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
import com.aptech.framework.util.DateFormatUtil;

/**
 * 
 * 合同管理应用管理服务实现类
 *
 * @author 
 * @created 2018-04-17 14:03:40
 * @lastModified 
 * @history
 *
 */
@Service("contractManageService")
@Transactional
public class ContractManageServiceImpl extends AbstractBaseEntityOperation<ContractManageEntity> implements ContractManageService {
	
	@Autowired
	private ContractManageDao contractManageDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SupplierManageService supplierManageService;
	@Override
	public IBaseEntityOperation<ContractManageEntity> getDao() {
		return contractManageDao;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		String qdnf=(String)params.get("qdnf");//获取前台到底用没用年份查询
		DateFormatUtil  dd=DateFormatUtil.getInstance("yyyy");
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("id"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		if(qdnf==null||qdnf.equals("")){
			conditions.add(new Condition("C_QDRQ", FieldTypeEnum.STRING, MatchTypeEnum.LIKE, dd.format(new Date())));
		}
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		
		List<ContractManageEntity> list=(List<ContractManageEntity>) this.findByCondition(conditions, page);
		for (ContractManageEntity contractManageEntity : list) {
			SysUnitEntity unit=sysUnitService.findById(Long.valueOf(contractManageEntity.getContractXdname()));
			contractManageEntity.setContractXdname(unit.getName());
			if(contractManageEntity.getYjsmoney().equals(Istypicaleum.ISSETNO.getCode())){
				contractManageEntity.setYjsmoneyName(Istypicaleum.ISSETNO.getName());
			}
			if(contractManageEntity.getYjsmoney().equals(Istypicaleum.ISSETYES.getCode())){
				contractManageEntity.setYjsmoneyName(Istypicaleum.ISSETYES.getName());
			}
			//供应商转中文
			conditions.clear();
			conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.DELETE.ordinal())));
			if(contractManageEntity.getRemark()!=null){
				conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Long.valueOf(contractManageEntity.getRemark())));
			}
	  		List<SupplierManageEntity> supplierIdList = supplierManageService.findByCondition(conditions, null);
	  		if(!supplierIdList.isEmpty()){
	  			contractManageEntity.setRemarkName(supplierIdList.get(0).getName());
	  		}
	  		
		}
		return (List<O>) list;
	}
	@Override
	public void addEntity(ContractManageEntity t) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		t.setYjsmoney(Istypicaleum.ISSETNO.getCode());
		super.addEntity(t);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.CONTRACT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	@Override
	public void updateEntity(ContractManageEntity t) {
		t.setYjsmoney(Istypicaleum.ISSETNO.getCode());
		super.updateEntity(t);
		SysUserEntity userEntity= RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.CONTRACT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	@Override
	public void deleteEntity(Serializable id) {
		ContractManageEntity  contractManageEntity=this.findById(id);
		contractManageEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(contractManageEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.CONTRACT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	@Override
	public void bulkDelete(Serializable[] ids) {
		String [] htids = ids.toString().split(",");
		for(int i = 0;i<htids.length;i++){
			ContractManageEntity  contractManageEntity=this.findById(i);
			contractManageEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
			super.updateEntity(contractManageEntity);
		}
		SysUserEntity userEntity= RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.CONTRACT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
}