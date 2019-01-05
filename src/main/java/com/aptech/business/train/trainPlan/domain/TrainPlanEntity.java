package com.aptech.business.train.trainPlan.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.TrainPlanClassifyEnum;
import com.aptech.business.component.dictionary.TrainPlanRangeEnum;
import com.aptech.business.component.dictionary.TrainPlanStatusEnum;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 培训计划实体类
 *
 * @author ly
 * @created 2018-03-19 10:04:50
 * @lastModified 
 * @history
 *
 */
@Alias("TrainPlanEntity")
public class TrainPlanEntity extends BaseEntity{
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
    	 * 培训状态，1已完成，0未完成
    	 */
    	private String status;
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
				Map<String, String> statusEnumMap = new HashMap<String, String>();
				for(TrainPlanStatusEnum key : TrainPlanStatusEnum.values()){
					statusEnumMap.put(key.getCode(), key.getName());
				}
				return statusEnumMap.get(this.status);
			}
			return null;
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
		
}