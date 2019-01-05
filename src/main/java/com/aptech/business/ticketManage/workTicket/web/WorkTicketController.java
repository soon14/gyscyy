package com.aptech.business.ticketManage.workTicket.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.aptech.business.ticketManage.workElectric.domain.WorkElectricEntity;
import com.aptech.business.ticketManage.workElectric.service.WorkElectricService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicket.service.WorkTicketService;
import com.aptech.business.ticketManage.workTicketEquip.domain.WorkTicketEquipEntity;
import com.aptech.business.ticketManage.workTicketEquip.service.WorkTicketEquipService;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEnum;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
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
 * 工作票应用配置控制器
 *
 * @author 
 * @created 2017-06-02 11:50:39
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workTicket")
public class WorkTicketController extends BaseController<WorkTicketEntity> {
	
	@Autowired
	private WorkTicketService workTicketService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private WorkElectricService workElectricService;
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
	private FourCodeService fourCodeService;
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private WorkTicketEquipService workTicketEquipService;
	@Override
	public IBaseEntityOperation<WorkTicketEntity> getService() {
		return workTicketService;
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
//		ComboboxVO companyVo = new ComboboxVO();
//		
//		for(SysUnitEntity sysUnitEntity : unitList){
//			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
//       }
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
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));//选择人员时只列出当前场站的人员
		List<SysUserEntity> allUsers=sysUserService.findByCondition(conditions, null);
		
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        resultMap.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
              
        
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
        String userUnitRels = "[]";
        resultMap.put("userUnitRels", userUnitRels);
		return this.createModelAndView("ticketManage/workticket/workTicketList", resultMap);
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
        
        //变更人的下拉框
  		List<Condition> conditions=new ArrayList<Condition>();
