package com.aptech.business.overhaul.overhaulWork.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 检修工作实体类
 *
 * @author 
 * @created 2017-08-11 09:27:00
 * @lastModified 
 * @history
 *
 */
@Alias("OverhaulWorkEntity")
public class OverhaulWorkEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 检修日志ID
		 */
    	private Long overhaul;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 工作内容
		 */
    	private String work;
		/**
		 * 负责人名
		 */
    	private String dutyUserName;
		/**
		 * 负责人id
		 */
    	private Long dutyUserId;
		/**
		 * 检修人姓名
		 */
    	private String checkUserName;
		/**
		 * 检修人Id
		 */
    	private Long checkUserId;
		/**
		 * 删除状态
		 */
    	private String status;

		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public Long getOverhaul(){
			return overhaul;
		}
		public void setOverhaul(Long overhaul){
			this.overhaul = overhaul;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getWork(){
			return work;
		}
		public void setWork(String work){
			this.work = work;
		}
		public String getDutyUserName(){
			return dutyUserName;
		}
		public void setDutyUserName(String dutyUserName){
			this.dutyUserName = dutyUserName;
		}
		public Long getDutyUserId(){
			return dutyUserId;
		}
		public void setDutyUserId(Long dutyUserId){
			this.dutyUserId = dutyUserId;
		}
		public String getCheckUserName(){
			return checkUserName;
		}
		public void setCheckUserName(String checkUserName){
			this.checkUserName = checkUserName;
		}
		public Long getCheckUserId(){
			return checkUserId;
		}
		public void setCheckUserId(Long checkUserId){
			this.checkUserId = checkUserId;
		}
		public String getStatus(){
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
}