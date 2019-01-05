package com.aptech.business.safeManage.safeCulture.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.SafeActivityTypeEnum;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.StringUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 安全文化实体类
 *
 * @author 
 * @created 2018-03-28 09:56:01
 * @lastModified 
 * @history
 *
 */
@Alias("SafeCultureEntity")
public class SafeCultureEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 名称
		 */
    	private String name;
		/**
		 * 年号
		 */
    	private String yearNum;
		/**
		 * 类型
		 */
    	private String type;
		/**
		 * 时间
		 */
    	private Date date;
		/**
		 * 相关资料名称
		 */
    	private String fileName;
		/**
		 * 相关资料地址
		 */
    	private String fileAddress;
    	/**
    	 *报表导出序号 
    	 */
    	private Integer number;
    	/**
    	 * 报表导出 时间字符串
    	 */
    	private String dateStr;
    	
    	/**
    	 * 创建人ID
    	 */
    	private String creatorId;
    	/**
    	 * 创建时间
    	 */
    	private Date createTime;
    	private String userId;
    	private String userName;
    	
    	
    	
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getCreatorId() {
			return creatorId;
		}
		public void setCreatorId(String creatorId) {
			this.creatorId = creatorId;
		}
		
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
				this.createTime =createTime;
		}
    	
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}
		public String getDateStr() {
			DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd");
			if(date.equals(null)){
				return dfu.format(date);
			}else{
				return dfu.format(date);
			}
		}
		public void setDateStr(String dateStr) {
			this.dateStr = dateStr;
		}
		public String getTypeName() {
			if(StringUtil.isEmpty(type)){
				return null;
			}else{
				SafeActivityTypeEnum[] arr=SafeActivityTypeEnum.values();
				Map<String,String> typeKey=new HashMap<String,String>();
				for (SafeActivityTypeEnum enu : arr) {
					typeKey.put(enu.getCode(),enu.getName());
				}
				return typeKey.get(type);
			}
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getYearNum(){
			return yearNum;
		}
		public void setYearNum(String yearNum){
			this.yearNum = yearNum;
		}
		public String getType(){
			return type;
		}
		public void setType(String type){
			this.type = type;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getDate(){
			return date;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setDate(Date date){
			this.date = date;
		}
		public String getFileName(){
			return fileName;
		}
		public void setFileName(String fileName){
			this.fileName = fileName;
		}
		public String getFileAddress(){
			return fileAddress;
		}
		public void setFileAddress(String fileAddress){
			this.fileAddress = fileAddress;
		}
}