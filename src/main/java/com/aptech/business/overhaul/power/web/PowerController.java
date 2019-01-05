package com.aptech.business.overhaul.power.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.Istypicaleum;
import com.aptech.business.component.dictionary.PowerStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
import com.aptech.business.overhaul.power.domain.PowerEntity;
import com.aptech.business.overhaul.power.service.PowerService;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.common.act.service.ActProcessService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.duties.service.SysDutiesService;
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
 * 停送电管理配置控制器
 *
 * @author 
 * @created 2017-07-31 09:55:19
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/power")
public class PowerController extends BaseController<PowerEntity> {
	
	@Autowired
	private PowerService powerService;
	
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<PowerEntity> getService() {
		return powerService;
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
		List<PowerEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("powerTreeList", JsonUtil.toJson(treeNodeList));
		SysUserEntity userEntity= RequestContext.get().getUser();

		// 单位名称下拉树
//		List<SysUnitEntity> unitNameIdTreeList = sysUnitService.getUnitTreeNodeList();
		
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		conditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitNameIdTreeList = sysUnitService.findByCondition(conditions, null);
		model.put("unitNameIdTreeList", JsonUtil.toJson(unitNameIdTreeList));
		
		List<Condition> userconditions = new ArrayList<Condition>();
//		conditions.add(new Condition("C_USER_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userEntity.getId()));
//		List<UserUnitRelEntity> list = userUnitRelService.findByCondition(conditions, null);
//		if(list!=null){
//			Long[] funcids = new Long[list.size()];
//			for (int i = 0; i < list.size(); i++) {
//				funcids[i] = list.get(i).getUnitId();
//			}
//			conditions.clear();
//			conditions.add(new Condition("id",FieldTypeEnum.LONG,MatchTypeEnum.IN,funcids));
//			userconditions.add(new Condition("unitId",FieldTypeEnum.LONG,MatchTypeEnum.IN,funcids));
//		}
		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);

