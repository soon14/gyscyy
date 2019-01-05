package com.aptech.business.overhaul.overhaulLog.domain;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 检修日志实体类
 *
 * @author 
 * @created 2017-06-30 18:28:04
 * @lastModified 
 * @history
 *
 */
@Alias("OverhaulLogEntity")
public class OverhaulLogEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 日志编号
		 */
    	private String overhaulNumber;
    	/**
    	 * 创建时间
    	 */
    	private Date createDate;
		/**
    	 * 创建时间
    	 */
    	private Long createUserId;
		/**
		/**
		 * 修改时间
		 */
    	private Date updateDate;
    	/**
    	 * 创建时间
    	 */
    	private Long updateUserId;
		/**
		 * 日志时间
		 */
    	private Date logDate;
    	/**
    	 * 日志时间
    	 */
		private String logDateString;
		/**
		 * 单位ID
		 */
    	private Long unitId;
		/**
		 * 单位名称
		 */
    	private String unitName;
		/**
		 * 检修负责人ID
		 */
    	private String dutyUserId;
    	/**
    	 * 检修负责人姓名
    	 */
    	private String dutyUserName;
		/**
		 * 检修负责人登陆帐号
		 */
    	private String dutyUserLoginName;
		/**
		 * 删除状态
		 */
    	private String status;
    	/**
    	 * 外协单位名称
    	 */
    	private String company;
    	/**
    	 * 外协单位ID
    	 */
    	private long companyid;
    	/**
    	 * 填报人
    	 */
    	private long submitUserId;
		/**
		 * 填报人姓名
		 */
    	private String submitUserName;
    	/**
		 * 附件Id
		 */
    	private String fileId;
		/**
    	 * 附件名称
    	 */
		private String[] fileName;
		/**
		 * 附件URL
		 */
		private String[] fileUrl;
		/**
		 * 运行日志ID(工作安排使用)
		 */
		private String overhaulLogId;
		/**
		 * 流程状态
		 */
		private String processStatus;
		/**
		 * 完成状态
		 */
		private String finishStatus;
		/**
		 * 完成状态名称
		 */
    	private String finishStatusString;
    	
    	private String overhaulClass; //检修分类
    	private String overhaulClassName;
    	
    	private String askPersonName;
    	private String outUnitName;
    	private String askPersonId;
    	
 
		public String getAskPersonId() {
			return askPersonId;
		}
		public void setAskPersonId(String askPersonId) {
			this.askPersonId = askPersonId;
		}
		public String getAskPersonName() {
			return askPersonName;
		}
		public void setAskPersonName(String askPersonName) {
			this.askPersonName = askPersonName;
		}
		public String getOutUnitName() {
			return outUnitName;
		}
		public void setOutUnitName(String outUnitName) {
			this.outUnitName = outUnitName;
		}
		public String getOverhaulClassName() {
			return overhaulClassName;
		}
		public void setOverhaulClassName(String overhaulClassName) {
			this.overhaulClassName = overhaulClassName;
		}
		public String getOverhaulClass() {
			return overhaulClass;
		}
		public void setOverhaulClass(String overhaulClass) {
			this.overhaulClass = overhaulClass;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
    	public Date getLogDate() {
			return logDate;
		}
    	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setLogDate(Date logDate) {
			this.logDate = logDate;
		}
    	@JsonSerialize(using = JsonDateTimeSerializer.class)
    	public Date getCreateDate() {
			return createDate;
		}
    	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
    	@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getUpdateDate() {
			return updateDate;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setUpdateDate(Date updateDate) {
			this.updateDate = updateDate;
		}
    	public String getFinishStatusString() {
    		Map<String, SysDictionaryVO> finishStatusMap  =  DictionaryUtil.getDictionaries("OVERHUALARRANGE_FINISHSTATUS");
			if(finishStatusMap.keySet().size()>0){
				finishStatusString = finishStatusMap.get(String.valueOf(this.finishStatus)).getName();
			}
			return finishStatusString;
		}
		public void setFinishStatusString(String finishStatusString) {
			this.finishStatusString = finishStatusString;
		}
		public String getFinishStatus() {
			return finishStatus;
		}
		public void setFinishStatus(String finishStatus) {
			this.finishStatus = finishStatus;
		}
		public String getDutyUserLoginName() {
			return dutyUserLoginName;
		}
		public void setDutyUserLoginName(String dutyUserLoginName) {
			this.dutyUserLoginName = dutyUserLoginName;
		}
    	public Long getCreateUserId() {
			return createUserId;
		}
		public void setCreateUserId(Long createUserId) {
			this.createUserId = createUserId;
		}
		public Long getUpdateUserId() {
			return updateUserId;
		}
		public void setUpdateUserId(Long updateUserId) {
			this.updateUserId = updateUserId;
		}
    	public String getDutyUserId() {
			return dutyUserId;
		}
		public void setDutyUserId(String dutyUserId) {
			this.dutyUserId = dutyUserId;
		}
    	public String getProcessStatus() {
			return processStatus;
		}
		public void setProcessStatus(String processStatus) {
			this.processStatus = processStatus;
		}
		public String getOverhaulLogId() {
			return overhaulLogId;
		}
		public void setOverhaulLogId(String overhaulLogId) {
			this.overhaulLogId = overhaulLogId;
		}
    	public String getFileId() {
			return fileId;
		}
		public void setFileId(String fileId) {
			this.fileId = fileId;
		}
		public String[] getFileName() {
			return fileName;
		}
		public void setFileName(String[] fileName) {
			this.fileName = fileName;
		}
		public String[] getFileUrl() {
			return fileUrl;
		}
		public void setFileUrl(String[] fileUrl) {
			this.fileUrl = fileUrl;
		}
    	public long getSubmitUserId() {
			return submitUserId;
		}
		public void setSubmitUserId(long submitUserId) {
			this.submitUserId = submitUserId;
		}
		public String getSubmitUserName() {
			return submitUserName;
		}
		public void setSubmitUserName(String submitUserName) {
			this.submitUserName = submitUserName;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getOverhaulNumber(){
			return overhaulNumber;
		}
		public void setOverhaulNumber(String overhaulNumber){
			this.overhaulNumber = overhaulNumber;
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
		public String getDutyUserName(){
			return dutyUserName;
		}
		public void setDutyUserName(String dutyUserName){
			this.dutyUserName = dutyUserName;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public String getLogDateString() {
			if(logDateString !=null && logDateString !=""){
				return logDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.logDate ==null){
				return logDateString;
			}
			return df.format(this.logDate);
		}
		public void setLogDateString(String logDateString) {
			this.logDateString = logDateString;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public long getCompanyid() {
			return companyid;
		}
		public void setCompanyid(long companyid) {
			this.companyid = companyid;
		}
}