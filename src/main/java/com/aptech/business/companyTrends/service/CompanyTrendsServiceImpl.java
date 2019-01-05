package com.aptech.business.companyTrends.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.companyTrends.dao.CompanyTrendsDao;
import com.aptech.business.companyTrends.domain.CompanyTrendsEntity;
import com.aptech.business.companyTrends.exception.CompanyTrendsException;
import com.aptech.business.companyTrends.exception.CompanyTrendsExceptionType;
import com.aptech.business.component.dictionary.CompanyTrendsStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.TypicalTicketApproveStatusEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.ticketManage.operationTicket.exception.OperationTicketException;
import com.aptech.business.ticketManage.operationTicket.exception.OperationTicketExceptionType;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 公司动态应用管理服务实现类
 *
 * @author 
 * @created 2018-04-03 11:20:52
 * @lastModified 
 * @history
 *
 */
@Service("companyTrendsService")
@Transactional
public class CompanyTrendsServiceImpl extends AbstractBaseEntityOperation<CompanyTrendsEntity> implements CompanyTrendsService {
	
	@Autowired
	private CompanyTrendsDao companyTrendsDao;
	@Autowired
	private ActTaskService actTaskService;
	
	@Override
	public IBaseEntityOperation<CompanyTrendsEntity> getDao() {
		return companyTrendsDao;
	}
	
	/** 批量删除
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
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
	 * @Date           2017年6月29日 上午11:36:54 
	 * @throws         Exception
	 */
	private boolean validateisDeleteBulk(List<Integer> ids) {
		boolean flag=true;
		for (int i=0;i<ids.size();i++) {
			CompanyTrendsEntity  companyTrendsEntity=this.findById(Long.valueOf(ids.get(i)));
			if(companyTrendsEntity!=null){
				String workStatus=companyTrendsEntity.getStatus();
				if(!workStatus.equals(WorkStatusEnum.TOBESUBMIT.getCode())){
					flag=false;
					break;
				}
			}
		}
		if (flag) {
			for (int i=0;i<ids.size();i++) {
				CompanyTrendsEntity  companyTrendsEntity=this.findById(Long.valueOf(ids.get(i)));
				super.deleteEntity(companyTrendsEntity);
			}
		}else{
			throw new CompanyTrendsException(CompanyTrendsExceptionType.COMPANYTRENDS_BATHDELETE);
		}
		
		return true;
	}
	
	/**
	 * 提交实体
	 * 
	 * @param id
	 */
	@Override
	public ResultObj submit(Serializable id, Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		CompanyTrendsEntity t = this.findById(id);
		if (validate(t)) {
			if ( String.valueOf(t.getStatus()).equals(TypicalTicketApproveStatusEnum.REJECT.getCode())) {
				throw new OperationTicketException(OperationTicketExceptionType.OPERATIONTICKET_CODE_REJECT);
			}
			t.setStatus(CompanyTrendsStatusEnum.FOREMANEXAMINE.getCode());
			companyTrendsDao.updateEntity(t);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),
					params.get("userList"));
			actTaskService.startProcess(
					ProcessMarkEnum.TECHNICAL_COMPANYTRENDS.getName(),
					id.toString(), vars);
		}
		return resultObj;
	}
	
	/**
	 * 审核后继续下一步流程
	 */
	@Override
	public ResultObj approve(CompanyTrendsEntity companyTrendsEntity,Map<String, Object> params) {
		if(validate(companyTrendsEntity)){
			// 修改状态
			CompanyTrendsEntity entity = this.findById(companyTrendsEntity.getId());
			entity.setStatus(params.get("processStatus").toString());
			this.updateEntity(entity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(companyTrendsEntity.getTaskId(),params);
			// 调用流程接口
			params.put(ExamMarkEnum.RESULT.getCode(), params.get("result"));
			actTaskService.complete(companyTrendsEntity.getTaskId(), companyTrendsEntity.getProcInstId(),params);
		}
		return  new ResultObj();
	}
	
	// 基本验证
		public boolean validate(CompanyTrendsEntity t) {
			CompanyTrendsEntity entity = this.findById(t.getId());
			if (entity == null) {
				throw new CompanyTrendsException(CompanyTrendsExceptionType.COMPANYTRENDS_CODE_NULL);
			}
			return true;
		}
	
}