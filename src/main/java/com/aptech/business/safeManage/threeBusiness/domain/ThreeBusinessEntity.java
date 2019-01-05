package com.aptech.business.safeManage.threeBusiness.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.EducationCategoryTypeEnum;
import com.aptech.business.component.dictionary.ThreeBusCategoryTypeEnum;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 三项业务实体类
 *
 * @author 
 * @created 2018-04-03 13:17:33
 * @lastModified 
 * @history
 *
 */
@Alias("ThreeBusinessEntity")
public class ThreeBusinessEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 名称
		 */
    	private String name;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 类别
		 */
    	private String category;
		/**
		 * 年号
		 */
    	private Date yearNum;
		/**
		 * 时间
		 */
    	private Date businessDate;
		/**
		 * 相关资料
		 */
    	private String fileId;
    	
    	private  String  fileName;
    	
    	/**
    	 * 附件名称
    	 */
		private String[] fileNames;
		/**
		 * 附件URL
		 */
		private String[] fileUrl;
		
		private String yearNumString;
		
		private String businessDateString;
		private String userName;
		private String unitId;
		private String unitName;
		
		
		
		
		public String getUnitId() {
			return unitId;
		}


		public void setUnitId(String unitId) {
			this.unitId = unitId;
		}


		public String getUnitName() {
			return unitName;
		}


		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}


		public String getUserName() {
			return userName;
		}


		public void setUserName(String userName) {
			this.userName = userName;
		}


		public String getFileName() {
			return fileName;
		}


		public void setFileName(String fileName) {
			this.fileName = fileName;
		}


		public String getCategoryName(){
			if (this.category!=null) {
				Map<String, String> categoryEnumMap = new HashMap<String, String>();
				for(ThreeBusCategoryTypeEnum key : ThreeBusCategoryTypeEnum.values()){
					categoryEnumMap.put(key.getCode(), key.getName());
				}
				return categoryEnumMap.get(this.category);
			}
			return null;
		}
		
		
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getYearNum() {
			return yearNum;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setYearNum(Date yearNum) {
			this.yearNum = yearNum;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getBusinessDate() {
			return businessDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setBusinessDate(Date businessDate) {
			this.businessDate = businessDate;
		}
		public String[] getFileNames() {
			return fileNames;
		}
		public void setFileNames(String[] fileNames) {
			this.fileNames = fileNames;
		}
		public String[] getFileUrl() {
			return fileUrl;
		}
		public void setFileUrl(String[] fileUrl) {
			this.fileUrl = fileUrl;
		}
		public String getYearNumString() {
			if (yearNumString !=null && yearNumString !="") {
				return yearNumString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy");
			if (this.yearNum==null) {
				return yearNumString;
			}
			return df.format(this.yearNum);
		}
		public void setYearNumString(String yearNumString) {
			this.yearNumString = yearNumString;
		}
		public String getBusinessDateString() {
			
			if (businessDateString !=null && businessDateString !="") {
				return businessDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if (this.businessDate==null) {
				return businessDateString;
			}
			return df.format(this.businessDate);
		}
		public void setBusinessDateString(String businessDateString) {
			this.businessDateString = businessDateString;
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
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getCategory(){
			return category;
		}
		public void setCategory(String category){
			this.category = category;
		}
		
	
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
		}
}