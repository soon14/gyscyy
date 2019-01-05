package com.aptech.business.train.trainManagement.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.TrainPlanClassifyEnum;
import com.aptech.business.component.dictionary.TrainPlanRangeEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 培训管理实体类
 * @author Administrator
 *
 */
@Alias("trainManagementEntity")
public class TrainManagementEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2801741976973199634L;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 单位id
		 */
    	private String unitId;
    	/**
    	 * 展示单位名称
    	 */
    	private String unitName;
		
		/**
		 * 课题名称
		 */
    	private String titleName;
		/**
		 * 培训分类
		 */
    	private String classify;
		/**
		 * 培训范围
		 */
    	private String range;
		/**
		 * 培训时间
		 */
    	private Date time;
		/**
		 * 培训人数
		 */
    	private String count;
		/**
		 * 培训时长
		 */
    	private String duration;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 培训信息
		 */
    	private String message;
    	/**
    	 * 审核状态，1同意，0驳回
    	 */
    	private String status;
    	
    	private String statusName;
    	/**
    	 * 参加人员
    	 */
    	private String people;
    	
		/**
    	 * 导出序号（用于导出excel）
    	 */
    	private int number;
    	/**
		 * 创建人id
		 */
    	private String createPeopleId;
    	/**
		 * 创建时间
		 */
    	private Date createDate;
    	
    	/**
    	 * 培训人
    	 */
    	private String trainPersion;
    	
    	private String taskId;
    	
    	/**
    	 * 选择use
    	 */
    	private String userList;
    	
    	private String procInstId;
    	
    	/**
    	 * 审核人Id
    	 */
    	private String reviewerID;
    	/**
    	 * 审核人名称
    	 */
    	private String reviewerName;
    	/**
    	 * 审核人时间
    	 */
    	private String reviewTime;
    	/**
    	 * 审核人结果 1同意 2不同意
    	 */
    	private String reviewResult;
    	private String reviewResultCN;
    	
    	/**
    	 * 发文标题
    	 */
    	private String dispatchTitle;
    	
    	/**
    	 * 发文字号
    	 */
    	private String dispatchNumber;
    	
    	private Long dispatchId;
		//培训地点
    	private String trainLocation;
    	//考核方式
    	private String assessmentMethod;
    	//培训内容
    	private String trainContent;
    	
    	
    	
    	
		public String getTrainLocation() {
			return trainLocation;
		}
		public void setTrainLocation(String trainLocation) {
			this.trainLocation = trainLocation;
		}
		public String getAssessmentMethod() {
			return assessmentMethod;
		}
		public void setAssessmentMethod(String assessmentMethod) {
			this.assessmentMethod = assessmentMethod;
		}
		public String getTrainContent() {
			return trainContent;
		}
		public void setTrainContent(String trainContent) {
			this.trainContent = trainContent;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getStatus() {
			return status;
		}
		public String getPeople() {
			return people;
		}
		public void setPeople(String people) {
			this.people = people;
		}
		public String getStatusName(){
			if (this.status!=null) {
				for(TrainApprovalStatusEnum key : TrainApprovalStatusEnum.values()){
					if (key.getCode().equals(status)) {
						statusName = key.getName();
						break;
					}
				}
			}
			return statusName;
		}
		
		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}
		
		public String getClassifyName(){
			if (this.classify!=null) {
				Map<String, String> classifyEnumMap = new HashMap<String, String>();
				for(TrainPlanClassifyEnum key : TrainPlanClassifyEnum.values()){
					classifyEnumMap.put(key.getCode(), key.getName());
				}
				return classifyEnumMap.get(this.classify);
			}
			return null;
		}
		public String getRangeName(){
			if (this.range!=null) {
				Map<String, String> rangeEnumMap = new HashMap<String, String>();
				for(TrainPlanRangeEnum key : TrainPlanRangeEnum.values()){
					rangeEnumMap.put(key.getCode(), key.getName());
				}
				return rangeEnumMap.get(this.range.toString());
			}
			return null;
		}
		public String getShowTime() {
			if (time != null) {
				DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
				return dfu.format(this.time);
			}
			return "";
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getUnitId() {
			return unitId;
		}
		public void setUnitId(String unitId) {
			this.unitId = unitId;
		}
		public String getTitleName() {
			return titleName;
		}
		public void setTitleName(String titleName) {
			this.titleName = titleName;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getClassify() {
			return classify;
		}
		public void setClassify(String classify) {
			this.classify = classify;
		}
		public String getRange() {
			return range;
		}
		public void setRange(String range) {
			this.range = range;
		}
		@JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getTime() {
			return time;
		}
		@JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setTime(Date time) {
			this.time = time;
		}
		public String getCount() {
			return count;
		}
		public void setCount(String count) {
			this.count = count;
		}
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getCreatePeopleId() {
			return createPeopleId;
		}
		public void setCreatePeopleId(String createPeopleId) {
			this.createPeopleId = createPeopleId;
		}
		public Date getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		public String getTrainPersion() {
			return trainPersion;
		}
		public void setTrainPersion(String trainPersion) {
			this.trainPersion = trainPersion;
		}
		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
		public String getUserList() {
			return userList;
		}
		public void setUserList(String userList) {
			this.userList = userList;
		}
		public String getProcInstId() {
			return procInstId;
		}
		public void setProcInstId(String procInstId) {
			this.procInstId = procInstId;
		}
		public String getReviewerID() {
			return reviewerID;
		}
		public void setReviewerID(String reviewerID) {
			this.reviewerID = reviewerID;
		}
		public String getReviewerName() {
			return reviewerName;
		}
		public void setReviewerName(String reviewerName) {
			this.reviewerName = reviewerName;
		}
		public String getReviewTime() {
			return reviewTime;
		}
		public void setReviewTime(String reviewTime) {
			this.reviewTime = reviewTime;
		}
		public String getReviewResult() {
			return reviewResult;
		}
		public void setReviewResult(String reviewResult) {
			this.reviewResult = reviewResult;
		}
		public String getReviewResultCN() {
			return reviewResultCN;
		}
		public void setReviewResultCN(String reviewResultCN) {
			this.reviewResultCN = reviewResultCN;
		}
		public String getDispatchTitle() {
			return dispatchTitle;
		}
		public void setDispatchTitle(String dispatchTitle) {
			this.dispatchTitle = dispatchTitle;
		}
		public String getDispatchNumber() {
			return dispatchNumber;
		}
		public void setDispatchNumber(String dispatchNumber) {
			this.dispatchNumber = dispatchNumber;
		}
		public Long getDispatchId() {
			return dispatchId;
		}
		public void setDispatchId(Long dispatchId) {
			this.dispatchId = dispatchId;
		}
		// 考核方式
		public String getAssessmentMethodName() {
			if (!StringUtils.isEmpty(this.assessmentMethod)) {
				Map<String, SysDictionaryVO> assessmentMethodMap = DictionaryUtil
						.getDictionaries("ASSESSMENT_METHODS");
				Map<String, String> assessmentMethodEnumMap = new HashMap<String, String>();
				for (String key : assessmentMethodMap.keySet()) {
					SysDictionaryVO sysDictionaryVO = assessmentMethodMap.get(key);
					assessmentMethodEnumMap.put(sysDictionaryVO.getCode(),
							sysDictionaryVO.getName());
				}
				return assessmentMethodEnumMap.get(this.assessmentMethod);
			}
			return null;
		}
}