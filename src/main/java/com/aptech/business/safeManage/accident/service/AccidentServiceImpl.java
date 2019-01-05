package com.aptech.business.safeManage.accident.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.SummaryStatusEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.accident.dao.AccidentDao;
import com.aptech.business.safeManage.accident.domain.AccidentEntity;
import com.aptech.business.technical.summary.exception.SummaryException;
import com.aptech.business.technical.summary.exception.SummaryExceptionType;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
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
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 技术监督总结应用管理服务实现类
 *
 * @author 
 * @created 2018-04-02 09:02:22
 * @lastModified 
 * @history
 *
 */
@Service("accidentService")
@Transactional
public class AccidentServiceImpl extends AbstractBaseEntityOperation<AccidentEntity> implements AccidentService {
	
	@Autowired
	private AccidentDao accidentDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired 
	private SysUnitService sysUnitService;
	/**
	 * 操作日志
	 */
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<AccidentEntity> getDao() {
		return accidentDao;
	}
	
	
	/**
	 * 查询列表 zzq 20170605
	 */
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.desc("C_CREATE_DATE"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<AccidentEntity> resultList = (List<AccidentEntity>) super.findByCondition(conditions, page);
		for (AccidentEntity accidentEntity : resultList) {
			String unitId=accidentEntity.getUnitId();
			SysUnitEntity sysUnitEntity=sysUnitService.findById(Long.valueOf(unitId));
			accidentEntity.setUnitName(sysUnitEntity.getName());
		}
		return (List<O>) resultList;
	}
	
	//保存
	@Override
	public void addEntity(AccidentEntity t) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		t.setCreateDate(new Date());
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		super.addEntity(t);
		
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.ACCIDENTDISPOSE.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
	}
	//修改方法
	@Override
	public ResultObj update(AccidentEntity t, Long id) {
		AccidentEntity  accidentEntity=this.findById(t.getId());
		accidentEntity.setUnitId(t.getUnitId());
		accidentEntity.setSglb(t.getSglb());
		accidentEntity.setTime(t.getTime());
		accidentEntity.setFileid(t.getFileid());
		accidentEntity.setName(t.getName());
		accidentEntity.setDcbg(t.getDcbg());
		super.updateEntity(accidentEntity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.ACCIDENTDISPOSE.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		
		return new ResultObj();
	}
	//删除
	@Override
	public void deleteEntity(Serializable id) {
		AccidentEntity accidentEntity=this.findById(id);
		accidentEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(accidentEntity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.ACCIDENTDISPOSE.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
	}
	
	/**
	 * @Description:   修改时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@Override
	public ResultObj updateValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		isupdateValidate(id);
		return resultObj;
	}
	private boolean isupdateValidate(Long id) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		if(!userEntity.getLoginName().equals("super")){
			List<Condition> conditions=new ArrayList<Condition>();
			conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
			List<AccidentEntity> list=this.findByCondition(conditions, null);
			if(list.isEmpty()){
				throw new SummaryException(SummaryExceptionType.WORKTICKET_UPDATEDELETE);
			}else{
				AccidentEntity  accidentEntity=list.get(0);
				//String workStatus=accidentEntity.getWorkStatus().toString();
//				if(!workStatus.equals(SummaryStatusEnum.DTJ.getCode())&&!workStatus.equals(SummaryStatusEnum.BHGZFZR.getCode())){
//					throw new SummaryException(SummaryExceptionType.WORKTICKET_UPDATESTATUS);
//				}
			}
		}
		
		return true;
	}
	/** 批量删除
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@Override
	public ResultObj deleteBulk(List<Integer> ids) {
		validateisDeleteBulk(ids);
		return new ResultObj();
	}
	/**
	 * @Description:   批量删除的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	private boolean validateisDeleteBulk(List<Integer> ids) {
		boolean flag=true;
		SysUserEntity userEntity= RequestContext.get().getUser();
		for (int i=0;i<ids.size();i++) {
			AccidentEntity  accidentEntity=this.findById(Long.valueOf(ids.get(i)));
			if(accidentEntity!=null){
				String workStatus=accidentEntity.getStatus().toString();
				if(!workStatus.equals(SummaryStatusEnum.DTJ.getCode())){
					flag=false;
					break;
				}
			}
		}
		if (flag) {
			for (int i=0;i<ids.size();i++) {
				AccidentEntity  accidentEntity=this.findById(Long.valueOf(ids.get(i)));
				accidentEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
				super.updateEntity(accidentEntity);
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.ACCIDENTDISPOSE.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
			}
		}else{
			throw new SummaryException(SummaryExceptionType.WORKTICKET_BATHDELETE);
		}
		
		return true;
	}
	
	/**
	 * @Description:   单个删除时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@Override
	public ResultObj deleteValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		isdeleteValidate(id);
		return resultObj;
	}
	private boolean isdeleteValidate(Long id) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		if(!userEntity.getLoginName().equals("super")){
			List<Condition> conditions=new ArrayList<Condition>();
			conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
			List<AccidentEntity> list=this.findByCondition(conditions, null);
			if(list.isEmpty()){
				throw new SummaryException(SummaryExceptionType.WORKTICKET_UPDATEDELETE);
			}else{
				AccidentEntity  workTicket=list.get(0);
				String workStatus=workTicket.getStatus().toString();
				if(!workStatus.equals(SummaryStatusEnum.DTJ.getCode())){
					throw new SummaryException(SummaryExceptionType.WORKTICKET_BATHDELETE);
				}
			}
		}
		return true;
	}
	/**
	 * @Description:   提交时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@Override
	public ResultObj tijiaoValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		istijiaoValidate(id);
		return resultObj;
	}
	private boolean istijiaoValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<AccidentEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new SummaryException(SummaryExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			AccidentEntity  workTicket=list.get(0);
			String workStatus=workTicket.getStatus().toString();
			if(!workStatus.equals(SummaryStatusEnum.DTJ.getCode())){
				throw new SummaryException(SummaryExceptionType.WORKTICKET_SUBMITSTATUS);
			}
		}
		return true;
	}


	
}