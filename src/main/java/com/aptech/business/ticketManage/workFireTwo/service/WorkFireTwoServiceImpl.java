package com.aptech.business.ticketManage.workFireTwo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.WorkFireTwoBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkFireTwoStatusEnum;
import com.aptech.business.ticketManage.workFireTwo.dao.WorkFireTwoDao;
import com.aptech.business.ticketManage.workFireTwo.domain.WorkFireTwoEntity;
import com.aptech.business.ticketManage.workTicketFireTwo.domain.WorkTicketFireTwoEntity;
import com.aptech.business.ticketManage.workTicketFireTwo.service.WorkTicketFireTwoService;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.fourcode.service.FourCodeService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 电气工作票应用管理服务实现类
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Service("workFireTwoService")
@Transactional
public class WorkFireTwoServiceImpl extends AbstractBaseEntityOperation<WorkFireTwoEntity> implements WorkFireTwoService {
	
	@Autowired
	private WorkFireTwoDao workFireTwoDao;
	@Autowired
	private WorkTicketFireTwoService workTicketFireTwoService;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private FourCodeService fourCodeService;
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	@Override
	public IBaseEntityOperation<WorkFireTwoEntity> getDao() {
		return workFireTwoDao;
	}
	@Override
	public void updateSpnrAgree(WorkFireTwoEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkFireTwoBtnTypeEnum.QF.getCode())){
		    WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
		    workFireEntity.setSignerId(userEntity.getId());
		    workFireEntity.setSignerName(userEntity.getName());
		    workFireEntity.setSignerDate(new Date());
			super.updateEntity(workFireEntity);
			//更新主表状态
			WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
			workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.TOBEHQSH.getCode()));
			workTicketFireTwoService.updateEntity(workTicketFireEntity);
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(1);
			workTicketUserRelEntity.setWorkTicketId(workFireEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkFireTwoBtnTypeEnum.HQF.getCode())){
		    WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
		    workFireEntity.setMeetSignId(userEntity.getId());
		    workFireEntity.setMeetSignName(userEntity.getName());
		    workFireEntity.setMeetSignDate(new Date());
			super.updateEntity(workFireEntity);
			//更新主表状态
			WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
			workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.TOBEXFSH.getCode()));
			workTicketFireTwoService.updateEntity(workTicketFireEntity);
		}else if(spFlag.equals(WorkFireTwoBtnTypeEnum.XFJHR.getCode())){
			WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			workFireEntity.setOtherApproveFireId(userEntity.getId());
			workFireEntity.setOtherApproveFire(userEntity.getName());
		    workFireEntity.setOtherApproveFireDate(new Date());
			super.updateEntity(workFireEntity);
			//更新主表状态为未执行
			WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
			workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.TOBEAJSH.getCode()));
			workTicketFireTwoService.updateEntity(workTicketFireEntity);
		}else if(spFlag.equals(WorkFireTwoBtnTypeEnum.AJBM.getCode())){
		    WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setOtherApproveSafeId(userEntity.getId());
            workFireEntity.setOtherApproveSafe(userEntity.getName());
            workFireEntity.setOtherApproveSafeDate(new Date());
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.EXESURE.getCode()));
            workTicketFireTwoService.updateEntity(workTicketFireEntity);
		}else if(spFlag.equals(WorkFireTwoBtnTypeEnum.SP.getCode())){
		    WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
		    workFireEntity.setDutyMonitorId(userEntity.getId());
		    workFireEntity.setDutyMonitorName(userEntity.getName());
		    workFireEntity.setDutyMonitorDate(new Date());
		   
            super.updateEntity(workFireEntity);
            
            WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
            //工作票编号开始
  	        Map<String, Object> codeparams=new HashMap<String, Object> ();
//  	        SysUnitEntity sysunit=sysUnitService.findById(userEntity.getUnitId());
  	        SysUnitEntity sysunit=sysUnitService.findById(Long.parseLong(workTicketFireEntity.getUnitNameId()));
  			codeparams.put("workcode", sysunit.getNameAB());
  			codeparams.put("workTicketType", "9");
  			codeparams.put("YM", new Date());
  			String code=fourCodeService.getBusinessCode("工作票编号", codeparams);
  			String str="";
  			StringBuilder sb=new StringBuilder(code);
  			sb.replace(3, 5, str);
  			String nowCode=sb.toString();
  			//工作票编号结束
            //更新主表状态为未执行
            workTicketFireEntity.setCode(nowCode);
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.XFSURE.getCode()));
            workTicketFireTwoService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireTwoBtnTypeEnum.XK.getCode())){
            WorkFireTwoEntity elentity = this.findById(t.getId());//查询这个表的实体
            WorkTicketFireTwoEntity workEntity=workTicketFireTwoService.findById(elentity.getWorkticketId());
            
            elentity.setApproveStarttime(t.getApproveStarttime());
            elentity.setOtherPicSignId(workEntity.getGuarderId());
            elentity.setOtherPicSign(workEntity.getGuarderName());
            
            elentity.setOtherExecutorSignId(t.getOtherExecutorSignId());
            if (t.getOtherExecutorSignId()!=null) {
            	SysUserEntity fzrEntity=sysUserService.findById(t.getOtherExecutorSignId());
            	elentity.setOtherExecutorSign(fzrEntity.getName());
			}
            
            elentity.setRunFireSignId(t.getRunFireSignId());
            if (t.getRunFireSignId()!=null) {
            SysUserEntity fzrEntity2=sysUserService.findById(t.getRunFireSignId());
            elentity.setRunFireSignName(fzrEntity2.getName());
            } 
            elentity.setOther(t.getOther());
            super.updateEntity(elentity);
           
            //更新工作票表
            workEntity.setChangeAllowDate(t.getQksjZhu());
            workEntity.setChangeAllowId(userEntity.getId());
            workEntity.setChangeAllowName(userEntity.getName());
            workEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.FZRSURE.getCode()));
            workTicketFireTwoService.updateEntity(workEntity);
            
            //保存三种人数据
 			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
 			workTicketUserRelEntity.setType(3);
 			workTicketUserRelEntity.setWorkTicketId(elentity.getWorkticketId());
 			workTicketUserRelEntity.setUserId(userEntity.getId());//许可人
 			workTicketUserRelService.addEntity(workTicketUserRelEntity);
     			
        }else if(spFlag.equals(WorkFireTwoBtnTypeEnum.XFJHRXK.getCode())){
            WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setOtherFireSignId(userEntity.getId());
            workFireEntity.setOtherFireSign(userEntity.getName());
            workFireEntity.setOtherFireSignDate(new Date());
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.DHRSURE.getCode()));
            workTicketFireTwoService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireTwoBtnTypeEnum.GZJD.getCode())){//工作交底
			//更新电气第一种票表
        	 WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
        	 workFireEntity.setWorkDisclosure(t.getWorkDisclosure());
			super.updateEntity(workFireEntity);
		}else if(spFlag.equals(WorkFireTwoBtnTypeEnum.YS.getCode())){
        	 WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			
        	 workFireEntity.setEndHand(t.getEndHand());
        	 workFireEntity.setEndStandIndex(t.getEndStandIndex());
        	 workFireEntity.setEndStand(t.getEndStand());
        	 workFireEntity.setRemark(t.getRemark());
			super.updateEntity(workFireEntity);
			
			WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
			//更新工作票表
			workTicketFireEntity.setEndTime(t.getEndTimeZhu());
		 	workTicketFireEntity.setEndPicId(t.getEndPicIdZhu());//动火工作负责人
			workTicketFireEntity.setEndPicName(t.getEndPicNameZhu());
			//workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.LDSURE.getCode()));
			workTicketFireTwoService.updateEntity(workTicketFireEntity);

		}else if(spFlag.equals(WorkFireTwoBtnTypeEnum.DHFZRYS.getCode())){
            WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            
            workFireEntity.setOtherendExecutorSignId(t.getOtherendExecutorSignId());//动火执行人 
            SysUserEntity entity=sysUserService.findById(t.getOtherendExecutorSignId());
            workFireEntity.setOtherendExecutorSign(entity.getName());
            
            workFireEntity.setFireUserId(userEntity.getId());
            workFireEntity.setFireUserName(userEntity.getName());
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
            
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.TOEXE.getCode()));
            workTicketFireTwoService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireTwoBtnTypeEnum.XFJHRZJ.getCode())){
            WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setOtherendFireSignId(userEntity.getId());
            workFireEntity.setOtherendFireSign(userEntity.getName());
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.JHRCHECK.getCode()));
            workTicketFireTwoService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireTwoBtnTypeEnum.XKZJ.getCode())){
            WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            
        	WorkTicketFireTwoEntity workEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndAllowId(t.getEndAllowIdZhu());
			workEntity.setEndAllowName(t.getEndAllowNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.END.getCode()));
			workEntity.setChangeAllowIdea(t.getFileid());
			workTicketFireTwoService.updateEntity(workEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workFireEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//许可人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
            
        }else if(spFlag.equals(WorkFireTwoBtnTypeEnum.ZTJ.getCode())){ //在提交
			//更新主表状态
			WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(t.getId());
			workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.TOBEISSUED.getCode()));
			workTicketFireTwoService.updateEntity(workTicketFireEntity);
		}
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(WorkFireTwoEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkFireTwoBtnTypeEnum.QF.getCode())){
			WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			//更新从表信息
			workFireEntity.setSignerId(null);
			workFireEntity.setSignerName(null);
			workFireEntity.setSignerDate(null);
			super.updateEntity(workFireEntity);
			//更新主表状态为驳回
			WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
			workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.FZRCHECK.getCode()));
			workTicketFireTwoService.updateEntity(workTicketFireEntity);
			
		}else if(spFlag.equals(WorkFireTwoBtnTypeEnum.HQF.getCode())){
		    WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
		    workFireEntity.setMeetSignId(null);
		    workFireEntity.setMeetSignName(null);
		    workFireEntity.setMeetSignDate(null);
		    
		    //签发人清空
			workFireEntity.setSignerId(null);
			workFireEntity.setSignerName(null);
			workFireEntity.setSignerDate(null);
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.FZRCHECK.getCode()));
            workTicketFireTwoService.updateEntity(workTicketFireEntity);
		}else if(spFlag.equals(WorkFireTwoBtnTypeEnum.XFJHR.getCode())){
            WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setOtherApproveFireId(null);
			workFireEntity.setOtherApproveFire(null);
		    workFireEntity.setOtherApproveFireDate(null);  
		    
		    //会签人清空
		    workFireEntity.setMeetSignId(null);
		    workFireEntity.setMeetSignName(null);
		    workFireEntity.setMeetSignDate(null);
		    
		    //签发人清空
			workFireEntity.setSignerId(null);
			workFireEntity.setSignerName(null);
			workFireEntity.setSignerDate(null);
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.FZRCHECK.getCode()));
            workTicketFireTwoService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireTwoBtnTypeEnum.AJBM.getCode())){
            WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setOtherApproveSafeId(null);
            workFireEntity.setOtherApproveSafe(null);
            workFireEntity.setOtherApproveSafeDate(null);
            //消防监护清空
                workFireEntity.setOtherApproveFireId(null);
       			workFireEntity.setOtherApproveFire(null);
       		    workFireEntity.setOtherApproveFireDate(null);  
       		    
       		    //会签人清空
       		    workFireEntity.setMeetSignId(null);
       		    workFireEntity.setMeetSignName(null);
       		    workFireEntity.setMeetSignDate(null);
       		    
       		    //签发人清空
       			workFireEntity.setSignerId(null);
       			workFireEntity.setSignerName(null);
       			workFireEntity.setSignerDate(null);
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.FZRCHECK.getCode()));
            workTicketFireTwoService.updateEntity(workTicketFireEntity);
		}else if(spFlag.equals(WorkFireTwoBtnTypeEnum.SP.getCode())){
			WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setDutyMonitorId(null);
		    workFireEntity.setDutyMonitorName(null);
		    workFireEntity.setDutyMonitorDate(null);
		    //清空安监部
	        workFireEntity.setOtherApproveSafeId(null);
            workFireEntity.setOtherApproveSafe(null);
            workFireEntity.setOtherApproveSafeDate(null);
            //消防监护清空
            workFireEntity.setOtherApproveFireId(null);
   			workFireEntity.setOtherApproveFire(null);
   		    workFireEntity.setOtherApproveFireDate(null);  
       		    
   		    //会签人清空
   		    workFireEntity.setMeetSignId(null);
   		    workFireEntity.setMeetSignName(null);
   		    workFireEntity.setMeetSignDate(null);
   		    
   		    //签发人清空
   			workFireEntity.setSignerId(null);
   			workFireEntity.setSignerName(null);
   			workFireEntity.setSignerDate(null);
		    super.updateEntity(workFireEntity);
		    
		    WorkTicketFireTwoEntity workEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
            workEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.FZRCHECK.getCode()));
            workTicketFireTwoService.updateEntity(workEntity);
        }else if(spFlag.equals(WorkFireTwoBtnTypeEnum.XK.getCode())){
			WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			workFireEntity.setApproveStarttime(null);
			workFireEntity.setOtherPicSignId(null);
			workFireEntity.setOtherPicSign(null);
			workFireEntity.setOtherExecutorSignId(null);
			workFireEntity.setOtherExecutorSign(null);
			workFireEntity.setRunFireSignId(null);
			workFireEntity.setRunFireSignName(null);
			workFireEntity.setOther("");
      
			//运行值班人员
            workFireEntity.setDutyMonitorId(null);
		    workFireEntity.setDutyMonitorName(null);
		    workFireEntity.setDutyMonitorDate(null);
		    //清空安监部
	        workFireEntity.setOtherApproveSafeId(null);
            workFireEntity.setOtherApproveSafe(null);
            workFireEntity.setOtherApproveSafeDate(null);
            //消防监护清空
            workFireEntity.setOtherApproveFireId(null);
   			workFireEntity.setOtherApproveFire(null);
   		    workFireEntity.setOtherApproveFireDate(null);  
       		    
   		    //会签人清空
   		    workFireEntity.setMeetSignId(null);
   		    workFireEntity.setMeetSignName(null);
   		    workFireEntity.setMeetSignDate(null);
   		    
   		    //签发人清空
   			workFireEntity.setSignerId(null);
   			workFireEntity.setSignerName(null);
   			workFireEntity.setSignerDate(null);
		    super.updateEntity(workFireEntity);
		    
		    //更新主票状态
		    WorkTicketFireTwoEntity workEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
            workEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.FZRCHECK.getCode()));
            workEntity.setChangeAllowDate(null);
            workEntity.setChangeAllowId(null);
            workEntity.setChangeAllowName(null);
            workTicketFireTwoService.updateEntity(workEntity);
        }else if(spFlag.equals(WorkFireTwoBtnTypeEnum.XFJHRZJ.getCode())){
            WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setOtherendFireSignId(null);
            workFireEntity.setOtherendFireSign(null);
 			
         	 workFireEntity.setEndHand(null);
         	 workFireEntity.setEndStandIndex(null);
         	 workFireEntity.setEndStand(null);
         	 workFireEntity.setRemark("");
 			super.updateEntity(workFireEntity);
 			
 			WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
 			//更新工作票表
 			workTicketFireEntity.setEndTime(null);
 			workTicketFireEntity.setEndPicId(null);
 			workTicketFireEntity.setEndPicName(null);
 
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.DHRSURE.getCode()));
            workTicketFireTwoService.updateEntity(workTicketFireEntity);
        }
		else if(spFlag.equals(WorkFireTwoBtnTypeEnum.XKZJ.getCode())){
	        WorkFireTwoEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
	        
	        workFireEntity.setEndHand(null);
        	workFireEntity.setEndStandIndex(null);
        	workFireEntity.setEndStand(null);
            workFireEntity.setRemark("");
            //清理消防监护
            workFireEntity.setOtherendFireSignId(null);
            workFireEntity.setOtherendFireSign(null);
			super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(workFireEntity.getWorkticketId());
            
            workTicketFireEntity.setEndTime(null);
            workTicketFireEntity.setEndPicId(null);
            workTicketFireEntity.setEndPicName(null);
            workTicketFireEntity.setEndAllowId(null);
            workTicketFireEntity.setEndAllowName(null);
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.DHRSURE.getCode()));
            workTicketFireTwoService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireTwoBtnTypeEnum.FP.getCode())){
            //更新主表状态为废票
            WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(t.getId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.WORKSTATUS_TYPE_INVALID.getCode()));
            workTicketFireTwoService.updateEntity(workTicketFireEntity);
        }
	}
	/**提交电气第一种工作票
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           2017年6月12日 下午6:10:01 
	 * @throws         Exception
	 */
	@Override
	public void submit(String id,String selectUser) {
			//准备启动流程
  		    String key=ProcessMarkEnum.WORK_TICKET_FIRE_TWO_PROCESS_KEY.getName();	
  		    
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, id, vars);
			
			WorkTicketFireTwoEntity workTicketFireEntity=workTicketFireTwoService.findById(Long.valueOf(id));
			workTicketFireEntity.setId(Long.valueOf(id));
			workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.TOBEISSUED.getCode())); 
			
			workTicketFireTwoService.updateEntity(workTicketFireEntity);
	}
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkFireTwoEntity t, Long id) {
		WorkFireTwoEntity workFireEntity=this.findById(id);
		workFireEntity.setRemark(t.getRemark());
		workFireEntity.setOther(t.getOther());
		this.updateEntity(workFireEntity);
		return new ResultObj();
	}
}