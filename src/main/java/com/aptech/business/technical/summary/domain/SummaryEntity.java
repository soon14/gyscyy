package com.aptech.business.technical.summary.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aptech.business.component.dictionary.TechnicalStatusEnum;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 技术监督总结实体类
 *
 * @author 
 * @created 2018-03-14 14:02:22
 * @lastModified 
 * @history
 *
 */
@Alias("SummaryEntity")
public class SummaryEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 填报单位id
		 */
    	private String unitId;
		/**
		 * 填报人id
		 */
    	private String tbrId;
		/**
		 * 填报人名字
		 */
    	private String tbrName;
		/**
		 * 时间名称
		 */
    	private String sjmc;
		/**
		 * 附件id
		 */
    	private String fileid;
		/**
		 * 填报时间
		 */
    	private Date time;
		/**
		 * 状态
		 */
    	private Long status;
		/**
		 * 下一步审核人
		 */
    	private String nextshr;
		/**
		 * 总结内容
		 */
    	private String zjnr;
    	
    	private String unitName;
    	
    	private String workStatus;
    	
    	private String workStatusName;
    	
    	private String selectUser;
    	
    	private String saveOrSubmit;
    	
    	private String timeString;
    	private String spFlag;
    	private String approveIdea;
    	private int number;
    	
    	
    	
    	
    	
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getApproveIdea() {
			return approveIdea;
		}
		public void setApproveIdea(String approveIdea) {
			this.approveIdea = approveIdea;
		}
		public String getSpFlag() {
			return spFlag;
		}
		public void setSpFlag(String spFlag) {
			this.spFlag = spFlag;
		}
		public String getSelectUser() {
			return selectUser;
		}
		public void setSelectUser(String selectUser) {
			this.selectUser = selectUser;
		}
		public String getSaveOrSubmit() {
			return saveOrSubmit;
		}
		public void setSaveOrSubmit(String saveOrSubmit) {
			this.saveOrSubmit = saveOrSubmit;
		}
		public String getWorkStatus() {
			return workStatus;
		}
		public void setWorkStatus(String workStatus) {
			this.workStatus = workStatus;
		}
		public void setWorkStatusName(String workStatusName) {
			this.workStatusName = workStatusName;
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
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getTbrId(){
			return tbrId;
		}
		public void setTbrId(String tbrId){
			this.tbrId = tbrId;
		}
		public String getTbrName(){
			return tbrName;
		}
		public void setTbrName(String tbrName){
			this.tbrName = tbrName;
		}
		public String getSjmc(){
			return sjmc;
		}
		public void setSjmc(String sjmc){
			this.sjmc = sjmc;
		}
		public String getFileid(){
			return fileid;
		}
		public void setFileid(String fileid){
			this.fileid = fileid;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getTime(){
			return time;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setTime(Date time){
			this.time = time;
		}
		public Long getStatus(){
			return status;
		}
		public void setStatus(Long status){
			this.status = status;
		}
		public String getNextshr(){
			return nextshr;
		}
		public void setNextshr(String nextshr){
			this.nextshr = nextshr;
		}
		public String getZjnr(){
			return zjnr;
		}
		public void setZjnr(String zjnr){
			this.zjnr = zjnr;
		}
		public String getTimeString() {
			if(time!=null){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    			return  sdf.format(time);
    		}
    		return "";
		}
		public void setTimeString(String timeString) {
			this.timeString = timeString;
		}
		public String getWorkStatusName() {
			for (TechnicalStatusEnum  worktypeenum: TechnicalStatusEnum.values()) {
				if (worktypeenum.getCode().equals(String.valueOf(this.getWorkStatus()))) {
					workStatusName =  worktypeenum.getName();
				}
			}
			return workStatusName;
		}
}