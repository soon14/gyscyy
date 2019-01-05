package com.aptech.business.run.safeMeeting.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.component.dictionary.Istypicaleum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.equip.equipModel.domain.EquipModelEntity;
import com.aptech.business.equip.equipModel.domain.EquipModelEnum;
import com.aptech.business.equip.modelParameter.domain.ModelParameterEntity;
import com.aptech.business.equip.modelParameter.domain.ModelParameterEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.business.run.runLog.service.RunLogService;
import com.aptech.business.run.safeMeeting.dao.SafeMeetingDao;
import com.aptech.business.run.safeMeeting.domain.SafeMeetingEntity;
import com.aptech.business.run.safeMeeting.domain.SafeMeetingEnum;
import com.aptech.business.run.safeMeeting.service.SafeMeetingService;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
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
 * 安全例会配置控制器
 *
 * @author 
 * @created 2017-06-07 16:00:28
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/safeMeeting")
public class SafeMeetingController extends BaseController<SafeMeetingEntity> {
	
	@Autowired
	private SafeMeetingService safeMeetingService;
	@Autowired
    private ListFormService listFormService;
	@Autowired
    private SysUserService sysUserService;
	@Autowired
    private RunLogService runLogService;
	@Autowired
	private SafeMeetingDao safeMeetingDao;
	@Override
	public IBaseEntityOperation<SafeMeetingEntity> getService() {
		return safeMeetingService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{rlId}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long rlId) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
        RunLogEntity runLogEntity = (RunLogEntity)runLogService.findById(rlId);
        if(runLogEntity.getBtmContent()!=null){
        	 model.put("dataMap", runLogEntity);
             model.put("dataMapJson", JsonUtil.toJson(runLogEntity));
        }else{//如果班前会内容为空，则查询上一个班的数据
        	long id = rlId-1;
            RunLogEntity runLogEntityLast = (RunLogEntity)runLogService.findById(id);
            model.put("dataMap", runLogEntityLast);
            model.put("dataMapJson", JsonUtil.toJson(runLogEntityLast));
        }
		List<SafeMeetingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> userCondition = new ArrayList<Condition>();
	     //获取登录人信息
	 	SysUserEntity userEntity= RequestContext.get().getUser();
		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
//		List<SysUserEntity> allUsers = sysUserService.findAll();
        for(SysUserEntity sysUserEntity : allUsers){
            comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
        model.put("rlId", rlId);
        return this.createModelAndView("run/runLog/safeMeetingList", model);
	}
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
//		RunLogEntity runLogEntity = (RunLogEntity)runLogService.findById(rlId);
//		model.put("dataMap", runLogEntity);
//		model.put("dataMapJson", JsonUtil.toJson(runLogEntity));
		List<SafeMeetingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers = sysUserService.findByCondition(
				conditions, null);
		for(SysUserEntity sysUserEntity : allUsers){
			comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
		}
		model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
		//统计状态
        Map<String, SysDictionaryVO> runWayMap  =  DictionaryUtil.getDictionaries("DATEUTIL");
   	 	ComboboxVO joinLandCombobox = new ComboboxVO();
        for(String key :  runWayMap.keySet()){
            SysDictionaryVO sysDictionaryVO = runWayMap.get(key);
            joinLandCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("DateUtil", JsonUtil.toJson(joinLandCombobox.getOptions()));
//		model.put("rlId", rlId);
		return this.createModelAndView("run/runLog/safeMeetingListExport", model);
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
	@RequestMapping("/getAdd/{rlId}")
	public ModelAndView getAddPage(HttpServletRequest request, @PathVariable Long rlId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SafeMeetingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers = sysUserService.findByCondition(
				conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
            comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
		model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
		//编码类型
        ComboboxVO meetingFlagCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("MEETING_FLAG");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            meetingFlagCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("meetingFlagCombobox", JsonUtil.toJson(meetingFlagCombobox.getOptions()));  
      //编码类型
        ComboboxVO checkStateCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap2  =  DictionaryUtil.getDictionaries("CHECK_STATE");
        
        for(String key :  codeDateTypeMap2.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap2.get(key);
            checkStateCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("checkStateCombobox", JsonUtil.toJson(checkStateCombobox.getOptions()));  
        model.put("rlId", rlId);	
        String flag=request.getParameter("flag");
        model.put("flag", flag);
		return this.createModelAndView("run/runLog/safeMeetingAdd", model);
	}
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getEditRun/{id}")
	public ModelAndView getEditRun(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SafeMeetingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers = sysUserService.findByCondition(
				conditions, null);
		for(SysUserEntity sysUserEntity : allUsers){
			comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
		}
		model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
		//编码类型
		ComboboxVO meetingFlagCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("MEETING_FLAG");
		
		for(String key :  codeDateTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
			meetingFlagCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("meetingFlagCombobox", JsonUtil.toJson(meetingFlagCombobox.getOptions()));  
		//编码类型
		ComboboxVO checkStateCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> codeDateTypeMap2  =  DictionaryUtil.getDictionaries("CHECK_STATE");
		
		for(String key :  codeDateTypeMap2.keySet()){
			SysDictionaryVO sysDictionaryVO = codeDateTypeMap2.get(key);
			checkStateCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("checkStateCombobox", JsonUtil.toJson(checkStateCombobox.getOptions()));
		model.put("rlId", id);	
		String flag=request.getParameter("flag");
		model.put("flag", flag);
		String type = request.getParameter("type");
		if(type.equals("1")){
			safeMeetingDao.setFlag(false);
			SafeMeetingEntity safeMeetingWindEntity = safeMeetingService.findById(id);
			model.put("dataMap", safeMeetingWindEntity);
			model.put("dataMapJson", JsonUtil.toJson(safeMeetingWindEntity));
			model.put("flag", false);
		}else{
			SafeMeetingEntity safeMeetingEntity = safeMeetingService.findById(id);
			model.put("dataMap", safeMeetingEntity);
			model.put("dataMapJson", JsonUtil.toJson(safeMeetingEntity));
			model.put("flag", true);
		}
		return this.createModelAndView("run/runLog/safeMeetingEdit", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
//	@RequestMapping(value ="/getEditPage/{id}{flag}")
//	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id,@PathVariable boolean flag){
	@RequestMapping(value ="/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeMeetingEntity safeMeetingEntity = null;
		String flag = request.getParameter("flag");
		String type = request.getParameter("type");
//		if(StringUtils.equals(flag, SafeMeetingEnum.SAFE.getCode())){
//			safeMeetingEntity = (SafeMeetingEntity)safeMeetingService.findByIdForWind(id);
//		}else{
			safeMeetingEntity = (SafeMeetingEntity)safeMeetingService.findById(id);
//		}
		model.put("dataMap", safeMeetingEntity);
		model.put("dataMapJson", JsonUtil.toJson(safeMeetingEntity));
		List<SafeMeetingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
            comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
		model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));		
		//编码类型
        ComboboxVO meetingFlagCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("MEETING_FLAG");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            meetingFlagCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("meetingFlagCombobox", JsonUtil.toJson(meetingFlagCombobox.getOptions()));  
        //编码类型
        ComboboxVO checkStateCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap2  =  DictionaryUtil.getDictionaries("CHECK_STATE");
        
        for(String key :  codeDateTypeMap2.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap2.get(key);
            checkStateCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("checkStateCombobox", JsonUtil.toJson(checkStateCombobox.getOptions()));
        String redirctUrl = "";
        if(flag.equals(SafeMeetingEnum.SAFEONE.getCode())||flag.equals(SafeMeetingEnum.SAFETWO.getCode())||flag.equals(SafeMeetingEnum.SAFETHREE.getCode())){
        	redirctUrl = "run/runLog/safeMeetingEditSafe";
        }else{
        	safeMeetingDao.setFlag(false);
        	safeMeetingEntity = (SafeMeetingEntity)safeMeetingService.findByIdForWind(id);
        	model.put("dataMap", safeMeetingEntity);
        	redirctUrl = "run/runLog/safeMeetingEditSafeForWind";
        	model.put("id", id);
        }
		return this.createModelAndView(redirctUrl, model);
	}
	/**
	* @Title: detail
	* @Description: 会议详细页
	* @author sunliang
	* @date 上午11:34:33
	* @param request
	* @param params
	* @param rlId
	* @return
	* @throws
	*/
	@RequestMapping("/detail/{rlId}")
    public ModelAndView detail(HttpServletRequest request, Map<String, Object> params, @PathVariable Long rlId) {
        Map<String, Object> model = new HashMap<String, Object>();
        // 返回前台数据项
        RunLogEntity runLogEntity = (RunLogEntity)runLogService.findById(rlId);
        if(runLogEntity.getBtmContent()!=null){
        	model.put("dataMap", runLogEntity);
            model.put("dataMapJson", JsonUtil.toJson(runLogEntity));
        }else{
        	long id = rlId-1;
            RunLogEntity runLogEntityLast = (RunLogEntity)runLogService.findById(id);
            model.put("dataMap", runLogEntityLast);
            model.put("dataMapJson", JsonUtil.toJson(runLogEntityLast));
        }
        List<SafeMeetingEntity> treeNodeList = null; 
        //TODO下拉树具体内容根据具体业务定制
        model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
        ComboboxVO comboSafeMeetingVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
        List<SysUserEntity> allUsers = sysUserService.findAll();
        for(SysUserEntity sysUserEntity : allUsers){
            comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
        model.put("rlId", rlId);
        return this.createModelAndView("run/runLog/safeMeetingDetail", model);
    }
	/**
	 * @Title: detail
	 * @Description: 会议详细页
	 * @author sunliang
	 * @date 上午11:34:33
	 * @param request
	 * @param params
	 * @param rlId
	 * @return
	 * @throws
	 */
	@RequestMapping("/show/{id}")
	public ModelAndView show(HttpServletRequest request, Map<String, Object> params, @PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeMeetingEntity safeMeetingEntity = safeMeetingService.findById(id);
		model.put("safeMeetingEntity", safeMeetingEntity);
		model.put("dataMapJson", JsonUtil.toJson(safeMeetingEntity));
		List<SafeMeetingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<SysUserEntity> allUsers = sysUserService.findAll();
		for(SysUserEntity sysUserEntity : allUsers){
			comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
		}
		model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
		return this.createModelAndView("run/runLog/safeMeetingShow", model);
	}
	/**
	* @Title: expData
	* @Description: 工作安排导出
	* @author sunliang
	* @date 2017年9月4日下午4:09:29
	* @param req
	* @param res
	* @throws UnsupportedEncodingException
	* @throws
	*/
	@RequestMapping("/exportExcel/{rlId}")
    public void expData(HttpServletRequest req,HttpServletResponse res, @PathVariable Long rlId) throws UnsupportedEncodingException{
        List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,rlId));
        conditions.add(new Condition("a.C_MEETING_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"3"));
        List<SafeMeetingEntity> dataList=safeMeetingService.findByCondition(conditions, null);
        Map<String,Object> resultMap=new HashMap<String, Object>();
        for(int i = 0;i<dataList.size();i++){
			dataList.get(i).setNumber(i+1);
		}
        resultMap.put("dataList", dataList);
//        ExcelUtil.export(req, res, "工作安排报表模板.xlsx","工作安排.xlsx", resultMap);
        ExcelUtil.export(req, res, "安全交底报表模板.xlsx","安全交底.xlsx", resultMap);
    }
	/**
	 * @Title: expData
	 * @Description: 工作安排导出
	 * @author sunliang
	 * @date 2017年9月4日下午4:09:29
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 * @throws
	 */
	@RequestMapping("/exportExcelRun")
	public void exportExcelRun(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		Page<SafeMeetingEntity> page=new Page<SafeMeetingEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_WORK_TIME"));
		List<SafeMeetingEntity> dataList=safeMeetingService.findByCondition(conditions, page);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		for (SafeMeetingEntity safeMeetingEntity : dataList) {
			if(safeMeetingEntity.getWorkTime()!=null){
				safeMeetingEntity.setWorkTimeString(df.format(safeMeetingEntity.getWorkTime()));
			}
		}
		for(int i = 0;i<dataList.size();i++){
			dataList.get(i).setNumber(i+1);
		}
		resultMap.put("dataList", dataList); 
		ExcelUtil.export(req, res, "工作安排模板.xlsx","工作安排.xlsx", resultMap);
	}
	/**
	 * @Description: 批量删除
	 * @author wangchuncheng
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/bulkDelete")
	public @ResponseBody
	ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		for (Integer id : ids) {
				safeMeetingService.deleteEntity(id.longValue());
		}
		return resultObj;
	}
	/**
	 * @Description:   自己写的修改方法
	 * @author         huoxy
	 * @Date           2018年3月14日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}/{flag}")
	public @ResponseBody ResultObj update(@RequestBody SafeMeetingEntity t, HttpServletRequest request ,@PathVariable Long id,@PathVariable boolean flag) {
		return safeMeetingService.update(t,id,flag);
	}
	/**
	 * @Description:   自己写的查询方法
	 * @author         huoxy
	 * @Date           2018年3月14日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/searchList")
	public @ResponseBody ResultListObj searchList(HttpServletRequest request ,@RequestBody Map<String, Object> params) {
		//按照时间倒序排列
				Page<SafeMeetingEntity> pages = PageUtil.getPage(params);
//				pages.addOrder(Sort.desc("C_WORK_TIME"));
//				List<Condition> conditions = OrmUtil.changeMapToCondition(params);
				List<SafeMeetingEntity> safeMeetingEntity = safeMeetingService.findByCondition(params, pages);
				//获得返回结果
				ResultListObj resultObj = new ResultListObj();
				resultObj.setDraw((Integer)params.get("draw"));
				if (safeMeetingEntity != null) {
					if (pages != null) {
						resultObj.setData(safeMeetingEntity);
						resultObj.setRecordsTotal(pages.getTotal());
					}
				}
				return resultObj;
	}
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAddSafe/{rlId}")
	public ModelAndView getAddSafe(HttpServletRequest request, @PathVariable Long rlId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SafeMeetingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<SysUserEntity> allUsers = sysUserService.findAll();
        for(SysUserEntity sysUserEntity : allUsers){
            comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
		model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
		//编码类型
        ComboboxVO meetingFlagCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("MEETING_FLAG");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            meetingFlagCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("meetingFlagCombobox", JsonUtil.toJson(meetingFlagCombobox.getOptions()));  
      //编码类型
        ComboboxVO checkStateCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap2  =  DictionaryUtil.getDictionaries("CHECK_STATE");
        
        for(String key :  codeDateTypeMap2.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap2.get(key);
            checkStateCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("checkStateCombobox", JsonUtil.toJson(checkStateCombobox.getOptions()));  
        model.put("rlId", rlId);	
        String flag=request.getParameter("flag");
        model.put("flag", flag);
		return this.createModelAndView("run/runLog/safeMeetingAddSafe", model);
	}
	/**
	* @Title: detailForWind
	* @Description: 场站会议详细页
	* @author wangchuncheng
	* @date 上午11:34:33
	* @param request
	* @param params
	* @param rlId
	* @return
	* @throws
	*/
	@RequestMapping("/detailForWind/{rlId}")
    public ModelAndView detailForWind(HttpServletRequest request, Map<String, Object> params, @PathVariable Long rlId) {
        Map<String, Object> model = new HashMap<String, Object>();
        // 返回前台数据项
        RunLogEntity runLogEntity = (RunLogEntity)runLogService.getDataById(rlId);
        if(runLogEntity.getBtmContent()!=null){
        	model.put("dataMap", runLogEntity);
            model.put("dataMapJson", JsonUtil.toJson(runLogEntity));
        }else{
        	Long id = rlId-1;
        	RunLogEntity runLogEntityLast = runLogService.getDataById(id);
        	model.put("dataMap", runLogEntityLast);
            model.put("dataMapJson", JsonUtil.toJson(runLogEntityLast));
        };
        List<SafeMeetingEntity> treeNodeList = null; 
        //TODO下拉树具体内容根据具体业务定制
        model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
        ComboboxVO comboSafeMeetingVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
        List<SysUserEntity> allUsers = sysUserService.findAll();
        for(SysUserEntity sysUserEntity : allUsers){
            comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
        model.put("rlId", rlId);
        return this.createModelAndView("run/runLog/safeMeetingDetailForWind", model);
    }
	
	/**
	 * @Description:   集控运行日志-日例会
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2017年12月25日 上午11:47:58 
	 * @throws         Exception
	 */
	@RequestMapping("/searchData")
	public @ResponseBody  ResultListObj searchDate(HttpServletRequest request,@RequestBody Map<String, Object> params){
		return safeMeetingService.searchDate(request,params);
	}
	
	
	/**
	 * @Description:   场站运行日志-日例会
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2018年12月22日 上午22:43:58 
	 * @throws         Exception
	 */
	@RequestMapping("/searchDataForWind")
	public @ResponseBody  ResultListObj searchDataForWind(HttpServletRequest request,@RequestBody Map<String, Object> params){
		return safeMeetingService.searchDataForWind(request,params);
	}
	
	
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/indexForWind/{rlId}")
	public ModelAndView listForWind(HttpServletRequest request, Map<String, Object> params, @PathVariable Long rlId) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		safeMeetingDao.setFlag(false);
        RunLogEntity runLogEntity = runLogService.getDataById(rlId);
        safeMeetingDao.setFlag(true);
//        if(runLogEntity.getBtmContent()!=null){
    	model.put("dataMap", runLogEntity);
        model.put("dataMapJson", JsonUtil.toJson(runLogEntity));
//        }else{
//        	Long id = rlId-1;
//        	safeMeetingDao.setFlag(false);
//        	RunLogEntity runLogEntityLast = runLogService.getDataById(id);
//        	safeMeetingDao.setFlag(true);
//        	model.put("dataMap", runLogEntityLast);
//            model.put("dataMapJson", JsonUtil.toJson(runLogEntityLast));
//        }
        
		List<SafeMeetingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> userCondition = new ArrayList<Condition>();
		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
//		List<SysUserEntity> allUsers = sysUserService.findAll();
        for(SysUserEntity sysUserEntity : allUsers){
            comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
        model.put("rlId", rlId);
        return this.createModelAndView("run/runLog/safeMeetingListForWind", model);
	}
	
	/**
	 *	跳转到添加页面(场站运行日志-日例会)
	 */
	@RequestMapping("/getAddSafeFowWind/{rlId}")
	public ModelAndView getAddSafeFowWind(HttpServletRequest request, @PathVariable Long rlId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SafeMeetingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
//		List<SysUserEntity> allUsers = sysUserService.findAll();
		List<Condition> userCondition = new ArrayList<Condition>();
		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
        for(SysUserEntity sysUserEntity : allUsers){
            comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
		model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
		//编码类型
        ComboboxVO meetingFlagCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("MEETING_FLAG");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            meetingFlagCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("meetingFlagCombobox", JsonUtil.toJson(meetingFlagCombobox.getOptions()));  
      //编码类型
        ComboboxVO checkStateCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap2  =  DictionaryUtil.getDictionaries("CHECK_STATE");
        
        for(String key :  codeDateTypeMap2.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap2.get(key);
            checkStateCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("checkStateCombobox", JsonUtil.toJson(checkStateCombobox.getOptions()));  
        model.put("rlId", rlId);	
        String flag=request.getParameter("flag");
        model.put("flag", flag);
		return this.createModelAndView("run/runLog/safeMeetingAddSafeForWind", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAddForWind/{rlId}")
	public ModelAndView getAddPageForWind(HttpServletRequest request, @PathVariable Long rlId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SafeMeetingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers = sysUserService.findByCondition(
				conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
            comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
		model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
		//编码类型
        ComboboxVO meetingFlagCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("MEETING_FLAG");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            meetingFlagCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("meetingFlagCombobox", JsonUtil.toJson(meetingFlagCombobox.getOptions()));  
      //编码类型
        ComboboxVO checkStateCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap2  =  DictionaryUtil.getDictionaries("CHECK_STATE");
        
        for(String key :  codeDateTypeMap2.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap2.get(key);
            checkStateCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("checkStateCombobox", JsonUtil.toJson(checkStateCombobox.getOptions()));  
        model.put("rlId", rlId);	
        String flag=request.getParameter("flag");
        model.put("flag", flag);
		return this.createModelAndView("run/runLog/safeMeetingAddForWind", model);
	}
	
	/**
	 * @Description:   新增
	 * @author         wangcc 
	 * @Date           2018年4月24日 下午10:48:21 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/addForWind", method = RequestMethod.POST)
	public @ResponseBody ResultObj addForWind(@RequestBody SafeMeetingEntity meetingEntity ,HttpServletRequest request) {
		return safeMeetingService.addForWind(meetingEntity,request);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAddSafeForWind/{rlId}")
	public ModelAndView getAddSafeForWind(HttpServletRequest request, @PathVariable Long rlId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SafeMeetingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<SysUserEntity> allUsers = sysUserService.findAll();
        for(SysUserEntity sysUserEntity : allUsers){
            comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
		model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));
		//编码类型
        ComboboxVO meetingFlagCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("MEETING_FLAG");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            meetingFlagCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("meetingFlagCombobox", JsonUtil.toJson(meetingFlagCombobox.getOptions()));  
      //编码类型
        ComboboxVO checkStateCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap2  =  DictionaryUtil.getDictionaries("CHECK_STATE");
        
        for(String key :  codeDateTypeMap2.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap2.get(key);
            checkStateCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("checkStateCombobox", JsonUtil.toJson(checkStateCombobox.getOptions()));  
        model.put("rlId", rlId);	
        String flag=request.getParameter("flag");
        model.put("flag", flag);
		return this.createModelAndView("run/runLog/safeMeetingAddSafeForWind", model);
	}
	
	/**
	 * @Description: 批量删除
	 * @author wangchuncheng
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/bulkDeleteForWind")
	public @ResponseBody
	ResultObj bulkDeleteForWind(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		for (Integer id : ids) {
				safeMeetingService.deleteEntityForWind(id.longValue());
		}
		return resultObj;
	}
	
	/**
	* @Title: expData
	* @Description: 工作安排导出
	* @author wangchuncheng
	* @date 2018年4月25日下午4:09:29
	* @param req
	* @param res
	* @throws UnsupportedEncodingException
	* @throws
	*/
	@RequestMapping("/exportExcelForWind/{rlId}")
    public void expDataForWind(HttpServletRequest req,HttpServletResponse res, @PathVariable Long rlId) throws UnsupportedEncodingException{
        List<SafeMeetingEntity> dataList= safeMeetingService.findByConditionForWind(rlId);
        Map<String,Object> resultMap=new HashMap<String, Object>();
        for(int i = 0;i<dataList.size();i++){
			dataList.get(i).setNumber(i+1);
		}
        resultMap.put("dataList", dataList);
        ExcelUtil.export(req, res, "安全交底报表模板.xlsx","安全交底.xlsx", resultMap);
    }
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEditForWind/{id}")
	public ModelAndView getEditPageForWind(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeMeetingEntity safeMeetingEntity = (SafeMeetingEntity)safeMeetingService.findByIdForWind(id);
		model.put("dataMap", safeMeetingEntity);
		model.put("dataMapJson", JsonUtil.toJson(safeMeetingEntity));
		
		List<SafeMeetingEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeMeetingTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeMeetingVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> allUsers = sysUserService.findByCondition(
				conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
            comboSafeMeetingVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
		model.put("safeMeetingCombobox", JsonUtil.toJson(comboSafeMeetingVO.getOptions()));		
		//编码类型
        ComboboxVO meetingFlagCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("MEETING_FLAG");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            meetingFlagCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("meetingFlagCombobox", JsonUtil.toJson(meetingFlagCombobox.getOptions()));  
      //编码类型
        ComboboxVO checkStateCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap2  =  DictionaryUtil.getDictionaries("CHECK_STATE");
        
        for(String key :  codeDateTypeMap2.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap2.get(key);
            checkStateCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("checkStateCombobox", JsonUtil.toJson(checkStateCombobox.getOptions()));  
		return this.createModelAndView("run/runLog/safeMeetingEditForWind", model);
	}
	
	/**
	 * @Description:   更新
	 * @author         wangchuncheng
	 * @Date           2018年4月25日 上23:30:34 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateForWind/{id}/{flag}")
	public @ResponseBody ResultObj updateForWind(@RequestBody SafeMeetingEntity t, HttpServletRequest request ,@PathVariable Long id,@PathVariable boolean flag) {
		return safeMeetingService.updateForWind(t,id,flag);
	}
	
	/**
	 * @Description:   删除
	 * @author         wangcc 
	 * @Date           2018年4月25日 上23:29:34 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/delForWind/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj deleteForWind(HttpServletRequest request ,@PathVariable Long id) {
		return safeMeetingService.deleteForWind(id);
	}
}