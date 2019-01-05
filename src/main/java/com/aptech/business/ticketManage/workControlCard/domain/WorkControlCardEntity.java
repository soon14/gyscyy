package com.aptech.business.ticketManage.workControlCard.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 工作票危险因素控制卡实体类
 *
 * @author 
 * @created 2017-06-05 17:12:09
 * @lastModified 
 * @history
 *
 */
@Alias("workControlCardEntity")
public class WorkControlCardEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 主票据信息的id
		 */
    	private Long workticketId;
		/**
		 * 补充说明
		 */
    	private String cardSortDescription;
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
    	private String createUserId;
		/**
		 * 作业类别
		 */
    	private String cardSort;
    	/**
		 * 作业类别
		 */
    	private String cardSortTwo;
    	/**
		 * 作业类别
		 */
    	private String cardSortThree;
    	/**
		 * 作业类别
		 */
    	private String cardSortFour;

		public String getCardSortTwo() {
			return cardSortTwo;
		}
		public void setCardSortTwo(String cardSortTwo) {
			this.cardSortTwo = cardSortTwo;
		}
		public String getCardSortThree() {
			return cardSortThree;
		}
		public void setCardSortThree(String cardSortThree) {
			this.cardSortThree = cardSortThree;
		}
		public String getCardSortFour() {
			return cardSortFour;
		}
		public void setCardSortFour(String cardSortFour) {
			this.cardSortFour = cardSortFour;
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
		public String getCardSortDescription(){
			return cardSortDescription;
		}
		public void setCardSortDescription(String cardSortDescription){
			this.cardSortDescription = cardSortDescription;
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
		public String getCardSort(){
			return cardSort;
		}
		public void setCardSort(String cardSort){
			this.cardSort = cardSort;
		}
}