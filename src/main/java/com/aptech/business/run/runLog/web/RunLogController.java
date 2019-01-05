package com.aptech.business.run.runLog.web;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.dutyOrder.domain.DutyOrderEntity;
import com.aptech.business.dutyOrder.service.DutyOrderService;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
import com.aptech.business.run.runLog.dao.RunLogDao;
import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.business.run.runLog.service.RunLogService;
import com.aptech.business.run.runRecord.domain.RunRecordEntity;
import com.aptech.business.run.runRecord.domain.RunRecordEnum;
import com.aptech.business.run.runRecord.service.RunRecordService;
import com.aptech.business.run.safeMeeting.domain.SafeMeetingEntity;
import com.aptech.business.run.safeMeeting.service.SafeMeetingService;
import com.aptech.business.teamMemApp.domain.TeamMemAppEntity;
import com.aptech.business.teamMemApp.service.TeamMemAppService;
import com.aptech.business.ticketManage.operationTicket.service.OperationTicketService;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicket.service.WorkTicketService;
import com.aptech.business.util.ExcelUtilAll;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
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
 * 运行日志配置控制器
 *
 * @author 
 * @created 2017-06-05 10:52:45
 * @lastModified 
 * @history
 *
 */

@Controller
@RequestMapping("/runLog")
public class RunLogController extends BaseController<RunLogEntity> {
	@Autowired
	private RunLogService runLogService;
	@Autowired
    private SysUserService sysUserService;
	@Autowired
    private DutyOrderService dutyOrderService;
	@Autowired
    private TeamMemAppService teamMemAppService;
	@Autowired
    private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
    private WorkTicketService workTicketService;
	@Autowired
	private OperationTicketService operationTicketService;
	@Autowired
    private RunRecordService runRecordService;
	@Autowired
	private RunLogDao runLogDao;
	@Autowired
	private SafeMeetingService safeMeetingService;
	@Autowired
	private OperateLogService userOperateLogService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<RunLogEntity> getService() {
		return runLogService;
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
		List<RunLogEntity> treeNodeList = null; 
		model.put("runLogTreeList", JsonUtil.toJson(treeNodeList));
		//值长查询
        ComboboxVO comboChargeVO = new ComboboxVO();
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
	    SysUserEntity userEntity= RequestContext.get().getUser();
	    conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//	    conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
	    List<SysUserEntity> SysUserEntityList =sysUserService.findByCondition(conditions, null);
	    for(SysUserEntity sysUserEntity : SysUserEntityList){
	         comboChargeVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	    }
	    model.put("runLogCombobox", JsonUtil.toJson(comboChargeVO.getOptions()));
		ComboboxVO comboRunLogVO1 = new ComboboxVO();
	    List<TeamMemAppEntity> allTeamMemAppEntity = teamMemAppService.findAll();
	    for(TeamMemAppEntity teamMemAppEntity : allTeamMemAppEntity){
	         comboRunLogVO1.addOption(teamMemAppEntity.getId().toString(), teamMemAppEntity.getName());
	    }
	    model.put("teamMemCombobox", JsonUtil.toJson(comboRunLogVO1.getOptions()));
	    ComboboxVO comboRunLogVO4 = new ComboboxVO();
	    List<DutyOrderEntity> allDutyOrderEntity = dutyOrderService.findAll();       
	    for(DutyOrderEntity dutyOrderEntity : allDutyOrderEntity){
            comboRunLogVO4.addOption(dutyOrderEntity.getId().toString(), dutyOrderEntity.getName());
        }
	    model.put("orgAppCombobox", JsonUtil.toJson(comboRunLogVO4.getOptions()));
	    //编码类型
	    ComboboxVO grStateCombobox = new ComboboxVO();
	    Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("GR_STATE");
     
	    for(String key :  codeDateTypeMap.keySet()){
	    	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
	    	grStateCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
	    }
	    model.put("grStateCombobox", JsonUtil.toJson(grStateCombobox.getOptions()));
	    model.put("userEntity", userEntity);
	    ComboboxVO runCheckCombobox = new ComboboxVO();
	    Map<String, SysDictionaryVO> runCheckTypeMap  =  DictionaryUtil.getDictionaries("RUN_CHECK");
	    for(String key :  runCheckTypeMap.keySet()){
	         SysDictionaryVO sysDictionaryVO = runCheckTypeMap.get(key);
	         runCheckCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
	    }
	    model.put("runCheckCombobox", JsonUtil.toJson(runCheckCombobox.getOptions()));
		return this.createModelAndView("run/runLog/runLogList", model);
	}
	
