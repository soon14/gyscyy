package com.aptech.business.planManage.plan.domain;

import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.PlanCycleEnum;
import com.aptech.business.component.dictionary.PlanStatusEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 计划管理实体类
 *
 * @author 
 * @created 2017-11-13 15:10:16
 * @lastModified 
 * @history
 *
 */
@Alias("PlanEntity")
public class PlanEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = -7096775951096805267L;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 单位名称
		 */
    	private Long unitId;
		/**
		 * 填报人
		 */
    	private Long userId;
		/**
		 * 计划周期
		 */
    	private String cycle;
		/**
		 * 计划类型
		 */
    	private Long type;
		/**
		 * 计划时间
		 */
    	private String time;
    	/**
		 * 计划时间结束
		 */
    	private String timeEnd;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 附件
		 */
    	private String fileId;
		/**
		 * 状态
		 */
    	private Long status;
		/**
		 * 标记
		 */
    	private String flag;
		/**
		 * 虚拟主键
		 */
    	private String planSum;
    	
    	private String unitName;
    	
    	private String userName;
    	
    	private String userList;
    	
    	private String taskId;
    	
    	private String procInstId;
    	
    	private String  approve;
    	
    	private String  uuid;
    	
    	
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getApprove() {
			return approve;
		}
		public void setApprove(String approve) {
			this.approve = approve;
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
		public String getUserList() {
			return userList;
		}
		public void setUserList(String userList) {
			this.userList = userList;
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
		public String getCycleName() {
			for (PlanCycleEnum planCycleEnum:PlanCycleEnum.values()) {
				if(cycle!=null&&planCycleEnum.getCode().equals(String.valueOf(cycle))){
					return planCycleEnum.getName();
				}
			}
			return "";
		}
		public String getTypeName() {
			Map<String, SysDictionaryVO> typeMap  =  DictionaryUtil.getDictionaries("PLAN_TYPE");
			for(String key :  typeMap.keySet()){
				SysDictionaryVO sysDictionaryVO = typeMap.get(key);
				if(type!=null&&sysDictionaryVO.getCode().equals(String.valueOf(type))){
					 return  sysDictionaryVO.getName();
				}
			}
			return "";
		}
		public String getStatusName() {
			for (PlanStatusEnum planStatusEnum:PlanStatusEnum.values()) {
				if(status!=null&&planStatusEnum.getCode().equals(String.valueOf(status))){
					return planStatusEnum.getName();
				}
			}
			return "";
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public Long getUnitId(){
			return unitId;
		}
		public void setUnitId(Long unitId){
			this.unitId = unitId;
		}
		public Long getUserId(){
			return userId;
		}
		public void setUserId(Long userId){
			this.userId = userId;
		}
		public String getCycle(){
			return cycle;
		}
		public void setCycle(String cycle){
			this.cycle = cycle;
		}
		public Long getType(){
			return type;
		}
		public void setType(Long type){
			this.type = type;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getTimeEnd() {
			return timeEnd;
		}
		public void setTimeEnd(String timeEnd) {
			this.timeEnd = timeEnd;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
		}
		public Long getStatus(){
			return status;
		}
		public void setStatus(Long status){
			this.status = status;
		}
		public String getFlag(){
			return flag;
		}
		public void setFlag(String flag){
			this.flag = flag;
		}
		public String getPlanSum() {
			return planSum;
		}
		public void setPlanSum(String planSum) {
			this.planSum = planSum;
		}
		
}