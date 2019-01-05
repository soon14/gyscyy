package com.aptech.business.companyTrends.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.CompanyTrendsStatusEnum;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 公司动态实体类
 *
 * @author 
 * @created 2018-04-03 11:20:52
 * @lastModified 
 * @history
 *
 */
@Alias("CompanyTrendsEntity")
public class CompanyTrendsEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = -4172911580707998253L;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 标题
		 */
    	private String title;
		/**
		 * 副标题
		 */
    	private String subTitle;
		/**
		 * 发布时间
		 */
    	private Date publishTime;
		/**
		 * 结束时间
		 */
    	private Date endTime;
		/**
		 * 上传人
		 */
    	private String uploadPerson;
		/**
		 * 上传时间
		 */
    	private Date uploadTime;
		/**
		 * 状态,1待提交，2已发布，3已完成，4待部门领导审核，5部门领导驳回，6待党群部审核，7党群部驳回，8审核通过
		 */
    	private String status;
		/**
		 * 是否上推荐位，true为上，false为不上
		 */
    	private String commendStatus;
		/**
		 * 推荐位图片
		 */
    	private String commendPicture;
		/**
		 * 首页图片
		 */
    	private String homePagePicture;
		/**
		 * 内容
		 */
    	private String content;
    	/**
    	 * 预览内容
    	 */
    	private String preview;
		/**
		 * 附件
		 */
    	private String fileAddress;
    	/**
    	 * 导出序号（用于导出excel）
    	 */
    	private int number;
    	/**
    	 * 发布时间（用于导出excel和页面查看）
    	 */
    	private String publishTimeShow;
    	/**
    	 * 结束时间（用于导出excel和页面查看）
    	 */
    	private String endTimeShow;
    	/**
    	 * 上传时间（用于导出excel和页面查看）
    	 */
    	private String uploadTimeShow;
    	/**
		 * 创建人id
		 */
    	private String createPeopleId;
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
		private String taskId;

     	private String procInstId;

     	private String readFlag;
     	
     	/**
     	 * 置顶
     	 */
     	private String setTop;
     	/**
     	 * 置顶时间
     	 */
     	private Date setTopDate; 
     	
     	/**
     	 * 字符串型发布时间
     	 */
     	private String publishTimeStr;
     	
     	/**
     	 * 字符串型结束时间
     	 */
     	private String endTimeStr;
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getTitle(){
			return title;
		}
		public void setTitle(String title){
			this.title = title;
		}
		public String getSubTitle(){
			return subTitle;
		}
		public void setSubTitle(String subTitle){
			this.subTitle = subTitle;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getPublishTime(){
			return publishTime;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setPublishTime(Date publishTime){
			this.publishTime = publishTime;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getEndTime(){
			return endTime;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setEndTime(Date endTime){
			this.endTime = endTime;
		}
		public String getUploadPerson(){
			return uploadPerson;
		}
		public void setUploadPerson(String uploadPerson){
			this.uploadPerson = uploadPerson;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getUploadTime(){
			return uploadTime;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setUploadTime(Date uploadTime){
			this.uploadTime = uploadTime;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public String getCommendStatus(){
			return commendStatus;
		}
		public void setCommendStatus(String commendStatus){
			this.commendStatus = commendStatus;
		}
		public String getCommendPicture(){
			return commendPicture;
		}
		public void setCommendPicture(String commendPicture){
			this.commendPicture = commendPicture;
		}
		public String getHomePagePicture(){
			return homePagePicture;
		}
		public void setHomePagePicture(String homePagePicture){
			this.homePagePicture = homePagePicture;
		}
		public String getContent(){
			return content;
		}
		public void setContent(String content){
			this.content = content;
		}
		public String getFileAddress(){
			return fileAddress;
		}
		public void setFileAddress(String fileAddress){
			this.fileAddress = fileAddress;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getPublishTimeShow() {
			DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.publishTime!=null){
				return this.publishTimeShow = format.format(this.publishTime);
			}else{
				return null;
			}
		}
		public void setPublishTimeShow(String publishTimeShow) {
			this.publishTimeShow = publishTimeShow;
		}
		public String getEndTimeShow() {
			DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.endTime!=null){
				return this.endTimeShow = format.format(this.endTime);
			}else{
				return null;
			}
		}
		public void setEndTimeShow(String endTimeShow) {
			this.endTimeShow = endTimeShow;
		}
		public String getUploadTimeShow() {
			DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.uploadTime!=null){
				return this.uploadTimeShow = format.format(this.uploadTime);
			}else{
				return null;
			}
		}
		public void setUploadTimeShow(String uploadTimeShow) {
			this.uploadTimeShow = uploadTimeShow;
		}
		public String getStatusName() {
			if (this.status!=null) {
				Map<String, String> StatusEnumMap = new HashMap<String, String>();
		        for(CompanyTrendsStatusEnum key : CompanyTrendsStatusEnum.values()){
		        	StatusEnumMap.put(key.getCode(), key.getName());
				}
				return StatusEnumMap.get(this.status);
			}
			return null;
		}
		public String getProcessStatus() {
			return processStatus;
		}
		public void setProcessStatus(String processStatus) {
			this.processStatus = processStatus;
		}
		public String getProcessStatusName() {
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
		public String getPreview() {
			return preview;
		}
		public void setPreview(String preview) {
			this.preview = preview;
		}
		public Object getCommendPictureUrl() {
			Map<String, Object> map = new HashMap<String, Object>();
			if(!StringUtils.equals(this.commendPicture, "[]")){
				map = (Map<String, Object>)JsonUtil.getMapFromJson(this.commendPicture.replace("[", "").replace("]", ""));
				return ".."+map.get("url");
			}
			
			return "";
		}
		public String getCreatePeopleId() {
			return createPeopleId;
		}
		public void setCreatePeopleId(String createPeopleId) {
			this.createPeopleId = createPeopleId;
		}
		public String getReadFlag() {
			return readFlag;
		}
		public void setReadFlag(String readFlag) {
			this.readFlag = readFlag;
		}
		public String getSetTop() {
			return setTop;
		}
		public void setSetTop(String setTop) {
			this.setTop = setTop;
		}
		public Date getSetTopDate() {
			return setTopDate;
		}
		public void setSetTopDate(Date setTopDate) {
			this.setTopDate = setTopDate;
		}
		public String getPublishTimeStr() {
			return publishTimeStr;
		}
		public void setPublishTimeStr(String publishTimeStr) {
			this.publishTimeStr = publishTimeStr;
		}
		public String getEndTimeStr() {
			return endTimeStr;
		}
		public void setEndTimeStr(String endTimeStr) {
			this.endTimeStr = endTimeStr;
		}
		
}