		//获取停送电方式
		Map<String, SysDictionaryVO> powerTypeMap  =  DictionaryUtil.getDictionaries("POWER_TYPE");
		ComboboxVO powerTypeVO = new ComboboxVO();
		for(String key : powerTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = powerTypeMap.get(key);
			powerTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("powerTypes", JsonUtil.toJson(powerTypeVO.getOptions()));
		
		//申请人下拉列表
		ComboboxVO requestUserVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
        for(SysUserEntity sysUserEntity : allUsers){
        	requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
		//设备名称下拉树
		
        //申请人下拉列表
        ComboboxVO equipLedgerComboboxVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
        List<EquipLedgerEntity> allEquipLedgerS = equipLedgerService.findAll();
        for(EquipLedgerEntity equipLedgerSVO : allEquipLedgerS){
        	equipLedgerComboboxVO.addOption(equipLedgerSVO.getCode(), equipLedgerSVO.getName());
        }
        model.put("equipLedgers", JsonUtil.toJson(equipLedgerComboboxVO.getOptions()));
        //设备名称下拉树
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("POWER_STATUS");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("approveStatus", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        
      //获取登录人信息
  		SysUserEntity sysUserEntity = RequestContext.get().getUser();
  		model.put("sysUserId", JsonUtil.toJson(sysUserEntity.getId()));
  		model.put("loginName", JsonUtil.toJson(sysUserEntity.getLoginName()));
  		
  		model.put("dutyName",JsonUtil.toJson(sysUserEntity.getDutyName()) );
  		
        
		return this.createModelAndView("overhaul/power/powerList", model);
	}
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		SysUserEntity userEntity= RequestContext.get().getUser();

		// 单位名称下拉树
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		conditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
     
		/* List<Condition> userconditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_USER_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userEntity.getId()));
		List<UserUnitRelEntity> list = userUnitRelService.findByCondition(conditions, null);
		if(list!=null){
			Long[] funcids = new Long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				funcids[i] = list.get(i).getUnitId();
			}
			conditions.clear();
			conditions.add(new Condition("id",FieldTypeEnum.LONG,MatchTypeEnum.IN,funcids));
		}
		List<SysUnitEntity> unitNameIdTreeList = sysUnitService.findByCondition(conditions, null);
		userconditions.add(new Condition("unitId",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userEntity.getUnitId()));
		userconditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
        List<SysUserEntity> allUsers = sysUserService.findByCondition(userconditions, null);
		model.put("unitNameIdTreeList", JsonUtil.toJson(unitNameIdTreeList));*/
		
		List<SysUnitEntity> unitNameIdTreeList = sysUnitService.findByCondition(conditions, null);
		model.put("unitNameIdTreeList", JsonUtil.toJson(unitNameIdTreeList));
		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);

 		//申请人下拉列表
		ComboboxVO requestUserVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
        for(SysUserEntity sysUserEntity : allUsers){
        	requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
		
		//获取停送电方式
		Map<String, SysDictionaryVO> powerTypeMap  =  DictionaryUtil.getDictionaries("POWER_TYPE");
		ComboboxVO powerTypeVO = new ComboboxVO();
		for(String key : powerTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = powerTypeMap.get(key);
			powerTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("powerTypes", JsonUtil.toJson(powerTypeVO.getOptions()));
		
		//是否停送电
		Map<String, SysDictionaryVO> powerMap  =  DictionaryUtil.getDictionaries("IS_POWER");
		ComboboxVO powerVO = new ComboboxVO();
		for(String key : powerMap.keySet()){
			SysDictionaryVO sysDictionaryVO = powerMap.get(key);
			powerVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("isPower", JsonUtil.toJson(powerVO.getOptions()));
		
		// 电压等级
		Map<String, SysDictionaryVO> idTypeMap  =  DictionaryUtil.getDictionaries("POWER_LEVEL");
        List<SysDictionaryVO> dicVo=new ArrayList<SysDictionaryVO>();
        for(String key :  idTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = idTypeMap.get(key);
        	dicVo.add(sysDictionaryVO);
        }
        model.put("levelList", dicVo);
		
        //当前用户
		model.put("userEntity", userEntity);
		SysUnitEntity unitEntity = sysUnitService.findById(userEntity.getUnitId());
		model.put("unitEntity", unitEntity);
		String requestNumber = IdUtil.creatUUID();
		model.put("requestNumber", requestNumber);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		model.put("date", df.format(new Date()));
		String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView("overhaul/power/powerAdd", model);
	}
	
	/**
	 * 新增保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody
	ResultObj save(@RequestBody PowerEntity t, HttpServletRequest request) {
		return powerService.add(t);
	}
	
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody
	ResultObj update(@RequestBody PowerEntity t, HttpServletRequest request, @PathVariable Long id) {
		return powerService.update(t);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request,
			@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		PowerEntity powerEntity = (PowerEntity) powerService.findById(id);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("entityJson", JsonUtil.toJson(powerEntity));
		model.put("entity", powerEntity);
		// 单位名称下拉树
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		conditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitNameIdTreeList = sysUnitService.findByCondition(conditions, null);
		model.put("unitNameIdTreeList", JsonUtil.toJson(unitNameIdTreeList));
		
		List<Condition> userconditions = new ArrayList<Condition>();
		/*conditions.add(new Condition("C_USER_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userEntity.getId()));
		List<UserUnitRelEntity> list = userUnitRelService.findByCondition(conditions, null);
		if(list!=null){
			Long[] funcids = new Long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				funcids[i] = list.get(i).getUnitId();
			}
			conditions.clear();
			conditions.add(new Condition("id",FieldTypeEnum.LONG,MatchTypeEnum.IN,funcids));
		}*/
		userconditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		userconditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
        List<SysUserEntity> allUsers = sysUserService.findByCondition(userconditions, null);
        // 申请人下拉列表
 		ComboboxVO requestUserVO = new ComboboxVO();
 		for (SysUserEntity sysUserEntity : allUsers) {
 			requestUserVO.addOption(sysUserEntity.getId().toString(),
 					sysUserEntity.getName());
 		}
 		model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));

		// 获取停送电方式
		Map<String, SysDictionaryVO> powerTypeMap = DictionaryUtil.getDictionaries("POWER_TYPE");
		ComboboxVO powerTypeVO = new ComboboxVO();
		for (String key : powerTypeMap.keySet()) {
			SysDictionaryVO sysDictionaryVO = powerTypeMap.get(key);
			powerTypeVO.addOption(sysDictionaryVO.getCode(),sysDictionaryVO.getName());
		}
		model.put("powerTypes", JsonUtil.toJson(powerTypeVO.getOptions()));

		//是否停送电
		Map<String, SysDictionaryVO> powerMap  =  DictionaryUtil.getDictionaries("IS_POWER");
		ComboboxVO powerVO = new ComboboxVO();
		for(String key : powerMap.keySet()){
			SysDictionaryVO sysDictionaryVO = powerMap.get(key);
			powerVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("isPower", JsonUtil.toJson(powerVO.getOptions()));
		
	     // 电压等级
		Map<String, SysDictionaryVO> idTypeMap  =  DictionaryUtil.getDictionaries("POWER_LEVEL");
	     List<SysDictionaryVO> dicVo=new ArrayList<SysDictionaryVO>();
	     for(String key :  idTypeMap.keySet()){
	     	SysDictionaryVO sysDictionaryVO = idTypeMap.get(key);
	     	dicVo.add(sysDictionaryVO);
	     }
	     model.put("levelList", dicVo);
		

		return this.createModelAndView("overhaul/power/powerEdit", model);
	}
	
	/**
	 *	停送电管理列表跳转到修改页面
	 */
	@RequestMapping("/getShowEdit/{id}")
	public ModelAndView getShowEditPage(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		PowerEntity powerEntity = (PowerEntity) powerService.findById(id);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("entityJson", JsonUtil.toJson(powerEntity));
		model.put("entity", powerEntity);
		// 单位名称下拉树
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		conditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitNameIdTreeList = sysUnitService.findByCondition(conditions, null);
		model.put("unitNameIdTreeList", JsonUtil.toJson(unitNameIdTreeList));
		
		List<Condition> userconditions = new ArrayList<Condition>();
		userconditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		userconditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<SysUserEntity> allUsers = sysUserService.findByCondition(userconditions, null);
		// 申请人下拉列表
		ComboboxVO requestUserVO = new ComboboxVO();
		for (SysUserEntity sysUserEntity : allUsers) {
			requestUserVO.addOption(sysUserEntity.getId().toString(),
					sysUserEntity.getName());
		}
		model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
		
		// 获取停送电方式
		Map<String, SysDictionaryVO> powerTypeMap = DictionaryUtil.getDictionaries("POWER_TYPE");
		ComboboxVO powerTypeVO = new ComboboxVO();
		for (String key : powerTypeMap.keySet()) {
			SysDictionaryVO sysDictionaryVO = powerTypeMap.get(key);
			powerTypeVO.addOption(sysDictionaryVO.getCode(),sysDictionaryVO.getName());
		}
		model.put("powerTypes", JsonUtil.toJson(powerTypeVO.getOptions()));
		
		//是否停送电
		Map<String, SysDictionaryVO> powerMap  =  DictionaryUtil.getDictionaries("IS_POWER");
		ComboboxVO powerVO = new ComboboxVO();
		for(String key : powerMap.keySet()){
			SysDictionaryVO sysDictionaryVO = powerMap.get(key);
			powerVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("isPower", JsonUtil.toJson(powerVO.getOptions()));
		
		// 电压等级
		Map<String, SysDictionaryVO> idTypeMap  =  DictionaryUtil.getDictionaries("POWER_LEVEL");
		List<SysDictionaryVO> dicVo=new ArrayList<SysDictionaryVO>();
		for(String key :  idTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = idTypeMap.get(key);
			dicVo.add(sysDictionaryVO);
		}
		model.put("levelList", dicVo);
		
		
		return this.createModelAndView("overhaul/power/powerShowEdit", model);
	}
	
	/**
	 * 跳转到查看页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request,
			@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		PowerEntity powerEntity = (PowerEntity) powerService.findById(id);
		model.put("entity", powerEntity);
		
		// 电压等级
		Map<String, SysDictionaryVO> idTypeMap  =  DictionaryUtil.getDictionaries("POWER_LEVEL");
        List<SysDictionaryVO> dicVo=new ArrayList<SysDictionaryVO>();
        for(String key :  idTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = idTypeMap.get(key);
        	dicVo.add(sysDictionaryVO);
        }
        model.put("levelList", dicVo);
		return this.createModelAndView("overhaul/power/powerDetail", model);
	}
	
	
	/**
	 * 停送电管理列表跳转到查看页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/getShowDetail/{id}")
	public ModelAndView getShowDetail(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		PowerEntity powerEntity = (PowerEntity) powerService.findById(id);
		model.put("entity", powerEntity);
		
		// 电压等级
		Map<String, SysDictionaryVO> idTypeMap  =  DictionaryUtil.getDictionaries("POWER_LEVEL");
		List<SysDictionaryVO> dicVo=new ArrayList<SysDictionaryVO>();
		for(String key :  idTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = idTypeMap.get(key);
			dicVo.add(sysDictionaryVO);
		}
		model.put("levelList", dicVo);
		return this.createModelAndView("overhaul/power/powerShowDetail", model);
	}
	
	
	/**
	 * 导出
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
//	@RequestMapping("/exportExcel/{conditions}")
//	public void expData(HttpServletRequest req,HttpServletResponse res,@PathVariable String conditions) throws UnsupportedEncodingException{
//		
//		List<Map<String,Object>> list = JsonUtil.jsonToObj(conditions,List.class);
//		
//		Map<String,Object> param = new HashMap<String,Object>();
//		param.put("conditions",list);
// 		List<PowerEntity> dataList=powerService.findByCondition(param, null);
// 		List<String []> dataStyle=new ArrayList<String[]>();
// 		for (int i = 0; i < dataList.size(); i++) {
//			dataList.get(i).setRequestNumber(String.valueOf(i+1));
//			if(dataList.get(i).getPowerStatusText().equals("停电")){
//				dataStyle.add(new String []{String.valueOf(i+2),"4",
//						dataList.get(i).getPowerStatusText(),String.valueOf(Cell.CELL_TYPE_STRING)});
//			}
//		}
//		Map<String,Object> resultMap=new HashMap<String, Object>();
//		resultMap.put("dataList", dataList);
//		resultMap.put("dataStyle", dataStyle);
//		ExcelUtil.export(req, res, "停送电管理报表模板.xlsx","停送电管理.xlsx", resultMap);
//	}
	
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));

		Page<PowerEntity> page=new Page<PowerEntity>();
		page.setPageSize(Integer.MAX_VALUE);
//		page.addOrder(Sort.desc("C_ID"));
		List<PowerEntity> dataList=powerService.findByCondition(conditions, page);
		List<String []> dataStyle=new ArrayList<String[]>();
 		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).setRequestNumber(String.valueOf(i+1));
			if(dataList.get(i).getPowerStatusText().equals("停电")){
				dataStyle.add(new String []{String.valueOf(i+2),"4",
						dataList.get(i).getPowerStatusText(),String.valueOf(Cell.CELL_TYPE_STRING)});
			}
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		resultMap.put("dataStyle", dataStyle);
		ExcelUtil.export(req, res, "停送电管理报表模板.xlsx","停送电管理.xlsx", resultMap);
	}
	/**
	 * 提交前校验
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/beforeSubmit")
	public @ResponseBody
	ResultObj beforeSubmit(@RequestBody PowerEntity t, HttpServletRequest request) {
		return powerService.beforeSubmit(t);
	}
	
	/**
	 * 选择审核人
	 * @param request
	 * @return
	 */
	@RequestMapping("/sureSubmitPerson")
	public ModelAndView sureSubmitPerson(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.POWER_PROCESS_KEY.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		String modelId="";
		if(!defList.isEmpty()){
			modelId=defList.get(0).getModelId();
		}
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
		request.setAttribute("userList", userList);
		return this.createModelAndView("overhaul/power/sureSubmitPerson", model);
	}
	
	/**
	 * 提交审核
	 */
	@RequestMapping(value = "/submit/{id}")
	public @ResponseBody
	ResultObj submit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		return powerService.submit(id,params);
	}
	
	/**
	 * 审核页面跳转
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request,@PathVariable Long id,@PathVariable String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		PowerEntity powerEntity = powerService.findById(id);
		model.put("entity", powerEntity);
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.POWER_PROCESS_KEY.getName()));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}
		//查询各个人的按钮权限 结束
		return this.createModelAndView("overhaul/power/powerApprove",model);
	}
	
	
	/**
	 *  检修班长审批查询提交人(同意)
	 * @param request
	 * @param taskId
	 * @param id
	 * @return
	 */
	@RequestMapping("/submitPersonAgree/{taskId}/{id}")
	public ModelAndView submitPersonAgree(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);
		PowerEntity entity = powerService.findById(id);
		model.put("entity", entity);
		return this.createModelAndView("overhaul/power/powerOverhaulAudit", model);
	}
	/**
	 *  检修班长审批查询提交人(同意)
	 * @param request
	 * @param taskId
	 * @param id
	 * @return
	 */
	@RequestMapping("/powerZztgAudit/{taskId}/{id}")
	public ModelAndView powerZztgAudit(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);
		PowerEntity entity = powerService.findById(id);
		model.put("entity", entity);
		return this.createModelAndView("overhaul/power/powerZztgAudit", model);
	}
	/**
	 *  检修班长审批查询提交人(同意)
	 * @param request
	 * @param taskId
	 * @param id
	 * @return
	 */
	@RequestMapping("/powerLeader/{taskId}/{id}")
	public ModelAndView powerLeader(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);
		PowerEntity entity = powerService.findById(id);
		model.put("entity", entity);
		return this.createModelAndView("overhaul/power/powerLeader", model);
	}
	/**
	 *  值长审批查询提交人(同意)
	 * @param request
	 * @param taskId
	 * @param id
	 * @return
	 */
	@RequestMapping("/powerMonitorAudit/{taskId}/{id}")
	public ModelAndView powerMonitorAudit(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		PowerEntity entity = powerService.findById(id);
		model.put("entity", entity);
		// 申请人下拉列表
		ComboboxVO requestUserVO = new ComboboxVO();
		// TODO下拉框具体内容根据具体业务定制
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		for (SysUserEntity sysUserEntity : userList) {
			requestUserVO.addOption(sysUserEntity.getId().toString(),sysUserEntity.getName());
		}
		model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
		return this.createModelAndView("overhaul/power/powerMonitorAudit", model);
	}

	/**
	 * 执行人提交执行结果(同意)
	 * @param request
	 * @param taskId
	 * @param id
	 * @return
	 */
	@RequestMapping("/powerExecutedResult/{taskId}/{id}")
	public ModelAndView powerExecutedResult(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		PowerEntity entity = powerService.findById(id);
		model.put("entity", entity);
		return this.createModelAndView("overhaul/power/powerExecutedResult", model);
	}
	
	/**
	 * 检修班长审核通过
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/firstAudit/{taskId}")
	public @ResponseBody
	ResultObj biotechApprove(@RequestBody PowerEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("status", PowerStatusEnum.MONITOR.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());;
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		return powerService.approve(t,params);
	}
	/**
	 * 检修班长审核通过
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/zztgAudit/{taskId}")
	public @ResponseBody
	ResultObj zztgAudit(@RequestBody PowerEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("status", PowerStatusEnum.LEADER.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());;
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		return powerService.approve(t,params);
	}
	/**
	 * 检修班长审核通过
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/leaderAudit/{taskId}")
	public @ResponseBody
	ResultObj leaderAudit(@RequestBody PowerEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		boolean finish=actTaskService.isParallelEndTask(t.getTaskId());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		if(finish){
			Object status= actTaskService.getVarialble(t.getTaskId(), BranchMarkEnum.PARLLEL_BRANCH_KEY.getName());
			if(String.valueOf(status).equals(String.valueOf(ExamResultEnum.BACK.getId()))){
				params.put("status", PowerStatusEnum.REJECT.getCode());
				params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
				params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());
			}else{
				params.put("status", PowerStatusEnum.IMPLEMENT.getCode());
				params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
				params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
			}
		}
		return powerService.approve(t,params);
	}
	/**
	 * 值长审核通过
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/secondAudit/{id}")
	public @ResponseBody
	ResultObj secondAudit(@RequestBody PowerEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("status", PowerStatusEnum.UNEXECUTED.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());;
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		  //调用群发消息接口
	       MessageCenterEntity messageEntity =new MessageCenterEntity();
	       messageEntity.setTitle("停送电审批通过");
	       PowerEntity powerEntity=powerService.findById(t.getId());
	       messageEntity.setContext("停送电设备编码为："+powerEntity.getEquipNumber()+"审批通过");
	       messageEntity.setSendTime(new Date());
	       messageEntity.setReceivePerson(t.getCostodyUserId().toString());
	       SysUserEntity sysUserEntity = RequestContext.get().getUser();
	       messageEntity.setSendPerson(sysUserEntity.getId().toString());
	       messageEntity.setType("private");
	       messageCenterService.addMessage(messageEntity);
		return powerService.approve(t,params);
	}
	
	/**
	 * 执行人完成
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/executed/{id}")
	public @ResponseBody
	ResultObj executed(@RequestBody PowerEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("status", PowerStatusEnum.EXECUTED.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(),"" );
		  //调用群发消息接口
	       List<String> receivePersonList=actProcessService.getAllUsersByProcessInstanceId(t.getId().toString(), ProcessMarkEnum.POWER_PROCESS_KEY.getName());
	       String receivePersons="";
	       for (String receivePerson :receivePersonList) {
	    	   receivePersons+=receivePerson+",";
	        }
	       MessageCenterEntity messageEntity =new MessageCenterEntity();
	       messageEntity.setTitle("停送电已执行");
	       PowerEntity powerEntity=powerService.findById(t.getId());
	       messageEntity.setContext("停送电设备编码为："+powerEntity.getEquipNumber()+"已执行");;
	       messageEntity.setSendTime(new Date());
	       messageEntity.setReceivePerson(receivePersons);
	       SysUserEntity sysUserEntity = RequestContext.get().getUser();
	       messageEntity.setSendPerson(sysUserEntity.getId().toString());
	       messageEntity.setType("private");
	       messageCenterService.addMessage(messageEntity);
		return powerService.approve(t,params);
	}
	
	/**
	 * 检修班长审核未通过
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/reject1/{id}")
	public @ResponseBody
	ResultObj reject1(@RequestBody PowerEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("status", PowerStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());
		return powerService.approve(t,params);
	}
	
	/**
	 * 值长审核未通过
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/reject2/{id}")
	public @ResponseBody
	ResultObj reject2(@RequestBody PowerEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("status", PowerStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(),t.getAuditMsg());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());
		return powerService.approve(t,params);
	}
	/**
	 * 审核未通过提交驳回原因
	 * @param request
	 * @param taskId
	 * @param id
	 * @return
	 */
	@RequestMapping("/powerRejectAudit/{taskId}/{id}")
	public ModelAndView powerRejectAudit(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		PowerEntity entity = powerService.findById(id);
		model.put("entity", entity);
		return this.createModelAndView("overhaul/power/powerRejectAudit", model);
	}
	/**
	 * 提交人再次提交按钮
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/submitAgain/{id}")
	public @ResponseBody ResultObj submitAgain(@RequestBody PowerEntity t){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", PowerStatusEnum.OVERHAUL.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(),"");
		params.put(ExamMarkEnum.RESULT.getCode(), "再次提交");
		return powerService.approve(t, params);
	}
	/**
	 * 再次提交查询审核人
	 * @param request
	 * @param taskId
	 * @param id
	 * @return
	 */
	@RequestMapping("/sureSubmitPersonAgain/{taskId}/{id}")
	public ModelAndView sureSubmitPersonAgain(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		PowerEntity entity = powerService.findById(id);
		model.put("entity", entity);
		powerService.beforeSubmit(entity);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("overhaul/power/sureSubmitPerson", model);
	}
	
	/**
	 * 提交人取消流程
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/cancel/{id}")
	public @ResponseBody ResultObj cancel(@RequestBody PowerEntity t){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", PowerStatusEnum.CANCEL.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK_END.getName());
		return powerService.approve(t, params);
	}
	
	/**
	 * @Description:   停送电管理列表
	 * @author          
	 * @param <T>
	 * @Date           
	 * @throws         Exception
	 */
	@RequestMapping("/searchData")
	public @ResponseBody  ResultListObj searchData(HttpServletRequest request,@RequestBody Map<String, Object> params){
			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
//			conditions.add(new Condition("a.C_ID",FieldTypeEnum.INT,MatchTypeEnum.NE,"-1"));
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));//检修计划审批后状态
			ResultListObj resultObj = new ResultListObj();
			Page<PowerEntity> page = PageUtil.getPage(params);
			page.setOrders(OrmUtil.changeMapToOrders(params));
			List<PowerEntity> powerEntities =  (List<PowerEntity>) powerService.findByCondition(conditions, page);
			resultObj.setDraw((Integer)params.get("draw"));
			if (powerEntities != null) {
				resultObj.setData(powerEntities);
				resultObj.setRecordsTotal(page.getTotal());
			}
			return resultObj;
	}
	
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showUpdate/{id}")
	public @ResponseBody
	ResultObj showUpdate(@RequestBody PowerEntity t, HttpServletRequest request, @PathVariable Long id) {
		PowerEntity powerEntity = powerService.findById(id);
		powerEntity.setEndDate(t.getEndDate());
		powerEntity.setMeasur(t.getMeasur());
		powerEntity.setEndEquipLocalName(t.getEndEquipLocalName());
		powerEntity.setEndPowerRange(t.getEndPowerRange());
		powerService.updateEntity(powerEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.POWER.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return new ResultObj();
	}
}