package com.aptech.business.ticketManage.workFire.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkFireBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkFireStatusEnum;
import com.aptech.business.ticketManage.workFire.dao.WorkFireDao;
import com.aptech.business.ticketManage.workFire.domain.WorkFireEntity;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicket.service.WorkTicketService;
import com.aptech.business.ticketManage.workTicketFire.domain.WorkTicketFireEntity;
import com.aptech.business.ticketManage.workTicketFire.service.WorkTicketFireService;
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
@Service("workFireService")
@Transactional
public class WorkFireServiceImpl extends AbstractBaseEntityOperation<WorkFireEntity> implements WorkFireService {
	
	@Autowired
	private WorkFireDao workFireDao;
	@Autowired
	private WorkTicketFireService workTicketFireService;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private FourCodeService fourCodeService;
	@Autowired 
	private WorkTicketService workTicketService;
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	@Override
	public IBaseEntityOperation<WorkFireEntity> getDao() {
		return workFireDao;
	}
	@Override
	public void updateSpnrAgree(WorkFireEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkFireBtnTypeEnum.QF.getCode())){
		    WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
		    workFireEntity.setSignerId(userEntity.getId());
		    workFireEntity.setSignerName(userEntity.getName());
		    workFireEntity.setSignerDate(new Date());
			super.updateEntity(workFireEntity);
			//更新主表状态
			WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
			workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.TOBEXFSH.getCode()));
			workTicketFireService.updateEntity(workTicketFireEntity);
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(1);
			workTicketUserRelEntity.setWorkTicketId(workFireEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkFireBtnTypeEnum.HQF.getCode())){
			WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
		    workFireEntity.setMeetSignId(userEntity.getId());
		    workFireEntity.setMeetSignName(userEntity.getName());
		    workFireEntity.setMeetSignDate(new Date());
			super.updateEntity(workFireEntity);
			//更新主表状态为未执行
			WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
			workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.TOBEJHSP.getCode()));
			workTicketFireService.updateEntity(workTicketFireEntity);
		}else if(spFlag.equals(WorkFireBtnTypeEnum.XFJHR.getCode())){
		    WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			workFireEntity.setOtherApproveFireId(userEntity.getId());
			workFireEntity.setOtherApproveFire(userEntity.getName());
		    workFireEntity.setOtherApproveFireDate(new Date());
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.TOBEAJSH.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
		}else if(spFlag.equals(WorkFireBtnTypeEnum.AJBM.getCode())){
		    WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
		    workFireEntity.setOtherApproveSafeId(userEntity.getId());
            workFireEntity.setOtherApproveSafe(userEntity.getName());
            workFireEntity.setOtherApproveSafeDate(new Date());
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.TOBELDSH.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.AQZJ.getCode())){
            WorkFireEntity elentity = this.findById(t.getId());//查询这个表的实体
            
            elentity.setSafeDirctorDate(new Date());
            elentity.setSafeDirctorId(userEntity.getId());
            elentity.setSafeDirctorName(userEntity.getName());
            super.updateEntity(elentity);
            
            WorkTicketFireEntity workEntity=workTicketFireService.findById(elentity.getWorkticketId());
            workEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.ALLOW.getCode()));
            workTicketFireService.updateEntity(workEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.FGSCFZR.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setOtherLederSignId(userEntity.getId());
            workFireEntity.setOtherLederSign(userEntity.getName());
            workFireEntity.setOtherLederSignDate(new Date());
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.EXESURE.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.SP.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setDutyMonitorId(userEntity.getId());
            workFireEntity.setDutyMonitorName(userEntity.getName());
            workFireEntity.setDutyMonitorDate(new Date());
            super.updateEntity(workFireEntity);
            
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            //许可的时候需要生产工作票编号
            //工作票编号开始
  	        Map<String, Object> codeparams=new HashMap<String, Object> ();
//  	        SysUnitEntity sysunit=sysUnitService.findById(userEntity.getUnitId());
  	        SysUnitEntity sysunit=sysUnitService.findById(Long.parseLong(workTicketFireEntity.getUnitNameId()));
  			codeparams.put("workcode", sysunit.getNameAB());
  			codeparams.put("workTicketType", "8");
  			codeparams.put("YM", new Date());
  			String code=fourCodeService.getBusinessCode("工作票编号", codeparams);
  			String str="";
  			StringBuilder sb=new StringBuilder(code);
  			sb.replace(3, 5, str);
  			String nowCode=sb.toString();
  			//工作票编号结束
  			workTicketFireEntity.setCode(nowCode);
            //更新主表状态为未执行
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.XFSURE.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.XK.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            //更新主表状态为未执行
            WorkTicketFireEntity workEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            
            workFireEntity.setApproveStarttime(t.getApproveStarttime());//允许动火时间
            workFireEntity.setOtherPicSignId(workEntity.getGuarderId());
            workFireEntity.setOtherPicSign(workEntity.getGuarderName());
            
            workFireEntity.setOtherExecutorSignId(t.getOtherExecutorSignId());
            if (t.getOtherExecutorSignId()!=null) {
            	SysUserEntity fzrEntity=sysUserService.findById(t.getOtherExecutorSignId());
            	workFireEntity.setOtherExecutorSign(fzrEntity.getName());
			}
            
            workFireEntity.setRunFireSignId(t.getRunFireSignId());
            if (t.getRunFireSignId()!=null) {
            SysUserEntity fzrEntity2=sysUserService.findById(t.getRunFireSignId());
            workFireEntity.setRunFireSignName(fzrEntity2.getName());
            }
            
            workFireEntity.setOtherGroupSignId(t.getOtherGroupSignId());
            if (t.getOtherGroupSignId()!=null) {
            	SysUserEntity fzrEntity3=sysUserService.findById(t.getOtherGroupSignId());
            	workFireEntity.setOtherGroupSign(fzrEntity3.getName() );
			}
            
            workFireEntity.setOtherSafeSignId(t.getOtherSafeSignId());
            if (t.getOtherSafeSignId()!=null) {
            SysUserEntity fzrEntity4=sysUserService.findById(t.getOtherSafeSignId());
            workFireEntity.setOtherSafeSign(fzrEntity4.getName());
            }
            workFireEntity.setRunSafeDirectorId(t.getRunSafeDirectorId());
            if (t.getRunSafeDirectorId()!=null) {
            SysUserEntity fzrEntity5=sysUserService.findById(t.getRunSafeDirectorId());
            workFireEntity.setRunSafeDirector(fzrEntity5.getName());
            }
            workFireEntity.setRunLederSignId(t.getRunLederSignId());
            if (t.getRunLederSignId()!=null) {
            SysUserEntity fzrEntity6=sysUserService.findById(t.getRunLederSignId());
            workFireEntity.setRunLederSign(fzrEntity6.getName());
            }
            workFireEntity.setOther(t.getOther());
            super.updateEntity(workFireEntity);
       
            //更新工作票表
            workEntity.setChangeAllowDate(t.getQksjZhu());
            workEntity.setChangeAllowId(userEntity.getId());
            workEntity.setChangeAllowName(userEntity.getName());
            workEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.FZRSURE.getCode()));
            workTicketFireService.updateEntity(workEntity);
            
          //保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workFireEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//许可人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
        }else if(spFlag.equals(WorkFireBtnTypeEnum.XFJHRXK.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setOtherFireSignId(userEntity.getId());
            workFireEntity.setOtherFireSign(userEntity.getName());
            workFireEntity.setOtherFireSignDate(new Date());
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.AJYSURE.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        } else if(spFlag.equals(WorkFireBtnTypeEnum.GZJD.getCode())){//工作交底
			//更新电气第一种票表
        	 WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
        	 workFireEntity.setWorkDisclosure(t.getWorkDisclosure());
			 super.updateEntity(workFireEntity);
		}else if(spFlag.equals(WorkFireBtnTypeEnum.YS.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            
            workFireEntity.setEndHand(t.getEndHand());
       	    workFireEntity.setEndStandIndex(t.getEndStandIndex());
       	    workFireEntity.setEndStand(t.getEndStand());
       	    workFireEntity.setRemark(t.getRemark());
			super.updateEntity(workFireEntity);
            
            //更新主表状态为未执行
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setEndTime(t.getEndTimeZhu());
            workTicketFireEntity.setEndPicId(t.getEndPicIdZhu());//动火工作负责人
      		workTicketFireEntity.setEndPicName(t.getEndPicNameZhu());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.LDSURE.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.DHFZRYS.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            
            workFireEntity.setFireUserId(userEntity.getId());
            workFireEntity.setFireUserName(userEntity.getName());
            
            workFireEntity.setOtherendExecutorSignId(t.getOtherendExecutorSignId());//动火执行人 
            SysUserEntity entity=sysUserService.findById(t.getOtherendExecutorSignId()); 
            workFireEntity.setOtherendExecutorSign(entity.getName());
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.TOEXE.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.XFJHRZJ.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setOtherendFireSignId(userEntity.getId());
            workFireEntity.setOtherendFireSign(userEntity.getName());
            
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.JHRCHECK.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.XKZJ.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体          
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
        	workTicketFireService.updateEntity(workTicketFireEntity);
            //更新主表状态为未执行
            WorkTicketFireEntity workEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workEntity.setEndAllowId(t.getEndAllowIdZhu());
			workEntity.setEndAllowName(t.getEndAllowNameZhu());
			workEntity.setChangeAllowIdea(t.getFileid());
			workEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.END.getCode()));
			workTicketFireService.updateEntity(workEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workFireEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//许可人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
		
        }else if(spFlag.equals(WorkBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(t.getId());
			workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.TOBEISSUED.getCode()));
			workTicketFireService.updateEntity(workTicketFireEntity);
		}
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(WorkFireEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkFireBtnTypeEnum.QF.getCode())){
			WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			//更新从表信息
			workFireEntity.setSignerId(null);
			workFireEntity.setSignerName(null);
			workFireEntity.setSignerDate(null);
			super.updateEntity(workFireEntity);
			//更新主表状态为驳回
			WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
			workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.FZRCHECK.getCode()));
			workTicketFireService.updateEntity(workTicketFireEntity);
			
		}else if(spFlag.equals(WorkFireBtnTypeEnum.HQF.getCode())){
		    WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
		    workFireEntity.setMeetSignId(null);
		    workFireEntity.setMeetSignName(null);
		    workFireEntity.setMeetSignDate(null);
		    
		    //签发人清空
			workFireEntity.setSignerId(null);
			workFireEntity.setSignerName(null);
			workFireEntity.setSignerDate(null);
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.FZRCHECK.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
		}else if(spFlag.equals(WorkFireBtnTypeEnum.XFJHR.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            
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
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.FZRCHECK.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.AJBM.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
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
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.FZRCHECK.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
		}else if(spFlag.equals(WorkFireBtnTypeEnum.AQZJ.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
           
            workFireEntity.setSafeDirctorDate(null);
            workFireEntity.setSafeDirctorId(null);
            workFireEntity.setSafeDirctorName(null);
            //安全总监
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
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.FZRCHECK.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        }
		else if(spFlag.equals(WorkFireBtnTypeEnum.FGSCFZR.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setOtherLederSignId(null);
            workFireEntity.setOtherLederSign(null);
            workFireEntity.setOtherLederSignDate(null);
            
            workFireEntity.setSafeDirctorDate(null);
            workFireEntity.setSafeDirctorId(null);
            workFireEntity.setSafeDirctorName(null);
            //安全总监
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
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.FZRCHECK.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.SP.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setDutyMonitorId(null);
            workFireEntity.setDutyMonitorName(null);
            workFireEntity.setDutyMonitorDate(null);
            
            //分管领导
            workFireEntity.setOtherLederSignId(null);
            workFireEntity.setOtherLederSign(null);
            workFireEntity.setOtherLederSignDate(null);
            
            workFireEntity.setSafeDirctorDate(null);
            workFireEntity.setSafeDirctorId(null);
            workFireEntity.setSafeDirctorName(null);
            //安全总监
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
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.FZRCHECK.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.XK.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setApproveStarttime(null);//允许动火时间
            workFireEntity.setOtherPicSignId(null);
            workFireEntity.setOtherPicSign(null);
            workFireEntity.setOtherExecutorSignId(null);
            workFireEntity.setOtherExecutorSign(null);
            workFireEntity.setRunFireSignId(null);
            workFireEntity.setRunFireSignName(null);
            workFireEntity.setOtherGroupSignId(null);
            workFireEntity.setOtherGroupSign(null);
            workFireEntity.setOtherSafeSignId(null);
            workFireEntity.setOtherSafeSign(null);
            workFireEntity.setRunSafeDirectorId(null);
            workFireEntity.setRunSafeDirector(null);
            workFireEntity.setRunLederSignId(null);
            workFireEntity.setRunLederSign(null);
            workFireEntity.setOther("");
            
            //运行值班人
            workFireEntity.setDutyMonitorId(null);
            workFireEntity.setDutyMonitorName(null);
            workFireEntity.setDutyMonitorDate(null);
            
            //分管领导
            workFireEntity.setOtherLederSignId(null);
            workFireEntity.setOtherLederSign(null);
            workFireEntity.setOtherLederSignDate(null);
            
            workFireEntity.setSafeDirctorDate(null);
            workFireEntity.setSafeDirctorId(null);
            workFireEntity.setSafeDirctorName(null);
            //安全总监
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
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.FZRCHECK.getCode()));
            workTicketFireEntity.setChangeAllowDate(null);
            workTicketFireEntity.setChangeAllowId(null);
            workTicketFireEntity.setChangeAllowName(null);
            workTicketFireService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.XFJHRZJ.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            
            workFireEntity.setFireUserId(null);
            workFireEntity.setFireUserName(null);
            
            workFireEntity.setOtherendExecutorSignId(null);//动火执行人 
            workFireEntity.setOtherendExecutorSign(null);
 			
            workFireEntity.setOtherendFireSignId(null);//消防监护人
            workFireEntity.setOtherendFireSign(null);
         	 workFireEntity.setEndHand(null);
         	 workFireEntity.setEndStandIndex(null);
         	 workFireEntity.setEndStand(null);
         	 workFireEntity.setRemark("");
            
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
 			//更新工作票表
 			workTicketFireEntity.setEndTime(null);
 			workTicketFireEntity.setEndPicId(null);
 			workTicketFireEntity.setEndPicName(null);
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.AJYSURE.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.XKZJ.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            workFireEntity.setEndHand(null);
        	workFireEntity.setEndStandIndex(null);
        	workFireEntity.setEndStand(null);
            workFireEntity.setRemark("");
            
            workFireEntity.setFireUserId(null);
            workFireEntity.setFireUserName(null);
            
            workFireEntity.setOtherendExecutorSignId(null);//动火执行人 
            workFireEntity.setOtherendExecutorSign(null);
            //清理消防监护
            workFireEntity.setOtherendFireSignId(null);
            workFireEntity.setOtherendFireSign(null);
            super.updateEntity(workFireEntity);
            //更新主表状态为未执行
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setEndTime(null);
            workTicketFireEntity.setEndPicId(null);
            workTicketFireEntity.setEndPicName(null);
            workTicketFireEntity.setEndAllowId(null);
            workTicketFireEntity.setEndAllowName(null);
            workTicketFireEntity.setChangeAllowIdea(t.getFileid());
            System.out.println(t.getFileid());
            System.out.println(workTicketFireEntity.getChangeAllowIdea());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.AJYSURE.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
        }else if(spFlag.equals(WorkFireBtnTypeEnum.FP.getCode())){
            WorkFireEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            //更新主表状态为废票
            WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
            workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.WORKSTATUS_TYPE_INVALID.getCode()));
            workTicketFireService.updateEntity(workTicketFireEntity);
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
  		    String key=ProcessMarkEnum.WORK_TICKET_FIRE_PROCESS_KEY.getName();	
  		    
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, id, vars);
			
			WorkTicketFireEntity workTicketFireEntity=workTicketFireService.findById(Long.valueOf(id));
			workTicketFireEntity.setId(Long.valueOf(id));
			workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireStatusEnum.TOBEISSUED.getCode())); 
			
			workTicketFireService.updateEntity(workTicketFireEntity);
	}
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkFireEntity t, Long id) {
		WorkFireEntity workFireEntity=this.findById(id);
		workFireEntity.setRemark(t.getRemark());
		workFireEntity.setOther(t.getOther());
		this.updateEntity(workFireEntity);
		return new ResultObj();
	}
}