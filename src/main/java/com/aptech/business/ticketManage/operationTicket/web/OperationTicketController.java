package com.aptech.business.ticketManage.operationTicket.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.IssetEnum;
import com.aptech.business.component.dictionary.Istypicaleum;
import com.aptech.business.component.dictionary.OperationStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.TypicalTicketTypeEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelEntity;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.orgaApp.domain.OrgaAppEntity;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.ticketManage.operationItem.domain.OperationItemEntity;
import com.aptech.business.ticketManage.operationItem.service.OperationItemService;
import com.aptech.business.ticketManage.operationTicket.domain.OperationTicketEntity;
import com.aptech.business.ticketManage.operationTicket.service.OperationTicketService;
import com.aptech.business.ticketManage.repairTicket.domain.RepairTicketEntity;
import com.aptech.business.ticketManage.typicalTicket.domain.TypicalTicketEntity;
import com.aptech.business.ticketManage.typicalTicket.service.TypicalTicketService;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicket.service.WorkTicketService;
import com.aptech.business.ticketManage.workTicketEquip.domain.WorkTicketEquipEntity;
import com.aptech.business.ticketManage.workTicketEquip.service.WorkTicketEquipService;
import com.aptech.business.ticketManage.workTicketFireTwo.domain.WorkTicketFireTwoEntity;
import com.aptech.common.fourcode.service.FourCodeService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
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
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 操作票配置控制器
 * 
 * @author
 * @created 2017-07-12 15:53:44
 * @lastModified
 * @history
 * 
 */
@Controller
@RequestMapping("/operationTicket")
public class OperationTicketController extends
		BaseController<OperationTicketEntity> {

	@Autowired
	private OperationTicketService operationTicketService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private FourCodeService fourCodeService;
	@Autowired
	private OperationItemService operationItemService;
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private WorkTicketService workTicketService;
	@Autowired
	private TypicalTicketService typicalTicketService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private WorkTicketEquipService workTicketEquipService;
	@Override
	public IBaseEntityOperation<OperationTicketEntity> getService() {
		return operationTicketService;
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
			Map<String, Object> params,String cookieTime) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		//获取登录用户信息并返回  
        SysUserEntity userEntity= RequestContext.get().getUser();
        model.put("userEntity", userEntity);
		
		model.put("cookieTime", cookieTime);
		// 部门
//		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);

		model.put("unitNameIdTreeList", JsonUtil.toJson(unitList));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		// 状态下拉
		ComboboxVO searchprocessStatus = new ComboboxVO();
		for (OperationStatusEnum operationStatusEnum : OperationStatusEnum
				.values()) {
			searchprocessStatus.addOption(operationStatusEnum.getCode(),
					operationStatusEnum.getName());
		}
		model.put("searchprocessStatus",JsonUtil.toJson(searchprocessStatus.getOptions()));
		
		//典型票鉴定人员
		ComboboxVO setuser = new ComboboxVO();
		
		conditions.clear();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,144));
		List<SysUserEntity> setuserList = sysUserService.findByCondition(conditions, null);
		for (SysUserEntity user : setuserList) {
			setuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("setuser", JsonUtil.toJson(setuser.getOptions()));
		
		
		return this.createModelAndView(
				"ticketManage/operationTicket/operationTicketList", model);
	}

	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		String typicalTicketId=(String)request.getParameter("typicalTicketId");
		model.put("typicalTicketId", typicalTicketId);
		OperationTicketEntity t = new OperationTicketEntity();
		t.setId(System.currentTimeMillis());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setOperateName(userEntity.getName());
		model.put("t", t);
		
		//往表里面加默认记录无
		OperationItemEntity operationItemEntity=new OperationItemEntity();
		operationItemEntity.setFinishDate(new Date());
		operationItemEntity.setOperationId(t.getId());
		operationItemEntity.setOrder(Long.valueOf(1));
		operationItemEntity.setOperationItem("无");
		operationItemService.addEntity(operationItemEntity);
		// 部门
