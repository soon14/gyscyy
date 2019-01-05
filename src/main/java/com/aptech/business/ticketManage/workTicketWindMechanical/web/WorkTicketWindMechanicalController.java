package com.aptech.business.ticketManage.workTicketWindMechanical.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.DefectStatusEnum;
import com.aptech.business.component.dictionary.Istypicaleum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.WorkSafeTypeEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.orgaApp.domain.OrgaAppEntity;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
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
import com.aptech.business.ticketManage.workTicketTwo.domain.WorkTicketTwoEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEnum;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
import com.aptech.business.ticketManage.workTicketWindFlow.domain.WorkTicketWindEntity;
import com.aptech.business.ticketManage.workTicketWindFlow.service.WorkTicketWindService;
import com.aptech.business.ticketManage.workTicketWindMechanical.service.WorkTicketWindMechanicalService;
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
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.processNodeAuth.domain.ProcessNodeAuthEntity;
import com.aptech.common.workflow.processNodeAuth.service.ProcessNodeAuthService;
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
import com.aptech.framework.util.IdUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 风力机械工作票配置控制器
 *
 * @author zzq
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workTicketWindMechanical")
public class WorkTicketWindMechanicalController extends BaseController<WorkTicketEntity> {
	
	@Autowired
	private WorkTicketWindMechanicalService workTicketWindMechanicalService;
	
