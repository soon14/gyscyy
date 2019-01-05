package com.aptech.business.standardManage.standard.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.StandardTypeEnum;
import com.aptech.business.file.web.ZipUtils;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.standardManage.standard.domain.StandardEntity;
import com.aptech.business.standardManage.standard.service.StandardService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 标准管理配置控制器
 * 
 * @author
 * @created 2017-12-07 10:17:00
 * @lastModified
 * @history
 * 
 */
@Controller
@RequestMapping("/standard")
public class StandardController extends BaseController<StandardEntity> {

	@Autowired
	private StandardService standardService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	
	@Autowired
	private OperateLogService operateLogService;

	@Override
	public IBaseEntityOperation<StandardEntity> getService() {
		return standardService;
	}

	/**
	 * list页面跳转
	 * 
	 * @Title:
	 * @Description:
	 * @param
	 * @return
	 */
	@RequestMapping("/index/{type}")
	public ModelAndView list(HttpServletRequest request,
			Map<String, Object> params, @PathVariable Long type) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		for (StandardTypeEnum standardTypeEnum : StandardTypeEnum.values()) {
			if (standardTypeEnum.getCode().equals(type.toString())) {
				model.put("standardType", standardTypeEnum.getName());
			}
		}
		// 人员
		ComboboxVO searchuser = new ComboboxVO();

		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
 		
//		List<SysUserEntity> userList = sysUserService.findByCondition(new ArrayList<Condition>(), null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		
		String [] notContainUnitLevel = {"8","10","11","13"};
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.STRING, MatchTypeEnum.NOT_IN, notContainUnitLevel));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		
		ComboboxVO unitCombo = new ComboboxVO();
		
		for(SysUnitEntity unit : unitList){
			unitCombo.addOption(unit.getId().toString(), unit.getName());
		}
		model.put("unitList", JsonUtil.toJson(unitCombo.getOptions()));
		
		return this.createModelAndView("standardManage/standard/standardList", model);
	}

	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		String [] notContainUnitLevel = {"8","10","11","13"};
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.STRING, MatchTypeEnum.NOT_IN, notContainUnitLevel));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		
		ComboboxVO unitCombo = new ComboboxVO();
		
		for(SysUnitEntity unit : unitList){
			unitCombo.addOption(unit.getId().toString(), unit.getName());
		}
		model.put("unitList", JsonUtil.toJson(unitCombo.getOptions()));
		
		return this.createModelAndView("standardManage/standard/standardAdd", model);
	}

	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request,
			@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		StandardEntity standardEntity =standardService.findById(id);
		model.put("standardEntity", standardEntity);
		
		String [] notContainUnitLevel = {"8","10","11","13"};
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.STRING, MatchTypeEnum.NOT_IN, notContainUnitLevel));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		
		ComboboxVO unitCombo = new ComboboxVO();
		
		for(SysUnitEntity unit : unitList){
			unitCombo.addOption(unit.getId().toString(), unit.getName());
		}
		model.put("unitList", JsonUtil.toJson(unitCombo.getOptions()));
		
		return this.createModelAndView("standardManage/standard/standardEdit", model);
	}
	/**
	 * 下载
	 * @throws IOException 
	 */
	@RequestMapping("/upload/{ids}")
	public void upload(HttpServletRequest request,@PathVariable List<Long> ids,HttpServletResponse response) throws IOException {
	        response.setContentType("APPLICATION/OCTET-STREAM");  
	        response.setHeader("Content-Disposition","attachment; filename=myfile.zip");
	        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
	        String realPath=request.getSession().getServletContext().getRealPath("/");
	        try {
	        	for (Long id: ids) {
	        		StandardEntity standardEntity=standardService.findById(id);
	        		JSONArray jSONArray=JSONArray.fromObject(standardEntity.getFileId());
	        		for (int i = 0; i < jSONArray.size() ;i++) {
	        			JSONObject jSONObject=(JSONObject) jSONArray.get(i);
	        			 ZipUtils.doCompress(realPath+jSONObject.get("url"), out);
					}
	            }
	        	response.flushBuffer();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	            out.close();
	        }

	}
	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request,
			@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		StandardEntity standardEntity =standardService.findById(id);
		model.put("standardEntity", standardEntity);
		return this.createModelAndView("standardManage/standard/standardDetail",
				model);
	}
	
	/**
	 * @Description: 删除
	 * @param id 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/deleteOne/{id}")
	public @ResponseBody
	ResultObj deleteOne( @PathVariable Long id) {
		ResultObj resultObj = new ResultObj();
		standardService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.STANDARD.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		return resultObj;
	}
	/**
	 * @Description: 批量删除
	 * @param ids 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/allDelete")
	public @ResponseBody
	ResultObj allDelete(@RequestBody List<String> ids) {
		ResultObj resultObj = new ResultObj();
		for (String id : ids) {
			Long longId = new Long(id);
			StandardEntity entity = standardService.findById(longId);
			if (entity != null) {
				standardService.deleteEntity(longId);
				SysUserEntity userEntity = RequestContext.get().getUser();
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.STANDARD.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
			}
		}
		return resultObj;
	}
}