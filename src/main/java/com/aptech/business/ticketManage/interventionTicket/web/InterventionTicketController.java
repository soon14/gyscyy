package com.aptech.business.ticketManage.interventionTicket.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.orgaApp.domain.OrgaAppEntity;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.interventionTicket.domain.InterventionTicketEntity;
import com.aptech.business.ticketManage.interventionTicket.service.InterventionTicketService;
import com.aptech.business.ticketManage.workControlCard.domain.WorkControlCardEntity;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workticketIntervention.domain.WorkticketInterventionEntity;
import com.aptech.business.ticketManage.workticketIntervention.service.WorkticketInterventionService;
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
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.processNodeAuth.domain.ProcessNodeAuthEntity;
import com.aptech.common.workflow.processNodeAuth.service.ProcessNodeAuthService;
import com.aptech.common.workflow.todoTask.domain.TodoTaskEntity;
import com.aptech.common.workflow.todoTask.service.TodoTaskService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.IdUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 工作票应用配置控制器
 *
 * @author 
 * @created 2017-06-29 18:50:39
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/interventionTicket")
public class InterventionTicketController extends BaseController<InterventionTicketEntity> {
	
	@Autowired
	private InterventionTicketService interventionTicketService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private WorkticketInterventionService workticketInterventionService;
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
	@Override
	public IBaseEntityOperation<InterventionTicketEntity> getService() {
		return interventionTicketService;
	}
	
