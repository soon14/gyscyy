package com.aptech.business.ticketManage.operationItem.web;

import java.util.HashMap;
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

import com.aptech.business.ticketManage.operationItem.domain.OperationItemEntity;
import com.aptech.business.ticketManage.operationItem.exception.OperationItemException;
import com.aptech.business.ticketManage.operationItem.exception.OperationItemExceptionType;
import com.aptech.business.ticketManage.operationItem.service.OperationItemService;
import com.aptech.business.ticketManage.operationTicket.domain.OperationTicketEntity;
import com.aptech.business.ticketManage.operationTicket.service.OperationTicketService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.PropertyUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 操作票项目表配置控制器
 * 
 * @author
 * @created 2017-07-12 17:27:36
 * @lastModified
 * @history
 * 
 */
@Controller
@RequestMapping("/operationItem")
public class OperationItemController extends
		BaseController<OperationItemEntity> {

	@Autowired
	private OperationItemService operationItemService;
	@Autowired
	private OperationTicketService operationTicketService;
	@Override
	public IBaseEntityOperation<OperationItemEntity> getService() {
		return operationItemService;
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
		return this.createModelAndView(
				"ticketManage/operationItem/operationItemList", model);
	}
	/**
	 * list页面跳转
	 * 
	 * @Title:
	 * @Description:
	 * @param
	 * @return
	 */
	@RequestMapping("/indexDetail")
	public ModelAndView listDetail(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView(
				"ticketManage/operationItem/operationItemListDetail", model);
	}
	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 操作票实际
		ComboboxVO actualtype = new ComboboxVO();
		Map<String, SysDictionaryVO> actualMap = DictionaryUtil
				.getDictionaries("OPERATION_ACTUAL");
		for (String key : actualMap.keySet()) {
			SysDictionaryVO sysDictionaryVO = actualMap.get(key);
			actualtype.addOption(sysDictionaryVO.getCode(),
					sysDictionaryVO.getName());

		}
		model.put("actualtype", JsonUtil.toJson(actualtype.getOptions()));
		return this.createModelAndView(
				"ticketManage/operationItem/operationItemAdd", model);
	}

	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request,
			@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 操作票实际
		ComboboxVO actualtype = new ComboboxVO();
		Map<String, SysDictionaryVO> actualMap = DictionaryUtil
				.getDictionaries("OPERATION_ACTUAL");
		for (String key : actualMap.keySet()) {
			SysDictionaryVO sysDictionaryVO = actualMap.get(key);
			actualtype.addOption(sysDictionaryVO.getCode(),
					sysDictionaryVO.getName());

		}
		model.put("actualtype", JsonUtil.toJson(actualtype.getOptions()));
		OperationItemEntity t= operationItemService.findById(id);
		model.put("t", t);
		return this.createModelAndView(
				"ticketManage/operationItem/operationItemEdit", model);
	}
	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/getListEdit/{id}")
	public ModelAndView getListEditPage(HttpServletRequest request,
			@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 操作票实际
		ComboboxVO actualtype = new ComboboxVO();
		Map<String, SysDictionaryVO> actualMap = DictionaryUtil
				.getDictionaries("OPERATION_ACTUAL");
		for (String key : actualMap.keySet()) {
			SysDictionaryVO sysDictionaryVO = actualMap.get(key);
			actualtype.addOption(sysDictionaryVO.getCode(),
					sysDictionaryVO.getName());
			
		}
		model.put("actualtype", JsonUtil.toJson(actualtype.getOptions()));
		OperationItemEntity t= operationItemService.findById(id);
		model.put("t", t);
		return this.createModelAndView(
				"ticketManage/operationItem/operationItemListEdit", model);
	}
	/**
	 * 修改
	 * 
	 * @Title: edit
	 * @Description:
	 * @param T
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.POST)
	public @ResponseBody ResultObj edit(@RequestBody OperationItemEntity t){
		Long id = Long.valueOf(t.getId().toString());
		OperationItemEntity entity = this.getService().findById(id);
		String simulation=entity.getSimulation();
		if(!StringUtil.isEmpty(t.getSimulation())){
			simulation=t.getSimulation();
		}
//		if(!StringUtil.isEmpty(t.getSimulation())&&t.getSimulation().equals("1")){
//			t.setOperateItemNum(t.getOperateItemNum()+1);
//		}
//		if(!StringUtil.isEmpty(t.getActual())&&t.getActual().equals("1")){
//			t.setOperateItemNum(t.getOperateItemNum()+1);
//		}
		if(!StringUtil.isEmpty(t.getSimulation())&&t.getSimulation().equals("2")&&StringUtil.isEmpty(t.getReason())){
			throw new OperationItemException(OperationItemExceptionType.OPERATIONITEM_REASON_NULL);
		}
		if(!StringUtil.isEmpty(t.getActual())&&t.getActual().equals("2")&&StringUtil.isEmpty(t.getReason())){
			throw new OperationItemException(OperationItemExceptionType.OPERATIONITEM_REASON_NULL);
		}
		if (entity != null) {
			PropertyUtil.copyProperties(entity, t);
			entity.setSimulation(simulation);
			this.getService().updateEntity(entity);
			OperationTicketEntity operationTicket= operationTicketService.findById(t.getOperationId());
			if(operationTicket!=null){
				operationTicket.setReason(t.getReason());
				operationTicket.setOperateItemNum(t.getOperateItemNum());
				operationTicketService.update(operationTicket);
			}
			
		}
		ResultObj resultObj = new ResultObj();
		resultObj.setData(entity);
		return resultObj;
	}
}