//  		conditions.add(new Condition("unitId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,workTicketEntity.getUnitNameId() ));
  		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
  		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
  		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		resultMap.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
      		
  		resultMap.put("typicalTicketId", typicalTicketId);
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
  		workSafeEntity=new WorkSafeEntity();
  		workSafeEntity.setSignerContent("无");
  		workSafeEntity.setSafeType(Long.valueOf(WorkSafeTypeEnum.SAFEFIVE.getCode()));
  		workSafeEntity.setOrderSeq(Long.valueOf(1));
  		workSafeEntity.setUuidCode(uuid);
  		workSafeService.addEntity(workSafeEntity);
  		workSafeEntity=new WorkSafeEntity();
  		workSafeEntity.setSignerContent("无");
  		workSafeEntity.setSafeType(Long.valueOf(WorkSafeTypeEnum.SAFESIX.getCode()));
  		workSafeEntity.setOrderSeq(Long.valueOf(1));
  		workSafeEntity.setUuidCode(uuid);
  		workSafeService.addEntity(workSafeEntity);
  		//添加的时候要给安全措施写一条无的记录，zzq修改结束 20180315
  		String userUnitRels = "[]";
  		resultMap.put("userUnitRels", userUnitRels);
		return this.createModelAndView("ticketManage/workticket/workTicketAdd", resultMap);
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
		WorkTicketEntity workTicketEntity=workTicketService.findById(id);
		resultMap.put("workTicketEntity", workTicketEntity);
		List<Condition> conditions=new ArrayList<Condition>();
	       //查询电气一开始
  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
  		WorkElectricEntity workElectricEntity=null;
  		List<WorkElectricEntity> workElectricList=workElectricService.findByCondition(conditions, null);
        if(!workElectricList.isEmpty()){
        	workElectricEntity=workElectricList.get(0);
          	resultMap.put("workElectricEntity", workElectricEntity);
          }else{
        	workElectricEntity=new WorkElectricEntity();
          	resultMap.put("workElectricEntity", workElectricEntity);
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
		//安全措施打印页数
  	
  		conditions.clear();
  		conditions.add(new Condition("C_SAFE_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ, 15));
  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
  		List<WorkSafeEntity>  safeEntityList = workSafeService.findByCondition(conditions, null);
  		if (!safeEntityList.isEmpty() ) {
  		    int safeNum=((safeEntityList.size()%20==0)?(safeEntityList.size()/20):(safeEntityList.size()/20+1));
  		     resultMap.put("safeNum",safeNum);
		}
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
		return this.createModelAndView("ticketManage/workticket/workTicketEdit", resultMap);
	}
	
	/**
	 *	详细页面
	 * @throws ParseException 
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
		conditions.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_PROCESS_KEY.getName()));
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
		return this.createModelAndView("ticketManage/workticket/workTicketDetail", resultMap);
	}
	/**
	 *	详细页面   第一个tab   
	 * @throws ParseException 
	 */
	@RequestMapping("/workPjxxDetail/{id}")
	public ModelAndView workPjxxDetail(HttpServletRequest request, @PathVariable Long id) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 返回前台数据项
		WorkTicketEntity workTicketEntity=workTicketService.findById(id);
		if(StringUtil.isEmpty(workTicketEntity.getChangeAllowIdea())){
			workTicketEntity.setChangeAllowIdea("[]");
		}
		resultMap.put("workTicketEntity", workTicketEntity);
		
		
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workTicketEntity.getUnitNameId()));
		workTicketEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workTicketEntity.getGroupId()));
		workTicketEntity.setGroupName(orgaEntity.getName());//
		
		List<Condition> conditions=new ArrayList<Condition>();
		ComboboxVO userComboboxVO = new ComboboxVO();
		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		for(SysUserEntity sysUserEntity : userListBox){
			userComboboxVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	    }
		resultMap.put("userListBox", JsonUtil.toJson(userComboboxVO.getOptions()));
       //查询电气一开始
        conditions.clear();
  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
  		WorkElectricEntity workElectricEntity=null;
  		List<WorkElectricEntity> workElectricList=workElectricService.findByCondition(conditions, null);
        if(!workElectricList.isEmpty()){
        	workElectricEntity=workElectricList.get(0);
          	resultMap.put("workElectricEntity", workElectricEntity);
          }else{
        	workElectricEntity=new WorkElectricEntity();
          	resultMap.put("workElectricEntity", workElectricEntity);
        }
       //查询电气一结束
		        
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
		
		return this.createModelAndView("ticketManage/workticket/workTicketDetailPjxx", resultMap);
	}
	/**
	 *	详细页面   第二个tab   
	 * @throws ParseException 
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
		return this.createModelAndView("ticketManage/workticket/workTicketDetailCard", resultMap);
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
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_PROCESS_KEY.getName()));
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
        
        //查询电气一开始
        conditions.clear();
  		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
  		WorkElectricEntity workElectricEntity=new WorkElectricEntity();
  		List<WorkElectricEntity> workElectricList=workElectricService.findByCondition(conditions, null);
        if(!workElectricList.isEmpty()){
        	workElectricEntity=workElectricList.get(0);
          	resultMap.put("workElectricEntity", workElectricEntity);
          }else{
          	resultMap.put("workElectricEntity", workElectricEntity);
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
  		
		return this.createModelAndView("ticketManage/workticket/workTicketApprove", resultMap);
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
	
		//获取三种人记录
		conditions.clear();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.SIGNATURE.getId()));
//		conditions.add(new Condition("C_USER_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,starter.getId()));
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
		
		Iterator<SysUserEntity> iterator2 = userList.iterator();
		while (iterator2.hasNext()) {
			SysUserEntity sysUserEntity = iterator2.next();
				if (sysUserEntity.getId().equals(starter.getId())) {
					iterator2.remove();
			}
       }
		
		resultMap.put("userList", userList);
		return new ModelAndView("/ticketManage/workticket/sureSubmitPerson",resultMap);
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
		return new ModelAndView("/ticketManage/workticket/approveSubmitPerson",resultMap);
	}
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody ResultObj update(@RequestBody WorkTicketEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.update(t,id);
	}
	/** 批量删除
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/bulkDelete/{ids}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		workTicketService.deleteBulk(ids);
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
		        
		return new ModelAndView("/ticketManage/workticket/workInvalid",resultMap);
	}
	/**
	 * @Description:   鉴定
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@RequestMapping("/saveInvalid")
	public @ResponseBody ResultObj saveInvalid(@RequestBody WorkTicketEntity workTicketEntity, HttpServletRequest request) {
		return workTicketService.saveInvalid(workTicketEntity);
	}
	/**
	 * @Description:   验证是否执行了安全措施
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecute/{id}")
	public @ResponseBody ResultObj isExecute(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.isExecute(id);
	}
	/**
	 * @Description:   申请试运
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecuteSqsy/{id}")
	public @ResponseBody ResultObj isExecuteSqsy(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.isExecuteSqsy(id);
	}
	/**
	 * @Description:   试运恢复
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午5:17:16 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isExecuteSyhf/{id}")
	public @ResponseBody ResultObj isExecuteSyhf(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.isExecuteSyhf(id);
	}
	/**
	 * @Description:   修改时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateValidate/{id}")
	public @ResponseBody ResultObj updateValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.updateValidate(id);
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
		return workTicketService.addValidate(userEntity);
	}
	/**
	 * @Description:   单个删除时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/deleteValidate/{id}")
	public @ResponseBody ResultObj deleteValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.deleteValidate(id);
	}
	
	/**
	 * @Description:   提交时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/tijiaoValidate/{id}")
	public @ResponseBody ResultObj tijiaoValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.tijiaoValidate(id);
	}
	/**
	 * @Description:   鉴定的时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/invalidValidate/{id}")
	public @ResponseBody ResultObj invalidValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.invalidValidate(id);
	}
	/**
	 * @Description:   设置标准票时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/setValidate/{id}")
	public @ResponseBody ResultObj setValidate(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.setValidate(id);
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
		SysUserEntity userEntity= RequestContext.get().getUser();
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		Page<WorkTicketEntity> page=new Page<WorkTicketEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
		//第一种工作票
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.ELECTRICONE.getCode()));
		conditions.add(new Condition("t.C_UNIT_NAME_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,userEntity.getUnitId()));
		
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
				conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.ELECTRICONE.getCode()));
				dataList = workTicketService.findByCondition(conditions, page);
			}else {
				 dataList=workTicketService.findByCondition(conditions, page);
			}
		}
	
//		List<WorkTicketEntity> dataList=workTicketService.findByCondition(conditions, page);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		for (WorkTicketEntity workTicketEntity : dataList) {
			if(workTicketEntity.getChangeAllowDate()!=null){
				workTicketEntity.setPlandateStartString(df.format(workTicketEntity.getChangeAllowDate()));
			}
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "电气第一种工作票模板.xlsx","电气第一种工作票.xlsx", resultMap);
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
		return this.createModelAndView("ticketManage/workticket/tickeList", resultMap);
	}
	/**
	 * @Description:   签发人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:20:39 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isQfAqcs/{id}")
	public @ResponseBody ResultObj isQfAqcs(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.isQfAqcs(id);
	}
	/**
	 * @Description:   签发人是否写了安全措施  新增按钮
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:20:39 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isQfAqcsAdd/{id}")
	public @ResponseBody ResultObj isQfAqcsAdd(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.isQfAqcsAdd(id);
	}
	/**
	 * @Description:   许可人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:20:39 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isXkAqcs/{id}")
	public @ResponseBody ResultObj isXkAqcs(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.isXkAqcs(id);
	}
	/**
	 * @Description:   许可人是否写了安全措施  新增按钮
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:20:39 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/isXkAqcsAdd/{id}")
	public @ResponseBody ResultObj isXkAqcsAdd(HttpServletRequest request ,@PathVariable Long id) {
		return workTicketService.isXkAqcsAdd(id);
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
        resultMap.put("identify", identify);
        resultMap.put("qualifiedCount", qualifiedCount);
        resultMap.put("unQualifiedCount", unQualifiedCount);
              
              
        //删除垃圾数据
        if(uuid!=null){
        	WorkSafeEntity workSafeEntity=new WorkSafeEntity();
    		workSafeEntity.setUuidCode(uuid);
            workSafeService.deleteByCondition("deleteByUuid", workSafeEntity);
            ControlCardRiskEntity controlCardRiskEntity=new ControlCardRiskEntity();
    		controlCardRiskEntity.setUuidCode(uuid);
    		controlCardRiskService.deleteByCondition("deleteByUuid", controlCardRiskEntity);
        }
        resultMap.put("yearStart", yearStart);
        resultMap.put("yearEnd", yearEnd);
		return this.createModelAndView("ticketManage/workticket/workTicketListJan", resultMap);
		
	}
	@RequestMapping("/searchMon")
	public ModelAndView searchMon(HttpServletRequest request) throws Exception{
		String JanStart = request.getParameter("JanStart");
		String identify = request.getParameter("identify");
		String month = request.getParameter("month");
		String day []=month.split("-");
		String qualifiedCount = request.getParameter("qualifiedCount");
		String unQualifiedCount = request.getParameter("unQualifiedCount");
//		String yearStart=month+"-"+JanStart;
//		String yearEnd=month+"-"+JanEnd;
		Calendar timeStart = Calendar.getInstance();
		timeStart.set(Integer.parseInt(day[0]), Integer.parseInt(day[1])-1, Integer.parseInt(JanStart), 0, 0, 0);
		Calendar timeEnd = Calendar.getInstance();
		timeEnd.set(Integer.parseInt(day[0]), Integer.parseInt(day[1])-1, Integer.parseInt(JanStart), 23, 59, 59);
		String yearStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeStart.getTime());
		String yearEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeEnd.getTime());
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
		resultMap.put("identify", identify);
		resultMap.put("qualifiedCount", qualifiedCount);
		resultMap.put("unQualifiedCount", unQualifiedCount);
		
		
		//删除垃圾数据
		if(uuid!=null){
			WorkSafeEntity workSafeEntity=new WorkSafeEntity();
			workSafeEntity.setUuidCode(uuid);
			workSafeService.deleteByCondition("deleteByUuid", workSafeEntity);
			ControlCardRiskEntity controlCardRiskEntity=new ControlCardRiskEntity();
			controlCardRiskEntity.setUuidCode(uuid);
			controlCardRiskService.deleteByCondition("deleteByUuid", controlCardRiskEntity);
		}
		resultMap.put("yearStart", yearStart);
		resultMap.put("yearEnd", yearEnd);
		return this.createModelAndView("ticketManage/workticket/workTicketListJan", resultMap);
		
	}
	@RequestMapping(value = "/searchJanData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchJanData(HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
		String yearStart = request.getParameter("yearStart");
		String yearEnd = request.getParameter("yearEnd");
		String identify = request.getParameter("identify");
		String qualifiedCount = request.getParameter("qualifiedCount");
		String unQualifiedCount = request.getParameter("unQualifiedCount");
		Page<WorkTicketEntity> page =  PageUtil.getPage(params);
		  
			List<WorkTicketEntity> entities =workTicketService.searchJan(yearStart,yearEnd,params,page,identify,qualifiedCount,unQualifiedCount);
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
	@RequestMapping("/searchTicketCount_3")
	public ModelAndView searchTicketCount_3(HttpServletRequest request) throws Exception{
		String MarStart = request.getParameter("MarStart");
		String MarEnd = request.getParameter("MarEnd");
		String yearTextLocal = request.getParameter("yearTextLocal");
		String yearStart=yearTextLocal+"-"+MarStart;
		String yearEnd=yearTextLocal+"-"+MarEnd;
		String ticketType=request.getParameter("ticketType");
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
              
              
        //删除垃圾数据
        if(uuid!=null){
        	WorkSafeEntity workSafeEntity=new WorkSafeEntity();
    		workSafeEntity.setUuidCode(uuid);
            workSafeService.deleteByCondition("deleteByUuid", workSafeEntity);
            ControlCardRiskEntity controlCardRiskEntity=new ControlCardRiskEntity();
    		controlCardRiskEntity.setUuidCode(uuid);
    		controlCardRiskService.deleteByCondition("deleteByUuid", controlCardRiskEntity);
        }
        resultMap.put("yearStart", yearStart);
        resultMap.put("yearEnd", yearEnd);
        	return this.createModelAndView("ticketManage/workticket/workTicketListMar", resultMap);
		
	}
	@RequestMapping(value = "/searchMarData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchMarData(HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
		String yearStart = request.getParameter("yearStart");
		String yearEnd = request.getParameter("yearEnd");
		Page<WorkTicketEntity> page =  PageUtil.getPage(params);
		  
			List<WorkTicketEntity> entities =workTicketService.searchMar(yearStart,yearEnd,params,page);
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
	 *	运行管理跳转的详细页面
	 * @throws ParseException 
	 */
	@RequestMapping("/runDetail")
	public ModelAndView runDetail(HttpServletRequest request) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String code = request.getParameter("code");
		String rlId = request.getParameter("rlId");
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("t.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, code));
		List<WorkTicketEntity> workticketList = workTicketService.findByCondition(conditions, null);
		WorkTicketEntity WorkTicketEntity = workticketList.get(0);
		resultMap.put("workTicketId", WorkTicketEntity.getId());
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		conditions.clear();
		conditions.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditions.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTicketEntity.getId()));
		conditions.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.WORK_TICKET_PROCESS_KEY.getName()));
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
		resultMap.put("workStatus", WorkTicketEntity.getWorkStatus());
		resultMap.put("rlId", rlId);
		return this.createModelAndView("ticketManage/workticket/workTicketDetail", resultMap);
	}
	
	/**
	 * 新增
	 * @Title: add
	 * @Description:
	 * @param T
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public @ResponseBody ResultObj add(@RequestBody WorkTicketEntity WorkTicketEntity, HttpServletRequest request) {
		workTicketService.addEntity(WorkTicketEntity);
		//保存三种人数据
		WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
		workTicketUserRelEntity.setType(2);
		workTicketUserRelEntity.setWorkTicketId(WorkTicketEntity.getId());
		workTicketUserRelEntity.setUserId(WorkTicketEntity.getGuarderId());//工作负责人
		workTicketUserRelService.addEntity(workTicketUserRelEntity);
		ResultObj resultObj = new ResultObj();
		resultObj.setData(WorkTicketEntity);
		return resultObj;
	}
	
	/**
	 * 检索电气一票列表
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
		List<WorkTicketEntity> windEntities = new ArrayList<WorkTicketEntity>();
		Page<WorkTicketEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("t.C_UNIT_NAME_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,userEntity.getUnitId()));
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.ELECTRICONE.getCode()));
		
		for (String str : userNameList) {
			if (userEntity.getLoginName().equals(str)|| userEntity.getLoginName().equals("admin")||userEntity.getLoginName().equals("super")) {
				conditions.clear();
				conditions = OrmUtil.changeMapToCondition(params);
				conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
				//本身是工作票
				conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
				conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.ELECTRICONE.getCode()));
				windEntities = (List<WorkTicketEntity>)workTicketService.findByCondition(conditions, page);
			}else {
				windEntities =  (List<WorkTicketEntity>) workTicketService.findByCondition(conditions, page);
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