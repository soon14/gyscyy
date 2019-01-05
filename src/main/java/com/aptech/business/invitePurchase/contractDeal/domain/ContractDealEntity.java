package com.aptech.business.invitePurchase.contractDeal.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 合同签订实体类
 *
 * @author 
 * @created 2018-09-12 10:01:29
 * @lastModified 
 * @history
 *
 */
@Alias("ContractDealEntity")
public class ContractDealEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 所属部门id
		 */
    	private String unitId;
		/**
		 * 合同编号
		 */
    	private String contractCode;
		/**
		 * 签订合同名称
		 */
    	private String name;
		/**
		 * 供应商id
		 */
    	private String supplierId;
		/**
		 * 签约时间
		 */
    	private Date dealTime;
		/**
		 * 存档时间
		 */
    	private Date createTime;
		/**
		 * 删除状态，0删除，1有效
		 */
    	private String status;
		/**
		 * 上传文件
		 */
    	private String file;
		/**
		 * 合同台帐id
		 */
    	private String contractManageId;
    	/**
    	 * 所属部门中文名称
    	 */
    	private String unitName;
    	/**
    	 * 供应商中文名称
    	 */
    	private String supplierName;
    	/**
		 * 签约时间字符串格式
		 */
    	private String dealTimeStr;
		/**
		 * 存档时间字符串格式
		 */
    	private String createTimeStr;
    	/**
    	 * 创建人id
    	 */
    	private String createId;
    	//合同年限
    	private int contractLife;
    	//履行开始时间
    	private Date startDate;
    	//履行结束时间
    	private String endDate;
    	
    	
    	
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public int getContractLife() {
			return contractLife;
		}
		public void setContractLife(int contractLife) {
			this.contractLife = contractLife;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getStartDate() {
			return startDate;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getContractCode(){
			return contractCode;
		}
		public void setContractCode(String contractCode){
			this.contractCode = contractCode;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getSupplierId(){
			return supplierId;
		}
		public void setSupplierId(String supplierId){
			this.supplierId = supplierId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getDealTime(){
			return dealTime;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setDealTime(Date dealTime){
			this.dealTime = dealTime;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getCreateTime(){
			return createTime;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setCreateTime(Date createTime){
			this.createTime = createTime;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
		public String getFile(){
			return file;
		}
		public void setFile(String file){
			this.file = file;
		}
		public String getContractManageId(){
			return contractManageId;
		}
		public void setContractManageId(String contractManageId){
			this.contractManageId = contractManageId;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getSupplierName() {
			return supplierName;
		}
		public void setSupplierName(String supplierName) {
			this.supplierName = supplierName;
		}
		public String getDealTimeStr() {
			if (dealTimeStr !=null && dealTimeStr !="") {
				return dealTimeStr;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if (this.dealTime==null) {
				return dealTimeStr;
			}
			return df.format(this.dealTime);
		}
		public void setDealTimeStr(String dealTimeStr) {
			this.dealTimeStr = dealTimeStr;
		}
		public String getCreateTimeStr() {
			if (createTimeStr !=null && createTimeStr !="") {
				return createTimeStr;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if (this.createTime==null) {
				return createTimeStr;
			}
			return df.format(this.createTime);
		}
		public void setCreateTimeStr(String createTimeStr) {
			this.createTimeStr = createTimeStr;
		}
		public String getCreateId() {
			return createId;
		}
		public void setCreateId(String createId) {
			this.createId = createId;
		}
}