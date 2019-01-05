package com.aptech.business.messageCenter.web;

import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.messageBox.domain.MessageBoxEntity;
import com.aptech.business.messageBox.service.MessageBoxService;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.common.workflow.todoTask.service.TodoTaskService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 消息中心配置控制器
 *
 * @author 
 * @created 2017-08-10 17:12:09
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/messageCenter")
public class MessageCenterController extends BaseController<MessageCenterEntity> {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private MessageBoxService messageBoxService;
	@Autowired
	private MessageCenterService messageCenterService;
	
	@Override
	public IBaseEntityOperation<MessageCenterEntity> getService() {
		return messageCenterService;
	}
	
	/**
	 * 
	 * 消息中心首页
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月15日 下午7:00:40
	 * @lastModified
	 */
	@RequestMapping("/index")
	public ModelAndView tabIndex(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView("messageCenter/messageCenterTab", model);
	}
	
	/**
	 *	收件箱选项卡
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/recIndex")
	public ModelAndView recIndex(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		ComboboxVO comboMessageTypeVO = new ComboboxVO();
		Map<String, SysDictionaryVO> messageTypeMap  =  DictionaryUtil.getDictionaries("MESSAGE_TYPE");
		for(String key : messageTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = messageTypeMap.get(key);
			comboMessageTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("messageTypeCombo", JsonUtil.toJson(comboMessageTypeVO.getOptions()));
		ComboboxVO comboMessageStatusVO = new ComboboxVO();
		Map<String, SysDictionaryVO> messageStatusMap  =  DictionaryUtil.getDictionaries("MESSAGE_STATUS");
		for(String key : messageStatusMap.keySet()){
			SysDictionaryVO sysDictionaryVO = messageStatusMap.get(key);
			comboMessageStatusVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("messageStatusCombo", JsonUtil.toJson(comboMessageStatusVO.getOptions()));
		return this.createModelAndView("messageCenter/messageCenterList", model);
	}
	
	/**
	 * 
	 * 收件箱列表检索数据
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月16日 下午1:31:57
	 * @lastModified
	 */
	@RequestMapping(value = "/recList")
	public  @ResponseBody ResultListObj recList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		//获取登录人信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		//按照时间倒序排列
		Page<MessageCenterEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.desc("sendTime"));
		pages.addOrder(Sort.asc("status"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_RECEIVE_PERSON",FieldTypeEnum.STRING,MatchTypeEnum.EQ,sysUserEntity.getId()));
		conditions.add(new Condition("C_STATUS",FieldTypeEnum.STRING,MatchTypeEnum.NE,'2'));
		List<MessageCenterEntity> MessageCenterEntities = messageCenterService.findByCondition(conditions, pages);
		if(null != MessageCenterEntities && MessageCenterEntities.size()!=0){
			for(MessageCenterEntity messageCenterEntity : MessageCenterEntities){
				if(messageCenterEntity!=null){
					SysUserEntity sendUserEntity = sysUserService.findById(Long.valueOf(messageCenterEntity.getSendPerson()));
					messageCenterEntity.setSendPerson(sendUserEntity.getName());
				}
			}
		}
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (MessageCenterEntities != null) {
			if (pages != null) {
				resultObj.setData(MessageCenterEntities);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultObj;
	}
	
	/**
	 * 
	 * 跳转消息查看页面
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月16日 下午1:32:26
	 * @lastModified
	 */
	@RequestMapping("/showPage/{id}")
	public ModelAndView showPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//通过xml文件可知id为messageStatus的id
		MessageBoxEntity messageBoxEntity = messageBoxService.findById(id);
		// 返回前台数据项
		MessageCenterEntity messageCenterEntity = (MessageCenterEntity)messageCenterService.findById(messageBoxEntity.getContextId());
		if(messageCenterEntity!=null){
			Date sendTimeDate = messageCenterEntity.getSendTime();
			if(sendTimeDate!=null){
				DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
				String sendTimeString = sdf.format(sendTimeDate);
				messageCenterEntity.setSendTimeString(sendTimeString);
			}
			SysUserEntity sendUserEntity = sysUserService.findById(Long.valueOf(messageCenterEntity.getSendPerson()));
			messageCenterEntity.setSendPerson(sendUserEntity.getName());
		}
		model.put("entity", messageCenterEntity);
		model.put("entityJson", JsonUtil.toJson(messageCenterEntity));
		ComboboxVO comboMessageCenterVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("messageCenterCombobox", JsonUtil.toJson(comboMessageCenterVO.getOptions()));
		//用户已经打开查看页面，消息更改为已读状态
		if(messageBoxEntity != null){
			messageBoxEntity.setStatus("1");
			messageBoxService.updateEntity(messageBoxEntity);
		}
		return this.createModelAndView("messageCenter/messageCenterShow", model);
	}
	
	/**
	 * 
	 * 收件箱单行删除
	 * 
	 * @param @param id
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月16日 下午2:15:55
	 * @lastModified
	 */
	@RequestMapping(value = "/singleDel/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj singleDel(@PathVariable Long id){
		MessageBoxEntity messageBoxEntity = messageBoxService.findById(id);
		if(messageBoxEntity != null){
			messageBoxEntity.setStatus("2");
			messageBoxService.updateEntity(messageBoxEntity);
		}
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	/**
	 * 收件箱批量删除
	 */
	@RequestMapping(value = "/bulkDel", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDel(@RequestBody List<Integer> ids){
		for (Integer id : ids) {
			long longId = (long)id;
			MessageBoxEntity messageBoxEntity = messageBoxService.findById(longId);
			if(messageBoxEntity != null){
				messageBoxEntity.setStatus("2");
				messageBoxService.updateEntity(messageBoxEntity);
			}
		}
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	/**
	 * 
	 * 发件箱选项卡
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月15日 下午7:01:07
	 * @lastModified
	 */
	@RequestMapping("/sendIndex")
	public ModelAndView sendIndex(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		//从数据字典查信息类型
		ComboboxVO comboMessageTypeVO = new ComboboxVO();
		Map<String, SysDictionaryVO> messageTypeMap  =  DictionaryUtil.getDictionaries("MESSAGE_TYPE");
		for(String key : messageTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = messageTypeMap.get(key);
			comboMessageTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("messageTypeCombo", JsonUtil.toJson(comboMessageTypeVO.getOptions()));
		//从数据字典查信息分类
		ComboboxVO comboMessageCategoryVO = new ComboboxVO();
		Map<String, SysDictionaryVO> messageCategoryMap  =  DictionaryUtil.getDictionaries("MESSAGE_CATEGORY");
		for(String key : messageCategoryMap.keySet()){
			SysDictionaryVO sysDictionaryVO = messageCategoryMap.get(key);
			comboMessageCategoryVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("messageCategoryCombo", JsonUtil.toJson(comboMessageCategoryVO.getOptions()));
		
		return this.createModelAndView("messageCenter/messageCenterAdd", model);
	}
	
	/**
	 * 
	 * 消息分类变更时调用
	 * 
	 * @param @param request
	 * @param @param categoryCode
	 * @param @return
	 * @return Map<String,Object>
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月16日 下午4:16:39
	 * @lastModified
	 */
	@RequestMapping("/getGroup/{categoryCode}")
	public @ResponseBody Map<String, Object> getInfoItems(HttpServletRequest request, @PathVariable String categoryCode){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//如果是职务返回职务下职位明细
		if(StringUtil.isNotEmpty(categoryCode) && categoryCode.equals("duty")){
			List<SysDutiesEntity> sysDutiesEntities = sysDutiesService.findAll();
			ComboboxVO messageGroupCombobox = new ComboboxVO();
			for(SysDutiesEntity sysDutiesEntity : sysDutiesEntities){
				messageGroupCombobox.addOption(sysDutiesEntity.getCode(), sysDutiesEntity.getName());
			}
			resultMap.put("messageGroupData", messageGroupCombobox.getOptions());
		}
		return resultMap;
	}
	
	/**
	 * 
	 * 发送消息
	 * 
	 * @param @param messageEntity
	 * @param @param request
	 * @param @return
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月16日 下午5:35:11
	 * @lastModified
	 */
	@RequestMapping("/addMessage")
	public @ResponseBody ResultObj addMessage(@RequestBody MessageCenterEntity messageEntity, HttpServletRequest request) throws ParseException{
		//发送时间
		DateFormatUtil df = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
		Date sendMessageTime = df.parse(df.format(new Date())); 
		//发送人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		messageEntity.setSendPerson(sysUserEntity.getId().toString());
		messageEntity.setSendTime(sendMessageTime);
		messageCenterService.addMessage(messageEntity);
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	/**
	 * 
	 * 获取未读消息数量
	 * 
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月17日 下午7:27:36
	 * @lastModified
	 */
	@RequestMapping("/searchMessageCount")
	public @ResponseBody ResultObj searchMessageCount(){
		ResultObj resultObj = new ResultObj();
		int messageCount = messageCenterService.searchMessageCount();
		resultObj.setData(messageCount);
		return resultObj;
	}
	
	/**
	 * 
	 * 获取代办任务数量
	 * 
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月17日 下午7:27:55
	 * @lastModified
	 */
	@RequestMapping("/searchTaskCount")
	public @ResponseBody ResultObj searchTaskCount(){
		ResultObj resultObj = new ResultObj();
		int taskCount = todoTaskService.findTaskCount();
		resultObj.setData(taskCount);
		return resultObj;
	}
	
	
}