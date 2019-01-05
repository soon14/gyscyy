package com.aptech.business.overhaul.overhaulLogDetail.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.overhaul.overhaulLogDetail.domain.OverhaulLogDetailEntity;
import com.aptech.business.overhaul.overhaulLogDetail.service.OverhaulLogDetailService;
import com.aptech.business.overhaul.overhaulRecord.domain.OverhaulRecordEntity;
import com.aptech.business.overhaul.overhaulRecord.service.OverhaulRecordService;
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
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修日志明细配置控制器
 *
 * @author 
 * @created 2018-01-04 10:56:15
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/overhaulLogDetail")
public class OverhaulLogDetailController extends BaseController<OverhaulLogDetailEntity> {
	
	@Autowired
	private OverhaulLogDetailService overhaulLogDetailService;
	@Autowired
	private OverhaulRecordService overhaulRecordService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public IBaseEntityOperation<OverhaulLogDetailEntity> getService() {
		return overhaulLogDetailService;
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
		List<OverhaulLogDetailEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("overhaulLogDetailTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOverhaulLogDetailVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("overhaulLogDetailCombobox", JsonUtil.toJson(comboOverhaulLogDetailVO.getOptions()));
		return this.createModelAndView("overhaulLogDetail/resource/views/overhaulLogDetailList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{recordId}")
	public ModelAndView getAddPage(HttpServletRequest request,@PathVariable Long recordId){
		Map<String, Object> model = new HashMap<String, Object>();
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
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
        
        DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH");
		model.put("yeardate", df.format(new Date()));
		model.put("recordId", recordId);
		
		OverhaulRecordEntity recordEntity = overhaulRecordService.findById(recordId);
		model.put("yeardate", df.format(recordEntity.getStartDate()));
		if (recordEntity.getEndDate()!=null) {
			model.put("enddate", df.format(recordEntity.getEndDate()));
		}
		
		return this.createModelAndView("overhaul/overhaulLogDetail/overhaulLogDetailAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}/{recordId}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id,@PathVariable Long recordId){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("D.C_OVERHAUL_RECORD_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, recordId));
		List<OverhaulLogDetailEntity> overhaulLogDetailEntityList = overhaulLogDetailService.findByCondition(conditions, null);
		model.put("entity", overhaulLogDetailEntityList.get(0));
		model.put("entityJson", JsonUtil.toJson(overhaulLogDetailEntityList.get(0)));
		
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
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
		model.put("recordId", recordId);
		
		return this.createModelAndView("overhaul/overhaulLogDetail/overhaulLogDetailEdit", model);
	}
	
	/**
	 * @Description:   工作明细新增
	 * @author         wangcc 
	 * @Date           2018年1月4日 下午1:29:28 
	 * @throws         Exception
	 */
	@RequestMapping("/addMore")
	public @ResponseBody ResultObj addMore(HttpServletRequest request,@RequestBody OverhaulLogDetailEntity overhaulLogDetailEntity){
		return overhaulLogDetailService.addMore(request,overhaulLogDetailEntity);
	}
	
}