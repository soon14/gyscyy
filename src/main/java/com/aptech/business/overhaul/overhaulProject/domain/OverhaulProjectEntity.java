package com.aptech.business.overhaul.overhaulProject.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 检修项目实体类
 *
 * @author 
 * @created 2017-06-12 18:48:28
 * @lastModified 
 * @history
 *
 */
@Alias("overhaulProjectEntity")
public class OverhaulProjectEntity extends BaseEntity{
		/**
		 * 开工时间
		 */
    	private Date startDate;
		/**
		 * 方案措施
		 */
    	private String measure;
		/**
		 * 删除状态
		 */
    	private String status;
		/**
		 * 项目明细
		 */
    	private String projecDetail;
		/**
		 * 检修计划ID
		 */
    	private Long overhualPlanId;
		/**
		 * 完成时间
		 */
    	private Date endDate;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 列入原因
		 */
    	private String projectReason;
		/**
		 * 名称
		 */
    	private String name;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 项目名称
		 */
    	private String projectName;
		/**
		 * 计划总金额
		 */
    	private String totalMoney;
    	private String startDateString;
    	private String endDateString;

	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getStartDate(){
			return startDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setStartDate(Date startDate){
			this.startDate = startDate;
		}
		public String getMeasure(){
			return measure;
		}
		public void setMeasure(String measure){
			this.measure = measure;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public String getProjecDetail(){
			return projecDetail;
		}
		public void setProjecDetail(String projecDetail){
			this.projecDetail = projecDetail;
		}
		public Long getOverhualPlanId(){
			return overhualPlanId;
		}
		public void setOverhualPlanId(Long overhualPlanId){
			this.overhualPlanId = overhualPlanId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getEndDate(){
			return endDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setEndDate(Date endDate){
			this.endDate = endDate;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getProjectReason(){
			return projectReason;
		}
		public void setProjectReason(String projectReason){
			this.projectReason = projectReason;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getProjectName(){
			return projectName;
		}
		public void setProjectName(String projectName){
			this.projectName = projectName;
		}
		public String getTotalMoney(){
			return totalMoney;
		}
		public void setTotalMoney(String totalMoney){
			this.totalMoney = totalMoney;
		}
		public String getStartDateString() {
		
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			
			if(this.startDate ==null){
				return startDateString;
			}
			return df.format(this.startDate);
		}
		public void setStartDateString(String startDateString) {
			this.startDateString = startDateString;
		}
		public String getEndDateString() {
		
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.endDate ==null){
				return endDateString;
			}
			return df.format(this.endDate);
		}
		public void setEndDateString(String endDateString) {
			this.endDateString = endDateString;
		}
		
		
}