package com.aptech.business.contractManage.domain;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 合同管理实体类
 *
 * @author 
 * @created 2018-04-17 14:03:40
 * @lastModified 
 * @history
 *
 */
@Alias("ContractManageEntity")
public class ContractManageEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

		private String attachment;
	
		private String contractCode;//暂时不用
		
		private String contractName;
		
		private String contractXdname;
		
		private String zbfs;
		
		private Date qdrq;
		
		private String contractMoney;
		
		private String yjsmoney;
		
		private Date endDate;
		
		private String remark;
		
		private String status;//基本状态
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 修改人
		 */
    	private String updateUserId;
		/**
		 * 修改时间
		 */
    	private Date updateDate;
    	
    	private String qdrqString;
    	
    	private String endDateString;
    	
    	private String zbfsName;
    	
    	private String yjsmoneyName;
    	
    	private String remarkName;
    	//合同年限
    	private int contractLife;
    	//履行开始时间
    	private Date contractStartDate;
    	//履行结束时间
    	private String contractEndDate;
    	//是否可以驳回
    	private String isnotReject;
    	
    	
    	
		public String getIsnotReject() {
			return isnotReject;
		}
		public void setIsnotReject(String isnotReject) {
			this.isnotReject = isnotReject;
		}
		public String getContractEndDate() {
			return contractEndDate;
		}
		public void setContractEndDate(String contractEndDate) {
			this.contractEndDate = contractEndDate;
		}
		public int getContractLife() {
			return contractLife;
		}
		public void setContractLife(int contractLife) {
			this.contractLife = contractLife;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getContractStartDate() {
			return contractStartDate;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setContractStartDate(Date contractStartDate) {
			this.contractStartDate = contractStartDate;
		}
		public String getAttachment() {
			return attachment;
		}
		public void setAttachment(String attachment) {
			this.attachment = attachment;
		}
		public String getContractCode() {
			return contractCode;
		}
		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}
		
		public String getContractName() {
			return contractName;
		}
		public void setContractName(String contractName) {
			this.contractName = contractName;
		}
		public String getContractXdname() {
			return contractXdname;
		}
		public void setContractXdname(String contractXdname) {
			this.contractXdname = contractXdname;
		}
		public String getZbfs() {
			return zbfs;
		}
		public void setZbfs(String zbfs) {
			this.zbfs = zbfs;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getQdrq() {
			return qdrq;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setQdrq(Date qdrq) {
			this.qdrq = qdrq;
		}
		public String getContractMoney() {
			return contractMoney;
		}
		public void setContractMoney(String contractMoney) {
			this.contractMoney = contractMoney;
		}
		public String getYjsmoney() {
			
			return yjsmoney;
		}
		public void setYjsmoney(String yjsmoney) {
			this.yjsmoney = yjsmoney;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getEndDate() {
			return endDate;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getCreateUserId() {
			return createUserId;
		}
		
		public void setCreateUserId(String createUserId) {
			this.createUserId = createUserId;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate() {
			return createDate;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		public String getUpdateUserId() {
			return updateUserId;
		}
		public void setUpdateUserId(String updateUserId) {
			this.updateUserId = updateUserId;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getUpdateDate() {
			return updateDate;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setUpdateDate(Date updateDate) {
			this.updateDate = updateDate;
		}
		public String getQdrqString() {
			return qdrqString;
		}
		public void setQdrqString(String qdrqString) {
			this.qdrqString = qdrqString;
		}
		public String getEndDateString() {
			return endDateString;
		}
		public void setEndDateString(String endDateString) {
			this.endDateString = endDateString;
		}
		public String getZbfsName() {
			Map<String, SysDictionaryVO> equipTypeMap = DictionaryUtil.getDictionaries("PURCHASE_MODE");
			for(String key :  equipTypeMap.keySet()){
	        	SysDictionaryVO sysDictionaryVO = equipTypeMap.get(key);
	        	if(zbfs!=null&&sysDictionaryVO.getCode().equals(zbfs.toString())){
	        		return sysDictionaryVO.getName();
	        	}
	        }	
			return "";
		}
		public void setZbfsName(String zbfsName) {
			this.zbfsName = zbfsName;
		}
		public String getYjsmoneyName() {
			return yjsmoneyName;
		}
		public void setYjsmoneyName(String yjsmoneyName) {
			this.yjsmoneyName = yjsmoneyName;
		}
		public String getRemarkName() {
			return remarkName;
		}
		public void setRemarkName(String remarkName) {
			this.remarkName = remarkName;
		}
		
    	
    	
}