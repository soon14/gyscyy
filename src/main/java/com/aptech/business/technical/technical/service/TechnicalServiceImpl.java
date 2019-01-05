package com.aptech.business.technical.technical.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.TechnicalBtnTypeEnum;
import com.aptech.business.component.dictionary.TechnicalStatusEnum;
import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.technical.technical.dao.TechnicalDao;
import com.aptech.business.technical.technical.domain.TechnicalEntity;
import com.aptech.business.technical.technical.exception.TechnicalException;
import com.aptech.business.technical.technical.exception.TechnicalExceptionType;
import com.aptech.business.technical.technicalPlandetail.domain.TechnicalPlandetailEntity;
import com.aptech.business.technical.technicalPlandetail.service.TechnicalPlandetailService;
import com.aptech.business.technical.technicalWork.domain.TechnicalWorkEntity;
import com.aptech.business.technical.technicalWork.service.TechnicalWorkService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
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
 * 技术监督应用管理服务实现类
 *
 * @author 
 * @created 2017-11-13 16:15:05
 * @lastModified 
 * @history
 *
 */
@Service("technicalService")
@Transactional
public class TechnicalServiceImpl extends AbstractBaseEntityOperation<TechnicalEntity> implements TechnicalService {
	
	@Autowired
	private TechnicalDao technicalDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private TechnicalWorkService technicalWorkService;
	@Autowired
	private TechnicalPlandetailService technicalPlandetailService;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<TechnicalEntity> getDao() {
		return technicalDao;
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
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	
	@Override
	public void addEntity(TechnicalEntity t) {
		SysUserEntity sysUserEntity=sysUserService.findById(Long.valueOf(t.getFillId()));
		t.setFillName(sysUserEntity.getName());
		t.setCreateDate(new Date());
		t.setStatus(Long.valueOf(DataStatusEnum.NORMAL.ordinal()));
		t.setSpStatus(TechnicalStatusEnum.DTJ.getCode());
		super.addEntity(t);
		//更新technicalwork表   把空着的technicalid 变成  真的
		TechnicalWorkEntity technicalWorkEntity=new TechnicalWorkEntity();
		technicalWorkEntity.setTechnicalId(t.getId());
		technicalWorkEntity.setUuidCode(t.getUuid());
		technicalWorkService.updateByMap("updateByUuid", technicalWorkEntity);
		
		//提交
		String saveOrsubmit=t.getSaveOrSubmit();
		String selectUser=t.getSelectUser();
		if(saveOrsubmit!=null&&!saveOrsubmit.equals("")){
			//准备启动流程
  		    String key=ProcessMarkEnum.TECHNICAL_PROCESS_KEY.getName();	
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, t.getId().toString(), vars);
			
		    TechnicalEntity technicalEntity=this.findById(t.getId());
		    technicalEntity.setId(t.getId());
		    technicalEntity.setSpStatus((TechnicalStatusEnum.DSJCSH.getCode()));
		    technicalEntity.setGzfzrtxWcqk(selectUser);
			this.updateEntity(technicalEntity);
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TECHNICAL.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
	@Override
	public ResultObj update(TechnicalEntity t, Long id) {
		TechnicalEntity  technicalEntity=this.findById(t.getId());
		technicalEntity.setUnitId(t.getUnitId());
		technicalEntity.setFillId(t.getFillId());
		SysUserEntity sysUserEntity=sysUserService.findById(Long.valueOf(t.getFillId()));
		technicalEntity.setFillName(sysUserEntity.getName());
		technicalEntity.setJdzyId(t.getJdzyId());
		technicalEntity.setWczt(t.getWczt());
		technicalEntity.setTime(t.getTime());
		technicalEntity.setGzzj(t.getGzzj());
		technicalEntity.setRemark(t.getRemark());
		technicalEntity.setFileid(t.getFileid());
		super.updateEntity(technicalEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TECHNICAL.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return new ResultObj();
	}
	
	@Override
	public void deleteEntity(Serializable id) {
		TechnicalEntity  technicalEntity=this.findById(id);
		technicalEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(technicalEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TECHNICAL.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	
	/**提交技术监督
	 * @Description:   
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@Override
	public void submit(String id,String selectUser) {
		//准备启动流程
	    String key=ProcessMarkEnum.TECHNICAL_PROCESS_KEY.getName();	
	    
		Map<String, Object> vars=new HashMap<String,Object>();
  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
	    actTaskService.startProcess(key, id, vars);
	    TechnicalEntity  technicalEntity=this.findById(Long.valueOf(id)); 
	    technicalEntity.setId(Long.valueOf(id));
	    technicalEntity.setSpStatus(TechnicalStatusEnum.DSJCSH.getCode()); 
	    technicalEntity.setGzfzrtxWcqk(selectUser);
		super.updateEntity(technicalEntity);
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
			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
			List<TechnicalEntity> list=this.findByCondition(conditions, null);
			if(list.isEmpty()){
				throw new TechnicalException(TechnicalExceptionType.WORKTICKET_UPDATEDELETE);
			}else{
				TechnicalEntity  technicalEntity=list.get(0);
				String workStatus=technicalEntity.getSpStatus().toString();
				if(!workStatus.equals(TechnicalStatusEnum.DTJ.getCode())&&!workStatus.equals(TechnicalStatusEnum.BHGZFZR.getCode())){
					throw new TechnicalException(TechnicalExceptionType.WORKTICKET_UPDATESTATUS);
				}
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
		for (int i=0;i<ids.size();i++) {
			TechnicalEntity  technicalEntity=this.findById(Long.valueOf(ids.get(i)));
			if(technicalEntity!=null){
				String workStatus=technicalEntity.getSpStatus().toString();
				if(!workStatus.equals(TechnicalStatusEnum.DTJ.getCode())){
					flag=false;
					break;
				}
			}
		}
		if (flag) {
			for (int i=0;i<ids.size();i++) {
				TechnicalEntity  technicalEntity=this.findById(Long.valueOf(ids.get(i)));
				technicalEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
				super.updateEntity(technicalEntity);
			}
		}else{
			throw new TechnicalException(TechnicalExceptionType.WORKTICKET_BATHDELETE);
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
			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
			List<TechnicalEntity> list=this.findByCondition(conditions, null);
			if(list.isEmpty()){
				throw new TechnicalException(TechnicalExceptionType.WORKTICKET_UPDATEDELETE);
			}else{
				TechnicalEntity  workTicket=list.get(0);
				String workStatus=workTicket.getSpStatus().toString();
				if(!workStatus.equals(TechnicalStatusEnum.DTJ.getCode())){
					throw new TechnicalException(TechnicalExceptionType.WORKTICKET_DELETESTATUS);
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
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<TechnicalEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new TechnicalException(TechnicalExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			TechnicalEntity  workTicket=list.get(0);
			String workStatus=workTicket.getSpStatus().toString();
			if(!workStatus.equals(TechnicalStatusEnum.DTJ.getCode())){
				throw new TechnicalException(TechnicalExceptionType.WORKTICKET_SUBMITSTATUS);
			}
		}
		return true;
	}

	@Override
	public void updateSpnrAgree(TechnicalEntity t,
			SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECH.getCode())){
			//更新主表状态
			TechnicalEntity technicalEntity=this.findById(t.getId());
			technicalEntity.setJxzgId(userEntity.getId());
			technicalEntity.setJxzgName(userEntity.getName());
			technicalEntity.setJxzgDate(new Date());
			technicalEntity.setSpStatus(TechnicalStatusEnum.DSJBZRSH.getCode());
			super.updateEntity(technicalEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode())){
			//更新主表状态
			TechnicalEntity technicalEntity=this.findById(t.getId());
			technicalEntity.setJxzrId(userEntity.getId());
			technicalEntity.setJxzrName(userEntity.getName());
			technicalEntity.setJxzrDate(new Date());
			technicalEntity.setSpStatus(TechnicalStatusEnum.DSJLDSH.getCode());
			super.updateEntity(technicalEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.LEADER.getCode())){
			//更新主表状态
			TechnicalEntity technicalEntity=this.findById(t.getId());
			technicalEntity.setSjbzrId(userEntity.getId());
			technicalEntity.setSjbzrName(userEntity.getName());
			technicalEntity.setSjbzrDate(new Date());
			technicalEntity.setSpStatus(TechnicalStatusEnum.END.getCode());
			super.updateEntity(technicalEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.REJECT.getCode())){
			//更新主表状态
			TechnicalEntity technicalEntity=this.findById(t.getId());
			technicalEntity.setSpStatus(TechnicalStatusEnum.DSJCSH.getCode());
			super.updateEntity(technicalEntity);
		}
	}

	@Override
	public void updateSpnrDisagree(TechnicalEntity t,
			SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECH.getCode())){
			TechnicalEntity technicalEntity=this.findById(t.getId());
			//更新主表状态为驳回
			technicalEntity.setJxzgId(null);
			technicalEntity.setJxzgName(null);
			technicalEntity.setJxzgDate(null);
			technicalEntity.setSpStatus(TechnicalStatusEnum.BHGZFZR.getCode());
			super.updateEntity(technicalEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode())){
			TechnicalEntity technicalEntity=this.findById(t.getId());
			//更新主表状态为驳回
			technicalEntity.setJxzgId(null);
			technicalEntity.setJxzgName(null);
			technicalEntity.setJxzgDate(null);
			technicalEntity.setJxzrId(null);
			technicalEntity.setJxzrName(null);
			technicalEntity.setJxzrDate(null);
			technicalEntity.setSpStatus(TechnicalStatusEnum.BHGZFZR.getCode());
			super.updateEntity(technicalEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.LEADER.getCode())){
			TechnicalEntity technicalEntity=this.findById(t.getId());
			//更新主表状态为驳回
			technicalEntity.setJxzgId(null);
			technicalEntity.setJxzgName(null);
			technicalEntity.setJxzgDate(null);
			technicalEntity.setJxzrId(null);
			technicalEntity.setJxzrName(null);
			technicalEntity.setJxzrDate(null);
			technicalEntity.setSjbzrId(null);
			technicalEntity.setSjbzrName(null);
			technicalEntity.setSjbzrDate(null);
			technicalEntity.setSpStatus(TechnicalStatusEnum.BHGZFZR.getCode());
			super.updateEntity(technicalEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.REJECT.getCode())){
			TechnicalEntity technicalEntity=this.findById(t.getId());
			technicalEntity.setSpStatus(TechnicalStatusEnum.GZPXQ.getCode());
			super.updateEntity(technicalEntity);
		}
	}
	
	
	/**
	 * @Description:   提交时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@Override
	public ResultObj saveValidate(String unitId,String jdzyId,String time,String technicalId,String uuid) {
		ResultObj resultObj = new ResultObj();
		issaveValidate(unitId,time,technicalId,uuid);
		return resultObj;
	}
	private boolean issaveValidate(String unitId,String time,String technicalId,String uuid) {
		if(!unitId.isEmpty()){//页面没填值，不走验证
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(unitId)));
		conditions.add(new Condition("t.C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, time));
		List<TechnicalEntity> list=this.findByCondition(conditions, null);
		if(technicalId!=null&&!technicalId.equals("")){//修改的时候
			conditions.clear();
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("C_TECHNICAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(technicalId)));
			conditions.add(new Condition("C_WORK_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(1)));
			List<TechnicalWorkEntity> listwork=technicalWorkService.findByCondition(conditions, null);
			if(listwork.isEmpty()){
				throw new TechnicalException(TechnicalExceptionType.WORKTICKET_NOTZEROQFR);
			}
			conditions.clear();
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("C_TECHNICAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(technicalId)));
			conditions.add(new Condition("C_WORK_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(2)));
			List<TechnicalWorkEntity> listworkTwo=technicalWorkService.findByCondition(conditions, null);
//			if(listworkTwo.isEmpty()){
//				throw new TechnicalException(TechnicalExceptionType.WORKTICKET_NOTZEROXKR);
//			}
			
			
			if(!list.isEmpty()){
				TechnicalEntity t=list.get(0);
				if(t.getId().toString().equals(technicalId)){
				}else{
					throw new TechnicalException(TechnicalExceptionType.WORKTICKET_NOCONTROLCARD);
				}
			}
		}else{//新增的时候
			conditions.clear();
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("C_UUID_CODE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, uuid));
			conditions.add(new Condition("C_WORK_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(1)));
			List<TechnicalWorkEntity> listwork=technicalWorkService.findByCondition(conditions, null);
			if(listwork.isEmpty()){
				throw new TechnicalException(TechnicalExceptionType.WORKTICKET_NOTZEROQFR);
			}
			conditions.clear();
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("C_UUID_CODE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, uuid));
			conditions.add(new Condition("C_WORK_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(2)));
			List<TechnicalWorkEntity> listworkTwo=technicalWorkService.findByCondition(conditions, null);
//			if(listworkTwo.isEmpty()){
//				throw new TechnicalException(TechnicalExceptionType.WORKTICKET_NOTZEROXKR);
//			}
			
			if(!list.isEmpty()){
				throw new TechnicalException(TechnicalExceptionType.WORKTICKET_NOCONTROLCARD);
			}
		}
		}
		return true;
	}
	
	/**
	 * @Description:   审批时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@Override
	public ResultObj approveValidate(String technicalId) {
		ResultObj resultObj = new ResultObj();
		isApproveValidate(technicalId);
		return resultObj;
	}
	private boolean isApproveValidate(String technicalId) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_TECHNICAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(technicalId)));
		conditions.add(new Condition("C_WORK_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(1)));
		List<TechnicalWorkEntity> listwork=technicalWorkService.findByCondition(conditions, null);
		if(!listwork.isEmpty()){
			for (TechnicalWorkEntity technicalWorkEntity : listwork) {
				conditions.clear();
				conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
				conditions.add(new Condition("C_TECHNICAL_WORKID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, technicalWorkEntity.getId()));
				List<TechnicalPlandetailEntity> planlist=technicalPlandetailService.findByCondition(conditions, null);
				if(planlist.isEmpty()){
					throw new TechnicalException(TechnicalExceptionType.WORKTICKET_NOTOUTTHREEQFR);
				}
			}
		}
		
		conditions.clear();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_TECHNICAL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(technicalId)));
		conditions.add(new Condition("C_WORK_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(2)));
		List<TechnicalWorkEntity> listworkTwo=technicalWorkService.findByCondition(conditions, null);
		if(!listworkTwo.isEmpty()){
			for (TechnicalWorkEntity technicalWorkEntity : listworkTwo) {
				conditions.clear();
				conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
				conditions.add(new Condition("C_TECHNICAL_WORKID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, technicalWorkEntity.getId()));
				List<TechnicalPlandetailEntity> planlist=technicalPlandetailService.findByCondition(conditions, null);
//				if(planlist.isEmpty()){
//					throw new TechnicalException(TechnicalExceptionType.WORKTICKET_NOTOUTTHREEXKR);
//				}
			}
		}
		return true;
	}
	
	/**
	 * @Description:   撤销时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@Override
	public ResultObj cexiaoValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		iscexiaoValidate(id);
		return resultObj;
	}
	private boolean iscexiaoValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<TechnicalEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new TechnicalException(TechnicalExceptionType.WORKTICKET_UPDATEDELETE);
		}
//		else{
//			TechnicalEntity  workTicket=list.get(0);
//			String workStatus=workTicket.getSpStatus().toString();
//			if(!workStatus.equals(TechnicalStatusEnum.DFZRTXWCQK.getCode())){
//				throw new TechnicalException(TechnicalExceptionType.WORKTICKET_JIANDINGSTATUS);
//			}
//		}
		return true;
	}
	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TECHNICAL.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}

	@Override
	public void updateSpnrDisagreeUp(TechnicalEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		 if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode())){
			TechnicalEntity technicalEntity=this.findById(t.getId());
			//更新主表状态为驳回
			technicalEntity.setJxzrId(null);
			technicalEntity.setJxzrName(null);
			technicalEntity.setJxzrDate(null);
			technicalEntity.setSpStatus(TechnicalStatusEnum.DSJCSH.getCode());
			super.updateEntity(technicalEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.LEADER.getCode())){
			TechnicalEntity technicalEntity=this.findById(t.getId());
			//更新主表状态为驳回
			technicalEntity.setSjbzrId(null);
			technicalEntity.setSjbzrName(null);
			technicalEntity.setSjbzrDate(null);
			technicalEntity.setSpStatus(TechnicalStatusEnum.DSJBZRSH.getCode());
			super.updateEntity(technicalEntity);
		}
	}
}