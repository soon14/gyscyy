package com.aptech.business.fileLearn.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.FileLearnApproveStatusEnum;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 文件学习实体类
 *
 * @author
 * @created 2018-04-02 16:50:12
 * @lastModified
 * @history
 *
 */
@Alias("FileLearnEntity")
public class FileLearnEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 发文单位id
	 */
	private Long sendUnitId;
	/**
	 * 发文单位
	 */
	private String sendUnitName;
	/**
	 * 发文时间
	 */
	private Date sendTime;
	/**
	 * 学习提醒时间
	 */
	private Date warnTime;
	/**
	 * 发布人id
	 */
	private Long sendUserId;
	/**
	 * 发布人
	 */
	private String sendUserName;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否全网
	 */
	private String isAllNet;
	/**
	 * 学习文件
	 */
	private String learnFile;
	/**
	 * 附件
	 */
	private String attachment;
	
	/**
	 * 审核状态
	 */
	private String approveStatus;
	/**
	 * 审核状态dic
	 */
	private String approveStatusDic;
	
	private String approveComment;
	
	/**
	 * 接收人ids
	 */
	private String receiveUserIds;
	/**
	 * 接收人names
	 */
	private String receiveUserNames;
	/**
	 * 是否通知
	 */
	private String isNotification="0";
	/**
	 * 是否重要
	 */
	private String isImportant="0";
	private String taskId;
	private String procInstId;
	private String userList;
	public String getIsImportant() {
		return isImportant;
	}
	public void setIsImportant(String isImportant) {
		this.isImportant = isImportant;
	}
	public String getIsNotification() {
		return isNotification;
	}
	public void setIsNotification(String isNotification) {
		this.isNotification = isNotification;
	}
	private String isLearn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getSendUnitId() {
		return sendUnitId;
	}

	public void setSendUnitId(Long sendUnitId) {
		this.sendUnitId = sendUnitId;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getSendTime() {
		return sendTime;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Long getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(Long sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSendUnitName() {
		return sendUnitName;
	}

	public void setSendUnitName(String sendUnitName) {
		this.sendUnitName = sendUnitName;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getWarnTime() {
		return warnTime;
	}
	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setWarnTime(Date warnTime) {
		this.warnTime = warnTime;
	}

	public String getLearnFile() {
		return learnFile;
	}

	public void setLearnFile(String learnFile) {
		this.learnFile = learnFile;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getIsAllNet() {
		return isAllNet;
	}

	public void setIsAllNet(String isAllNet) {
		this.isAllNet = isAllNet;
	}

	public String getReceiveUserIds() {
		return receiveUserIds;
	}

	public void setReceiveUserIds(String receiveUserIds) {
		this.receiveUserIds = receiveUserIds;
	}

	public String getReceiveUserNames() {
		return receiveUserNames;
	}

	public void setReceiveUserNames(String receiveUserNames) {
		this.receiveUserNames = receiveUserNames;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
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

	public String getIsLearn() {
		return isLearn;
	}

	public void setIsLearn(String isLearn) {
		this.isLearn = isLearn;
	}

	public String getApproveStatusDic() {
		if(FileLearnApproveStatusEnum.PENDING.getCode().equals(this.approveStatus)){
			return FileLearnApproveStatusEnum.PENDING.getName();
		}else if(FileLearnApproveStatusEnum.REJECT.getCode().equals(this.approveStatus)){
			return FileLearnApproveStatusEnum.REJECT.getName();
		}else if(FileLearnApproveStatusEnum.LEADER.getCode().equals(this.approveStatus)){
			return FileLearnApproveStatusEnum.LEADER.getName();
		}else if(FileLearnApproveStatusEnum.PUBLISH.getCode().equals(this.approveStatus)){
			return FileLearnApproveStatusEnum.PUBLISH.getName();
		}else if(FileLearnApproveStatusEnum.END.getCode().equals(this.approveStatus)){
			return FileLearnApproveStatusEnum.END.getName();
		}else {
			return "";
		}
		
	}

	public void setApproveStatusDic(String approveStatusDic) {
		this.approveStatusDic = approveStatusDic;
	}

	public String getApproveComment() {
		return approveComment;
	}

	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}
	public String getSendTimeString() {
		if(sendTime!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return  sdf.format(sendTime);
		}
		return "";
	}
	
	
	// 拓展
}