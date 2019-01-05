package com.aptech.business.ticketManage.workSituation.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 开工和收工情况实体类
 *
 * @author 
 * @created 2017-06-05 17:12:39
 * @lastModified 
 * @history
 *
 */
@Alias("workSituationEntity")
public class WorkSituationEntity extends BaseEntity{
		/**
		 * 开工时间
		 */
    	private Date startDate;
		/**
		 * 工作负责人2ID
		 */
    	private Long endPicId;
		/**
		 * 工作许可人2名字
		 */
    	private String endAllowName;
		/**
		 * 收工时间
		 */
    	private Date endDate;
		/**
		 * 工作许可人名字
		 */
    	private String startAllowName;
		/**
		 * 修改时间
		 */
    	private Date updateDate;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 工作票表的id
		 */
    	private Long workticketId;
		/**
		 * 工作负责人名字
		 */
    	private String startPicName;
		/**
		 * 工作许可人2ID
		 */
    	private Long endAllowId;
		/**
		 * 工作负责人2名字
		 */
    	private String endPicName;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 工作许可人ID
		 */
    	private Long startAllowId;
		/**
		 * 工作负责人ID
		 */
    	private Long startPicId;
		/**
		 * 创建人
		 */
    	private String createUserId;

	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getStartDate(){
			return startDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setStartDate(Date startDate){
			this.startDate = startDate;
		}
		public Long getEndPicId(){
			return endPicId;
		}
		public void setEndPicId(Long endPicId){
			this.endPicId = endPicId;
		}
		public String getEndAllowName(){
			return endAllowName;
		}
		public void setEndAllowName(String endAllowName){
			this.endAllowName = endAllowName;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getEndDate(){
			return endDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setEndDate(Date endDate){
			this.endDate = endDate;
		}
		public String getStartAllowName(){
			return startAllowName;
		}
		public void setStartAllowName(String startAllowName){
			this.startAllowName = startAllowName;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public Long getWorkticketId(){
			return workticketId;
		}
		public void setWorkticketId(Long workticketId){
			this.workticketId = workticketId;
		}
		public String getStartPicName(){
			return startPicName;
		}
		public void setStartPicName(String startPicName){
			this.startPicName = startPicName;
		}
		public Long getEndAllowId(){
			return endAllowId;
		}
		public void setEndAllowId(Long endAllowId){
			this.endAllowId = endAllowId;
		}
		public String getEndPicName(){
			return endPicName;
		}
		public void setEndPicName(String endPicName){
			this.endPicName = endPicName;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public Long getStartAllowId(){
			return startAllowId;
		}
		public void setStartAllowId(Long startAllowId){
			this.startAllowId = startAllowId;
		}
		public Long getStartPicId(){
			return startPicId;
		}
		public void setStartPicId(Long startPicId){
			this.startPicId = startPicId;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
}