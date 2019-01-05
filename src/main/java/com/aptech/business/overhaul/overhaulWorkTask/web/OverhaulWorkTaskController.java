package com.aptech.business.overhaul.overhaulWorkTask.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.equip.equipTree.domain.EquipTreeEnum;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeEntity;
import com.aptech.business.overhaul.overhaulArrange.service.OverhaulArrangeService;
import com.aptech.business.overhaul.overhaulLogDetail.domain.OverhaulLogDetailEntity;
import com.aptech.business.overhaul.overhaulWorkTask.domain.OverhaulWorkTaskEntity;
import com.aptech.business.overhaul.overhaulWorkTask.service.OverhaulWorkTaskService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
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
 * 检修工作任务配置控制器
 *
 * @author 
 * @created 2017-12-22 10:15:21
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/overhaulWorkTask")
public class OverhaulWorkTaskController extends BaseController<OverhaulWorkTaskEntity> {
	
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private OverhaulWorkTaskService overhaulWorkTaskService;
	@Autowired
	private OverhaulArrangeService overhaulArrangeService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Override
	public IBaseEntityOperation<OverhaulWorkTaskEntity> getService() {
		return overhaulWorkTaskService;
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
		List<OverhaulWorkTaskEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("overhaulWorkTaskTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOverhaulWorkTaskVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("overhaulWorkTaskCombobox", JsonUtil.toJson(comboOverhaulWorkTaskVO.getOptions()));
		return this.createModelAndView("overhaul/overhaulWorkTask/overhaulWorkTaskList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{overhaulArrangeId}")
	public ModelAndView getAddPage(HttpServletRequest request,@PathVariable Long overhaulArrangeId){
		Map<String, Object> model = new HashMap<String, Object>();
		//完成状态
		ComboboxVO finishStatusCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("OVERHUALARRANGE_FINISHSTATUS");
		
		for(String key :  codeDateTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
			finishStatusCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("overhaulArrangeId", overhaulArrangeId);
		model.put("overhaulFinishStatus", JsonUtil.toJson(finishStatusCombobox.getOptions()));
		return this.createModelAndView("overhaul/overhaulWorkTask/overhaulWorkTaskAdd", model);
	}

	/**
	 * @Description:   检修日志-工作任务-新增
	 * @author         wangcc 
	 * @Date           2018年1月3日 上午10:34:30 
	 * @throws         Exception
	 */
	@RequestMapping("/overhaulWorkTaskAdd")
	public @ResponseBody <T> ResultObj overhaulWorkTaskAdd(@RequestBody OverhaulWorkTaskEntity overhaulWorkTaskEntity, HttpServletRequest request){
		return overhaulWorkTaskService.overhaulWorkTaskAdd(overhaulWorkTaskEntity);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		OverhaulWorkTaskEntity overhaulWorkTaskEntity = (OverhaulWorkTaskEntity)overhaulWorkTaskService.findById(id);
		model.put("entity", overhaulWorkTaskEntity);
		model.put("entityJson", JsonUtil.toJson(overhaulWorkTaskEntity));
		
		List<OverhaulWorkTaskEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("overhaulWorkTaskTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOverhaulWorkTaskVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("overhaulWorkTaskCombobox", JsonUtil.toJson(comboOverhaulWorkTaskVO.getOptions()));
		
		return this.createModelAndView("overhaul/overhaulWorkTask/overhaulWorkTaskEdit", model);
	}
	/**
	 *	删除
	 */
	@RequestMapping("/delete/{id}")
	public @ResponseBody <T> ResultObj delete(HttpServletRequest request, @PathVariable Long id){
		return overhaulWorkTaskService.delete(request, id);
	}
	
	/**
	 *	勾掉设备后删除
	 */
	@RequestMapping("/deleteUnCheckDate")
	public @ResponseBody <T> ResultObj deleteUnCheckDate(HttpServletRequest request,@RequestBody Map<String, Object> params){
		return overhaulWorkTaskService.deleteUnCheckDate(request,params);
	}
	
	/**
	 *	添加设备相关数据
	 */
	@RequestMapping("/addEquipInfo/{overhaulArrangeId}")
	public @ResponseBody <T> ResultObj addEquipInfo(HttpServletRequest request,@RequestBody Map<String, Object> params, @PathVariable Long overhaulArrangeId){
		return overhaulWorkTaskService.addEquipInfo(request, params,overhaulArrangeId);
	}
	/**
	 *	保存工作任务
	 */
	@RequestMapping("/addTask")
	public @ResponseBody ResultObj addTask(HttpServletRequest request,@RequestBody OverhaulLogDetailEntity entity){
//		return overhaulWorkTaskService.addWorkTask(params,request);
		ResultObj resultObj = new ResultObj();
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		OverhaulArrangeEntity overhaulArrangeEntity = overhaulArrangeService.findById(Long.valueOf(entity.getOverhaulRecordId()));
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("O.C_STATUS",FieldTypeEnum.STRING,MatchTypeEnum.EQ,DataStatusEnum.NORMAL.ordinal()));
		conditions.add(new Condition("O.C_OVERHAUL_ARRANGE_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,overhaulArrangeEntity.getId()));
		List<OverhaulWorkTaskEntity> taskEntities = overhaulWorkTaskService.findByCondition(conditions, null);
		
		if (!taskEntities.isEmpty()) {
			for (OverhaulWorkTaskEntity overhaulWorkTaskEntity : taskEntities) {
			
				overhaulWorkTaskService.deleteEntity(overhaulWorkTaskEntity.getId());
			}
		}
		
		for (OverhaulWorkTaskEntity workTaskEntity : entity.getTaskList()) {
		
			OverhaulWorkTaskEntity overhaulWorkTaskEntity = new OverhaulWorkTaskEntity();
				
				overhaulWorkTaskEntity.setUpdateDate(new Date());
				overhaulWorkTaskEntity.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
				overhaulWorkTaskEntity.setOverhaulRecordId(entity.getOverhaulRecordId());
				overhaulWorkTaskEntity.setUpdateUserId(userEntity.getId());
				overhaulWorkTaskEntity.setEquipName(workTaskEntity.getEquipName());
				overhaulWorkTaskEntity.setEquipId(workTaskEntity.getEquipId());
				overhaulWorkTaskEntity.setFinishStatus(workTaskEntity.getFinishStatus());
				overhaulWorkTaskEntity.setWorkTask(workTaskEntity.getWorkTask());
				overhaulWorkTaskService.addEntity(overhaulWorkTaskEntity);
			
		}

		return resultObj;
	}
	/**
	 * 条件查询
	 * @param request
	 * @param params 查询条件参数map
	 * @return
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<OverhaulWorkTaskEntity> page = PageUtil.getPage(params);
		List<Sort> orders  = OrmUtil.changeMapToOrders(params);
		List<Sort> newOrders = new ArrayList<Sort>();
		for (Sort sort : orders) {
			if ("equipName".equals(sort.getField())) {
				sort.setField("equipId");
			}
			if ("workTask".equals(sort.getField())) {
				sort.setField("workTask");
			}
			if ("logdutyUserName".equals(sort.getField())) {
				sort.setField("logdutyUserId");
			}
			if ("logsubmitUserName".equals(sort.getField())) {
				sort.setField("logsubmitUserId");
			}
			if ("loglogDateString".equals(sort.getField())) {
				sort.setField("loglogDate");
			}
			if ("logoutUnitName".equals(sort.getField())) {
				sort.setField("logoutUnitName");
			}
			if ("logfinishStatusString".equals(sort.getField())) {
				sort.setField("logfinishStatus");
			}
			newOrders.add(sort);
		}
		page.setOrders(newOrders);

		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		String[] equitId = {};
		for (Condition con :conditions) {
			if ("O.C_EQUIP_ID".equals(con.getFieldName())) {
				equitId = (String[])con.getValue();
			}
		}
		List<OverhaulWorkTaskEntity> dataList;
		if (equitId.length == 0) {
			dataList = new ArrayList<OverhaulWorkTaskEntity>();
		} else if (equitId.length == 1){
			if ("".equals("equitId[0]")) {
				dataList = new ArrayList<OverhaulWorkTaskEntity>();
			} else {
				dataList = overhaulWorkTaskService.findByCondition("findEquitOverhaulData", conditions, page);
			}
		} else {
			dataList = overhaulWorkTaskService.findByCondition("findEquitOverhaulData", conditions, page);
		}
		 
		
//		List<SysUnitEntity> unitList = sysUnitService.findAll();
//		if (dataList != null && dataList.size() > 0 ) {
//			for (OverhaulWorkTaskEntity entity : dataList) {
//				for (SysUnitEntity unitEntity : unitList) {
//					if (entity.getLogunitId().toString().equals( unitEntity.getId().toString())) {
//						entity.setLogunitName(unitEntity.getName());
//						break;
//					}
//				}
//			}
//		}
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (dataList != null) {
			resultObj.setData(dataList);
			resultObj.setRecordsTotal(dataList.size());
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			}
		}
		return resultObj;
	}
	
	/**
	 * @Description:   选择带回 
	 * @author         changl 
	 * @Date           2017年6月26日 下午2:30:45 
	 * @throws         Exception
	 */
	@RequestMapping("/selectEquipLedger")
	public ModelAndView selectEquipLedgerProtect(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("unitId", userEntity.getUnitId());
		//设备类型
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE",FieldTypeEnum.STRING, MatchTypeEnum.EQ,EquipTreeEnum.EQUIPTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesList  = sysDictionaryService.findByCondition(conditions, null);
		ComboboxVO comboequipType = new ComboboxVO();
		for(SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesList){
			comboequipType.addOption(sysdictionaryentity.getCode().toString(), sysdictionaryentity.getName());
		}
		request.setAttribute("equipType", JsonUtil.toJson(comboequipType.getOptions()));
		// 部门下拉树
		conditions.clear();
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ, '2'));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.STRING, MatchTypeEnum.EQ, '1'));
		List<SysUnitEntity> sysunitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO comboboxVO = new ComboboxVO();
		for (SysUnitEntity sysUnitEntity : sysunitList) {
			comboboxVO.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName().toString());
			
		}
		resultMap.put("equipUnit", JsonUtil.toJson(comboboxVO.getOptions()));
		//列表中已存在的数据id
		String[] ids = request.getParameterValues("ids");
		resultMap.put("ids", JsonUtil.toJson(ids));
		return new ModelAndView("overhaul/overhaulWorkTask/selectEquipLedger",resultMap);
	}
}