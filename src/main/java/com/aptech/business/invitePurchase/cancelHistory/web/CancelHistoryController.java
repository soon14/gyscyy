package com.aptech.business.invitePurchase.cancelHistory.web;

import java.text.ParseException;
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

import com.aptech.business.contractManage.domain.ContractManageEntity;
import com.aptech.business.contractManage.service.ContractManageService;
import com.aptech.business.invitePurchase.cancelHistory.domain.CancelHistoryEntity;
import com.aptech.business.invitePurchase.cancelHistory.service.CancelHistoryService;
import com.aptech.business.invitePurchase.contractApprove.domain.ContractApproveEntity;
import com.aptech.business.invitePurchase.contractApprove.service.ContractApproveService;
import com.aptech.business.invitePurchase.contractDeal.domain.ContractDealEntity;
import com.aptech.business.invitePurchase.contractDeal.service.ContractDealService;
import com.aptech.business.invitePurchase.dealStandard.domain.DealStandardEntity;
import com.aptech.business.invitePurchase.dealStandard.service.DealStandardService;
import com.aptech.business.invitePurchase.produceReply.domain.ProduceReplyEntity;
import com.aptech.business.invitePurchase.produceReply.service.ProduceReplyService;
import com.aptech.business.managePlanContract.supplierManage.domain.SupplierManageEntity;
import com.aptech.business.managePlanContract.supplierManage.service.SupplierManageService;
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
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 撤回历史配置控制器
 *
 * @author 
 * @created 2018-09-10 13:41:56
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/cancelHistory")
public class CancelHistoryController extends BaseController<CancelHistoryEntity> {
	
	@Autowired
	private CancelHistoryService cancelHistoryService;
	@Autowired
	private ProduceReplyService produceReplyService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private DealStandardService dealStandardService;
	@Autowired
	private ContractApproveService contractApproveService;
	@Autowired
	private SupplierManageService supplierManageService;
	@Autowired
	private ContractDealService contractDealService;
	@Autowired
	private ContractManageService contractManageService;
	
	@Override
	public IBaseEntityOperation<CancelHistoryEntity> getService() {
		return cancelHistoryService;
	}
	
	/**
	* 跳转到撤回原因填写页面（通用）
	* @author ly
	* @date 2018年9月10日 下午3:06:37 
	* @param request
	* @param id
	* @return
	* @return ModelAndView
	*/
	@RequestMapping("/addHistoryMessage/{id}/{itemType}")
	public ModelAndView addProduceReplyHistory(HttpServletRequest request, @PathVariable String id, @PathVariable String itemType){
		Map<String, Object> model = new HashMap<String, Object>();
		//事项id
		model.put("itemId", id);
		//事项类型
		model.put("itemType", itemType);
		//当前登陆人
		SysUserEntity sysUserEntity=RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		//当前时间
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
        String dateStr = df.format(new Date());
        model.put("nowDate", dateStr);
        return this.createModelAndView("invitePurchase/cancelHistory/cancelMessage", model);
		
	}
	/**
	 * @Description:   撤回信息添加保存（通用）
	 * @author        ly
	 * @Date          
	 * @throws        
	 */
	@RequestMapping(value = "/savePage")
	public @ResponseBody ResultObj addSave(@RequestBody CancelHistoryEntity t, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		cancelHistoryService.addEntity(t);
		if("1".equals(t.getItemType())){
			//立项批复撤回
			ProduceReplyEntity reEntity = produceReplyService.findById(Long.valueOf(t.getItemId()));
			reEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
			produceReplyService.updateEntity(reEntity);
		}else if("2".equals(t.getItemType())){
			//定标请示函撤回
			DealStandardEntity deEntity = dealStandardService.findById(Long.valueOf(t.getItemId()));
			deEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
			dealStandardService.updateEntity(deEntity);
		}else if("3".equals(t.getItemType())){
			//合同审批撤回
			ContractApproveEntity caEntity = contractApproveService.findById(Long.valueOf(t.getItemId()));
			caEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
			contractApproveService.updateEntity(caEntity);
		}else{
			//合同签订撤回
			ContractDealEntity cdEntity = contractDealService.findById(Long.valueOf(t.getItemId()));
			cdEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
			contractDealService.updateEntity(cdEntity);
			ContractManageEntity cmEntity = contractManageService.findById(Long.valueOf(cdEntity.getContractManageId()));
			cmEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
			contractManageService.updateEntity(cmEntity);
		}
//		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.YEARPURCHASE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return resultObj;
	}
	/**
	* 跳转到撤回历史页面（通用）
	* @author ly
	* @date 2018年9月10日 下午3:06:37 
	* @param request
	* @param id
	* @return
	* @return ModelAndView
	*/
	@RequestMapping("/getHistoryList/{itemType}")
	public ModelAndView produceReplyHistory(HttpServletRequest request, @PathVariable String itemType){
		Map<String, Object> model = new HashMap<String, Object>();
        if("1".equals(itemType)){
        	//所属部门
    		ComboboxVO unitIdCombobox = new ComboboxVO();
      		List<Condition> conditions = new ArrayList<Condition>();
      		String [] organizationIds = {"1", "3", "4", "7"};
    		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
    		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
    		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
      		
      		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
      		
      		for(SysUnitEntity entity : unitList){
      			unitIdCombobox.addOption(entity.getId().toString(), entity.getName());
      		}
      		model.put("unitIdList", JsonUtil.toJson(unitIdCombobox.getOptions()));
            
      		//立项单位
      		ComboboxVO departmentIdCombobox = new ComboboxVO();
      		conditions.clear();
    		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
    		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
    		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.EQ, "5"));
      		
