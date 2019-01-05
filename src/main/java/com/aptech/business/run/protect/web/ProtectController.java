package com.aptech.business.run.protect.web;

import java.io.UnsupportedEncodingException;
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

import com.aptech.business.component.dictionary.EquipVoltageEnum;
import com.aptech.business.component.dictionary.EventNotificationStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ProtectResultEnum;
import com.aptech.business.component.dictionary.ProtectStatusEnum;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.business.run.dispaCom.domain.DispaComEntity;
import com.aptech.business.run.dispaCom.service.DispaComService;
import com.aptech.business.run.protect.domain.ProtectEntity;
import com.aptech.business.run.protect.service.ProtectService;
import com.aptech.business.technical.eventAnalysis.domain.EventAnalysisEntity;
import com.aptech.common.act.service.ActProcessService;
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
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 保护投退配置控制器
 *
 * @author 
 * @created 2017-06-02 14:38:25
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/protect")
public class ProtectController extends BaseController<ProtectEntity> {
	
	@Autowired
	private ProtectService protectService;
	@Autowired
    private SysUnitService sysUnitService;
	@Autowired
    private SysUserService sysUserService;
	@Autowired
    private NodeConfigService nodeConfigService;
	@Autowired
    private DefinitionService definitionService;
	@Autowired
    private TodoTaskService todoTaskService;
	@Autowired
    private ProcessNodeAuthService processNodeAuthService;
	@Autowired
    private EquipLedgerService equipLedgerService;
	@Autowired
	private DispaComService dispaComService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private ActTaskService actTaskService;
	@Override
	public IBaseEntityOperation<ProtectEntity> getService() {
		return protectService;
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
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("protectTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboProtectVO = new ComboboxVO();
	        //TODO下拉框具体内容根据具体业务定制
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
	        for(SysUserEntity sysUserEntity : allUsers){
	            comboProtectVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	        }
		model.put("protectCombobox", JsonUtil.toJson(comboProtectVO.getOptions()));
		//编码类型
        ComboboxVO protectWayCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("PROTECT_WAY");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            protectWayCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("protectWayCombobox", JsonUtil.toJson(protectWayCombobox.getOptions()));  
        //申请类别
        ComboboxVO applyTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> applyTypeMap  =  DictionaryUtil.getDictionaries("APPLYTYPE");
        
        for(String key :  applyTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = applyTypeMap.get(key);
            applyTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("applyTypeCombobox", JsonUtil.toJson(applyTypeCombobox.getOptions()));  
        //状态
        ComboboxVO protectStatusCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> protectStatusMap  =  DictionaryUtil.getDictionaries("PROTECTSTATUS");
        
        for(String key :  protectStatusMap.keySet()){
            SysDictionaryVO sysDictionaryVO = protectStatusMap.get(key);
            protectStatusCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("protectStatusCombobox", JsonUtil.toJson(protectStatusCombobox.getOptions())); 
    	model.put("roids", userEntity.getRoleIds());
    	String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView("run/protect/protectList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门
		List<Condition> conditions = new ArrayList<Condition>();
//				conditions.add(new Condition("C_GENERATION_TYPE = 1 "));
//				conditions.add(new Condition(" C_LEVEL = 2 "));
//				conditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4  "));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
		model.put("unitNameIdTreeList", JsonUtil.toJson(companyVo.getOptions()));
		ComboboxVO comboProtectVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
	        for(SysUserEntity sysUserEntity : allUsers){
	            comboProtectVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	        }
		model.put("protectCombobox", JsonUtil.toJson(comboProtectVO.getOptions()));		
		//保护变动方式
        ComboboxVO protectWayCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("PROTECT_WAY");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            protectWayCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("protectWayCombobox", JsonUtil.toJson(protectWayCombobox.getOptions()));  
        //申请类别
        ComboboxVO applyTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> applyTypeMap  =  DictionaryUtil.getDictionaries("APPLYTYPE");
        
        for(String key :  applyTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = applyTypeMap.get(key);
            applyTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("applyTypeCombobox", JsonUtil.toJson(applyTypeCombobox.getOptions()));  
        //设备电压
        ComboboxVO equipVoltageCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> equipVoltageMap  =  DictionaryUtil.getDictionaries("EQUIPVOLTAGE");
        
        for(String key :  equipVoltageMap.keySet()){
            SysDictionaryVO sysDictionaryVO = equipVoltageMap.get(key);
            equipVoltageCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("equipVoltageCombobox", JsonUtil.toJson(equipVoltageCombobox.getOptions()));  
        model.put("giveDate", new Date());
        model.put("userEntity", userEntity);
        String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView("run/protect/protectAdd", model);
	}
	/**
	 * @throws Exception 
	 * @throws ParseException 
	* @Title: save
	* @Description: 保存操作
	* @author sunliang
	* @date 2017年6月28日上午9:31:33
	* @param t
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping(value = "/save")
    public @ResponseBody
    ResultObj save(@RequestBody ProtectEntity t, HttpServletRequest request) throws Exception{
        return protectService.add(t);
    }
	/**
	* @Title: save
	* @Description: 修改操作
	* @author sunliang
	* @date 2017年8月1日下午2:58:36
	* @param t
	* @param request
	* @return
	* @throws Exception
	* @throws
	*/
	@RequestMapping(value = "/update/{id}")
    public @ResponseBody
    ResultObj update(@RequestBody ProtectEntity t, HttpServletRequest request,@PathVariable Long id) throws Exception{
	    t.setId(id);
        return protectService.update(t);
    }
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		ProtectEntity protectEntity = (ProtectEntity)protectService.findById(id);
//		EquipLedgerEntity equipLedgerEntity=equipLedgerService.findById(new Long(protectEntity.getDeviceId()));
//		if(equipLedgerEntity!=null){
//		    protectEntity.setEquipName(equipLedgerEntity.getName());
//	        protectEntity.setEquipNumber(equipLedgerEntity.getCode());
//		}
		if(protectEntity.getDispatchCommandId()!=null){
			DispaComEntity dispaComEntity= dispaComService.findById(protectEntity.getDispatchCommandId().longValue());
//			SysUserEntity sysUser = sysUserService.findById(Long.valueOf(dispaComEntity.getDutyChiefPerson()));
//			model.put("sysUser", sysUser);
			model.put("dispaComEntity", dispaComEntity);
		}else{
			model.put("dispaComEntity", new DispaComEntity());
		}
		model.put("dataMap", protectEntity);
		model.put("dataMapJson", JsonUtil.toJson(protectEntity));
		// 部门
		List<Condition> conditions = new ArrayList<Condition>();
//		conditions.add(new Condition("C_GENERATION_TYPE = 1 "));
//		conditions.add(new Condition(" C_LEVEL = 2 "));
//		conditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4  "));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
		model.put("unitNameIdTreeList", JsonUtil.toJson(companyVo.getOptions()));
		ComboboxVO comboProtectVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
         for(SysUserEntity sysUserEntity : allUsers){
             comboProtectVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
         }
		model.put("protectCombobox", JsonUtil.toJson(comboProtectVO.getOptions()));
		//编码类型
        ComboboxVO protectWayCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("PROTECT_WAY");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            protectWayCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("protectWayCombobox", JsonUtil.toJson(protectWayCombobox.getOptions()));  
        //申请类别
        ComboboxVO applyTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> applyTypeMap  =  DictionaryUtil.getDictionaries("APPLYTYPE");
        
        for(String key :  applyTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = applyTypeMap.get(key);
            applyTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("applyTypeCombobox", JsonUtil.toJson(applyTypeCombobox.getOptions()));  
        //设备电压
        ComboboxVO equipVoltageCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> equipVoltageMap  =  DictionaryUtil.getDictionaries("EQUIPVOLTAGE");
        
        for(String key :  equipVoltageMap.keySet()){
            SysDictionaryVO sysDictionaryVO = equipVoltageMap.get(key);
            equipVoltageCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("equipVoltageCombobox", JsonUtil.toJson(equipVoltageCombobox.getOptions()));
        //获取关联设备
        String equipId = protectEntity.getDeviceId();
        List<EquipLedgerEntity> list = new ArrayList<EquipLedgerEntity>();
        if(equipId.contains(",")){
        	String[] equipIdArray = equipId.split("\\,");
        	for(String equipid:equipIdArray){
        		EquipLedgerEntity entity  =  equipLedgerService.findById(Long.parseLong(equipid));
        		list.add(entity);
        	}
        }
    	model.put("userUnitRels", JsonUtil.toJson(list));
        //申请类别
        model.put("id", id);
		return this.createModelAndView("run/protect/protectEdit", model);
	}
	  /**
	* @Title: submitPerson
	* @Description: 提交审批人
	* @author sunliang
	* @date 2017年7月31日下午1:26:33
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping("/submitPerson/{id}")
	    public  ModelAndView submitPerson(HttpServletRequest request, @PathVariable Long id) {
	        Map<String, Object> model = new HashMap<String, Object>();
	        List<Condition> conditions=new ArrayList<Condition>();
	        conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.PROTECT_PROCESS_KEY.getName()));
	        List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
	        String modelId="";
	        if(!defList.isEmpty()){
	            modelId=defList.get(0).getModelId();
	        }
	        //审批页面点击签发按钮的时候，把下一步的人查询出来
	        SysUserEntity userEntity = RequestContext.get().getUser();
	        ProtectEntity protectEntity = protectService.findById(Long.valueOf(id));
	        List<SysUserEntity> userList = new ArrayList<SysUserEntity>();
	        if(protectEntity.getApplyType()==1){
	        	userList= nodeConfigService.getFirstNodeTransactor(modelId,ProtectResultEnum.AGREE.getId().toString(),userEntity);
	        }else{
	        	userList= nodeConfigService.getFirstNodeTransactor(modelId,ProtectResultEnum.BACK_END.getId().toString(),userEntity);
	        }
	        request.setAttribute("userList", userList);
	        return this.createModelAndView("run/protect/submitPerson", model);
	    }
    /**
    * @Title: sureSubmitPerson
    * @Description: 选择审批人
    * @author sunliang
    * @date 2017年6月29日下午4:37:56
    * @param request
    * @return
    * @throws
    */
    @RequestMapping("/sureSubmitPerson")
    public ModelAndView sureSubmitPerson(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.PROTECT_PROCESS_KEY.getName()));
        List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
        String modelId="";
        if(!defList.isEmpty()){
            modelId=defList.get(0).getModelId();
        }
        //审批页面点击签发按钮的时候，把下一步的人查询出来
        SysUserEntity userEntity = RequestContext.get().getUser();
        List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
        request.setAttribute("userList", userList);
        return this.createModelAndView("run/protect/sureSubmitPerson", model);
    }
	/**
     *  提交审批
     */
    @RequestMapping("/toCheck/{id}")
    public @ResponseBody ResultObj toCheck(HttpServletRequest request, @PathVariable Long id,@RequestBody Map<String, Object> params){
        ResultObj resultObj = new ResultObj();
        protectService.tocheck(id,params);
        return resultObj;
    }
    /**
    * @Title: againSubmit
    * @Description: 再提交
    * @author sunliang
    * @date 2017年8月16日上午11:57:14
    * @param request
    * @return
    * @throws
    */
    @RequestMapping("/againSubmit/{id}")
    public @ResponseBody ResultObj againSubmit(@RequestBody ProtectEntity t){
            Map<String, Object> variables=new HashMap<String, Object>();
            ProtectEntity protectEntity=protectService.findById(t.getId());
            if(protectEntity.getApplyType()!=null&&String.valueOf(protectEntity.getApplyType()).equals("0")){
            	variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
            	variables.put("status", ProtectStatusEnum.SELEXECUTE.getCode());
            }else{
        	    variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
           		variables.put("status", ProtectStatusEnum.ZZCHECK.getCode());
            }
            variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
            variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
            variables.put(ExamMarkEnum.COMMENT.getCode(), t.getApproveIdea()==null?"":t.getApproveIdea());           
            return protectService.check(t,variables);
    }
    /**
     *  跳转到审批页面
     */
    @RequestMapping("/approve/{id}/{type}")
    public ModelAndView approve(HttpServletRequest request, @PathVariable Long id,@PathVariable String type){
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("type", type);
        // 返回前台数据项
        ProtectEntity protectEntity = (ProtectEntity)protectService.findById(id);
        SysUnitEntity sysUnitEntity=sysUnitService.findById((long)protectEntity.getUnitId());
        protectEntity.setUnitName(sysUnitEntity.getName());
        model.put("dataMap", protectEntity);
        model.put("dataMapJson", JsonUtil.toJson(protectEntity));
        if(protectEntity.getDispatchCommandId()!=null){
			DispaComEntity dispaComEntity= dispaComService.findById(protectEntity.getDispatchCommandId().longValue());
			model.put("dispaComEntity", dispaComEntity);
		}else{
			model.put("dispaComEntity", new DispaComEntity());
		}
        //TODO下拉树具体内容根据具体业务定制
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();

        model.put("protectTreeList", JsonUtil.toJson(treeNodeList));
        ComboboxVO comboWorkPlanVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
        List<SysUserEntity> allUsers = sysUserService.findAll();
        for(SysUserEntity sysUserEntity : allUsers){
            comboWorkPlanVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("protectCombobox", JsonUtil.toJson(comboWorkPlanVO.getOptions()));
        //编码类型
        ComboboxVO protectWayCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("PROTECT_WAY");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            protectWayCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("protectWayCombobox", JsonUtil.toJson(protectWayCombobox.getOptions()));  
      //查询各个人的按钮权限 开始
        SysUserEntity userEntity= RequestContext.get().getUser();
        List<Condition> conditionsLc=new ArrayList<Condition>();
        conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
        conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
        conditionsLc.add(new Condition("task.end_time_ IS NULL"));
        List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
        TodoTaskEntity todoTaskEntity=null;
        if(!list.isEmpty()){
            todoTaskEntity=list.get(0);
            List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
            model.put("nodeList", nodeList);
        }
        //申请类别
        ComboboxVO applyTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> applyTypeMap  =  DictionaryUtil.getDictionaries("APPLYTYPE");
        
        for(String key :  applyTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = applyTypeMap.get(key);
            applyTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("applyTypeCombobox", JsonUtil.toJson(applyTypeCombobox.getOptions()));  
        //设备电压
        ComboboxVO equipVoltageCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> equipVoltageMap  =  DictionaryUtil.getDictionaries("EQUIPVOLTAGE");
        
        for(String key :  equipVoltageMap.keySet()){
            SysDictionaryVO sysDictionaryVO = equipVoltageMap.get(key);
            equipVoltageCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("equipVoltageCombobox", JsonUtil.toJson(equipVoltageCombobox.getOptions()));
        //查询各个人的按钮权限 结束
        return this.createModelAndView("run/protect/protectCheck", model);
    }
   
    /**
    * @Title: 查询审批人
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @author sunliang
    * @param request
    * @param taskId
    * @return
    * @throws
    */
    @RequestMapping("/submitPersonAgree/{taskId}")
    public ModelAndView submitPersonAgree(HttpServletRequest request,@PathVariable String taskId) {
        //审批页面点击签发按钮的时候，把下一步的人查询出来
        Map<String, Object> model = new HashMap<String, Object>();
        List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ProtectResultEnum.THIRTYFIVE.getId().toString());
        model.put("userList", userList);
        return this.createModelAndView("run/protect/sureSubmitPerson", model);
    }
    /**
     * @Title: 查询审批人
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author sunliang
     * @param request
     * @param taskId
     * @return
     * @throws
     */
     @RequestMapping("/submitPersonAgreeZZ")
     public ModelAndView submitPersonAgreeZZ(HttpServletRequest request) {
         //审批页面点击签发按钮的时候，把下一步的人查询出来
         Map<String, Object> model = new HashMap<String, Object>();
         String taskId = request.getParameter("taskId");
         String id = request.getParameter("id");
         ProtectEntity protectEntity = protectService.findById(Long.valueOf(id));
         List<SysUserEntity> userList = new ArrayList<SysUserEntity>();
         if(protectEntity.getEquipVoltage()==110){
        	 userList=nodeConfigService.getNextNodeTransactor(taskId,ProtectResultEnum.ONEONEZEROKV.getId().toString());
         }else{
        	 userList=nodeConfigService.getNextNodeTransactor(taskId,ProtectResultEnum.AGREE.getId().toString()); 
         }
         
         model.put("userList", userList);
         return this.createModelAndView("run/protect/sureSubmitPerson", model);
     }
    /**
     *  值长审批通过(选择执行人)
     */
    @RequestMapping("/pass/{id}")
    public @ResponseBody
    ResultObj Pass(@RequestBody ProtectEntity t) {
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("status", ProtectStatusEnum.SELEXECUTE.getCode());
        params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
        params.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.AGREE.getId());
        params.put(ExamMarkEnum.RESULT.getCode(), t.getApproveIdea()==null?"":t.getApproveIdea());
        params.put(ExamMarkEnum.COMMENT.getCode(), "");
        return protectService.check(t,params);
    }
    /**
     *  值长审批不通过
     */
    @RequestMapping("/noPass/{id}")
    public @ResponseBody ResultObj noPass(@RequestBody ProtectEntity t){
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("status", ProtectStatusEnum.ZZREJECT.getCode());
        SysUserEntity fzrEntity=sysUserService.findById(new Long(t.getApplyPersonId()));
        params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
        params.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.BACK_END.getId());
        params.put(ExamMarkEnum.RESULT.getCode(), ProtectResultEnum.BACK_END.getName());
        params.put(ExamMarkEnum.COMMENT.getCode(), "");
        return  protectService.check(t, params);
    }
    /**
     *  主任审批通过(选择执行人)
     */
    @RequestMapping("/directorPass/{id}")
    public @ResponseBody ResultObj directorPass(@RequestBody ProtectEntity t){
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("status", ProtectStatusEnum.SELEXECUTE.getCode());
        params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
        params.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.FINALAGREE.getId());
        params.put(ExamMarkEnum.RESULT.getCode(), ProtectResultEnum.FINALAGREE.getName());
        params.put(ExamMarkEnum.COMMENT.getCode(), "");
        return  protectService.check(t, params);
    }
    /**
     *  主任审批不通过 
     */
    @RequestMapping("/directorNoPass/{id}")
    public @ResponseBody ResultObj directorNoPass(@RequestBody ProtectEntity t){
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("status", ProtectStatusEnum.ZRREJECT.getCode());
        SysUserEntity fzrEntity=sysUserService.findById(new Long(t.getApplyPersonId()));
        params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
        params.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.BACK_END.getId());
        params.put(ExamMarkEnum.RESULT.getCode(), ProtectResultEnum.BACK_END.getName());
        params.put(ExamMarkEnum.COMMENT.getCode(), "");
        return  protectService.check(t, params);
    }
    /**
     *  值长审批通过（进入下级审批）
     */
    @RequestMapping("/tempPass/{id}")
    public @ResponseBody ResultObj tempPass(@RequestBody ProtectEntity 	t){
        ProtectEntity tEntity = protectService.findById(t.getId());
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("status", ProtectStatusEnum.ZRCHECK.getCode());
        params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
        if(tEntity!=null&&tEntity.getEquipVoltage()!=null&&
        		String.valueOf(tEntity.getEquipVoltage()).equals(EquipVoltageEnum.KV35.getName())){
        	params.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.THIRTYFIVE.getId());
        }else{
        	
        	params.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.ONEONEZEROKV.getId());
        }
        params.put(ExamMarkEnum.RESULT.getCode(), ProtectResultEnum.AGREE.getName());
        params.put(ExamMarkEnum.COMMENT.getCode(), "");
        return  protectService.check(t, params);
    }
    /**
     *  主任审批通过 （进入下级审批）
     */
    @RequestMapping("/directortempPass/{id}")
    public @ResponseBody ResultObj directortempPass(@RequestBody ProtectEntity t){
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("status", ProtectStatusEnum.SELEXECUTE.getCode());
        params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
        params.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.AGREE.getId());
        params.put(ExamMarkEnum.RESULT.getCode(), ProtectResultEnum.AGREE.getName());
        params.put(ExamMarkEnum.COMMENT.getCode(), "");
        return  protectService.check(t, params);
    }
    /**
     *  主管审批不通过 
     */
    @RequestMapping("/zgnoPass/{id}")
    public @ResponseBody ResultObj zgnoPass(@RequestBody ProtectEntity t){
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("status", ProtectStatusEnum.SELEXECUTE.getCode());
        SysUserEntity fzrEntity=sysUserService.findById(new Long(t.getApplyPersonId()));
        params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
        params.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.BACK_END.getId());
        params.put(ExamMarkEnum.RESULT.getCode(), ProtectResultEnum.BACK_END.getName());
        params.put(ExamMarkEnum.COMMENT.getCode(), "");
        return  protectService.check(t, params);
    }
    /**
     *  主管审批通过(选择执行人)
     */
    @RequestMapping("/zgPass/{id}")
    public @ResponseBody ResultObj zgPass(@RequestBody ProtectEntity t){
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("status", ProtectStatusEnum.SELEXECUTE.getCode());
        params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
        params.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.AGREE.getId());
        params.put(ExamMarkEnum.RESULT.getCode(), ProtectResultEnum.AGREE.getName());
        params.put(ExamMarkEnum.COMMENT.getCode(), "");
        return  protectService.check(t, params);
    }
    /**
     *  跳转到选择执行人
     */
    @RequestMapping("/confirmView/{taskId}")
    public ModelAndView confirmView(HttpServletRequest request,@PathVariable String taskId){
        Map<String, Object> model = new HashMap<String, Object>();
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
        //TODO下拉树具体内容根据具体业务定制
        model.put("protectTreeList", JsonUtil.toJson(treeNodeList));
        ComboboxVO comboProtectVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
         List<SysUserEntity> allUsers = sysUserService.findAll();
            for(SysUserEntity sysUserEntity : allUsers){
                comboProtectVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
            }
        model.put("protectCombobox", JsonUtil.toJson(comboProtectVO.getOptions()));  
        List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ProtectResultEnum.AGREE.getId().toString());
        model.put("userList", userList);
        return this.createModelAndView("run/protect/protectConfirm", model);
    }
    /**
     *  跳转到执行界面
     */
    @RequestMapping("/executeView")
    public ModelAndView executeView(HttpServletRequest request){
        Map<String, Object> model = new HashMap<String, Object>();
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
        //TODO下拉树具体内容根据具体业务定制
        model.put("protectTreeList", JsonUtil.toJson(treeNodeList));
        ComboboxVO comboProtectVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
         List<SysUserEntity> allUsers = sysUserService.findAll();
            for(SysUserEntity sysUserEntity : allUsers){
                comboProtectVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
            }
        model.put("protectCombobox", JsonUtil.toJson(comboProtectVO.getOptions()));     
        return this.createModelAndView("run/protect/protectExecute", model);
    }
    /**
    * @Title: zzConfirm
    * @Description: 确定执行人
    * @author sunliang
    * @date 下午5:04:21
    * @param t
    * @param request
    * @return
    * @throws
    */
    @RequestMapping(value = "/zzConfirm")
    public @ResponseBody ResultObj zzConfirm(HttpServletRequest request,@RequestBody ProtectEntity t) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("status", ProtectStatusEnum.EXECUTE.getCode());
        String selectUser=request.getParameter("selectUser");
      //  SysUserEntity sysUser=sysUserService.findById(Long.parseLong(t.getUserList()));
        variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
        variables.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.AGREE.getId());
        variables.put(ExamMarkEnum.RESULT.getCode(), ProtectResultEnum.AGREE.getName());
        variables.put(ExamMarkEnum.COMMENT.getCode(), "");
        ProtectEntity protectEntity = protectService.findById(t.getId());
        List<SysUserEntity> copyUserList=nodeConfigService.getNextNodeCopyTransactor(t.getTaskId(), ExamResultEnum.AGREE.getId().toString());
		String copyUser = "";
		String setCopyUserIds = "";
		if (copyUserList != null && copyUserList.size() > 0) {
			for (int i = 0;i<copyUserList.size(); i++) {
				SysUserEntity sysUserEntity  = copyUserList.get(i);
				copyUser += sysUserEntity.getLoginName();
				setCopyUserIds += sysUserEntity.getId().toString();
				if (i != copyUserList.size() -1) {
					copyUser += ",";
					setCopyUserIds += ",";
				}
			} 
		}
		
		protectEntity.setCopyUserIds(setCopyUserIds);
		//抄送跳转
		if (copyUser.length() > 0) {
			variables.put(CandidateMarkEnum.COPY_HANDLERS.getName(), copyUser);
		}
		protectEntity.setCheckState(ProtectStatusEnum.EXECUTE.getCode());
		actTaskService.complete(t.getTaskId(),t.getProcInstId(), variables);
		protectService.updateEntity(protectEntity);
        return new ResultObj();
    }
    /**
    * @Title: zxrExecute
    * @Description: 执行人执行
    * @author sunliang
    * @date 下午5:07:31
    * @param t
    * @return
    * @throws
    */
    @RequestMapping(value = "/zxrExecute")
    public @ResponseBody ResultObj zxrExecute(@RequestBody ProtectEntity t) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", ProtectStatusEnum.FINISH.getCode());
        params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
        params.put(BranchMarkEnum.BRANCH_KEY.getName(), ProtectResultEnum.AGREE.getId());
        params.put(ExamMarkEnum.RESULT.getCode(), ProtectResultEnum.AGREE.getName());
        params.put(ExamMarkEnum.COMMENT.getCode(), "");
        //调用群发消息接口
