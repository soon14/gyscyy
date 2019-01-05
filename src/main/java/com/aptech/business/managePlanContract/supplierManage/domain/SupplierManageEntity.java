package com.aptech.business.managePlanContract.supplierManage.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aptech.business.component.dictionary.EducationCategoryTypeEnum;
import com.aptech.business.component.dictionary.SupplierStatusTypeEnum;
import com.aptech.business.component.dictionary.SupplierTypeEnum;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 供应商管理实体类
 *
 * @author 
 * @created 2018-07-26 17:15:46
 * @lastModified 
 * @history
 *
 */
@Alias("SupplierManageEntity")
public class SupplierManageEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 营业执照号码
		 */
    	private String code;
		/**
		 * 名称
		 */
    	private String name;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 资质等级
		 */
    	private String level;
		/**
		 * 法人代表
		 */
    	private String delegateUserId;
		/**
		 * 从业范围
		 */
    	private String workRange;
		/**
		 * 主要从业范围
		 */
    	private String mainWorkRange;
		/**
		 * 公司地址
		 */
    	private String address;
		/**
		 * 公司电话
		 */
    	private String companyPhone;
		/**
		 * 联系人
		 */
    	private String userId;
		/**
		 * 联系电话
		 */
    	private String phone;
		/**
		 * 联系人电子邮箱
		 */
    	private String email;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 单位
		 */
    	private String unitId;
		/**
		 * 时间
		 */
    	private Date year;
    	
    	/**
    	 * 厅启用状态
    	 */
    	private String status;
    	
    	private String type;//类型
    	
    	private String typeName;

    	
    	public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getStatusName(){
			if (this.status!=null) {
				Map<String, String> statusEnumMap = new HashMap<String, String>();
				for(SupplierStatusTypeEnum key : SupplierStatusTypeEnum.values()){
					statusEnumMap.put(key.getCode(), key.getName());
				}
				return statusEnumMap.get(this.status);
			}
			return null;
		}
    	
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getLevel(){
			return level;
		}
		public void setLevel(String level){
			this.level = level;
		}
		public String getDelegateUserId(){
			return delegateUserId;
		}
		public void setDelegateUserId(String delegateUserId){
			this.delegateUserId = delegateUserId;
		}
		public String getWorkRange(){
			return workRange;
		}
		public void setWorkRange(String workRange){
			this.workRange = workRange;
		}
		public String getMainWorkRange(){
			return mainWorkRange;
		}
		public void setMainWorkRange(String mainWorkRange){
			this.mainWorkRange = mainWorkRange;
		}
		public String getAddress(){
			return address;
		}
		public void setAddress(String address){
			this.address = address;
		}
		public String getCompanyPhone(){
			return companyPhone;
		}
		public void setCompanyPhone(String companyPhone){
			this.companyPhone = companyPhone;
		}
		public String getUserId(){
			return userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
		public String getPhone(){
			return phone;
		}
		public void setPhone(String phone){
			this.phone = phone;
		}
		public String getEmail(){
			return email;
		}
		public void setEmail(String email){
			this.email = email;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getYear(){
			return year;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setYear(Date year){
			this.year = year;
		}
}