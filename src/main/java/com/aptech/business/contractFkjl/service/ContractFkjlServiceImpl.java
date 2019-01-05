package com.aptech.business.contractFkjl.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.Istypicaleum;
import com.aptech.business.contractFkjl.dao.ContractFkjlDao;
import com.aptech.business.contractFkjl.domain.ContractFkjlEntity;
import com.aptech.business.contractManage.domain.ContractManageEntity;
import com.aptech.business.contractManage.service.ContractManageService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
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
 * 合同付款记录应用管理服务实现类
 *
 * @author 
 * @created 2018-09-11 15:39:29
 * @lastModified 
 * @history
 *
 */
@Service("contractFkjlService")
@Transactional
public class ContractFkjlServiceImpl extends AbstractBaseEntityOperation<ContractFkjlEntity> implements ContractFkjlService {
	
	@Autowired
	private ContractFkjlDao contractFkjlDao;
	@Autowired
	private ContractManageService contractManageService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<ContractFkjlEntity> getDao() {
		return contractFkjlDao;
	}
	@SuppressWarnings("unchecked")
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		String zbid=(String)params.get("zbid");
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("id"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		if(zbid!=null&&!zbid.equals("")){
			conditions.add(new Condition("C_CONTRACT_MANAGE_I_D", FieldTypeEnum.STRING, MatchTypeEnum.LIKE, zbid));
		}
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		
		List<ContractFkjlEntity> list=(List<ContractFkjlEntity>) this.findByCondition(conditions, page);
		for (ContractFkjlEntity contractFkjlEntity : list) {
			DecimalFormat df = new DecimalFormat("#0.00");
			String wfhtje=contractFkjlEntity.getWfhtje();
			String fkje=contractFkjlEntity.getFkje();
			String xwfhtje=df.format(Double.valueOf(wfhtje)-Double.valueOf(fkje));
			contractFkjlEntity.setFalg1(xwfhtje);
		}
		return (List<O>) list;
	}
	
	@Override
	public void addEntity(ContractFkjlEntity t) {
		DecimalFormat df = new DecimalFormat("#0.00");
		String wfhtje=t.getWfhtje();
		String fkje=t.getFkje();
		String xwfhtje=df.format(Double.valueOf(wfhtje)-Double.valueOf(fkje));
		if(xwfhtje.equals("0.00")){
			ContractManageEntity entity=new ContractManageEntity();
			entity.setId(Long.valueOf(t.getContractManageID()));
			entity.setYjsmoney(Istypicaleum.ISSETYES.getCode());
			contractManageService.updateByMap("updateZxzt", entity);
		}
		super.addEntity(t);
		SysUserEntity userEntity= RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.CONTRACT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	@Override
	public void updateEntity(ContractFkjlEntity t) {
		DecimalFormat df = new DecimalFormat("#0.00");
		String wfhtje=t.getWfhtje();
		String fkje=t.getFkje();
		String xwfhtje=df.format(Double.valueOf(wfhtje)-Double.valueOf(fkje));
		if(xwfhtje.equals("0.00")){
			ContractManageEntity entity=new ContractManageEntity();
			entity.setId(Long.valueOf(t.getContractManageID()));
			entity.setYjsmoney(Istypicaleum.ISSETYES.getCode());
			contractManageService.updateByMap("updateZxzt", entity);
		}
		super.updateEntity(t);
		SysUserEntity userEntity= RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.CONTRACT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
}