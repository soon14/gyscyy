package com.aptech.business.teamMemApp.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 班次实体类
 *
 * @author 
 * @created 2017-09-13 17:15:08
 * @lastModified 
 * @history
 *
 */
@Alias("TeamMemAppEntity")
public class TeamMemAppEntity extends BaseEntity{
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
		 * 值长
		 */
    	private Integer teamLeaderId;
    	
    	private String teamLeaderName;
		/**
		 * 组员
		 */
    	private String teamMemberIds;
		/**
		 * 备注
		 */
    	private String remark;
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
		public Integer getTeamLeaderId(){
			return teamLeaderId;
		}
		public void setTeamLeaderId(Integer teamLeaderId){
			this.teamLeaderId = teamLeaderId;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getDeleteFlag(){
			return deleteFlag;
		}
		public void setDeleteFlag(String deleteFlag){
			this.deleteFlag = deleteFlag;
		}
		public String getTeamMemberIds() {
			return teamMemberIds;
		}
		public void setTeamMemberIds(String teamMemberIds) {
			this.teamMemberIds = teamMemberIds;
		}
		public String getTeamLeaderName() {
			return teamLeaderName;
		}
		public void setTeamLeaderName(String teamLeaderName) {
			this.teamLeaderName = teamLeaderName;
		}
		
}