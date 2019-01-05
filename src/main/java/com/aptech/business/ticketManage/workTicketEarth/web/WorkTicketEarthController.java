package com.aptech.business.ticketManage.workTicketEarth.web;

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
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.orgaApp.domain.OrgaAppEntity;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.workControlCard.domain.WorkControlCardEntity;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workEarth.domain.WorkEarthEntity;
import com.aptech.business.ticketManage.workEarth.service.WorkEarthService;
import com.aptech.business.ticketManage.workTicketEarth.domain.WorkTicketEarthEntity;
import com.aptech.business.ticketManage.workTicketEarth.service.WorkTicketEarthService;
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
 * @created 2017-06-02 11:50:39
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workTicketEarth")
public class WorkTicketEarthController extends BaseController<WorkTicketEarthEntity> {
	
	@Autowired
	private WorkTicketEarthService workTicketEarthService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private WorkEarthService workEarthService;
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
	public IBaseEntityOperation<WorkTicketEarthEntity> getService() {
		return workTicketEarthService;
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
		return this.createModelAndView("ticketManage/workEarth/workEarthList", resultMap);
	}
	
	
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
        
        //变更人的下拉框
  		List<Condition> conditions=new ArrayList<Condition>();
  		//conditions.add(new Condition("unitId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,workTicketEarthEntity.getUnitNameId() ));
  		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
  		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
  		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		resultMap.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
      		
