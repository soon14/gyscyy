package com.aptech.business.wareHouse.wareHouse.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.cargo.inStock.domain.InstockEntity;
import com.aptech.business.cargo.inStock.service.InstockService;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.business.wareHouse.wareHouse.service.WareHouseService;
import com.aptech.business.wareHouse.wareHouseArea.domain.WareHouseAreaEntity;
import com.aptech.business.wareHouse.wareHouseArea.service.WareHouseAreaService;
import com.aptech.business.wareHouse.wareHouseAreaPosition.domain.WareHouseAreaPositionEntity;
import com.aptech.business.wareHouse.wareHouseAreaPosition.service.WareHouseAreaPositionService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
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
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;
import com.mxgraph.util.svg.ParseException;

/**
 * 
 * 仓库管理配置控制器
 *
 * @author 
 * @created 2017-11-03 16:51:10
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/wareHouse")
public class WareHouseController extends BaseController<WareHouseEntity> {
	
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private WareHouseAreaService wareHouseAreaService;
	@Autowired
	private WareHouseAreaPositionService wareHouseAreaPositionService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private InstockService instockService;
	@Autowired
	private UserUnitRelService userUnitRelService;

	@Override
	public IBaseEntityOperation<WareHouseEntity> getService() {
		return wareHouseService;
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
		//获取仓库类型
		model.put("wareHouseType", JsonUtil.toJson(wareHouseService.getWareHouseType().getOptions()));
		//获取启停用状态
		model.put("status", JsonUtil.toJson(wareHouseService.getStatus().getOptions()));
		//获取父仓库
		model.put("parentWareHouse", JsonUtil.toJson(wareHouseService.getParentWareHouse().getOptions()));
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		/*for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}*/
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//获取同一场站的库管员
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,81));
 		List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(conditions, null);
		List<String> userStr = new ArrayList<String>();
		for (SysDutiesDetailEntity usersp : sysDutiesDetailList) {
			List<Condition> condition = new ArrayList<Condition>();
			Long userUnitid = Long.parseLong(usersp.getUserUnitRelId());
			condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
			List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
			for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
				userStr.add(tempuserUnitRel.getUserId().toString());
			}
		}
		SysUserEntity userEntity= RequestContext.get().getUser();

		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING,MatchTypeEnum.IN, userStr.toArray()));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING,MatchTypeEnum.EQ, userEntity.getUnitId()));
		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		model.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
		return this.createModelAndView("wareHouse/wareHouse/wareHouseList", model);
	}
	
	/**
	 * 
	 * 列表检索
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月4日 上午10:54:13
	 * @lastModified
	 */
	@RequestMapping(value = "/data/list")
	public  @ResponseBody ResultListObj dataList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		Page<WareHouseEntity> pages=PageUtil.getPage(params);
		pages.addOrder(Sort.asc("id"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.LONG, MatchTypeEnum.EQ,'N'));
		List<WareHouseEntity> wareHouseEntities = wareHouseService.findByCondition(conditions, pages);
		for(WareHouseEntity wareHouseEntity:wareHouseEntities){
			String storeKeeper = wareHouseEntity.getStoreKeeper();
			if(storeKeeper.contains("[")){
				storeKeeper = storeKeeper.substring(1, storeKeeper.length()-1);
			}
			String[] ids = storeKeeper.toString().split(",");
			conditions.clear();
			conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN,ids));
			List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
			String userNames = "";
			for(int i=0;i<userList.size();i++){
				if(i==userList.size()-1){
					userNames+=userList.get(i).getName();
					wareHouseEntity.setStoreKeeper(userNames);
				}else{
					userNames+=userList.get(i).getName()+",";
					wareHouseEntity.setStoreKeeper(userNames);
				}
			}
		}
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (wareHouseEntities != null) {
			if (pages != null) {
				resultObj.setData(wareHouseEntities);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultObj;
	}
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//获取仓库类型
		model.put("wareHouseType", JsonUtil.toJson(wareHouseService.getWareHouseType().getOptions()));
		//获取启停用状态
		model.put("status", JsonUtil.toJson(wareHouseService.getStatus().getOptions()));
		//获取父仓库
		model.put("parentWareHouse", JsonUtil.toJson(wareHouseService.getParentWareHouse().getOptions()));
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		/*for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}*/
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//获取同一场站的库管员
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,43));
 		List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(conditions, null);
		List<String> userStr = new ArrayList<String>();
		for (SysDutiesDetailEntity usersp : sysDutiesDetailList) {
			List<Condition> condition = new ArrayList<Condition>();
			Long userUnitid = Long.parseLong(usersp.getUserUnitRelId());
			condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
			List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
			for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
				userStr.add(tempuserUnitRel.getUserId().toString());
			}
		}
		SysUserEntity userEntity= RequestContext.get().getUser();

		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING,MatchTypeEnum.IN, userStr.toArray()));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING,MatchTypeEnum.EQ, userEntity.getUnitId()));
		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		model.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
		return this.createModelAndView("wareHouse/wareHouse/wareHouseAdd", model);
	}
	
	/**
	 * 
	 * 添加保存
	 * 
	 * @param @param entityMap
	 * @param @return
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月4日 下午12:38:05
	 * @lastModified
	 */
	@RequestMapping("/saveAddPage")
	public @ResponseBody ResultObj saveAddPage(@RequestBody Map<String, Object> entityMap) throws ParseException{
		ResultObj resultObj = new ResultObj();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_WARE_HOUSE_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,entityMap.get("wareHouseName").toString()));
		conditions.add(new Condition("w.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,Long.valueOf(entityMap.get("unitId").toString())));
		List<WareHouseEntity> wareHouseEntities = wareHouseService.findByCondition(conditions, null);
		if(wareHouseEntities.size()>0){
			resultObj.setResult("exception");
			return resultObj;
		}
		//添加数据到仓库表
		WareHouseEntity wareHouseEntity = new WareHouseEntity();
		wareHouseEntity.setWareHouseName(entityMap.get("wareHouseName").toString());
		wareHouseEntity.setWareHouseType(entityMap.get("wareHouseType").toString());
		wareHouseEntity.setWareHouseAddress(entityMap.get("wareHouseAddress").toString());
		wareHouseEntity.setUnitId(Long.valueOf(entityMap.get("unitId").toString()));
		wareHouseEntity.setStatus(entityMap.get("status").toString());
		wareHouseEntity.setParentWareHouse(entityMap.get("parentWareHouse").toString());
		wareHouseEntity.setDeleteFlag("N");
		wareHouseEntity.setStoreKeeper(entityMap.get("storeKeeper").toString());
		wareHouseService.addEntity(wareHouseEntity);
		return resultObj;
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WareHouseEntity wareHouseEntity = (WareHouseEntity)wareHouseService.findById(id);
		model.put("entity", wareHouseEntity);
		model.put("entityJson", JsonUtil.toJson(wareHouseEntity));
		
		//获取仓库类型
		model.put("wareHouseType", JsonUtil.toJson(wareHouseService.getWareHouseType().getOptions()));
		//获取启停用状态
		model.put("status", JsonUtil.toJson(wareHouseService.getStatus().getOptions()));
		//获取父仓库
		model.put("parentWareHouse", JsonUtil.toJson(wareHouseService.getParentWareHouse().getOptions()));
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		/*for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}*/
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//获取同一场站的库管员
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,81));
 		List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(conditions, null);
		List<String> userStr = new ArrayList<String>();
		for (SysDutiesDetailEntity usersp : sysDutiesDetailList) {
			List<Condition> condition = new ArrayList<Condition>();
			Long userUnitid = Long.parseLong(usersp.getUserUnitRelId());
			condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
			List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
			for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
				userStr.add(tempuserUnitRel.getUserId().toString());
			}
		}
		SysUserEntity userEntity= RequestContext.get().getUser();

		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING,MatchTypeEnum.IN, userStr.toArray()));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING,MatchTypeEnum.EQ, userEntity.getUnitId()));
		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		model.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
		return this.createModelAndView("wareHouse/wareHouse/wareHouseEdit", model);
	}
	
	/**
	 * 
	 * 修改保存
	 * 
	 * @param @param entityMap
	 * @param @return
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月4日 下午12:52:22
	 * @lastModified
	 */
	@RequestMapping("/saveEditPage")
	public @ResponseBody ResultObj saveEditPage(@RequestBody Map<String, Object> entityMap) throws ParseException{
		ResultObj resultObj = new ResultObj();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_WARE_HOUSE_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,entityMap.get("wareHouseName").toString()));
		conditions.add(new Condition("w.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,Long.valueOf(entityMap.get("unitId").toString())));
		conditions.add(new Condition("w.C_ID", FieldTypeEnum.STRING, MatchTypeEnum.NE,entityMap.get("id").toString()));
		List<WareHouseEntity> wareHouseEntities = wareHouseService.findByCondition(conditions, null);
		if(wareHouseEntities.size()>0){
			resultObj.setResult("exception");
			return resultObj;
		}
		//添加数据到仓库表
		WareHouseEntity wareHouseEntity = new WareHouseEntity();
		wareHouseEntity.setId(Long.valueOf(entityMap.get("id").toString()));
		wareHouseEntity.setWareHouseName(entityMap.get("wareHouseName").toString());
		wareHouseEntity.setWareHouseType(entityMap.get("wareHouseType").toString());
		wareHouseEntity.setWareHouseAddress(entityMap.get("wareHouseAddress").toString());
		wareHouseEntity.setUnitId(Long.valueOf(entityMap.get("unitId").toString()));
		wareHouseEntity.setStatus(entityMap.get("status").toString());
		wareHouseEntity.setParentWareHouse(entityMap.get("parentWareHouse").toString());
		wareHouseEntity.setDeleteFlag("N");
		wareHouseEntity.setStoreKeeper(entityMap.get("storeKeeper").toString());
		wareHouseService.updateEntity(wareHouseEntity);
		return resultObj;
	}
	
	/**
	 * 
	 * 单行删除
	 * 
	 * @param @param id
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月4日 下午1:00:26
	 * @lastModified
	 */
	@RequestMapping("/deleteSingle/{id}")
    public @ResponseBody ResultObj deleteSingle(@PathVariable Long id){
		ResultObj resultObj = new ResultObj();
		List<Long> wareHouseIds=new ArrayList<Long>();
		List<InstockEntity> instockEntities = instockService.findAll();
		for(InstockEntity instockEntity:instockEntities){
			wareHouseIds.add(instockEntity.getWareHouseId());
		}
		if(!wareHouseIds.contains(id)){
			//删除仓库信息
			WareHouseEntity wareHouseEntity = wareHouseService.findById(id);
			wareHouseEntity.setDeleteFlag("Y");
			wareHouseService.updateEntity(wareHouseEntity);
			List<Condition> conditions =new ArrayList<Condition>();
			conditions.add(new Condition("a.C_WAREHOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseEntity.getId()));
			List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions, null);
			for(WareHouseAreaEntity wareHouseAreaEntity:wareHouseAreaEntities){
				wareHouseAreaEntity.setStatus("Y");
				wareHouseAreaService.updateEntity(wareHouseAreaEntity);
				conditions.clear();
				conditions.add(new Condition("p.C_GOOD_AREA_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseAreaEntity.getId()));
				List<WareHouseAreaPositionEntity> wareHouseAreaPositionEntities = wareHouseAreaPositionService.findByCondition(conditions, null);
				for(WareHouseAreaPositionEntity wareHouseAreaPositionEntity:wareHouseAreaPositionEntities){
					wareHouseAreaPositionEntity.setStatus("Y");
					wareHouseAreaPositionService.updateEntity(wareHouseAreaPositionEntity);
					resultObj.setResult("success");
				}
			}
		}else {
			resultObj.setResult("exception");
		}
    	return resultObj;
    }
	
	/**
	 * 
	 * 多行删除
	 * 
	 * @param @param request
	 * @param @param ids
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月4日 下午1:06:09
	 * @lastModified
	 */
	@RequestMapping("/deleteMulti/{ids}")
    public @ResponseBody ResultObj deleteMulti(HttpServletRequest request, @PathVariable String ids){
		ResultObj resultObj = new ResultObj();
		//删除仓库信息
		String[] wareHouseIdsArray = ids.split(",");
		List<String> instockWareHouseIds=new ArrayList<String>();
		List<InstockEntity> instockEntities = instockService.findAll();
		for(InstockEntity instockEntity:instockEntities){
			instockWareHouseIds.add(instockEntity.getWareHouseId().toString());
		}
		boolean isContain=true;
		for(int i=0;i<wareHouseIdsArray.length;i++){
			if(instockWareHouseIds.indexOf(wareHouseIdsArray[i])>=0){
				isContain=false;
				break;
			}
		}
		if(isContain==true){
			for(int i=0;i<wareHouseIdsArray.length;i++){
				WareHouseEntity wareHouseEntity=wareHouseService.findById(Long.valueOf(wareHouseIdsArray[i]));
				wareHouseEntity.setDeleteFlag("Y");
				wareHouseService.updateEntity(wareHouseEntity);
				List<Condition> conditions= new ArrayList<Condition>();
				conditions.add(new Condition("a.C_WAREHOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseIdsArray[i]));
				List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions, null);
				for(WareHouseAreaEntity wareHouseAreaEntity:wareHouseAreaEntities){
					wareHouseAreaEntity.setStatus("Y");
					wareHouseAreaService.updateEntity(wareHouseAreaEntity);
					conditions.clear();
					conditions.add(new Condition("p.C_GOOD_AREA_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseAreaEntity.getId()));
					List<WareHouseAreaPositionEntity> wareHouseAreaPositionEntities = wareHouseAreaPositionService.findByCondition(conditions, null);
					for(WareHouseAreaPositionEntity wareHouseAreaPositionEntity:wareHouseAreaPositionEntities){
						wareHouseAreaPositionEntity.setStatus("Y");
						wareHouseAreaPositionService.updateEntity(wareHouseAreaPositionEntity);
						resultObj.setResult("success");
					}
				}
			}
		}else{
			resultObj.setResult("exception");
		}
    	return resultObj;
    }
	
	/**
	 * 
	 * 改为启用状态
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @param status
	 * @param @param farmId
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月4日 下午1:50:29
	 * @lastModified
	 */
	@RequestMapping("/resultConfirmStart/{id}/{status}/{unitId}")
	public @ResponseBody ResultObj resultConfirmStart(HttpServletRequest request, @PathVariable Long id, @PathVariable Integer status, @PathVariable Long unitId){
		ResultObj resultObj = new ResultObj();
		WareHouseEntity wareHouseEntity = wareHouseService.findById(id);
//		int unitCount=wareHouseService.findUnitTotal(unitId);
//		if (status == 2&&unitCount==0) {
			wareHouseEntity.setStatus("1");
			wareHouseService.updateEntity(wareHouseEntity);
			resultObj.setResult("success");
//		}else {
//			resultObj.setResult("exception");
//		}
		return resultObj;
	}
	
	/**
	 * 
	 * 改为停用状态
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @param status
	 * @param @param farmId
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月4日 下午1:50:55
	 * @lastModified
	 */
	@RequestMapping("/resultConfirmStop/{id}/{status}/{unitId}")
	public @ResponseBody ResultObj resultConfirmStop(HttpServletRequest request, @PathVariable Long id, @PathVariable Integer status, @PathVariable Long unitId){
		ResultObj resultObj = new ResultObj();
		WareHouseEntity wareHouseEntity = wareHouseService.findById(id);
		wareHouseEntity.setStatus("2");
		wareHouseService.updateEntity(wareHouseEntity);
		return resultObj;
	}
	
	/**
	 * 
	 * 导出
	 * 
	 * @param @param req
	 * @param @param res
	 * @param @throws UnsupportedEncodingException
	 * @return void
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月4日 下午3:08:22
	 * @lastModified
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		String wareHouseName = req.getParameter("wareHouseName");
		String wareHouseAddress = req.getParameter("wareHouseAddress");
		String unitId = req.getParameter("unitId");
		String wareHouseType = req.getParameter("wareHouseType");
		String status = req.getParameter("status");
		Page<WareHouseEntity> pages = new Page<WareHouseEntity>();
		pages.addOrder(Sort.asc("id"));
		pages.setPageSize(Integer.MAX_VALUE);
		List<Condition> conditions=new ArrayList<Condition>();
		if(StringUtil.isNotEmpty(wareHouseName)){
			conditions.add(new Condition("w.C_WARE_HOUSE_NAME",FieldTypeEnum.STRING,MatchTypeEnum.LIKE,wareHouseName));
		}
		if(StringUtil.isNotEmpty(wareHouseAddress)){
			conditions.add(new Condition("w.C_WARE_HOUSE_ADDRESS",FieldTypeEnum.STRING,MatchTypeEnum.LIKE,wareHouseAddress));
		}
		if(StringUtil.isNotEmpty(unitId)&&!unitId.equals("undefined")){
			conditions.add(new Condition("w.C_UNIT_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,unitId));
		}
		if(StringUtil.isNotEmpty(wareHouseType)){
			conditions.add(new Condition("w.C_WARE_HOUSE_TYPE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,wareHouseType));
		}
		if(StringUtil.isNotEmpty(status)){
			conditions.add(new Condition("w.C_STATUS",FieldTypeEnum.STRING,MatchTypeEnum.EQ,status));
		}
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.LONG, MatchTypeEnum.EQ,'N'));
		List<WareHouseEntity> dataList=wareHouseService.findByCondition(conditions, pages);
		for(WareHouseEntity wareHouseEntity:dataList){
			String storeKeeper = wareHouseEntity.getStoreKeeper();
			if(storeKeeper.contains("[")){
				storeKeeper = storeKeeper.substring(1, storeKeeper.length()-1);
			}
			String[] ids = storeKeeper.toString().split(",");
			conditions.clear();
			conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN,ids));
			List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
			String userNames = "";
			for(int i=0;i<userList.size();i++){
				if(i==userList.size()-1){
					userNames+=userList.get(i).getName();
					wareHouseEntity.setStoreKeeper(userNames);
				}else{
					userNames+=userList.get(i).getName()+",";
					wareHouseEntity.setStoreKeeper(userNames);
				}
			}
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "仓库管理报表模板.xlsx","仓库管理.xlsx", resultMap);
	}
	
	@RequestMapping("/getGoodAreasByWareHouseId/{wareHouseId}")
	public @ResponseBody ComboboxVO getGoodAreasByWareHouseId(HttpServletRequest request, @PathVariable Long wareHouseId){
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, wareHouseId));
		List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions, null);
		ComboboxVO comboboxVO = new ComboboxVO();
		for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
			comboboxVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
		}
		
		return comboboxVO;
		
	}
	
	@RequestMapping("/getGoodPositionByGoodAreas/{wareAreaId}")
	public @ResponseBody ComboboxVO getGoodPositionByGoodAreas(HttpServletRequest request, @PathVariable Long wareAreaId){
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("p.C_GOOD_AREA_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, wareAreaId));
		List<WareHouseAreaPositionEntity> wareHouseAreaPositionEntities = wareHouseAreaPositionService.findByCondition(conditions, null);
		ComboboxVO comboboxVO = new ComboboxVO();
		for(WareHouseAreaPositionEntity wareHouseAreaPositionEntity :wareHouseAreaPositionEntities){
			comboboxVO.addOption(wareHouseAreaPositionEntity.getId().toString(), wareHouseAreaPositionEntity.getName());
		}
		return comboboxVO;
	}
	
	@RequestMapping("/getGoodPositionByGoodAreas")
	public @ResponseBody ComboboxVO getGoodPositionByGoodAreas(HttpServletRequest request){
		List<WareHouseAreaPositionEntity> wareHouseAreaPositionEntities = wareHouseAreaPositionService.findAll();
		ComboboxVO comboboxVO = new ComboboxVO();
		for(WareHouseAreaPositionEntity wareHouseAreaPositionEntity :wareHouseAreaPositionEntities){
			comboboxVO.addOption(wareHouseAreaPositionEntity.getId().toString(), wareHouseAreaPositionEntity.getName());
		}
		
		return comboboxVO;
		
	}
}