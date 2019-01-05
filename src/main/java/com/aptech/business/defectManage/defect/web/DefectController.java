package com.aptech.business.defectManage.defect.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.aptech.business.component.dictionary.DefectStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.business.defectManage.defectEquipment.domain.DefectEquipmentEntity;
import com.aptech.business.defectManage.defectEquipment.service.DefectEquipmentService;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.excel.ExcelUtil;
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
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.common.workflow.todoTask.domain.TodoTaskEntity;
import com.aptech.common.workflow.todoTask.service.TodoTaskService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 *缺陷管理
 * 
 * @author
 * @created 2017-06-02 13:18:00
 * @lastModified
 * @history
 * 
 */
@Controller
@RequestMapping("/defect")
public class DefectController extends BaseController<DefectEntity> {

	@Autowired
	private DefectService defectService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private DefectEquipmentService defectEquipmentService;
	@Override
	public IBaseEntityOperation<DefectEntity> getService() {
		return defectService;
	}

	/**
	 * @Description: 缺陷页面初始化
	 * @author changl
	 * @Date 2017年6月5日 下午1:17:55
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request,
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
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil
				.getDictionaries("DEFECT_TYPE");
		ComboboxVO searchtype=getTypeMap(typeMap);
		model.put("searchtype", JsonUtil.toJson(searchtype.getOptions()));
		// 部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		conditions.add(new Condition(" C_LEVEL = 2 "));
		conditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4  "));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
		model.put("unitNameIdTreeList", JsonUtil.toJson(companyVo.getOptions()));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		// 设备类型
		Map<String, SysDictionaryVO> equipTypeMap = DictionaryUtil
						.getDictionaries("DEVICE_TYPE");
		ComboboxVO equipType=new  ComboboxVO();
		for(String key :  equipTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = equipTypeMap.get(key);
        	equipType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }	
		model.put("equipType", JsonUtil.toJson(equipType.getOptions()));
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView("defectManage/defect/defectList", model);
	}
	private ComboboxVO  getTypeMap(Map<String, SysDictionaryVO> typeMap ){
		ComboboxVO searchtype = new ComboboxVO();
		List<Map.Entry<String, SysDictionaryVO>> list = new ArrayList<Map.Entry<String, SysDictionaryVO>>(typeMap.entrySet()); 
		  Collections.sort(list, new Comparator<Map.Entry<String, SysDictionaryVO>>() { 
		      public int compare(Map.Entry<String, SysDictionaryVO> o1, Map.Entry<String, SysDictionaryVO> o2) { 
		          return (o1.getKey()).toString().compareTo(o2.getKey()); 

		      } 

		  }); 
		for (Map.Entry<String, SysDictionaryVO> entry : list) {
			SysDictionaryVO sysDictionaryVO = entry.getValue();
			searchtype.addOption(sysDictionaryVO.getCode(),
					sysDictionaryVO.getName());

		}
		return searchtype;
	}
	/**
	 * @Description: 设备名称树
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/equipTree/{id}")
	public @ResponseBody ResultObj equipTree(@PathVariable Long id, HttpServletRequest request) {
		ResultObj resultObj=new ResultObj();
		//设备名称
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("T.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,id));
		List<SysUserEntity> equipList=equipLedgerService.findByCondition("findtTreeByCondition", conditions, null);
		resultObj.setData(JsonUtil.toJson(equipList));
		return resultObj;
	}

	/**
	 * @Description: 缺陷添加页面初始化
	 * @author changl
	 * @Date 2017年6月5日 下午1:17:55
	 * @throws Exception
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 缺陷类型
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil
				.getDictionaries("DEFECT_TYPE");
		ComboboxVO searchtype=getTypeMap(typeMap);
		model.put("searchtype", JsonUtil.toJson(searchtype.getOptions()));
		// 部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		conditions.add(new Condition(" C_LEVEL = 2 "));
		conditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4  "));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
		model.put("unitNameIdTreeList", JsonUtil.toJson(companyVo.getOptions()));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		model.put("userEntity", userEntity);
		model.put("date", new Date());
		// 设备类型
		Map<String, SysDictionaryVO> equipTypeMap = DictionaryUtil
						.getDictionaries("DEVICE_TYPE");
		ComboboxVO equipType=new  ComboboxVO();
		for(String key :  equipTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = equipTypeMap.get(key);
        	equipType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }	
		model.put("equipType", JsonUtil.toJson(equipType.getOptions()));
		//设备管理类型
		ComboboxVO equipmentTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> equipmentTypeMap = DictionaryUtil.getDictionaries("EQUIPMANAGETYPE");
		for(String key : equipmentTypeMap.keySet()){
			SysDictionaryVO equipmentTypeVO = equipmentTypeMap.get(key);
			equipmentTypeCombobox.addOption(equipmentTypeVO.getCode(), equipmentTypeVO.getName());
		}
		model.put("equipmentTypeCombobox", JsonUtil.toJson(equipmentTypeCombobox.getOptions()));
		String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView("defectManage/defect/defectAdd", model);
	}

	/**
	 * @Description: 缺陷修改页面初始化
	 * @author changl
	 * @Date 2017年6月5日 下午1:17:55
	 * @throws Exception
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request,@PathVariable Long id,
			@RequestBody Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		//上一条 下一条
		ResultListObj resultListObj=this.search(request, params);
		if(resultListObj.getData()!=null&&!resultListObj.getData().isEmpty()){
			DefectEntity t=(DefectEntity) resultListObj.getData().get(0);
			id=t.getId();
			params.put("total", resultListObj.getRecordsTotal());
		}
		model.put("dedit", JsonUtil.toJson(params));
		// 缺陷类型
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil
				.getDictionaries("DEFECT_TYPE");
		ComboboxVO searchtype=getTypeMap(typeMap);
		model.put("searchtype", JsonUtil.toJson(searchtype.getOptions()));
		// 部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		conditions.add(new Condition(" C_LEVEL = 2 "));
		conditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4  "));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
		model.put("unitNameIdTreeList", JsonUtil.toJson(companyVo.getOptions()));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		DefectEntity defectEntity = defectService.findById(id);
		model.put("defectEntity", defectEntity);
		// 设备类型
		Map<String, SysDictionaryVO> equipTypeMap = DictionaryUtil
						.getDictionaries("DEVICE_TYPE");
		ComboboxVO equipType=new  ComboboxVO();
		for(String key :  equipTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = equipTypeMap.get(key);
        	equipType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }	
		model.put("equipType", JsonUtil.toJson(equipType.getOptions()));
		//设备管理类型
		ComboboxVO equipmentTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> equipmentTypeMap = DictionaryUtil.getDictionaries("EQUIPMANAGETYPE");
		for(String key : equipmentTypeMap.keySet()){
			SysDictionaryVO equipmentTypeVO = equipmentTypeMap.get(key);
			equipmentTypeCombobox.addOption(equipmentTypeVO.getCode(), equipmentTypeVO.getName());
		}
		model.put("equipmentTypeCombobox", JsonUtil.toJson(equipmentTypeCombobox.getOptions()));
		model.put("id", id);
		//获取关联设备
		List<EquipLedgerEntity> templist = new ArrayList<EquipLedgerEntity>();
		conditions.clear();
		conditions.add(new Condition("C_DEFECT_ID",FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
		List<DefectEquipmentEntity> defectEquipmentEntity = defectEquipmentService.findByCondition(conditions, null);
		for(DefectEquipmentEntity entity:defectEquipmentEntity){
			conditions.clear();
			conditions.add(new Condition("L.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,entity.getEquipCode()));
			List<EquipLedgerEntity>  equipList = equipLedgerService.findByCondition(conditions, null);
			if(!equipList.isEmpty()){
				templist.add(equipList.get(0));
			}
		}
		model.put("userUnitRels", JsonUtil.toJson(templist));
		return this.createModelAndView("defectManage/defect/defectEdit", model);
	}

	/**
	 * @Description: 缺陷添加
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody
	ResultObj save(@RequestBody DefectEntity t, HttpServletRequest request) {
		return defectService.add(t);
	}

	/**
	 * @Description: 缺陷查看页面初始化
	 * @author changl
	 * @Date 2017年6月5日 下午1:17:55
	 * @throws Exception
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetailPage(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 缺陷类型
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil
				.getDictionaries("DEFECT_TYPE");
		ComboboxVO searchtype=getTypeMap(typeMap);
		model.put("searchtype", JsonUtil.toJson(searchtype.getOptions()));
		// 部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		conditions.add(new Condition(" C_LEVEL = 2 "));
		conditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4  "));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
		model.put("unitNameIdTreeList", JsonUtil.toJson(companyVo.getOptions()));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		DefectEntity defectEntity = defectService.findById(id);
		model.put("defectEntity", defectEntity);
		return this.createModelAndView("defectManage/defect/defectDetail",
				model);
	}

	/**
	 * @Description: 缺陷修改
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public @ResponseBody
	ResultObj update(@RequestBody DefectEntity t, HttpServletRequest request) {
		return defectService.update(t);
	}

	/**
	 * @Description: 缺陷删除
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody
	ResultObj delete(@PathVariable Long id) {
		return defectService.delete(id);
	}

	/**
	 * @Description: 缺陷批量删除
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/bulkDelete")
	public @ResponseBody
	ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		for (Integer id : ids) {
			long longId = (long) id;
			DefectEntity t = defectService.findById(longId);
			if (t != null) {
				defectService.delete(longId);
			}
		}
		return resultObj;
	}
	/**
	 * @Description: 缺陷提交查询提交人
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping("/sureSubmitPerson")
	public ModelAndView sureSubmitPerson(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,
				ProcessMarkEnum.DEFECT_PROCESS_KEY.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		String modelId="";
		if(!defList.isEmpty()){
			modelId=defList.get(0).getModelId();
		}
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
		request.setAttribute("userList", userList);
		return this.createModelAndView("defectManage/defect/sureSubmitPerson", model);
	}
	/**
	 * @Description: 缺陷审批查询提交人(同意)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping("/submitPersonAgree/{taskId}")
	public ModelAndView submitPersonAgree(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("defectManage/defect/sureSubmitPerson", model);
	}
	/**
	 * @Description: 缺陷审批查询提交人(驳回上一级)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping("/submitPersonBack/{taskId}")
	public ModelAndView submitPersonBack(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.BACK.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("defectManage/defect/sureSubmitPerson", model);
	}
	/**
	 * @Description: 缺陷审批查询提交人(驳回)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping("/submitPersonBackEnd/{taskId}")
	public ModelAndView submitPersonBackEnd(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.BACK_END.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("defectManage/defect/sureSubmitPerson", model);
	}
	/**
	 * @Description: 缺陷提交
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/submit/{id}")
	public @ResponseBody
	ResultObj submit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		return defectService.submit(id,params);
	}

	/**
	 * @Description: 缺陷审批页面初始化
	 * @author changl
	 * @Date 2017年6月5日 下午1:17:55
	 * @throws Exception
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request,@PathVariable Long id,@PathVariable String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 缺陷类型
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil.getDictionaries("DEFECT_TYPE");
		ComboboxVO searchtype=getTypeMap(typeMap);
		model.put("searchtype", JsonUtil.toJson(searchtype.getOptions()));
		model.put("type", type);
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<SysUserEntity> userList = sysUserService.findByCondition(
				new ArrayList<Condition>(), null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		DefectEntity defectEntity = defectService.findById(id);
		model.put("defectEntity", defectEntity);
		//查询各个人的按钮权限 开始
				SysUserEntity userEntity= RequestContext.get().getUser();
				List<Condition> conditionsLc=new ArrayList<Condition>();
				conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
				conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
				conditionsLc.add(new Condition("task.end_time_ IS NULL"));
				conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.DEFECT_PROCESS_KEY.getName()));
				List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
				TodoTaskEntity todoTaskEntity=null;
				if(!list.isEmpty()){
					todoTaskEntity=list.get(0);
					List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
					model.put("nodeList", nodeList);
				}
		//查询各个人的按钮权限 结束
		return this.createModelAndView("defectManage/defect/defectApprove",
				model);
	}
	/**
	 * @Description:   工作票
	 * @author         changl 
	 * @Date           2017年6月22日 上午10:01:07 
	 * @throws         Exception
	 */
	@RequestMapping("/workTicket")
	public ModelAndView workTicket(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView("defectManage/defect/defectWorkTicketList",
				model);
	}
	/**
	 * @Description:   操作票
	 * @author         changl 
	 * @Date           2017年6月22日 上午10:01:07 
	 * @throws         Exception
	 */
	@RequestMapping("/operationTicket")
	public ModelAndView operationTicket(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView("defectManage/defect/defectOperationTicketList",
				model);
	}
	/**
	 * @Description:   设备异动
	 * @author         changl 
	 * @Date           2017年6月22日 上午10:01:07 
	 * @throws         Exception
	 */
	@RequestMapping("/sbyd")
	public ModelAndView sbyd(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView("defectManage/defect/defectsbydList",
				model);
	}
	/**
	 * @Description:   导出
	 * @author         changl 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditions=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditions));
		List<DefectEntity> dataList=defectService.findByCondition(params, null);
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).setNumber(i+1);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "缺陷管理报表模板.xlsx","缺陷管理.xlsx", resultMap);
	}
	/**
	 * @Description:   缺陷单的选择带回
	 * @author         zhangzq 
	 * @Date           2017年8月1日 下午1:26:58 
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
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil
				.getDictionaries("DEFECT_TYPE");
		ComboboxVO searchtype=getTypeMap(typeMap);
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
		List<SysUserEntity> userList = sysUserService.findByCondition(
				new ArrayList<Condition>(), null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		return this.createModelAndView("defectManage/defect/workDefectList", model);
	}
	/**
	 * @Description:   消缺
	 * @author         changl 
	 * @Date           2017年6月21日 上午9:19:29 
	 * @throws         Exception
	 */
	@RequestMapping("/getXQ")
	public ModelAndView getXQ(HttpServletRequest request,DefectEntity defectEntity) {
		Map<String, Object> model = new HashMap<String, Object>();
		DefectEntity t=defectService.findById(defectEntity.getId());
		List<SysUserEntity> userList1=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList1);
		return this.createModelAndView("defectManage/defect/appraisalAddXQ",
				model);
	}
	/**
	 * @Description:   挂起
	 * @author         changl 
	 * @Date           2017年6月21日 上午9:19:29 
	 * @throws         Exception
	 */
	@RequestMapping("/getGQ")
	public ModelAndView getGQ(HttpServletRequest request,DefectEntity defectEntity) {
		Map<String, Object> model = new HashMap<String, Object>();
		DefectEntity t=defectService.findById(defectEntity.getId());
		List<SysUserEntity> userList1=nodeConfigService.getNextNodeTransactor(defectEntity.getTaskId(),ExamResultEnum.BACK.getId().toString());
		model.put("userList", userList1);
		return this.createModelAndView("defectManage/defect/appraisalAddGQ",
				model);
	}
}