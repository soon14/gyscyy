package com.aptech.business.equip.equipAppraise.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelEntity;
import com.aptech.business.equip.equipAbnormalEquipRel.service.EquipAbnormalEquipRelService;
import com.aptech.business.equip.equipAbnormalReport.domain.EquipAbnormalReportEntity;
import com.aptech.business.equip.equipAppraise.domain.EquipAppraiseEntity;
import com.aptech.business.equip.equipAppraise.service.EquipAppraiseService;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.equip.equipTree.service.EquipTreeService;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
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
import com.aptech.framework.util.PropertyUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 设备评价配置控制器
 *
 * @author baisy
 * @created 2017-09-18 16:41:54
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/equipAppraise")
public class EquipAppraiseController extends BaseController<EquipAppraiseEntity> {
	
	@Autowired
	private EquipAppraiseService equipAppraiseService;
	@Autowired
	private EquipTreeService equipTreeService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private EquipAbnormalEquipRelService abnormalEquipRelService;
	@Override
	public IBaseEntityOperation<EquipAppraiseEntity> getService() {
		return equipAppraiseService;
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
		List<EquipAppraiseEntity> treeNodeList = null;
		//人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity = RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		//原评
		Map<String, SysDictionaryVO> equipGradePre = DictionaryUtil
				.getDictionaries("EQUIP_GRADE_PRE");
		ComboboxVO searchEquipGradePre=getTypeMap(equipGradePre);
		model.put("equipGradePre", JsonUtil.toJson(searchEquipGradePre.getOptions()));
		//现评
		Map<String, SysDictionaryVO> equipGradeNow= DictionaryUtil
				.getDictionaries("EQUIP_GRADE_NOW");
		ComboboxVO searchEquipGradeNow=getTypeMap(equipGradeNow);
		model.put("equipGradeNow", JsonUtil.toJson(searchEquipGradeNow.getOptions()));
		//TODO下拉树具体内容根据具体业务定制
		model.put("equipAppraiseTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboEquipAppraiseVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("equipAppraiseCombobox", JsonUtil.toJson(comboEquipAppraiseVO.getOptions()));
		String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView("equip/equipAppraise/equipAppraiseList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<EquipAppraiseEntity> treeNodeList = null; 
		Map<String, SysDictionaryVO> equipGradePre = DictionaryUtil
				.getDictionaries("EQUIP_GRADE_PRE");
		ComboboxVO searchEquipGradePre=getTypeMap(equipGradePre);
		model.put("equipGradePre", JsonUtil.toJson(searchEquipGradePre.getOptions()));
		
		Map<String, SysDictionaryVO> equipGradeNow= DictionaryUtil
				.getDictionaries("EQUIP_GRADE_NOW");
		ComboboxVO searchEquipGradeNow=getTypeMap(equipGradeNow);
		model.put("equipGradeNow", JsonUtil.toJson(searchEquipGradeNow.getOptions()));
		//TODO下拉树具体内容根据具体业务定制
		model.put("equipAppraiseTreeList", JsonUtil.toJson(treeNodeList));
		SysUserEntity userEntity= RequestContext.get().getUser();
		String userName = userEntity.getName();
		model.put("userEntity", userName);
		ComboboxVO comboEquipAppraiseVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("equipAppraiseCombobox", JsonUtil.toJson(comboEquipAppraiseVO.getOptions()));
		String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView("equip/equipAppraise/equipAppraiseAdd", model);
	}
	
	/**
	 * 设备异动报告新增
	 */
	@RequestMapping(value = "/addEquipAppraise" , method = RequestMethod.POST)
	public @ResponseBody ResultObj addEquipAppraise(@RequestBody EquipAppraiseEntity equipAppraiseEntity, HttpServletRequest request) {
		return equipAppraiseService.addEquipAppraise(equipAppraiseEntity,request);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		EquipAppraiseEntity equipAppraiseEntity = (EquipAppraiseEntity)equipAppraiseService.findById(id);
		model.put("entity", equipAppraiseEntity);
		model.put("entityJson", JsonUtil.toJson(equipAppraiseEntity));
		Map<String, SysDictionaryVO> equipGradePre = DictionaryUtil
				.getDictionaries("EQUIP_GRADE_PRE");
		ComboboxVO searchEquipGradePre=getTypeMap(equipGradePre);
		model.put("equipGradePre", JsonUtil.toJson(searchEquipGradePre.getOptions()));
		
		Map<String, SysDictionaryVO> equipGradeNow= DictionaryUtil
				.getDictionaries("EQUIP_GRADE_NOW");
		ComboboxVO searchEquipGradeNow=getTypeMap(equipGradeNow);
		model.put("equipGradeNow", JsonUtil.toJson(searchEquipGradeNow.getOptions()));
		
		SysUserEntity userEntity= RequestContext.get().getUser();
		String userName = userEntity.getName();
		model.put("equipAppraisePerson", userName);
		
		List<EquipAppraiseEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("equipAppraiseTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboEquipAppraiseVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("equipAppraiseCombobox", JsonUtil.toJson(comboEquipAppraiseVO.getOptions()));
		//获取关联设备
		List<EquipLedgerEntity> templist = new ArrayList<EquipLedgerEntity>();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_EQUIP_APPRAISE_ID",FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
		conditions.add(new Condition("C_STATUS",FieldTypeEnum.INT, MatchTypeEnum.EQ,1));
		List<EquipAbnormalEquipRelEntity> abnormalEquipRelEntities = abnormalEquipRelService.findByCondition(conditions, null);
		for(EquipAbnormalEquipRelEntity entity:abnormalEquipRelEntities){
			conditions.clear();
			conditions.add(new Condition("L.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,entity.getEquipCode()));
			List<EquipLedgerEntity>  equipList = equipLedgerService.findByCondition(conditions, null);
			if(!equipList.isEmpty()){
				templist.add(equipList.get(0));
			}
		}
		model.put("userUnitRels", JsonUtil.toJson(templist));
		return this.createModelAndView("equip/equipAppraise/equipAppraiseEdit", model);
	}
	
	/**
	 * 设备异动报告新增
	 */
	@RequestMapping(value = "/updateEquipAppraise" , method = RequestMethod.POST)
	public @ResponseBody ResultObj updateEquipAppraise(@RequestBody EquipAppraiseEntity equipAppraiseEntity, HttpServletRequest request) {
		return equipAppraiseService.updateEquipAppraise(equipAppraiseEntity,request);
	}
	
	
	/**
	 * @Description:   批量删除
	 * @author         wangcc 
	 * @Date           2018年9月17日 下午14:04:34 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/deleteMore")
	public @ResponseBody ResultObj delSelectInfo(HttpServletRequest request,@RequestBody List<Long> ids) {
		return equipAppraiseService.delSelectInfo(ids);
	}
	
	/**
	 * @Description:   删除
	 * @author         wangcc 
	 * @Date           2018年9月17日 下午14:04:34 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/deleteOnlyOne/{id}")
	public @ResponseBody ResultObj deleteOnlyOne(HttpServletRequest request,@PathVariable Long id) {
		return equipAppraiseService.deleteOnlyOne(id);
	}
	
	/**
	 * @Description:   查看
	 * @author         wangcc 
	 * @Date           2018年9月26日 上午11:32:34 
	 * @throws         Exception
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, SysDictionaryVO> equipGradeNow= DictionaryUtil.getDictionaries("EQUIP_GRADE_NOW");
		ComboboxVO searchEquipGradeNow=getTypeMap(equipGradeNow);
		model.put("equipGradeNow", JsonUtil.toJson(searchEquipGradeNow.getOptions()));
		EquipAppraiseEntity entity = equipAppraiseService.findById(id);
		model.put("EquipAppraiseEntity", entity);
		return this.createModelAndView("equip/equipAppraise/equipAppraiseDetail", model);
	}
	
	
	/**
	 * @Description:   设备评级设备选择带回 
	 * @author         baisy 
	 * @Date           2017-09-18 16:41:54
	 * @throws         Exception
	 */
	@RequestMapping("/selectEquipLedger")
	public ModelAndView userSelectRevalBox(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("unitId", userEntity.getUnitId());
//		List<EquipAppraiseEntity> equipAppraiseList=equipAppraiseService.findAll();
		List<Long> equipAppraiseIdList=new ArrayList<Long>();
//		for(EquipAppraiseEntity equipAppraiseEntity:equipAppraiseList){
//			equipAppraiseIdList.add(equipAppraiseEntity.getEquipmentId());
//		}
		model.put("equipAppraiseIdList", equipAppraiseIdList);
		
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		ComboboxVO comboboxVO = new ComboboxVO();
		for (SysUnitEntity sysUnitEntity : treeNodeList) {
			comboboxVO.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName().toString());
			
		}
		model.put("equipUnit", JsonUtil.toJson(comboboxVO.getOptions()));
		return new ModelAndView("equip/equipAppraise/selectEquipLedger",model);
	}
	
	
	
	//把ID带回，排除以有的ID
	@RequestMapping("/searchEquipLedger")
	public @ResponseBody ResultListObj equipAppraiseIdList(HttpServletRequest request,@RequestBody  Map<String, Object> params){
		
		ResultListObj resultListObj = new ResultListObj();
		//接收前台传送已有数据的设备id
		String equipIdString = request.getParameter("equipAppraiseIdList");
		String[] haveEquipId = null;
		if(equipIdString.contains(",")){
			haveEquipId = equipIdString.split(",");
		}
		Page<EquipLedgerEntity> pages = PageUtil.getPage(params);
		pages.setOrders(OrmUtil.changeMapToOrders(params));
//		pages.addOrder(Sort.asc("name"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		//排除已经有的ID
		if(null != haveEquipId && haveEquipId.length!=0){
			conditions.add(new Condition("L.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.NOT_IN,haveEquipId));
		}
		//取所有的设备
		List<EquipLedgerEntity> EquipLedgerEntities = equipLedgerService.findByCondition(conditions, pages);
		//获得返回结果
		resultListObj.setDraw((Integer)params.get("draw"));
		if (EquipLedgerEntities != null) {
			if (pages != null) {
				resultListObj.setData(EquipLedgerEntities);
				resultListObj.setRecordsTotal(pages.getTotal());
			}
		}
		
		return resultListObj;
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
}