package com.aptech.business.run.dispaCom.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.business.run.dispaCom.domain.DispaComEntity;
import com.aptech.business.run.dispaCom.service.DispaComService;
import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.business.run.runLog.service.RunLogService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
import com.aptech.common.template.listForm.service.ListFormService;
import com.aptech.common.template.listForm.vo.AppListFormVO;
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
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 调度命令配置控制器
 *
 * @author 
 * @created 2017-06-07 11:31:01
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/dispaCom")
public class DispaComController extends BaseController<DispaComEntity> {
	
	@Autowired
	private DispaComService dispaComService;
	@Autowired
    private ListFormService listFormService;
	@Autowired
    private RunLogService runLogService;
	@Autowired
    private SysUnitService sysUnitService;
	@Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDutiesDetailService sysDutiesDetailService;
    @Autowired
	private MessageCenterService messageCenterService;
    @Autowired
    private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<DispaComEntity> getService() {
		return dispaComService;
	}
	
	/**
	 *	list页面跳转
	 * @param <T>
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index")
	public <T> ModelAndView list(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		model.put("SysUserEntity", userEntity);
		//单位下拉列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_ORGANIZATION",FieldTypeEnum.INT,MatchTypeEnum.EQ,1));
		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
       model.put("dispaComTreeList", JsonUtil.toJson(companyVo.getOptions()));
//        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		model.put("dispaComTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboDispaComVO = new ComboboxVO();
        List<Condition> conditions=new ArrayList<Condition>();
//        conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,"11"));
         List<SysDutiesDetailEntity> sysDutiesDetailEntityList = sysDutiesDetailService.findByCondition(conditions, null);
         String userIdstr="";
         for(int i=0;i<sysDutiesDetailEntityList.size();i++){
             SysDutiesDetailEntity sysDutiesDetailEntity = sysDutiesDetailEntityList.get(i);
             List<Condition> condition = new ArrayList<Condition>();
             Long userUnitid = Long.parseLong(sysDutiesDetailEntity.getUserUnitRelId());
             condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
             List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
             for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
             	userIdstr+= tempuserUnitRel.getUserId()+",";
             }
         }
         conditions.clear();
         conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.IN,userIdstr.split(",")));
         List<SysUserEntity> SysUserEntityList =sysUserService.findByCondition(conditions, null);
         for(SysUserEntity sysUserEntity : SysUserEntityList){
             comboDispaComVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
         }
        model.put("dispaComCombobox", JsonUtil.toJson(comboDispaComVO.getOptions()));
        //编码类型
        ComboboxVO protectWayCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("DISPATCH_UNIT");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            protectWayCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("dispatchCombobox", JsonUtil.toJson(protectWayCombobox.getOptions()));
        conditions.clear();
	    conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.IN,userIdstr.split(",")));
	    for(SysUserEntity sysUserEntity : SysUserEntityList){
	        comboDispaComVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	    }
	    model.put("sysUserCombobox", JsonUtil.toJson(comboDispaComVO.getOptions()));
	    ComboboxVO comboProtectVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
 		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
        for(SysUserEntity sysUserEntity : allUsers){
            comboProtectVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("searchPerson", JsonUtil.toJson(comboProtectVO.getOptions()));
        //查询当前运行日志信息
        Page<T> page = new Page<T>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
        conditions.clear();
        conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,"-1"));
        List<RunLogEntity> runLogEntities =  (List<RunLogEntity>)runLogService.findByCondition(conditions, page);
        if(!runLogEntities.isEmpty()){
        	RunLogEntity runLogEntity = runLogEntities.get(0);
        	model.put("currentDutyPersonId", runLogEntity.getChargeId());
        	model.put("currentDutyDate", runLogEntity.getDateString());
        	model.put("RLId", runLogEntity.getId());
        }
        return this.createModelAndView("run/dispaCom/dispaComManageList", model);
	}
	
	/**
	 * list数据查询
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/seach/{appId}", method = RequestMethod.POST)
	public @ResponseBody ResultListObj commonList(HttpServletRequest request,
			@RequestBody Map<String, Object> params, @PathVariable Long appId) {
		AppListFormVO appListFormVO = listFormService.getAppListFormVOById(appId);
		Page<Map<String, Object>> page = PageUtil.getPage(params);
		List<Condition> conditions = new ArrayList<Condition>();
		if (params.get("conditions") != null) {
			conditions = OrmUtil.changeMapToCondition(params);
			if(!RequestContext.get().isDeveloperMode()){
				SysUserEntity sysUserEntity = (SysUserEntity)RequestContext.get().getUser();
				for(Condition condition: conditions){
					if(condition.getValue().toString().contains("${")){
						String flag = condition.getValue().toString();
						if(flag.equals("${currentUnitId}")){
							condition.setValue(sysUserEntity.getUnitId());
						}else if(flag.equals("${currentUserId}")){
							condition.setValue(sysUserEntity.getId());
						}else if(flag.equals("${currentRoleId}")){
							condition.setValue(sysUserEntity.getRoleIds());
						}
					}
				}
			}else{
				for(Condition condition: conditions){
					if(condition.getValue().toString().contains("${")){
						String flag = condition.getValue().toString();
						if(flag.equals("${currentUnitId}")){
							condition.setValue(0);
						}else if(flag.equals("${currentUserId}")){
							condition.setValue(0);
						}else if(flag.equals("${currentRoleId}")){
							condition.setValue(0);
						}
					}
				}
			}
		}
		List<Sort> orders = OrmUtil.changeMapToOrders(params);
		if (page != null) {
			page.setOrders(orders);
		}
		List<Map<String, Object>> entities = listFormService.findListByCondition(appListFormVO, conditions, page);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
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
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		model.put("SysUserEntity", userEntity);
		//单位下拉列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_ORGANIZATION",FieldTypeEnum.INT,MatchTypeEnum.EQ,1));
		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
       model.put("dispaComTreeList", JsonUtil.toJson(companyVo.getOptions()));
//        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		//TODO下拉树具体内容根据具体业务定制
//		model.put("dispaComTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboDispaComVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,"11"));
        List<SysDutiesDetailEntity> sysDutiesDetailEntityList = sysDutiesDetailService.findByCondition(conditions, null);
        String userIdstr="";
        for(int i=0;i<sysDutiesDetailEntityList.size();i++){
             SysDutiesDetailEntity sysDutiesDetailEntity = sysDutiesDetailEntityList.get(i);
             List<Condition> condition = new ArrayList<Condition>();
             Long userUnitid = Long.parseLong(sysDutiesDetailEntity.getUserUnitRelId());
             condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
             List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
             for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
             	userIdstr+= tempuserUnitRel.getUserId()+",";
             }
        }
        conditions.clear();
        conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.IN,userIdstr.split(",")));
        List<SysUserEntity> SysUserEntityList =sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : SysUserEntityList){
             comboDispaComVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
		model.put("sysUserCombobox", JsonUtil.toJson(comboDispaComVO.getOptions()));
		//编码类型
        ComboboxVO protectWayCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("DISPATCH_UNIT");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            protectWayCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("dispatchCombobox", JsonUtil.toJson(protectWayCombobox.getOptions())); 
       //默认当前时间
        DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
        String cTime = sdf.format(new Date());
        model.put("cTime",cTime);
		return this.createModelAndView("run/dispaCom/dispaComAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		SysUserEntity userEntity = RequestContext.get().getUser();
		model.put("SysUserEntity", userEntity);
		// 返回前台数据项
		DispaComEntity dispaComEntity = (DispaComEntity)dispaComService.findById(id);
		model.put("dataMap", dispaComEntity);
		model.put("dataMapJson", JsonUtil.toJson(dispaComEntity));
		//单位下拉列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_ORGANIZATION",FieldTypeEnum.INT,MatchTypeEnum.EQ,1));
		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
       model.put("dispaComTreeList", JsonUtil.toJson(companyVo.getOptions()));
//        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		//TODO下拉树具体内容根据具体业务定制
//		model.put("dispaComTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboDispaComVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> conditions=new ArrayList<Condition>();
	    conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,"11"));
	    List<SysDutiesDetailEntity> sysDutiesDetailEntityList = sysDutiesDetailService.findByCondition(conditions, null);
	    String userIdstr="";
	    for(int i=0;i<sysDutiesDetailEntityList.size();i++){
	        SysDutiesDetailEntity sysDutiesDetailEntity = sysDutiesDetailEntityList.get(i);
	        List<Condition> condition = new ArrayList<Condition>();
	        Long userUnitid = Long.parseLong(sysDutiesDetailEntity.getUserUnitRelId());
	        condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
	        List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
	        for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
	        	userIdstr+= tempuserUnitRel.getUserId()+",";
	        }
	    }
	    conditions.clear();
	    conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.IN,userIdstr.split(",")));
	    List<SysUserEntity> SysUserEntityList =sysUserService.findByCondition(conditions, null);
	    for(SysUserEntity sysUserEntity : SysUserEntityList){
	        comboDispaComVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	    }
	    model.put("sysUserCombobox", JsonUtil.toJson(comboDispaComVO.getOptions()));
		//编码类型
        ComboboxVO protectWayCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("DISPATCH_UNIT");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            protectWayCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("dispatchCombobox", JsonUtil.toJson(protectWayCombobox.getOptions())); 
		return this.createModelAndView("run/dispaCom/dispaComEdit", model);
	}
	/**
	 *  运行日志关联调度命令列表页面初始化
	 */
	@RequestMapping("/disComList/{RLId}")
	public ModelAndView disComList(HttpServletRequest request, @PathVariable Long RLId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("dispaComTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboDispaComVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("DISPATCH_UNIT");
		
		for(String key :  codeDateTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
			comboDispaComVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		  ComboboxVO comboProtectVO = new ComboboxVO();
	        //TODO下拉框具体内容根据具体业务定制
		  // 人员
	 		List<Condition> userCondition = new ArrayList<Condition>();
	 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
	 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
	        for(SysUserEntity sysUserEntity : allUsers){
	            comboProtectVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	        }
	        model.put("searchPerson", JsonUtil.toJson(comboProtectVO.getOptions()));
		model.put("dispaComCombobox", JsonUtil.toJson(comboDispaComVO.getOptions()));
		model.put("RLId", RLId);
		return this.createModelAndView("run/dispaCom/dispaComList", model);
	}
	/**
     *  运行日志关联调度命令列表页面初始化
     */
    @RequestMapping("/detail/{RLId}")
    public ModelAndView detail(HttpServletRequest request, @PathVariable Long RLId){
        Map<String, Object> model = new HashMap<String, Object>();
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
        //TODO下拉树具体内容根据具体业务定制
        model.put("dispaComTreeList", JsonUtil.toJson(treeNodeList));
        ComboboxVO comboDispaComVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("DISPATCH_UNIT");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            comboDispaComVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("dispaComCombobox", JsonUtil.toJson(comboDispaComVO.getOptions()));
        model.put("RLId", RLId);
        return this.createModelAndView("run/dispaCom/dispaComDetail", model);
    }
    /**
     *  运行日志关联调度命令列表页面
     */
    @RequestMapping(value = "/searchDetail/{RLId}", method = RequestMethod.POST)
    public @ResponseBody ResultListObj searchDetail(HttpServletRequest request, @PathVariable Long RLId, @RequestBody Map<String, Object> params){
        Page<DispaComEntity> page = PageUtil.getPage(params);
        page.setPageSize(Integer.MAX_VALUE);        
        page.setOrders(OrmUtil.changeMapToOrders(params));
        RunLogEntity runLogEntity =(RunLogEntity)runLogService.findById(RLId);
        String startTime="";
        String endTime="";
        /**
         * 根据班次不同，开始与结束时间两个参数传值不同
         */
        List<Condition> conditions = OrmUtil.changeMapToCondition(params);
        DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
        String dateStr = df.format(runLogEntity.getDate());
        String dateStrGiveDate=null;
        conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, dateStr));
        if(runLogEntity.getGiveDate()!=null){
        	dateStrGiveDate = df.format(runLogEntity.getGiveDate());
        	conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LT, dateStrGiveDate));
        }
        /*原有查询条件
        startTime=dateStr+" 00:00:00";
        endTime=dateStr+" 08:00:00";
        conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, startTime));
        conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LT, endTime));*/
       
    	List<DispaComEntity> dispaComEntityList = dispaComService.findByCondition("findByCondition",conditions, page);
        ResultListObj resultObj = new ResultListObj();

        if (dispaComEntityList != null) {
            resultObj.setData(dispaComEntityList);
            if (page != null) {
                resultObj.setRecordsTotal(page.getTotal());
            }
        }
        resultObj.setDraw((Integer)params.get("draw"));
        return resultObj;
    }
    @RequestMapping(value = "/update/")
    public @ResponseBody
    ResultObj update(@RequestBody DispaComEntity dispaComEntity, HttpServletRequest request) throws Exception{
        ResultObj resultObj = new ResultObj();
        dispaComEntity.setUpdateDate(new Date());
        dispaComService.updateEntity(dispaComEntity);
        return resultObj;
    }
    /**
    * @Title: expData
    * @Description: excel导出
    * @author sunliang
    * @date 2017年7月20日下午4:03:11
    * @param req
    * @param res
    * @throws UnsupportedEncodingException
    * @throws
    */
    @RequestMapping("/exportExcel")
    public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
    	String RLId=req.getParameter("RLId");
    	RunLogEntity runLogEntity =(RunLogEntity)runLogService.findById(Long.parseLong(RLId));
    	 String startTime="";
         String endTime="";
         /**
          * 根据班次不同，开始与结束时间两个参数传值不同
          */
         DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
         String dateStr = df.format(runLogEntity.getDate());
         //早班
         if(runLogEntity.getTeamId()==1){
             startTime=dateStr+" 08:00:01";
             endTime=dateStr+" 16:00:00";
         } 
         //中班
         if(runLogEntity.getTeamId()==2){
             startTime=dateStr+" 16:00:01";
             endTime=dateStr+" 22:00:00";
         }
         //晚班
         if(runLogEntity.getTeamId()==3){
             startTime=dateStr+" 22:00:01";
             endTime=dateStr+" 08:00:00";
         }
         startTime=dateStr+" 00:00:00";
         endTime=dateStr+" 08:00:00";
         Page<DispaComEntity> page = new Page<DispaComEntity>();
         page.setPageSize(Integer.MAX_VALUE);        
         page.addOrder(Sort.desc("time"));
         List<Condition> conditions = OrmUtil.changeMapToCondition(params);
         df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
         startTime = df.format(runLogEntity.getDate());
