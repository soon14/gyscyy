package com.aptech.business.dutyOrder.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 值次实体类
 *
 * @author 
 * @created 2017-09-13 17:23:46
 * @lastModified 
 * @history
 *
 */
@Alias("DutyOrderEntity")
public class DutyOrderEntity extends BaseEntity{
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
		 * 组织机构
		 */
    	private Long organization;
		/**
		 * 组长
		 */
    	private String teamLeader;
		/**
		 * 组员
		 */
    	private String teamMember;
		/**
		 * 备注
		 */
    	private String remark;
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
		public Long getOrganization(){
			return organization;
		}
		public void setOrganization(Long organization){
			this.organization = organization;
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