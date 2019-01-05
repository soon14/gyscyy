package com.aptech.business.defectManage.check.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * ȱ������ʵ����
 *
 * @author 
 * @created 2017-06-05 15:59:06
 * @lastModified 
 * @history
 *
 */
@Alias("checkEntity")
public class CheckEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = 5008617554241195614L;
		/**
		 * ����
		 */
    	private Long id;
		/**
		 * ȱ��id
		 */
    	private String defectId;
		/**
		 * ����ֵ��Աid
		 */
    	private String userId;
		/**
		 * �������
		 */
    	private String checkOpinions;
		/**
		 * ����ʱ��
		 */
    	private Date checkTime;
    	
    	private String taskId;
    	
    	private String procInstId;
    	/**
		 * ����id
		 */
    	private String userName;
    	
    	private String processStatus;
    	
    	private String userList;
    	
    	private String remark;
    	
    	private String fileId;
    	
    	private String result;
    	
    	private int lossEletricity;
    	
    	
    	private String approveIdea;
    	
    	
    	
    	public String getApproveIdea() {
			return approveIdea;
		}

		public void setApproveIdea(String approveIdea) {
			this.approveIdea = approveIdea;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getFileId() {
			return fileId;
		}

		public void setFileId(String fileId) {
			this.fileId = fileId;
		}

		public String getUserList() {
    		return userList;
    	}

    	public void setUserList(String userList) {
    		this.userList = userList;
    	}
    	public String getProcessStatus() {
			return processStatus;
		}
		public void setProcessStatus(String processStatus) {
			this.processStatus = processStatus;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getProcInstId() {
			return procInstId;
		}
		public void setProcInstId(String procInstId) {
			this.procInstId = procInstId;
		}
		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getDefectId(){
			return defectId;
		}
		public void setDefectId(String defectId){
			this.defectId = defectId;
		}
		public String getUserId(){
			return userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
		public String getCheckOpinions(){
			return checkOpinions;
		}
		public void setCheckOpinions(String checkOpinions){
			this.checkOpinions = checkOpinions;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCheckTime(){
			return checkTime;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCheckTime(Date checkTime){
			this.checkTime = checkTime;
		}

		public int getLossEletricity() {
			return lossEletricity;
		}

		public void setLossEletricity(int lossEletricity) {
			this.lossEletricity = lossEletricity;
		}
	    
}