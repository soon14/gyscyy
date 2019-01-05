package com.aptech.business.overhaul.overhaulPlanMember.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 检修计划人员实体类
 *
 * @author 
 * @created 2018-03-22 10:43:15
 * @lastModified 
 * @history
 *
 */
@Alias("OverhaulPlanMemberEntity")
public class OverhaulPlanMemberEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 检修人名称
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
		 * 检修人
		 */
    	private String createUserId;
		/**
		 * 修改人
		 */
    	private String updateUserId;
		/**
		 * 检修项目Id
		 */
    	private Long overhaulPlanId;

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
		public String getUpdateUserId(){
			return updateUserId;
		}
		public void setUpdateUserId(String updateUserId){
			this.updateUserId = updateUserId;
		}
		public Long getOverhaulPlanId(){
			return overhaulPlanId;
		}
		public void setOverhaulPlanId(Long overhaulPlanId){
			this.overhaulPlanId = overhaulPlanId;
		}
}