package com.aptech.business.cargo.securityApparatus.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 安全工器具管理实体类
 *
 * @author 
 * @created 2018-03-15 13:41:51
 * @lastModified 
 * @history
 *
 */
@Alias("SecurityApparatusEntity")
public class SecurityApparatusEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 工具名称
		 */
    	private String name;
		/**
		 * 编码
		 */
    	private String code;
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
		 * 创建人名
		 */
    	private String createUser;
		/**
		 * 修改人
		 */
    	private String updateUserId;
		/**
		 * 所属单位
		 */
    	private String unitId;
		/**
		 * 所属单位名字
		 */
    	private String unitName;
		/**
		 * 检验日期
		 */
    	private Date checkDate;
		/**
		 * 到期日期
		 */
    	private Date endDate;
		/**
		 * 附件
		 */
    	private String attachment;
		/**
		 * 备注
		 */
    	private String remark;
    	/**
		 * 检验日期string
		 */
    	//private String checkDateString;
    	/**
		 * 到期日期string
		 */
    	//private String endDateString;
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
		public String getUpdateUserId(){
			return updateUserId;
		}
		public void setUpdateUserId(String updateUserId){
			this.updateUserId = updateUserId;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getCheckDate(){
			return checkDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCheckDate(Date checkDate){
			this.checkDate = checkDate;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getEndDate(){
			return endDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setEndDate(Date endDate){
			this.endDate = endDate;
		}
		public String getAttachment(){
			return attachment;
		}
		public void setAttachment(String attachment){
			this.attachment = attachment;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getShowCheckDate() {
			if (checkDate != null) {
				DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd");
				return dfu.format(this.checkDate);
			}
			return "";
		}
		public String getShowEndDate() {
			if (endDate != null) {
				DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd");
				return dfu.format(this.endDate);
			}
			return "";
		}
		public String getShowCreateDate(){
			if (createDate != null) {
				DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd");
				return dfu.format(this.createDate);
			}
			return "";
		}
		public String getCreateUser() {
			return createUser;
		}
		public void setCreateUser(String createUser) {
			this.createUser = createUser;
		}
}