//         String dateStrGiveDate=null;
         conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, startTime));
         if(runLogEntity.getGiveDate()!=null){
        	 endTime = df.format(runLogEntity.getGiveDate());
         	conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LT, endTime));
         }
//         List<Condition> conditions = OrmUtil.changeMapToCondition(params);
//         conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, startTime));
//         conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LT, endTime));
        
     	List<DispaComEntity> dispaComEntityList = dispaComService.findByCondition("findByCondition",conditions, page);
//		params.put("conditions", JSONArray.fromObject(conditions));
//        List<RunLogEntity> dataList=dispaComService.findByCondition(params, null);
        Map<String,Object> resultMap=new HashMap<String, Object>();
        resultMap.put("dataList", dispaComEntityList);
        ExcelUtil.export(req, res, "调度命令报表模板.xlsx","调度命令.xlsx", resultMap);
    }
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/selectindex")
	public ModelAndView selectindex(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("dispaComTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboDispaComVO = new ComboboxVO();
		  //TODO下拉框具体内容根据具体业务定制
        List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,"11"));
         List<SysDutiesDetailEntity> sysDutiesDetailEntityList = sysDutiesDetailService.findByCondition(conditions, null);
         String userIdstr="";
         for(int i=0;i<sysDutiesDetailEntityList.size();i++){
             SysDutiesDetailEntity sysDutiesDetailEntity = sysDutiesDetailEntityList.get(i);
             List<Condition> condition = new ArrayList<Condition>();
             Long userUnitid = Long.parseLong(sysDutiesDetailEntity.getUserUnitRelId());
             condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
             List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
             for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
             	userIdstr+= tempuserUnitRel.getUserId()+",";
             }
         }
         conditions.clear();
         conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.IN,userIdstr.split(",")));
         List<SysUserEntity> SysUserEntityList =sysUserService.findByCondition(conditions, null);
         for(SysUserEntity sysUserEntity : SysUserEntityList){
             comboDispaComVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
         }
        model.put("dispaComCombobox", JsonUtil.toJson(comboDispaComVO.getOptions()));
        //编码类型
        ComboboxVO protectWayCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("DISPATCH_UNIT");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            protectWayCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("dispatchCombobox", JsonUtil.toJson(protectWayCombobox.getOptions())); 
        return this.createModelAndView("run/dispaCom/selectDispaComManageList", model);
	}
	
	/**
	 * @Description: 调度添加
	 * @author changl
	 * @Date 2017年12月12日 上午10:31:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody
	ResultObj save(@RequestBody DispaComEntity dispaComEntity, HttpServletRequest request) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		dispaComEntity.setCreateDate(new Date());
		dispaComEntity.setCreateUserId(Integer.parseInt(userEntity.getId().toString()));
		dispaComEntity.setCreateUserName(userEntity.getName());
		MessageCenterEntity messageEntity =new MessageCenterEntity();
		messageEntity.setTitle("调度指令");
		messageEntity.setContext(dispaComEntity.getContactContent());
		messageEntity.setSendTime(new Date());
		messageEntity.setReceivePerson(dispaComEntity.getDispatchPerson());
		messageEntity.setSendPerson(dispaComEntity.getDutyChiefPersonName());
		messageEntity.setType("private");
		messageCenterService.addMessage(messageEntity);
		return dispaComService.add(dispaComEntity);
	}
	
	/**
     *  运行日志关联调度命令列表页面初始化
     */
    @RequestMapping("/dispaComdetail/{id}")
    public ModelAndView dispaComdetail(HttpServletRequest request,@PathVariable Long id){
    	Map<String, Object> model = new HashMap<String, Object>();
    	SysUserEntity userEntity = RequestContext.get().getUser();
		model.put("SysUserEntity", userEntity);
        DispaComEntity dispaComEntity = dispaComService.findById(id);
        List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
        List<DispaComEntity> dispaComEntities  = dispaComService.findByCondition(conditions, null);
        if(!dispaComEntities.isEmpty()){
        	DispaComEntity tempDispaComEntity = dispaComEntities.get(0);
        	dispaComEntity.setUnitName(tempDispaComEntity.getUnitName());
        	dispaComEntity.setDispathName(tempDispaComEntity.getDispathName());
        	dispaComEntity.setDutyChiefPersonName(tempDispaComEntity.getDutyChiefPersonName());
        }
        model.put("dataMap", dispaComEntity);
		model.put("dataMapJson", JsonUtil.toJson(dispaComEntity));
//		SysUserEntity sysUser = sysUserService.findById(Long.valueOf(dispaComEntity.getDutyChiefPerson()));
//		model.put("sysUser", sysUser);
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
 		//TODO下拉树具体内容根据具体业务定制
 		model.put("dispaComTreeList", JsonUtil.toJson(treeNodeList));
 		//编码类型
        ComboboxVO protectWayCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("DISPATCH_UNIT");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            protectWayCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("dispatchCombobox", JsonUtil.toJson(protectWayCombobox.getOptions())); 
        return this.createModelAndView("run/dispaCom/dispaComDetailPage", model);
    }
    
    /**
	 * @Description: 调度添加
	 * @author changl
	 * @Date 2017年12月12日 上午10:31:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/isAdd")
	public @ResponseBody
	Object isAdd(HttpServletRequest request) {
		return dispaComService.isAdd();
	}
	/**
	 * @Description:   导出
	 * @author         changl 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcelCom")
	public void exportExcelCom(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
//		String conditions=req.getParameter("conditions");
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		Page<DispaComEntity> page = new Page<DispaComEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("time"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<DispaComEntity> dataList=dispaComService.findByCondition(conditions, null);
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).setNumber(i+1);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "调度命令报表模板.xlsx","调度命令.xlsx", resultMap);
	}
	/**
	 * @Description:   查询
	 * @author         changl 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@RequestMapping("/searchDis")
	public @ResponseBody ResultListObj searchDis(@RequestBody Map<String, Object> params) {
		 ResultListObj resultObj = new ResultListObj();
		 Page<DispaComEntity> page = new Page<DispaComEntity>();
			page.setPageSize(Integer.MAX_VALUE);
			page.setOrders(OrmUtil.changeMapToOrders(params));
			page.addOrder(Sort.desc("id"));
			List<Condition> conditions = new ArrayList<Condition>();
			List<DispaComEntity> dispaComList = dispaComService.findByCondition(conditions, page);
			for(DispaComEntity dispaComEntity : dispaComList){
//				Pattern pattern = Pattern.compile("[0-9]*");
//				Matcher isNum = pattern.matcher(dispaComEntity.getDutyChiefPerson());
				Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$"); 
//				pattern.matcher(str).matches();
				if(pattern.matcher(dispaComEntity.getDutyChiefPerson()).matches()){
					SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(dispaComEntity.getDutyChiefPerson()));
					if(sysUserEntity!=null){
						dispaComEntity.setDutyChiefPersonString(sysUserEntity.getName());
						dispaComService.updateEntity(dispaComEntity);
					}	
				}
			}
	        if (dispaComList != null) {
	            resultObj.setData(dispaComList);
	        }
	        resultObj.setDraw((Integer)params.get("draw"));
	        return resultObj;
	}
}