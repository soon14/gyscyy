package com.aptech.business.run.workRecord.service;

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
import com.aptech.business.run.workRecord.dao.WorkRecordDao;
import com.aptech.business.run.workRecord.domain.WorkRecordEntity;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 定期工作记录应用管理服务实现类
 *
 * @author 
 * @created 2017-06-01 15:08:52
 * @lastModified 
 * @history
 *
 */
@Service("workRecordService")
@Transactional
public class WorkRecordServiceImpl extends AbstractBaseEntityOperation<WorkRecordEntity> implements WorkRecordService {
	
	@Autowired
	private WorkRecordDao workRecordDao;
	@Autowired
    private ActTaskService actTaskService;
	@Override
	public IBaseEntityOperation<WorkRecordEntity> getDao() {
		return workRecordDao;
	}
	@Override
    public void tocheck(Long id, Map<String, Object> params) {
	    WorkRecordEntity workRecordEntity = this.findById(id);
        Map<String, Object> vars = new HashMap<String, Object>();
	    if("1".equals(params.get("delayStates"))){
	        workRecordEntity.setCheckState(ProtectStatusEnum.ZZCHECK.getCode());
            vars.put(BranchMarkEnum.BRANCH_KEY.getName(),"1");
	        workRecordEntity.setDelayStates(1);	        
	    }else if("2".equals(params.get("delayStates"))){
            workRecordEntity.setCheckState(ProtectStatusEnum.YXZGCHECK.getCode());
            vars.put(BranchMarkEnum.BRANCH_KEY.getName(),"2");
	    }else if (workRecordEntity.getDelayStates()!=null){
	        if(2==workRecordEntity.getDelayStates()){
	            workRecordEntity.setCheckState(ProtectStatusEnum.ZZCHECK.getCode());
	            vars.put(BranchMarkEnum.BRANCH_KEY.getName(),"1");
	        }
	    }
        this.updateEntity(workRecordEntity);
      //准备启动流程
        vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), params.get("userList"));        
        actTaskService.startProcess(ProcessMarkEnum.WORKRECORD_PROCESS_KEY.getName(), id.toString(), vars);
    }
	 @Override
	    public ResultObj check(WorkRecordEntity t,Map<String, Object> params) {      
	            // 修改状态
	            WorkRecordEntity tEntity = this.findById(t.getId());
	            tEntity.setCheckState(params.get("status").toString());
	            if(ProtectStatusEnum.YQPASS.getCode().equals(params.get("status").toString())){
	                tEntity.setDelayStates(2); 
	            }
	            if(ProtectStatusEnum.YQREJECT.getCode().equals(params.get("status").toString())){
                    tEntity.setDelayStates(1); 
                }
	            this.updateEntity(tEntity);
	            //设置当前流程,当前任务节点的下一个节点的办理人
	            actTaskService.setVarialble(t.getTaskId(),params);
	            // 调用流程接口
//	            Map<String, String> comments = new HashMap<String, String>();
//	            comments.put("event", params.get("event").toString());
//	            comments.put("comment", params.get("comment").toString());
	            params.put(ExamMarkEnum.RESULT.getCode(), params.get(ExamMarkEnum.RESULT.getCode()).toString());
	            actTaskService.complete(t.getTaskId(), t.getProcInstId(), params);
	        
	        return  new ResultObj();
	    }
	 @Override
	    public ResultObj delete(Serializable id) throws Exception {
	        ResultObj resultObj = new ResultObj();
	        WorkRecordEntity t = this.findById(id);
	        if(validateisDelete(t)){
	            this.deleteEntity(id);
	        }
	        return resultObj;
	    }
	 // 批量删除验证
	    private boolean validateisDelete(WorkRecordEntity t) throws Exception{
	        if (!(t.getCheckState().equals(ProtectStatusEnum.TOSUBMIT.getCode()) || t
	                .getCheckState().equals(ProtectStatusEnum.CANCEL.getCode()))) {
	            throw new ProtectException(ProtectExceptionType.PROTECT_CODE_DEL);
	        }
	        
	        return true;
	    }
}