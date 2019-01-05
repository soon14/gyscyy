package com.aptech.business.overhaul.overhaulLogDetail.domain;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aptech.business.overhaul.overhaulSafe.domain.OverhaulSafeEntity;
import com.aptech.business.overhaul.overhaulSpareconsume.domain.OverhaulSpareconsumeEntity;
import com.aptech.business.overhaul.overhaulWorkTask.domain.OverhaulWorkTaskEntity;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 
 * 检修日志明细实体类
 *
 * @author 
 * @created 2018-01-04 10:56:15
 * @lastModified 
 * @history
 *
 */
@Alias("OverhaulLogDetailEntity")
public class OverhaulLogDetailEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 修改时间
		 */
    	private Date updateDate;
		/**
		 * 创建人
		 */
    	private Long createUserId;
		/**
		 * 修改人
		 */
    	private Long updateUserId;
		
    	private Long overhaulRecordId;
		/**
		 * 日期
		 */
    	private Date logDate;
    	/**
    	 * 所属组织机构
    	 */
    	private Long unitId;
		/**
		 * 外协单位
		 */
    	private String unitName;
		/**
		 * 工作班成员
		 */
    	private String teamMember;
		/**
		 * 工作完成情况
		 */
    	private String workFinishInfo;
		/**
		 * 遗留问题和应急措施
		 */
    	private String problemMeasures;
		/**
		 * 班前会
		 */
    	private String beforeMeet;
		/**
		 * 班后会
		 */
    	private String afterMeet;
		/**
		 * 负责人
		 */
    	private Long dutyPerson;
    	private String dutyPersonName;
		/**
		 * 状态
		 */
    	private String status;
    	/**
    	 * 工作班成员
    	 */
    	private String teamMemberNames;
    	/**
    	 * 工作班成员
    	 */
    	private String teamMemberIds;
    	/**
		 * 附件Id
		 */
    	private String fileId;
    	/**
    	 * 工作明细
    	 */
    	private List<OverhaulWorkTaskEntity> workTaskList;
		/**
    	 * 安全交底
    	 */
    	private List<OverhaulSafeEntity> saftList1;
    	private List<OverhaulSafeEntity> saftList2;
    	private List<OverhaulWorkTaskEntity > taskList;
    	//设备消耗
    	private List<OverhaulSpareconsumeEntity> spareconsumeList;
    	
    	
    	private Date endDate;
    	private String logDateString;
    	
    	private String endDateString;
    	
    	public Long getUnitId() {
			return unitId;
		}
		public void setUnitId(Long unitId) {
			this.unitId = unitId;
		}
    	public Long getOverhaulRecordId() {
			return overhaulRecordId;
		}
		public void setOverhaulRecordId(Long overhaulRecordId) {
			this.overhaulRecordId = overhaulRecordId;
		}
    	public String getEndDateString() {
    		if(endDateString !=null && endDateString !=""){
				return endDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if(this.endDate ==null){
				return endDateString;
			}
			return df.format(this.endDate);
		}
		public void setEndDateString(String endDateString) {
			this.endDateString = endDateString;
		}
		public String getLogDateString() {
			if(logDateString !=null && logDateString !=""){
				return logDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			if(this.logDate ==null){
				return logDateString;
			}
			return df.format(this.logDate);
		}
		public void setLogDateString(String logDateString) {
			this.logDateString = logDateString;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
    	public Date getEndDate() {
			return endDate;
		}
		 @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		public List<OverhaulWorkTaskEntity> getTaskList() {
			return taskList;
		}
		public void setTaskList(List<OverhaulWorkTaskEntity> taskList) {
			this.taskList = taskList;
		}
		public List<OverhaulSpareconsumeEntity> getSpareconsumeList() {
			return spareconsumeList;
		}
		public void setSpareconsumeList(
				List<OverhaulSpareconsumeEntity> spareconsumeList) {
			this.spareconsumeList = spareconsumeList;
		}
		public String getDutyPersonName() {
			return dutyPersonName;
		}
		public void setDutyPersonName(String dutyPersonName) {
			this.dutyPersonName = dutyPersonName;
		}
    	public List<OverhaulWorkTaskEntity> getWorkTaskList() {
			return workTaskList;
		}
		public void setWorkTaskList(List<OverhaulWorkTaskEntity> workTaskList) {
			this.workTaskList = workTaskList;
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
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getLogDate() {
			return logDate;
		}
		 @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setLogDate(Date logDate) {
			this.logDate = logDate;
		}
    	public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getTeamMemberNames() {
			return teamMemberNames;
		}
		public void setTeamMemberNames(String teamMemberNames) {
			this.teamMemberNames = teamMemberNames;
		}
		public String getTeamMemberIds() {
			return teamMemberIds;
		}
		public void setTeamMemberIds(String teamMemberIds) {
			this.teamMemberIds = teamMemberIds;
		}
		public String getFileId() {
			return fileId;
		}
		public void setFileId(String fileId) {
			this.fileId = fileId;
		}
		public List<OverhaulSafeEntity> getSaftList1() {
			return saftList1;
		}
		public void setSaftList1(List<OverhaulSafeEntity> saftList1) {
			this.saftList1 = saftList1;
		}
		public List<OverhaulSafeEntity> getSaftList2() {
			return saftList2;
		}
		public void setSaftList2(List<OverhaulSafeEntity> saftList2) {
			this.saftList2 = saftList2;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
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
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
		public String getTeamMember(){
			return teamMember;
		}
		public void setTeamMember(String teamMember){
			this.teamMember = teamMember;
		}
		public String getWorkFinishInfo(){
			return workFinishInfo;
		}
		public void setWorkFinishInfo(String workFinishInfo){
			this.workFinishInfo = workFinishInfo;
		}
		public String getProblemMeasures(){
			return problemMeasures;
		}
		public void setProblemMeasures(String problemMeasures){
			this.problemMeasures = problemMeasures;
		}
		public String getBeforeMeet(){
			return beforeMeet;
		}
		public void setBeforeMeet(String beforeMeet){
			this.beforeMeet = beforeMeet;
		}
		public String getAfterMeet(){
			return afterMeet;
		}
		public void setAfterMeet(String afterMeet){
			this.afterMeet = afterMeet;
		}
		public Long getDutyPerson(){
			return dutyPerson;
		}
		public void setDutyPerson(Long dutyPerson){
			this.dutyPerson = dutyPerson;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
}