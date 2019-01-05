package com.aptech.business.overhaul.overhaulRecord.domain;


import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;

/**
 * 
 * 设备检修记录实体类
 *
 * @author 
 * @created 2018-06-05 11:23:18
 * @lastModified 
 * @history
 *
 */
@Alias("OverhaulRecordEntity")
public class OverhaulRecordEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 编码
		 */
    	private String code;
		/**
		 * 项目名称
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
		 * 开始时间
		 */
    	private Date startDate;
		/**
		 * 结束时间
		 */
    	private Date endDate;
		/**
		 * 检修负责人
		 */
    	private Long dutyUserId;
		/**
		 * 检修单位
		 */
    	private Long unitId;
		/**
		 * 设备属地
		 */
    	private String equipLocal;
    	
    	private String startDateString;
    	
    	private String endDateString;
    	
    	private String unitName;
    	
    	private String equipLocalName;
    	
    	private String dutyUserName;
		/**
    	 * 所属组织机构ID
    	 */
    	private Long orgId;
		public Long getOrgId() {
			return orgId;
		}
		public void setOrgId(Long orgId) {
			this.orgId = orgId;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getEquipLocalName() {
			return equipLocalName;
		}
		public void setEquipLocalName(String equipLocalName) {
			this.equipLocalName = equipLocalName;
		}
		public String getDutyUserName() {
			return dutyUserName;
		}
		public void setDutyUserName(String dutyUserName) {
			this.dutyUserName = dutyUserName;
		}
		public String getStartDateString() {
			if (startDateString !=null && startDateString !="") {
				return startDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH");
			if (this.startDate==null) {
				return startDateString;
			}
			return df.format(this.startDate);
		}
		public void setStartDateString(String startDateString) {
			this.startDateString = startDateString;
		}
		public String getEndDateString() {
			if (endDateString !=null && endDateString !="") {
				return endDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH");
			if (this.endDate==null) {
				return endDateString;
			}
			return df.format(this.endDate);
		}
		public void setEndDateString(String endDateString) {
			this.endDateString = endDateString;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
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
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getStartDate(){
			return startDate;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setStartDate(Date startDate){
			this.startDate = startDate;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getEndDate(){
			return endDate;
		}
		 @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setEndDate(Date endDate){
			this.endDate = endDate;
		}
		public Long getDutyUserId(){
			return dutyUserId;
		}
		public void setDutyUserId(Long dutyUserId){
			this.dutyUserId = dutyUserId;
		}
		public Long getUnitId(){
			return unitId;
		}
		public void setUnitId(Long unitId){
			this.unitId = unitId;
		}
		public String getEquipLocal(){
			return equipLocal;
		}
		public void setEquipLocal(String equipLocal){
			this.equipLocal = equipLocal;
		}
}