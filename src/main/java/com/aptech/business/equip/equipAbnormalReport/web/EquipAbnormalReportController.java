package com.aptech.business.equip.equipAbnormalReport.web;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.equip.equipAbnormal.domain.EquipAbnormalBranchConditionEnum;
import com.aptech.business.equip.equipAbnormal.domain.EquipAbnormalConstantEnum;
import com.aptech.business.equip.equipAbnormal.domain.EquipAbnormalEnum;
import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelEntity;
import com.aptech.business.equip.equipAbnormalEquipRel.service.EquipAbnormalEquipRelService;
import com.aptech.business.equip.equipAbnormalReport.domain.EquipAbnormalReportBranchConditionEnum;
import com.aptech.business.equip.equipAbnormalReport.domain.EquipAbnormalReportConstantEnum;
import com.aptech.business.equip.equipAbnormalReport.domain.EquipAbnormalReportEntity;
import com.aptech.business.equip.equipAbnormalReport.domain.EquipAbnormalReportEnum;
import com.aptech.business.equip.equipAbnormalReport.service.EquipAbnormalReportService;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.fourcode.service.FourCodeService;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
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
 * 设备异动报告配置控制器
 *
 * @author 
 * @created 2018-09-14 13:48:29
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/equipAbnormalReport")
public class EquipAbnormalReportController extends BaseController<EquipAbnormalReportEntity> {
	
