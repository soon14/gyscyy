package com.aptech.business.messageCenter.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 消息中心实体类
 *
 * @author 
 * @created 2017-08-10 17:12:09
 * @lastModified 
 * @history
 *
 */
@Alias("MessageCenterEntity")
public class MessageCenterEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 主题
		 */
    	private String title;
		/**
		 * 消息内容
		 */
    	private String context;
		/**
		 * 消息发送时间
		 */
    	private Date sendTime;
		/**
		 * 消息发送人
		 */
    	private String sendPerson;
		/**
		 * 消息类型
		 */
    	private String type;
		/**
		 * 消息分类
		 */
    	private String category;
		/**
		 * 消息分组
		 */
    	private String group;
    	/**
		 * 消息接收人
		 */
    	private String receivePerson;
		/**
		 * 消息内容
		 */
    	private Long contextId;
		/**
		 * 消息状态
		 */
    	private String status;
    	/**
    	 * 显示时间
    	 */
    	private String sendTimeString;
    	//是否重要 0为不找用，1位重要
    	private String isnotImportant;
    	
    	
    	
		public String getIsnotImportant() {
			return isnotImportant;
		}
		public void setIsnotImportant(String isnotImportant) {
			this.isnotImportant = isnotImportant;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getTitle(){
			return title;
		}
		public void setTitle(String title){
			this.title = title;
		}
		public String getContext(){
			return context;
		}
		public void setContext(String context){
			this.context = context;
		}
		 @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getSendTime() {
			return sendTime;
		}
		 @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setSendTime(Date sendTime) {
			this.sendTime = sendTime;
		}
		public String getSendPerson(){
			return sendPerson;
		}
		public void setSendPerson(String sendPerson){
			this.sendPerson = sendPerson;
		}
		public String getType(){
			return type;
		}
		public void setType(String type){
			this.type = type;
		}
		public String getCategory(){
			return category;
		}
		public void setCategory(String category){
			this.category = category;
		}
		public String getGroup(){
			return group;
		}
		public void setGroup(String group){
			this.group = group;
		}
		public String getReceivePerson() {
			return receivePerson;
		}
		public void setReceivePerson(String receivePerson) {
			this.receivePerson = receivePerson;
		}
		public Long getContextId() {
			return contextId;
		}
		public void setContextId(Long contextId) {
			this.contextId = contextId;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getSendTimeString() {
			return sendTimeString;
		}
		public void setSendTimeString(String sendTimeString) {
			this.sendTimeString = sendTimeString;
		}
		public String getMessageTypeName(){
			if(!StringUtils.isEmpty(this.type)){
				Map<String, SysDictionaryVO> instockTypeMap  =  DictionaryUtil.getDictionaries("MESSAGE_TYPE");
				Map<String,String> instockTypeEnumMap = new HashMap<String, String>();
				for(String key : instockTypeMap.keySet()){
					SysDictionaryVO sysDictionaryVO = instockTypeMap.get(key);
					instockTypeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return instockTypeEnumMap.get(this.type);
			}
			return null;
		}
		public String getMessageStatusName(){
			if(!StringUtils.isEmpty(this.status)){
				Map<String, SysDictionaryVO> instockTypeMap  =  DictionaryUtil.getDictionaries("MESSAGE_STATUS");
				Map<String,String> instockTypeEnumMap = new HashMap<String, String>();
				for(String key : instockTypeMap.keySet()){
					SysDictionaryVO sysDictionaryVO = instockTypeMap.get(key);
					instockTypeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
				}
				return instockTypeEnumMap.get(this.status);
			}
			return null;
		}
		
}