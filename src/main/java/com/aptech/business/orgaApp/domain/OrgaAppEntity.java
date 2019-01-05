package com.aptech.business.orgaApp.domain;


import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 班组实体类
 *
 * @author 
 * @created 2017-09-14 09:37:02
 * @lastModified 
 * @history
 *
 */
@Alias("OrgaAppEntity")
public class OrgaAppEntity extends BaseEntity{
		private static final long serialVersionUID = 2268281730301294900L;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 名称
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
		 * 组织机构
		 */
    	private Integer organization;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 组长
		 */
    	private String teamLeader;
		/**
		 * 组员
		 */
    	private String teamMember;
		/**
		 * 删除标记
		 */
    	private String deleteFlag;

    	private String teamLeaderName;
    	
    	private String unitName;
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
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
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
		public Integer getOrganization(){
			return organization;
		}
		public void setOrganization(Integer organization){
			this.organization = organization;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getTeamLeader(){
			return teamLeader;
		}
		public void setTeamLeader(String teamLeader){
			this.teamLeader = teamLeader;
		}
		public String getTeamMember(){
			return teamMember;
		}
		public void setTeamMember(String teamMember){
			this.teamMember = teamMember;
		}
		public String getDeleteFlag(){
			return deleteFlag;
		}
		public void setDeleteFlag(String deleteFlag){
			this.deleteFlag = deleteFlag;
		}
		public String getTeamLeaderName() {
			return teamLeaderName;
		}
		public void setTeamLeaderName(String teamLeaderName) {
			this.teamLeaderName = teamLeaderName;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		
}