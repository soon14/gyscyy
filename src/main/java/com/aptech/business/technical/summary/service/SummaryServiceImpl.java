package com.aptech.business.technical.summary.service;

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
import com.aptech.business.component.dictionary.SummaryStatusEnum;
import com.aptech.business.component.dictionary.TechnicalBtnTypeEnum;
import com.aptech.business.component.dictionary.TechnicalStatusEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.technical.summary.dao.SummaryDao;
import com.aptech.business.technical.summary.domain.SummaryEntity;
import com.aptech.business.technical.summary.exception.SummaryException;
import com.aptech.business.technical.summary.exception.SummaryExceptionType;
import com.aptech.business.technical.technical.domain.TechnicalEntity;
import com.aptech.business.technical.technical.exception.TechnicalException;
import com.aptech.business.technical.technical.exception.TechnicalExceptionType;
import com.aptech.business.technical.technicalPlandetail.domain.TechnicalPlandetailEntity;
import com.aptech.business.technical.technicalWork.domain.TechnicalWorkEntity;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
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
 * 技术监督总结应用管理服务实现类
 *
 * @author 
 * @created 2018-03-14 14:02:22
 * @lastModified 
 * @history
 *
 */
@Service("summaryService")
@Transactional
public class SummaryServiceImpl extends AbstractBaseEntityOperation<SummaryEntity> implements SummaryService {
	
	@Autowired
	private SummaryDao summaryDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired 
	private SysUnitService sysUnitService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<SummaryEntity> getDao() {
		return summaryDao;
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
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<SummaryEntity> resultList = (List<SummaryEntity>) super.findByCondition(conditions, page);
		for (SummaryEntity summaryEntity : resultList) {
			String unitId=summaryEntity.getUnitId();
			SysUnitEntity sysUnitEntity=sysUnitService.findById(Long.valueOf(unitId));
			summaryEntity.setUnitName(sysUnitEntity.getName());
		}
		return (List<O>) resultList;
	}
	
