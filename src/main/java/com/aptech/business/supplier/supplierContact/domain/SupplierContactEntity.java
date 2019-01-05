package com.aptech.business.supplier.supplierContact.domain;

import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 供应商联系人关联表实体类
 *
 * @author 
 * @created 2017-11-02 14:14:40
 * @lastModified 
 * @history
 *
 */
@Alias("SupplierContactEntity")
public class SupplierContactEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 供应商管理表id
		 */
    	private Long supplierId;
		/**
		 * 姓名
		 */
    	private String name;
		/**
		 * 性别
		 */
    	private String sex;
		/**
		 * 部门
		 */
    	private String department;
		/**
		 * 职务
		 */
    	private String duty;
		/**
		 * 负责业务
		 */
    	private String business;
		/**
		 * 电话
		 */
    	private String telephone;
		/**
		 * 手机
		 */
    	private String mobilePhone;
		/**
		 * 邮箱
		 */
    	private String email;
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
		public Long getSupplierId(){
			return supplierId;
		}
		public void setSupplierId(Long supplierId){
			this.supplierId = supplierId;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getSex(){
			return sex;
		}
		public void setSex(String sex){
			this.sex = sex;
		}
		public String getDepartment(){
			return department;
		}
		public void setDepartment(String department){
			this.department = department;
		}
		public String getDuty(){
			return duty;
		}
		public void setDuty(String duty){
			this.duty = duty;
		}
		public String getBusiness(){
			return business;
		}
		public void setBusiness(String business){
			this.business = business;
		}
		public String getTelephone(){
			return telephone;
		}
		public void setTelephone(String telephone){
			this.telephone = telephone;
		}
		public String getMobilePhone(){
			return mobilePhone;
		}
		public void setMobilePhone(String mobilePhone){
			this.mobilePhone = mobilePhone;
		}
		public String getEmail(){
			return email;
		}
		public void setEmail(String email){
			this.email = email;
		}
		public String getDeleteFlag(){
			return deleteFlag;
		}
		public void setDeleteFlag(String deleteFlag){
			this.deleteFlag = deleteFlag;
		}
}