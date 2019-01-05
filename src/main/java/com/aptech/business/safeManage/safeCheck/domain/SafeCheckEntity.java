package com.aptech.business.safeManage.safeCheck.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.SafeCheckHeadTypeEnum;
import com.aptech.business.component.dictionary.SafeCheckUnitTypeEnum;
import com.aptech.business.component.dictionary.TrainPlanClassifyEnum;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 安全检查实体类
 *
 * @author 
 * @created 2018-03-28 10:22:17
 * @lastModified 
 * @history
 *
 */
@Alias("SafeCheckEntity")
public class SafeCheckEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 检查名称
		 */
    	private String checkName;
		/**
		 * 年号
		 */
    	private String yearNum;
		/**
		 * 生产单位id
		 */
    	private String unitId;
    	/**
    	 * 展示单位名称
    	 */
    	private String unitName;
		/**
		 * 安全检查类别
		 */
    	private String category;
		/**
		 * 检查时间
		 */
    	private Date checkDate;
		/**
		 * 相关资料名称
		 */
    	private String fileName;
		/**
		 * 相关资料地址
		 */
    	private String fileAddress;
		/**
		 * 安全检查区分标识
		 */
    	private String type;
    	/**
    	 * 导出序号（用于导出excel）
    	 */
    	private int number;
    	/**
    	 * 检查时间字符串格式（用于导出excel）
    	 */
    	private String exportDate;
    	/**
		 * 创建人id
		 */
    	private String createPeopleId;
    	/**
		 * 创建时间
		 */
    	private Date createDate;
    	
    	private String userName;
    	private String correctionRectify;
    	private String remark;
    	private Date correctionDate;
    	private String correctionDateString;
    	private String landWaiter;
    	private Date rectificationTime;
    	private String rectificationDate; 
    	
    	
    	
    	@JsonSerialize(using = JsonDateSerializer.class)
		public Date getRectificationTime() {
			return rectificationTime;
		}
		 @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setRectificationTime(Date rectificationTime) {
			this.rectificationTime = rectificationTime;
		}
		public String getLandWaiter() {
			return landWaiter;
		}
		public void setLandWaiter(String landWaiter) {
			this.landWaiter = landWaiter;
		}
		public String getCorrectionRectify() {
			return correctionRectify;
		}
		public void setCorrectionRectify(String correctionRectify) {
			this.correctionRectify = correctionRectify;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getCorrectionDate() {
			return correctionDate;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setCorrectionDate(Date correctionDate) {
			this.correctionDate = correctionDate;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getCheckName(){
			return checkName;
		}
		public void setCheckName(String checkName){
			this.checkName = checkName;
		}
		public String getYearNum(){
			return yearNum;
		}
		public void setYearNum(String yearNum){
			this.yearNum = yearNum;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getCategory(){
			return category;
		}
		public void setCategory(String category){
			this.category = category;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getCheckDate(){
			return checkDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setCheckDate(Date checkDate){
			this.checkDate = checkDate;
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
		public String getType(){
			return type;
		}
		public void setType(String type){
			this.type = type;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getExportDate() {
			DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.checkDate!=null){
				return this.exportDate = format.format(this.checkDate);
			}else{
				return null;
			}
		}
		public String getCorrectionDateString() {
			DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.correctionDate!=null){
				return this.correctionDateString = format.format(this.correctionDate);
			}else{
				return null;
			}
		}
		public void setExportDate(String exportDate) {
			this.exportDate = exportDate;
		}
		public String getCategoryName(){
			if (this.category!=null) {
				Map<String, String> categoryEnumMap = new HashMap<String, String>();
				for(SafeCheckHeadTypeEnum key : SafeCheckHeadTypeEnum.values()){
					categoryEnumMap.put(key.getCode(), key.getName());
				}
				return categoryEnumMap.get(this.category);
			}
			return null;
		}
		public String getCategoryUnitName(){
			if (this.category!=null) {
				Map<String, String> categoryEnumMap = new HashMap<String, String>();
				for(SafeCheckUnitTypeEnum key : SafeCheckUnitTypeEnum.values()){
					categoryEnumMap.put(key.getCode(), key.getName());
				}
				return categoryEnumMap.get(this.category);
			}
			return null;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getCreatePeopleId() {
			return createPeopleId;
		}
		public void setCreatePeopleId(String createPeopleId) {
			this.createPeopleId = createPeopleId;
		}
		public Date getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
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