package com.aptech.business.run.workPlan.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ProtectStatusEnum;
import com.aptech.business.run.protect.domain.ProtectEntity;
import com.aptech.business.run.protect.exception.ProtectException;
import com.aptech.business.run.protect.exception.ProtectExceptionType;
import com.aptech.business.run.workPlan.dao.WorkPlanDao;
import com.aptech.business.run.workPlan.domain.WorkPlanEntity;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 定期工作计划应用管理服务实现类
 *
 * @author 
 * @created 2017-05-27 11:40:09
 * @lastModified 
 * @history
 *
 */
@Service("workPlanService")
@Transactional
public class WorkPlanServiceImpl extends AbstractBaseEntityOperation<WorkPlanEntity> implements WorkPlanService {
	
	@Autowired
	private WorkPlanDao workPlanDao;
	@Autowired
    private ActTaskService actTaskService;
	@Autowired
    private SysUnitService sysUnitService;
	@Override
	public IBaseEntityOperation<WorkPlanEntity> getDao() {
		return workPlanDao;
	}
	@Override
    public void tocheck(Long id, Map<String, Object> params) {
	    WorkPlanEntity workPlanEntity = this.findById(id);
	    workPlanEntity.setCheckState(ProtectStatusEnum.ZRCHECK.getCode());
        this.updateEntity(workPlanEntity);
      //准备启动流程
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), params.get("userList"));
        actTaskService.startProcess(ProcessMarkEnum.WORKPLAN_PROCESS_KEY.getName(), id.toString(), vars);
    }
    @Override
    public ResultObj check(WorkPlanEntity t,Map<String, Object> params) {      
            // 修改状态
        WorkPlanEntity tEntity = this.findById(t.getId());
            tEntity.setCheckState(params.get("status").toString());
            this.updateEntity(tEntity);
            //设置当前流程,当前任务节点的下一个节点的办理人
            actTaskService.setVarialble(t.getTaskId(),params);
            // 调用流程接口
//            Map<String, String> comments = new HashMap<String, String>();
//            comments.put("event", params.get("event").toString());
//            comments.put("comment", params.get("comment").toString());
            params.put(ExamMarkEnum.RESULT.getCode(), params.get(ExamMarkEnum.RESULT.getCode()).toString());
            actTaskService.complete(t.getTaskId(), t.getProcInstId(),
                    params);
        
        return  new ResultObj();
    }
    @Override
    public ResultObj add(WorkPlanEntity t)  {
        SysUnitEntity sysUnitEntity=sysUnitService.findById((long)t.getUnitId());
        String applyTimeTemp=(t.getTime().replaceAll("-", ""));
        t.setCode(sysUnitEntity.getCode()+applyTimeTemp);
        t.setCheckState(ProtectStatusEnum.TOSUBMIT.getCode());
        this.addEntity(t);
      //提交
        String saveOrsubmit=t.getSaveOrSubmit();
        String selectUser=t.getSelectUser();
        if(saveOrsubmit!=null&&!saveOrsubmit.equals("")){
            //准备启动流程
            String key=ProcessMarkEnum.WORKPLAN_PROCESS_KEY.getName(); 
            Map<String, Object> vars=new HashMap<String,Object>();
            vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
            actTaskService.startProcess(key, t.getId().toString(), vars);
            
            WorkPlanEntity workPlanEntity=this.findById(t.getId());
            workPlanEntity.setId(t.getId());
            workPlanEntity.setCheckState(ProtectStatusEnum.ZRCHECK.getCode());
            this.updateEntity(workPlanEntity);
        }
        ResultObj resultObj = new ResultObj();
        resultObj.setData(t);
        return resultObj;
    }
    @Override
    public ResultObj delete(Serializable id) throws Exception {
        ResultObj resultObj = new ResultObj();
        WorkPlanEntity t = this.findById(id);
        if(validateisDelete(t)){
            this.deleteEntity(id);
        }
        return resultObj;
    }
 // 批量删除验证
    private boolean validateisDelete(WorkPlanEntity t) throws Exception{
        if (!(t.getCheckState().equals(ProtectStatusEnum.TOSUBMIT.getCode()) || t
                .getCheckState().equals(ProtectStatusEnum.CANCEL.getCode()))) {
            throw new ProtectException(ProtectExceptionType.PROTECT_CODE_DEL);
        }      
        return true;
    }
}