		return this.createModelAndView("ticketManage/workEarth/workEarthAdd", resultMap);
	}
	
	
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(id);
		resultMap.put("workTicketEarthEntity", workTicketEarthEntity);
		List<Condition> conditions=new ArrayList<Condition>();
	       //查询电气一开始
  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
  		WorkEarthEntity workEarthEntity=null;
  		List<WorkEarthEntity> workEarthEntityList=workEarthService.findByCondition(conditions, null);
        if(!workEarthEntityList.isEmpty()){
            workEarthEntity=workEarthEntityList.get(0);
          	resultMap.put("workEarthEntity", workEarthEntity);
          }else{
              workEarthEntity=new WorkEarthEntity();
          	resultMap.put("workEarthEntity", workEarthEntity);
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
		return this.createModelAndView("ticketManage/workEarth/workEarthEdit", resultMap);
	}
	
	/**
	 *	详细页面
	 * @throws ParseException 
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("workTicketId", id);
		WorkTicketEarthEntity  workTicketEarthEntity=workTicketEarthService.findById(Long.valueOf(id));
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditions.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditions.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_EARTH_PROCESS_KEY.getName()));
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
		resultMap.put("workStatus", workTicketEarthEntity.getWorkStatus());
		resultMap.put("rlId", "");
		return this.createModelAndView("ticketManage/workEarth/workEarthDetail", resultMap);
	}
	/**
	 *	详细页面   第一个tab   
	 * @throws ParseException 
	 */
	@RequestMapping("/workPjxxDetail/{id}")
	public ModelAndView workPjxxDetail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(id);
		resultMap.put("workTicketEarthEntity", workTicketEarthEntity);
		
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workTicketEarthEntity.getUnitNameId()));
		workTicketEarthEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workTicketEarthEntity.getGroupId()));
		workTicketEarthEntity.setGroupName(orgaEntity.getName());//
		
		List<Condition> conditions=new ArrayList<Condition>();
        
       //查询电气一开始
        conditions.clear();
  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
  		WorkEarthEntity workEarthEntity=null;
  		List<WorkEarthEntity> workEarthEntityList=workEarthService.findByCondition(conditions, null);
        if(!workEarthEntityList.isEmpty()){
            workEarthEntity=workEarthEntityList.get(0);
            SysUserEntity sysUserEntity=sysUserService.findById(new Long(workEarthEntity.getRemarkGuarderName()));
            workEarthEntity.setRemarkGuarderName(sysUserEntity.getName());
          	resultMap.put("workEarthEntity", workEarthEntity);
          }else{
              workEarthEntity=new WorkEarthEntity();
          	resultMap.put("workEarthEntity", workEarthEntity);
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
		
		return this.createModelAndView("ticketManage/workEarth/workEarthDetailPjxx", resultMap);
	}
	/**
	 *	详细页面   第二个tab   
	 * @throws ParseException 
	 */
	@RequestMapping("/workCardDetail/{id}")
	public ModelAndView workCardDetail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(id);
		resultMap.put("workTicketEarthEntity", workTicketEarthEntity);
		
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
		return this.createModelAndView("ticketManage/workEarth/workTicketDetailCard", resultMap);
	}
	/**
	 *	动土工作票的审批页面
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
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_EARTH_PROCESS_KEY.getName()));
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
		WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(id);
		resultMap.put("workTicketEarthEntity", workTicketEarthEntity);
		
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workTicketEarthEntity.getUnitNameId()));
		workTicketEarthEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workTicketEarthEntity.getGroupId()));
		workTicketEarthEntity.setGroupName(orgaEntity.getName());//
		
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
  		WorkEarthEntity workEarthEntity=new WorkEarthEntity();
  		List<WorkEarthEntity> workEarthEntityList=workEarthService.findByCondition(conditions, null);
        if(!workEarthEntityList.isEmpty()){
            workEarthEntity=workEarthEntityList.get(0);
            SysUserEntity sysUserEntity=sysUserService.findById(new Long(workEarthEntity.getRemarkGuarderName()));
            workEarthEntity.setRemarkGuarderName(sysUserEntity.getName());
          	resultMap.put("workEarthEntity", workEarthEntity);
          }else{
          	resultMap.put("workEarthEntity", workEarthEntity);
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
  		
		return this.createModelAndView("ticketManage/workEarth/workEarthApprove", resultMap);
	}
	

	/**
	* @Title: sureSubmit
	* @Description: 提交审批
	* @author sunliang
	* @date 2017年7月26日上午9:26:16
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping("/sureSubmit")
	public ModelAndView sureSubmit(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		String id=request.getParameter("id");
		resultMap.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORK_TICKET_EARTH_PROCESS_KEY.getName()));
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
		return new ModelAndView("/ticketManage/workEarth/sureSubmitPerson",resultMap);
	}
	/**
	 * @Description:   审批确认弹出框
	 * @author         sunliang 
	 * @Date           2017年6月28日 下午2:33:19 
	 * @throws         Exception
	 */
	@RequestMapping("/approveSubmit")
	public ModelAndView approveSubmit(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		String id=request.getParameter("id");
		resultMap.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.WORK_TICKET_EARTH_PROCESS_KEY.getName()));
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
		return new ModelAndView("/ticketManage/workEarth/approveSubmitPerson",resultMap);
	}
	/**
	 * @Description:   自己写的修改方法
	 * @author         sunliang 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody ResultObj update(@RequestBody WorkTicketEarthEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return workTicketEarthService.update(t,id);
	}
	/** 批量删除
	 * @author         sunliang 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/bulkDelete/{ids}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		workTicketEarthService.deleteBulk(ids);
		return resultObj;
	}
	/**
	 * @Description:   鉴定跳转页面
	 * @author         sunliang 
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
		        
		return new ModelAndView("/ticketManage/workEarth/workInvalid",resultMap);
	}
	/**
	 * @Description:   鉴定
	 * @author         sunliang 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@RequestMapping("/saveInvalid")
	public @ResponseBody ResultObj saveInvalid(@RequestBody WorkTicketEarthEntity workTicketEarthEntity, HttpServletRequest request) {
		return workTicketEarthService.saveInvalid(workTicketEarthEntity);
	}
	/**
	 * @Description:   验证是否执行了安全措施
	 * @author         sunliang 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecute/{id}")
	public @ResponseBody ResultObj isExecute(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketEarthService.isExecute(id);
	}
	/**
	 * @Description:   申请试运
	 * @author         sunliang 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecuteSqsy/{id}")
	public @ResponseBody ResultObj isExecuteSqsy(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketEarthService.isExecuteSqsy(id);
	}
	/**
	 * @Description:   试运恢复
	 * @author         sunliang 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecuteSyhf/{id}")
	public @ResponseBody ResultObj isExecuteSyhf(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketEarthService.isExecuteSyhf(id);
	}
	/**
	 * @Description:   修改时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateValidate/{id}")
	public @ResponseBody ResultObj updateValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketEarthService.updateValidate(id);
	}
	
	/**
	 * @Description:   单个删除时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/deleteValidate/{id}")
	public @ResponseBody ResultObj deleteValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketEarthService.deleteValidate(id);
	}
	
	/**
	 * @Description:   提交时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/tijiaoValidate/{id}")
	public @ResponseBody ResultObj tijiaoValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketEarthService.tijiaoValidate(id);
	}
	/**
	 * @Description:   鉴定的时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/invalidValidate/{id}")
	public @ResponseBody ResultObj invalidValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketEarthService.invalidValidate(id);
	}
	/**
	 * @Description:   设置标准票时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/setValidate/{id}")
	public @ResponseBody ResultObj setValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketEarthService.setValidate(id);
	}
	/**
	 * @Description:   
	 * @author         sunliang 
	 * @Date           2017年6月28日 下午6:32:33 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
		//第一种工作票
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.EARTH.getCode()));
		List<WorkTicketEarthEntity> dataList=workTicketEarthService.findByCondition(conditions, null);
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (WorkTicketEarthEntity workTicketEarthEntity : dataList) {
			workTicketEarthEntity.setPlandateStartString(sf.format(workTicketEarthEntity.getPlandateStart()));
			workTicketEarthEntity.setPlandateEndString(sf.format(workTicketEarthEntity.getPlandateEnd()));
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "动土工作票模板.xlsx","动土工作票.xlsx", resultMap);
	}
	/**
	 * @Description:   缺陷单的选择带回
	 * @author         sunliang 
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
		// TODO下拉树具体内容根据具体业务定制
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
		return this.createModelAndView("/ticketManage/workticket/workDefectList", model);
	}
}