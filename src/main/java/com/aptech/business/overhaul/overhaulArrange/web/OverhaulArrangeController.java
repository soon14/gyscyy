package com.aptech.business.overhaul.overhaulArrange.web;

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

import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeActionEnum;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeEntity;
import com.aptech.business.overhaul.overhaulArrange.service.OverhaulArrangeService;
import com.aptech.business.overhaul.overhaulLog.service.OverhaulLogService;
import com.aptech.business.overhaul.overhaulLogDetail.domain.OverhaulLogDetailEntity;
import com.aptech.business.overhaul.overhaulLogDetail.service.OverhaulLogDetailService;
import com.aptech.business.overhaul.overhaulRecord.domain.OverhaulRecordEntity;
import com.aptech.business.overhaul.overhaulRecord.service.OverhaulRecordService;
import com.aptech.business.overhaul.overhaulWorkTask.domain.OverhaulWorkTaskEntity;
import com.aptech.business.overhaul.overhaulWorkTask.service.OverhaulWorkTaskService;
import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.business.wareHouse.wareHouse.service.WareHouseService;
import com.aptech.common.fourcode.service.FourCodeService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.workflow.definition.service.DefinitionService;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.processNodeAuth.service.ProcessNodeAuthService;
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
 * 工作安排配置控制器
 *
 * @author 
 * @created 2017-12-20 16:24:01
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/overhaulArrange")
public class OverhaulArrangeController extends BaseController<OverhaulArrangeEntity> {
	
