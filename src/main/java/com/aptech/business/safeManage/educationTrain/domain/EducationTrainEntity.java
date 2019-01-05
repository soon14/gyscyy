package com.aptech.business.safeManage.educationTrain.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.EducationCategoryTypeEnum;
import com.aptech.business.component.dictionary.SafeCheckHeadTypeEnum;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 教育培训实体类
 *
 * @author 
 * @created 2018-03-31 12:43:38
 * @lastModified 
 * @history
 *
 */
@Alias("EducationTrainEntity")
public class EducationTrainEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 培训名称
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
		 * 培训地点
		 */
    	private String trainAddress;
		/**
		 * 类别
		 */
    	private String category;
		/**
		 * 年号
		 */
    	private Date yearNum;
		/**
		 * 培训时间
		 */
    	private Date trainDate;
		/**
		 * 生产单位
		 */
    	private Long unitId;
		/**
		 * 单位名称
		 */
    	private String unitName;
		/**
		 * 类型
		 */
    	private Long type;
		/**
		 * 相关资料
		 */
    	private String fileId;
    	private String fileName;
    	
    	/**
    	 * 附件名称
    	 */
		private String[] fileNames;
		/**
		 * 附件URL
		 */
		private String[] fileUrl;
		
		private String yearNumString;
		
		private String trainDateString;
		
		private String userName;
    	
       
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
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
		public String getTrainDateString() {
			
			if (trainDateString !=null && trainDateString !="") {
				return trainDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if (this.trainDate==null) {
				return trainDateString;
			}
			return df.format(this.trainDate);
		}
		public void setTrainDateString(String trainDateString) {
			this.trainDateString = trainDateString;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
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
		public String getTrainAddress(){
			return trainAddress;
		}
		public void setTrainAddress(String trainAddress){
			this.trainAddress = trainAddress;
		}
		public String getCategory(){
			return category;
		}
		public void setCategory(String category){
			this.category = category;
		}
		
		public String getCategoryName(){
			if (this.category!=null) {
				Map<String, String> categoryEnumMap = new HashMap<String, String>();
				for(EducationCategoryTypeEnum key : EducationCategoryTypeEnum.values()){
					categoryEnumMap.put(key.getCode(), key.getName());
				}
				return categoryEnumMap.get(this.category);
			}
			return null;
		}
		
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getYearNum(){
			return yearNum;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setYearNum(Date yearNum){
			this.yearNum = yearNum;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getTrainDate(){
			return trainDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setTrainDate(Date trainDate){
			this.trainDate = trainDate;
		}
		public Long getUnitId(){
			return unitId;
		}
		public void setUnitId(Long unitId){
			this.unitId = unitId;
		}
		public String getUnitName(){
			return unitName;
		}
		public void setUnitName(String unitName){
			this.unitName = unitName;
		}
		public Long getType(){
			return type;
		}
		public void setType(Long type){
			this.type = type;
		}
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
		}
}