	//保存
	@Override
	public void addEntity(SummaryEntity t) {
		SysUserEntity sysUserEntity=sysUserService.findById(Long.valueOf(t.getTbrId()));
		t.setTbrName(sysUserEntity.getName());
		t.setCreateDate(new Date());
		t.setStatus(Long.valueOf(DataStatusEnum.NORMAL.ordinal()));
		t.setWorkStatus(SummaryStatusEnum.DTJ.getCode());
		super.addEntity(t);
		//并提交
		String saveOrsubmit=t.getSaveOrSubmit();
		String selectUser=t.getSelectUser();
		if(saveOrsubmit!=null&&!saveOrsubmit.equals("")){
			//准备启动流程
  		    String key=ProcessMarkEnum.TECHNICAL_SUMMARY_PROCESS_KEY.getName();	
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, t.getId().toString(), vars);
			
		    SummaryEntity summaryEntity=this.findById(t.getId());
		    summaryEntity.setId(t.getId());
		    summaryEntity.setWorkStatus((SummaryStatusEnum.DSJBZRSH.getCode()));
		    summaryEntity.setNextshr(selectUser);
			this.updateEntity(summaryEntity);
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUMMARY.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	//修改方法
	@Override
	public ResultObj update(SummaryEntity t, Long id) {
		SummaryEntity  summaryEntity=this.findById(t.getId());
		summaryEntity.setUnitId(t.getUnitId());
		summaryEntity.setTbrId(t.getTbrId());
		SysUserEntity sysUserEntity=sysUserService.findById(Long.valueOf(t.getTbrId()));
		summaryEntity.setTbrName(sysUserEntity.getName());
		summaryEntity.setTime(t.getTime());
		summaryEntity.setFileid(t.getFileid());
		summaryEntity.setSjmc(t.getSjmc());
		summaryEntity.setZjnr(t.getZjnr());
		super.updateEntity(summaryEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUMMARY.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return new ResultObj();
	}
	//删除
	@Override
	public void deleteEntity(Serializable id) {
		SummaryEntity summaryEntity=this.findById(id);
		summaryEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(summaryEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUMMARY.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
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
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
			List<SummaryEntity> list=this.findByCondition(conditions, null);
			if(list.isEmpty()){
				throw new SummaryException(SummaryExceptionType.WORKTICKET_UPDATEDELETE);
			}else{
				SummaryEntity  summaryEntity=list.get(0);
				String workStatus=summaryEntity.getWorkStatus().toString();
				if(!workStatus.equals(SummaryStatusEnum.DTJ.getCode())&&!workStatus.equals(SummaryStatusEnum.BHGZFZR.getCode())){
					throw new SummaryException(SummaryExceptionType.WORKTICKET_UPDATESTATUS);
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
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUMMARY.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
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
			SummaryEntity  summaryEntity=this.findById(Long.valueOf(ids.get(i)));
			if(summaryEntity!=null){
				String workStatus=summaryEntity.getWorkStatus().toString();
				if(!workStatus.equals(SummaryStatusEnum.DTJ.getCode())){
					flag=false;
					break;
				}
			}
		}
		if (flag) {
			for (int i=0;i<ids.size();i++) {
				SummaryEntity  summaryEntity=this.findById(Long.valueOf(ids.get(i)));
				summaryEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
				super.updateEntity(summaryEntity);
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
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
			List<SummaryEntity> list=this.findByCondition(conditions, null);
			if(list.isEmpty()){
				throw new SummaryException(SummaryExceptionType.WORKTICKET_UPDATEDELETE);
			}else{
				SummaryEntity  workTicket=list.get(0);
				String workStatus=workTicket.getWorkStatus().toString();
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
		List<SummaryEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new SummaryException(SummaryExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			SummaryEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(SummaryStatusEnum.DTJ.getCode())){
				throw new SummaryException(SummaryExceptionType.WORKTICKET_SUBMITSTATUS);
			}
		}
		return true;
	}


	@Override
	public void submit(String id, String selectUser) {
		// TODO Auto-generated method stub
		//准备启动流程
	    String key=ProcessMarkEnum.TECHNICAL_SUMMARY_PROCESS_KEY.getName();	
	    
		Map<String, Object> vars=new HashMap<String,Object>();
  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
	    actTaskService.startProcess(key, id, vars);
	    SummaryEntity  summaryEntity=this.findById(Long.valueOf(id)); 
	    summaryEntity.setId(Long.valueOf(id));
	    summaryEntity.setWorkStatus(TechnicalStatusEnum.DSJCSH.getCode()); 
		super.updateEntity(summaryEntity);
	}


	@Override
	public void updateSpnrAgree(SummaryEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECH.getCode())){
			//更新主表状态
			SummaryEntity summaryEntity=this.findById(t.getId());
			summaryEntity.setWorkStatus(TechnicalStatusEnum.DSJBZRSH.getCode());
			super.updateEntity(summaryEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode())){
			//更新主表状态
			SummaryEntity summaryEntity=this.findById(t.getId());
			summaryEntity.setWorkStatus(TechnicalStatusEnum.DSJLDSH.getCode());
			super.updateEntity(summaryEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.LEADER.getCode())){
			//更新主表状态
			SummaryEntity summaryEntity=this.findById(t.getId());
			summaryEntity.setWorkStatus(TechnicalStatusEnum.END.getCode());
			super.updateEntity(summaryEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.REJECT.getCode())){
			//更新主表状态
			SummaryEntity summaryEntity=this.findById(t.getId());
			summaryEntity.setWorkStatus(TechnicalStatusEnum.DSJCSH.getCode());
			super.updateEntity(summaryEntity);
		}
	}


	@Override
	public void updateSpnrDisagree(SummaryEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECH.getCode())){
			SummaryEntity summaryEntity=this.findById(t.getId());
			summaryEntity.setWorkStatus(TechnicalStatusEnum.BHGZFZR.getCode());
			super.updateEntity(summaryEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode())){
			SummaryEntity summaryEntity=this.findById(t.getId());
			//更新主表状态为驳回
			summaryEntity.setWorkStatus(TechnicalStatusEnum.BHGZFZR.getCode());
			super.updateEntity(summaryEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.LEADER.getCode())){
			SummaryEntity summaryEntity=this.findById(t.getId());
			//更新主表状态为驳回
			summaryEntity.setWorkStatus(TechnicalStatusEnum.BHGZFZR.getCode());
			super.updateEntity(summaryEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.REJECT.getCode())){
			SummaryEntity summaryEntity=this.findById(t.getId());
			summaryEntity.setWorkStatus(TechnicalStatusEnum.GZPXQ.getCode());
			super.updateEntity(summaryEntity);
		}
	}


	@Override
	public void updateSpnrDisagreeUp(SummaryEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		 if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode())){
			 SummaryEntity summaryEntity=this.findById(t.getId());
			//更新主表状态为驳回
			summaryEntity.setWorkStatus(TechnicalStatusEnum.DSJCSH.getCode());
			super.updateEntity(summaryEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.LEADER.getCode())){
			SummaryEntity summaryEntity=this.findById(t.getId());
			//更新主表状态为驳回
			summaryEntity.setWorkStatus(TechnicalStatusEnum.DSJBZRSH.getCode());
			super.updateEntity(summaryEntity);
		}
	}



	
}