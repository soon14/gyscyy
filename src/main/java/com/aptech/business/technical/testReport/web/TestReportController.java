package com.aptech.business.technical.testReport.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.EventNotificationStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.TechnicalBtnTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.technical.eventNotification.domain.EventNotificationEntity;
import com.aptech.business.technical.testReport.domain.TestReportEntity;
import com.aptech.business.technical.testReport.service.TestReportService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.common.workflow.definition.domain.DefinitionEntity;
import com.aptech.common.workflow.definition.service.DefinitionService;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.processNodeAuth.domain.ProcessNodeAuthEntity;
import com.aptech.common.workflow.processNodeAuth.service.ProcessNodeAuthService;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.common.workflow.todoTask.domain.TodoTaskEntity;
import com.aptech.common.workflow.todoTask.service.TodoTaskService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 试验报告配置控制器
 *
 * @author 
 * @created 2018-09-05 14:35:08
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/testReport")
public class TestReportController extends BaseController<TestReportEntity> {
	
	@Autowired
	private TestReportService testReportService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Override
	public IBaseEntityOperation<TestReportEntity> getService() {
		return testReportService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboTestReportVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("testReportCombobox", JsonUtil.toJson(comboTestReportVO.getOptions()));
		//工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();
  	    SysUserEntity userEntity= RequestContext.get().getUser();
        //TODO下拉框具体内容根据具体业务定制
  	    List<Condition> conditions=new ArrayList<Condition>();
  	    conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
  	    conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));//选择人员时只列出当前场站的人员
        List<SysUserEntity> allUsers = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        SysUserEntity sysUserEntity = RequestContext.get().getUser();
        model.put("userId", sysUserEntity.getId());
        model.put("loginName", sysUserEntity.getLoginName());
        model.put("unitId", userEntity.getUnitId());
		return this.createModelAndView("technical/testReport/testReportList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		ComboboxVO comboTestReportVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("testReportCombobox", JsonUtil.toJson(comboTestReportVO.getOptions()));
		ComboboxVO combotestReportVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> userCondition = new ArrayList<Condition>();
	     //获取登录人信息
		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
        for(SysUserEntity sysUserEntity : allUsers){
        	combotestReportVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("testReportCombobox", JsonUtil.toJson(combotestReportVO.getOptions()));
		return this.createModelAndView("technical/testReport/testReportAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		TestReportEntity testReportEntity = (TestReportEntity)testReportService.findById(id);
		model.put("entity", testReportEntity);
		model.put("entityJson", JsonUtil.toJson(testReportEntity));
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(testReportEntity.getUserId()));
		model.put("sysUserEntity", sysUserEntity);
		
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboTestReportVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("testReportCombobox", JsonUtil.toJson(comboTestReportVO.getOptions()));
		ComboboxVO combotestReportVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> userCondition = new ArrayList<Condition>();
	     //获取登录人信息
		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,sysUserEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
        for(SysUserEntity userEntity : allUsers){
        	combotestReportVO.addOption(userEntity.getId().toString(), userEntity.getName());
        }
        model.put("testReportCombobox", JsonUtil.toJson(combotestReportVO.getOptions()));
		return this.createModelAndView("technical/testReport/testReportEdit", model);
	}
	/**
	 *	跳转到详细页面
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		TestReportEntity testReportEntity = (TestReportEntity)testReportService.findById(id);
        resultMap.put("testReportEntity", testReportEntity);
        SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(testReportEntity.getUnitId()));
        resultMap.put("sysUnitEntity", sysUnitEntity);
        SysUserEntity sysUser = sysUserService.findById(Long.valueOf(testReportEntity.getUserId()));
        resultMap.put("sysUser", sysUser);
        ComboboxVO combotestReportVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> userCondition = new ArrayList<Condition>();
	     //获取登录人信息
		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
        for(SysUserEntity sysUserEntity : allUsers){
        	combotestReportVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        resultMap.put("testReportCombobox", JsonUtil.toJson(combotestReportVO.getOptions()));
		return this.createModelAndView("technical/testReport/testReportDetail", resultMap);
	}
	/**
	 * @Description:   提交确认  弹出框      这里要调用第一个流程接口，得到下一步的那些人的id集合
	 * @author         zhangzq 
	 * @Date           2017年6月13日 
	 * @throws         Exception
	 */
	@RequestMapping("/sureSubmit")
	public ModelAndView sureSubmit(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		String id = request.getParameter("id");
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.TEST_REPORT_PROCESS_KEY.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		String modelId="";
		if(!defList.isEmpty()){
			modelId=defList.get(0).getModelId();
		}
		SysUserEntity starter= null;
		if(!RequestContext.get().isDeveloperMode()){
			starter = RequestContext.get().getUser();
		}
		List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",starter);
		resultMap.put("userList", userList);
		resultMap.put("id", id);
		return new ModelAndView("/technical/testReport/sureSubmitPerson",resultMap);
	}
	/**
	 * @Description:   提交方法
	 * @author         zhangzq 
	 * @Date           2017年6月12日 上午11:39:16 
	 * @throws         Exception
	 */
	@RequestMapping("/submit")
	public @ResponseBody ResultObj submit(HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		try {
			String id=request.getParameter("workId");
			String selectUser=request.getParameter("selectUser");
			testReportService.submit(id,selectUser);
			resultObj.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.setResult("error");
		}
		return resultObj;
	}
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id
			,@PathVariable String type) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		TestReportEntity testReportEntity = (TestReportEntity)testReportService.findById(id);
        resultMap.put("testReportEntity", testReportEntity);
        SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
        resultMap.put("testReportEntity", testReportEntity);
        SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(testReportEntity.getUnitId()));
        resultMap.put("sysUnitEntity", sysUnitEntity);
        SysUserEntity sysUser = sysUserService.findById(Long.valueOf(testReportEntity.getUserId()));
        resultMap.put("sysUser", sysUser);
        ComboboxVO combotestReportVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> userCondition = new ArrayList<Condition>();
	     //获取登录人信息
		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
        for(SysUserEntity sysUserEntity : allUsers){
        	combotestReportVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        resultMap.put("testReportCombobox", JsonUtil.toJson(combotestReportVO.getOptions()));
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.TEST_REPORT_PROCESS_KEY.getName()));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			resultMap.put("nodeList", nodeList);
		}
		//查询各个人的按钮权限 结束
	
		return this.createModelAndView("technical/testReport/testReportApprove", resultMap);
	}
	/**
	 *	跳转到生技部审核页面
	 */
	@RequestMapping("/getAddSJB")
	public ModelAndView getAddSJB(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String testReportEntityId=request.getParameter("testReportEntityId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("testReportEntityId", testReportEntityId);
		return this.createModelAndView("technical/testReport/testReportAddSJB", model);
	}
	/**
	 * @Description:   专工主任同意
	 * @author         huoxy 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSJB")
	public @ResponseBody ResultObj agreeSJB(HttpServletRequest request,@RequestBody TestReportEntity testReportEntity){
		ResultObj resultObj = new ResultObj();
		try {
			testReportEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECH.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),testReportEntity.getApproveIdea()==null?"":testReportEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			testReportService.updateSpnrAgree(testReportEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   生技部不同意
	 * @author         huoxy 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeSJB")
	public @ResponseBody ResultObj disAgreeSJB(HttpServletRequest request,@RequestBody TestReportEntity testReportEntity){
		ResultObj resultObj = new ResultObj();
		try {
			testReportEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECH.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),testReportEntity.getApproveIdea()==null?"":testReportEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			testReportService.updateSpnrDisagree(testReportEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 *	跳转到生技部主任审核页面
	 */
	@RequestMapping("/getAddSJBZR")
	public ModelAndView getAddSJBZR(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String testReportEntityId=request.getParameter("testReportEntityId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("testReportEntityId", testReportEntityId);
		return this.createModelAndView("technical/testReport/testReportAddSJBZR", model);
	}
	/**
	 * @Description:   生技部主任同意
	 * @author         huoxy 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSJBZR")
	public @ResponseBody ResultObj agreeSJBZR(HttpServletRequest request,@RequestBody TestReportEntity testReportEntity){
		ResultObj resultObj = new ResultObj();
		try {
			testReportEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),testReportEntity.getApproveIdea()==null?"":testReportEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			testReportService.updateSpnrAgree(testReportEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   生技部主任不同意
	 * @author         huoxy 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeSJBZR")
	public @ResponseBody ResultObj disAgreeSJBZR(HttpServletRequest request,@RequestBody TestReportEntity testReportEntity){
		ResultObj resultObj = new ResultObj();
		try {
			testReportEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),testReportEntity.getApproveIdea()==null?"":testReportEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			testReportService.updateSpnrDisagree(testReportEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   生技部主任驳回上一级
	 * @author         huoxy 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeSJBZRUP")
	public @ResponseBody ResultObj disAgreeSJBZRUP(HttpServletRequest request,@RequestBody TestReportEntity testReportEntity){
		ResultObj resultObj = new ResultObj();
		try {
			testReportEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK_END.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK_END.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),testReportEntity.getApproveIdea()==null?"":testReportEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			testReportService.updateSpnrDisagreeUp(testReportEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 *	跳转到上级领导审核页面
	 */
	@RequestMapping("/getAddSJLD")
	public ModelAndView getAddSJLD(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String testReportEntityId=request.getParameter("testReportEntityId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("testReportEntityId", testReportEntityId);
		return this.createModelAndView("technical/testReport/testReportAddSJLD", model);
	}
	/**
	 * @Description:   上级领导同意
	 * @author         huoxy 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSJLD")
	public @ResponseBody ResultObj agreeSJLD(HttpServletRequest request,@RequestBody TestReportEntity testReportEntity){
		ResultObj resultObj = new ResultObj();
		try {
			testReportEntity.setSpFlag(TechnicalBtnTypeEnum.LEADER.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),testReportEntity.getApproveIdea()==null?"":testReportEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			testReportService.updateSpnrAgree(testReportEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   上级领导不同意
	 * @author         huoxy 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeSJLD")
	public @ResponseBody ResultObj disAgreeSJLD(HttpServletRequest request,@RequestBody TestReportEntity testReportEntity){
		ResultObj resultObj = new ResultObj();
		try {
			testReportEntity.setSpFlag(TechnicalBtnTypeEnum.LEADER.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),testReportEntity.getApproveIdea()==null?"":testReportEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			testReportService.updateSpnrDisagree(testReportEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   上级领导驳回上一级
	 * @author         huoxy 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeSJLDUP")
	public @ResponseBody ResultObj disAgreeSJLDUP(HttpServletRequest request,@RequestBody TestReportEntity testReportEntity){
		ResultObj resultObj = new ResultObj();
		try {
			testReportEntity.setSpFlag(TechnicalBtnTypeEnum.LEADER.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK_END.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK_END.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),testReportEntity.getApproveIdea()==null?"":testReportEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			testReportService.updateSpnrDisagreeUp(testReportEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	//废票
	@RequestMapping("/disAgreeFp")
	public @ResponseBody ResultObj disAgreeFp(HttpServletRequest request,@RequestBody TestReportEntity testReportEntity){
		ResultObj resultObj = new ResultObj();
		try {
			testReportEntity.setSpFlag(TechnicalBtnTypeEnum.REJECT.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),testReportEntity.getApproveIdea()==null?"":testReportEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			testReportService.updateSpnrDisagree(testReportEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 *	跳转到负责人再提交页面
	 */
	@RequestMapping("/getAddZtj")
	public ModelAndView getAddZtj(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String testReportEntityId=request.getParameter("testReportEntityId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("testReportEntityId", testReportEntityId);
		return this.createModelAndView("technical/testReport/testReportAddZtj", model);
	}
	/**
	 * @Description:  负责人提交同意
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeFzrtj")
	public @ResponseBody ResultObj agreeFzrtj(HttpServletRequest request,@RequestBody TestReportEntity testReportEntity){
		ResultObj resultObj = new ResultObj();
		try {
			testReportEntity.setSpFlag(TechnicalBtnTypeEnum.REJECT.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),testReportEntity.getApproveIdea()==null?"":testReportEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			testReportService.updateSpnrAgree(testReportEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   导出
	 * @author         changl 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditions=req.getParameter("conditions");
		Page<TestReportEntity> page = new Page<TestReportEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("id"));
		params = (Map<String, Object>)JSONObject.fromObject(conditions);
		List<TestReportEntity> dataList=testReportService.findByCondition(params, null);
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).setNumber(i+1);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "试验报告模板.xlsx","试验报告.xlsx", resultMap);
	}
}