	/**
	 * list数据查询
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/seach", method = RequestMethod.POST)
	public @ResponseBody ResultListObj commonList(HttpServletRequest request,
			@RequestBody Map<String, Object> params) {
	//	AppListFormVO appListFormVO = listFormService.getAppListFormVOById(43);
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
	//	List<Map<String, Object>> entities = listFormService.findListByCondition(appListFormVO, conditions, page);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
//		if (entities != null) {
//			resultObj.setData(entities);
//			if (page != null) {
//				resultObj.setRecordsTotal(page.getTotal());
//			} else {
//				resultObj.setRecordsTotal((long) entities.size());
//			}
//		}
		return resultObj;
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<RunLogEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("runLogTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboRunLogVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers = sysUserService.findByCondition(
				conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
            comboRunLogVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
		model.put("runLogCombobox", JsonUtil.toJson(comboRunLogVO.getOptions()));
		 ComboboxVO runCheckCombobox = new ComboboxVO();
         Map<String, SysDictionaryVO> runCheckTypeMap  =  DictionaryUtil.getDictionaries("RUN_CHECK");
         
         for(String key :  runCheckTypeMap.keySet()){
             SysDictionaryVO sysDictionaryVO = runCheckTypeMap.get(key);
             runCheckCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
         }
         model.put("runCheckCombobox", JsonUtil.toJson(runCheckCombobox.getOptions()));
		return this.createModelAndView("run/runLog/runLogAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		String responseUrl = "";
		Map<String, Object> model = new HashMap<String, Object>();
		//返回前台数据项
		RunLogEntity runLogEntity = (RunLogEntity)runLogService.findById(id);
		model.put("dataMap", runLogEntity);
		model.put("dataMapJson", JsonUtil.toJson(runLogEntity));
		List<RunLogEntity> treeNodeList = null; 
		model.put("runLogTreeList", JsonUtil.toJson(treeNodeList));
		//值长查询
        ComboboxVO comboChargeVO = new ComboboxVO();
        List<Condition> conditions=new ArrayList<Condition>();
        SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> SysUserEntityList = sysUserService.findByCondition(
				conditions, null);
        for(SysUserEntity sysUserEntity : SysUserEntityList){
             comboChargeVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
		model.put("chargeCombobox", JsonUtil.toJson(comboChargeVO.getOptions()));
		//值长下属人员(暂未控制)
		ComboboxVO comboRunLogVO = new ComboboxVO();       
        List<SysUserEntity> allUsers = sysUserService.findAll();
        for(SysUserEntity sysUserEntity : allUsers){
            comboRunLogVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("runLogCombobox", JsonUtil.toJson(comboRunLogVO.getOptions()));
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = format.format(new Date());
		model.put("giveDate", dateStr);
		//班次、值次.teamMem为班次
		ComboboxVO comboRunLogVO4 = new ComboboxVO();
		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,runLogEntity.getDutyId()));
	    conditions.add(new Condition("a.C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.EQ,userEntity.getUnitId()));
	    List<DutyOrderEntity> allDutyOrderEntity = dutyOrderService.findByCondition("findByCondition", conditions, null);	     
	    for(DutyOrderEntity dutyOrderEntity : allDutyOrderEntity){
	          comboRunLogVO4.addOption(dutyOrderEntity.getId().toString(), dutyOrderEntity.getName());
	    }
	    model.put("orgAppCombobox", JsonUtil.toJson(comboRunLogVO4.getOptions()));
        ComboboxVO comboRunLogVO1 = new ComboboxVO();
        List<DutyOrderEntity> allGiveDutyOrderEntity = dutyOrderService.findAll();
        for(DutyOrderEntity dutyOrderEntity : allGiveDutyOrderEntity){
            comboRunLogVO1.addOption(dutyOrderEntity.getId().toString(), dutyOrderEntity.getName());
        }
        model.put("giveorgAppCombobox", JsonUtil.toJson(comboRunLogVO1.getOptions()));
        ComboboxVO comboRunLogVO2 = new ComboboxVO();
        if(userEntity.getUnitId()==138){
	        conditions.clear();
	        Page<TeamMemAppEntity> page= new Page<TeamMemAppEntity>();
	        page.addOrder(Sort.asc("C_ID"));
	        List<TeamMemAppEntity> allTeamMemAppEntity = teamMemAppService.findByCondition(conditions, page);
	        for(int i=0;i<allTeamMemAppEntity.size();i++){
	        	comboRunLogVO2.addOption(allTeamMemAppEntity.get(i).getId().toString(), allTeamMemAppEntity.get(i).getName());
	        }
	        model.put("teamMemCombobox", JsonUtil.toJson(comboRunLogVO2.getOptions()));
        }
        ComboboxVO comboRunLogVO3 = new ComboboxVO();
        List<TeamMemAppEntity> allgiveTeamMemAppEntity = teamMemAppService.findAll();
        for(TeamMemAppEntity teamMemAppEntity : allgiveTeamMemAppEntity){
            comboRunLogVO3.addOption(teamMemAppEntity.getId().toString(), teamMemAppEntity.getName());
        }
        model.put("giveteamMemCombobox", JsonUtil.toJson(comboRunLogVO3.getOptions()));
        ComboboxVO runCheckCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> runCheckTypeMap  =  DictionaryUtil.getDictionaries("RUN_CHECK");
        
        for(String key :  runCheckTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = runCheckTypeMap.get(key);
            runCheckCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("runCheckCombobox", JsonUtil.toJson(runCheckCombobox.getOptions()));
        conditions.clear();
        conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,id));
        conditions.add(new Condition("a.C_RECORD_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.NE,RunRecordEnum.TELITEM));
        List<RunRecordEntity> recordEntities =  runRecordService.findByCondition(conditions, null);
        model.put("recordEntities", recordEntities);
        model.put("rlId", id);
        //用户所属单位判断
		return this.createModelAndView("run/runLog/runLogEdit", model);
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEditWind/{id}")
	public ModelAndView getEditWind(HttpServletRequest request, @PathVariable Long id){
		String responseUrl = "";
		Map<String, Object> model = new HashMap<String, Object>();
		//返回前台数据项
		RunLogEntity runLogEntity = (RunLogEntity)runLogService.findById(id);
		model.put("dataMap", runLogEntity);
		model.put("dataMapJson", JsonUtil.toJson(runLogEntity));
		List<RunLogEntity> treeNodeList = null; 
		model.put("runLogTreeList", JsonUtil.toJson(treeNodeList));
		//值长查询
		ComboboxVO comboChargeVO = new ComboboxVO();
		List<Condition> conditions=new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> SysUserEntityList = sysUserService.findByCondition(
				conditions, null);
		for(SysUserEntity sysUserEntity : SysUserEntityList){
			comboChargeVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
		}
		model.put("chargeCombobox", JsonUtil.toJson(comboChargeVO.getOptions()));
		//值长下属人员(暂未控制)
		ComboboxVO comboRunLogVO = new ComboboxVO();       
		List<SysUserEntity> allUsers = sysUserService.findAll();
		for(SysUserEntity sysUserEntity : allUsers){
			comboRunLogVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
		}
		model.put("runLogCombobox", JsonUtil.toJson(comboRunLogVO.getOptions()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateStr = format.format(new Date());
		model.put("giveDate", dateStr);
		//班次、值次.teamMem为班次
		ComboboxVO comboRunLogVO4 = new ComboboxVO();
		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,runLogEntity.getDutyId()));
		conditions.add(new Condition("a.C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<DutyOrderEntity> allDutyOrderEntity = dutyOrderService.findByCondition("findByCondition", conditions, null);	     
		for(DutyOrderEntity dutyOrderEntity : allDutyOrderEntity){
			comboRunLogVO4.addOption(dutyOrderEntity.getId().toString(), dutyOrderEntity.getName());
		}
		model.put("orgAppCombobox", JsonUtil.toJson(comboRunLogVO4.getOptions()));
		ComboboxVO comboRunLogVO1 = new ComboboxVO();
		List<DutyOrderEntity> allGiveDutyOrderEntity = dutyOrderService.findAll();
		for(DutyOrderEntity dutyOrderEntity : allGiveDutyOrderEntity){
			comboRunLogVO1.addOption(dutyOrderEntity.getId().toString(), dutyOrderEntity.getName());
		}
		model.put("giveorgAppCombobox", JsonUtil.toJson(comboRunLogVO1.getOptions()));
		ComboboxVO comboRunLogVO2 = new ComboboxVO();
//		if(userEntity.getUnitId()==138){
			conditions.clear();
			Page<TeamMemAppEntity> page= new Page<TeamMemAppEntity>();
			page.addOrder(Sort.asc("C_ID"));
			List<TeamMemAppEntity> allTeamMemAppEntity = teamMemAppService.findByCondition(conditions, page);
			for(int i=0;i<allTeamMemAppEntity.size();i++){
				comboRunLogVO2.addOption(allTeamMemAppEntity.get(i).getId().toString(), allTeamMemAppEntity.get(i).getName());
			}
			model.put("teamMemCombobox", JsonUtil.toJson(comboRunLogVO2.getOptions()));
//		}
		ComboboxVO comboRunLogVO3 = new ComboboxVO();
		List<TeamMemAppEntity> allgiveTeamMemAppEntity = teamMemAppService.findAll();
		for(TeamMemAppEntity teamMemAppEntity : allgiveTeamMemAppEntity){
			comboRunLogVO3.addOption(teamMemAppEntity.getId().toString(), teamMemAppEntity.getName());
		}
		model.put("giveteamMemCombobox", JsonUtil.toJson(comboRunLogVO3.getOptions()));
		ComboboxVO runCheckCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> runCheckTypeMap  =  DictionaryUtil.getDictionaries("RUN_CHECK");
		
		for(String key :  runCheckTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = runCheckTypeMap.get(key);
			runCheckCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("runCheckCombobox", JsonUtil.toJson(runCheckCombobox.getOptions()));
		conditions.clear();
		conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,id));
		conditions.add(new Condition("a.C_RECORD_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.NE,RunRecordEnum.TELITEM));
		List<RunRecordEntity> recordEntities =  runRecordService.findByCondition(conditions, null);
		model.put("recordEntities", recordEntities);
		model.put("rlId", id);
		int rid = id.intValue()-1;
		model.put("rid", rid);
		//用户所属单位判断
		return this.createModelAndView("run/runLog/runLogEditForWind", model);
	}
	/**
	 * 
	* @Title: 跳转到填报框架页
	* @Description: 跳转到填报框架页
	* @author sunliang
	* @param request
	* @param id
	* @param currentPage
	* @param pageSize
	* @return
	* @throws
	 */
	   @RequestMapping("/info/{id}")
	    public ModelAndView funPage(HttpServletRequest request, @PathVariable Long id){
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        resultMap.put("RLId", id);
	        SysUserEntity userEntity = RequestContext.get().getUser();
			userOperateLogService.addOperateLog(userEntity,OperateUserModuleEnum.RUNLOG.getName(),OperateUserEnum.ADDRUN.getName(),"",OperateUserEnum.ADDRUN.getId());
	        return new ModelAndView("run/runLog/infoFrame",resultMap);
	    }
	    /**
	    * @Title: detail
	    * @Description: 跳转到详细页
	    * @author sunliang
	    * @date 2017年7月31日下午3:24:04
	    * @param request
	    * @param RLId
	    * @return
	    * @throws
	    */
	    @RequestMapping("/detail/{RLId}")
	    public ModelAndView detail(HttpServletRequest request, @PathVariable Long RLId){
	        Map<String, Object> model = new HashMap<String, Object>();
	        // 返回前台数据项
	        RunLogEntity runLogEntity = (RunLogEntity)runLogService.findById(RLId);
            List<Condition> conditions=new ArrayList<Condition>();
            conditions.add(new Condition("a.C_DATE", FieldTypeEnum.INT, MatchTypeEnum.EQ,runLogEntity.getGiveDate()));
            List<RunLogEntity> runLogEntityList=runLogService.findByCondition(conditions, null);
            if(runLogEntityList.size()!=0){
                RunLogEntity runLogEntityTemp=runLogEntityList.get(0);
                runLogEntity.setGiveDate(runLogEntityTemp.getDate()); 
                runLogEntity.setGiveChargeId(runLogEntityTemp.getChargeId());
                runLogEntity.setGiveDutyId(runLogEntityTemp.getDutyId());
                runLogEntity.setGiveTeamId(runLogEntityTemp.getTeamId());
                runLogEntity.setGivePersonsIds(runLogEntityTemp.getPersonsIds());

            }            
	        model.put("dataMap", runLogEntity);
	        model.put("dataMapJson", JsonUtil.toJson(runLogEntity));
	        
	        List<RunLogEntity> treeNodeList = null; 
	        //TODO下拉树具体内容根据具体业务定制
	        model.put("runLogTreeList", JsonUtil.toJson(treeNodeList));
	        ComboboxVO comboRunLogVO = new ComboboxVO();
	        //TODO下拉框具体内容根据具体业务定制
	        List<SysUserEntity> allUsers = sysUserService.findAll();
	         for(SysUserEntity sysUserEntity : allUsers){
	             comboRunLogVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	         }
	        model.put("runLogCombobox", JsonUtil.toJson(comboRunLogVO.getOptions()));	  
	        /**
	         * 班次、值次.teamMem为班次
	         */
	          ComboboxVO comboRunLogVO1 = new ComboboxVO();
	        List<DutyOrderEntity> allDutyOrderEntity = dutyOrderService.findAll();
	        for(DutyOrderEntity dutyOrderEntity : allDutyOrderEntity){
	            comboRunLogVO1.addOption(dutyOrderEntity.getId().toString(), dutyOrderEntity.getName());
	        }
	        model.put("orgAppCombobox", JsonUtil.toJson(comboRunLogVO1.getOptions()));
	        ComboboxVO comboRunLogVO2 = new ComboboxVO();
	        List<TeamMemAppEntity> allTeamMemAppEntity = teamMemAppService.findAll();
	        for(TeamMemAppEntity teamMemAppEntity : allTeamMemAppEntity){
	            comboRunLogVO2.addOption(teamMemAppEntity.getId().toString(), teamMemAppEntity.getName());
	        }
	        model.put("teamMemCombobox", JsonUtil.toJson(comboRunLogVO2.getOptions()));
	        ComboboxVO runCheckCombobox = new ComboboxVO();
	         Map<String, SysDictionaryVO> runCheckTypeMap  =  DictionaryUtil.getDictionaries("RUN_CHECK");
	         
	         for(String key :  runCheckTypeMap.keySet()){
	             SysDictionaryVO sysDictionaryVO = runCheckTypeMap.get(key);
	             runCheckCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
	         }
	         model.put("runCheckCombobox", JsonUtil.toJson(runCheckCombobox.getOptions()));
	         model.put("rlId", RLId);
	        return this.createModelAndView("run/runLog/runLogDetail", model);
	    }
	    /**
	    * @Title: joinTeam
	    * @Description: 交接班
	    * @author sunliang
	    * @date 2017年7月31日下午3:24:27
	    * @param request
	    * @param runLogEntity
	    * @param id
	    * @return
	    * @throws Exception
	    * @throws
	    */
	    @RequestMapping("/joinTeam/{id}")
	    public @ResponseBody ResultObj joinTeam(HttpServletRequest request,@RequestBody RunLogEntity runLogEntity, @PathVariable Long id) throws Exception{
	        
	        return runLogService.joinTeam(runLogEntity);
	    }
	    /**
	     * @Title: joinTeam
	     * @Description: 场站运行日志交接班
	     * @author sunliang
	     * @date 2017年7月31日下午3:24:27
	     * @param request
	     * @param runLogEntity
	     * @param id
	     * @return
	     * @throws Exception
	     * @throws
	     */
	    @RequestMapping("/joinTeamWind/{id}")
	    public @ResponseBody ResultObj joinTeamWind(HttpServletRequest request,@RequestBody RunLogEntity runLogEntity, @PathVariable Long id) throws Exception{
	    	
	    	return runLogService.joinTeamWind(runLogEntity);
	    }
        /**
        * @Title: meeting
        * @Description:修改集控运行日志信息
        * @author sunliang
        * @date 2017年7月31日下午3:24:40
        * @param request
        * @param runLogEntity
        * @param id
        * @return
        * @throws
        */
        @RequestMapping("/meeting/{id}")
        public @ResponseBody ResultObj meeting(HttpServletRequest request,@RequestBody RunLogEntity runLogEntity, @PathVariable Long id){
            ResultObj resultObj = new ResultObj();
            try {
               runLogService.updateMeeting(runLogEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            SysUserEntity userEntity = RequestContext.get().getUser();
			userOperateLogService.addOperateLog(userEntity,OperateUserModuleEnum.DAILYREPORT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
            return resultObj;
        }
        
        /**
        * @Title: getDetail
        * @Description: 进入运行日志详细页
        * @author sunliang
        * @date 上午11:30:21
        * @param request
        * @param id
        * @return
        * @throws
        */
        @RequestMapping("/getDetail/{id}")
        public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id,@RequestBody Map<String, Object> params){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            //上一条 下一条
            ResultListObj resultListObj=this.search(request, params);
            if(resultListObj.getData()!=null&&!resultListObj.getData().isEmpty()){
            	RunLogEntity t=(RunLogEntity) resultListObj.getData().get(0);
            	id=t.getId();
            	params.put("total", resultListObj.getRecordsTotal());
            }
            resultMap.put("dedit", JsonUtil.toJson(params));
            resultMap.put("RLId", id);
            resultMap.put("rlId", id-1);
            return new ModelAndView("run/runLog/infoDetail",resultMap);
        }
        /**
        * @Title: getCharge
        * @Description: 关联查询接班负责人与人员信息
        * @author sunliang
        * @date 2017年7月19日下午6:34:27
        * @param request
        * @param dutyId
        * @return
        * @throws
        */
        @RequestMapping("/getCharge/{dutyId}")
        public @ResponseBody Map<String, Object> getCharge(HttpServletRequest request, @PathVariable String dutyId){            
            Map<String, Object> resultMap = new HashMap<String, Object>();           
            DutyOrderEntity dutyOrderEntity = dutyOrderService.findById(new Long(dutyId));
            List<Condition> conditions=new ArrayList<Condition>();
            conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,dutyOrderEntity.getTeamLeader()));
            List<SysUserEntity> leaderEntityList = sysUserService.findByCondition("findByCondition", conditions, null);
            ComboboxVO leaderCombobox = new ComboboxVO();
            for(SysUserEntity sysUserEntity : leaderEntityList){
                leaderCombobox.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
            }
            resultMap.put("leaderCombobox", leaderCombobox.getOptions());
            conditions.clear();
            conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.IN,dutyOrderEntity.getTeamMember().split(",")));
            List<SysUserEntity> sysUserEntityList = sysUserService.findByCondition("findByCondition", conditions, null);
            ComboboxVO sysUserEntityCombobox = new ComboboxVO();
            for(SysUserEntity sysUserEntity : sysUserEntityList){
                sysUserEntityCombobox.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
            }
            resultMap.put("sysUserEntityCombobox", sysUserEntityCombobox.getOptions());        
            return resultMap;
        }
        /**
        * @Title: expData
        * @Description: excel导出
        * @author sunliang
        * @date 2017年7月20日下午4:02:41
        * @param req
        * @param res
        * @throws UnsupportedEncodingException
        * @throws
        */
        @RequestMapping("/exportExcel")
        public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
        	Page<RunLogEntity> page = new Page<RunLogEntity>();
    		page.addOrder(Sort.desc("date"));
    		page.setPageSize(Integer.MAX_VALUE);
    		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
        	SysUserEntity userEntity = RequestContext.get().getUser();
