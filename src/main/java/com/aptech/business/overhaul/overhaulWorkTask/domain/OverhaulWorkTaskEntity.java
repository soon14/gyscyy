package com.aptech.business.overhaul.overhaulWorkTask.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
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
 * 检修工作任务实体类
 *
 * @author 
 * @created 2017-12-22 10:15:21
 * @lastModified 
 * @history
 *
 */
@Alias("OverhaulWorkTaskEntity")
public class OverhaulWorkTaskEntity extends BaseEntity{
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
    	/**
    	 * 设备ID
    	 */
    	private Long equipId;
		/**
		 * 设备ID
		 */
    	private String equipName;
		/**
		 * 工作任务
		 */
    	private String workTask;
    	/**
    	 * 完成状态
    	 */
    	private Long finishStatus;
		/**
		 * 完成状态
		 */
    	private String finishStatusString;
    	/**
    	 * 检修纪录Id
    	 */
    	private Long overhaulRecordId;
		/**
		 * 工作安排Id
		 */
    	private String status;
    	/**
    	 * 运行日志ID
    	 */
    	private Long logid;
    	
		/**
    	 * 运行日志编号
    	 */
    	private String logoverhaulNumber;
    	/**
    	 * 运行创建时间
    	 */
    	private Date logcreateDate;
    	/**
    	 * 运行修改时间
    	 */
    	private Date logupdateDate;
    	/**
    	 * 运行创建人
    	 */
    	private Long logcreateUserId;
    	/**
    	 * 运行修改人
    	 */
    	private Long logupdateUserId;
    	/**
    	 * 运行日志时间
    	 */
    	private Date loglogDate;
    	/**
    	 * 运行日志时间
    	 */
    	private String loglogDateString;
    	/**
		 * 单位ID
		 */
    	private Long logunitId;
    	/**
		 * 单位名称
		 */
    	private String logunitName;
    	/**
		 * 检修负责人ID
		 */
    	private String logdutyUserId;
    	/**
    	 * 检修负责人姓名
    	 */
    	private String logdutyUserName;
    	/**
    	 * 填报人
    	 */
    	private long logsubmitUserId;
    	/**
		 * 填报人姓名
		 */
    	private String logsubmitUserName;
    	/**
		 * 删除状态
		 */
    	private String logstatus;
    	/**
		 * 附件Id
		 */
    	private String logfileId;
    	/**
		 * 流程状态
		 */
		private String logprocessStatus;
		/**
		 * 完成状态
		 */
		private String logfinishStatus;
		/**
		 * 完成状态名称
		 */
		private String logfinishStatusString;
		/**
		 * 请假人姓名
		 */
		private String logaskPersonName;
		/**
		 * 请假人姓名
		 */
    	private String LogoutUnitName;
    	/**
		 * 设备下拉列表（检修日志工作带回任务专用）
		 */
    	private String equipCombox;
    	
    	public Long getOverhaulRecordId() {
			return overhaulRecordId;
		}
		public void setOverhaulRecordId(Long overhaulRecordId) {
			this.overhaulRecordId = overhaulRecordId;
		}
    	
