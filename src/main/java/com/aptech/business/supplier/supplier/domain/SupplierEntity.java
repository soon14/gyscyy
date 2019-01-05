package com.aptech.business.supplier.supplier.domain;

import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 供应商管理实体类
 *
 * @author 
 * @created 2017-11-02 10:30:36
 * @lastModified 
 * @history
 *
 */
@Alias("SupplierEntity")
public class SupplierEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 供应商名称
		 */
    	private String supplierName;
		/**
		 * 供应商类型
		 */
    	private String supplierType;
    	private String supplierTypeName;
		/**
		 * 公司电话
		 */
    	private String companyTel;
		public String getSupplierTypeName() {
			return supplierTypeName;
		}
		public void setSupplierTypeName(String supplierTypeName) {
			this.supplierTypeName = supplierTypeName;
		}
		/**
		 * 公司传真
		 */
    	private String companyFax;
		/**
		 * 公司网址
		 */
    	private String companyWebsite;
		/**
		 * 公司邮箱
		 */
    	private String companyEmail;
		/**
		 * 地址
		 */
    	private String address;
		/**
		 * 删除标记
		 */
    	private String deleteFlag;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getSupplierName(){
			return supplierName;
		}
		public void setSupplierName(String supplierName){
			this.supplierName = supplierName;
		}
		public String getSupplierType(){
			return supplierType;
		}
		public void setSupplierType(String supplierType){
			this.supplierType = supplierType;
		}
		public String getCompanyTel(){
			return companyTel;
		}
		public void setCompanyTel(String companyTel){
			this.companyTel = companyTel;
		}
		public String getCompanyFax(){
			return companyFax;
		}
		public void setCompanyFax(String companyFax){
			this.companyFax = companyFax;
		}
		public String getCompanyWebsite(){
			return companyWebsite;
		}
		public void setCompanyWebsite(String companyWebsite){
			this.companyWebsite = companyWebsite;
		}
		public String getCompanyEmail(){
			return companyEmail;
		}
		public void setCompanyEmail(String companyEmail){
			this.companyEmail = companyEmail;
		}
		public String getAddress(){
			return address;
		}
		public void setAddress(String address){
			this.address = address;
		}
		public String getDeleteFlag(){
			return deleteFlag;
		}
		public void setDeleteFlag(String deleteFlag){
			this.deleteFlag = deleteFlag;
		}
}