//		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
////		for (int i = 0; i < treeNodeList.size(); i++) {
////			treeNodeList.get(i).setOpen("true");
////		}
//		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1  OR C_GENERATION_TYPE = 4"));
		companyConditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		//人员
		ComboboxVO searchuser = new ComboboxVO();
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
			
			List<Condition> conditions=new ArrayList<Condition>();
			conditions.clear();
			conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,sysUnitEntity.getId()));
			List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
			for (SysUserEntity user : userList) {
				searchuser.addOption(user.getId().toString(), user.getName());
			}
       }
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
	    model.put("unitNameIdTreeList", JsonUtil.toJson(unitList));
		model.put("userEntity", userEntity);
		//下面的是要查询班组，
		List<OrgaAppEntity> orgaApp = orgaAppService.findAll();
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		for(OrgaAppEntity orgaAppEntity : orgaApp){
			comboWorkTicketVO.addOption(orgaAppEntity.getId().toString(), orgaAppEntity.getName());
		}
		//TODO下拉框具体内容根据具体业务定制
		model.put("groupIdCombobox", JsonUtil.toJson(comboWorkTicketVO.getOptions()));
		// 人员
//		ComboboxVO searchuser = new ComboboxVO();
//		List<Condition> conditions=new ArrayList<Condition>();
//		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
//		List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
//		for (SysUserEntity user : userList) {
//			searchuser.addOption(user.getId().toString(), user.getName());
//		}
//		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView(
				"ticketManage/operationTicket/operationTicketAdd", model);
	}

	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request,
			@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		String typicalTicketId=(String)request.getParameter("typicalTicketId");
		model.put("typicalTicketId", typicalTicketId);
		OperationTicketEntity t = this.getService().findById(id);
		model.put("t", t);
		// 部门
//		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
////		for (int i = 0; i < treeNodeList.size(); i++) {
////			treeNodeList.get(i).setOpen("true");
////		}
//		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		ComboboxVO companyVo = new ComboboxVO();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		//人员
		ComboboxVO searchuser = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
			
			List<Condition> conditions=new ArrayList<Condition>();
			conditions.clear();
			conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,sysUnitEntity.getId()));
			List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
			for (SysUserEntity user : userList) {
				searchuser.addOption(user.getId().toString(), user.getName());
			}
       }
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
	
	    model.put("unitNameIdTreeList", JsonUtil.toJson(unitList));
		//下面的是要查询班组，
		List<OrgaAppEntity> orgaApp = orgaAppService.findAll();
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		for(OrgaAppEntity orgaAppEntity : orgaApp){
			comboWorkTicketVO.addOption(orgaAppEntity.getId().toString(), orgaAppEntity.getName());
		}
		//TODO下拉框具体内容根据具体业务定制
		model.put("groupIdCombobox", JsonUtil.toJson(comboWorkTicketVO.getOptions()));
		// 人员
