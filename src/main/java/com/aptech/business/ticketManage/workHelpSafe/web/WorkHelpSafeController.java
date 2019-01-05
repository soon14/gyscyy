package com.aptech.business.ticketManage.workHelpSafe.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.Istypicaleum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkSafeTypeEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.orgaApp.domain.OrgaAppEntity;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.workControlCard.domain.WorkControlCardEntity;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workHelpSafe.domain.WorkHelpSafeEntity;
import com.aptech.business.ticketManage.workHelpSafe.service.WorkHelpSafeService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workTicketEquip.domain.WorkTicketEquipEntity;
import com.aptech.business.ticketManage.workTicketEquip.service.WorkTicketEquipService;
import com.aptech.business.ticketManage.workTicketTwo.domain.WorkTicketTwoEntity;
import com.aptech.common.act.service.ActTaskService;
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
import com.aptech.framework.util.IdUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 工作票应用配置控制器
 *
 * @author 
 * @created 2017-10-26 11:50:39
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workHelpSafe")
public class WorkHelpSafeController extends BaseController<WorkHelpSafeEntity> {
	
	@Autowired
	private WorkHelpSafeService workHelpSafeService;
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
	private ActTaskService actTaskService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private WorkTicketEquipService workTicketEquipService;

	@Override
	public IBaseEntityOperation<WorkHelpSafeEntity> getService() {
		return workHelpSafeService;
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
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("WORKHELP_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        resultMap.put("statusTypes", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        
        
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
              
              
        //删除垃圾数据
        if(uuid!=null){
        	WorkSafeEntity workSafeEntity=new WorkSafeEntity();
    		workSafeEntity.setUuidCode(uuid);
            workSafeService.deleteByCondition("deleteByUuid", workSafeEntity);
            ControlCardRiskEntity controlCardRiskEntity=new ControlCardRiskEntity();
    		controlCardRiskEntity.setUuidCode(uuid);
    		controlCardRiskService.deleteByCondition("deleteByUuid", controlCardRiskEntity);
        }
        String userUnitRels = "[]";
        resultMap.put("userUnitRels", userUnitRels);
		return this.createModelAndView("ticketManage/workHelpSafe/workHelpSafeList", resultMap);
	}
	
	
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String typicalTicketId=request.getParameter("typicalTicketId");
		
		
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
        //添加的时候要给安全措施写一条无的记录，zzq修改开始 20180315
        String uuid=IdUtil.creatUUID();
        resultMap.put("uuid", uuid);
        //添加的时候要给安全措施写一条无的记录，zzq修改结束 20180315
        
  		resultMap.put("typicalTicketId", typicalTicketId);
  		
  		//添加的时候要给安全措施写一条无的记录，zzq修改开始 20180315
  		WorkSafeEntity workSafeEntity=null;
  		workSafeEntity=new WorkSafeEntity();
  		workSafeEntity.setSignerContent("无");
  		workSafeEntity.setSafeType(Long.valueOf(WorkSafeTypeEnum.SAFEONE.getCode()));
  		workSafeEntity.setOrderSeq(Long.valueOf(1));
  		workSafeEntity.setUuidCode(uuid);
  		workSafeService.addEntity(workSafeEntity);
  		//添加的时候要给安全措施写一条无的记录，zzq修改结束 20180315
  		String userUnitRels = "[]";
  		resultMap.put("userUnitRels", userUnitRels);
		return this.createModelAndView("ticketManage/workHelpSafe/workHelpSafeAdd", resultMap);
	}
	
	
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String typicalTicketId=request.getParameter("typicalTicketId");
		resultMap.put("typicalTicketId", typicalTicketId);
		// 返回前台数据项
		WorkHelpSafeEntity workHelpSafeEntity=workHelpSafeService.findById(id);
		resultMap.put("workHelpSafeEntity", workHelpSafeEntity);
		List<Condition> conditions=new ArrayList<Condition>();
	        
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
		return this.createModelAndView("ticketManage/workHelpSafe/workHelpSafeEdit", resultMap);
	}
	
	/**
	 *	详细页面
	 * @throws ParseException 
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("workTicketId", id);
		WorkHelpSafeEntity  workHelpSafeEntity=workHelpSafeService.findById(Long.valueOf(id));
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditions.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditions.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_HELPSAFE_PROCESS_KEY.getName()));
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
		resultMap.put("workStatus", workHelpSafeEntity.getWorkStatus());
		return this.createModelAndView("ticketManage/workHelpSafe/workHelpSafeDetail", resultMap);
	}
	/**
	 *	详细页面   第一个tab   
	 * @throws ParseException 
	 */
	/**
	 * SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workHelpSafeEntity.getUnitNameId()));
		workHelpSafeEntity.setUnitName(sysUnitEntity.getName());
	 * OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workHelpSafeEntity.getGroupId()));
	workHelpSafeEntity.setGroupName(orgaEntity.getName());//
	*/
	@RequestMapping("/workPjxxDetail/{id}")
	public ModelAndView workPjxxDetail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		WorkHelpSafeEntity workHelpSafeEntity=workHelpSafeService.findById(id);
		resultMap.put("workHelpSafeEntity", workHelpSafeEntity);
		List<Condition> conditions=new ArrayList<Condition>();
		ComboboxVO userComboboxVO = new ComboboxVO();
		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		for(SysUserEntity sysUserEntity : userListBox){
			userComboboxVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	    }
		resultMap.put("userListBox", JsonUtil.toJson(userComboboxVO.getOptions()));
      
		        
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
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
		
		return this.createModelAndView("ticketManage/workHelpSafe/workHelpSafeDetailPjxx", resultMap);
	}
	/**
	 *	详细页面   第二个tab   
	 * @throws ParseException 
	 */
	@RequestMapping("/workCardDetail/{id}")
	public ModelAndView workCardDetail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		WorkHelpSafeEntity workHelpSafeEntity=workHelpSafeService.findById(id);
		resultMap.put("workHelpSafeEntity", workHelpSafeEntity);
		
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
		return this.createModelAndView("ticketManage/workHelpSafe/workHelpSafeDetailCard", resultMap);
	}
	/**
	 *	电器第一种票的审批页面
	 * @throws ParseException 
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id
			,@PathVariable String type) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("type",type);
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_HELPSAFE_PROCESS_KEY.getName()));
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
		WorkHelpSafeEntity workHelpSafeEntity=workHelpSafeService.findById(id);
		resultMap.put("workHelpSafeEntity", workHelpSafeEntity);
		
		/*SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workHelpSafeEntity.getUnitNameId()));
		workHelpSafeEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workHelpSafeEntity.getGroupId()));
		workHelpSafeEntity.setGroupName(orgaEntity.getName());//
*/		
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
  		
		return this.createModelAndView("ticketManage/workHelpSafe/workHelpSafeApprove", resultMap);
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
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORK_TICKET_HELPSAFE_PROCESS_KEY.getName()));
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
		return new ModelAndView("/ticketManage/workHelpSafe/sureSubmitPerson",resultMap);
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
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORK_TICKET_HELPSAFE_PROCESS_KEY.getName()));
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
		return new ModelAndView("/ticketManage/workHelpSafe/approveSubmitPerson",resultMap);
	}
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody ResultObj update(@RequestBody WorkHelpSafeEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.update(t,id);
	}
	/** 批量删除
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/bulkDelete/{ids}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		workHelpSafeService.deleteBulk(ids);
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
		        
		return new ModelAndView("/ticketManage/workHelpSafe/workInvalid",resultMap);
	}
	/**
	 * @Description:   鉴定
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@RequestMapping("/saveInvalid")
	public @ResponseBody ResultObj saveInvalid(@RequestBody WorkHelpSafeEntity workHelpSafeEntity, HttpServletRequest request) {
		return workHelpSafeService.saveInvalid(workHelpSafeEntity);
	}
	/**
	 * @Description:   验证是否执行了操作项目执行
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecute/{id}")
	public @ResponseBody ResultObj isExecute(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.isExecute(id);
	}
	/**
	 * @Description:   验证是否执行了操作项目 恢复
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecuteHf/{id}")
	public @ResponseBody ResultObj isExecuteHf(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.isExecuteHf(id);
	}
	/**
	 * @Description:   申请试运
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecuteSqsy/{id}")
	public @ResponseBody ResultObj isExecuteSqsy(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.isExecuteSqsy(id);
	}
	/**
	 * @Description:   试运恢复
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecuteSyhf/{id}")
	public @ResponseBody ResultObj isExecuteSyhf(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.isExecuteSyhf(id);
	}
	/**
	 * @Description:   修改时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateValidate/{id}")
	public @ResponseBody ResultObj updateValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.updateValidate(id);
	}
	
	/**
	 * @Description:   单个删除时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/deleteValidate/{id}")
	public @ResponseBody ResultObj deleteValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.deleteValidate(id);
	}
	
	/**
	 * @Description:   提交时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/tijiaoValidate/{id}")
	public @ResponseBody ResultObj tijiaoValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.tijiaoValidate(id);
	}
	/**
	 * @Description:   鉴定的时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/invalidValidate/{id}")
	public @ResponseBody ResultObj invalidValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.invalidValidate(id);
	}
	/**
	 * @Description:   设置标准票时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/setValidate/{id}")
	public @ResponseBody ResultObj setValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.setValidate(id);
	}
	/**
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午6:32:33 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		//List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
		//第一种工作票
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WORKHELPSAFE.getCode()));
		Page<WorkHelpSafeEntity> page=new Page<WorkHelpSafeEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		List<WorkHelpSafeEntity> dataList=workHelpSafeService.findByCondition(conditions, page);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		for (WorkHelpSafeEntity workHelpSafeEntity : dataList) {
			if(workHelpSafeEntity.getChangeAllowDate()!=null){
				workHelpSafeEntity.setPlandateStartString(df.format(workHelpSafeEntity.getChangeAllowDate()));
			}
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "继电保护安全措施票模板.xlsx","继电保护安全措施票.xlsx", resultMap);
	}
	
	/**
	 *	工作票选择带回
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/tickeList")
	public ModelAndView tickeList(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
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
		return this.createModelAndView("ticketManage/workHelpSafe/tickeList", resultMap);
	}
	/**
	 * @Description:   签发人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:20:39 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isQfAqcs/{id}")
	public @ResponseBody ResultObj isQfAqcs(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.isQfAqcs(id);
	}
	/**
	 * @Description:   签发人是否写了安全措施  新增按钮
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:20:39 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isQfAqcsAdd/{id}")
	public @ResponseBody ResultObj isQfAqcsAdd(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.isQfAqcsAdd(id);
	}
	/**
	 * @Description:   许可人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:20:39 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isXkAqcs/{id}")
	public @ResponseBody ResultObj isXkAqcs(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.isXkAqcs(id);
	}
	/**
	 * @Description:   许可人是否写了安全措施  新增按钮
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:20:39 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isXkAqcsAdd/{id}")
	public @ResponseBody ResultObj isXkAqcsAdd(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.isXkAqcsAdd(id);
	}
	/**
	 * @Description:   提交方法
	 * @author         zhangzq 
	 * @Date           20171026
	 * @throws         Exception
	 */
	@RequestMapping("/submit")
	public @ResponseBody ResultObj submit(HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		try {
			String id=request.getParameter("workId");
			String selectUser=request.getParameter("selectUser");
			workHelpSafeService.submit(id,selectUser);
			resultObj.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.setResult("error");
		}
		return resultObj;
	}
	/**
	 *	跳转到执行页面
	 */
	@RequestMapping("/getAddZx")
	public ModelAndView getAddZx(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String workId=request.getParameter("workId");
		model.put("workId", workId);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		return this.createModelAndView("ticketManage/workHelpSafe/helpSafeAddZx", model);
	}
	/**
	 * @Description:   执行人同意
	 * @author         zhangzq 
	 * @Date           20171026
	 * @throws         Exception
	 */
	@RequestMapping("/agreeZx")
	public @ResponseBody ResultObj agreeZx(HttpServletRequest request,@RequestBody WorkHelpSafeEntity workHelpSafeEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workHelpSafeEntity.setSpFlag(WorkBtnTypeEnum.ZX.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workHelpSafeEntity.getApproveIdea()==null?"":workHelpSafeEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			workHelpSafeService.updateSpnrAgree(workHelpSafeEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   执行人不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeZx")
	public @ResponseBody ResultObj disAgreeZx(HttpServletRequest request,@RequestBody WorkHelpSafeEntity workHelpSafeEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workHelpSafeEntity.setSpFlag(WorkBtnTypeEnum.ZX.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkHelpSafeEntity WorkHelpSafe = workHelpSafeService.findById(workHelpSafeEntity.getId());
			SysUserEntity fzrEntity=sysUserService.findById(WorkHelpSafe.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workHelpSafeEntity.getApproveIdea()==null?"":workHelpSafeEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workHelpSafeService.updateSpnrDisagree(workHelpSafeEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 *	跳转到执行监护页面
	 */
	@RequestMapping("/getAddZxjh")
	public ModelAndView getAddZxjh(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String workId=request.getParameter("workId");
		model.put("workId", workId);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		return this.createModelAndView("ticketManage/workHelpSafe/helpSafeAddZxjh", model);
	}
	
	/**
	 * @Description:   执行监护同意
	 * @author         zhangzq 
	 * @Date           20171026
	 * @throws         Exception
	 */
	@RequestMapping("/agreeZxjh")
	public @ResponseBody ResultObj agreeZxjh(HttpServletRequest request,@RequestBody WorkHelpSafeEntity workHelpSafeEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workHelpSafeEntity.setSpFlag(WorkBtnTypeEnum.ZXJH.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workHelpSafeEntity.getApproveIdea()==null?"":workHelpSafeEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			workHelpSafeService.updateSpnrAgree(workHelpSafeEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	
	/**
	 *	跳转到恢复页面
	 */
	@RequestMapping("/getAddHf")
	public ModelAndView getAddHf(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String workId=request.getParameter("workId");
		model.put("workId", workId);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		return this.createModelAndView("ticketManage/workHelpSafe/helpSafeAddHf", model);
	}
	
	
	/**
	 * @Description:   恢复人同意
	 * @author         zhangzq 
	 * @Date           20171026
	 * @throws         Exception
	 */
	@RequestMapping("/agreeHf")
	public @ResponseBody ResultObj agreeHf(HttpServletRequest request,@RequestBody WorkHelpSafeEntity workHelpSafeEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workHelpSafeEntity.setSpFlag(WorkBtnTypeEnum.HF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workHelpSafeEntity.getApproveIdea()==null?"":workHelpSafeEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			workHelpSafeService.updateSpnrAgree(workHelpSafeEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   恢复人不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeHf")
	public @ResponseBody ResultObj disAgreeHf(HttpServletRequest request,@RequestBody WorkHelpSafeEntity workHelpSafeEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workHelpSafeEntity.setSpFlag(WorkBtnTypeEnum.HF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkHelpSafeEntity WorkHelpSafe = workHelpSafeService.findById(workHelpSafeEntity.getId());
			SysUserEntity fzrEntity=sysUserService.findById(WorkHelpSafe.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workHelpSafeEntity.getApproveIdea()==null?"":workHelpSafeEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workHelpSafeService.updateSpnrDisagree(workHelpSafeEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 *	跳转到恢复监护页面
	 */
	@RequestMapping("/getAddHfjh")
	public ModelAndView getAddHfjh(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String workId=request.getParameter("workId");
		model.put("workId", workId);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		return this.createModelAndView("ticketManage/workHelpSafe/helpSafeAddHfjh", model);
	}
	
	/**
	 * @Description:   监护同意
	 * @author         zhangzq 
	 * @Date           20171026
	 * @throws         Exception
	 */
	@RequestMapping("/agreeHfjh")
	public @ResponseBody ResultObj agreeHfjh(HttpServletRequest request,@RequestBody WorkHelpSafeEntity workHelpSafeEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workHelpSafeEntity.setSpFlag(WorkBtnTypeEnum.HFJH.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workHelpSafeEntity.getApproveIdea()==null?"":workHelpSafeEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			workHelpSafeService.updateSpnrAgree(workHelpSafeEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   废票
	 * @author         zhangzq 
	 * @Date           2017年7月5日 上午10:08:02 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeFp")
	public @ResponseBody ResultObj disAgreeFp(HttpServletRequest request,@RequestBody WorkHelpSafeEntity workHelpSafeEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workHelpSafeEntity.setSpFlag(WorkBtnTypeEnum.FP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workHelpSafeEntity.getApproveIdea()==null?"":workHelpSafeEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workHelpSafeService.updateSpnrDisagree(workHelpSafeEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   再提交
	 * @author         zhangzq 
	 * @Date           2017年7月13日 上午9:26:10 
	 * @throws         Exception
	 */
	@RequestMapping("/againSubmit")
	public @ResponseBody ResultObj againSubmit(HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		try {
			WorkHelpSafeEntity workHelpSafeEntity=new WorkHelpSafeEntity();
			workHelpSafeEntity.setSpFlag(WorkBtnTypeEnum.ZTJ.getCode());
			String workId=request.getParameter("workId");
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workHelpSafeEntity.getApproveIdea()==null?"":workHelpSafeEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			workHelpSafeEntity.setId(Long.valueOf(workId));
			workHelpSafeService.updateSpnrAgree(workHelpSafeEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   新增是否填写了安全措施的验证
	 * @author         zhangzq 
	 * @Date           20171124
	 * @throws         Exception
	 */
	@RequestMapping(value = "/addValidate/{uuid}")
	public @ResponseBody ResultObj addValidate(HttpServletRequest request ,@PathVariable String uuid) {
		return workHelpSafeService.addValidate(uuid);
	}
	/**
	 * @Description:   新增是否填写了安全措施的验证
	 * @author         zhangzq 
	 * @Date           20171124
	 * @throws         Exception
	 */
	@RequestMapping(value = "/editValidate/{id}")
	public @ResponseBody ResultObj editValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workHelpSafeService.editValidate(id);
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
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("WORKHELP_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        resultMap.put("statusTypes", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        
        
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
              
              
        //删除垃圾数据
        if(uuid!=null){
        	WorkSafeEntity workSafeEntity=new WorkSafeEntity();
    		workSafeEntity.setUuidCode(uuid);
            workSafeService.deleteByCondition("deleteByUuid", workSafeEntity);
            ControlCardRiskEntity controlCardRiskEntity=new ControlCardRiskEntity();
    		controlCardRiskEntity.setUuidCode(uuid);
    		controlCardRiskService.deleteByCondition("deleteByUuid", controlCardRiskEntity);
        }
        resultMap.put("identify", identify);
        resultMap.put("qualifiedCount", qualifiedCount);
        resultMap.put("unQualifiedCount", unQualifiedCount);
        resultMap.put("yearStart", yearStart);
        resultMap.put("yearEnd", yearEnd);
		return this.createModelAndView("ticketManage/workHelpSafe/workHelpSafeListJan", resultMap);
        
		
	}
	@RequestMapping("/month")
	public ModelAndView month(HttpServletRequest request) throws Exception{
		String JanStart = request.getParameter("JanStart");
		String JanEnd = request.getParameter("JanEnd");
		String identify = request.getParameter("identify");
		String month = request.getParameter("month");
		String qualifiedCount = request.getParameter("qualifiedCount");
		String unQualifiedCount = request.getParameter("unQualifiedCount");
		String yearStart=month+"-"+JanStart+" 00:00:00";
		String yearEnd=month+"-"+JanEnd+" 00:00:00";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String uuid=request.getParameter("uuid");
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
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
		Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("WORKHELP_TYPE");
		
		for(String key :  codeDateTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
			codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		resultMap.put("statusTypes", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
		
		
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
		
		
		//删除垃圾数据
		if(uuid!=null){
			WorkSafeEntity workSafeEntity=new WorkSafeEntity();
			workSafeEntity.setUuidCode(uuid);
			workSafeService.deleteByCondition("deleteByUuid", workSafeEntity);
			ControlCardRiskEntity controlCardRiskEntity=new ControlCardRiskEntity();
			controlCardRiskEntity.setUuidCode(uuid);
			controlCardRiskService.deleteByCondition("deleteByUuid", controlCardRiskEntity);
		}
		resultMap.put("identify", identify);
		resultMap.put("qualifiedCount", qualifiedCount);
		resultMap.put("unQualifiedCount", unQualifiedCount);
		resultMap.put("yearStart", yearStart);
		resultMap.put("yearEnd", yearEnd);
		return this.createModelAndView("ticketManage/workHelpSafe/workHelpSafeListJan", resultMap);
		
		
	}
	@RequestMapping(value = "/searchJanData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchJanData(HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
		String yearStart = request.getParameter("yearStart");
		String yearEnd = request.getParameter("yearEnd");
		String identify = request.getParameter("identify");
		String qualifiedCount = request.getParameter("qualifiedCount");
		String unQualifiedCount = request.getParameter("unQualifiedCount");
		Page<WorkHelpSafeEntity> page =  PageUtil.getPage(params);
		  
			List<WorkHelpSafeEntity> entities =workHelpSafeService.searchJan(yearStart,yearEnd,params,page,identify,qualifiedCount,unQualifiedCount);
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
		conditions.add(new Condition("t.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, code));
		List<WorkHelpSafeEntity> workHelpSafeList = workHelpSafeService.findByCondition(conditions, null);
		WorkHelpSafeEntity workHelpSafeEntity = workHelpSafeList.get(0);
		resultMap.put("workTicketId", workHelpSafeEntity.getId());
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		conditions.clear();
		conditions.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditions.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, workHelpSafeEntity.getId()));
		conditions.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_HELPSAFE_PROCESS_KEY.getName()));
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
		resultMap.put("workStatus", workHelpSafeEntity.getWorkStatus());
		resultMap.put("rlId", rlId);
		return this.createModelAndView("ticketManage/workHelpSafe/workHelpSafeDetail", resultMap);
	}
	
	/**
	 * 检索继电保护安措列表
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
		
		ResultListObj resultObj = new ResultListObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<WorkHelpSafeEntity> windEntities = new ArrayList<WorkHelpSafeEntity>();
		Page<WorkHelpSafeEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("t.C_UNIT_NAME_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,userEntity.getUnitId()));
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WORKHELPSAFE.getCode()));
		
		for (String str : userNameList) {
			if (userEntity.getLoginName().equals(str)|| userEntity.getLoginName().equals("admin")||userEntity.getLoginName().equals("super")) {
				conditions.clear();
				conditions = OrmUtil.changeMapToCondition(params);
				conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
				//本身是工作票
				conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
				conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WORKHELPSAFE.getCode()));
				windEntities = (List<WorkHelpSafeEntity>)workHelpSafeService.findByCondition(conditions, page);
			}else {
				windEntities =  (List<WorkHelpSafeEntity>) workHelpSafeService.findByCondition(conditions, page);
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