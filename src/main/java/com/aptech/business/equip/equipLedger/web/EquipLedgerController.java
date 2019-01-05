package com.aptech.business.equip.equipLedger.web;

import java.io.File;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.equip.equipModel.domain.EquipModelEntity;
import com.aptech.business.equip.equipModel.service.EquipModelService;
import com.aptech.business.equip.equipTree.domain.EquipTreeEntity;
import com.aptech.business.equip.equipTree.domain.EquipTreeEnum;
import com.aptech.business.equip.equipTree.service.EquipTreeService;
import com.aptech.business.equip.equiplParameter.service.EquipParameterService;
import com.aptech.business.equip.modelParameter.service.ModelParameterService;
import com.aptech.business.run.protect.service.ProtectService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
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
 * 设备管理配置控制器
 *
 * @author 
 * @created 2017-06-08 10:50:56
 * @history
 * @lastModified 
 *
 */
@Controller
@RequestMapping("/equipLedger")
public class EquipLedgerController extends BaseController<EquipLedgerEntity> {
	
	
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private EquipTreeService equipTreeService;
	@Autowired
	private EquipModelService equipModelService ;
	@Autowired
	private EquipParameterService equipParameterService ;
	@Autowired
	private ModelParameterService  modelParameterService; 
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private DefectService defectService;
	@Autowired
	private ProtectService protectService;
	//请求参数 
	public Map<String,Object> kmap = new HashMap<String,Object>();
	
	public String entityKey = EquipLedgerEntity.class.toString().split("@")[0];
	
