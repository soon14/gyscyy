package com.aptech.business.technical.technicalExchange.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.aptech.business.technical.eventNotification.domain.EventNotificationEntity;
import com.aptech.business.technical.technicalExchange.domain.TechnicalExchangeEntity;
import com.aptech.business.technical.technicalExchange.service.TechnicalExchangeService;
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
import com.aptech.framework.web.base.PageUtil;

/**
 * 
 * 技术交流配置控制器
 *
 * @author 
 * @created 2018-09-17 10:37:44
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/technicalExchange")
public class TechnicalExchangeController extends BaseController<TechnicalExchangeEntity> {
	
	@Autowired
	private TechnicalExchangeService technicalExchangeService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Override
	public IBaseEntityOperation<TechnicalExchangeEntity> getService() {
		return technicalExchangeService;
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
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboTechnicalExchangeVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("technicalExchangeCombobox", JsonUtil.toJson(comboTechnicalExchangeVO.getOptions()));
		//工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();
  	    SysUserEntity userEntity= RequestContext.get().getUser();
        //TODO下拉框具体内容根据具体业务定制
  	    List<Condition> conditions=new ArrayList<Condition>();
  	    conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
  	    conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));//选择人员时只列出当前场站的人员
        List<SysUserEntity> allUsers = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        SysUserEntity sysUserEntity = RequestContext.get().getUser();
        model.put("userId", sysUserEntity.getId());
        model.put("loginName", sysUserEntity.getLoginName());
        model.put("unitId", userEntity.getUnitId());
		return this.createModelAndView("technical/technicalExchange/technicalExchangeList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboTechnicalExchangeVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("technicalExchangeCombobox", JsonUtil.toJson(comboTechnicalExchangeVO.getOptions()));
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		return this.createModelAndView("technical/technicalExchange//technicalExchangeAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		TechnicalExchangeEntity technicalExchangeEntity = (TechnicalExchangeEntity)technicalExchangeService.findById(id);
		model.put("entity", technicalExchangeEntity);
		model.put("entityJson", JsonUtil.toJson(technicalExchangeEntity));
		model.put("id", technicalExchangeEntity.getId());
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(technicalExchangeEntity.getUserId()));
		model.put("sysUserEntity", sysUserEntity);
		
		return this.createModelAndView("technical/technicalExchange/technicalExchangeEdit", model);
	}
	/**
	 *	跳转到详细页面
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		TechnicalExchangeEntity technicalExchangeEntity = (TechnicalExchangeEntity)technicalExchangeService.findById(id);
        resultMap.put("technicalExchangeEntity", technicalExchangeEntity);
        SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(technicalExchangeEntity.getUnitId()));
        SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(technicalExchangeEntity.getUserId()));
        resultMap.put("sysUnitEntity", sysUnitEntity);
        resultMap.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("technical/technicalExchange/technicalExchangeDetail", resultMap);
	}
	/**
	 * @Description:   导出
	 * @author         changl 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditions=req.getParameter("conditions");
		Page<TechnicalExchangeEntity> page = new Page<TechnicalExchangeEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("id"));
		params = (Map<String, Object>)JSONObject.fromObject(conditions);
		List<TechnicalExchangeEntity> dataList=technicalExchangeService.findByCondition(params, null);
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).setNumber(i+1);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "技术交流模板.xlsx","技术交流.xlsx", resultMap);
	}
}