//       List<String> receivePersonList=actProcessService.getAllUsersByProcessInstanceId(t.getId().toString(), ProcessMarkEnum.PROTECT_PROCESS_KEY.getName());
//       String receivePersons="";
//       for (String receivePerson :receivePersonList) {
//    	   receivePersons+=receivePerson+",";
//        }
//       MessageCenterEntity messageEntity =new MessageCenterEntity();
//       messageEntity.setTitle("保护投退已执行");
//       ProtectEntity protectEntity=protectService.findById(t.getId());
//       messageEntity.setContext("设备"+protectEntity.getEquipName()+protectEntity.getProtectWayName()+"已执行");
//       messageEntity.setSendTime(new Date());
//       messageEntity.setReceivePerson(receivePersons);
//       SysUserEntity sysUserEntity = RequestContext.get().getUser();
//       messageEntity.setSendPerson(sysUserEntity.getId().toString());
//       messageEntity.setType("private");
//       messageCenterService.addMessage(messageEntity);
        ProtectEntity protectEntity = protectService.findById(t.getId());
		String setCopyUserIds = protectEntity.getCopyUserIds();
		if (setCopyUserIds != null && setCopyUserIds.length()> 0) {
			String [] uids = setCopyUserIds.split(",");
			for (String uid: uids) {
				MessageCenterEntity messageEntity =new MessageCenterEntity();
				messageEntity.setTitle("保护投退已执行");
				messageEntity.setSendTime(new Date());
				messageEntity.setReceivePerson(uid);
				messageEntity.setSendPerson(uid);
				messageEntity.setContext("<a href='#' onclick='goOverCopyTaskList()'>" +"设备"+protectEntity.getEquipName()+protectEntity.getProtectWayName()+  "已执行</a>");
				messageEntity.setType("private");
				messageCenterService.addMessage(messageEntity); 
			} 
		}
        return protectService.checkAndUpdate(t,params);
    }
    /**
    * @Title: disAgreeFp
    * @Description: 流程取消
    * @author sunliang
    * @date 2017年8月8日上午11:37:52
    * @param request
    * @param workEarthEntity
    * @return
    * @throws
    */
    @RequestMapping("/cancel/{id}")
    public @ResponseBody ResultObj cancel(HttpServletRequest request,@RequestBody ProtectEntity t){
        ResultObj resultObj = new ResultObj();
        try {
            String selectUser=request.getParameter("selectUser");
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put("status", ProtectStatusEnum.CANCEL.getCode());            
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),t.getApproveIdea()==null?"":t.getApproveIdea());//审批意见
            protectService.check(t,taskVariables);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
    /**
    * @Title: getDetail
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @author sunliang
    * @date 2017年6月27日下午5:26:24
    * @param request
    * @param id
    * @return
    * @throws
    */
    @RequestMapping("/getDetail/{id}")
    public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id){
        Map<String, Object> model = new HashMap<String, Object>();
        // 返回前台数据项
        ProtectEntity protectEntity = (ProtectEntity)protectService.findById(id);
        SysUnitEntity sysUnitEntity=sysUnitService.findById((long)protectEntity.getUnitId());
        protectEntity.setUnitName(sysUnitEntity.getName());
        model.put("dataMap", protectEntity);
        model.put("dataMapJson", JsonUtil.toJson(protectEntity));
        if(protectEntity.getDispatchCommandId()!=null){
			DispaComEntity dispaComEntity= dispaComService.findById(protectEntity.getDispatchCommandId().longValue());
			model.put("dispaComEntity", dispaComEntity);
//			SysUserEntity sysUser = sysUserService.findById(Long.valueOf(dispaComEntity.getDutyChiefPerson()));
//			model.put("sysUser", sysUser);
		}else{
			model.put("dispaComEntity", new DispaComEntity());
		}
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
        //TODO下拉树具体内容根据具体业务定制
        model.put("protectTreeList", JsonUtil.toJson(treeNodeList));
        ComboboxVO comboProtectVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
         List<SysUserEntity> allUsers = sysUserService.findAll();
         for(SysUserEntity sysUserEntity : allUsers){
             comboProtectVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
         }
        model.put("protectCombobox", JsonUtil.toJson(comboProtectVO.getOptions()));
        //编码类型
        ComboboxVO protectWayCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("PROTECT_WAY");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            protectWayCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
      //申请类别
        ComboboxVO applyTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> applyTypeMap  =  DictionaryUtil.getDictionaries("APPLYTYPE");
        
        for(String key :  applyTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = applyTypeMap.get(key);
            applyTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("applyTypeCombobox", JsonUtil.toJson(applyTypeCombobox.getOptions()));  
        //设备电压
        ComboboxVO equipVoltageCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> equipVoltageMap  =  DictionaryUtil.getDictionaries("EQUIPVOLTAGE");
        
        for(String key :  equipVoltageMap.keySet()){
            SysDictionaryVO sysDictionaryVO = equipVoltageMap.get(key);
            equipVoltageCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("equipVoltageCombobox", JsonUtil.toJson(equipVoltageCombobox.getOptions()));
        model.put("protectWayCombobox", JsonUtil.toJson(protectWayCombobox.getOptions()));          
        return this.createModelAndView("run/protect/protectDetail", model);
    }
    /**
    * @Title: expData
    * @Description: excel导出
    * @author sunliang
    * @date 2017年7月20日下午4:13:13
    * @param req
    * @param res
    * @throws UnsupportedEncodingException
    * @throws
    */
    @RequestMapping("/exportExcel")
    public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
//        List<Condition> conditions=new ArrayList<Condition>();
//        conditions.add(new Condition("C_FLAG", FieldTypeEnum.STRING,
//				MatchTypeEnum.EQ, "1"));
        String conditions=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditions));
        List<ProtectEntity> protectList=protectService.findByCondition(params, null);
        DateFormatUtil sf = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
        for (ProtectEntity protectEntity:protectList) {
        	if(protectEntity.getExecuteEndtime()!=null)
        	protectEntity.setExecuteEndtimeString(sf.format(protectEntity.getExecuteEndtime()));
        	if(protectEntity.getExecuteEndtime()!=null)
        	protectEntity.setApplyTimeString(sf.format(protectEntity.getApplyTime()));
		}
        Map<String,Object> resultMap=new HashMap<String, Object>();
        resultMap.put("dataList", protectList);
        ExcelUtil.export(req, res, "保护投退报表模板.xlsx","保护投退.xlsx", resultMap);
    }
    /**
    * @Title: batchDelete
    * @Description: 批量删除
    * @author sunliang
    * @date 2017年8月3日下午3:07:08
    * @param ids
    * @return
    * @throws Exception
    * @throws
    */
    @RequestMapping(value = "/batchDelete/{role}")
    public @ResponseBody ResultObj batchDelete(@RequestBody List<Integer> ids
    		,@PathVariable String role)throws Exception {
        ResultObj resultObj = new ResultObj();
        for (Integer id : ids) {
            long longId = (long) id;
            ProtectEntity t = protectService.findById(longId);
            if (t != null) {                
                    protectService.delete(longId, role);              
            }
        }
        return resultObj;
    }
}