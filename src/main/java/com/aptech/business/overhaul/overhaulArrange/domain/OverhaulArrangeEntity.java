package com.aptech.business.overhaul.overhaulArrange.domain;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
 * 工作安排实体类
 *
 * @author 
 * @created 2017-12-20 16:24:01
 * @lastModified 
 * @history
 *
 */
@Alias("OverhaulArrangeEntity")
public class OverhaulArrangeEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 创建时间
		 */
    	private Date createDate;
    	private String createDateString;
		/**
		 * 修改时间
		 */
    	private Date updateDate;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 修改人
		 */
    	private String updateUserId;
		/**
		 * 工作地点
		 */
    	private String workPlace;
		/**
		 * 工作项目
		 */
    	private String workProject;
    	/**
    	 * 负责人
    	 */
    	private Long dutyPersonId;
		/**
		 * 完成状态
		 */
    	private Long finishStatus;
    	/**
		 * 完成状态
		 */
    	private String finishStatusString;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 检修日志ID
		 */
    	private Long overhaulLogId;
    	/**
    	 * 负责人姓名
    	 */
		private String dutyPersonName;
		/**
		 * 状态
		 */
		private String status;
		/**
    	 * 流程状态
    	 */
		private String processStatus;
		/**
    	 * 流程状态(审批状态)
    	 */
    	private String processStatusName;
    	/**
    	 * 审批意见
    	 */
    	private String approveIdea;
    	/**
    	 * 请假人员及事由（当日遗留问题）
    	 */
    	private String other;
		/**
    	 * 任务ID
    	 */
		private String taskId;
		private String procInstId;
		
		private String askPersonName;
		
		private Long askPersonId;
		
		private String workArrange;//工人安排
		
		private Date workDate;
		
		private String workDateString;
		
		
		
		public String getWorkDateString() {
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.workDate ==null){
				return workDateString;
			}
			return df.format(this.workDate);
		}
		public void setWorkDateString(String workDateString) {
			this.workDateString = workDateString;
		}
		public Date getWorkDate() {
			return workDate;
		}
		public void setWorkDate(Date workDate) {
			this.workDate = workDate;
		}
		public String getWorkArrange() {
			return workArrange;
		}
		public void setWorkArrange(String workArrange) {
			this.workArrange = workArrange;
		}
		public Long getAskPersonId() {
			return askPersonId;
		}
		public void setAskPersonId(Long askPersonId) {
			this.askPersonId = askPersonId;
		}
		public String getAskPersonName() {
			return askPersonName;
		}
		public void setAskPersonName(String askPersonName) {
			this.askPersonName = askPersonName;
		}
		public String getOther() {
			return other;
		}
		public void setOther(String other) {
			this.other = other;
		}
     	public String getCreateDateString() {
     		if(createDateString !=null && createDateString !=""){
				return createDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.createDate ==null){
				return createDateString;
			}
			return df.format(this.createDate);
		}
		public void setCreateDateString(String createDateString) {
			this.createDateString = createDateString;
		}
		public String getProcessStatusName() {
			for (OverhaulArrangeProcessEnum overhaulArrangeProcessEnum : OverhaulArrangeProcessEnum.values()) {
    			if (StringUtils.equals(overhaulArrangeProcessEnum.getCode(),this.getProcessStatus())) {
    				this.processStatusName =  overhaulArrangeProcessEnum.getName();
    			}
    		}
			return processStatusName;
		}
		public void setProcessStatusName(String processStatusName) {
			this.processStatusName = processStatusName;
		}
		public String getApproveIdea() {
			return approveIdea;
		}
		public void setApproveIdea(String approveIdea) {
			this.approveIdea = approveIdea;
		}
		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
		public String getProcInstId() {
			return procInstId;
		}
		public void setProcInstId(String procInstId) {
			this.procInstId = procInstId;
		}
		public String getProcessStatus() {
			return processStatus;
		}
		public void setProcessStatus(String processStatus) {
			this.processStatus = processStatus;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Long getDutyPersonId() {
			return dutyPersonId;
		}
		public void setDutyPersonId(Long dutyPersonId) {
			this.dutyPersonId = dutyPersonId;
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
		public String getDutyPersonName() {
			return dutyPersonName;
		}
		public void setDutyPersonName(String dutyPersonName) {
			this.dutyPersonName = dutyPersonName;
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
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
		public String getUpdateUserId(){
			return updateUserId;
		}
		public void setUpdateUserId(String updateUserId){
			this.updateUserId = updateUserId;
		}
		public String getWorkPlace(){
			return workPlace;
		}
		public void setWorkPlace(String workPlace){
			this.workPlace = workPlace;
		}
		public String getWorkProject(){
			return workProject;
		}
		public void setWorkProject(String workProject){
			this.workProject = workProject;
		}
		public Long getFinishStatus(){
			return finishStatus;
		}
		public void setFinishStatus(Long finishStatus){
			this.finishStatus = finishStatus;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public Long getOverhaulLogId(){
			return overhaulLogId;
		}
		public void setOverhaulLogId(Long overhaulLogId){
			this.overhaulLogId = overhaulLogId;
		}
}