    	private List<OverhaulWorkTaskEntity > taskList;
    	
    	
		public List<OverhaulWorkTaskEntity> getTaskList() {
			return taskList;
		}
		public void setTaskList(List<OverhaulWorkTaskEntity> taskList) {
			this.taskList = taskList;
		}
		public String getEquipCombox() {
			return equipCombox;
		}
		public void setEquipCombox(String equipCombox) {
			this.equipCombox = equipCombox;
		}
		public String getLogoutUnitName() {
			return LogoutUnitName;
		}
		public void setLogoutUnitName(String logoutUnitName) {
			LogoutUnitName = logoutUnitName;
		}
		public String getLogaskPersonName() {
			return logaskPersonName;
		}
		public void setLogaskPersonName(String logaskPersonName) {
			this.logaskPersonName = logaskPersonName;
		}
    	public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getFinishStatusString() {
    		Map<String, SysDictionaryVO> finishStatusMap  =  DictionaryUtil.getDictionaries("OVERHUALARRANGE_FINISHSTATUS");
			if(finishStatusMap.keySet().size()>0){
				if(this.finishStatus!=null){
					finishStatusString = finishStatusMap.get(String.valueOf(this.finishStatus)).getName();
				}
			}
			return finishStatusString;
		}
		public void setFinishStatusString(String finishStatusString) {
			this.finishStatusString = finishStatusString;
		}
    	public String getEquipName() {
			return equipName;
		}
		public void setEquipName(String equipName) {
			this.equipName = equipName;
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
		public Long getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(Long createUserId){
			this.createUserId = createUserId;
		}
		public Long getUpdateUserId(){
			return updateUserId;
		}
		public void setUpdateUserId(Long updateUserId){
			this.updateUserId = updateUserId;
		}
		public Long getEquipId(){
			return equipId;
		}
		public void setEquipId(Long equipId){
			this.equipId = equipId;
		}
		public String getWorkTask(){
			return workTask;
		}
		public void setWorkTask(String workTask){
			this.workTask = workTask;
		}
		public Long getFinishStatus(){
			return finishStatus;
		}
		public void setFinishStatus(Long finishStatus){
			this.finishStatus = finishStatus;
		}
		public Long getLogid() {
			return logid;
		}
		public void setLogid(Long logid) {
			this.logid = logid;
		}
		public String getLogoverhaulNumber() {
			return logoverhaulNumber;
		}
		public void setLogoverhaulNumber(String logoverhaulNumber) {
			this.logoverhaulNumber = logoverhaulNumber;
		}
		public Date getLogcreateDate() {
			return logcreateDate;
		}
		public void setLogcreateDate(Date logcreateDate) {
			this.logcreateDate = logcreateDate;
		}
		public Date getLogupdateDate() {
			return logupdateDate;
		}
		public void setLogupdateDate(Date logupdateDate) {
			this.logupdateDate = logupdateDate;
		}
		public Long getLogcreateUserId() {
			return logcreateUserId;
		}
		public void setLogcreateUserId(Long logcreateUserId) {
			this.logcreateUserId = logcreateUserId;
		}
		public Long getLogupdateUserId() {
			return logupdateUserId;
		}
		public void setLogupdateUserId(Long logupdateUserId) {
			this.logupdateUserId = logupdateUserId;
		}
		public Date getLoglogDate() {
			return loglogDate;
		}
		public void setLoglogDate(Date loglogDate) {
			this.loglogDate = loglogDate;
		}
		public String getLoglogDateString() {
			if(loglogDateString !=null && loglogDateString !=""){
				return loglogDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.loglogDate ==null){
				return loglogDateString;
			}
			return df.format(this.loglogDate);
		}
		public void setLoglogDateString(String loglogDateString) {
			this.loglogDateString = loglogDateString;
		}
		public Long getLogunitId() {
			return logunitId;
		}
		public void setLogunitId(Long logunitId) {
			this.logunitId = logunitId;
		}
		public String getLogunitName() {
			return logunitName;
		}
		public void setLogunitName(String logunitName) {
			this.logunitName = logunitName;
		}
		public String getLogdutyUserId() {
			return logdutyUserId;
		}
		public void setLogdutyUserId(String logdutyUserId) {
			this.logdutyUserId = logdutyUserId;
		}
		public String getLogdutyUserName() {
			return logdutyUserName;
		}
		public void setLogdutyUserName(String logdutyUserName) {
			this.logdutyUserName = logdutyUserName;
		}
		public long getLogsubmitUserId() {
			return logsubmitUserId;
		}
		public void setLogsubmitUserId(long logsubmitUserId) {
			this.logsubmitUserId = logsubmitUserId;
		}
		public String getLogsubmitUserName() {
			return logsubmitUserName;
		}
		public void setLogsubmitUserName(String logsubmitUserName) {
			this.logsubmitUserName = logsubmitUserName;
		}
		public String getLogstatus() {
			return logstatus;
		}
		public void setLogstatus(String logstatus) {
			this.logstatus = logstatus;
		}
		public String getLogfileId() {
			return logfileId;
		}
		public void setLogfileId(String logfileId) {
			this.logfileId = logfileId;
		}
		public String getLogprocessStatus() {
			return logprocessStatus;
		}
		public void setLogprocessStatus(String logprocessStatus) {
			this.logprocessStatus = logprocessStatus;
		}
		public String getLogfinishStatus() {
			return logfinishStatus;
		}
		public void setLogfinishStatus(String logfinishStatus) {
			this.logfinishStatus = logfinishStatus;
		}
		public String getLogfinishStatusString() {
			Map<String, SysDictionaryVO> finishStatusMap  =  DictionaryUtil.getDictionaries("OVERHUALARRANGE_FINISHSTATUS");
			if(finishStatusMap.keySet().size()>0 && this.logfinishStatus!=null){
				logfinishStatusString = finishStatusMap.get(String.valueOf(this.logfinishStatus)).getName();
			}
			return logfinishStatusString;
		}
		public void setLogfinishStatusString(String logfinishStatusString) {
			this.logfinishStatusString = logfinishStatusString;
		}
	
}