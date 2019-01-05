package com.aptech.business.overhaul.overhaulLog.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.Istypicaleum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeProcessEnum;
import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
import com.aptech.business.overhaul.overhaulLog.service.OverhaulLogService;
import com.aptech.business.safeManage.hiddenTrouble.domain.HiddenTroubleEntity;
import com.aptech.business.ticketManage.workTicket.exception.WorkTicketException;
import com.aptech.business.ticketManage.workTicket.exception.WorkTicketExceptionType;
import com.aptech.business.ticketManage.workTicketFire.domain.WorkTicketFireEntity;
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
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修日志配置控制器
 *
 * @author 
 * @created 2017-06-30 18:28:04
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/overhaulLog")
public class OverhaulLogController extends BaseController<OverhaulLogEntity> {
	
	@Autowired
	private OverhaulLogService overhaulLogService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Override
	public IBaseEntityOperation<OverhaulLogEntity> getService() {
		return overhaulLogService;
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
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		//公司列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
		List<SysUnitEntity> companyList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : companyList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("companyList", JsonUtil.toJson(companyVo.getOptions()));
        //单位下拉列表
        List<Condition> unitConditions = new ArrayList<Condition>();
        unitConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUnitEntity> unitList = sysUnitService.findByCondition(unitConditions, null);
        model.put("unitList", JsonUtil.toJson(unitList));
        
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
        
    	//获取登录人信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserId", JsonUtil.toJson(sysUserEntity.getId()));
		model.put("loginName", JsonUtil.toJson(sysUserEntity.getLoginName()));

		return this.createModelAndView("overhaul/overhaulLog/overhaulLogList", model);
	}
	
	
	/**
	 *	跳转到基础添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddBase(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		SysUnitEntity unitEntity = sysUnitService.findById(userEntity.getUnitId());
		SysUnitEntity companyEntity = sysUnitService.findById(unitEntity.getId());
        //单位下拉列表
        List<Condition> unitConditions = new ArrayList<Condition>();
//        unitConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,companyEntity.getId()));
        unitConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUnitEntity> unitList = sysUnitService.findByCondition(unitConditions, null);
        ComboboxVO unitVo = new ComboboxVO();
        for(SysUnitEntity sysUnitEntity : unitList){
        	unitVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(unitVo.getOptions()));
        //检修负责人拉列表
  		ComboboxVO dutyUserVo = new ComboboxVO();
  		List<Condition> dutyUserConditions = new ArrayList<Condition>();
  		dutyUserConditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,unitEntity.getId()));
  		dutyUserConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUserEntity> userList = sysUserService.findByCondition(dutyUserConditions, null);
        for(SysUserEntity sysUserEntity : userList){
          	dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("dutyUsers", JsonUtil.toJson(dutyUserVo.getOptions()));
        
		//检修分类
		Map<String, SysDictionaryVO> overhaulClassMap  =  DictionaryUtil.getDictionaries("OVERHAUL_CLASS");
		ComboboxVO overhaulVO = new ComboboxVO();
		for(String key : overhaulClassMap.keySet()){
			SysDictionaryVO sysDictionaryVO = overhaulClassMap.get(key);
			overhaulVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("overhaulClass", JsonUtil.toJson(overhaulVO.getOptions()));
		
        //获取日期
  		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
        String dateStr = df.format(new Date());
        model.put("giveDate", dateStr);
        
        //获取登录人信息
        SysUserEntity sysUserEntity = RequestContext.get().getUser();
        model.put("sysUserId", JsonUtil.toJson(sysUserEntity.getId()));
        model.put("sysUserName", JsonUtil.toJson(sysUserEntity.getName()));
        return this.createModelAndView("overhaul/overhaulLog/overhaulLogAdd", model);
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
		OverhaulLogEntity overhaulLogEntity = overhaulLogService.findById(id);
		model.put("overhaulLogEntity", overhaulLogEntity);
		SysUnitEntity unitEntity = sysUnitService.findById(userEntity.getUnitId());
		SysUnitEntity companyEntity = sysUnitService.findById(unitEntity.getId());
        //单位下拉列表
        List<Condition> unitConditions = new ArrayList<Condition>();
//        unitConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,companyEntity.getId()));
        unitConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUnitEntity> unitList = sysUnitService.findByCondition(unitConditions, null);
        ComboboxVO unitVo = new ComboboxVO();
        for(SysUnitEntity sysUnitEntity : unitList){
        	unitVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(unitVo.getOptions()));
        //检修负责人拉列表
  		ComboboxVO dutyUserVo = new ComboboxVO();
  		List<Condition> dutyUserConditions = new ArrayList<Condition>();
  		dutyUserConditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,unitEntity.getId()));
  		dutyUserConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUserEntity> userList = sysUserService.findByCondition(dutyUserConditions, null);
        for(SysUserEntity sysUserEntity : userList){
          	dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("dutyUsers", JsonUtil.toJson(dutyUserVo.getOptions()));
        model.put("id", id);

		//检修分类
		Map<String, SysDictionaryVO> overhaulClassMap  =  DictionaryUtil.getDictionaries("OVERHAUL_CLASS");
		ComboboxVO overhaulVO = new ComboboxVO();
		for(String key : overhaulClassMap.keySet()){
			SysDictionaryVO sysDictionaryVO = overhaulClassMap.get(key);
			overhaulVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("overhaulClass", JsonUtil.toJson(overhaulVO.getOptions()));
		
		//获取登录人信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserId", JsonUtil.toJson(sysUserEntity.getId()));
		model.put("sysUserName", JsonUtil.toJson(sysUserEntity.getName()));
		return this.createModelAndView("overhaul/overhaulLog/overhaulLogEdit", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getReadyAdd/{overhaulLogId}")
	public ModelAndView getReadyAdd(HttpServletRequest request, @PathVariable Long overhaulLogId){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		long id=overhaulLogId;
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		OverhaulLogEntity overhaulLogEntity = overhaulLogService.findById(id);
		model.put("overhaulLogEntity", overhaulLogEntity);
		SysUnitEntity unitEntity = sysUnitService.findById(userEntity.getUnitId());
		SysUnitEntity companyEntity = sysUnitService.findById(unitEntity.getId());
        //单位下拉列表
        List<Condition> unitConditions = new ArrayList<Condition>();
        unitConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,companyEntity.getId()));
        unitConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUnitEntity> unitList = sysUnitService.findByCondition(unitConditions, null);
        ComboboxVO unitVo = new ComboboxVO();
        for(SysUnitEntity sysUnitEntity : unitList){
        	unitVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(unitVo.getOptions()));
        //检修负责人拉列表
  		ComboboxVO dutyUserVo = new ComboboxVO();
  		List<Condition> dutyUserConditions = new ArrayList<Condition>();
  		dutyUserConditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,unitEntity.getId()));
  		dutyUserConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUserEntity> userList = sysUserService.findByCondition(dutyUserConditions, null);
        for(SysUserEntity sysUserEntity : userList){
          	dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
      //获取日期
  		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
        String dateStr = df.format(new Date());
        model.put("dutyUsers", JsonUtil.toJson(dutyUserVo.getOptions()));
        model.put("giveDate", dateStr);
        model.put("id", id);
        
      //检修分类
  		Map<String, SysDictionaryVO> overhaulClassMap  =  DictionaryUtil.getDictionaries("OVERHAUL_CLASS");
  		ComboboxVO overhaulVO = new ComboboxVO();
  		for(String key : overhaulClassMap.keySet()){
  			SysDictionaryVO sysDictionaryVO = overhaulClassMap.get(key);
  			overhaulVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
  		}
  		model.put("overhaulClass", JsonUtil.toJson(overhaulVO.getOptions()));
		return this.createModelAndView("overhaul/overhaulLog/overhaulLogReadyAdd", model);
	}
	
	
	
	/**
	 *	查询公司下的单位
	 */
	@RequestMapping("/getUnitList/{id}")
	public @ResponseBody ResultObj getUnitList(HttpServletRequest request, @PathVariable Long id){
		List<Condition> unitConditions = new ArrayList<Condition>();
		unitConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,id));
		unitConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
		List<SysUnitEntity> companyList = sysUnitService.findByCondition(unitConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		for(SysUnitEntity sysUnitEntity : companyList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
		ResultObj obj = new ResultObj();
		obj.setData(JsonUtil.toJson(companyVo.getOptions()));
		return obj;
	}
	/**
	 *	查询单位下面的人
	 */
	@RequestMapping("/getUserList/{id}")
	public @ResponseBody ResultObj getUserList(HttpServletRequest request, @PathVariable Long id){
		//检修负责人拉列表
		ComboboxVO dutyUserVo = new ComboboxVO();
		List<Condition> dutyUserConditions = new ArrayList<Condition>();
		dutyUserConditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,id));
		dutyUserConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUserEntity> userList = sysUserService.findByCondition(dutyUserConditions, null);
        for(SysUserEntity sysUserEntity : userList){
        	dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        ResultObj obj = new ResultObj();
		obj.setData(JsonUtil.toJson(dutyUserVo.getOptions()));
		return obj;
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		OverhaulLogEntity overhaulLogEntity = overhaulLogService.findById(id);
		model.put("overhaulLogEntity", overhaulLogEntity);
		SysUnitEntity unitEntity = sysUnitService.findById(userEntity.getUnitId());
		SysUnitEntity companyEntity = sysUnitService.findById(unitEntity.getId());
        //单位下拉列表
        List<Condition> unitConditions = new ArrayList<Condition>();
        unitConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,companyEntity.getId()));
        unitConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUnitEntity> unitList = sysUnitService.findByCondition(unitConditions, null);
        ComboboxVO unitVo = new ComboboxVO();
        for(SysUnitEntity sysUnitEntity : unitList){
        	unitVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(unitVo.getOptions()));
        //检修负责人拉列表
  		ComboboxVO dutyUserVo = new ComboboxVO();
  		List<Condition> dutyUserConditions = new ArrayList<Condition>();
  		dutyUserConditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,unitEntity.getId()));
  		dutyUserConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUserEntity> userList = sysUserService.findByCondition(dutyUserConditions, null);
        for(SysUserEntity sysUserEntity : userList){
          	dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("dutyUsers", JsonUtil.toJson(dutyUserVo.getOptions()));
        model.put("id", id);
        
      //检修分类
  		Map<String, SysDictionaryVO> overhaulClassMap  =  DictionaryUtil.getDictionaries("OVERHAUL_CLASS");
  		ComboboxVO overhaulVO = new ComboboxVO();
  		for(String key : overhaulClassMap.keySet()){
  			SysDictionaryVO sysDictionaryVO = overhaulClassMap.get(key);
  			overhaulVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
  		}
  		model.put("overhaulClass", JsonUtil.toJson(overhaulVO.getOptions()));
      		
		return this.createModelAndView("overhaul/overhaulLog/overhaulLogDetail", model);
	}
	
	
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody
	ResultObj update(@RequestBody OverhaulLogEntity t, HttpServletRequest request) {
		 overhaulLogService.updateEntity(t);
		 return new ResultObj();
	}
	
	/**
	 * 导出
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		
		Page<OverhaulLogEntity> page=new Page<OverhaulLogEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
	
		List<OverhaulLogEntity> dataList=overhaulLogService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "检修日志报表模板.xlsx","检修日志.xlsx", resultMap);
		
	}
	
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/searchIndex")
	public ModelAndView searchIndex(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门
		return this.createModelAndView("overhaul/overhaulLog/overhaulLogSearchList", model);
	}
	
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getDetailByDate/{logDate}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable String logDate){
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LOG_DATE",FieldTypeEnum.DATE,MatchTypeEnum.GE,logDate+" 00:00:00"));
		conditions.add(new Condition("C_LOG_DATE",FieldTypeEnum.DATE,MatchTypeEnum.LE,logDate+" 23:59:59"));
		List<OverhaulLogEntity> list = overhaulLogService.findByCondition(conditions, null);
		model.put("list", list);
		return this.createModelAndView("overhaul/overhaulLog/overhaulLogDateDetail", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getDetailDay/{id}")
	public ModelAndView getDetailDay(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		OverhaulLogEntity entity = overhaulLogService.findById(id);
		model.put("entity", entity);
		return this.createModelAndView("overhaul/overhaulLog/overhaulLogDetailDay", model);
	}
	
	/**
	 *	跳转到基础添加页面
	 */
	@RequestMapping("/getMore")
	public ModelAndView getAddMore(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		SysUnitEntity unitEntity = sysUnitService.findById(userEntity.getUnitId());
		SysUnitEntity companyEntity = sysUnitService.findById(unitEntity.getId());
		model.put("unitEntity", unitEntity);
		model.put("companyEntity", companyEntity);
		//检修负责人拉列表
  		ComboboxVO dutyUserVo = new ComboboxVO();
  		List<Condition> dutyUserConditions = new ArrayList<Condition>();
  		dutyUserConditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,unitEntity.getId()));
  		dutyUserConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUserEntity> userList = sysUserService.findByCondition(dutyUserConditions, null);
        for(SysUserEntity sysUserEntity : userList){
          	dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("dutyPersons", JsonUtil.toJson(dutyUserVo.getOptions()));
        Map<String, SysDictionaryVO> powerTypeMap  =  DictionaryUtil.getDictionaries("CHECK_STATE");
		ComboboxVO powerTypeVO = new ComboboxVO();
		for(String key : powerTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = powerTypeMap.get(key);
			powerTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("checkState", JsonUtil.toJson(powerTypeVO.getOptions()));
		return this.createModelAndView("overhaul/overhaulLog/overhaulLogAddMore", model);
	}
	
	/**
	 * @Description:   检修日志新增
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2017年12月25日 上午11:47:58 
	 * @throws         Exception
	 */
	@RequestMapping("/overhaulLogAdd")
	public @ResponseBody <T> ResultObj overhaulLogAdd(@RequestBody OverhaulLogEntity overhaulLogEntity, HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		overhaulLogService.addEntity(overhaulLogEntity);
		Page<T> page = new Page<T>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		List<Condition> Conditions = new ArrayList<Condition>();
		Conditions.add(new Condition("a.C_ID",FieldTypeEnum.INT,MatchTypeEnum.NE,"-1"));
		List<OverhaulLogEntity> overhaulLogEntities =  (List<OverhaulLogEntity>) overhaulLogService.findByCondition(Conditions, page);
		if(!overhaulLogEntities.isEmpty()){
			resultObj.setData(overhaulLogEntities.get(0));
		}
		return resultObj;
	}
	
	/**
	 * @Description:   检修日志新增
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2017年12月25日 上午11:47:58 
	 * @throws         Exception
	 */
	@RequestMapping("/overhaulLogEdit")
	public @ResponseBody <T> ResultObj overhaulLogEdit(@RequestBody OverhaulLogEntity overhaulLogEntity, HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		overhaulLogEntity.setProcessStatus(OverhaulArrangeProcessEnum.WATISUBMIT.getCode());
		overhaulLogService.updEntity(overhaulLogEntity);
		Page<T> page = new Page<T>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		List<Condition> Conditions = new ArrayList<Condition>();
		Conditions.add(new Condition("a.C_ID",FieldTypeEnum.INT,MatchTypeEnum.NE,"-1"));
		List<OverhaulLogEntity> overhaulLogEntities =  (List<OverhaulLogEntity>) overhaulLogService.findByCondition(Conditions, page);
		if(!overhaulLogEntities.isEmpty()){
			resultObj.setData(overhaulLogEntities.get(0));
		}
		return resultObj;
	}
	
	/**
	 * @Description:   批量删除
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2018年1月12日 上午10:17:58
	 * @throws         Exception
	 */
	@RequestMapping(value = "/batchDelete", method = RequestMethod.DELETE)
	public @ResponseBody <T> ResultObj batchDelete(@RequestBody List<Long> ids){
		ResultObj resultObj = new ResultObj();
		overhaulLogService.batchDelete(ids);
		return resultObj;
	}
	
	/**
	 * @Description:   批量删除
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2018年1月12日 上午11:47:58 
	 * @throws         Exception
	 */
	@RequestMapping("/deleteOnlyOne/{id}")
	public @ResponseBody <T> ResultObj deleteOnlyOne(HttpServletRequest request, @PathVariable Long id){
			ResultObj resultObj = new ResultObj();
			overhaulLogService.deleteOnlyOne(id);
			return resultObj;
	}
	
	/**
	 * @Description:   检修日志列表
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2017年12月25日 上午11:47:58 
	 * @throws         Exception
	 */
	@RequestMapping("/searchDate")
	public @ResponseBody  ResultListObj searchDate(HttpServletRequest request,@RequestBody Map<String, Object> params){
			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
			conditions.add(new Condition("a.C_ID",FieldTypeEnum.INT,MatchTypeEnum.NE,"-1"));
			conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			ResultListObj resultObj = new ResultListObj();
			Page<OverhaulLogEntity> page = PageUtil.getPage(params);
			page.setOrders(OrmUtil.changeMapToOrders(params));
			List<OverhaulLogEntity> overhaulLogEntities =  (List<OverhaulLogEntity>) overhaulLogService.findByCondition(conditions, page);
			resultObj.setDraw((Integer)params.get("draw"));
			if (overhaulLogEntities != null) {
				resultObj.setData(overhaulLogEntities);
				resultObj.setRecordsTotal(page.getTotal());
			}
			return resultObj;
	}
	
	/**
	 *	查看首次数据
	 */
	@RequestMapping("/getHistory/{id}")
	public ModelAndView getHistory(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		OverhaulLogEntity overhaulLogEntity = overhaulLogService.findById(id);
		model.put("overhaulLogEntity", overhaulLogEntity);
		SysUnitEntity unitEntity = sysUnitService.findById(userEntity.getUnitId());
		SysUnitEntity companyEntity = sysUnitService.findById(unitEntity.getId());
        //单位下拉列表
        List<Condition> unitConditions = new ArrayList<Condition>();
        unitConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,companyEntity.getId()));
        unitConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUnitEntity> unitList = sysUnitService.findByCondition(unitConditions, null);
        ComboboxVO unitVo = new ComboboxVO();
        for(SysUnitEntity sysUnitEntity : unitList){
        	unitVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(unitVo.getOptions()));
        //检修负责人拉列表
  		ComboboxVO dutyUserVo = new ComboboxVO();
  		List<Condition> dutyUserConditions = new ArrayList<Condition>();
  		dutyUserConditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,unitEntity.getId()));
  		dutyUserConditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
        List<SysUserEntity> userList = sysUserService.findByCondition(dutyUserConditions, null);
        for(SysUserEntity sysUserEntity : userList){
          	dutyUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("dutyUsers", JsonUtil.toJson(dutyUserVo.getOptions()));
        model.put("id", id);
        
      //检修分类
  		Map<String, SysDictionaryVO> overhaulClassMap  =  DictionaryUtil.getDictionaries("OVERHAUL_CLASS");
  		ComboboxVO overhaulVO = new ComboboxVO();
  		for(String key : overhaulClassMap.keySet()){
  			SysDictionaryVO sysDictionaryVO = overhaulClassMap.get(key);
  			overhaulVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
  		}
  		model.put("overhaulClass", JsonUtil.toJson(overhaulVO.getOptions()));
      		
		return this.createModelAndView("overhaul/overhaulLog/overhaulLogHistory", model);
	}
	

}