	@Autowired
	private WorkTicketWindService workTicketWindService;
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
	private WorkSafeService workSafeService;
	@Autowired
	private ControlCardRiskService controlCardRiskService;
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private WorkTicketEquipService workTicketEquipService;
	@Autowired
	private WorkTicketService workTicketService;
	@Override
	public IBaseEntityOperation<WorkTicketEntity> getService() {
		return workTicketWindMechanicalService;
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
        
        //返回登录用户信息
        resultMap.put("userEntity", userEntity); 
        //删除垃圾数据
        if(uuid!=null){
        	WorkSafeEntity workSafeEntity=new WorkSafeEntity();
    		workSafeEntity.setUuidCode(uuid);
            workSafeService.deleteByCondition("deleteByUuid", workSafeEntity);
            ControlCardRiskEntity controlCardRiskEntity=new ControlCardRiskEntity();
    		controlCardRiskEntity.setUuidCode(uuid);
    		controlCardRiskService.deleteByCondition("deleteByUuid", controlCardRiskEntity);
        }
        
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));

		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList=sysUserService.findByCondition(conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		resultMap.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		String userUnitRels = "[]";
		resultMap.put("userUnitRels", userUnitRels);
        return this.createModelAndView("ticketManage/workticketWindMechanical/workTicketList", resultMap);
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
  	    //添加的时候要给安全措施写一条无的记录，zzq修改开始 20180315
        String uuid=IdUtil.creatUUID();
        resultMap.put("uuid", uuid);
        //添加的时候要给安全措施写一条无的记录，zzq修改结束 20180315
        //添加的时候要给安全措施写一条无的记录，zzq修改开始 20180315
  		WorkSafeEntity workSafeEntity=null;
  		workSafeEntity=new WorkSafeEntity();
  		workSafeEntity.setSignerContent("无");
  		workSafeEntity.setSafeType(Long.valueOf(WorkSafeTypeEnum.SAFEONE.getCode()));
  		workSafeEntity.setOrderSeq(Long.valueOf(1));
  		workSafeEntity.setUuidCode(uuid);
  		workSafeService.addEntity(workSafeEntity);
  		workSafeEntity=new WorkSafeEntity();
  		workSafeEntity.setSignerContent("无");
  		workSafeEntity.setSafeType(Long.valueOf(WorkSafeTypeEnum.SAFETWO.getCode()));
  		workSafeEntity.setOrderSeq(Long.valueOf(1));
  		workSafeEntity.setUuidCode(uuid);
  		workSafeService.addEntity(workSafeEntity);
  		workSafeEntity=new WorkSafeEntity();
  		workSafeEntity.setSignerContent("无");
  		workSafeEntity.setSafeType(Long.valueOf(WorkSafeTypeEnum.SAFETHREE.getCode()));
  		workSafeEntity.setOrderSeq(Long.valueOf(1));
  		workSafeEntity.setUuidCode(uuid);
  		workSafeService.addEntity(workSafeEntity);
  		workSafeEntity=new WorkSafeEntity();
  		workSafeEntity.setSignerContent("无");
  		workSafeEntity.setSafeType(Long.valueOf(WorkSafeTypeEnum.SAFEFOUR.getCode()));
  		workSafeEntity.setOrderSeq(Long.valueOf(1));
  		workSafeEntity.setUuidCode(uuid);
  		workSafeService.addEntity(workSafeEntity);
  		//添加的时候要给安全措施写一条无的记录，zzq修改结束 20180315
  		String userUnitRels = "[]";
  		resultMap.put("userUnitRels", userUnitRels);
		return this.createModelAndView("ticketManage/workticketWindMechanical/workTicketAdd", resultMap);
	}
	
	
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(id);
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
        System.out.println("WorkTicketWindEntity.getRunAllowName()");
        System.out.println(WorkTicketWindEntity.getRunAllowName());
        System.out.println("WorkTicketWindEntity.getStopAllowName()");
        System.out.println(WorkTicketWindEntity.getStopAllowName());
	        
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
		return new ModelAndView("ticketManage/workticketWindMechanical/workTicketEdit", resultMap);
	}
	
	/**
	 *	详细页面
	 * @throws ParseException 
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("workTicketId", id);
		WorkTicketEntity  workTicketEntity=workTicketWindMechanicalService.findById(Long.valueOf(id));
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditions.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditions.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_WINDMECHANICAL_PROCESS_KEY.getName()));
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
		return this.createModelAndView("ticketManage/workticketWindMechanical/workTicketDetail", resultMap);
	}
	/**
	 *	详细页面   第一个tab   
	 * @throws ParseException 
	 */
	@RequestMapping("/workPjxxDetail/{id}")
	public ModelAndView workPjxxDetail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(id);
		if(StringUtil.isEmpty(workTicketEntity.getChangeAllowIdea())){
			workTicketEntity.setChangeAllowIdea("[]");
		}
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
		
		return this.createModelAndView("ticketManage/workticketWindMechanical/workTicketDetailPjxx", resultMap);
	}
	/**
	 *	详细页面   第二个tab   
	 * @throws ParseException 
	 */
	@RequestMapping("/workCardDetail/{id}")
	public ModelAndView workCardDetail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(id);
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
		return this.createModelAndView("ticketManage/workticketWindMechanical/workTicketDetailCard", resultMap);
	}
	/**
	 *	审批页面
	 * @throws ParseException 
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
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_WINDMECHANICAL_PROCESS_KEY.getName()));
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
		WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(id);
		resultMap.put("workTicketEntity", workTicketEntity);
		
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
		return this.createModelAndView("ticketManage/workticketWindMechanical/workTicketApprove", resultMap);
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
		String id=request.getParameter("id");
		resultMap.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORK_TICKET_WINDMECHANICAL_PROCESS_KEY.getName()));
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
		//获取三种人记录
		conditions.clear();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.SIGNATURE.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
		//移除签发人列表中的工作负责人
		Iterator<SysUserEntity> iterator = userList.iterator();
		while (iterator.hasNext()) {
			SysUserEntity sysUserEntity = iterator.next();
            for(WorkTicketUserRelEntity workTicketUserRelEntity:list){
            	if(sysUserEntity.getId().equals(workTicketUserRelEntity.getUserId())){
            		iterator.remove();
            	}
            }
       }
		resultMap.put("userList", userList);
		
		
		return new ModelAndView("ticketManage/workticketWindMechanical/sureSubmitPerson",resultMap);
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
				WorkTicketEntity workEntity = workTicketWindMechanicalService.findById(Long.valueOf(id));
				for(int i=0;i<userList.size();i++){
					if(userList.get(i).getId()==workEntity.getGuarderId()){
						userList.remove(i);
						break;
					}
				}
			}
		}
		resultMap.put("userList", userList);
		return new ModelAndView("ticketManage/workticketWindMechanical/sureSubmitPerson",resultMap);
	}
	/**
	 * @Description:   审批确认弹出框
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午2:33:19 
	 * @throws         Exception
	 */
	@RequestMapping("/approveSubmit")
	public ModelAndView approveSubmit(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		String id=request.getParameter("id");
		resultMap.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORK_TICKET_PROCESS_KEY.getName()));
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
		return new ModelAndView("ticketManage/workticketWindMechanical/approveSubmitPerson",resultMap);
	}
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
	public @ResponseBody ResultObj update(@RequestBody WorkTicketEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindMechanicalService.update(t,id);
	}
	/** 批量删除
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/bulkDelete/{ids}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		workTicketWindMechanicalService.deleteBulk(ids);
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
		        
		return new ModelAndView("ticketManage/workticketWindMechanical/workInvalid",resultMap);
	}
	/**
	 * @Description:   鉴定
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@RequestMapping("/saveInvalid")
	public @ResponseBody ResultObj saveInvalid(@RequestBody WorkTicketEntity workTicketEntity, HttpServletRequest request) {
		return workTicketWindMechanicalService.saveInvalid(workTicketEntity);
	}
	/**
	 * @Description:   验证是否执行了安全措施
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecute/{id}")
	public @ResponseBody ResultObj isExecute(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindMechanicalService.isExecute(id);
	}
	/**
	 * @Description:   申请试运
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecuteSqsy/{id}")
	public @ResponseBody ResultObj isExecuteSqsy(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindMechanicalService.isExecuteSqsy(id);
	}
	/**
	 * @Description:   试运恢复
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecuteSyhf/{id}")
	public @ResponseBody ResultObj isExecuteSyhf(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindMechanicalService.isExecuteSyhf(id);
	}
	/**
	 * @Description:   修改时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateValidate/{id}")
	public @ResponseBody ResultObj updateValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindMechanicalService.updateValidate(id);
	}
	
	/**
	 * @Description:   单个删除时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/deleteValidate/{id}")
	public @ResponseBody ResultObj deleteValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindMechanicalService.deleteValidate(id);
	}
	
	/**
	 * @Description:   提交时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/tijiaoValidate/{id}")
	public @ResponseBody ResultObj tijiaoValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindMechanicalService.tijiaoValidate(id);
	}
	/**
	 * @Description:   鉴定的时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/invalidValidate/{id}")
	public @ResponseBody ResultObj invalidValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindMechanicalService.invalidValidate(id);
	}
	/**
	 * @Description:   设置标准票时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/setValidate/{id}")
	public @ResponseBody ResultObj setValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindMechanicalService.setValidate(id);
	}
	
/*	@RequestMapping("/exportExcel/{conditions}")
	public void expData(HttpServletRequest req,HttpServletResponse res,@PathVariable String conditions) throws UnsupportedEncodingException{
		List<Condition> list = JsonUtil.jsonToObj(conditions,List.class);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("conditions", list);
		List<WorkTicketEntity> dataList=workTicketWindMechanicalService.findByCondition(param, null);
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (WorkTicketEntity workTicketEntity : dataList) {
			if(workTicketEntity.getChangeAllowDate()!=null){
				workTicketEntity.setPlandateStartString(sf.format(workTicketEntity.getChangeAllowDate()));
			}
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "风机工作票模板.xlsx","风机工作票.xlsx", resultMap);
	}*/
	
	/**
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午6:32:33 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		SysUserEntity userEntity = RequestContext.get().getUser();
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
		//第一种工作票
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WINDMECHANICAL.getCode()));
		conditions.add(new Condition("t.C_UNIT_NAME_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,userEntity.getUnitId()));
		Page<WorkTicketEntity> page=new Page<WorkTicketEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		
	  //查询集控中心人员
		List<Condition> userConditions=new ArrayList<Condition>();
		userConditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,138));//集控中心
		userConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<SysUserEntity> userEntities =sysUserService.findByCondition(userConditions, null);
		List<String> userNameList = new ArrayList<String>();
		for (SysUserEntity userEntity2 : userEntities) {
			userNameList.add(userEntity2.getLoginName());
		}
		List<WorkTicketEntity> dataList = new ArrayList<WorkTicketEntity>();
		for (String str : userNameList) {
			if (userEntity.getLoginName().equals(str)|| userEntity.getLoginName().equals("admin")||userEntity.getLoginName().equals("super")) {
				conditions.clear();
				conditions = OrmUtil.changeMapToCondition(params);
				conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
				//本身是工作票
				conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
				conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WINDMECHANICAL.getCode()));
				dataList=workTicketWindMechanicalService.findByCondition(conditions, page);
			}else {
				dataList=workTicketWindMechanicalService.findByCondition(conditions, page);
			}
		}
//		List<WorkTicketWindEntity> dataList=workTicketWindMechanicalService.findByCondition(conditions, page);
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (WorkTicketEntity workTicketEntity : dataList) {
			if(workTicketEntity.getChangeAllowDate()!=null){
				workTicketEntity.setPlandateStartString(sf.format(workTicketEntity.getChangeAllowDate()));
			}
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "风机工作票模板.xlsx","风机工作票.xlsx", resultMap);
	}
	
	
	/**
	 * @Description:   缺陷单的选择带回
	 * @author         zhangzq 
	 * @Date           2017年7月4日 下午1:26:58 
	 * @throws         Exception
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
		return this.createModelAndView("ticketManage/workticketWindMechanical/workDefectList", model);
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
			workTicketWindMechanicalService.submit(id,selectUser);
			resultObj.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.setResult("error");
		}
		return resultObj;
	}
	
	
	/**
	 *	详细页面的审批试运  zzq 20170621
	 */
	@RequestMapping("/sqsyDetail/{id}")
	public ModelAndView sqsyDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(id);
		
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
		return this.createModelAndView("ticketManage/workticketWindMechanical/workElectricDetailSqsy", model);
	}
	/**
	 * @Description:   详细页面的试运恢复
	 * @author         zhangzq 
	 * @Date           2017年6月21日 上午9:52:38 
	 * @throws         Exception
	 */
	 
	@RequestMapping("/syhfDetail/{id}")
	public ModelAndView syhfDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(id);
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
		return this.createModelAndView("ticketManage/workticketWindMechanical/workElectricDetailSyhf", model);
	}
	/**
	 * @Description:   新增是否填写了安全措施的验证
	 * @author         zhangzq 
	 * @Date           20171124
	 * @throws         Exception
	 */
	@RequestMapping(value = "/addValidate/{uuid}")
	public @ResponseBody ResultObj addValidate(HttpServletRequest request ,@PathVariable String uuid) {
		return workTicketWindMechanicalService.addValidate(uuid);
	}
	/**
	 * @Description:   新增是否填写了安全措施的验证
	 * @author         zhangzq 
	 * @Date           20171124
	 * @throws         Exception
	 */
	@RequestMapping(value = "/editValidate/{id}")
	public @ResponseBody ResultObj editValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketWindMechanicalService.editValidate(id);
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
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String uuid=request.getParameter("uuid");
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
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
        
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));

		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList=sysUserService.findByCondition(conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
			resultMap.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
			resultMap.put("yearStart", yearStart);
			resultMap.put("yearEnd", yearEnd);
			resultMap.put("identify", identify);
	        resultMap.put("qualifiedCount", qualifiedCount);
	        resultMap.put("unQualifiedCount", unQualifiedCount);
        return this.createModelAndView("ticketManage/workticketWindMechanical/workTicketListJan", resultMap);
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
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String uuid=request.getParameter("uuid");
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
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
		
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));

		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList=sysUserService.findByCondition(conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		resultMap.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		resultMap.put("yearStart", yearStart);
		resultMap.put("yearEnd", yearEnd);
		resultMap.put("identify", identify);
		resultMap.put("qualifiedCount", qualifiedCount);
		resultMap.put("unQualifiedCount", unQualifiedCount);
		return this.createModelAndView("ticketManage/workticketWindMechanical/workTicketListJan", resultMap);
	}
	@RequestMapping(value = "/searchJanData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchJanData(HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
		String yearStart = request.getParameter("yearStart");
		String yearEnd = request.getParameter("yearEnd");
		String identify = request.getParameter("identify");
		String qualifiedCount = request.getParameter("qualifiedCount");
		String unQualifiedCount = request.getParameter("unQualifiedCount");
		Page<WorkTicketEntity> page =  PageUtil.getPage(params);
		  
			List<WorkTicketEntity> entities =workTicketWindMechanicalService.searchJan(yearStart,yearEnd,params,page,identify,qualifiedCount,unQualifiedCount);
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
	 *	详细页面
	 * @throws ParseException 
	 */
	@RequestMapping("/runDetail")
	public ModelAndView runDetail(HttpServletRequest request) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String code = request.getParameter("code");
		String rlId = request.getParameter("rlId");
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, code));
		List<WorkTicketEntity> workTicketList = workTicketWindMechanicalService.findByCondition(conditions, null);
		WorkTicketEntity workTicketEntity = workTicketList.get(0);
		resultMap.put("workTicketId", workTicketEntity.getId());
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		conditions.clear();
		conditions.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditions.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, workTicketEntity.getId()));
		conditions.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_WINDMECHANICAL_PROCESS_KEY.getName()));
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
		resultMap.put("rlId", rlId);
		return this.createModelAndView("ticketManage/workticketWindMechanical/workTicketDetail", resultMap);
	}
	/**
	 * 检索风机票列表
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping("/searchData")
	public @ResponseBody  ResultListObj searchDate(HttpServletRequest request,@RequestBody Map<String, Object> params){
		
		//查询集控中心人员
		List<Condition> userConditions=new ArrayList<Condition>();
		userConditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,138));//集控中心
		userConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<SysUserEntity> userEntities =sysUserService.findByCondition(userConditions, null);
		List<String> userNameList = new ArrayList<String>();
		for (SysUserEntity userEntity : userEntities) {
			userNameList.add(userEntity.getLoginName());
		}
		
		//默认计划时间 倒叙
		Page<WorkTicketWindEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.desc("createDate"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		for(Condition condition :conditions){
			if(condition.getFieldName().equals("code")){
				condition.setFieldName("t.C_CODE");
			}
		}
		ResultListObj resultObj = new ResultListObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<WorkTicketWindEntity> windEntities = new ArrayList<WorkTicketWindEntity>();
		conditions.add(new Condition("t.C_UNIT_NAME_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,userEntity.getUnitId()));
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WINDMECHANICAL.getCode()));
		
		for (String str : userNameList) {
			if (userEntity.getLoginName().equals(str)|| userEntity.getLoginName().equals("admin")||userEntity.getLoginName().equals("super")) {
				conditions.clear();
				conditions = OrmUtil.changeMapToCondition(params);
				conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
				//本身是工作票
				conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
				conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WINDMECHANICAL.getCode()));
				windEntities = (List<WorkTicketWindEntity>)workTicketWindMechanicalService.findByCondition(conditions, page);
			}else {
				windEntities =  (List<WorkTicketWindEntity>) workTicketWindMechanicalService.findByCondition(conditions, page);
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