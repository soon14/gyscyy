package com.aptech.business.invitePurchase.contractApprove.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 合同审批实体类
 *
 * @author 
 * @created 2018-09-11 15:13:24
 * @lastModified 
 * @history
 *
 */
@Alias("ContractApproveEntity")
public class ContractApproveEntity extends BaseEntity{
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
		 * 合同名称
		 */
    	private String name;
		/**
		 * 供应商id
		 */
    	private String supplierId;
		/**
		 * 合同金额（元）
		 */
    	private String money;
		/**
		 * 采购方式id
		 */
    	private String purchaseModeId;
		/**
		 * 删除标识，0删除，1有效
		 */
    	private String status;
		/**
		 * 上传文件
		 */
    	private String file;
    	/**
    	 * 采购方式中文名称
    	 */
    	private String purchaseModeName;
    	/**
    	 * 所属部门中文名称
    	 */
    	private String unitName;
    	/**
    	 * 供应商中文名称
    	 */
    	private String supplierName;
    	/**
    	 * 创建人id
    	 */
    	private String createId;

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
		public String getMoney(){
			return money;
		}
		public void setMoney(String money){
			this.money = money;
		}
		public String getPurchaseModeId(){
			return purchaseModeId;
		}
		public void setPurchaseModeId(String purchaseModeId){
			this.purchaseModeId = purchaseModeId;
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
		public String getSupplierName() {
			return supplierName;
		}
		public void setSupplierName(String supplierName) {
			this.supplierName = supplierName;
		}
		public String getCreateId() {
			return createId;
		}
		public void setCreateId(String createId) {
			this.createId = createId;
		}
}