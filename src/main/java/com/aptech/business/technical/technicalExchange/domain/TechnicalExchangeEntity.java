package com.aptech.business.technical.technicalExchange.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 技术交流实体类
 *
 * @author 
 * @created 2018-09-17 10:37:44
 * @lastModified 
 * @history
 *
 */
@Alias("TechnicalExchangeEntity")
public class TechnicalExchangeEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 交流时间
		 */
    	private Date time;
		/**
		 * 交流申请人
		 */
    	private String userId;
		/**
		 * 交流单位
		 */
    	private String unitId;
		/**
		 * 交流主题
		 */
    	private String name;
		/**
		 * 交流内容
		 */
    	private String content;
		/**
		 * 附件
		 */
    	private String fileId;
    	private String userName;
    	private String unitName;
    	private int number;
    	
    	
    	
    	
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getTime(){
			return time;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setTime(Date time){
			this.time = time;
		}
		public String getUserId(){
			return userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getContent(){
			return content;
		}
		public void setContent(String content){
			this.content = content;
		}
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
		}
		public String getTimeString() {
    		if(time!=null){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			return  sdf.format(time);
    		}
    		return "";
    	}
}