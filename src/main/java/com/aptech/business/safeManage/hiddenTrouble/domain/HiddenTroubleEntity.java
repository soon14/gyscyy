package com.aptech.business.safeManage.hiddenTrouble.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 隐患排查实体类
 *
 * @author 
 * @created 2018-03-31 12:52:23
 * @lastModified 
 * @history
 *
 */
@Alias("HiddenTroubleEntity")
public class HiddenTroubleEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 隐患 排查名称
		 */
    	private String troubleName;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 创建人
		 */
    	private String createUserId;
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
    	private Date checkDate;
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
		
		private String checkDateString;
		private String userName;
		
		private Date editDate;//整改时间
		private String editContent;//整改情况
		private Long unitId;//单位
		
		private String unitName;
		
		private String remark;
		
		private String editDateString;
		private String investigator;
		private Date rectificationTime;
		private String rectificationDate; 
		
		
		
		
		public String getInvestigator() {
			return investigator;
		}
		public void setInvestigator(String investigator) {
			this.investigator = investigator;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getRectificationTime() {
			return rectificationTime;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setRectificationTime(Date rectificationTime) {
			this.rectificationTime = rectificationTime;
		}
		public String getEditDateString() {
			if (editDateString !=null && editDateString !="") {
				return editDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if (this.editDate==null) {
				return editDateString;
			}
			return df.format(this.editDate);
		}
		public void setEditDateString(String editDateString) {
			this.editDateString = editDateString;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public Date getEditDate() {
			return editDate;
		}
		public void setEditDate(Date editDate) {
			this.editDate = editDate;
		}
		public String getEditContent() {
			return editContent;
		}
		public void setEditContent(String editContent) {
			this.editContent = editContent;
		}
		public Long getUnitId() {
			return unitId;
		}
		public void setUnitId(Long unitId) {
			this.unitId = unitId;
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
		public String getCheckDateString() {
			if (checkDateString !=null && checkDateString !="") {
				return checkDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if (this.checkDate==null) {
				return checkDateString;
			}
			return df.format(this.checkDate);
		}
		
		public String getCategoryName() {
			Map<String, SysDictionaryVO> typeMap  =  DictionaryUtil.getDictionaries("HIDDEN_TROUBLE_TYPE");
			Map<String,String> typeEnumMap = new HashMap<String, String>();
			for(String key : typeMap.keySet()){
				SysDictionaryVO sysDictionaryVO = typeMap.get(key);
				typeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
			}
			return typeEnumMap.get(this.category);
		}
		public void setCheckDateString(String checkDateString) {
			this.checkDateString = checkDateString;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getTroubleName(){
			return troubleName;
		}
		public void setTroubleName(String troubleName){
			this.troubleName = troubleName;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
		public String getCategory(){
			return category;
		}
		public void setCategory(String category){
			this.category = category;
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
		public Date getCheckDate(){
			return checkDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCheckDate(Date checkDate){
			this.checkDate = checkDate;
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
		public String getRectificationDate() {
			DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.rectificationTime!=null){
				return this.rectificationDate = format.format(this.rectificationTime);
			}else{
				return null;
			}
		}
}