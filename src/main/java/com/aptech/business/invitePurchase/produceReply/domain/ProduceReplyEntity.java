package com.aptech.business.invitePurchase.produceReply.domain;

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
 * 立项批复实体类
 *
 * @author 
 * @created 2018-09-07 14:52:40
 * @lastModified 
 * @history
 *
 */
@Alias("ProduceReplyEntity")
public class ProduceReplyEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 所属部门id
		 */
    	private String unitId;
		/**
		 * 采购事项
		 */
    	private String name;
		/**
		 * 立项单位id
		 */
    	private String departmentId;
		/**
		 * 现估价（元）
		 */
    	private String judge;
		/**
		 * 采购方式id
		 */
    	private String purchaseModeId;
		/**
		 * 上传时间
		 */
    	private Date updateDate;
		/**
		 * 备注
		 */
    	private String remark;
    	/**
    	 * 上传时间字符串格式
    	 */
    	private String updateDateStr;
    	/**
    	 * 采购方式中文名称
    	 */
    	private String purchaseModeName;
    	/**
    	 * 所属部门中文名称
    	 */
    	private String unitName;
    	/**
    	 * 立项单位中文名称
    	 */
    	private String departmentName;
    	/**
    	 * 删除标识，0删除，1有效
    	 */
    	private String status;
    	/**
    	 * 上传附件
    	 */
    	private String file;
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
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getDepartmentId(){
			return departmentId;
		}
		public void setDepartmentId(String departmentId){
			this.departmentId = departmentId;
		}
		public String getJudge(){
			return judge;
		}
		public void setJudge(String judge){
			this.judge = judge;
		}
		public String getPurchaseModeId(){
			return purchaseModeId;
		}
		public void setPurchaseModeId(String purchaseModeId){
			this.purchaseModeId = purchaseModeId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
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
		public String getUpdateDateStr() {
			if (updateDateStr !=null && updateDateStr !="") {
				return updateDateStr;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if (this.updateDate==null) {
				return updateDateStr;
			}
			return df.format(this.updateDate);
		}
		public void setUpdateDateStr(String updateDateStr) {
			this.updateDateStr = updateDateStr;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getFile() {
			return file;
		}
		public void setFile(String file) {
			this.file = file;
		}
		public String getCreateId() {
			return createId;
		}
		public void setCreateId(String createId) {
			this.createId = createId;
		}
}