//        	List<Condition> conditions = OrmUtil.changeMapToCondition(params);
        	
    		conditions.add(new Condition("a.C_UNIT",FieldTypeEnum.INT,MatchTypeEnum.EQ,userEntity.getUnitId()));
//            List<RunLogEntity> dataList=runLogService.findByCondition(conditions, page);
            List<RunLogEntity> dataList=runLogService.findByCondition("findByCondition", conditions, page);
            Map<String,Object> resultMap=new HashMap<String, Object>();
            DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
            DateFormatUtil dfuri= DateFormatUtil.getInstance("yyyy-MM-dd ");
            List<String []> dataStyle=new ArrayList<String[]>();
            int row = 0;
            int number = 1;
            for (RunLogEntity runLogEntity : dataList) {
    			if(runLogEntity.getDate()!=null){
    				runLogEntity.setDateString(df.format(runLogEntity.getDate()));
    			}
    			if(runLogEntity.getGiveDate()!=null){
    				runLogEntity.setGiveDateString(df.format(runLogEntity.getGiveDate()));
    			}
    			if(runLogEntity.getCreateDate()!=null){
    				runLogEntity.setCreateDateString(dfuri.format(runLogEntity.getCreateDate()));
    			}
    			if(runLogEntity.getCheck()!=null && runLogEntity.getCheck().equals(0)){
    				dataStyle.add(new String []{String.valueOf(row+2),"8",
    						runLogEntity.getCheckName().toString(),String.valueOf(Cell.CELL_TYPE_STRING)});
    			}
    			runLogEntity.setNumber(number++);
    			row++;
    		}
            resultMap.put("dataStyle", dataStyle);
            resultMap.put("dataList", dataList);
            resultMap.put("algin", "CENTER");
            //增加运行记事导出sheet页	
            Page<RunRecordEntity> runRecordPage=new Page<RunRecordEntity>();
            runRecordPage.setPageSize(Integer.MAX_VALUE);
            runRecordPage.addOrder(Sort.desc("C_RECORD_TIME"));
            List<Condition> runRecordConditions=new ArrayList<Condition>();
            List<RunRecordEntity> runRecordList=runRecordService.findByCondition(runRecordConditions, runRecordPage);
            int runRecordNum = 1;
            for(RunRecordEntity runRecordEntity : runRecordList){
            	runRecordEntity.setNumber(runRecordNum++);
            }
            resultMap.put("runRecordList", runRecordList);
            //增加工作安排导出sheet页	
            Page<SafeMeetingEntity> safeMeetingPage=new Page<SafeMeetingEntity>();
            safeMeetingPage.setPageSize(Integer.MAX_VALUE);
            List<Condition> safeMeetingConditions=new ArrayList<Condition>();
            safeMeetingConditions.add(new Condition("a.C_MEETING_FLAG", FieldTypeEnum.INT, MatchTypeEnum.EQ, 2));
            List<SafeMeetingEntity> SafeMeetingList=safeMeetingService.findByCondition(safeMeetingConditions, safeMeetingPage);
            int SafeMeetingNum = 1;
            for(SafeMeetingEntity safeMeetingEntity : SafeMeetingList){
            	safeMeetingEntity.setNumber(SafeMeetingNum++);
            }
            resultMap.put("SafeMeetingList", SafeMeetingList);
            ExcelUtil.exportNew(req, res, "运行日志报表模板.xlsx","运行日志.xlsx", resultMap);
        }
        /**
         * @Title: expData
         * @Description: excel场站运行日志导出
         * @author sunliang
         * @date 2017年7月20日下午4:02:41
         * @param req
         * @param res
         * @throws UnsupportedEncodingException
         * @throws
         */
        @RequestMapping("/exportExcelWind")
        public void exportExcelWind(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
        	Page<RunLogEntity> page = new Page<RunLogEntity>();
        	page.addOrder(Sort.desc("date"));
        	page.setPageSize(Integer.MAX_VALUE);
        	runLogDao.setFlag(false);
        	List<Condition> conditions = OrmUtil.changeMapToCondition(params);
        	SysUserEntity userEntity = RequestContext.get().getUser();
//        	List<Condition> conditions = OrmUtil.changeMapToCondition(params);
        	
        	conditions.add(new Condition("a.C_UNIT",FieldTypeEnum.INT,MatchTypeEnum.EQ,userEntity.getUnitId()));
//            List<RunLogEntity> dataList=runLogService.findByCondition(conditions, page);
        	List<RunLogEntity> dataList=runLogService.findByCondition("findByCondition", conditions, page);
        	Map<String,Object> resultMap=new HashMap<String, Object>();
        	DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
        	DateFormatUtil dfuri= DateFormatUtil.getInstance("yyyy-MM-dd ");
        	List<String []> dataStyle=new ArrayList<String[]>();
        	int row = 0;
        	int number = 1;
        	for (RunLogEntity runLogEntity : dataList) {
        		if(runLogEntity.getDate()!=null){
        			runLogEntity.setDateString(df.format(runLogEntity.getDate()));
        		}
        		if(runLogEntity.getGiveDate()!=null){
        			runLogEntity.setGiveDateString(df.format(runLogEntity.getGiveDate()));
        		}
        		if(runLogEntity.getCreateDate()!=null){
        			runLogEntity.setCreateDateString(dfuri.format(runLogEntity.getCreateDate()));
        		}
        		if(runLogEntity.getCheck()!=null && runLogEntity.getCheck().equals(0)){
        			dataStyle.add(new String []{String.valueOf(row+2),"8",
        					runLogEntity.getCheckName().toString(),String.valueOf(Cell.CELL_TYPE_STRING)});
        		}
        		runLogEntity.setNumber(number++);
        		row++;
        	}
        	resultMap.put("dataStyle", dataStyle);
        	resultMap.put("dataList", dataList);
        	resultMap.put("algin", "CENTER");
        	ExcelUtil.exportNew(req, res, "场站运行日志报表模板.xlsx","场站运行日志.xlsx", resultMap);
        }
        /**
        * @Title: workTicket
        * @Description: 关联两票初始化
        * @author sunliang
        * @date 2017年7月27日下午5:12:36
        * @param request
        * @param params
        * @return
        * @throws
        */
        @RequestMapping("/workTicket/{rlId}")
        public ModelAndView workTicket(HttpServletRequest request, @PathVariable String rlId,
                Map<String, Object> params) {
            Map<String, Object> model = new HashMap<String, Object>();
            RunLogEntity runLogEntity=runLogService.findById(new Long(rlId));
            DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
            String dateStr=df.format(runLogEntity.getDate());
            model.put("dateStr", dateStr);
            model.put("rlId", rlId);
            model.put("runLogEntity", runLogEntity);
            return this.createModelAndView("run/runLog/runWorkTicketList",model);
        }
        /**
        * @Title: searchDetail
        * @Description: 关联两票查询
        * @author sunliang
        * @date 2017年7月28日上午9:48:32
        * @param request
        * @param RLId
        * @return
        * @throws
        */
        @RequestMapping(value = "/workTicketSearch/{RLId}", method = RequestMethod.POST)
        public @ResponseBody ResultListObj workTicketSearch(HttpServletRequest request, @PathVariable Long RLId, @RequestBody Map<String, Object> params){
            RunLogEntity runLogEntity =(RunLogEntity)runLogService.findById(RLId);
            List<Condition> conditions = new ArrayList<Condition>();
            Page<WorkTicketEntity> page = PageUtil.getPage(params);
            if (page == null) {
                page = new Page<WorkTicketEntity>();
                page.setPageSize(Integer.MAX_VALUE);
            }            
            page.setOrders(OrmUtil.changeMapToOrders(params));
            DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
            String dateStr = df.format(new Date());
          //  String tempSQL=" T.C_WORK_STATUS!='8'";
        //    Condition condition=new Condition(tempSQL);
        //    StringBuffer asd= new StringBuffer();
        //    asd.append(aaa).toString();
          //  conditions.add(condition);
            String date = df.format(runLogEntity.getDate());
            conditions.add(new Condition("T.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.NE,new Long(8)));
            conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ,new Long(1)));
            conditions.add(new Condition("T.C_CREATE_DATE", FieldTypeEnum.DATE, MatchTypeEnum.GE,date+" OR T.C_END_TIME >='"+date+"'"+" OR T.C_PLANDATE_END >='"+date+"'"));
            List<WorkTicketEntity> workTicketEntityList=workTicketService.findByCondition("findByCondition",conditions, page);
            ResultListObj resultObj = new ResultListObj();
            resultObj.setDraw((Integer)params.get("draw"));
            if (workTicketEntityList != null) {
                resultObj.setData(workTicketEntityList);
                if (page != null) {
                    resultObj.setRecordsTotal(page.getTotal());
                }
            }
                
            
            return resultObj;
        }

         /**
        * @Title: opeTicketSearch
        * @Description: 操作票列表查询
        * @author sunliang
        * @date 2017年8月30日上午9:11:47
        * @param request
        * @param RLId
        * @param params
        * @return
        * @throws
        */
        @RequestMapping(value = "/opeTicketSearch/{RLId}", method = RequestMethod.POST)
         public @ResponseBody ResultListObj opeTicketSearch(HttpServletRequest request, @PathVariable Long RLId, @RequestBody Map<String, Object> params){
             RunLogEntity runLogEntity =(RunLogEntity)runLogService.findById(RLId);
             List<Condition> conditions = new ArrayList<Condition>();
             Page<WorkTicketEntity> page = PageUtil.getPage(params);
             if (page == null) {
                 page = new Page<WorkTicketEntity>();
                 page.setPageSize(Integer.MAX_VALUE);
             }            
             page.setOrders(OrmUtil.changeMapToOrders(params));
             DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
             String dateStr = df.format(new Date());
           //  String tempSQL=" T.C_WORK_STATUS!='8'";
         //    Condition condition=new Condition(tempSQL);
         //    StringBuffer asd= new StringBuffer();
         //    asd.append(aaa).toString();
           //  conditions.add(condition);
             String logDate = df.format(runLogEntity.getDate());
//             conditions.add(new Condition("T.C_END_TIME = "+logDate+" OR T.C_START_DATE = "+logDate+" OR T.C_END_TIME IS NULL"));
//             conditions.add(new Condition("", FieldTypeEnum.STRING, MatchTypeEnum.GE, value));
//             conditions.add(new Condition("", FieldTypeEnum.STRING, MatchTypeEnum.LE, value));
             conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.NE,new Long(8)));
             conditions.add(new Condition("T.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,new Long(1)));
             conditions.add(new Condition("T.C_CREATE_TIME", FieldTypeEnum.STRING, MatchTypeEnum.GE,logDate+" OR T.C_END_TIME >='"+logDate+"'"));
             List<WorkTicketEntity> workTicketEntityList=operationTicketService.findByCondition("findByCondition",conditions, page);
             ResultListObj resultObj = new ResultListObj();
             resultObj.setDraw((Integer)params.get("draw"));
             if (workTicketEntityList != null) {
                 resultObj.setData(workTicketEntityList);
                 if (page != null) {
                     resultObj.setRecordsTotal(page.getTotal());
                 }
             }
                 
             
             return resultObj;
         }
        /* (非 Javadoc)
        * <p>Title: delete</p>
        * <p>Description: 删除</p>
        * @param id
        * @return
        * @see com.aptech.common.web.base.BaseController#delete(java.lang.Long)
        */
        @RequestMapping(value = "/delete/{id}")
        public @ResponseBody ResultObj delete(@PathVariable Long id){          
                try
                {
                    return runLogService.delete(id);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;                       
        }
        @RequestMapping(value = "/validate/{id}")
        public @ResponseBody ResultObj validate(HttpServletRequest request ,@PathVariable Long id) throws Exception {
            return runLogService.invalidValidate(id);
        }
        @RequestMapping(value = "/invalidValidateWind/{id}")
        public @ResponseBody ResultObj invalidValidateWind(HttpServletRequest request ,@PathVariable Long id) throws Exception {
        	return runLogService.invalidValidateWind(id);
        }
        
        /**
    	 * @Description:   检修日志列表
    	 * @author         wangcc 
    	 * @param <T>
    	 * @Date           2018年3月6日 下午23:24:58 
    	 * @throws         Exception
    	 */
    	@RequestMapping("/searchData")
    	public @ResponseBody  ResultListObj searchDate(HttpServletRequest request,@RequestBody Map<String, Object> params){
    		SysUserEntity userEntity = RequestContext.get().getUser();
    		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
			conditions.add(new Condition("a.C_UNIT",FieldTypeEnum.INT,MatchTypeEnum.EQ,userEntity.getUnitId()));
			ResultListObj resultObj = new ResultListObj();
			Page<OverhaulLogEntity> page = PageUtil.getPage(params);
			page.setOrders(OrmUtil.changeMapToOrders(params));
			page.addOrder(Sort.desc("C_DATE"));
			List<OverhaulLogEntity> overhaulLogEntities = new ArrayList<OverhaulLogEntity>();
			if (userEntity.getLoginName().equals("super")|| userEntity.getLoginName().equals("admin") ) {
				conditions.clear();
				conditions = OrmUtil.changeMapToCondition(params);
				overhaulLogEntities = (List<OverhaulLogEntity>)runLogService.findByCondition(conditions, page);
			}else {
				overhaulLogEntities =  (List<OverhaulLogEntity>) runLogService.findByCondition(conditions, page);
			}
			resultObj.setDraw((Integer)params.get("draw"));
			if (overhaulLogEntities != null) {
				resultObj.setData(overhaulLogEntities);
				resultObj.setRecordsTotal(page.getTotal());
			}
			return resultObj;
    	}
    	
    	/**
    	 *	运行日志(运行检查)页面跳转
    	 * @Title: 
    	 * @Description:
    	 * @param 
    	 * @return
    	 */
    	@RequestMapping("/indexForRunCheck")
    	public ModelAndView indexForRunCheck(HttpServletRequest request, Map<String, Object> params) {
    		Map<String, Object> model = new HashMap<String, Object>();
    		List<RunLogEntity> treeNodeList = null; 
    		model.put("runLogTreeList", JsonUtil.toJson(treeNodeList));
    		//值长查询
            ComboboxVO comboChargeVO = new ComboboxVO();
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
    	    List<SysUserEntity> SysUserEntityList =sysUserService.findByCondition(conditions, null);
    	    for(SysUserEntity sysUserEntity : SysUserEntityList){
    	         comboChargeVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
    	    }
    	    model.put("runLogCombobox", JsonUtil.toJson(comboChargeVO.getOptions()));
    		ComboboxVO comboRunLogVO1 = new ComboboxVO();
    	    List<TeamMemAppEntity> allTeamMemAppEntity = teamMemAppService.findAll();
    	    for(TeamMemAppEntity teamMemAppEntity : allTeamMemAppEntity){
    	         comboRunLogVO1.addOption(teamMemAppEntity.getId().toString(), teamMemAppEntity.getName());
    	    }
    	    model.put("teamMemCombobox", JsonUtil.toJson(comboRunLogVO1.getOptions()));
    	    ComboboxVO comboRunLogVO4 = new ComboboxVO();
    	    List<DutyOrderEntity> allDutyOrderEntity = dutyOrderService.findAll();       
    	    for(DutyOrderEntity dutyOrderEntity : allDutyOrderEntity){
                comboRunLogVO4.addOption(dutyOrderEntity.getId().toString(), dutyOrderEntity.getName());
            }
    	    model.put("orgAppCombobox", JsonUtil.toJson(comboRunLogVO4.getOptions()));
    	    //编码类型
    	    ComboboxVO grStateCombobox = new ComboboxVO();
    	    Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("GR_STATE");
         
    	    for(String key :  codeDateTypeMap.keySet()){
    	    	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
    	    	grStateCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
    	    }
    	    model.put("grStateCombobox", JsonUtil.toJson(grStateCombobox.getOptions()));
    	    SysUserEntity userEntity = RequestContext.get().getUser();
    	    model.put("userEntity", userEntity);
    	    ComboboxVO runCheckCombobox = new ComboboxVO();
    	    Map<String, SysDictionaryVO> runCheckTypeMap  =  DictionaryUtil.getDictionaries("RUN_CHECK");
    	    for(String key :  runCheckTypeMap.keySet()){
    	         SysDictionaryVO sysDictionaryVO = runCheckTypeMap.get(key);
    	         runCheckCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
    	    }
    	    model.put("runCheckCombobox", JsonUtil.toJson(runCheckCombobox.getOptions()));
    		return this.createModelAndView("run/runLog/runLogListForRunCheck", model);
    	}
    	
    	/*************************************场站检修日志******************************************/
    	/**
    	 *	list页面跳转
    	 * @Title: 
    	 * @Description:
    	 * @param 
    	 * @return
    	 */
    	@RequestMapping("/indexForWind")
    	public ModelAndView listForWind(HttpServletRequest request, Map<String, Object> params) {
    		Map<String, Object> model = new HashMap<String, Object>();
    		List<RunLogEntity> treeNodeList = null; 
    		model.put("runLogTreeList", JsonUtil.toJson(treeNodeList));
    		//值长查询
            ComboboxVO comboChargeVO = new ComboboxVO();
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
    	    SysUserEntity userEntity= RequestContext.get().getUser();
    	    conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//    	    conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
    	    List<SysUserEntity> SysUserEntityList =sysUserService.findByCondition(conditions, null);
    	    for(SysUserEntity sysUserEntity : SysUserEntityList){
    	         comboChargeVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
    	    }
    	    model.put("runLogCombobox", JsonUtil.toJson(comboChargeVO.getOptions()));
    		ComboboxVO comboRunLogVO1 = new ComboboxVO();
    	    List<TeamMemAppEntity> allTeamMemAppEntity = teamMemAppService.findAll();
    	    for(TeamMemAppEntity teamMemAppEntity : allTeamMemAppEntity){
    	         comboRunLogVO1.addOption(teamMemAppEntity.getId().toString(), teamMemAppEntity.getName());
    	    }
    	    model.put("teamMemCombobox", JsonUtil.toJson(comboRunLogVO1.getOptions()));
    	    ComboboxVO comboRunLogVO4 = new ComboboxVO();
    	    List<DutyOrderEntity> allDutyOrderEntity = dutyOrderService.findAll();       
    	    for(DutyOrderEntity dutyOrderEntity : allDutyOrderEntity){
                comboRunLogVO4.addOption(dutyOrderEntity.getId().toString(), dutyOrderEntity.getName());
            }
    	    model.put("orgAppCombobox", JsonUtil.toJson(comboRunLogVO4.getOptions()));
    	    //编码类型
    	    ComboboxVO grStateCombobox = new ComboboxVO();
    	    Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("GR_STATE");
         
    	    for(String key :  codeDateTypeMap.keySet()){
    	    	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
    	    	grStateCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
    	    }
    	    model.put("grStateCombobox", JsonUtil.toJson(grStateCombobox.getOptions()));
    	    model.put("userEntity", userEntity);
    	    ComboboxVO runCheckCombobox = new ComboboxVO();
    	    Map<String, SysDictionaryVO> runCheckTypeMap  =  DictionaryUtil.getDictionaries("RUN_CHECK");
    	    for(String key :  runCheckTypeMap.keySet()){
    	         SysDictionaryVO sysDictionaryVO = runCheckTypeMap.get(key);
    	         runCheckCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
    	    }
    	    model.put("runCheckCombobox", JsonUtil.toJson(runCheckCombobox.getOptions()));
    	    DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd");
    	    runLogDao.setFlag(false);
    	    conditions.clear();
    	    model.put("newFlag", true);
    	    String today = format.format(new Date());
    	    List<RunLogEntity> runLogList = runLogService.findByCondition(conditions, null);
    	    for(RunLogEntity runLogEntity : runLogList){
    	    	String date = format.format(runLogEntity.getDate());
    	    	if(date.equals(today)){
    	    		model.put("newFlag", false);
    	    		break;
    	    	}
    	    }
    		return this.createModelAndView("run/runLog/runLogListForWind", model);
    	}
    	
    	/**
    	 *	添加场站运行日志
    	 */
    	@RequestMapping("/getAddForWind")
    	public ModelAndView getAddForWind(HttpServletRequest request){
    		Map<String, Object> model = new HashMap<String, Object>();
    		DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
	        String dateStr = format.format(new Date());
    		model.put("giveDate", dateStr);
    		List<RunLogEntity> treeNodeList = null; 
    		model.put("runLogTreeList", JsonUtil.toJson(treeNodeList));
    		ComboboxVO comboChargeVO = new ComboboxVO();
            List<Condition> conditions=new ArrayList<Condition>();
            SysUserEntity userEntity= RequestContext.get().getUser();
    		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
    		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
    		List<SysUserEntity> SysUserEntityList = sysUserService.findByCondition(
    				conditions, null);
            for(SysUserEntity sysUserEntity : SysUserEntityList){
                 comboChargeVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
            }
    		model.put("chargeCombobox", JsonUtil.toJson(comboChargeVO.getOptions()));
    		ComboboxVO comboRunLogVO = new ComboboxVO();
    		conditions = new ArrayList<Condition>();
    		conditions.clear();
    		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
    		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
    		List<SysUserEntity> allUsers = sysUserService.findByCondition(
    				conditions, null);
            for(SysUserEntity sysUserEntity : allUsers){
                comboRunLogVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
            }
    		model.put("runLogCombobox", JsonUtil.toJson(comboRunLogVO.getOptions()));
    		ComboboxVO runCheckCombobox = new ComboboxVO();
            Map<String, SysDictionaryVO> runCheckTypeMap  =  DictionaryUtil.getDictionaries("RUN_CHECK");
             
            for(String key :  runCheckTypeMap.keySet()){
                SysDictionaryVO sysDictionaryVO = runCheckTypeMap.get(key);
                runCheckCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
            }
            model.put("runCheckCombobox", JsonUtil.toJson(runCheckCombobox.getOptions()));
            ComboboxVO comboRunLogVO3 = new ComboboxVO();
            List<TeamMemAppEntity> allgiveTeamMemAppEntity = teamMemAppService.findAll();
            for(TeamMemAppEntity teamMemAppEntity : allgiveTeamMemAppEntity){
                comboRunLogVO3.addOption(teamMemAppEntity.getId().toString(), teamMemAppEntity.getName());
            }
            model.put("giveteamMemCombobox", JsonUtil.toJson(comboRunLogVO3.getOptions()));
            ComboboxVO comboRunLogVO1 = new ComboboxVO();
    	    List<TeamMemAppEntity> allTeamMemAppEntity = teamMemAppService.findAll();
    	    for(TeamMemAppEntity teamMemAppEntity : allTeamMemAppEntity){
    	         comboRunLogVO1.addOption(teamMemAppEntity.getId().toString(), teamMemAppEntity.getName());
    	    }
    	    model.put("teamMemCombobox", JsonUtil.toJson(comboRunLogVO1.getOptions()));
            ComboboxVO comboRunLogVO4 = new ComboboxVO();
            conditions.clear();
    	    conditions.add(new Condition("a.C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.EQ,userEntity.getUnitId()));
    	    List<DutyOrderEntity> allDutyOrderEntity = dutyOrderService.findByCondition("findByCondition", conditions, null);	     
    	    for(DutyOrderEntity dutyOrderEntity : allDutyOrderEntity){
    	          comboRunLogVO4.addOption(dutyOrderEntity.getId().toString(), dutyOrderEntity.getName());
    	    }
    	    model.put("giveorgAppCombobox", JsonUtil.toJson(comboRunLogVO4.getOptions()));
    	  
    	    
    		return this.createModelAndView("run/runLog/runLogAddForWind", model);
    	}
    	
    	/**
    	 *	添加场站运行日志
    	 * @throws Exception 
    	 */
    	@RequestMapping(value = "/addForWind", method = RequestMethod.POST)
    	public @ResponseBody ResultObj addForWind(@RequestBody RunLogEntity runlogEntity, HttpServletRequest request) throws Exception {
    		return runLogService.addForWind(runlogEntity,request);
    	}
    	
    	  /**
    	 * @Description:   检修日志列表
    	 * @author         wangcc 
    	 * @param <T>
    	 * @Date           2018年4月24日 下午10:00:00 
    	 * @throws         Exception
    	 */
    	@RequestMapping("/searchDataForWind")
    	public @ResponseBody  ResultListObj searchDataForWind(HttpServletRequest request,@RequestBody Map<String, Object> params) throws Exception{
			return runLogService.searchDataForWind(request,params);
    	}
    	
        /**
        * @Title: getDetail
        * @Description: 进入场站运行日志详细页
        * @author wangchuncheng
        * @date 上午11:30:21
        * @param request
        * @param id
        * @return
        * @throws
        */
        @RequestMapping("/getDetailForWind/{id}")
        public ModelAndView getDetailForWind(HttpServletRequest request, @PathVariable Long id,@RequestBody Map<String, Object> params){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("dedit", JsonUtil.toJson(params));
            resultMap.put("RLId", id);
            return new ModelAndView("run/runLog/infoDetailForWind",resultMap);
        }
        
        /**
    	 * 
    	* @Title: 跳转到填报框架页
    	* @Description: 跳转到填报框架页
    	* @author wangchuncheng
    	* @param request
    	* @param id
    	* @return
    	* @throws
    	 */
	   @RequestMapping("/infoForWind/{id}")
	    public ModelAndView infoForWind(HttpServletRequest request, @PathVariable Long id){
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        resultMap.put("RLId", id);
	        return new ModelAndView("run/runLog/infoFrameForWind",resultMap);
	    }
	   
	   /**
        * @Title: meeting
        * @Description:修改场站运行日志信息
        * @author sunliang
        * @date 2017年7月31日下午3:24:40
        * @param request
        * @param runLogEntity
        * @param id
        * @return
        * @throws
        */
        @RequestMapping("/meetingForWind/{id}")
        public @ResponseBody ResultObj meetingForWind(HttpServletRequest request,@RequestBody RunLogEntity runLogEntity, @PathVariable Long id){
            ResultObj resultObj = new ResultObj();
            try {
               runLogService.updateMeetingForWind(runLogEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultObj;
        }
        /**
         * @Title:
         * @Description:集控运行日志导出
         * @author 
         * @date 
         * @param request
         * @param runLogEntity
         * @param id
         * @return
         * @throws
         */
        @RequestMapping("/exportExcelAll")
    	public void exportRunLog(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
         	Page<RunLogEntity> page = new Page<RunLogEntity>();
        		page.addOrder(Sort.desc("date"));
        		page.setPageSize(Integer.MAX_VALUE);
        		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
            	SysUserEntity userEntity = RequestContext.get().getUser();
//            	List<Condition> conditions = OrmUtil.changeMapToCondition(params);
            	
        		conditions.add(new Condition("a.C_UNIT",FieldTypeEnum.INT,MatchTypeEnum.EQ,userEntity.getUnitId()));
//                List<RunLogEntity> dataList=runLogService.findByCondition(conditions, page);
                List<RunLogEntity> dataList=runLogService.findByCondition("findByCondition", conditions, page);
                Map<String,Object> resultMap=new HashMap<String, Object>();
                DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
                DateFormatUtil dfuri= DateFormatUtil.getInstance("yyyy-MM-dd ");
                List<String []> dataStyle=new ArrayList<String[]>();
                int row = 0;
                int number = 1;
                for (RunLogEntity runLogEntity : dataList) {
        			if(runLogEntity.getDate()!=null){
        				runLogEntity.setDateString(df.format(runLogEntity.getDate()));
        			}
        			if(runLogEntity.getGiveDate()!=null){
        				runLogEntity.setGiveDateString(df.format(runLogEntity.getGiveDate()));
        			}
        			if(runLogEntity.getCreateDate()!=null){
        				runLogEntity.setCreateDateString(dfuri.format(runLogEntity.getCreateDate()));
        			}
        			if(runLogEntity.getCheck()!=null && runLogEntity.getCheck().equals(0)){
        				dataStyle.add(new String []{String.valueOf(row+2),"8",
        						runLogEntity.getCheckName().toString(),String.valueOf(Cell.CELL_TYPE_STRING)});
        			}
        			runLogEntity.setNumber(number++);
        			row++;
        		}
                resultMap.put("dataStyle", dataStyle);
                resultMap.put("dataList", dataList);
                resultMap.put("algin", "CENTER");
                ExcelUtil.exportNew(req, res, "运行日志报表模板.xlsx","运行日志.xlsx", resultMap);
                //获取数据
                int num = 1;
                Page<RunRecordEntity> pageRun=new Page<RunRecordEntity>();
                pageRun.setPageSize(Integer.MAX_VALUE);
                pageRun.addOrder(Sort.desc("C_RECORD_TIME"));
        		List<Condition> runConditions = new ArrayList<Condition>();
        		for(RunLogEntity runLogEntity : dataList){
            	runConditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,runLogEntity.getId()));
            	List<RunRecordEntity> dataListRun=runRecordService.findByCondition(runConditions, pageRun);
            	// excel标题
    			String[] title = { "序号","记录类型","记录时间", "单位名称", "负责人", "记录内容" };
    			// excel文件名
    			String fileName = "运行记事.xls";
    			// sheet名
    			String sheetName = "运行记事";
    			String[][] content = new String[dataListRun.size()][title.length];
    			for (int i = 0; i < dataListRun.size(); i++) {
    				content[i] = new String[title.length];
    				RunRecordEntity obj = dataListRun.get(i);
    				content[i][0] = String.valueOf(num++);
    				content[i][1] = obj.getRecordTypeName();
    				content[i][2] = obj.getRecordTimeString();
    				content[i][3] = obj.getUnitName();
    				content[i][4] = obj.getfZR();
    				content[i][5] = obj.getRecordContent();
    			}
    			// 创建HSSFWorkbook
    			HSSFWorkbook wb = ExcelUtilAll.getHSSFWorkbook(sheetName, title, content,null);
    			// 响应到客户端
    			try {
    				this.setResponseHeader(res, fileName);
    				OutputStream os = res.getOutputStream();
    				wb.write(os);
    				os.flush();
    				os.close();
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
        		}
        }
        //发送响应流方法
        public void setResponseHeader(HttpServletResponse response, String fileName) {
            try {
                try {
                    fileName = new String(fileName.getBytes(),"ISO8859-1");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                response.setContentType("application/octet-stream;charset=ISO8859-1");
                response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        /**
         * @Title: getDetail
         * @Description: 运行检查进入运行日志详细页
         * @author sunliang
         * @date 上午11:30:21
         * @param request
         * @param id
         * @return
         * @throws
         */
         @RequestMapping("/getDetailRunCheck/{id}")
         public ModelAndView getDetailRunCheck(HttpServletRequest request, @PathVariable Long id,@RequestBody Map<String, Object> params){
             Map<String, Object> resultMap = new HashMap<String, Object>();
             //上一条 下一条
             ResultListObj resultListObj=this.search(request, params);
             if(resultListObj.getData()!=null&&!resultListObj.getData().isEmpty()){
             	RunLogEntity t=(RunLogEntity) resultListObj.getData().get(0);
             	id=t.getId();
             	params.put("total", resultListObj.getRecordsTotal());
             }
             resultMap.put("dedit", JsonUtil.toJson(params));
             resultMap.put("RLId", id);
             resultMap.put("rlId", id-1);
             return new ModelAndView("run/runLog/infoDetailRunCheck",resultMap);
         }
}