	@Autowired
	private EquipAbnormalReportService equipAbnormalReportService;
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
	private FourCodeService fourCodeService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private EquipAbnormalEquipRelService abnormalEquipRelService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<EquipAbnormalReportEntity> getService() {
		return equipAbnormalReportService;
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
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("userId", sysUserEntity.getId());
		model.put("userLoginName", sysUserEntity.getLoginName());
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_USER_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getId()));
		List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(conditions, null);
		List<Long> userUnitIds = new ArrayList<Long>();
		for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
			userUnitIds.add(userUnitRelEntity.getId());
		}
		conditions.clear();
		conditions.add(new Condition("C_USER_UNIT_REL_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN, userUnitIds.toArray()));
		List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(conditions, null);
		List<String> dutiesIds = new ArrayList<String>();
		for(SysDutiesDetailEntity sysDutiesDetailEntity : sysDutiesDetailList){
			dutiesIds.add(sysDutiesDetailEntity.getDutiesId());
		}
		model.put("answer", false);
		conditions.clear();
		conditions.add(new Condition("C_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, dutiesIds.toArray()));
		List<SysDutiesEntity> sysDutiesList = sysDutiesService.findByCondition(conditions, null);
		for(SysDutiesEntity sysDutiesEntity : sysDutiesList){
			if(sysDutiesEntity.getName().equals("检修专工")||sysDutiesEntity.getName().equals("检修主任")||sysDutiesEntity.getName().equals("集控中心主任")||sysDutiesEntity.getName().equals("运行专工")){
				model.put("answer", true);
			}
		}
		String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView("equip/equipAbnormalReport/equipAbnormalReportList", model);
	}
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value = "/getDataList")
	public @ResponseBody ResultListObj getDataList(HttpServletRequest request, @RequestBody Map<String, Object> params) {
			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
			conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, EquipAbnormalReportConstantEnum.NORMAL.getId()));
			ResultListObj resultObj = new ResultListObj();
			Page<EquipAbnormalReportEntity> page = PageUtil.getPage(params);
			page.setOrders(OrmUtil.changeMapToOrders(params));
			page.addOrder(Sort.desc("id"));
			List<EquipAbnormalReportEntity> equipAbnormalEntities = equipAbnormalReportService.findByCondition(conditions, page);
			resultObj.setDraw((Integer)params.get("draw"));
			if (equipAbnormalEntities != null) {
				resultObj.setData(equipAbnormalEntities);
				resultObj.setRecordsTotal(page.getTotal());
			}
			return resultObj;
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		// 人员
		ComboboxVO sysUser = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			sysUser.addOption(user.getId().toString(), user.getName());
		}
		model.put("userId", JsonUtil.toJson(sysUser.getOptions()));
		//模版报告编码
	    Map<String, Object> params=new HashMap<String, Object> ();
		params.put("date", new Date());
		String equipAbnormalReportCode=fourCodeService.getBusinessCode(EquipAbnormalReportConstantEnum.EQUIPABNORMALREPORTCODE.getName(),params);
        model.put("equipAbnormalReportCode", equipAbnormalReportCode);
        String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView("equip/equipAbnormalReport/equipAbnormalReportAdd", model);
	}
	
	
	/**
	 * 设备异动报告新增
	 */
	@RequestMapping(value = "/addEquipAbnormalReport" , method = RequestMethod.POST)
	public @ResponseBody ResultObj addEquipAbnormal(@RequestBody EquipAbnormalReportEntity equipAbnormalReportEntity, HttpServletRequest request) {
		return equipAbnormalReportService.addEquipAbnormalReport(equipAbnormalReportEntity,request);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 人员
		ComboboxVO sysUser = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			sysUser.addOption(user.getId().toString(), user.getName());
		}
		model.put("userId", JsonUtil.toJson(sysUser.getOptions()));
		EquipAbnormalReportEntity equipAbnormalReportEntity = equipAbnormalReportService.findById(id);
		model.put("equipAbnormalReportEntity", equipAbnormalReportEntity);
		//获取关联设备
		List<EquipLedgerEntity> templist = new ArrayList<EquipLedgerEntity>();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_EQUIP_ABNORMAL_REPORT_ID",FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
		conditions.add(new Condition("C_STATUS",FieldTypeEnum.INT, MatchTypeEnum.EQ,1));
		List<EquipAbnormalEquipRelEntity> abnormalEquipRelEntities = abnormalEquipRelService.findByCondition(conditions, null);
		for(EquipAbnormalEquipRelEntity entity:abnormalEquipRelEntities){
			conditions.clear();
			conditions.add(new Condition("L.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,entity.getEquipCode()));
			List<EquipLedgerEntity>  equipList = equipLedgerService.findByCondition(conditions, null);
			if(!equipList.isEmpty()){
				templist.add(equipList.get(0));
			}
		}
		model.put("userUnitRels", JsonUtil.toJson(templist));
		return this.createModelAndView("equip/equipAbnormalReport/equipAbnormalReportEdit", model);
	}
	
	/**
	 * 设备异动报告修改
	 */
	@RequestMapping(value = "/updateEquipAbnormalReport" , method = RequestMethod.POST)
	public @ResponseBody ResultObj updateEquipAbnormalReport(@RequestBody EquipAbnormalReportEntity equipAbnormalReportEntity, HttpServletRequest request) {
		return equipAbnormalReportService.updateEquipAbnormalReport(equipAbnormalReportEntity,request);
	}
	
	/**
	 * @Description:   批量删除
	 * @author         wangcc 
	 * @Date           2018年9月17日 下午14:04:34 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/deleteMore", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj delSelectInfo(HttpServletRequest request,@RequestBody List<Long> ids) {
		return equipAbnormalReportService.delSelectInfo(ids);
	}
	
	
	/**
	 * @Description:   删除
	 * @author         wangcc 
	 * @Date           2018年9月17日 下午14:04:34 
	 * @throws         Exception
	 */
	@RequestMapping("/deleteOnlyOne/{id}")
	public @ResponseBody ResultObj deleteOnlyOne(HttpServletRequest request, @PathVariable Long id){
		return equipAbnormalReportService.deleteOnlyOne(request,id);
	}
	
	/**
	 * @Description:   查看
	 * @author         wangcc 
	 * @Date           2018年9月17日 下午17:38:34 
	 * @throws         Exception
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetailDay(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		EquipAbnormalReportEntity entity = equipAbnormalReportService.findById(id);
		model.put("equipAbnormalReportEntity", entity);
		return this.createModelAndView("equip/equipAbnormalReport/equipAbnormalReportDetail", model);
	}
	
	/**
	 * @Description:   选择下一节点负责人
	 * @author         wangcc 
	 * @Date           2018年8月17日 下午7:40:31 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/submit/{id}")
	public @ResponseBody ResultObj submit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		return equipAbnormalReportService.submit(id,params);
	}
	
	
	/**
	 * @Description:   流程审批
	 * @author         wangcc 
	 * @Date           2018年9月17日 下午19:26:31 
	 * @throws         Exception
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type",type);
		//审批状态
		ComboboxVO processStatusName = new ComboboxVO();
		for (EquipAbnormalEnum equipAbnormalEnum : EquipAbnormalEnum.values()) {
			processStatusName.addOption(equipAbnormalEnum.getCode(),
					equipAbnormalEnum.getName());
		}
		model.put("processStatus",JsonUtil.toJson(processStatusName.getOptions()));
		//单位名称
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity = RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		//申请人
		model.put("userId", JsonUtil.toJson(searchuser.getOptions()));
		EquipAbnormalReportEntity equipAbnormalReportEntity = equipAbnormalReportService.findById(id);
		model.put("equipAbnormalReportEntities", equipAbnormalReportEntity);
		model.put("id", id);
		//查询各个人的按钮权限 开始
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.EQUIP_ABNORMAL_REPORT_PROCESS_KEY.getName()));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}
		return this.createModelAndView("equip/equipAbnormalReport/equipAbnormalReportApprove", model);
	}
	
	/**
	 * @Description:   设备异动报告提交审核
	 * @author         wangcc 
	 * @Date           2018年9月18日 下午9:56:41 
	 * @throws         Exception
	 */
	@RequestMapping(value="/sureSubmitPerson/{processStatus}")
	public ModelAndView sureSubmitPerson(@PathVariable String processStatus,HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.EQUIP_ABNORMAL_REPORT_PROCESS_KEY.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		if(!defList.isEmpty()){
			String modelId=defList.get(0).getModelId();
			//审批页面点击签发按钮的时候，把下一步的人查询出来
			SysUserEntity userEntity = RequestContext.get().getUser();
			List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
			model.put("userList", userList);
		}
		model.put("processStatus", processStatus);
		return this.createModelAndView("equip/equipAbnormalReport/sureSubmitPerson", model);
	}
	
	/**
	 * @Description: 选择审批人
	 * @author wangcc
	 * @Date 2017年8月18日 下午18:11:30
	 * @throws Exception
	 */
	@RequestMapping("/submitPersonLeader/{taskId}")
	public ModelAndView submitPersonLeader(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,EquipAbnormalReportBranchConditionEnum.AGREE.getCode());
		model.put("userList", userList);
		return this.createModelAndView("equip/equipAbnormalReport/sureSubmitPerson", model);
	}
	
	/**
	 * @Description:   领导审批
	 * @author         wangcc 
	 * @Date           2017年8月22日 上午9:40:42 
	 * @throws         Exception
	 */
	@RequestMapping("/leader/{flag}")
	public @ResponseBody ResultObj leader(@RequestBody EquipAbnormalReportEntity equipAbnormalReportEntity,@PathVariable String flag){
		EquipAbnormalReportEntity entity = equipAbnormalReportService.findById(equipAbnormalReportEntity.getId());
		String executeUserName = equipAbnormalReportEntity.getUserList();
		//如果当前为指定执行人操作
		if(StringUtils.equals(flag, "-1")){
			List<Condition> conditions=new ArrayList<Condition>();
			if(executeUserName.contains(",")){
				conditions.add(new Condition("C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.IN, executeUserName.split(",")));
			}else{
				conditions.add(new Condition("C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, executeUserName));
			}
			List<SysUserEntity> sysUserEntities =  sysUserService.findByCondition(conditions, null);
			String executeUserId = "",executeUserNameString = "";
			for(int i = 0;i< sysUserEntities.size();i++){
				if(i < sysUserEntities.size()-1){
					executeUserId += sysUserEntities.get(i).getId()+",";
					executeUserNameString += sysUserEntities.get(i).getName()+",";
				}else{
					executeUserId += sysUserEntities.get(i).getId();
					executeUserNameString += sysUserEntities.get(i).getName();
				}
			}
			entity.setExecuteUserId(executeUserId);
			entity.setExecuteUserName(executeUserNameString);
		}
		entity.setTaskId(equipAbnormalReportEntity.getTaskId());
		entity.setProcInstId(equipAbnormalReportEntity.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),executeUserName);
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), EquipAbnormalBranchConditionEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), equipAbnormalReportEntity.getApproveIdea());
		if(StringUtils.equals(flag, "1")){
			params.put("processStatus",EquipAbnormalEnum.WAITCHECK.getId());
			params.put("result",EquipAbnormalEnum.WAITCHECK.getName());
		}else if(StringUtils.equals(flag, "2")){
			params.put("processStatus",EquipAbnormalEnum.WAITEXECUTE.getId());
			params.put("result",EquipAbnormalEnum.WAITEXECUTE.getName());
		}else if(StringUtils.equals(flag, "3")){
			params.put("processStatus",EquipAbnormalEnum.WAITLEDGERCHECK.getId());
			params.put("result",EquipAbnormalEnum.WAITLEDGERCHECK.getName());
		}
		return equipAbnormalReportService.approve(entity, params);
	}
	
	/**
	 * @Description:   驳回
	 * @author         wangcc 
	 * @Date           2018年9月19日 下午3:32:42 
	 * @throws         Exception
	 */
	@RequestMapping("/reject/{flag}")
	public @ResponseBody ResultObj reject(@RequestBody EquipAbnormalReportEntity equipAbnormalReportEntity,@PathVariable Long flag){
		EquipAbnormalReportEntity entity = equipAbnormalReportService.findById(equipAbnormalReportEntity.getId());
		entity.setTaskId(equipAbnormalReportEntity.getTaskId());
		entity.setProcInstId(equipAbnormalReportEntity.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),equipAbnormalReportEntity.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), EquipAbnormalReportBranchConditionEnum.DISAGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), equipAbnormalReportEntity.getApproveIdea());
		params.put("processStatus",EquipAbnormalEnum.REJECT.getId());
		params.put("result",EquipAbnormalEnum.REJECT.getName());
		return equipAbnormalReportService.approve(entity, params);
	}
	
	/**
	 * @Description:   再次提交
	 * @author         wangcc 
	 * @Date           2018年9月19日 上午9:40:23 
	 * @throws         Exception
	 */
	@RequestMapping("/submitAgain")
	public @ResponseBody ResultObj submitAgain(@RequestBody EquipAbnormalReportEntity equipAbnormalReportEntity){
		EquipAbnormalReportEntity entity = equipAbnormalReportService.findById(equipAbnormalReportEntity.getId());
		entity.setTaskId(equipAbnormalReportEntity.getTaskId());
		entity.setProcInstId(equipAbnormalReportEntity.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),equipAbnormalReportEntity.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), EquipAbnormalBranchConditionEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), equipAbnormalReportEntity.getApproveIdea());
		params.put("processStatus",EquipAbnormalEnum.WAITCHECK.getId());
		params.put("result",EquipAbnormalEnum.WAITCHECK.getName());
		return equipAbnormalReportService.approve(entity, params);
	}
	
	/**
	 * @Description:   流程结束
	 * @author         wangcc 
	 * @Date           2018年9月19日 上午9:40:03 
	 * @throws         Exception
	 */
	@RequestMapping("/cancel")
	public @ResponseBody ResultObj cancel(@RequestBody EquipAbnormalReportEntity equipAbnormalReportEntity){
		EquipAbnormalReportEntity entity = equipAbnormalReportService.findById(equipAbnormalReportEntity.getId());
		entity.setTaskId(equipAbnormalReportEntity.getTaskId());
		entity.setProcInstId(equipAbnormalReportEntity.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),equipAbnormalReportEntity.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), EquipAbnormalBranchConditionEnum.PROCESSOVER.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("processStatus",EquipAbnormalEnum.RROCESSPEND.getId());
		params.put("result",EquipAbnormalEnum.RROCESSPEND.getName());
		return equipAbnormalReportService.approve(entity, params);
	}
	
	/**
	 * @Description: 选择审批人(再提交专用)
	 * @author wangcc
	 * @Date 2017年8月18日 下午18:11:30
	 * @throws Exception
	 */
	@RequestMapping("/submitPersonLeaderForProposer/{taskId}/{processStatus}")
	public ModelAndView submitPersonLeaderForProposer(HttpServletRequest request,@PathVariable String taskId,@PathVariable String processStatus) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,EquipAbnormalReportBranchConditionEnum.AGREE.getCode());
		model.put("userList", userList);
		model.put("processStatus", processStatus);
		return this.createModelAndView("equip/equipAbnormalReport/sureSubmitPerson", model);
	}
	
	/**
	 * @Description: 选择执行人
	 * @author wangcc
	 * @Date 2018年9月19日 下午15:34:30
	 * @throws Exception
	 */
	@RequestMapping("/selectExecutePerson/{taskId}")
	public ModelAndView selectExecutePerson(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,EquipAbnormalReportBranchConditionEnum.TOEXECUTE.getCode());
		model.put("userList", userList);
		return this.createModelAndView("equip/equipAbnormalReport/sureSubmitPerson", model);
	}
	
	/**
	 * @Description:   不同意执行
	 * @author         wangcc 
	 * @Date           2018年9月11日 下午17:11:00 
	 * @throws         Exception
	 */
	@RequestMapping("/disagree")
	public @ResponseBody ResultObj disagree(@RequestBody EquipAbnormalReportEntity equipAbnormalReportEntity){
		EquipAbnormalReportEntity entity = equipAbnormalReportService.findById(equipAbnormalReportEntity.getId());
		entity.setTaskId(equipAbnormalReportEntity.getTaskId());
		entity.setProcInstId(equipAbnormalReportEntity.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),equipAbnormalReportEntity.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), EquipAbnormalReportBranchConditionEnum.DISAGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), equipAbnormalReportEntity.getApproveIdea());
		params.put("processStatus",EquipAbnormalReportEnum.UNEXECUTE.getId());
		params.put("result",EquipAbnormalReportEnum.EXECUTE.getName());
		return equipAbnormalReportService.approve(entity, params);
	}
	
	/**
	 * @Description:   送执行人
	 * @author         wangcc 
	 * @Date           2017年8月22日 上午9:40:54 
	 * @throws         Exception
	 */
	@RequestMapping("/execute")
	public @ResponseBody ResultObj execute(@RequestBody EquipAbnormalReportEntity equipAbnormalReportEntity){
		EquipAbnormalReportEntity entity = equipAbnormalReportService.findById(equipAbnormalReportEntity.getId());
		entity.setTaskId(equipAbnormalReportEntity.getTaskId());
		entity.setProcInstId(equipAbnormalReportEntity.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),equipAbnormalReportEntity.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), EquipAbnormalReportBranchConditionEnum.TOEXECUTE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), equipAbnormalReportEntity.getApproveIdea());
		params.put("processStatus",EquipAbnormalReportEnum.WAITEXECUTE.getId());
		params.put("result",EquipAbnormalReportEnum.WAITEXECUTE.getName());
		return equipAbnormalReportService.approve(entity, params);
	}
	
	/**
	 * @Description: 提交执行人执行页面
	 * @author wangcc
	 * @Date 2017年8月18日 下午18:11:30
	 * @throws Exception
	 */
	@RequestMapping("/submitExecutePerson/{taskId}")
	public ModelAndView submitExecutePerson(HttpServletRequest request,@PathVariable String taskId) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,EquipAbnormalReportBranchConditionEnum.AGREE.getCode());
		model.put("processStatus", request.getParameter("processStatus"));
		model.put("userList", userList);
		model.put("executePerson", userEntity.getLoginName());
		model.put("executePersonName", userEntity.getName());
		return this.createModelAndView("equip/equipAbnormalReport/executePerson", model);
	}
	
	/**
	 * @Description:   同意执行
	 * @author         wangcc 
	 * @Date           2018年9月20日 下午16:14:14 
	 * @throws         Exception
	 */
	@RequestMapping("/pass")
	public @ResponseBody ResultObj pass(@RequestBody EquipAbnormalReportEntity equipAbnormalReportEntity){
		EquipAbnormalReportEntity entity = equipAbnormalReportService.findById(equipAbnormalReportEntity.getId());
		entity.setTaskId(equipAbnormalReportEntity.getTaskId());
		entity.setProcInstId(equipAbnormalReportEntity.getProcInstId());
		entity.setExecuteUserId(equipAbnormalReportEntity.getExecuteUserId());
		entity.setStartDate(equipAbnormalReportEntity.getStartDate());
		entity.setEndDate(equipAbnormalReportEntity.getEndDate());
		entity.setFinishInfo(equipAbnormalReportEntity.getFinishInfo());
		entity.setExecuteFileId(equipAbnormalReportEntity.getExecuteFileId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),equipAbnormalReportEntity.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), EquipAbnormalReportBranchConditionEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), equipAbnormalReportEntity.getApproveIdea());
		params.put("processStatus",EquipAbnormalReportEnum.EXECUTE.getId());
		params.put("result",EquipAbnormalReportEnum.EXECUTE.getName());
		return equipAbnormalReportService.approve(entity, params);
	}
	
}