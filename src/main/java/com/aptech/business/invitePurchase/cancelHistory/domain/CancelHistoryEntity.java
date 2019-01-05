package com.aptech.business.invitePurchase.cancelHistory.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 撤回历史实体类
 *
 * @author 
 * @created 2018-09-10 13:41:56
 * @lastModified 
 * @history
 *
 */
@Alias("CancelHistoryEntity")
public class CancelHistoryEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 事项id
		 */
    	private String itemId;
		/**
		 * 撤回原因
		 */
    	private String cancelReason;
		/**
		 * 撤回时间
		 */
    	private Date cancelTime;
		/**
		 * 撤回操作人员id
		 */
    	private String cancelUserId;
    	/**
    	 * 撤回操作人员中文名称
    	 */
    	private String userName;
		/**
		 * 事项类型
		 */
    	private String itemType;
    	/**
    	 * 撤回时间字符串格式
    	 */
    	private String cancelTimeStr;
    	/**
    	 * 事项时间（统一）
    	 */
    	private Date itemTime;
    	/**
    	 * 事项时间字符串格式（统一）
    	 */
    	private String itemTimeStr;
    	/**
    	 * 事项内容（统一）
    	 */
    	private String itemName;
    	/**
    	 * 备注（统一）
    	 */
    	private String remark;
    	/**
    	 * 采购方式中文名称（统一）
    	 */
    	private String purchaseModeName;
    	/**
    	 * 所属部门中文名称（统一）
    	 */
    	private String unitName;
    	/**
    	 * 立项单位中文名称（统一）
    	 */
    	private String departmentName;
    	/**
    	 * 上传附件（统一）
    	 */
    	private String file;
		/**
		 * 现估价（元）（立项批复）
		 */
    	private String judge;
    	/**
    	 * 供应商中文名称（合同审批）
    	 */
    	private String supplierName;
    	/**
    	 * 合同编号（合同审批）
    	 */
    	private String contractCode;
    	/**
    	 * 合同金额(元)（合同审批）
    	 */
    	private String money;
    	/**
    	 * 存档时间（合同签订）
    	 */
    	private Date createTime;
    	/**
    	 * 存档时间字符串格式（合同签订）
    	 */
    	private String createTimeStr;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getItemId(){
			return itemId;
		}
		public void setItemId(String itemId){
			this.itemId = itemId;
		}
		public String getCancelReason(){
			return cancelReason;
		}
		public void setCancelReason(String cancelReason){
			this.cancelReason = cancelReason;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCancelTime(){
			return cancelTime;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCancelTime(Date cancelTime){
			this.cancelTime = cancelTime;
		}
		public String getCancelUserId(){
			return cancelUserId;
		}
		public void setCancelUserId(String cancelUserId){
			this.cancelUserId = cancelUserId;
		}
		public String getItemType(){
			return itemType;
		}
		public void setItemType(String itemType){
			this.itemType = itemType;
		}
		public String getCancelTimeStr() {
			if (cancelTimeStr !=null && cancelTimeStr !="") {
				return cancelTimeStr;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
			if (this.cancelTime==null) {
				return cancelTimeStr;
			}
			return df.format(this.cancelTime);
		}
		public void setCancelTimeStr(String cancelTimeStr) {
			this.cancelTimeStr = cancelTimeStr;
		}
		public Date getItemTime() {
			return itemTime;
		}
		public void setItemTime(Date itemTime) {
			this.itemTime = itemTime;
		}
		public String getItemTimeStr() {
			if (itemTimeStr !=null && itemTimeStr !="") {
				return itemTimeStr;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if (this.itemTime==null) {
				return itemTimeStr;
			}
			return df.format(this.itemTime);
		}
		public void setItemTimeStr(String itemTimeStr) {
			this.itemTimeStr = itemTimeStr;
		}
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getPurchaseModeName() {
			return purchaseModeName;
		}
		public void setPurchaseModeName(String purchaseModeName) {
			this.purchaseModeName = purchaseModeName;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getDepartmentName() {
			return departmentName;
		}
		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}
		public String getFile() {
			return file;
		}
		public void setFile(String file) {
			this.file = file;
		}
		public String getJudge() {
			return judge;
		}
		public void setJudge(String judge) {
			this.judge = judge;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getSupplierName() {
			return supplierName;
		}
		public void setSupplierName(String supplierName) {
			this.supplierName = supplierName;
		}
		public String getContractCode() {
			return contractCode;
		}
		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
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
}