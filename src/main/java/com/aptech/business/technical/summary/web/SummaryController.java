package com.aptech.business.technical.summary.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.TechnicalBtnTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.technical.summary.domain.SummaryEntity;
import com.aptech.business.technical.summary.service.SummaryService;
import com.aptech.business.technical.technical.domain.TechnicalEntity;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
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
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 技术监督总结配置控制器
 *
 * @author 
 * @created 2018-03-14 14:02:22
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/summary")
public class SummaryController extends BaseController<SummaryEntity> {
	
	@Autowired
	private SummaryService summaryService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private ActTaskService actTaskService;
	@Override
	public IBaseEntityOperation<SummaryEntity> getService() {
		return summaryService;
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
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
        //工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
  	    List<Condition> conditions=new ArrayList<Condition>();
  	    conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
  	    conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));//选择人员时只列出当前场站的人员
        List<SysUserEntity> allUsers = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        resultMap.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        
        resultMap.put("userId", userEntity.getId());
        resultMap.put("loginName", userEntity.getLoginName());
        resultMap.put("unitId", userEntity.getUnitId());
		return this.createModelAndView("technical/summary/summaryList", resultMap);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
  	    List<Condition> conditions=new ArrayList<Condition>();
	    conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
        List<SysUserEntity> allUsers = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        resultMap.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		return this.createModelAndView("technical/summary/summaryAdd", resultMap);
	}
	
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
  	    List<Condition> conditions=new ArrayList<Condition>();
	    conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
        List<SysUserEntity> allUsers = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        resultMap.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		SummaryEntity summaryEntity=summaryService.findById(id);
		resultMap.put("summaryEntity", summaryEntity);
		return this.createModelAndView("technical/summary/summaryEdit", resultMap);
	}
	
	/**
	 *	跳转到详细页面
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		SummaryEntity summaryEntity = (SummaryEntity)summaryService.findById(id);
		if(summaryEntity!=null){
			SysUnitEntity sysUnitEntity=sysUnitService.findById(Long.valueOf(summaryEntity.getUnitId()));
			summaryEntity.setUnitName(sysUnitEntity.getName());
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			summaryEntity.setTimeString(dateFm.format(summaryEntity.getTime()));
		}
        resultMap.put("summaryEntity", summaryEntity);
        return this.createModelAndView("technical/summary/summaryDetail", resultMap);
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
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.TECHNICAL_PROCESS_KEY.getName()));
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
		return new ModelAndView("/technical/summary/sureSubmitPerson",resultMap);
	}
	
	/**
	 * @Description:   修改时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateValidate/{id}")
	public @ResponseBody ResultObj updateValidate(HttpServletRequest request ,@PathVariable Long id) {
		return summaryService.updateValidate(id);
	}
	
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody ResultObj update(@RequestBody SummaryEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return summaryService.update(t,id);
	}
	/** 批量删除
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/bulkDelete/{ids}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		summaryService.deleteBulk(ids);
		return resultObj;
	}
	
	/**
	 * @Description:   单个删除时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/deleteValidate/{id}")
	public @ResponseBody ResultObj deleteValidate(HttpServletRequest request ,@PathVariable Long id) {
		return summaryService.deleteValidate(id);
	}
	
	/**
	 * @Description:   提交时候的验证
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/tijiaoValidate/{id}")
	public @ResponseBody ResultObj tijiaoValidate(HttpServletRequest request ,@PathVariable Long id) {
		return summaryService.tijiaoValidate(id);
	}
	
	/**
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           20180320
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		Page<SummaryEntity> page=new Page<SummaryEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_CREATE_DATE"));
		List<SummaryEntity> dataList=summaryService.findByCondition(conditions, page);
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		for (int j = 0; j < dataList.size(); j++) {
			dataList.get(j).setNumber(j+1);
		}
		for(SummaryEntity summaryEntity : dataList){
			SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(summaryEntity.getUnitId()));
			summaryEntity.setUnitName(sysUnitEntity.getName());
		}
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "总结模板.xlsx","总结.xlsx", resultMap);
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
			summaryService.submit(id,selectUser);
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
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.TECHNICAL_SUMMARY_PROCESS_KEY.getName()));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			resultMap.put("nodeList", nodeList);
		}
		//查询各个人的按钮权限 结束
		
		// 返回前台数据项
		SummaryEntity summaryEntity = (SummaryEntity)summaryService.findById(id);
//		List<Condition> conditions=new ArrayList<Condition>();
//		conditions.add(new Condition("C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
//		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//		List<TechnicalEntity> listtechnical=summaryService.findByCondition(conditions, null);
//		if(!listtechnical.isEmpty()){
//			 technicalEntity.setUnitName(listtechnical.get(0).getUnitName());
//			 technicalEntity.setJdzyName(listtechnical.get(0).getJdzyName());
//			 technicalEntity.setWcztName(listtechnical.get(0).getWcztName());
//		}
        resultMap.put("summaryEntity", summaryEntity);
	    SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(summaryEntity.getUnitId()));
	    resultMap.put("sysUnitEntity", sysUnitEntity);
		return this.createModelAndView("technical/summary/summaryApprove", resultMap);
	}
	/**
	 *	跳转到生技部页面
	 */
	@RequestMapping("/getAddBiotech")
	public ModelAndView getAddBiotech(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String summaryId=request.getParameter("summaryId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("summaryId", summaryId);
		return this.createModelAndView("technical/summary/summaryAddBiotech", model);
	}
	/**
	 * @Description:   生技部同意
	 * @author         huoxy 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeBiotech")
	public @ResponseBody ResultObj agreeBiotech(HttpServletRequest request,@RequestBody SummaryEntity summaryEntity){
		ResultObj resultObj = new ResultObj();
		try {
			summaryEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECH.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),summaryEntity.getApproveIdea()==null?"":summaryEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			summaryService.updateSpnrAgree(summaryEntity,userEntity);
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
	@RequestMapping("/disAgreeBiotech")
	public @ResponseBody ResultObj disAgreeBiotech(HttpServletRequest request,@RequestBody SummaryEntity summaryEntity){
		ResultObj resultObj = new ResultObj();
		try {
			summaryEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECH.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
//			SummaryEntity summaryEntity=summaryService.findById(summaryEntity.getId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),summaryEntity.getApproveIdea()==null?"":summaryEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			summaryService.updateSpnrDisagree(summaryEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 *	跳转到生技主任页面
	 */
	@RequestMapping("/getAddBiotechManage")
	public ModelAndView getAddBiotechManage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String summaryId=request.getParameter("summaryId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("summaryId", summaryId);
		return this.createModelAndView("technical/summary/summaryAddBiotechManage", model);
	}
	/**
	 * @Description:   生技主任同意
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeBiotechManage")
	public @ResponseBody ResultObj agreeBiotechManage(HttpServletRequest request,@RequestBody SummaryEntity summaryEntity){
		ResultObj resultObj = new ResultObj();
		try {
			summaryEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),summaryEntity.getApproveIdea()==null?"":summaryEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			summaryService.updateSpnrAgree(summaryEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   生技主任不同意
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeBiotechManage")
	public @ResponseBody ResultObj disAgreeBiotechManage(HttpServletRequest request,@RequestBody SummaryEntity summaryEntity){
		ResultObj resultObj = new ResultObj();
		try {
			summaryEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
//			TechnicalEntity technicalEn=technicalService.findById(technicalEntity.getId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),summaryEntity.getApproveIdea()==null?"":summaryEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			summaryService.updateSpnrDisagree(summaryEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   生技主任驳回到上一级
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeBiotechManageUp")
	public @ResponseBody ResultObj disAgreeBiotechManageUp(HttpServletRequest request,@RequestBody SummaryEntity summaryEntity){
		ResultObj resultObj = new ResultObj();
		try {
			summaryEntity.setSpFlag(TechnicalBtnTypeEnum.BIOTECHMANAGE.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
//			TechnicalEntity technicalEn=technicalService.findById(technicalEntity.getId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK_END.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK_END.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),summaryEntity.getApproveIdea()==null?"":summaryEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			summaryService.updateSpnrDisagreeUp(summaryEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 *	跳转到上级领导页面
	 */
	@RequestMapping("/getAddLeader")
	public ModelAndView getAddLeader(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String summaryId=request.getParameter("summaryId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("summaryId", summaryId);
		return this.createModelAndView("technical/summary/summaryAddLeader", model);
	}
	/**
	 * @Description:  上级领导同意
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeLeader")
	public @ResponseBody ResultObj agreeLeader(HttpServletRequest request,@RequestBody SummaryEntity summaryEntity){
		ResultObj resultObj = new ResultObj();
		try {
			summaryEntity.setSpFlag(TechnicalBtnTypeEnum.LEADER.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),summaryEntity.getApproveIdea()==null?"":summaryEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			summaryService.updateSpnrAgree(summaryEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   上级领导不同意
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeLeader")
	public @ResponseBody ResultObj disAgreeLeader(HttpServletRequest request,@RequestBody SummaryEntity summaryEntity){
		ResultObj resultObj = new ResultObj();
		try {
			summaryEntity.setSpFlag(TechnicalBtnTypeEnum.LEADER.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
//			TechnicalEntity technicalEn=technicalService.findById(technicalEntity.getId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),summaryEntity.getApproveIdea()==null?"":summaryEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			summaryService.updateSpnrDisagree(summaryEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   上级领导驳回到上一级
	 * @author         zhangzq 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeLeaderUp")
	public @ResponseBody ResultObj disAgreeLeaderUp(HttpServletRequest request,@RequestBody SummaryEntity summaryEntity){
		ResultObj resultObj = new ResultObj();
		try {
			summaryEntity.setSpFlag(TechnicalBtnTypeEnum.LEADER.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
//			TechnicalEntity technicalEn=technicalService.findById(technicalEntity.getId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK_END.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK_END.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),summaryEntity.getApproveIdea()==null?"":summaryEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			summaryService.updateSpnrDisagreeUp(summaryEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	//废票
		@RequestMapping("/disAgreeFp")
		public @ResponseBody ResultObj disAgreeFp(HttpServletRequest request,@RequestBody SummaryEntity summaryEntity){
			ResultObj resultObj = new ResultObj();
			try {
				summaryEntity.setSpFlag(TechnicalBtnTypeEnum.REJECT.getCode());
				String taskId=request.getParameter("taskId");
				String procInstId=request.getParameter("procInstId");
				String selectUser=request.getParameter("selectUser");
				SysUserEntity userEntity= RequestContext.get().getUser();
				Map<String, Object> taskVariables=new HashMap<String, Object>();
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
				taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
				taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
				taskVariables.put(ExamMarkEnum.COMMENT.getCode(),summaryEntity.getApproveIdea()==null?"":summaryEntity.getApproveIdea());//审批意见
				actTaskService.complete(taskId,procInstId,taskVariables);
				summaryService.updateSpnrDisagree(summaryEntity,userEntity);
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
			String summaryId=request.getParameter("summaryId");
			String taskId=request.getParameter("taskId");
			List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
			model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
			model.put("summaryId", summaryId);
			return this.createModelAndView("technical/summary/summaryAddZtj", model);
		}
		/**
		 * @Description:  负责人提交同意
		 * @author         zhangzq 
		 * @throws         Exception
		 */
		@RequestMapping("/agreeFzrtj")
		public @ResponseBody ResultObj agreeFzrtj(HttpServletRequest request,@RequestBody SummaryEntity summaryEntity){
			ResultObj resultObj = new ResultObj();
			try {
				summaryEntity.setSpFlag(TechnicalBtnTypeEnum.REJECT.getCode());
				String taskId=request.getParameter("taskId");
				String procInstId=request.getParameter("procInstId");
				String selectUser=request.getParameter("selectUser");
				SysUserEntity userEntity= RequestContext.get().getUser();
				
				Map<String, Object> variables=new HashMap<String, Object>();
				variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
				variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
				variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
				variables.put(ExamMarkEnum.COMMENT.getCode(),summaryEntity.getApproveIdea()==null?"":summaryEntity.getApproveIdea());//审批意见
				actTaskService.complete(taskId,procInstId, variables);
				
				summaryService.updateSpnrAgree(summaryEntity,userEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultObj;
		}
}