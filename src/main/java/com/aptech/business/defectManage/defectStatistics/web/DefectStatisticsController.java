package com.aptech.business.defectManage.defectStatistics.web;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.BusinessDictCategoryEnum;
import com.aptech.business.component.dictionary.DefectStatusEnum;
import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.business.defectManage.defectStatistics.domain.DefectStatisticsEntity;
import com.aptech.business.defectManage.defectStatistics.domain.HomeDefectStatisticsVO;
import com.aptech.business.defectManage.defectStatistics.service.DefectStatisticsService;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 缺陷统计配置控制器
 * 
 * @author
 * @created 2017-06-09 09:12:26
 * @lastModified
 * @history
 * 
 */
@Controller
@RequestMapping("/defectStatistics")
public class DefectStatisticsController extends
		BaseController<DefectStatisticsEntity> {

	@Autowired
	private DefectStatisticsService defectStatisticsService;
	@Autowired
	private DefectService defectService;
	@Autowired
	private SysUnitService sysUnitService;

	@Override
	public IBaseEntityOperation<DefectStatisticsEntity> getService() {
		return defectStatisticsService;
	}

	/**
	 * list页面跳转
	 * 
	 * @Title:
	 * @Description:
	 * @param
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		// 设备类型
		Map<String, SysDictionaryVO> equipTypeMap = DictionaryUtil
						.getDictionaries("DEVICE_TYPE");
		ComboboxVO equipType=new  ComboboxVO();
		for(String key :  equipTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = equipTypeMap.get(key);
        	equipType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }	
		model.put("equipType", JsonUtil.toJson(equipType.getOptions()));
		//设备管理类型
		ComboboxVO equipmentTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> equipmentTypeMap = DictionaryUtil.getDictionaries("EQUIPMANAGETYPE");
		for(String key : equipmentTypeMap.keySet()){
			SysDictionaryVO equipmentTypeVO = equipmentTypeMap.get(key);
			equipmentTypeCombobox.addOption(equipmentTypeVO.getCode(), equipmentTypeVO.getName());
		}
		model.put("equipmentTypeCombobox", JsonUtil.toJson(equipmentTypeCombobox.getOptions()));
		return this.createModelAndView(
				"defectManage/defectStatistics/defectStatisticsList", model);
	}

	
//	protected ResultListObj searchData(HttpServletRequest request, Map<String, Object> params, VoClassProvider voClassProvider) {
//		Page<T> page = PageUtil.getPage(params);
//		//List<T> entities = this.getService().findByCondition(params, page);
//		List <T> entities = new ArraryList<T>();
//		ResultListObj resultObj = new ResultListObj();
//		resultObj.setDraw((Integer)params.get("draw"));
//		if (entities != null) {
//			resultObj.setData(PropertyUtil.copyList(voClassProvider.getVoClass(), entities));
//			if (page != null) {
//				resultObj.setRecordsTotal(page.getTotal());
//			} else {
//				resultObj.setRecordsTotal((long) entities.size());
//			}
//		}
//		return resultObj;
//	}
	
	/**
	 * 本月各单位消缺率
	 */
	@RequestMapping("/findScale")
	public @ResponseBody ResultObj findScale(HttpServletRequest request,@RequestBody Map<String, Object> params) {
		ResultObj resultobj = new ResultObj();
		Map<String, Object> model = new HashMap<String, Object>();
		
		Page<DefectEntity> page = new Page<DefectEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.set(Calendar.DAY_OF_MONTH, 1);
		nowCalendar.set(Calendar.HOUR_OF_DAY, 0);
		nowCalendar.set(Calendar.MINUTE, 0);
		nowCalendar.set(Calendar.SECOND, 0);
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(nowCalendar.getTime());
		endCalendar.add(Calendar.MONTH, 1);
		endCalendar.add(Calendar.DAY_OF_MONTH, -1);
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("T.C_FIND_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, nowCalendar.getTime()));
		conditions.add(new Condition("T.C_FIND_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LT, endCalendar.getTime()));
		
		List<DefectEntity> defectEntities = defectService.findByCondition(conditions, page);
		
		Map<String, HomeDefectStatisticsVO> defectStatisticsMap = new HashMap<String, HomeDefectStatisticsVO>();
		for(DefectEntity defectEntity : defectEntities){
			String unitId = defectEntity.getUnitId().toString();
			String unitName = defectEntity.getUnitName();
			HomeDefectStatisticsVO homeDefectStatisticsVO = null;
			if(defectStatisticsMap.containsKey(unitId)){
				homeDefectStatisticsVO = defectStatisticsMap.get(unitId);
			}else{
				homeDefectStatisticsVO = new HomeDefectStatisticsVO();
				homeDefectStatisticsVO.setUnitId(unitId);
				homeDefectStatisticsVO.setUnitName(unitName);
				defectStatisticsMap.put(unitId, homeDefectStatisticsVO);
			}
			
			if(defectEntity.getProcessStatus().equals(DefectStatusEnum.IMPLEMENT.getCode())||
					defectEntity.getProcessStatus().equals(DefectStatusEnum.MONITOR.getCode())||
					defectEntity.getProcessStatus().equals(DefectStatusEnum.BIOTECH_APPROVE.getCode())||
					defectEntity.getProcessStatus().equals(DefectStatusEnum.BIOTECH.getCode())||
					defectEntity.getProcessStatus().equals(DefectStatusEnum.CHECK.getCode())||
					defectEntity.getProcessStatus().equals(DefectStatusEnum.SOLVE.getCode())){
				homeDefectStatisticsVO.setTotalNum(homeDefectStatisticsVO.getTotalNum() + 1);
				if(defectEntity.getProcessStatus().equals(DefectStatusEnum.SOLVE.getCode())){
					homeDefectStatisticsVO.setSolveNum(homeDefectStatisticsVO.getSolveNum() + 1);
				}
			}
		}
		List<String> unitNames = new ArrayList<String>();
		List<BigDecimal> solveNumbers = new ArrayList<BigDecimal>();
		for(String untiId : defectStatisticsMap.keySet()){
			HomeDefectStatisticsVO homeDefectStatisticsVO = defectStatisticsMap.get(untiId);
			unitNames.add(homeDefectStatisticsVO.getUnitName());
			solveNumbers.add(homeDefectStatisticsVO.getSolveScale());
		}
		model.put("unitName", unitNames);
		model.put("solve", solveNumbers);
		resultobj.setData(model);
		resultobj.setResult("success");
		return resultobj;
	}
	
	/**
	 * 根据登录人显示缺陷类型占比
	 */
	@RequestMapping("/findSolveScale")
	public @ResponseBody ResultObj findSolveScale(HttpServletRequest request,@RequestBody Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		Page<DefectEntity> pages = new Page<DefectEntity>();
		pages.setPageSize(Integer.MAX_VALUE);
		List<Condition> conditions=OrmUtil.changeMapToCondition(params);
		//登录人单位id(管理员另行处理)
		//if(!sysUserEntity.getRoleName().equals(""))
		List<Condition> unitCondition = new ArrayList<Condition>();
		unitCondition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,sysUserEntity.getUnitId()));
		List<SysUnitEntity> sysUnitEntities = sysUnitService.findByCondition(unitCondition, null);
		if(sysUnitEntities!=null&&sysUnitEntities.size()!=0){
			for(SysUnitEntity sysUnitEntity : sysUnitEntities){
				if(sysUnitEntity.getParentId()!=0){
					String companyId = sysUnitEntity.getParentId().toString();
					conditions.add(new Condition("T3.C_ID",FieldTypeEnum.STRING,MatchTypeEnum.EQ,companyId));
				}else{
					conditions.add(new Condition("T3.C_ID",FieldTypeEnum.STRING,MatchTypeEnum.EQ,sysUserEntity.getUnitId()));
				}
			}
		}
		//获取登录人场站的
		List<DefectEntity>  defectEntities = defectService.findByCondition(conditions, pages);
		Map<String, SysDictionaryVO> defectTypeMap = DictionaryUtil.getDictionaries(BusinessDictCategoryEnum.DEFECT_TYPE.name());
		List<Map<String, Object>> defectScaleList = new ArrayList<Map<String, Object>>();
		for(SysDictionaryVO defectType : defectTypeMap.values()){
			int defectTypeNum = 0;
			int totalNum = 0;
			Map<String, Object> defectMap = new HashMap<String, Object>();
			for(DefectEntity defectEntity : defectEntities){
				if(defectEntity.getProcessStatus().equals(DefectStatusEnum.IMPLEMENT.getCode())||
						defectEntity.getProcessStatus().equals(DefectStatusEnum.MONITOR.getCode())||
						defectEntity.getProcessStatus().equals(DefectStatusEnum.BIOTECH_APPROVE.getCode())||
						defectEntity.getProcessStatus().equals(DefectStatusEnum.BIOTECH.getCode())||
						defectEntity.getProcessStatus().equals(DefectStatusEnum.CHECK.getCode())||
						defectEntity.getProcessStatus().equals(DefectStatusEnum.SOLVE.getCode())){
					totalNum++;
					if(defectEntity.getType().equals(defectType.getCode())){
						//TODO
						defectTypeNum ++;
					}
				}
			}
			
			BigDecimal defectTypeScale = BigDecimal.ZERO;
			if(totalNum>0){
				defectTypeScale = new BigDecimal(defectTypeNum).multiply(new BigDecimal(100)).divide(new BigDecimal(totalNum), 2, RoundingMode.HALF_UP);
			}
			defectMap.put("name", defectType.getName());
			defectMap.put("value", defectTypeScale);
			defectScaleList.add(defectMap);
		}

		resultObj.setData(defectScaleList);
		return resultObj;
	}
	/**
	 * @Description:   导出
	 * @author         changl 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	@SuppressWarnings("unchecked")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditions=req.getParameter("conditions");
		params = (Map<String, Object>)JSONObject.fromObject(conditions);
		List<DefectStatisticsEntity> dataList=defectStatisticsService.findByCondition(params, null);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		List<int[]> dataSize=new ArrayList<int[]>();
		int start=3;
		for (int i=0;i<dataList.size();i++) {
			DefectStatisticsEntity ds=dataList.get(i);
			if(StringUtil.isEmpty(ds.getUnitName())){
				dataSize.add(new int []{start,3+i,0,0});
				start=3+i+1;
			}
		}
		resultMap.put("dataSize", dataSize);
		ExcelUtil.export(req, res, "缺陷统计报表模板.xlsx","缺陷统计.xlsx", resultMap);
	}
}