//		ComboboxVO searchuser = new ComboboxVO();
//		List<Condition> conditions=new ArrayList<Condition>();
//		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//		SysUserEntity userEntity= RequestContext.get().getUser();
//
//		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
//		List<SysUserEntity> userList=sysUserService.findByCondition(conditions, null);
//		for (SysUserEntity user : userList) {
//			searchuser.addOption(user.getId().toString(), user.getName());
//		}
//		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		//获取关联设备
		List<EquipLedgerEntity> templist = new ArrayList<EquipLedgerEntity>();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID",FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
		conditions.add(new Condition("C_TICKET_TYPE",FieldTypeEnum.INT, MatchTypeEnum.EQ,2));
		conditions.add(new Condition("C_STATUS",FieldTypeEnum.INT, MatchTypeEnum.EQ,1));
		List<WorkTicketEquipEntity> workTicketEquipList = workTicketEquipService.findByCondition(conditions, null);
		for(WorkTicketEquipEntity entity:workTicketEquipList){
			conditions.clear();
			conditions.add(new Condition("L.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,entity.getEquipCode()));
			List<EquipLedgerEntity>  equipList = equipLedgerService.findByCondition(conditions, null);
			if(!equipList.isEmpty()){
				templist.add(equipList.get(0));
			}
		}
		model.put("userUnitRels", JsonUtil.toJson(templist));
		return this.createModelAndView("ticketManage/operationTicket/operationTicketEdit", model);
	}
	/**
	 * 修改
	 * 
	 * @Title: edit
	 * @Description:
	 * @param T
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.POST)
	public @ResponseBody ResultObj edit(@RequestBody OperationTicketEntity t){
		
		if (t.getTypicalTicketId()!=null&&t.getTypicalTicketId()!="") {
			
			TypicalTicketEntity ticketEntity = typicalTicketService.findById(Long.parseLong(t.getTypicalTicketId()));
			ticketEntity.setName(t.getTypicalName());
			ticketEntity.setType(t.getTypicalType());
			ticketEntity.setFlag("2");
			typicalTicketService.updateEntity(ticketEntity);
		}
		operationTicketService.updateEntity(t);
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	/**
	 * @Description: 查看页面初始化
	 * @author changl
	 * @Date 2017年6月5日 下午1:17:55
	 * @throws Exception
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetailPage(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		OperationTicketEntity t = this.getService().findById(id);
		if(StringUtil.isEmpty(t.getFileId())){
			t.setFileId("[]");
		}
		model.put("t", t);
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		for (int i = 0; i < treeNodeList.size(); i++) {
//			treeNodeList.get(i).setOpen("true");
//		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		model.put("rlId", "");
		return this.createModelAndView("ticketManage/operationTicket/operationTicketDetail",
				model);
	}
	/**
	 * @Description: 查看页面初始化
	 * @author changl
	 * @throws Exception 
	 * @Date 2017年6月5日 下午1:17:55
	 * @throws Exception
	 */
	@RequestMapping("/runDetail")
	public ModelAndView runDetail(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String code = request.getParameter("code");
		String rlId = request.getParameter("rlId");
		code = new String(code.getBytes("iso-8859-1"), "utf-8");
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("T.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, code));
		List<OperationTicketEntity> operationTicketList = operationTicketService.findByCondition(conditions, null);
		OperationTicketEntity operationTicketEntity = operationTicketList.get(0);
		model.put("workTicketId", operationTicketEntity.getId());
		
		model.put("t", operationTicketEntity);
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		for (int i = 0; i < treeNodeList.size(); i++) {
//			treeNodeList.get(i).setOpen("true");
//		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		model.put("rlId", rlId);
		return this.createModelAndView("ticketManage/operationTicket/operationTicketDetail",
				model);
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
		return operationTicketService.submit(id,params);
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
		OperationTicketEntity t = this.getService().findById(id);
		model.put("t", t);
		model.put("type",type);
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		for (int i = 0; i < treeNodeList.size(); i++) {
//			treeNodeList.get(i).setOpen("true");
//		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.OPERATIONTICKET_PROCESS_KEY.getName()));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}
		//查询各个人的按钮权限 结束
		return this.createModelAndView("ticketManage/operationTicket/operationTicketApprove",
				model);
	}
	/**
	 * @Description: 驳回
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/reject/{id}")
	public @ResponseBody ResultObj reject(@RequestBody OperationTicketEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OperationStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.BACK_END.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		return operationTicketService.approve(t,params);
	}
	/**
	 * @Description: 废票
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/invalid/{id}")
	public @ResponseBody ResultObj invalid(@RequestBody OperationTicketEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OperationStatusEnum.INVALID.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.BACK.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		return operationTicketService.approve(t,params);
	}
	/**
	 * @Description: 驳回(值长)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/foremanReject/{id}")
	public @ResponseBody ResultObj foremanReject(@RequestBody OperationTicketEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OperationStatusEnum.FOREMANREJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.BACK.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		t.setOperateItemNum(0);
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_OPERATION_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,
				t.getId()));
		List<OperationItemEntity> operationItemList=operationItemService.findByCondition(conditions, null);
		for (OperationItemEntity operationItemEntity :operationItemList) {
			operationItemEntity.setSimulation("");
			operationItemEntity.setActual("");
			operationItemService.update(operationItemEntity);
		}
		return operationTicketService.approve(t,params);
	}
	/**
	 * @Description: 值长确定
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/foremanConfirm/{id}")
	public @ResponseBody ResultObj foremanConfirm(@RequestBody OperationTicketEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OperationStatusEnum.RESULTS.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		return operationTicketService.approve(t,params);
	}
	/**
	 * @Description: 记录结果
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/implement/{id}")
	public @ResponseBody ResultObj implement(@RequestBody OperationTicketEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OperationStatusEnum.RESULTS.getCode());//改为9  以前6
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		return operationTicketService.approve(t,params);
	}
	/**
	 * @Description: 同意(值长)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/foremanExamine/{id}")
	public @ResponseBody ResultObj foremanExamine(@RequestBody OperationTicketEntity t) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setWorkManagerId(userEntity.getId());
		t.setWorkManagerName(userEntity.getName());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OperationStatusEnum.IMPLEMENT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		/*Map<String, Object> codeparams=new HashMap<String, Object> ();
		OperationTicketEntity tEntity = operationTicketService.findById(t.getId());
		SysUnitEntity sysunit=sysUnitService.findById(tEntity.getUnitId());
		codeparams.put("STITION", sysunit.getNameAB());
		codeparams.put("YM", new Date());
		String code=fourCodeService.getBusinessCode("操作票编码", codeparams);*/
		//工作票编号开始
        Map<String, Object> codeparams=new HashMap<String, Object> ();
		SysUnitEntity sysunit=sysUnitService.findById(userEntity.getUnitId());
		codeparams.put("workcode", sysunit.getNameAB());
		codeparams.put("workTicketType", "1");
		codeparams.put("YM", new Date());
		String code=fourCodeService.getBusinessCode("工作票编号", codeparams);
		String str="";
		StringBuilder sb=new StringBuilder(code);
		sb.replace(3, 5, str);
		String nowCode=sb.toString();
		//工作票编号结束
		t.setCode(nowCode);
		return operationTicketService.approve(t,params);
	}
	/**
	 * @Description: 同意(值班负责人)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/monitor/{id}")
	public @ResponseBody ResultObj monitor(@RequestBody OperationTicketEntity t) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setPicId(userEntity.getId());
		t.setPicName(userEntity.getName());
		OperationTicketEntity ticketEntity = operationTicketService.findById(t.getId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OperationStatusEnum.IMPLEMENT.getCode());//4变成5
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),ticketEntity.getGuardianId());
//		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		return operationTicketService.approve(t,params);
	}
	/**
	 * @Description: 同意(监护人)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/guardian/{id}")
	public @ResponseBody ResultObj guardian(@RequestBody OperationTicketEntity t) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setGuardianId(userEntity.getId());
		t.setGuardianName(userEntity.getName());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OperationStatusEnum.MONITOR.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		return operationTicketService.approve(t,params);
	}
	/**
	 * @Description: 驳回提交
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/rejectSubmit/{id}")
	public @ResponseBody ResultObj rejectSubmit(@RequestBody OperationTicketEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OperationStatusEnum.GUARDIAN.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.RESULT.getCode(), "");
		return operationTicketService.approve(t,params);
	}
	/**
	 * @Description:   导出
	 * @author         changl 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		//查询集控中心和生技处人员
		List<Condition> userConditions=new ArrayList<Condition>();
		userConditions.add(new Condition(" a.C_UNIT_ID = 138 OR a.C_UNIT_ID = 144"));//集控中心和生技处人员
		userConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<SysUserEntity> userEntities =sysUserService.findByCondition(userConditions, null);
		List<String> userNameList = new ArrayList<String>();
		for (SysUserEntity userEntity : userEntities) {
			userNameList.add(userEntity.getLoginName());
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<OperationTicketEntity> dataList = new ArrayList<OperationTicketEntity>();
		Page<OperationTicketEntity> page=new Page<OperationTicketEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		
		conditions.add(new Condition("t.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,userEntity.getUnitId()));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
		for (String str : userNameList) {
			if (userEntity.getLoginName().equals(str)|| userEntity.getLoginName().equals("admin")||userEntity.getLoginName().equals("super")) {
				conditions.clear();
				conditions = OrmUtil.changeMapToCondition(params);
				//本身是工作票
				conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
				dataList = (List<OperationTicketEntity>)operationTicketService.findByCondition(conditions, page);
			}else {
				dataList =  (List<OperationTicketEntity>) operationTicketService.findByCondition(conditions, page);
			}
		}
//		List<OperationTicketEntity> dataList=operationTicketService.findByCondition(params, null);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		DateFormatUtil sf = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		for (OperationTicketEntity operationTicketEntity : dataList) {
			if (operationTicketEntity.getStartDate()!=null&&operationTicketEntity.getEndDate()!=null) {
				operationTicketEntity.setStartDateString(sf.format(operationTicketEntity.getStartDate()));
				operationTicketEntity.setEndDateString(sf.format(operationTicketEntity.getEndDate()));
				
			}
		}
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "操作票报表模板.xlsx","操作票.xlsx", resultMap);
	}
	/**
	 * @Description: 设置典型票
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/setIsSet/{id}")
	public @ResponseBody ResultObj setIsSet(HttpServletRequest request,
			@PathVariable Long id,@RequestBody TypicalTicketEntity tEntity) {
		OperationTicketEntity t=operationTicketService.findById(id);
		t.setIsSet(Long.parseLong(IssetEnum.ISSETYES.getCode()));
		return  operationTicketService.isSet(t,tEntity);
	}
	/**
	 * @Description: 引用典型票
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/yydxp")
	public @ResponseBody ResultObj yydxp(HttpServletRequest request,@RequestBody OperationTicketEntity t) {
		return  operationTicketService.yydxp(t);
	}
	/**
	 * 设置典型票弹出页面
	 */
	@RequestMapping("/getIsSet")
	public ModelAndView getIsSet(HttpServletRequest request,String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		TypicalTicketEntity t =new TypicalTicketEntity();
		t.setType(type);
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
				"ticketManage/operationTicket/operationTicketIsSet", model);
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
				ProcessMarkEnum.OPERATIONTICKET_PROCESS_KEY.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		String modelId="";
		if(!defList.isEmpty()){
			modelId=defList.get(0).getModelId();
		}
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
		request.setAttribute("userList", userList);
		return this.createModelAndView("ticketManage/operationTicket/sureSubmitPerson", model);
	}
	/**
	 * @Description: 获取设备相关工作票
	 * @author wangcc
	 * @Date 2017年7月6日 上午11:14:09
	 * @throws Exception
	 */
	@RequestMapping(value = "/workTicketList", method = RequestMethod.POST)
	public @ResponseBody ResultListObj getWorkTicketListWithEquip(
			HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<WorkTicketEntity> page = PageUtil.getPage(params);
		List<WorkTicketEntity> entities = workTicketService.findAll(
				params, page);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entities.size());
			}
		}
		return resultObj;
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
		        
		return new ModelAndView("/ticketManage/operationTicket/workInvalid",resultMap);
	}
	/**
	 * @Description:   鉴定
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@RequestMapping("/saveInvalid")
	public @ResponseBody ResultObj saveInvalid(@RequestBody OperationTicketEntity operationTicketEntity, HttpServletRequest request) {
		return operationTicketService.saveInvalid(operationTicketEntity);
	}
	@RequestMapping("/searchJan")
	public ModelAndView searchJan(HttpServletRequest request) throws Exception{
		String JanStart = request.getParameter("JanStart");
		String JanEnd = request.getParameter("JanEnd");
		String identify = request.getParameter("identify");
		String yearTextLocal = request.getParameter("yearTextLocal");
		String qualifiedCount = request.getParameter("qualifiedCount");
		String unQualifiedCount = request.getParameter("unQualifiedCount");
		String yearStart=yearTextLocal+"-"+JanStart+" 00:00:00";
		String yearEnd=yearTextLocal+"-"+JanEnd+" 59:59:59";
		Map<String, Object> model = new HashMap<String, Object>();
		String cookieTime = "0.1";
		model.put("cookieTime", cookieTime);
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
		ComboboxVO searchprocessStatus = new ComboboxVO();
		for (OperationStatusEnum operationStatusEnum : OperationStatusEnum
				.values()) {
			searchprocessStatus.addOption(operationStatusEnum.getCode(),
					operationStatusEnum.getName());
		}
		model.put("searchprocessStatus",
				JsonUtil.toJson(searchprocessStatus.getOptions()));
		
		model.put("identify", identify);
		model.put("qualifiedCount", qualifiedCount);
		model.put("unQualifiedCount", unQualifiedCount);
		model.put("yearStart", yearStart);
        model.put("yearEnd", yearEnd);
        return this.createModelAndView(
				"ticketManage/operationTicket/operationTicketListJan", model);
	}
	@RequestMapping("/searchMon")
	public ModelAndView searchMon(HttpServletRequest request) throws Exception{
		String JanStart = request.getParameter("JanStart");
		String JanEnd = request.getParameter("JanEnd");
		String identify = request.getParameter("identify");
		String month = request.getParameter("month");
		String qualifiedCount = request.getParameter("qualifiedCount");
		String unQualifiedCount = request.getParameter("unQualifiedCount");
		String yearStart=month+"-"+JanStart;
		String yearEnd=month+"-"+JanEnd;
		Map<String, Object> model = new HashMap<String, Object>();
		String cookieTime = "0.1";
		model.put("cookieTime", cookieTime);
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
		ComboboxVO searchprocessStatus = new ComboboxVO();
		for (OperationStatusEnum operationStatusEnum : OperationStatusEnum
				.values()) {
			searchprocessStatus.addOption(operationStatusEnum.getCode(),
					operationStatusEnum.getName());
		}
		model.put("searchprocessStatus",
				JsonUtil.toJson(searchprocessStatus.getOptions()));
		
		model.put("identify", identify);
		model.put("qualifiedCount", qualifiedCount);
		model.put("unQualifiedCount", unQualifiedCount);
		model.put("yearStart", yearStart);
		model.put("yearEnd", yearEnd);
		return this.createModelAndView(
				"ticketManage/operationTicket/operationTicketListMonth", model);
	}
	@RequestMapping(value = "/searchJanData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchJanData(HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
		String yearStart = request.getParameter("yearStart");
		String yearEnd = request.getParameter("yearEnd");
		String identify = request.getParameter("identify");
		String qualifiedCount = request.getParameter("qualifiedCount");
		String unQualifiedCount = request.getParameter("unQualifiedCount");
		Page<OperationTicketEntity> page =  PageUtil.getPage(params);
		  
			List<OperationTicketEntity> entities =operationTicketService.searchJan(yearStart,yearEnd,params,page,identify,qualifiedCount,unQualifiedCount);
			ResultListObj resultObj = new ResultListObj();
			resultObj.setDraw((Integer)params.get("draw"));
			if (entities != null) {
				resultObj.setData(entities);
				if (page != null) {
					resultObj.setRecordsTotal(page.getTotal());
				} else {
					resultObj.setRecordsTotal((long) entities.size());
				}
			}
			return resultObj;
	}
	
	/**
	 * @Description:   新增时候的验证  
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/addValidate")
	public @ResponseBody ResultObj addValidate(HttpServletRequest request) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		return operationTicketService.addValidate(userEntity);
	}
	//zzq 记录弹窗口
	@RequestMapping("/submitPersonAgree/{id}/{taskId}")
	public ModelAndView submitPersonAgree(HttpServletRequest request,@PathVariable Long id,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
//		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
//		model.put("userList", userList);
		model.put("electricId", id);
		model.put("taskId", taskId);
		if (id!=null) {
			OperationTicketEntity operationTicketEntity = operationTicketService.findById(id);
		    model.put("entity", operationTicketEntity);
		}
		return this.createModelAndView("ticketManage/operationTicket/sureSubmitPersonJljg", model);
	}
	
	//再提交
	@RequestMapping("/submitPerson2/{id}")
	public ModelAndView submitPersonPage(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,
				ProcessMarkEnum.OPERATIONTICKET_PROCESS_KEY.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		String modelId="";
		if(!defList.isEmpty()){
			modelId=defList.get(0).getModelId();
		}
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
		request.setAttribute("userList", userList);
		
		model.put("electricId", id);
		if (id!=null) {
			OperationTicketEntity operationTicketEntity = operationTicketService.findById(id);
		    model.put("entity", operationTicketEntity);
		}
		
		return this.createModelAndView("ticketManage/operationTicket/sureSubmitPersonZtj", model);
	}
	
	/**
	 * @Description:   新增时候的验证  
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/addCzpValidate")
	public @ResponseBody ResultObj addCzpValidate(HttpServletRequest request) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		return operationTicketService.addCzpValidate(userEntity);
	}
	
	//zzq  20180408
	@RequestMapping("/getDetailDx/{id}/{typicalTicketId}")
	public ModelAndView getDetailDx(HttpServletRequest request,@PathVariable Long id,@PathVariable String typicalTicketId) {
		Map<String, Object> model = new HashMap<String, Object>();
//		String typicalTicketId=(String)request.getParameter("typicalTicketId");
		model.put("typicalTicketId", typicalTicketId);
		//典型票信息
		TypicalTicketEntity tyEntity = typicalTicketService.findById(Long.parseLong(typicalTicketId));
		model.put("tyEntity", tyEntity);
		
		//操作票信息
		OperationTicketEntity t = this.getService().findById(id);
		if(StringUtil.isEmpty(t.getFileId())){
			t.setFileId("[]");
		}
		model.put("t", t);
		// 部门
//		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		for (int i = 0; i < treeNodeList.size(); i++) {
//			treeNodeList.get(i).setOpen("true");
//		}
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		model.put("unitNameIdTreeList", JsonUtil.toJson(unitList));
		return this.createModelAndView("ticketManage/operationTicket/operationTicketDxDetail",
				model);
	}
	//zzq  20180408
	@RequestMapping("/getEditDx/{id}/{typicalTicketId}")
	public ModelAndView getEditDx(HttpServletRequest request,@PathVariable Long id,@PathVariable String typicalTicketId) {
		Map<String, Object> model = new HashMap<String, Object>();
//		String typicalTicketId=(String)request.getParameter("typicalTicketId");
		model.put("typicalTicketId", typicalTicketId);
		//典型票信息
		TypicalTicketEntity tyEntity = typicalTicketService.findById(Long.parseLong(typicalTicketId));
		model.put("tyEntity", tyEntity);
		// 票类型
		ComboboxVO typicalTicketType = new ComboboxVO();
		for (TypicalTicketTypeEnum typicalTicketTypeEnum : TypicalTicketTypeEnum
				.values()) {
			typicalTicketType.addOption(typicalTicketTypeEnum.getCode(),
					typicalTicketTypeEnum.getName());
		}
		model.put("typicalTicketType",JsonUtil.toJson(typicalTicketType.getOptions()));
		
		//操作票信息
		OperationTicketEntity t = this.getService().findById(id);
		model.put("t", t);

		// 部门
//		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		for (int i = 0; i < treeNodeList.size(); i++) {
//			treeNodeList.get(i).setOpen("true");
//		}
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		model.put("unitNameIdTreeList", JsonUtil.toJson(unitList));
		//下面的是要查询班组，
		List<OrgaAppEntity> orgaApp = orgaAppService.findAll();
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		for(OrgaAppEntity orgaAppEntity : orgaApp){
			comboWorkTicketVO.addOption(orgaAppEntity.getId().toString(), orgaAppEntity.getName());
		}
		//TODO下拉框具体内容根据具体业务定制
		model.put("groupIdCombobox", JsonUtil.toJson(comboWorkTicketVO.getOptions()));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		
		ComboboxVO companyVo = new ComboboxVO();
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
			
			List<Condition> conditions=new ArrayList<Condition>();
			conditions.clear();
			conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,sysUnitEntity.getId()));
			List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
			for (SysUserEntity user : userList) {
				searchuser.addOption(user.getId().toString(), user.getName());
			}
       }
		
//		List<Condition> conditions=new ArrayList<Condition>();
//		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//		SysUserEntity userEntity= RequestContext.get().getUser();
//
//		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
//		List<SysUserEntity> userList=sysUserService.findByCondition(conditions, null);
//		for (SysUserEntity user : userList) {
//			searchuser.addOption(user.getId().toString(), user.getName());
//		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		List<EquipLedgerEntity> templist = new ArrayList<EquipLedgerEntity>();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID",FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
		conditions.add(new Condition("C_TICKET_TYPE",FieldTypeEnum.INT, MatchTypeEnum.EQ,2));
		List<WorkTicketEquipEntity> workTicketEquipList = workTicketEquipService.findByCondition(conditions, null);
		for(WorkTicketEquipEntity entity:workTicketEquipList){
					conditions.clear();
					conditions.add(new Condition("L.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,entity.getEquipCode()));
					List<EquipLedgerEntity>  equipList = equipLedgerService.findByCondition(conditions, null);
					if(!equipList.isEmpty()){
						templist.add(equipList.get(0));
					}
		}
		model.put("userUnitRels", JsonUtil.toJson(templist));
		return this.createModelAndView("ticketManage/operationTicket/operationTicketDxEdit", model);
	}
	
	/**
	 * 检索操作票列表
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping("/searchData")
	public @ResponseBody  ResultListObj searchDate(HttpServletRequest request,@RequestBody Map<String, Object> params){
		
		//查询集控中心和生技处人员
		List<Condition> userConditions=new ArrayList<Condition>();
//		userConditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,138));//集控中心
		userConditions.add(new Condition(" a.C_UNIT_ID = 138 OR a.C_UNIT_ID = 144"));//集控中心和生技处人员
		userConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<SysUserEntity> userEntities =sysUserService.findByCondition(userConditions, null);
		List<String> userNameList = new ArrayList<String>();
		for (SysUserEntity userEntity : userEntities) {
			userNameList.add(userEntity.getLoginName());
		}
		
		ResultListObj resultObj = new ResultListObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<OperationTicketEntity> windEntities = new ArrayList<OperationTicketEntity>();
		Page<OperationTicketEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("t.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,userEntity.getUnitId()));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
//		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.OPERATION.getCode()));
		
		for (String str : userNameList) {
			if (userEntity.getLoginName().equals(str)|| userEntity.getLoginName().equals("admin")||userEntity.getLoginName().equals("super")) {
				conditions.clear();
				conditions = OrmUtil.changeMapToCondition(params);
				//本身是工作票
				conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
//				conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.OPERATION.getCode()));
				windEntities = (List<OperationTicketEntity>)operationTicketService.findByCondition(conditions, page);
			}else {
				windEntities =  (List<OperationTicketEntity>) operationTicketService.findByCondition(conditions, page);
			}
		}
		resultObj.setDraw((Integer)params.get("draw"));
		if (windEntities != null) {
			resultObj.setData(windEntities);
			resultObj.setRecordsTotal(page.getTotal());
		}
		return resultObj;
	}
}