      		List<SysUnitEntity> departmentIdList = sysUnitService.findByCondition(conditions, null);
      		
      		for(SysUnitEntity entity : departmentIdList){
      			departmentIdCombobox.addOption(entity.getId().toString(), entity.getName());
      		}
      		model.put("departmentIdList", JsonUtil.toJson(departmentIdCombobox.getOptions()));
    		
    		//采购方式
    		ComboboxVO purchaseModeIdCombobox = new ComboboxVO();
    		Map<String, SysDictionaryVO> purchaseModeIdMap = DictionaryUtil.getDictionaries("PURCHASE_MODE");
    		for(String key : purchaseModeIdMap.keySet()){
    			SysDictionaryVO planTypeVO = purchaseModeIdMap.get(key);
    			purchaseModeIdCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
    		}
    		model.put("purchaseModeIdList", JsonUtil.toJson(purchaseModeIdCombobox.getOptions()));
    		
    		//事项类型
    		model.put("itemType", itemType);
    		
        	//跳转到立项批复撤回list页面
        	return this.createModelAndView("invitePurchase/cancelHistory/produceReplyHistory", model);
        }else if("2".equals(itemType)){
        	//跳转到定标请示函撤回list页面
        	//所属部门
    		ComboboxVO unitIdCombobox = new ComboboxVO();
      		List<Condition> conditions = new ArrayList<Condition>();
      		String [] organizationIds = {"1", "3", "4", "7"};
    		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
    		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
    		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
      		
      		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
      		
      		for(SysUnitEntity entity : unitList){
      			unitIdCombobox.addOption(entity.getId().toString(), entity.getName());
      		}
      		model.put("unitIdList", JsonUtil.toJson(unitIdCombobox.getOptions()));
            
      		//立项单位
      		ComboboxVO departmentIdCombobox = new ComboboxVO();
      		conditions.clear();
    		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
    		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
    		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.EQ, "5"));
      		
      		List<SysUnitEntity> departmentIdList = sysUnitService.findByCondition(conditions, null);
      		
      		for(SysUnitEntity entity : departmentIdList){
      			departmentIdCombobox.addOption(entity.getId().toString(), entity.getName());
      		}
      		model.put("departmentIdList", JsonUtil.toJson(departmentIdCombobox.getOptions()));
    		
    		//事项类型
    		model.put("itemType", itemType);
        	return this.createModelAndView("invitePurchase/cancelHistory/dealStandardHistory", model);
        }else if("3".equals(itemType)){
        	//跳转到合同审批撤回list页面
        	//所属部门
    		ComboboxVO unitIdCombobox = new ComboboxVO();
      		List<Condition> conditions = new ArrayList<Condition>();
      		String [] organizationIds = {"1", "3", "4", "7"};
    		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
    		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
    		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
      		
      		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
      		
      		for(SysUnitEntity entity : unitList){
      			unitIdCombobox.addOption(entity.getId().toString(), entity.getName());
      		}
      		model.put("unitIdList", JsonUtil.toJson(unitIdCombobox.getOptions()));
            
      		//供应商
      		ComboboxVO supplierIdCombobox = new ComboboxVO();
      		conditions.clear();
    		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.DELETE.ordinal())));
      		
      		List<SupplierManageEntity> supplierIdList = supplierManageService.findByCondition(conditions, null);
      		
      		for(SupplierManageEntity entity : supplierIdList){
      			supplierIdCombobox.addOption(entity.getId().toString(), entity.getName());
      		}
      		model.put("supplierIdList", JsonUtil.toJson(supplierIdCombobox.getOptions()));
    		
    		//采购方式
    		ComboboxVO purchaseModeIdCombobox = new ComboboxVO();
    		Map<String, SysDictionaryVO> purchaseModeIdMap = DictionaryUtil.getDictionaries("PURCHASE_MODE");
    		for(String key : purchaseModeIdMap.keySet()){
    			SysDictionaryVO planTypeVO = purchaseModeIdMap.get(key);
    			purchaseModeIdCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
    		}
    		model.put("purchaseModeIdList", JsonUtil.toJson(purchaseModeIdCombobox.getOptions()));
        	
    		//事项类型
    		model.put("itemType", itemType);
        	return this.createModelAndView("invitePurchase/cancelHistory/contractApproveHistory", model);
        }else{
        	//跳转到合同签订撤回list页面
        	//所属部门
    		ComboboxVO unitIdCombobox = new ComboboxVO();
      		List<Condition> conditions = new ArrayList<Condition>();
      		String [] organizationIds = {"1", "3", "4", "7"};
    		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
    		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
    		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
      		
      		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
      		
      		for(SysUnitEntity entity : unitList){
      			unitIdCombobox.addOption(entity.getId().toString(), entity.getName());
      		}
      		model.put("unitIdList", JsonUtil.toJson(unitIdCombobox.getOptions()));
            
      		//供应商
      		ComboboxVO supplierIdCombobox = new ComboboxVO();
      		conditions.clear();
    		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.DELETE.ordinal())));
      		
      		List<SupplierManageEntity> supplierIdList = supplierManageService.findByCondition(conditions, null);
      		
      		for(SupplierManageEntity entity : supplierIdList){
      			supplierIdCombobox.addOption(entity.getId().toString(), entity.getName());
      		}
      		model.put("supplierIdList", JsonUtil.toJson(supplierIdCombobox.getOptions()));
      		
      		//事项类型
    		model.put("itemType", itemType);
        	return this.createModelAndView("invitePurchase/cancelHistory/contractDealHistory", model);
        }
		
	}
	/**
	 * 立项批复list数据查询
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/seachProduceReply", method = RequestMethod.POST)
	public @ResponseBody ResultListObj seachProduceReply(HttpServletRequest request,
			@RequestBody Map<String, Object> params) throws ParseException {
		Page<CancelHistoryEntity> page = PageUtil.getPage(params);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<CancelHistoryEntity> entities = (List<CancelHistoryEntity>) this.getService().findByCondition("findLxByCondition",conditions, page);

		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			resultObj.setRecordsTotal((long) entities.size());
		}
		return resultObj;
	}
	/**
	 * 定标请示函list数据查询
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/seachDealStandard", method = RequestMethod.POST)
	public @ResponseBody ResultListObj seachDealStandard(HttpServletRequest request,
			@RequestBody Map<String, Object> params) throws ParseException {
		Page<CancelHistoryEntity> page = PageUtil.getPage(params);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<CancelHistoryEntity> entities = (List<CancelHistoryEntity>) this.getService().findByCondition("findDbByCondition",conditions, page);
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			resultObj.setRecordsTotal((long) entities.size());
		}
		return resultObj;
	}
	/**
	 * 合同审批list数据查询
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/seachContractApprove", method = RequestMethod.POST)
	public @ResponseBody ResultListObj seachContractApprove(HttpServletRequest request,
			@RequestBody Map<String, Object> params) throws ParseException {
		Page<CancelHistoryEntity> page = PageUtil.getPage(params);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<CancelHistoryEntity> entities = (List<CancelHistoryEntity>) this.getService().findByCondition("findSpByCondition",conditions, page);
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			resultObj.setRecordsTotal((long) entities.size());
		}
		return resultObj;
	}
	/**
	 * 合同签订list数据查询
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/seachContractDeal", method = RequestMethod.POST)
	public @ResponseBody ResultListObj seachContractDeal(HttpServletRequest request,
			@RequestBody Map<String, Object> params) throws ParseException {
		Page<CancelHistoryEntity> page = PageUtil.getPage(params);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<CancelHistoryEntity> entities = (List<CancelHistoryEntity>) this.getService().findByCondition("findQdByCondition",conditions, page);
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			resultObj.setRecordsTotal((long) entities.size());
		}
		return resultObj;
	}
	
	/**
	* 撤回历史详细信息查看（通用）
	* @author ly
	* @date 2018年9月11日 上午9:59:46 
	* @param request
	* @param id
	* @param itemType
	* @return
	* @return ModelAndView
	*/
	@RequestMapping("/getDetail/{id}/{itemType}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id, @PathVariable String itemType){
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("ca.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
        if("1".equals(itemType)){
        	//跳转到立项批复撤回详情页面
        	List<CancelHistoryEntity> canList = this.getService().findByCondition("findLxByCondition",conditions, null);
      		model.put("entity", canList.get(0));
        	return this.createModelAndView("invitePurchase/cancelHistory/produceReplyHistoryDetail", model);
        }else if("2".equals(itemType)){
        	//跳转到定标请示函撤回详情页面
        	List<CancelHistoryEntity> canList = this.getService().findByCondition("findDbByCondition",conditions, null);
      		model.put("entity", canList.get(0));
        	return this.createModelAndView("invitePurchase/cancelHistory/dealStandardHistoryDetail", model);
        }else if("3".equals(itemType)){
        	//跳转到合同审批撤回详情页面
        	conditions.clear();
        	conditions.add(new Condition("ch.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
        	List<CancelHistoryEntity> canList = this.getService().findByCondition("findSpByCondition",conditions, null);
      		model.put("entity", canList.get(0));
        	return this.createModelAndView("invitePurchase/cancelHistory/contractApproveHistoryDetail", model);
        }else{
        	//跳转到合同签订撤回详情页面
        	List<CancelHistoryEntity> canList = this.getService().findByCondition("findQdByCondition",conditions, null);
        	model.put("entity", canList.get(0));
        	return this.createModelAndView("invitePurchase/cancelHistory/contractDealHistoryDetail", model);
        }
        
		
	}

}