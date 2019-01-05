package com.aptech.business.ticketManage.workSafe.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 安全措施实体类
 *
 * @author 
 * @created 2017-06-05 17:12:33
 * @lastModified 
 * @history
 *
 */
@Alias("workSafeEntity")
public class WorkSafeEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 工作票表的id
		 */
    	private Long workticketId;
		/**
		 * 安全措施类型
		 */
    	private Long safeType;
		/**
		 * 序号
		 */
    	private Long orderSeq;
		/**
		 * 已做的安全措施
		 */
    	private String licensorContent;
		/**
		 * 执行情况
		 */
    	private String executeSituation;
		/**
		 * 字典表的类型
		 */
    	private String codeType;
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
		 * 应做的安全措施
		 */
    	private String signerContent;
    	
    	private String uuidCode;
    	
    	/**
		 * 恢复情况
		 */
    	private String hfSituation;
    	
    	/**
		 * 恢复时间
		 */
    	private Date hfDate;
    	/**
		 * 执行时间
		 */
    	private Date executeDate;
    	
    	/**
    	 * 安全措施信息表Id
    	 */
    	private Long safeId;
    	

		public Long getSafeId() {
			return safeId;
		}
		public void setSafeId(Long safeId) {
			this.safeId = safeId;
		}
		public String getUuidCode() {
			return uuidCode;
		}
		public void setUuidCode(String uuidCode) {
			this.uuidCode = uuidCode;
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
		public Long getSafeType(){
			return safeType;
		}
		public void setSafeType(Long safeType){
			this.safeType = safeType;
		}
		public Long getOrderSeq(){
			return orderSeq;
		}
		public void setOrderSeq(Long orderSeq){
			this.orderSeq = orderSeq;
		}
		public String getLicensorContent(){
			return licensorContent;
		}
		public void setLicensorContent(String licensorContent){
			this.licensorContent = licensorContent;
		}
		public String getExecuteSituation(){
			return executeSituation;
		}
		public void setExecuteSituation(String executeSituation){
			this.executeSituation = executeSituation;
		}
		public String getCodeType(){
			return codeType;
		}
		public void setCodeType(String codeType){
			this.codeType = codeType;
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
	    
		public String getHfSituation() {
			return hfSituation;
		}
		public void setHfSituation(String hfSituation) {
			this.hfSituation = hfSituation;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getHfDate() {
			return hfDate;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setHfDate(Date hfDate) {
			this.hfDate = hfDate;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getExecuteDate() {
			return executeDate;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setExecuteDate(Date executeDate) {
			this.executeDate = executeDate;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
		public String getSignerContent(){
			return signerContent;
		}
		public void setSignerContent(String signerContent){
			this.signerContent = signerContent;
		}
}