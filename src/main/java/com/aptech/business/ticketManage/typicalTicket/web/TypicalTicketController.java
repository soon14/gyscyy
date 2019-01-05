package com.aptech.business.ticketManage.typicalTicket.web;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.TypicalTicketApproveStatusEnum;
import com.aptech.business.component.dictionary.TypicalTicketTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.ticketManage.operationTicket.service.OperationTicketService;
import com.aptech.business.ticketManage.typicalTicket.domain.TypicalTicketEntity;
import com.aptech.business.ticketManage.typicalTicket.service.TypicalTicketService;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workElectric.service.WorkElectricService;
import com.aptech.business.ticketManage.workElectricTwo.service.WorkElectricTwoService;
import com.aptech.business.ticketManage.workTicket.service.WorkTicketService;
import com.aptech.business.ticketManage.workTicketTwo.service.WorkTicketTwoService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
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
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 典型票配置控制器
 * 
 * @author
 * @created 2017-07-20 15:55:55
 * @lastModified
 * @history
 * 
 */
@Controller
@RequestMapping("/typicalTicket")
public class TypicalTicketController extends
		BaseController<TypicalTicketEntity> {

	@Autowired
	private TypicalTicketService typicalTicketService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private OperationTicketService operationTicketService;
	@Autowired
	private WorkElectricService workElectricService;
	@Autowired
	private WorkTicketService workTicketService;
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private WorkControlCardService workControlCardService;
	@Autowired
	private WorkTicketTwoService workTicketTwoService;
	@Autowired
	private WorkElectricTwoService workElectricTwoService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private ActTaskService actTaskService;
	@Override
	public IBaseEntityOperation<TypicalTicketEntity> getService() {
		return typicalTicketService;
	}

	/**
	 * list页面跳转
	 * 
	 * @Title:
	 * @Description:
	 * @param
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		//获取登录用户信息并返回  
        SysUserEntity userEntity= RequestContext.get().getUser();
        model.put("userEntity", userEntity);
        
		// 票类型
		ComboboxVO typicalTicketType = new ComboboxVO();
		for (TypicalTicketTypeEnum typicalTicketTypeEnum : TypicalTicketTypeEnum
				.values()) {
			typicalTicketType.addOption(typicalTicketTypeEnum.getCode(),
					typicalTicketTypeEnum.getName());
		}
		model.put("typicalTicketType",
				JsonUtil.toJson(typicalTicketType.getOptions()));
		// 部门
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
//		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		for (int i = 0; i < treeNodeList.size(); i++) {
//			treeNodeList.get(i).setOpen("true");
//		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(unitList));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));

		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList=sysUserService.findByCondition(conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		// 状态下拉
		ComboboxVO typicalTicketApproveStatus = new ComboboxVO();
		for (TypicalTicketApproveStatusEnum typicalTicketApproveStatusEnum : TypicalTicketApproveStatusEnum
				.values()) {
			typicalTicketApproveStatus.addOption(typicalTicketApproveStatusEnum.getCode(),
					typicalTicketApproveStatusEnum.getName());
		}
		model.put("typicalTicketApproveStatus",
				JsonUtil.toJson(typicalTicketApproveStatus.getOptions()));
		return this.createModelAndView(
				"ticketManage/typicalTicket/typicalTicketList", model);
	}

	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("entity", new TypicalTicketEntity());
		// 票类型
		ComboboxVO typicalTicketType = new ComboboxVO();
		for (TypicalTicketTypeEnum typicalTicketTypeEnum : TypicalTicketTypeEnum
				.values()) {
			typicalTicketType.addOption(typicalTicketTypeEnum.getCode(),
					typicalTicketTypeEnum.getName());
		}
		model.put("typicalTicketType",
				JsonUtil.toJson(typicalTicketType.getOptions()));
		return this.createModelAndView(
				"ticketManage/typicalTicket/typicalTicketAdd", model);
	}

	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request,
			@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		TypicalTicketEntity t = typicalTicketService.findById(id);
		model.put("t", t);
		// 票类型
		ComboboxVO typicalTicketType = new ComboboxVO();
		for (TypicalTicketTypeEnum typicalTicketTypeEnum : TypicalTicketTypeEnum
				.values()) {
			typicalTicketType.addOption(typicalTicketTypeEnum.getCode(),
					typicalTicketTypeEnum.getName());
		}
		model.put("typicalTicketType",
				JsonUtil.toJson(typicalTicketType.getOptions()));
		return this.createModelAndView(
				"ticketManage/typicalTicket/typicalTicketEdit", model);
	}
	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request,
			@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		TypicalTicketEntity t = typicalTicketService.findById(id);
		model.put("t", t);
		// 票类型
		ComboboxVO typicalTicketType = new ComboboxVO();
		for (TypicalTicketTypeEnum typicalTicketTypeEnum : TypicalTicketTypeEnum
				.values()) {
			typicalTicketType.addOption(typicalTicketTypeEnum.getCode(),
					typicalTicketTypeEnum.getName());
		}
		model.put("typicalTicketType",
				JsonUtil.toJson(typicalTicketType.getOptions()));
		return this.createModelAndView(
				"ticketManage/typicalTicket/typicalTicketDetail", model);
	}
	/**
	 * @Description: 提交
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/submit/{id}")
	public @ResponseBody
	ResultObj submit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		return typicalTicketService.submit(id,params);
	}
	/**
	 * @Description: 修改提交
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/editSubmit/{id}")
	public @ResponseBody
	ResultObj editSubmit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		return typicalTicketService.editSubmit(id,params);
	}
	/**
	 * @Description: 审批页面初始化
	 * @author changl
	 * @Date 2017年6月5日 下午1:17:55
	 * @throws Exception
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request,
			@PathVariable Long id,@PathVariable String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		TypicalTicketEntity tEntity=typicalTicketService.findById(id);
		model.put("tEntity", tEntity);
		model.put("type", type);
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.TYPICALTICKET_PROCESS_KEY20.getName()));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}
		model.put("userEntity", userEntity);
		//查询各个人的按钮权限 结束
		return this.createModelAndView("ticketManage/typicalTicket/typicalTicketApprove",
				model);
	}
	/**
	 * @Description: 修改审批页面初始化
	 * @author changl
	 * @Date 2017年6月5日 下午1:17:55
	 * @throws Exception
	 */
	@RequestMapping("/editApprove/{id}/{type}")
	public ModelAndView editApprove(HttpServletRequest request,
			@PathVariable Long id,@PathVariable String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		TypicalTicketEntity tEntity=typicalTicketService.findById(id);
		model.put("tEntity", tEntity);
		model.put("type", type);
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING,MatchTypeEnum.EQ,ProcessMarkEnum.TYPICALTICKET_PROCESS_KEY30.getName()));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}
		model.put("userEntity", userEntity);
		//查询各个人的按钮权限 结束
		return this.createModelAndView("ticketManage/typicalTicket/typicalTicketEditApprove",
				model);
	}
	/**
	 * @Description: 驳回
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/reject/{id}")
	public @ResponseBody ResultObj reject(@RequestBody TypicalTicketEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", TypicalTicketApproveStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.BACK.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		return typicalTicketService.approve(t,params);
	}
	/**
	 * @Description: 专工审批
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/overhaul/{id}")
	public @ResponseBody ResultObj overhaul(@RequestBody TypicalTicketEntity t) {
		Object status= actTaskService.getVarialble(t.getTaskId(), BranchMarkEnum.PARLLEL_BRANCH_KEY.getName());
		Map<String, Object> params = new HashMap<String, Object>();
		if(status!=null&&String.valueOf(status).equals(String.valueOf(ExamResultEnum.BACK.getId()))){
			params.put("status", TypicalTicketApproveStatusEnum.REJECT.getCode());
		}else{
			params.put("status", TypicalTicketApproveStatusEnum.OVERHAULAPPROVE.getCode());
		}
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		return typicalTicketService.approve(t,params);
	}
	/**
	 * @Description: 主任审批
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/overhaulApprove/{id}")
	public @ResponseBody ResultObj overhaulApprove(@RequestBody TypicalTicketEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		Object status= actTaskService.getVarialble(t.getTaskId(), BranchMarkEnum.PARLLEL_BRANCH_KEY.getName());
		if(status!=null&&String.valueOf(status).equals(String.valueOf(ExamResultEnum.BACK.getId()))){
			params.put("status", TypicalTicketApproveStatusEnum.REJECT.getCode());
		}else{
			params.put("status", TypicalTicketApproveStatusEnum.BIOTECHAPPROVE.getCode());
		}
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		return typicalTicketService.approve(t,params);
	}
	/**
	 * @Description: 主任审批
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/biotechApprove/{id}")
	public @ResponseBody ResultObj biotechApprove(@RequestBody TypicalTicketEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		Object status= actTaskService.getVarialble(t.getTaskId(), BranchMarkEnum.PARLLEL_BRANCH_KEY.getName());
		if(status!=null&&String.valueOf(status).equals(String.valueOf(ExamResultEnum.BACK.getId()))){
			params.put("status", TypicalTicketApproveStatusEnum.REJECT.getCode());
		}else{
			params.put("status", TypicalTicketApproveStatusEnum.APPROVE.getCode());
		}
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		return typicalTicketService.approve(t,params);
	}
	/**
	 * @Description: 分管领导审批
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/resultsApprove/{id}")
	public @ResponseBody ResultObj approve(@RequestBody TypicalTicketEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", TypicalTicketApproveStatusEnum.RESULTS.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		return typicalTicketService.approve(t,params);
	}
	
	/**
	 * @Description: 专工审批
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/zgsp/{id}")
	public @ResponseBody ResultObj zgspapprove(@RequestBody TypicalTicketEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", TypicalTicketApproveStatusEnum.RESULTS.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		return typicalTicketService.approve(t,params);
	}
	/**
	 * 选择带回页面
	 * 
	 * @Title:
	 * @Description:
	 * @param
	 * @return
	 */
	@RequestMapping("/typicalTicketDialog")
	public ModelAndView typicalTicketDialog(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		String type=(String) request.getParameter("type");
		model.put("type", type);
		// 票类型
		ComboboxVO typicalTicketType = new ComboboxVO();
		for (TypicalTicketTypeEnum typicalTicketTypeEnum : TypicalTicketTypeEnum
				.values()) {
			typicalTicketType.addOption(typicalTicketTypeEnum.getCode(),
					typicalTicketTypeEnum.getName());
		}
		model.put("typicalTicketType",
				JsonUtil.toJson(typicalTicketType.getOptions()));
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		for (int i = 0; i < treeNodeList.size(); i++) {
//			treeNodeList.get(i).setOpen("true");
//		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		SysUserEntity userEntity= RequestContext.get().getUser();

		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList=sysUserService.findByCondition(conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		// 状态下拉
		ComboboxVO typicalTicketApproveStatus = new ComboboxVO();
		for (TypicalTicketApproveStatusEnum typicalTicketApproveStatusEnum : TypicalTicketApproveStatusEnum
				.values()) {
			typicalTicketApproveStatus.addOption(typicalTicketApproveStatusEnum.getCode(),
					typicalTicketApproveStatusEnum.getName());
		}
		model.put("typicalTicketApproveStatus",
				JsonUtil.toJson(typicalTicketApproveStatus.getOptions()));
		return this.createModelAndView(
				"ticketManage/typicalTicket/typicalTicketDialog", model);
	}
	/**
	 * @Description: 提交查询提交人
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping("/sureSubmitPerson")
	public ModelAndView sureSubmitPerson(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,
				ProcessMarkEnum.TYPICALTICKET_PROCESS_KEY20.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		String modelId="";
		if(!defList.isEmpty()){
			modelId=defList.get(0).getModelId();
		}
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
		request.setAttribute("userList", userList);
		return this.createModelAndView("ticketManage/typicalTicket/sureSubmitPerson", model);
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
		params.put("conditions", JSONArray.fromObject(conditions));
		List<TypicalTicketEntity> dataList=typicalTicketService.findByCondition(params, null);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (TypicalTicketEntity typicalTicketEntity : dataList) {
			typicalTicketEntity.setCreateDateString(sf.format(typicalTicketEntity.getCreateDate()));
		}
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "典型票报表模板.xlsx","典型票管理.xlsx", resultMap);
	}
	/**
	 * @Description: 缺陷审批查询提交人(同意)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping("/submitPersonAgree/{taskId}")
	public ModelAndView submitPersonAgree(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("ticketManage/typicalTicket/sureSubmitPerson", model);
	}
	/**
	 * @Description: 缺陷审批查询提交人(驳回上一级)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping("/submitPersonBack/{taskId}")
	public ModelAndView submitPersonBack(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.BACK.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("ticketManage/typicalTicket/sureSubmitPerson", model);
	}
	/**
	 * @Description: 缺陷审批查询提交人(驳回)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping("/submitPersonBackEnd/{taskId}")
	public ModelAndView submitPersonBackEnd(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.BACK_END.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("ticketManage/typicalTicket/sureSubmitPerson", model);
	}
	/**
	 * @Description:   鉴定跳转页面
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@RequestMapping("/invalid")
	public ModelAndView invalid(HttpServletRequest request){
		SysUserEntity userEntity= RequestContext.get().getUser();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id=request.getParameter("id");
		resultMap.put("invalidId", id);
		resultMap.put("dateInvalid", new Date());
		resultMap.put("userEntity", userEntity);
		//鉴定下拉框
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("IDENTITY");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        resultMap.put("identifyType", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
		        
		return new ModelAndView("/ticketManage/typicalTicket/workInvalid",resultMap);
	}
	
	/**
	 * @Description:   鉴定
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@RequestMapping("/saveInvalidInfo")
	public @ResponseBody ResultObj saveInvalidInfo(@RequestBody TypicalTicketEntity ticketEntity, HttpServletRequest request) {
		return typicalTicketService.saveInvalid(ticketEntity);
	}
}