	/**
	 * @Description:   list页面跳转
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:15:35 
	 * @throws         Exception
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String uuid=request.getParameter("uuid");
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//zzq20180416修改查询班组列表开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsBanzu=new ArrayList<Condition>();
		conditionsBanzu.add(new Condition("C_ORGANIZATION", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
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
		return this.createModelAndView("ticketManage/interventionTicket/interventionTicketList", resultMap);
	}
	
	
	
	/**
	 * @Description:   跳转到添加页面
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:15:27 
	 * @throws         Exception
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String typicalTicketId=request.getParameter("typicalTicketId");
		//zzq20180416修改查询班组列表开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsBanzu=new ArrayList<Condition>();
		conditionsBanzu.add(new Condition("C_ORGANIZATION", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
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
        
  		List<Condition> conditions=new ArrayList<Condition>();
  		//conditions.add(new Condition("unitId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,interventionTicketEntity.getUnitNameId() ));
  		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
  		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
  		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		resultMap.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
  		resultMap.put("typicalTicketId", typicalTicketId);
		return this.createModelAndView("ticketManage/interventionTicket/interventionTicketAdd", resultMap);
	}
	
	
	
	/**
	 * @Description:   跳转到修改页面
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:15:47 
	 * @throws         Exception
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String typicalTicketId=(String)request.getParameter("typicalTicketId");
		resultMap.put("typicalTicketId", typicalTicketId);
		// 返回前台数据项
		InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(id);
		resultMap.put("interventionTicketEntity", interventionTicketEntity);
		List<Condition> conditions=new ArrayList<Condition>();
	       //查询电气一开始
  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
  		WorkticketInterventionEntity workticketInterventionEntity=null;
  		List<WorkticketInterventionEntity> workElectricList=workticketInterventionService.findByCondition(conditions, null);
        if(!workElectricList.isEmpty()){
        	workticketInterventionEntity=workElectricList.get(0);
          	resultMap.put("workticketInterventionEntity", workticketInterventionEntity);
          }else{
        	workticketInterventionEntity=new WorkticketInterventionEntity();
          	resultMap.put("workticketInterventionEntity", workticketInterventionEntity);
        }
       //查询电气一结束
	        
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
		conditionsBanzu.add(new Condition("C_ORGANIZATION", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
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
		return this.createModelAndView("ticketManage/interventionTicket/interventionTicketEdit", resultMap);
	}
	
	/**
	 * @Description:   跳转到查看页面
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:15:58 
	 * @throws         Exception
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("workTicketId", id);
		InterventionTicketEntity  interventionTicketEntity=interventionTicketService.findById(Long.valueOf(id));
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditions.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditions.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_SECOND_PROCESS_KEY.getName()));
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
		resultMap.put("workStatus", interventionTicketEntity.getWorkStatus());
		return this.createModelAndView("ticketManage/interventionTicket/interventionTicketDetail", resultMap);
	}
	/**
	 * @Description:   详细页面   第一个tab
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:16:06 
	 * @throws         Exception
	 */
	@RequestMapping("/workPjxxDetail/{id}")
	public ModelAndView workPjxxDetail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(id);
		resultMap.put("interventionTicketEntity", interventionTicketEntity);
		
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(interventionTicketEntity.getUnitNameId()));
		interventionTicketEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(interventionTicketEntity.getGroupId()));
		interventionTicketEntity.setGroupName(orgaEntity.getName());//
		
		List<Condition> conditions=new ArrayList<Condition>();
        
       //查询电气一开始
        conditions.clear();
  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
  		WorkticketInterventionEntity workticketInterventionEntity=null;
  		List<WorkticketInterventionEntity> workElectricList=workticketInterventionService.findByCondition(conditions, null);
        if(!workElectricList.isEmpty()){
        	workticketInterventionEntity=workElectricList.get(0);
          	resultMap.put("workticketInterventionEntity", workticketInterventionEntity);
          }else{
        	workticketInterventionEntity=new WorkticketInterventionEntity();
          	resultMap.put("workticketInterventionEntity", workticketInterventionEntity);
        }
       //查询电气一结束
		        
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		//zzq20180416修改查询班组列表开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsBanzu=new ArrayList<Condition>();
		conditionsBanzu.add(new Condition("C_ORGANIZATION", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<OrgaAppEntity> orgaApp = orgaAppService.findByCondition(conditionsBanzu, null);
		//zzq20180416修改查询班组列表结束
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		for(OrgaAppEntity orgaAppEntity : orgaApp){
			comboWorkTicketVO.addOption(orgaAppEntity.getId().toString(), orgaAppEntity.getName());
	    }
		//TODO下拉框具体内容根据具体业务定制
		resultMap.put("groupIdCombobox", JsonUtil.toJson(comboWorkTicketVO.getOptions()));
		
		return this.createModelAndView("ticketManage/interventionTicket/interventionTicketDetailPjxx", resultMap);
	}
	/**
	 * @Description:   详细页面   第二个tab   
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:16:15 
	 * @throws         Exception
	 */
	@RequestMapping("/workCardDetail/{id}")
	public ModelAndView workCardDetail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(id);
		resultMap.put("interventionTicketEntity", interventionTicketEntity);
		
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
		return this.createModelAndView("ticketManage/interventionTicket/interventionTicketDetailCard", resultMap);
	}
	/**
	 * @Description:   介入工作票的审批页面
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:16:40 
	 * @throws         Exception
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
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.INTERVENTIONTICKE_PROCESS_KEY.getName()));
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
		InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(id);
		resultMap.put("interventionTicketEntity", interventionTicketEntity);
		
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(interventionTicketEntity.getUnitNameId()));
		interventionTicketEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(interventionTicketEntity.getGroupId()));
		interventionTicketEntity.setGroupName(orgaEntity.getName());//
		
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
        
        //查询电气一开始
        conditions.clear();
  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
  		WorkticketInterventionEntity workticketInterventionEntity=new WorkticketInterventionEntity();
  		List<WorkticketInterventionEntity> workElectricList=workticketInterventionService.findByCondition(conditions, null);
        if(!workElectricList.isEmpty()){
        	workticketInterventionEntity=workElectricList.get(0);
          	resultMap.put("workticketInterventionEntity", workticketInterventionEntity);
          }else{
          	resultMap.put("workticketInterventionEntity", workticketInterventionEntity);
        }
        //查询电气一结束
		        
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
  		
		return this.createModelAndView("ticketManage/interventionTicket/interventionTicketApprove", resultMap);
	}
	
	/**
	 * @Description:   提交确认  弹出框      这里要调用第一个流程接口，得到下一步的那些人的id集合
	 * @author         changl 
	 * @Date           2017年8月4日 上午9:48:43 
	 * @throws         Exception
	 */
	@RequestMapping("/sureSubmit")
	public ModelAndView sureSubmit(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		String id=request.getParameter("id");
		resultMap.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.INTERVENTIONTICKE_PROCESS_KEY.getName()));
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
		return new ModelAndView("ticketManage/workticket/sureSubmitPerson",resultMap);
	}
	/**
	 * @Description:   审批确认弹出框
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:17:17 
	 * @throws         Exception
	 */
	@RequestMapping("/approveSubmit")
	public ModelAndView approveSubmit(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		String id=request.getParameter("id");
		resultMap.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORK_TICKET_SECOND_PROCESS_KEY.getName()));
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
		return new ModelAndView("ticketManage/workticket/approveSubmitPerson",resultMap);
	}
	/**
	 * @Description:   自己写的修改方法
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:17:32 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody ResultObj update(@RequestBody InterventionTicketEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return interventionTicketService.update(t,id);
	}
	/**
	 * @Description:   批量删除
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:17:32 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/bulkDelete/{ids}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		interventionTicketService.deleteBulk(ids);
		return resultObj;
	}
	/**
	 * @Description:   鉴定跳转页面
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:18:17 
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
		        
		return new ModelAndView("ticketManage/interventionTicket/workTwoInvalid",resultMap);
	}
	/**
	 * @Description:   鉴定
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:18:28 
	 * @throws         Exception
	 */
	@RequestMapping("/saveInvalid")
	public @ResponseBody ResultObj saveInvalid(@RequestBody InterventionTicketEntity interventionTicketEntity, HttpServletRequest request) {
		return interventionTicketService.saveInvalid(interventionTicketEntity);
	}
	/**
	 * @Description:   验证是否执行了安全措施
	 * @author         changl 
	 * @Date           2017年8月3日 上午11:46:34 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecute/{id}")
	public @ResponseBody ResultObj isExecute(HttpServletRequest request ,@PathVariable Long id) {
		return interventionTicketService.isExecute(id);
	}
	/**
	 * @Description:   修改时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:18:52 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateValidate/{id}")
	public @ResponseBody ResultObj updateValidate(HttpServletRequest request ,@PathVariable Long id) {
		return interventionTicketService.updateValidate(id);
	}
	
	/**
	 * @Description:   单个删除时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:19:02 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/deleteValidate/{id}")
	public @ResponseBody ResultObj deleteValidate(HttpServletRequest request ,@PathVariable Long id) {
		return interventionTicketService.deleteValidate(id);
	}
	
	/**
	 * @Description:   提交时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:19:12 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/tijiaoValidate/{id}")
	public @ResponseBody ResultObj tijiaoValidate(HttpServletRequest request ,@PathVariable Long id) {
		return interventionTicketService.tijiaoValidate(id);
	}
	/**
	 * @Description:   鉴定的时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:19:18 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/invalidValidate/{id}")
	public @ResponseBody ResultObj invalidValidate(HttpServletRequest request ,@PathVariable Long id) {
		return interventionTicketService.invalidValidate(id);
	}
	/**
	 * @Description:   设置标准票时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:19:27 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/setValidate/{id}")
	public @ResponseBody ResultObj setValidate(HttpServletRequest request ,@PathVariable Long id) {
		return interventionTicketService.setValidate(id);
	}
	/**
	 * @Description:   导出
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:19:39 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditions=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditions));
		List<InterventionTicketEntity> dataList=interventionTicketService.findByCondition(params, null);
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (InterventionTicketEntity interventionTicketEntity : dataList) {
				if(interventionTicketEntity.getChangeAllowDate()!=null){
				interventionTicketEntity.setChangeAllowDateString(sf.format(interventionTicketEntity.getChangeAllowDate()));
				}
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "介入工作票模板.xlsx","介入工作票.xlsx", resultMap);
	}
	/**
	 * @Description: 引用典型票
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/yydxp")
	public @ResponseBody ResultObj yydxp(HttpServletRequest request,@RequestBody InterventionTicketEntity t) {
		return  interventionTicketService.yydxp(t);
	}
}