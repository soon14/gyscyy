package com.aptech.business.ticketManage.workTicketWindAuto.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.DefectStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkSafeTypeEnum;
import com.aptech.business.component.dictionary.WorkSpResultEnum;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.orgaApp.domain.OrgaAppEntity;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.workControlCard.domain.WorkControlCardEntity;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicket.service.WorkTicketService;
import com.aptech.business.ticketManage.workTicketEquip.domain.WorkTicketEquipEntity;
import com.aptech.business.ticketManage.workTicketEquip.service.WorkTicketEquipService;
import com.aptech.business.ticketManage.workTicketWindAuto.service.WorkTicketWindAutoService;
import com.aptech.business.ticketManage.workTicketWindFlow.domain.WorkTicketWindEntity;
import com.aptech.business.ticketManage.workTicketWindFlow.service.WorkTicketWindService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.role.domain.SysRoleEntity;
import com.aptech.common.system.role.service.SysRoleService;
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
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.IdUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;
import com.mxgraph.util.svg.ParseException;

/**
 * 
 * 风力机械工作票配置控制器
 *
 * @author 
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workTicketWindAuto")
public class WorkTicketWindAutoController extends BaseController<WorkTicketEntity> {
	
	@Autowired
	private WorkTicketWindAutoService workTicketWindAutoService;
	@Autowired
	private WorkTicketWindService workTicketWindService;
	@Autowired
	private WorkTicketService workTicketService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private WorkControlCardService workControlCardService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private WorkSafeService workSafeService;
	@Autowired
	private ControlCardRiskService controlCardRiskService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private WorkTicketEquipService workTicketEquipService;
	@Override
	public IBaseEntityOperation<WorkTicketEntity> getService() {
		return workTicketWindAutoService;
	}
	
	/**
	 *	list页面跳转
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String uuid=request.getParameter("uuid");
//		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		//TODO下拉树具体内容根据具体业务定制
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(unitList));
		//下面的是要查询班组，
		//zzq20180416修改查询班组列表开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsBanzu=new ArrayList<Condition>();
		conditionsBanzu.add(new Condition("a.C_ORGANIZATION", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<OrgaAppEntity> orgaApp = orgaAppService.findByCondition(conditionsBanzu, null);
		//zzq20180416修改查询班组列表结束
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		for(OrgaAppEntity orgaAppEntity : orgaApp){
			comboWorkTicketVO.addOption(orgaAppEntity.getId().toString(), orgaAppEntity.getName());
	    }
		//TODO下拉框具体内容根据具体业务定制
		resultMap.put("groupIdCombobox", JsonUtil.toJson(comboWorkTicketVO.getOptions()));
		
		//状态下拉框
		//编码类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("WORKTICKET_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        resultMap.put("statusTypes", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        //删除垃圾数据
        if(uuid!=null){
        	WorkSafeEntity workSafeEntity=new WorkSafeEntity();
    		workSafeEntity.setUuidCode(uuid);
            workSafeService.deleteByCondition("deleteByUuid", workSafeEntity);
            ControlCardRiskEntity controlCardRiskEntity=new ControlCardRiskEntity();
    		controlCardRiskEntity.setUuidCode(uuid);
    		controlCardRiskService.deleteByCondition("deleteByUuid", controlCardRiskEntity);
        }
        
		//工作负责人下拉列表
		ComboboxVO requestUserVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
        List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers=sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
        	requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        resultMap.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        String userUnitRels = "[]";
        resultMap.put("userUnitRels", userUnitRels);
        return this.createModelAndView("ticketManage/workticketWindAuto/workTicketList", resultMap);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//下面的是要查询班组，
		//zzq20180416修改查询班组列表开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsBanzu=new ArrayList<Condition>();
		conditionsBanzu.add(new Condition("a.C_ORGANIZATION", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<OrgaAppEntity> orgaApp = orgaAppService.findByCondition(conditionsBanzu, null);
		//zzq20180416修改查询班组列表结束
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		for(OrgaAppEntity orgaAppEntity : orgaApp){
			comboWorkTicketVO.addOption(orgaAppEntity.getId().toString(), orgaAppEntity.getName());
	    }
		//TODO下拉框具体内容根据具体业务定制
		resultMap.put("groupIdCombobox", JsonUtil.toJson(comboWorkTicketVO.getOptions()));
		resultMap.put("userEntity", userEntity);
		//作业类别(1)
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("CARD_SORT");
        List<SysDictionaryVO> dicVo=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	dicVo.add(sysDictionaryVO);
        }
        resultMap.put("cardSortList", dicVo);
        //作业类别(2)
        Map<String, SysDictionaryVO> codeDateTypeMapTwo  =  DictionaryUtil.getDictionaries("CARD_SORT_TWO");
        List<SysDictionaryVO> dicVoTwo=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMapTwo.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMapTwo.get(key);
        	dicVoTwo.add(sysDictionaryVO);
        }
        resultMap.put("cardSortListTwo", dicVoTwo);
        //作业类别(3)
        Map<String, SysDictionaryVO> codeDateTypeMapThree  =  DictionaryUtil.getDictionaries("CARD_SORT_THREE");
        List<SysDictionaryVO> dicVoThree=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMapThree.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMapThree.get(key);
        	dicVoThree.add(sysDictionaryVO);
        }
        resultMap.put("cardSortListThree", dicVoThree);
        
        //作业类别(4)
        Map<String, SysDictionaryVO> codeDateTypeMapFour  =  DictionaryUtil.getDictionaries("CARD_SORT_FOUR");
        List<SysDictionaryVO> dicVoFour=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMapFour.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMapFour.get(key);
        	dicVoFour.add(sysDictionaryVO);
        }
        resultMap.put("cardSortListFour", dicVoFour);
        resultMap.put("uuid", IdUtil.creatUUID());
        resultMap.put("safeType", WorkSafeTypeEnum.SAFEFOURTEEN.getId());
        
        //变更人的下拉框
  		List<Condition> conditions=new ArrayList<Condition>();
  		//conditions.add(new Condition("unitId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,workTicketEntity.getUnitNameId() ));
  		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
  		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
  		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		resultMap.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
  		String userUnitRels = "[]";
  		resultMap.put("userUnitRels", userUnitRels);	
		return this.createModelAndView("ticketManage/workticketWindAuto/workTicketAdd", resultMap);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		WorkTicketEntity workTicketEntity=workTicketService.findById(id);
		resultMap.put("workTicketEntity", workTicketEntity);
		List<Condition> conditions=new ArrayList<Condition>();
	    //查询开始
  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
  		WorkTicketWindEntity WorkTicketWindEntity=null;
  		List<WorkTicketWindEntity> workElectricList=workTicketWindService.findByCondition(conditions, null);
        if(!workElectricList.isEmpty()){
        	WorkTicketWindEntity=workElectricList.get(0);
          	resultMap.put("WorkTicketWindEntity", WorkTicketWindEntity);
         }else{
        	WorkTicketWindEntity=new WorkTicketWindEntity();
          	resultMap.put("WorkTicketWindEntity", WorkTicketWindEntity);
        }
        //查询结束
        resultMap.put("safeType", WorkSafeTypeEnum.SAFEFOURTEEN.getId());    
		//变更人的下拉框
	    conditions.clear();
  		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
  		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
  		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		resultMap.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
  		
		//查询危险控制卡开始
		conditions.clear();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		WorkControlCardEntity workControlCardEntity=null;
		List<WorkControlCardEntity> workControlCardList=workControlCardService.findByCondition(conditions, null);
        if(!workControlCardList.isEmpty()){
        	workControlCardEntity=workControlCardList.get(0);
        	resultMap.put("workControlCardEntity", workControlCardEntity);
        }else{
        	workControlCardEntity=new WorkControlCardEntity();
        	resultMap.put("workControlCardEntity", workControlCardEntity);
        }
        //查询危险控制卡结束
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		//下面的是要查询班组，
		//zzq20180416修改查询班组列表开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsBanzu=new ArrayList<Condition>();
		conditionsBanzu.add(new Condition("a.C_ORGANIZATION", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<OrgaAppEntity> orgaApp = orgaAppService.findByCondition(conditionsBanzu, null);
		//zzq20180416修改查询班组列表结束
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		for(OrgaAppEntity orgaAppEntity : orgaApp){
			comboWorkTicketVO.addOption(orgaAppEntity.getId().toString(), orgaAppEntity.getName());
	    }
		//TODO下拉框具体内容根据具体业务定制
		resultMap.put("groupIdCombobox", JsonUtil.toJson(comboWorkTicketVO.getOptions()));
		
		//作业类别(1)
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("CARD_SORT");
        List<SysDictionaryVO> dicVo=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	dicVo.add(sysDictionaryVO);
        }
        resultMap.put("cardSortList", dicVo);
        //作业类别(2)
        Map<String, SysDictionaryVO> codeDateTypeMapTwo  =  DictionaryUtil.getDictionaries("CARD_SORT_TWO");
        List<SysDictionaryVO> dicVoTwo=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMapTwo.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMapTwo.get(key);
        	dicVoTwo.add(sysDictionaryVO);
        }
        resultMap.put("cardSortListTwo", dicVoTwo);
        //作业类别(3)
        Map<String, SysDictionaryVO> codeDateTypeMapThree  =  DictionaryUtil.getDictionaries("CARD_SORT_THREE");
        List<SysDictionaryVO> dicVoThree=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMapThree.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMapThree.get(key);
        	dicVoThree.add(sysDictionaryVO);
        }
        resultMap.put("cardSortListThree", dicVoThree);
        //作业类别(4)
        Map<String, SysDictionaryVO> codeDateTypeMapFour  =  DictionaryUtil.getDictionaries("CARD_SORT_FOUR");
        List<SysDictionaryVO> dicVoFour=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMapFour.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMapFour.get(key);
        	dicVoFour.add(sysDictionaryVO);
        }
        resultMap.put("cardSortListFour", dicVoFour);
		resultMap.put("userEntity", userEntity);
		//获取关联设备
		List<EquipLedgerEntity> templist = new ArrayList<EquipLedgerEntity>();
		conditions.clear();
		conditions.add(new Condition("C_WORKTICKET_ID",FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
		conditions.add(new Condition("C_TICKET_TYPE",FieldTypeEnum.INT, MatchTypeEnum.EQ,1));
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
		resultMap.put("userUnitRels", JsonUtil.toJson(templist));
		return new ModelAndView("ticketManage/workticketWindAuto/workTicketEdit", resultMap);
	}
	
	@RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
	public @ResponseBody ResultObj update(@RequestBody WorkTicketEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindAutoService.update(t,id);
	}
	
	@RequestMapping(value = "/bulkDelete/{ids}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		workTicketWindAutoService.deleteBulk(ids);
		return resultObj;
	}
	
	@RequestMapping(value = "/updateValidate/{id}")
	public @ResponseBody ResultObj updateValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindAutoService.isupdateValidate(id);
	}
	
	@RequestMapping(value = "/deleteValidate/{id}")
	public @ResponseBody ResultObj deleteValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindAutoService.deleteValidate(id);
	}
	
	@RequestMapping("/exportExcel/{conditions}")
	public void expData(HttpServletRequest req,HttpServletResponse res,@PathVariable String conditions) throws UnsupportedEncodingException{
		List<Condition> list = JsonUtil.jsonToObj(conditions,List.class);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("conditions", list);
		List<WorkTicketEntity> dataList=workTicketWindAutoService.findByCondition(param, null);
		DateFormatUtil sf = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		for (WorkTicketEntity workTicketEntity : dataList) {
			if(workTicketEntity.getChangeAllowDate()!=null){
				workTicketEntity.setPlandateStartString(sf.format(workTicketEntity.getChangeAllowDate()));
			}		
	    }
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "风力自控工作票模板.xlsx","风力自控工作票.xlsx", resultMap);
	}
	
	/**
	 *	详细页面
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("workTicketId", id);
		WorkTicketEntity  workTicketEntity=workTicketService.findById(Long.valueOf(id));
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditions.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditions.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_WINDAUTO_PROCESS_KEY.getName()));
		conditions.add(new Condition("task.end_time_ IS NULL"));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditions, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			resultMap.put("todoTaskEntity", todoTaskEntity);
		}else{
			todoTaskEntity=new TodoTaskEntity();
			todoTaskEntity.setId_("noid");
			todoTaskEntity.setProcInstId("noid");
			resultMap.put("todoTaskEntity", todoTaskEntity);
		}
		resultMap.put("workStatus", workTicketEntity.getWorkStatus());
		resultMap.put("rlId", "");
		return this.createModelAndView("ticketManage/workticketWindAuto/workTicketDetail", resultMap);
	}
	/**
	 *	详细页面   第一个tab   
	 */
	@RequestMapping("/workPjxxDetail/{id}")
	public ModelAndView workPjxxDetail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		WorkTicketEntity workTicketEntity=workTicketService.findById(id);
		resultMap.put("workTicketEntity", workTicketEntity);
		
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workTicketEntity.getUnitNameId()));
		workTicketEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workTicketEntity.getGroupId()));
		workTicketEntity.setGroupName(orgaEntity.getName());//
		
		List<Condition> conditions=new ArrayList<Condition>();
        
       //查询开始
        conditions.clear();
  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
  		WorkTicketWindEntity WorkTicketWindEntity=null;
  		List<WorkTicketWindEntity> workElectricList=workTicketWindService.findByCondition(conditions, null);
        if(!workElectricList.isEmpty()){
        	WorkTicketWindEntity=workElectricList.get(0);
          	resultMap.put("WorkTicketWindEntity", WorkTicketWindEntity);
          }else{
        	WorkTicketWindEntity=new WorkTicketWindEntity();
          	resultMap.put("WorkTicketWindEntity", WorkTicketWindEntity);
        }
       //查询一结束
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//下面的是要查询班组，
		//zzq20180416修改查询班组列表开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsBanzu=new ArrayList<Condition>();
		conditionsBanzu.add(new Condition("a.C_ORGANIZATION", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<OrgaAppEntity> orgaApp = orgaAppService.findByCondition(conditionsBanzu, null);
		//zzq20180416修改查询班组列表结束
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		for(OrgaAppEntity orgaAppEntity : orgaApp){
			comboWorkTicketVO.addOption(orgaAppEntity.getId().toString(), orgaAppEntity.getName());
	    }
		//TODO下拉框具体内容根据具体业务定制
		resultMap.put("groupIdCombobox", JsonUtil.toJson(comboWorkTicketVO.getOptions()));
		
		return this.createModelAndView("ticketManage/workticketWindAuto/workTicketDetailPjxx", resultMap);
	}
	/**
	 *	详细页面   第二个tab   
	 */
	@RequestMapping("/workCardDetail/{id}")
	public ModelAndView workCardDetail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		WorkTicketEntity workTicketEntity=workTicketService.findById(id);
		resultMap.put("workTicketEntity", workTicketEntity);
		
		//查询危险控制卡开始
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		WorkControlCardEntity workControlCardEntity=null;
		List<WorkControlCardEntity> workControlCardList=workControlCardService.findByCondition(conditions, null);
        if(!workControlCardList.isEmpty()){
        	workControlCardEntity=workControlCardList.get(0);
        	resultMap.put("workControlCardEntity", workControlCardEntity);
        }else{
        	 workControlCardEntity=new WorkControlCardEntity();
        	resultMap.put("workControlCardEntity", workControlCardEntity);
        }
        //查询危险控制卡结束
		//作业类别(1)
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("CARD_SORT");
        List<SysDictionaryVO> dicVo=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	dicVo.add(sysDictionaryVO);
        }
        resultMap.put("cardSortList", dicVo);
        //作业类别(2)
        Map<String, SysDictionaryVO> codeDateTypeMapTwo  =  DictionaryUtil.getDictionaries("CARD_SORT_TWO");
        List<SysDictionaryVO> dicVoTwo=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMapTwo.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMapTwo.get(key);
        	dicVoTwo.add(sysDictionaryVO);
        }
        resultMap.put("cardSortListTwo", dicVoTwo);
        //作业类别(3)
        Map<String, SysDictionaryVO> codeDateTypeMapThree  =  DictionaryUtil.getDictionaries("CARD_SORT_THREE");
        List<SysDictionaryVO> dicVoThree=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMapThree.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMapThree.get(key);
        	dicVoThree.add(sysDictionaryVO);
        }
        resultMap.put("cardSortListThree", dicVoThree);
        //作业类别(4)
        Map<String, SysDictionaryVO> codeDateTypeMapFour  =  DictionaryUtil.getDictionaries("CARD_SORT_FOUR");
        List<SysDictionaryVO> dicVoFour=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMapFour.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMapFour.get(key);
        	dicVoFour.add(sysDictionaryVO);
        }
        resultMap.put("cardSortListFour", dicVoFour);
		return this.createModelAndView("ticketManage/workticketWindAuto/workTicketDetailCard", resultMap);
	}
	/**
	 *	审批页面
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id,@PathVariable String type) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("type", type);
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_WINDAUTO_PROCESS_KEY.getName()));
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
		WorkTicketEntity workTicketEntity=workTicketService.findById(id);
		resultMap.put("workTicketEntity", workTicketEntity);
		System.out.println(workTicketEntity.getRemarkGuarderName());
		
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workTicketEntity.getUnitNameId()));
		workTicketEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workTicketEntity.getGroupId()));
		workTicketEntity.setGroupName(orgaEntity.getName());//
		
		//查询危险控制卡开始
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		WorkControlCardEntity workControlCardEntity=new WorkControlCardEntity();
		List<WorkControlCardEntity> workControlCardList=workControlCardService.findByCondition(conditions, null);
        if(!workControlCardList.isEmpty()){
        	workControlCardEntity=workControlCardList.get(0);
        	resultMap.put("workControlCardEntity", workControlCardEntity);
        }else{
        	resultMap.put("workControlCardEntity", workControlCardEntity);
        }
        //查询危险控制卡结束
        
        //查询一开始
        conditions.clear();
  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
  		WorkTicketWindEntity WorkTicketWindEntity=new WorkTicketWindEntity();
  		List<WorkTicketWindEntity> workElectricList=workTicketWindService.findByCondition(conditions, null);
        if(!workElectricList.isEmpty()){
        	WorkTicketWindEntity=workElectricList.get(0);
          	resultMap.put("WorkTicketWindEntity", WorkTicketWindEntity);
          }else{
          	resultMap.put("WorkTicketWindEntity", WorkTicketWindEntity);
        }
        //查询一结束
		        
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		
		//作业类别(1)
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("CARD_SORT");
        List<SysDictionaryVO> dicVo=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	dicVo.add(sysDictionaryVO);
        }
        resultMap.put("cardSortList", dicVo);
        //作业类别(2)
        Map<String, SysDictionaryVO> codeDateTypeMapTwo  =  DictionaryUtil.getDictionaries("CARD_SORT_TWO");
        List<SysDictionaryVO> dicVoTwo=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMapTwo.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMapTwo.get(key);
        	dicVoTwo.add(sysDictionaryVO);
        }
        resultMap.put("cardSortListTwo", dicVoTwo);
        //作业类别(3)
        Map<String, SysDictionaryVO> codeDateTypeMapThree  =  DictionaryUtil.getDictionaries("CARD_SORT_THREE");
        List<SysDictionaryVO> dicVoThree=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMapThree.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMapThree.get(key);
        	dicVoThree.add(sysDictionaryVO);
        }
        resultMap.put("cardSortListThree", dicVoThree);
        //作业类别(4)
        Map<String, SysDictionaryVO> codeDateTypeMapFour  =  DictionaryUtil.getDictionaries("CARD_SORT_FOUR");
        List<SysDictionaryVO> dicVoFour=new ArrayList<SysDictionaryVO>();
        for(String key :  codeDateTypeMapFour.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMapFour.get(key);
        	dicVoFour.add(sysDictionaryVO);
        }
        resultMap.put("cardSortListFour", dicVoFour);
        //查询专责负责人的下拉框
        conditions.clear();
  		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
  		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
  		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		resultMap.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
  		resultMap.put("uuid", IdUtil.creatUUID());
		return this.createModelAndView("ticketManage/workticketWindAuto/workTicketApprove", resultMap);
	}
	
	/**
	 * @Description:   提交确认  弹出框      这里要调用第一个流程接口，得到下一步的那些人的id集合
	 */
	@RequestMapping("/sureSubmit")
	public ModelAndView sureSubmit(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		String id=request.getParameter("id");
		resultMap.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORK_TICKET_WINDAUTO_PROCESS_KEY.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		String modelId="";
		if(!defList.isEmpty()){
			modelId=defList.get(0).getModelId();
		}
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
		resultMap.put("userList", userList);
		return new ModelAndView("ticketManage/workticketWindAuto/sureSubmitPerson",resultMap);
	}
	/**
	 * @Description:   提交确认  弹出框      这里要调用第一个流程接口，得到下一步的那些人的id集合
	 */
	@RequestMapping("/beforeSubmit")
	public ModelAndView beforeSubmit(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		String id=request.getParameter("id");
		resultMap.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORK_TICKET_WINDAUTO_PROCESS_KEY.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		String modelId="";
		if(!defList.isEmpty()){
			modelId=defList.get(0).getModelId();
		}
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
		//下一步的签发人不可以是工作负责人
		if(userList!=null){
			if(id==null || "".equals(id)){
				for(int i=0;i<userList.size();i++){
					if(userList.get(i).getId()==userEntity.getId()){
						userList.remove(i);
						break;
					}
				}
			}else{
				WorkTicketEntity workEntity = workTicketService.findById(Long.valueOf(id));
				for(int i=0;i<userList.size();i++){
					if(userList.get(i).getId()==workEntity.getGuarderId()){
						userList.remove(i);
						break;
					}
				}
			}
		}
		
		resultMap.put("userList", userList);
		return new ModelAndView("ticketManage/workticketWindAuto/sureSubmitPerson",resultMap);
	}
	/**
	 * @Description:   审批确认弹出框
	 */
	@RequestMapping("/approveSubmit")
	public ModelAndView approveSubmit(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		String id=request.getParameter("id");
		resultMap.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORK_TICKET_WINDAUTO_PROCESS_KEY.getName()));
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
		return new ModelAndView("ticketManage/workticketWindAuto/approveSubmitPerson",resultMap);
	}

	/**
	 * @Description:   鉴定跳转页面
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
		        
		return new ModelAndView("ticketManage/workticketWindAuto/workInvalid",resultMap);
	}
	/**
	 * @Description:   鉴定
	 */
	@RequestMapping("/saveInvalid")
	public @ResponseBody ResultObj saveInvalid(@RequestBody WorkTicketEntity workTicketEntity, HttpServletRequest request) {
		return workTicketWindAutoService.saveInvalid(workTicketEntity);
	}
	/**
	 * @Description:   验证是否执行了安全措施
	 */
	@RequestMapping(value = "/isExecute/{id}")
	public @ResponseBody ResultObj isExecute(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindAutoService.isExecute(id);
	}
	/**
	 * @Description:   申请试运
	 */
	@RequestMapping(value = "/isExecuteSqsy/{id}")
	public @ResponseBody ResultObj isExecuteSqsy(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindAutoService.isExecuteSqsy(id);
	}
	/**
	 * @Description:   试运恢复
	 */
	@RequestMapping(value = "/isExecuteSyhf/{id}")
	public @ResponseBody ResultObj isExecuteSyhf(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindAutoService.isExecuteSyhf(id);
	}
	
	/**
	 * @Description:   提交时候的验证
	 */
	@RequestMapping(value = "/tijiaoValidate/{id}")
	public @ResponseBody ResultObj tijiaoValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindAutoService.tijiaoValidate(id);
	}
	/**
	 * @Description:   鉴定的时候的验证
	 */
	@RequestMapping(value = "/invalidValidate/{id}")
	public @ResponseBody ResultObj invalidValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindAutoService.invalidValidate(id);
	}
	/**
	 * @Description:   设置标准票时候的验证
	 */
	@RequestMapping(value = "/setValidate/{id}")
	public @ResponseBody ResultObj setValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindAutoService.setValidate(id);
	}

	/**
	 * @Description:   缺陷单的选择带回
	 */
	@RequestMapping("/defectList")
	public ModelAndView defectList(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 状态下拉
		ComboboxVO searchprocessStatus = new ComboboxVO();
		for (DefectStatusEnum defectStatusEnum : DefectStatusEnum.values()) {
			searchprocessStatus.addOption(defectStatusEnum.getCode(),
					defectStatusEnum.getName());
		}
		model.put("searchprocessStatus",
				JsonUtil.toJson(searchprocessStatus.getOptions()));
		// 缺陷类型
		ComboboxVO searchtype = new ComboboxVO();
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil
				.getDictionaries("DEFECT_TYPE");
		for (String key : typeMap.keySet()) {
			SysDictionaryVO sysDictionaryVO = typeMap.get(key);
			searchtype.addOption(sysDictionaryVO.getCode(),
					sysDictionaryVO.getName());

		}
		model.put("searchtype", JsonUtil.toJson(searchtype.getOptions()));
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		// TODO 下拉树具体内容根据具体业务定制
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
		return this.createModelAndView("ticketManage/workticketWindAuto/workDefectList", model);
	}
	
	
	/**
	 *	详细页面的审批试运  zzq 20170621
	 */
	@RequestMapping("/sqsyDetail/{id}")
	public ModelAndView sqsyDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		WorkTicketEntity workTicketEntity=workTicketService.findById(id);
		
		//查询班组和部门
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workTicketEntity.getUnitNameId()));
		workTicketEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workTicketEntity.getGroupId()));
		workTicketEntity.setGroupName(orgaEntity.getName());//
				
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("workticketId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,id ));
		List<WorkTicketWindEntity> electricList=workTicketWindService.findByCondition(conditions, null);
		WorkTicketWindEntity WorkTicketWindEntity =null;
		if(!electricList.isEmpty()){
			WorkTicketWindEntity=electricList.get(0);
		}else{
			WorkTicketWindEntity=new WorkTicketWindEntity();
		}
		model.put("WorkTicketWindEntity", WorkTicketWindEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", WorkTicketWindEntity.getId());
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricDetailSqsy", model);
	}
	/**
	 * @Description:   详细页面的试运恢复
	 */
	@RequestMapping("/syhfDetail/{id}")
	public ModelAndView syhfDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		WorkTicketEntity workTicketEntity=workTicketService.findById(id);
		//查询班组和部门
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workTicketEntity.getUnitNameId()));
		workTicketEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workTicketEntity.getGroupId()));
		workTicketEntity.setGroupName(orgaEntity.getName());//
				
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("workticketId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,id ));
		List<WorkTicketWindEntity> electricList=workTicketWindService.findByCondition(conditions, null);
		WorkTicketWindEntity WorkTicketWindEntity =null;
		if(!electricList.isEmpty()){
			WorkTicketWindEntity=electricList.get(0);
		}else{
			WorkTicketWindEntity=new WorkTicketWindEntity();
		}
		model.put("WorkTicketWindEntity", WorkTicketWindEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", WorkTicketWindEntity.getId());
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricDetailSyhf", model);
	}
	
	/**
	 * @Description:   提交方法
	 */
	@RequestMapping("/submit")
	public @ResponseBody ResultObj submit(HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		try {
			String id=request.getParameter("workId");
			String selectUser=request.getParameter("selectUser");
			workTicketWindAutoService.submit(id,selectUser);
			resultObj.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.setResult("error");
		}
		return resultObj;
	}
	
	/**
	 *	跳转到签发页面
	 */
	@RequestMapping("/getAddQf")
	public ModelAndView getAddQf(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return new ModelAndView("ticketManage/workticketWindAuto/workElectricAddQf", model);
	}
	/**
	 * 流程中 同意分支使用
	 */
	private void agreeWindProcess(HttpServletRequest request,WorkTicketWindEntity workElectricEntity){
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String selectUser=request.getParameter("selectUser");
		if(selectUser!=null && selectUser !=""){
			workElectricEntity.setSelectUser(selectUser);
		}
		SysUserEntity userEntity= RequestContext.get().getUser();
		Map<String, Object> taskVariables=new HashMap<String, Object>();
		taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workElectricEntity.getSelectUser());
		taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
		taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
		taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricEntity.getApproveIdea()==null?"":workElectricEntity.getApproveIdea());//审批意见
		actTaskService.complete(taskId,procInstId,taskVariables);
		workTicketWindAutoService.updateSpnrAgree(workElectricEntity,userEntity);
	}
	/**
	 * 流程中 不同意分支使用
	 */
	private void backWindProcess(HttpServletRequest request,WorkTicketWindEntity workElectricEntity){
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String selectUser=request.getParameter("selectUser");
		if(selectUser!=null && selectUser !=""){
			workElectricEntity.setSelectUser(selectUser);
		}
		SysUserEntity userEntity= RequestContext.get().getUser();
		Map<String, Object> taskVariables=new HashMap<String, Object>();
		taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workElectricEntity.getSelectUser());
		taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
		taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
		taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricEntity.getApproveIdea()==null?"":workElectricEntity.getApproveIdea());//审批意见
		actTaskService.complete(taskId,procInstId,taskVariables);
		workTicketWindAutoService.updateSpnrDisagree(workElectricEntity,userEntity);
	}
	/**
	 *  签发人同意
	 */
	@RequestMapping("/agreeQf")
	public @ResponseBody ResultObj agreeQf(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricEntity.setSpFlag(WorkBtnTypeEnum.QF.getCode());
			agreeWindProcess(request,workElectricEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 *	跳转到收票页面
	 */
	@RequestMapping("/getAddSp")
	public ModelAndView getAddSp(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		
		
		
		
		
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddSp", model);
	}
	
	/**
	 * @Description:   收票人同意
	 */
	@RequestMapping("/agreeSp")
	public @ResponseBody ResultObj agreeSp(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricEntity.setSpFlag(WorkBtnTypeEnum.SP.getCode());
			agreeWindProcess(request,workElectricEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 *	跳转到批准页面
	 */
	@RequestMapping("/getAddPz")
	public ModelAndView getAddPz(HttpServletRequest request){
		String electricId=request.getParameter("electricId");
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		
		WorkTicketWindEntity workWindEntity = workTicketWindService.findById(Long.valueOf(electricId));
		//下一步的许可人 不可以是工作签发人
		if(userList!=null){
			for(int i=0;i<userList.size();i++){
				if(userList.get(i).getId()==workWindEntity.getSignerId()){
					userList.remove(i);
					break;
				}
			}
		}
		
		WorkTicketEntity workEntity = workTicketService.findById(workWindEntity.getWorkticketId());
		//下一步的许可人不可以是 工作负责人
		if(userList!=null){
			for(int i=0;i<userList.size();i++){
				if(userList.get(i).getId()==workEntity.getGuarderId()){
					userList.remove(i);
					break;
				}
			}
		}
		
		
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddPz", model);
	}
	
	/**
	 * @Description:   值长同意
	 */
	@RequestMapping("/agreePz")
	public @ResponseBody ResultObj agreePz(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricEntity.setSpFlag(WorkBtnTypeEnum.ZZQZ.getCode());
			agreeWindProcess(request,workElectricEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 *	跳转到许可页面
	 */
	@RequestMapping("/getAddXk")
	public ModelAndView getAddXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
		model.put("workTicketEntity", workTicketEntity);
		model.put("workElectricEntity", workElectricEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddXk", model);
	}
	
	/**
	 * @Description:   许可人同意
	 */
	@RequestMapping("/agreeXk")
	public @ResponseBody ResultObj agreeXk(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		try {
			Long picId=workElectricEntity.getAllowPicPersonId();//工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或取这个工作负责人的实体
			workElectricEntity.setSpFlag(WorkBtnTypeEnum.XK.getCode());
			workElectricEntity.setSelectUser(sysUserEntity.getLoginName());
			agreeWindProcess(request,workElectricEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   更新备注
	 */
	@RequestMapping(value = "/updateBz/{id}")
	public @ResponseBody ResultObj updateBz(@RequestBody WorkTicketWindEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindService.update(t,id);
	}
	
	/**
	 *	跳转到工作负责人变更页面
	 */
	@RequestMapping("/getAddGzfzrbd")
	public ModelAndView getAddGzfzrbd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		SysUserEntity userEntity= RequestContext.get().getUser();

		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,WorkSpResultEnum.WORKPICCHANGE.getCode());
		
		
		//下一步的审核人 不可以是原工作负责人
		if(userList!=null){
			for(int i=0;i<userList.size();i++){
				if(userList.get(i).getId()==userEntity.getId()){
					userList.remove(i);
					break;
				}
			}
		}
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		model.put("workElectricEntity", workElectricEntity);
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
		
		//先查询角色
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"guarder"));
		List<SysRoleEntity> roleList=sysRoleService.findByCondition(conditions, null);
		SysRoleEntity sysRoleEntity=null;
		String roleId="";
		if(!roleList.isEmpty()){
			sysRoleEntity=roleList.get(0);
			roleId=sysRoleEntity.getId().toString();
		}
		//变更人的下拉框
		conditions.clear();
		conditions.add(new Condition("unitId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,workTicketEntity.getUnitNameId() ));
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("FIND_IN_SET("+roleId+",a.C_ROLE_IDS)"));
		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		//变更后的工作负责人  不可以是原工作负责人
		if(userListBox!=null){
			for(int i=0;i<userListBox.size();i++){
				if(userListBox.get(i).getId()==userEntity.getId()){
					userListBox.remove(i);
					break;
				}
			}
		}
		
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		for(SysUserEntity sysUserEntity : userListBox){
			comboWorkTicketVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	    }
		model.put("userListBox", JsonUtil.toJson(comboWorkTicketVO.getOptions()));
		
		
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddGzfzrbd", model);
	}
	
	/**
	 * @Description:   工作负责人变更的确定
	 */
	@RequestMapping("/sureGzfzrbg")
	public @ResponseBody ResultObj sureGzfzrbg(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBG.getCode());
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),request.getParameter("selectUser"));
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.WORKPICCHANGE.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricEntity.getApproveIdea()==null?"":workElectricEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(request.getParameter("taskId"),request.getParameter("procInstId"), taskVariables);
			workTicketWindAutoService.updateSpnrAgree(workElectricEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 *	跳转到工作负责人变更页面  签发
	 */
	@RequestMapping("/getAddGzfzrbgQf")
	public ModelAndView getAddGzfzrbgQf(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		SysUserEntity userEntity= RequestContext.get().getUser();

		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workWindEntity = workTicketWindService.findById(Long.valueOf(electricId));
		//下一步许可人不可以是签发人
		if(userList!=null){
			for(int i=0;i<userList.size();i++){
				if(userList.get(i).getId()==userEntity.getId()){
					userList.remove(i);
					break;
				}
			}
		}
		//下一步许可人不可以是原工作负责人
		if(userList!=null){
			for(int i=0;i<userList.size();i++){
				if(userList.get(i).getId()==workWindEntity.getChangeOldPicId()){
					userList.remove(i);
					break;
				}
			}
		}
		
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		model.put("workElectricEntity", workElectricEntity);
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("qfrDate", new Date());
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddGzfzrbdQf", model);
	}
	
	/**
	 * @Description:   工作负责人变更   签发  同意
	 */
	@RequestMapping("/agreeGzfzrbgQf")
	public @ResponseBody ResultObj agreeGzfzrbgQf(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGQF.getCode());
			agreeWindProcess(request,workElectricEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 *	跳转到工作负责人变更页面 许可
	 */
	@RequestMapping("/getAddGzfzrbgXk")
	public ModelAndView getAddGzfzrbgXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		model.put("workElectricEntity", workElectricEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("taskId", taskId);
		model.put("xkrDate", new Date());
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddGzfzrbdXk", model);
	}
	
	/**
	 * @Description:   工作负责人变更   许可  同意
	 */
	@RequestMapping("/agreeGzfzrbgXk")
	public @ResponseBody ResultObj agreeGzfzrbgXk(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGXK.getCode());
		Long newPicId=workElectricEntity.getChangeNewPicId();
		SysUserEntity  sysUserEntity=sysUserService.findById(newPicId);//或许这个工作负责人的实体
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		SysUserEntity userEntity= RequestContext.get().getUser();

		Map<String, Object> taskVariables=new HashMap<String, Object>();
		taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntity);
		taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
		taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
		taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
		taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricEntity.getApproveIdea()==null?"":workElectricEntity.getApproveIdea());//审批意见
		actTaskService.complete(taskId,procInstId, taskVariables);
		workTicketWindAutoService.updateSpnrAgree(workElectricEntity,userEntity);
		
		//		agreeWindProcess(request,workElectricEntity);
		return resultObj;
	}
	
	/**
	 *	跳转到工作人员变动页面
	 */
	@RequestMapping("/getAddGzrybd")
	public ModelAndView getAddGzrybd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
		model.put("workTicketEntity", workTicketEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddGzrybd", model);
	}
	/**
	 * @Description:   工作人员变动的  确定
	 * @author         zhangzq 
	 * @Date           201706016
	 * @throws         Exception
	 */
	@RequestMapping("/sureGzrybd")
	public @ResponseBody ResultObj sureGzrybd(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.GZRYBD.getCode());
		SysUserEntity userEntity= RequestContext.get().getUser();
		workTicketWindAutoService.updateSpnrAgree(workElectricEntity,userEntity);
		return resultObj;
	}
	/**
	 *	跳转到延期页面
	 */
	@RequestMapping("/getAddYq")
	public ModelAndView getAddYq(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.EXTENSION.getCode());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
		model.put("workElectricEntity", workElectricEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddYq", model);
	}
	/**
	 * @Description:   延期确定
	 */
	@RequestMapping("/sureYq")
	public @ResponseBody ResultObj sureYq(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.YQ.getCode());
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String selectUser=request.getParameter("selectUser");
		SysUserEntity userEntity= RequestContext.get().getUser();
		Map<String, Object> taskVariables=new HashMap<String, Object>();
		taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
		taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.EXTENSION.getCode());
		
		taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
		taskVariables.put(ExamMarkEnum.COMMENT.getCode(),"");//审批意见
		
		actTaskService.complete(taskId,procInstId, taskVariables);
		workTicketWindAutoService.updateSpnrAgree(workElectricEntity,userEntity);
		return resultObj;
	}
	/**
	 *	跳转到延期  值长签字
	 */
	@RequestMapping("/getAddYqZzqz")
	public ModelAndView getAddYqZzqz(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
		model.put("workElectricEntity", workElectricEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workElectricEntity.getChangeNewPicName()!=null&&!workElectricEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workElectricEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddYqZzqz", model);
	}
	/**
	 * @Description:   延期值长签字  同意
	 */
	@RequestMapping("/agreeYqZzqz")
	public @ResponseBody ResultObj agreeYqZzqz(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.YQZZQZ.getCode());
		agreeWindProcess(request,workElectricEntity);
		return resultObj;
	}
	
	/**
	 *	跳转到延期  许可
	 */
	@RequestMapping("/getAddYqXk")
	public ModelAndView getAddYqXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
		model.put("workElectricEntity", workElectricEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddYqXk", model);
	}
	/**
	 * @Description:   延期 许可人的  同意
	 */
	@RequestMapping("/agreeYqXk")
	public @ResponseBody ResultObj agreeYqXk(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.YQXK.getCode());
		agreeWindProcess(request,workElectricEntity);
		return resultObj;
	}
	
	
	/**
	 *	跳转到延期工作负责人
	 */
	@RequestMapping("/getAddYqFzr")
	public ModelAndView getAddYqFzr(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
		model.put("workElectricEntity", workElectricEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workElectricEntity.getChangeNewPicName()!=null&&!workElectricEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workElectricEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddYqFzr", model);
	}
	
	/**
	 * @Description:   延期 负责人的  同意
	 */
	@RequestMapping("/agreeYqFzr")
	public @ResponseBody ResultObj agreeYqFzr(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.YQFZR.getCode());
		
		String workPersonId=request.getParameter("workPersonId");
		SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
		workElectricEntity.setSelectUser(sysUserEntity.getLoginName());
		agreeWindProcess(request,workElectricEntity);
		return resultObj;
	}

	/**
	 *	跳转到申请试运页面
	 */
	@RequestMapping("/getEditSqsy")
	public ModelAndView getEditSqsy(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		
		String electricId=request.getParameter("electricId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.APPLYRUN.getCode());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("taskIdSqsy", taskId);
		model.put("procInstIdSqsy", procInstId);
		model.put("procDefIdSqsy", procDefId);
		
		String workId=request.getParameter("workId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(Long.valueOf(workId));
		model.put("workElectricEntity", workElectricEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddSqsy", model);
	}
	/**
	 * @Description:   申请试运确定
	 */
	@RequestMapping("/sureSqsy")
	public @ResponseBody ResultObj sureSqsy(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.SQSY.getCode());
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		SysUserEntity userEntity= RequestContext.get().getUser();
		Map<String, Object> taskVariables=new HashMap<String, Object>();
		taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workElectricEntity.getSelectUser());
		taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.APPLYRUN.getCode());
		
		taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
		taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricEntity.getApproveIdea()==null?"":workElectricEntity.getApproveIdea());//审批意见
		
		actTaskService.complete(taskId,procInstId, taskVariables);
		workTicketWindAutoService.updateSpnrAgree(workElectricEntity,userEntity);
		return resultObj;
	}
	
	/**
	 *	跳转到申请试运的  许可人界面
	 */
	@RequestMapping("/getAddSqsyXk")
	public ModelAndView getAddSqsyXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("taskIdSqsy", taskId);
		model.put("procInstIdSqsy", procInstId);
		model.put("procDefId", procDefId);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.APPLYRUN.getCode());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
		model.put("workElectricEntity", workElectricEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("dateSqsyTime", new Date());
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddSqsyXk", model);
	}
	
	/**
	 * @Description:   申请试运  许可人的  同意
	 */
	@RequestMapping("/agreeSqsyXk")
	public @ResponseBody ResultObj agreeSqsyXk(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.SQSYXK.getCode());
		agreeWindProcess(request,workElectricEntity);
		return resultObj;
	}
	

	/**
	 *	跳转到申请试运的  值长签字  界面
	 */
	@RequestMapping("/getAddSqsyZzqz")
	public ModelAndView getAddSqsyZzqz(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("taskIdSqsy", taskId);
		model.put("procInstIdSqsy", procInstId);
		model.put("procDefId", procDefId);
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
		model.put("workElectricEntity", workElectricEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workElectricEntity.getChangeNewPicName()!=null&&!workElectricEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workElectricEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddSqsyZzqz", model);
	}
	
	/**
	 * @Description:   申请试运 值长签字的  同意
	 */
	@RequestMapping("/agreeSqsyZzqz")
	public @ResponseBody ResultObj agreeSqsyZzqz(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.SQSYZZQZ.getCode());
		String workPersonId=request.getParameter("workPersonId");
		SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
		workElectricEntity.setSelectUser(sysUserEntity.getLoginName());
		agreeWindProcess(request,workElectricEntity);
		return resultObj;
	}
	
	/**
	 *	跳转到试运恢复页面
	 */
	@RequestMapping("/getEditSyhf")
	public ModelAndView getEditSyhf(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("taskIdSyhf", taskId);
		model.put("procInstIdSyhf", procInstId);
		model.put("procDefId", procDefId);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.STOPRESTORE.getCode());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		
		String workId=request.getParameter("workId");
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(Long.valueOf(workId));
		model.put("workElectricEntity", workElectricEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("syhfDate", new Date());
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddSyhf", model);
	}
	
	/**
	 * @Description:   试运恢复确定
	 */
	@RequestMapping("/sureSyhf")
	public @ResponseBody ResultObj sureSyhf(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.SYHF.getCode());
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		SysUserEntity userEntity= RequestContext.get().getUser();
		Map<String, Object> taskVariables=new HashMap<String, Object>();
		taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workElectricEntity.getSelectUser());
		taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.STOPRESTORE.getCode());
		
		taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
		taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricEntity.getApproveIdea()==null?"":workElectricEntity.getApproveIdea());//审批意见
		
		actTaskService.complete(taskId,procInstId,taskVariables);
		workTicketWindAutoService.updateSpnrAgree(workElectricEntity,userEntity);
		return resultObj;
	}

	/**
	 *	跳转到试运恢复页面     许可
	 */
	@RequestMapping("/getEditSyhfXk")
	public ModelAndView getEditSyhfXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("taskIdSyhf", taskId);
		model.put("procInstIdSyhf", procInstId);
		model.put("procDefId", procDefId);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.STOPRESTORE.getCode());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		
		String workId=request.getParameter("workId");
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(Long.valueOf(workId));
		model.put("workElectricEntity", workElectricEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("dateSyhfTime", new Date());
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddSyhfXk", model);
	}
	
	/**
	 * @Description:   试运恢复  许可人的  同意
	 */
	@RequestMapping("/agreeSyhfXk")
	public @ResponseBody ResultObj agreeSyhfXk(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.SYHFXK.getCode());
		agreeWindProcess(request,workElectricEntity);
		return resultObj;
	}
	
	/**
	 *	跳转到试运恢复页面   值长签字
	 */
	@RequestMapping("/getEditSyhfZzqz")
	public ModelAndView getEditSyhfZzqz(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("taskIdSyhf", taskId);
		model.put("procInstIdSyhf", procInstId);
		model.put("procDefId", procDefId);
		String workId=request.getParameter("workId");
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(Long.valueOf(workId));
		model.put("workElectricEntity", workElectricEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workElectricEntity.getChangeNewPicName()!=null&&!workElectricEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workElectricEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddSyhfZzqz", model);
	}
	
	/**
	 * @Description:   试运恢复  值长签字的  同意
	 */
	@RequestMapping("/agreeSyhfZzqz")
	public @ResponseBody ResultObj agreeSyhfZzqz(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.SYHFZZQZ.getCode());
		String workPersonId=request.getParameter("workPersonId");
		SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
		workElectricEntity.setSelectUser(sysUserEntity.getLoginName());
		agreeWindProcess(request,workElectricEntity);
		return resultObj;
	}
	/**
	 *	跳转到终结页面
	 */
	@RequestMapping("/getAddZj")
	public ModelAndView getAddZj(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String workId=request.getParameter("workId");
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("workIdZj", workId);
		model.put("taskIdZj", taskId);
		model.put("procInstIdZj", procInstId);
		model.put("procDefIdZj", procDefId);
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.THEEND.getCode());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
		model.put("workElectricEntity", workElectricEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddZj", model);
	}
	/**
	 *	跳转到终结   许可人  界面
	 */
	@RequestMapping("/getAddZjXk")
	public ModelAndView getAddZjXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String workId=request.getParameter("workId");
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("workIdZjXk", workId);
		model.put("taskIdZjXk", taskId);
		model.put("procInstIdZjXk", procInstId);
		model.put("procDefIdZjXk", procDefId);
		String electricId=request.getParameter("electricId");
		WorkTicketWindEntity workElectricEntity = workTicketWindService.findById(Long.valueOf(electricId));
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
		model.put("workElectricEntity", workElectricEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workElectricEntity.getChangeNewPicName()!=null&&!workElectricEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workElectricEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/workticketWindAuto/workElectricAddZjXk", model);
	}
	
	/**
	 * @Description:   终结确定
	 */
	@RequestMapping("/sureZj")
	public @ResponseBody ResultObj sureZj(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.ZJ.getCode());
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		SysUserEntity userEntity= RequestContext.get().getUser();
		Map<String, Object> taskVariables=new HashMap<String, Object>();
		taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workElectricEntity.getSelectUser());
		taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.THEEND.getCode());
		
		taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
		taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricEntity.getApproveIdea()==null?"":workElectricEntity.getApproveIdea());//审批意见
		
		actTaskService.complete(taskId,procInstId, taskVariables);
		workTicketWindService.updateSpnrAgree(workElectricEntity,userEntity);
		return resultObj;
	}
	
	
	/**
	 * @Description:   终结  许可人的  同意
	 */
	@RequestMapping("/agreeZjXk")
	public @ResponseBody ResultObj agreeZjXk(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.ZJXK.getCode());
		agreeWindProcess(request, workElectricEntity);
		return resultObj;
	}
	/**
	 * @Description:   终结  许可人的      不同意
	 */
	@RequestMapping("/disagreeZjXk")
	public @ResponseBody ResultObj disagreeZjXk(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.ZJXK.getCode());
		String workPersonId=request.getParameter("workPersonId"); 
		SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
		workElectricEntity.setSelectUser(sysUserEntity.getLoginName());
		backWindProcess(request,workElectricEntity);
		return resultObj;
	}
	
	/**
	 * @Description:   签发人不同意
	 */
	@RequestMapping("/disAgreeQf")
	public @ResponseBody ResultObj disAgreeQf(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.QF.getCode());
		//查询工作负责人的loginName
		WorkTicketWindEntity workElectric = workTicketWindService.findById(workElectricEntity.getId());
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectric.getWorkticketId());
		SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
		//工作负责人的loginName
		workElectricEntity.setSelectUser(fzrEntity.getLoginName());
		backWindProcess(request,workElectricEntity);
		return resultObj;
	}
	/**
	 * @Description:   收票人不同意
	 */
	@RequestMapping("/disAgreeSp")
	public @ResponseBody ResultObj disAgreeSp(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.SP.getCode());
		//查询工作负责人的loginName
		WorkTicketWindEntity workElectric = workTicketWindService.findById(workElectricEntity.getId());
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectric.getWorkticketId());
		SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
		//查询工作负责人的loginName
		workElectricEntity.setSelectUser(fzrEntity.getLoginName());
		backWindProcess(request,workElectricEntity);
		return resultObj;
	}
	
	/**
	 * @Description:   值长不同意
	 */
	@RequestMapping("/disAgreePz")
	public @ResponseBody ResultObj disAgreePz(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.ZZQZ.getCode());
		//查询工作负责人的loginName
		WorkTicketWindEntity workElectric = workTicketWindService.findById(workElectricEntity.getId());
		WorkTicketEntity workTicketEntity=workTicketService.findById(workElectric.getWorkticketId());
		SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
		//查询工作负责人的loginName
		workElectricEntity.setSelectUser(fzrEntity.getLoginName());
		backWindProcess(request,workElectricEntity);
		return resultObj;
	}
	/**
	 * @Description:   工作负责人变更   签发  不同意
	 */
	@RequestMapping("/disAgreeGzfzrbgQf")
	public @ResponseBody ResultObj disAgreeGzfzrbgQf(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGQF.getCode());
		Long picId=workElectricEntity.getChangeOldPicId();//工作负责人的id
		SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
		workElectricEntity.setSelectUser(sysUserEntity.getLoginName());
		backWindProcess(request,workElectricEntity);
		return resultObj;
	}
	/**
	 * @Description:   工作负责人变更   许可  不同意
	 */
	@RequestMapping("/disAgreeGzfzrbgXk")
	public @ResponseBody ResultObj disAgreeGzfzrbgXk(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGXK.getCode());
		Long picId=workElectricEntity.getChangeOldPicId();//工作负责人的id
		SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
		workElectricEntity.setSelectUser(sysUserEntity.getLoginName());
		backWindProcess(request,workElectricEntity);
		return resultObj;
	}
	/**
	 * @Description:   工作人员变动   许可  不同意
	 */
	@RequestMapping("/disagreeGzrybdFzr")
	public @ResponseBody ResultObj disagreeGzrybdFzr(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.GZRYBDFZR.getCode());
		Long picId=workElectricEntity.getWorkPersonPicId();
		SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
		workElectricEntity.setSelectUser(sysUserEntity.getLoginName());
		backWindProcess(request,workElectricEntity);
		return resultObj;
	}
	/**
	 * @Description:   延期值长签字    不同意
	 */
	@RequestMapping("/disAgreeYqZzqz")
	public @ResponseBody ResultObj disAgreeYqZzqz(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.YQZZQZ.getCode());
		String workPersonId=request.getParameter("workPersonId"); 
		SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
		workElectricEntity.setSelectUser(sysUserEntity.getLoginName());
		backWindProcess(request,workElectricEntity);
		return resultObj;
	}
	/**
	 * @Description:   再提交
	 */
	@RequestMapping("/againSubmit")
	public @ResponseBody ResultObj againSubmit(HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		WorkTicketWindEntity workElectricEntity=new WorkTicketWindEntity();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.ZTJ.getCode());
		String workId=request.getParameter("workId");
		String selectUser=request.getParameter("selectUser");
		workElectricEntity.setId(Long.valueOf(workId));
		workElectricEntity.setSelectUser(selectUser);
		agreeWindProcess(request,workElectricEntity);
		return resultObj;
	}
	/**
	 * @Description:   废票
	 */
	@RequestMapping("/disAgreeFp")
	public @ResponseBody ResultObj disAgreeFp(HttpServletRequest request,@RequestBody WorkTicketWindEntity workElectricEntity){
		ResultObj resultObj = new ResultObj();
		workElectricEntity.setSpFlag(WorkBtnTypeEnum.FP.getCode());
		String selectUser=request.getParameter("selectUser");
		workElectricEntity.setSelectUser(selectUser);
		backWindProcess(request,workElectricEntity);
		return resultObj;
	}
}