	@Autowired
	private OverhaulArrangeService overhaulArrangeService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private FourCodeService fourCodeService;
	@Autowired
	private OverhaulLogService overhaulLogService;
	@Autowired
	private OverhaulLogDetailService overhaulLogDetailService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private OverhaulWorkTaskService overhaulWorkTaskService;
	@Autowired
	private OverhaulRecordService overhaulRecordService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Override
	public IBaseEntityOperation<OverhaulArrangeEntity> getService() {
		return overhaulArrangeService;
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
		List<OverhaulArrangeEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("overhaulArrangeTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOverhaulArrangeVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("overhaulArrangeCombobox", JsonUtil.toJson(comboOverhaulArrangeVO.getOptions()));
		return this.createModelAndView("overhaul/overhaulArrange/overhaulArrangeList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		//完成状态
        ComboboxVO finishStatusCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("OVERHUALARRANGE_FINISHSTATUS");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            finishStatusCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("overhaulArrangeFinishStatus", JsonUtil.toJson(finishStatusCombobox.getOptions()));
        //检修负责人拉列表
  		ComboboxVO dutyUserVo = new ComboboxVO();
  		List<Condition> dutyUserConditions = new ArrayList<Condition>();
  		dutyUserConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
  		dutyUserConditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
        List<SysUserEntity> userList = sysUserService.findByCondition(dutyUserConditions, null);
        for(SysUserEntity sysUserEntity : userList){
          	dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("dutyUsers", JsonUtil.toJson(dutyUserVo.getOptions()));
        
        DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
		model.put("yeardate", df.format(new Date()));
		
		return this.createModelAndView("overhaul/overhaulArrange/overhaulArrangeAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		//完成状态
		ComboboxVO finishStatusCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("OVERHUALARRANGE_FINISHSTATUS");
		for(String key :  codeDateTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
			finishStatusCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("overhaulArrangeFinishStatus", JsonUtil.toJson(finishStatusCombobox.getOptions()));
		//检修负责人拉列表
		ComboboxVO dutyUserVo = new ComboboxVO();
		List<Condition> dutyUserConditions = new ArrayList<Condition>();
		dutyUserConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));	
  		dutyUserConditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(dutyUserConditions, null);
		for(SysUserEntity sysUserEntity : userList){
			dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
		}
		model.put("dutyUsers", JsonUtil.toJson(dutyUserVo.getOptions()));
		OverhaulArrangeEntity overhaulArrangeEntity =  overhaulArrangeService.findById(id);
		model.put("overhaulArrangeEntity", overhaulArrangeEntity);
		return this.createModelAndView("overhaul/overhaulArrange/overhaulArrangeEdit", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEditHis/{id}")
	public ModelAndView getEditHis(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		//完成状态
		ComboboxVO finishStatusCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("OVERHUALARRANGE_FINISHSTATUS");
		for(String key :  codeDateTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
			finishStatusCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("overhaulArrangeFinishStatus", JsonUtil.toJson(finishStatusCombobox.getOptions()));
		//检修负责人拉列表
		ComboboxVO dutyUserVo = new ComboboxVO();
		List<Condition> dutyUserConditions = new ArrayList<Condition>();
		dutyUserConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));	
  		dutyUserConditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(dutyUserConditions, null);
		for(SysUserEntity sysUserEntity : userList){
			dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
		}
		model.put("dutyUsers", JsonUtil.toJson(dutyUserVo.getOptions()));
		OverhaulArrangeEntity overhaulArrangeEntity =  overhaulArrangeService.findById(id);
		model.put("overhaulArrangeEntity", overhaulArrangeEntity);
		return this.createModelAndView("overhaul/overhaulArrange/overhaulArrangeEditHis", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		//完成状态
        ComboboxVO finishStatusCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("OVERHUALARRANGE_FINISHSTATUS");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            finishStatusCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("overhaulArrangeFinishStatus", JsonUtil.toJson(finishStatusCombobox.getOptions()));
        //检修负责人拉列表
  		ComboboxVO dutyUserVo = new ComboboxVO();
  		List<Condition> dutyUserConditions = new ArrayList<Condition>();
  		dutyUserConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));	
  		dutyUserConditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
        List<SysUserEntity> userList = sysUserService.findByCondition(dutyUserConditions, null);
        for(SysUserEntity sysUserEntity : userList){
          	dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("dutyUsers", JsonUtil.toJson(dutyUserVo.getOptions()));
        OverhaulArrangeEntity overhaulArrangeEntity =  overhaulArrangeService.findById(id);
        model.put("overhaulArrangeEntity", overhaulArrangeEntity);
		return this.createModelAndView("overhaul/overhaulArrange/overhaulArrangeDetail", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/edit/{id}")
	public @ResponseBody ResultObj edit(@RequestBody OverhaulArrangeEntity overhaulArrangeEntity, @PathVariable Long id){
		return overhaulArrangeService.updEntity(overhaulArrangeEntity,id);
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/editHis/{id}")
	public @ResponseBody ResultObj editHis(@RequestBody OverhaulArrangeEntity overhaulArrangeEntity, @PathVariable Long id){
		return overhaulArrangeService.updEntityHis(overhaulArrangeEntity,id);
	}
	
	
	/**
	 * @Description:   配置设备检修纪录
	 * @author         wangcc 
	 * @Date           2018年1月14日 下午22:30:43 
	 * @throws         Exception
	 */
	@RequestMapping("/getInfo/{overhaulRecordId}/{action}")
	public ModelAndView  getEditMore(HttpServletRequest request,@PathVariable Long overhaulRecordId,@PathVariable String action){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		SysUnitEntity unitEntity = sysUnitService.findById(userEntity.getUnitId());
		SysUnitEntity companyEntity = sysUnitService.findById(unitEntity.getId());
		model.put("unitEntity", unitEntity);
		model.put("companyEntity", companyEntity);
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("D.C_OVERHAUL_RECORD_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,overhaulRecordId));
		List<OverhaulLogDetailEntity> detailEntity = overhaulLogDetailService.findByCondition(conditions, null);
		if(detailEntity.size()>0){
			model.put("overhaulLogDetailEntities", detailEntity.get(0));
		}else{
			OverhaulLogDetailEntity tempEntity = new OverhaulLogDetailEntity();
			tempEntity.setFileId("[]");
			model.put("overhaulLogDetailEntities", tempEntity);
		}
		
		//检修负责人拉列表
  		ComboboxVO dutyUserVo = new ComboboxVO();
  		conditions.clear();
  		conditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,unitEntity.getId()));
  		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : userList){
          	dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("dutyPersons", JsonUtil.toJson(dutyUserVo.getOptions()));
        //措施检查状态
        Map<String, SysDictionaryVO> powerTypeMap  =  DictionaryUtil.getDictionaries("CHECK_STATE");
		ComboboxVO powerTypeVO = new ComboboxVO();
		for(String key : powerTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = powerTypeMap.get(key);
			powerTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("checkState", JsonUtil.toJson(powerTypeVO.getOptions()));
		
		//完成状态
		ComboboxVO finishStatusCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("OVERHUALARRANGE_FINISHSTATUS");
		for(String key :  codeDateTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
			finishStatusCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("overhaulRecordId", overhaulRecordId);
		model.put("overhaulArrangeFinishStatus", JsonUtil.toJson(finishStatusCombobox.getOptions()));
		
		//仓库名称
		conditions.clear();
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		conditions.add(new Condition("w.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ,1));
		List<WareHouseEntity> wareHouseEntities=wareHouseService.findByCondition(conditions, null);
		ComboboxVO comboWareHouseVO = new ComboboxVO();
		for(WareHouseEntity wareHouseEntity:wareHouseEntities){
			comboWareHouseVO.addOption(wareHouseEntity.getId().toString(), wareHouseEntity.getWareHouseName());
		}
		model.put("wareHouseIds", JsonUtil.toJson(comboWareHouseVO.getOptions()));
		
		conditions.clear();
		conditions.add(new Condition("O.C_OVERHAUL_RECORD_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,overhaulRecordId));
		List<OverhaulWorkTaskEntity> workTaskEntities =  overhaulWorkTaskService.findByCondition("findByEquip",conditions, null);
		ComboboxVO comboVO = new ComboboxVO();
		for (OverhaulWorkTaskEntity overhaulWorkTaskEntity : workTaskEntities) {
			comboVO.addOption(overhaulWorkTaskEntity.getEquipId().toString(), overhaulWorkTaskEntity.getEquipName());
		}
		model.put("equipCombox", JsonUtil.toJson(comboVO.getOptions()));
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
	    OverhaulRecordEntity recordEntity =  overhaulRecordService.findById(overhaulRecordId);
	    model.put("recordEntity", recordEntity);
	    //获取关联设备
  		List<EquipLedgerEntity> templist = new ArrayList<EquipLedgerEntity>();
  		conditions.clear();
  		conditions.add(new Condition("O.C_OVERHAUL_RECORD_ID",FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulRecordId));
  		List<OverhaulWorkTaskEntity> list = overhaulWorkTaskService.findByCondition(conditions, null);
  		for(OverhaulWorkTaskEntity entity:list){
  			conditions.clear();
  			conditions.add(new Condition("L.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,entity.getEquipId()));
  			List<EquipLedgerEntity>  equipList = equipLedgerService.findByCondition(conditions, null);
  			if(!equipList.isEmpty()){
  				templist.add(equipList.get(0));
  			}
  		}
  		String redirectPath = "";
	    if(action.equals(OverhaulArrangeActionEnum.CONFIG.getCode())){
	    	redirectPath = "overhaul/overhaulArrange/overhaulArrangeConfigMore";
	    }else{
	    	redirectPath = "overhaul/overhaulArrange/overhaulArrangeDetailMore";
	    }
  		model.put("userUnitRels", JsonUtil.toJson(templist));
		return this.createModelAndView(redirectPath, model);
	}
	
	
	
	/**
	 * @Description:   批量删除
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2018年1月18日 上午09:37:58
	 * @throws         Exception
	 */
	@RequestMapping(value = "/batchDelete", method = RequestMethod.DELETE)
	public @ResponseBody <T> ResultObj batchDelete(@RequestBody List<Long> ids){
		ResultObj resultObj = new ResultObj();
		overhaulArrangeService.batchDelete(ids);
		return resultObj;
	}
	
	/**
	 * @Description:   单独删除
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2018年1月12日 上午11:47:58 
	 * @throws         Exception
	 */
	@RequestMapping("/deleteOnlyOne/{id}")
	public @ResponseBody <T> ResultObj deleteOnlyOne(HttpServletRequest request, @PathVariable Long id){
		ResultObj resultObj = new ResultObj();
		overhaulArrangeService.deleteOnlyOne(request,id);
		return resultObj;
	}
	
	
	
	/**
	 * @Description:   工作安排提交
	 * @author         wangcc 
	 * @Date           2017年12月27日 下午4:54:14 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/aloneSubmit/{overhaulLogId}/{overhaulArrangeId}/{taskId}/{procInstId}")
	public @ResponseBody ResultObj aloneSubmit(HttpServletRequest request,@PathVariable Long overhaulLogId,@PathVariable Long overhaulArrangeId,
			@PathVariable String taskId,@PathVariable String procInstId,@RequestBody Map<String, Object> params) {
		ResultObj resultObj = overhaulArrangeService.aloneSubmit(overhaulLogId,overhaulArrangeId,taskId,procInstId,params);
		return resultObj;
	}
	


	
	/**
	 * @Description:   检修日志修改初始化
	 * @author         wangcc 
	 * @Date           2018年1月15日 下午13:20:43 
	 * @throws         Exception
	 */
	@RequestMapping("/getDetailMoreHis/{overhaulLogId}/{overhaulArrangeId}/{overhaullogDate}")
	public ModelAndView  getDetailMoreHis(HttpServletRequest request,@PathVariable Long overhaulLogId,@PathVariable Long overhaulArrangeId,@PathVariable String overhaullogDate){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		SysUnitEntity unitEntity = sysUnitService.findById(userEntity.getUnitId());
		SysUnitEntity companyEntity = sysUnitService.findById(unitEntity.getId());
		model.put("unitEntity", unitEntity);
		model.put("companyEntity", companyEntity);
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_OVERHAUL_ARRANGE_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,overhaulArrangeId));
		//检修日志明细
		List<OverhaulLogDetailEntity> overhaulLogDetailEntities =  overhaulLogDetailService.findByCondition(conditions, null);
		if(!overhaulLogDetailEntities.isEmpty()){
			model.put("overhaulLogDetailEntities", overhaulLogDetailEntities.get(0));
		}else{
			OverhaulLogDetailEntity overhaulLogDetailEntity = new OverhaulLogDetailEntity();
			overhaulLogDetailEntity.setFileId("[]");
			model.put("overhaulLogDetailEntities", overhaulLogDetailEntity);
		}
		//检修负责人拉列表
  		ComboboxVO dutyUserVo = new ComboboxVO();
  		conditions.clear();
  		conditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,unitEntity.getId()));
  		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : userList){
          	dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("dutyPersons", JsonUtil.toJson(dutyUserVo.getOptions()));
        //措施检查状态
        Map<String, SysDictionaryVO> powerTypeMap  =  DictionaryUtil.getDictionaries("CHECK_STATE");
		ComboboxVO powerTypeVO = new ComboboxVO();
		for(String key : powerTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = powerTypeMap.get(key);
			powerTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("checkState", JsonUtil.toJson(powerTypeVO.getOptions()));
		//完成状态
		ComboboxVO finishStatusCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("OVERHUALARRANGE_FINISHSTATUS");
		for(String key :  codeDateTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
			finishStatusCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("overhaulArrangeFinishStatus", JsonUtil.toJson(finishStatusCombobox.getOptions()));
		model.put("overhaulLogId", overhaulLogId);
		model.put("overhaullogDate", overhaullogDate);
		model.put("overhaulArrangeId", overhaulArrangeId);
		return this.createModelAndView("overhaul/overhaulArrange/overhaulArrangeDetailMoreHis", model);
	}
	
}