	@Override
	public IBaseEntityOperation<EquipLedgerEntity> getService() {
		return equipLedgerService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{appId}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long appId) {
		Map<String, Object> model = new HashMap<String, Object>();
		//设备类型
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE",FieldTypeEnum.STRING, MatchTypeEnum.EQ,EquipTreeEnum.EQUIPTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesList  = sysDictionaryService.findByCondition(conditions, null);
        ComboboxVO comboequipType = new ComboboxVO();
        for(SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesList){
        	comboequipType.addOption(sysdictionaryentity.getCode().toString(), sysdictionaryentity.getName());
        }
		request.setAttribute("equipType", JsonUtil.toJson(comboequipType.getOptions()));
		return this.createModelAndView("equip/equipledger/equipLedgerList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//组织机构(父级节点)
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//设备结点
		List<EquipTreeEntity> equipTreeList = equipTreeService.findAll();	
	    for(EquipTreeEntity equipTreeEntity:equipTreeList){
	    	SysUnitEntity tempSysunitEntity = new SysUnitEntity();
	    	tempSysunitEntity.setId(equipTreeEntity.getId());
	    	tempSysunitEntity.setParentId(equipTreeEntity.getParentId());
	    	tempSysunitEntity.setCode(equipTreeEntity.getCode());
	    	tempSysunitEntity.setName(equipTreeEntity.getName());
	    	treeNodeList.add(tempSysunitEntity);
	    }
	    //设备树
		model.put("sysunit", JsonUtil.toJson(treeNodeList));
		
		//设备类型
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE",FieldTypeEnum.STRING, MatchTypeEnum.EQ,EquipTreeEnum.EQUIPTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitieslist  = sysDictionaryService.findByCondition(conditions, null);
        ComboboxVO comboequipType = new ComboboxVO();
        for(SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitieslist){
        	comboequipType.addOption(sysdictionaryentity.getCode().toString(), sysdictionaryentity.getName());
        }
		request.setAttribute("equipType", JsonUtil.toJson(comboequipType.getOptions()));
        //设备模版
        List<EquipModelEntity> equipModellist = equipModelService.findAll();
        ComboboxVO equipModelcombobox= new ComboboxVO();
        for(EquipModelEntity equipModelEntity : equipModellist){
        	equipModelcombobox.addOption(equipModelEntity.getId().toString(), equipModelEntity.getModelNumber());
        }
        model.put("equipModel", JsonUtil.toJson(equipModelcombobox.getOptions()));
		return this.createModelAndView("equip/equipledger/equipLedgerAdd", model);
	}

	/**
	 * @Description:   新增 
	 * @author         wangcc 
	 * @Date           2017年7月27日 下午10:48:21 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/addEquimentLedger", method = RequestMethod.POST)
	public @ResponseBody ResultObj addEquimentLedger(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		return equipLedgerService.addEquimentLedger(params,request);
	}
	
	/**
	 * @Description:   修改
	 * @author         wangcc 
	 * @Date           2017年7月27日 下午10:48:21 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateEquimentLedger", method = RequestMethod.POST)
	public @ResponseBody ResultObj updateEquimentLedger(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		return equipLedgerService.updateEquimentLedger(params,request);
	}
	
	
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getModifyPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//组织机构(父级节点)
		model.put("sysunit", JsonUtil.toJson(treeNodeList));
		//设备类型
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE",FieldTypeEnum.STRING, MatchTypeEnum.EQ,EquipTreeEnum.EQUIPTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesList  = sysDictionaryService.findByCondition(conditions, null);
        ComboboxVO comboequipType = new ComboboxVO();
        for(SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesList){
        	comboequipType.addOption(sysdictionaryentity.getCode().toString(), sysdictionaryentity.getName());
        }
		request.setAttribute("equipType", JsonUtil.toJson(comboequipType.getOptions()));
		//设备模版
		List<EquipModelEntity> equipModellist = equipModelService.findAll();
		ComboboxVO equipModelcombobox= new ComboboxVO();
		for(EquipModelEntity equipModelEntity : equipModellist){
			equipModelcombobox.addOption(equipModelEntity.getId().toString(), equipModelEntity.getModelNumber());
		}
		model.put("equipModel", JsonUtil.toJson(equipModelcombobox.getOptions()));
		//设备信息
		EquipLedgerEntity  equipLedgerEntity  = equipLedgerService.findById(id);
		model.put("equipLedgerEntity", equipLedgerEntity);
		
		return this.createModelAndView("equip/equipledger/equipLedgerEdit", model);
	}
	

	/**
	 * @Description:   选择带回 
	 * @author         changl 
	 * @Date           2017年6月26日 下午2:30:45 
	 * @throws         Exception
	 */
	@RequestMapping("/selectEquipLedger")
	public ModelAndView userSelectRevalBox(HttpServletRequest request){
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
		return new ModelAndView("equip/equipledger/selectEquipLedger",resultMap);
	}
	/**
	 * @Description:   选择带回 
	 * @author         changl 
	 * @Date           2017年6月26日 下午2:30:45 
	 * @throws         Exception
	 */
	@RequestMapping("/selectEquipLedgerProtect")
	public ModelAndView selectEquipLedgerProtect(HttpServletRequest request){
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
		String id = request.getParameter("id");
		if(id!=null){
			String userUnitRels = protectService.findByEquipId(id);
			resultMap.put("userUnitRels", userUnitRels);
		}else{
			resultMap.put("userUnitRels", "[]");
		}
		return new ModelAndView("equip/equipledger/selectEquipLedger",resultMap);
	}
	
	/**
	 * @Description:   选择带回 
	 * @author         changl 
	 * @Date           2017年6月26日 下午2:30:45 
	 * @throws         Exception
	 */
	@RequestMapping("/selectEquipLedger/{unitId}")
	public ModelAndView userSelectRevalBox(HttpServletRequest request,@PathVariable Long unitId){
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
		conditions.add(new Condition("C_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, unitId));
		List<SysUnitEntity> sysunitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO comboboxVO = new ComboboxVO();
		for (SysUnitEntity sysUnitEntity : sysunitList) {
			comboboxVO.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName().toString());
			
		}
		resultMap.put("equipUnit", JsonUtil.toJson(comboboxVO.getOptions()));
		//列表中已存在的数据id
		String[] ids = request.getParameterValues("ids");
		resultMap.put("ids", JsonUtil.toJson(ids));
		return new ModelAndView("equip/equipledger/selectEquipLedger",resultMap);
	}
	
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value = "/dataList/{pathCode}/{id}")
	public @ResponseBody ResultListObj List(HttpServletRequest request, @RequestBody Map<String, Object> params,@PathVariable String pathCode,@PathVariable long id) {
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		//获取台账所有子设备
		List<EquipTreeEntity> equiptreeList = equipTreeService.findChildTreeById(id);
		if(equiptreeList.size()>1){
			conditions.add(new Condition(" C_PATH_CODE LIKE '%"+pathCode+"-%'"+" OR C_PATH_CODE = '"+pathCode+"'"));
		}else{
			conditions.add(new Condition("C_PATH_CODE", FieldTypeEnum.STRING, MatchTypeEnum.LIKE, pathCode));
		}
		conditions.add(new Condition("L.C_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, EquipTreeEnum.NORMAL.getId()));
		conditions.add(new Condition("TR.C_TREE_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ, EquipTreeEnum.PHYSICSEQUIP.getId()));
		ResultListObj resultObj = new ResultListObj();
		Page<EquipLedgerEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("createDate"));
		//获取所有可用的设备
		List<EquipLedgerEntity> equipLedgerEntities = equipLedgerService.findByCondition(conditions, page);
		resultObj.setDraw((Integer)params.get("draw"));
		if (equipLedgerEntities != null) {
			resultObj.setData(equipLedgerEntities);
			resultObj.setRecordsTotal(page.getTotal());
		}
		return resultObj;
	}
	
	
	
	/**
	 * 设备下拉树
	 */
	@RequestMapping(value = "/equipLedgerTreeList/{unitId}")
	public List<EquipLedgerEntity> getEquipLedgerTreeList(HttpServletRequest request, @PathVariable Long unitId) {
		return equipLedgerService.getEquipLedgerTreeList(unitId);
	}
	
	/**
	 * 设备导入跳转
	 */
	@RequestMapping(value = "/importEquipledgerInit")
	public  ModelAndView importEquipledgerInit(HttpServletRequest request) {
		return new ModelAndView("equip/equipledger/equipLedgerUploadExcel",null);
	}
	/**
	 * 设备导入
	 * @throws Exception 
	 */
	@RequestMapping(value = "/importEquipledger")
	public  @ResponseBody ResultListObj importEquipledger(HttpServletRequest request,@RequestParam(value="filename") MultipartFile file) throws Exception {
		ResultListObj resultObj = new ResultListObj();
		String location = request.getSession().getServletContext().getRealPath("upload");
		String OriginalFilename = file.getOriginalFilename();
		String filePath = location+"/"+System.currentTimeMillis()+OriginalFilename;
		File uploadFile = new File(filePath);
		if(!uploadFile.isDirectory()){
			uploadFile.mkdir();
		}
		//写入文件
		file.transferTo(uploadFile);
		equipLedgerService.importEquipLedger(request,uploadFile,OriginalFilename);
		return resultObj;
	}
	/**
	 * 
	 * 选择设备（去重）
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 上午10:25:33
	 * @lastModified
	 */
	@RequestMapping("/searchEquip")
	public @ResponseBody ResultListObj searchEquip(HttpServletRequest request,@RequestBody Map<String, Object> params){
		ResultListObj resultListObj = new ResultListObj();
		Page<EquipLedgerEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.desc("id"));//zzq修改为了弹出框是正序的(ly根据测试新的要求，改为倒序)
		//接收前台传送已有数据的物资id
		String ids = request.getParameter("ids");
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<EquipLedgerEntity> equipLedgerList = equipLedgerService.findByCondition(conditions, pages);
		resultListObj.setDraw((Integer) params.get("draw"));
		if (equipLedgerList != null) {
			resultListObj.setData(equipLedgerList);
			if (pages != null) {
				resultListObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultListObj;
	}
	
}