package com.aptech.business.technical.testReport.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.EventNotificationStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.TechnicalBtnTypeEnum;
import com.aptech.business.component.dictionary.TechnicalStatusEnum;
import com.aptech.business.dutyOrder.domain.DutyOrderEntity;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.technical.eventAnalysis.domain.EventAnalysisEntity;
import com.aptech.business.technical.eventNotification.domain.EventNotificationEntity;
import com.aptech.business.technical.testReport.dao.TestReportDao;
import com.aptech.business.technical.testReport.domain.TestReportEntity;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.validate.ValidateUtil;

/**
 * 
 * 试验报告应用管理服务实现类
 *
 * @author 
 * @created 2018-09-05 14:35:08
 * @lastModified 
 * @history
 *
 */
@Service("testReportService")
@Transactional
public class TestReportServiceImpl extends AbstractBaseEntityOperation<TestReportEntity> implements TestReportService {
	
	@Autowired
	private TestReportDao testReportDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<TestReportEntity> getDao() {
		return testReportDao;
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
   		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<TestReportEntity> resultList = (List<TestReportEntity>) super.findByCondition(conditions, page);
		
		if(resultList!=null && resultList.size()>0){
			for(TestReportEntity testReportEntity:resultList){
				if(testReportEntity.getTestMember()!=null){
					String[] ids = testReportEntity.getTestMember().split(",");
					conditions.clear();
					conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN,ids));
					List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
					String userNames = "";
					for(int i=0;i<userList.size();i++){
						if(i==userList.size()-1){
							userNames+=userList.get(i).getName();
						}else{
							userNames+=userList.get(i).getName()+",";
						}
					}
					testReportEntity.setTestMember(userNames);
				}
				
			}
		}
		
		return (List<O>) resultList;
	}
	@Override
	public void addEntity(TestReportEntity t) {
		t.setStatus(TechnicalStatusEnum.DTJ.getCode());
		getDao().addEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TEST_REPORT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	@Override
	public void updateEntity(TestReportEntity t) {
		getDao().updateEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TEST_REPORT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}

	@Override
	public void deleteEntity(Serializable id) {
		getDao().deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TEST_REPORT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}

	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TEST_REPORT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	@Override
	public void submit(String id, String selectUser) {
		// TODO Auto-generated method stub
		//准备启动流程
	    String key=ProcessMarkEnum.TEST_REPORT_PROCESS_KEY.getName();	
		Map<String, Object> vars=new HashMap<String,Object>();
  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
	    actTaskService.startProcess(key, id, vars);
	    TestReportEntity  testReportEntity=this.findById(Long.valueOf(id)); 
	    testReportEntity.setId(Long.valueOf(id));
	    testReportEntity.setStatus(TechnicalStatusEnum.DSJCSH.getCode());
		super.updateEntity(testReportEntity);
	}
	@Override
	public void updateSpnrAgree(TestReportEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECH.getCode())){
			//更新主表状态
			TestReportEntity testReportEntity=this.findById(t.getId());
			testReportEntity.setStatus(TechnicalStatusEnum.DSJBZRSH.getCode());
			super.updateEntity(testReportEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode())){
			//更新主表状态
			TestReportEntity testReportEntity=this.findById(t.getId());
			testReportEntity.setStatus(TechnicalStatusEnum.DSJLDSH.getCode());
			super.updateEntity(testReportEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.LEADER.getCode())){
			//更新主表状态
			TestReportEntity testReportEntity=this.findById(t.getId());
			testReportEntity.setStatus(TechnicalStatusEnum.END.getCode());
			super.updateEntity(testReportEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.REJECT.getCode())){
			//更新主表状态
			TestReportEntity testReportEntity=this.findById(t.getId());
			testReportEntity.setStatus(TechnicalStatusEnum.DSJCSH.getCode());
			super.updateEntity(testReportEntity);
		}
	}
	@Override
	public void updateSpnrDisagree(TestReportEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECH.getCode())){
			TestReportEntity testReportEntity=this.findById(t.getId());
			testReportEntity.setStatus(TechnicalStatusEnum.BHGZFZR.getCode());
			super.updateEntity(testReportEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode())){
			TestReportEntity testReportEntity=this.findById(t.getId());
			testReportEntity.setStatus(TechnicalStatusEnum.BHGZFZR.getCode());
			super.updateEntity(testReportEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.LEADER.getCode())){
			TestReportEntity testReportEntity=this.findById(t.getId());
			testReportEntity.setStatus(TechnicalStatusEnum.BHGZFZR.getCode());
			super.updateEntity(testReportEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.REJECT.getCode())){
			TestReportEntity testReportEntity=this.findById(t.getId());
			testReportEntity.setStatus(TechnicalStatusEnum.GZPXQ.getCode());
			super.updateEntity(testReportEntity);
		}
	}
	@Override
	public void updateSpnrDisagreeUp(TestReportEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode())){
			TestReportEntity testReportEntity=this.findById(t.getId());
			testReportEntity.setStatus(TechnicalStatusEnum.DSJCSH.getCode());
			super.updateEntity(testReportEntity);
		}else if(spFlag.equals(TechnicalBtnTypeEnum.LEADER.getCode())){
			TestReportEntity testReportEntity=this.findById(t.getId());
			testReportEntity.setStatus(TechnicalStatusEnum.DSJBZRSH.getCode());
			super.updateEntity(testReportEntity);
		}
	}
}