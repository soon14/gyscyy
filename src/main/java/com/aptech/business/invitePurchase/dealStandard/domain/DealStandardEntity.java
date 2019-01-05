package com.aptech.business.invitePurchase.dealStandard.domain;

import java.util.Date;

import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;

/**
 * 
 * 定标请示函实体类
 *
 * @author 
 * @created 2018-09-10 09:10:10
 * @lastModified 
 * @history
 *
 */
@Alias("DealStandardEntity")
public class DealStandardEntity extends BaseEntity{
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
		 * 请示批复时间
		 */
    	private Date replyTime;
		/**
		 * 备注
		 */
    	private String remark;
    	/**
    	 * 请示批复时间字符串格式
    	 */
    	private String replyTimeStr;
    	/**
    	 * 所属部门中文名称
    	 */
    	private String unitName;
    	/**
    	 * 立项单位中文名称
    	 */
    	private String departmentName;
    	/**
    	 * 删除标识，0已删除，1有效
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
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getReplyTime(){
			return replyTime;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setReplyTime(Date replyTime){
			this.replyTime = replyTime;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getReplyTimeStr() {
			if (replyTimeStr !=null && replyTimeStr !="") {
				return replyTimeStr;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			if (this.replyTime==null) {
				return replyTimeStr;
			}
			return df.format(this.replyTime);
		}
		public void setReplyTimeStr(String replyTimeStr) {
			this.replyTimeStr = replyTimeStr;
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