package com.aptech.business.equip.equipAbnormalEquipRel.domain;

import java.util.Date;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 设备异动设备关联表实体类
 *
 * @author 
 * @created 2018-09-10 17:38:22
 * @lastModified 
 * @history
 *
 */
@Alias("EquipAbnormalEquipRelEntity")
public class EquipAbnormalEquipRelEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 编码
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
		 * 修改人
		 */
    	private String updateUserId;
		/**
		 * 修改时间
		 */
    	private Date updateDate;
    	/**
    	 * 设备编码
    	 */
    	private String equipCode;
    	/**
    	 * 设备名称
    	 */
    	private String equipName;
    	/**
    	 * 设备异动申请ID
    	 */
    	private Long equipAbnormalId;
    	/**
    	 * 设备异动报告ID
    	 */
    	private Long equipAbnormalReportId;
    	/**
    	 * 设备评级ID
    	 */
    	private Long equipAppraiseId;
		/**
		 * 是否删除
		 */
    	private int status;
    	public Long getEquipAppraiseId() {
			return equipAppraiseId;
		}
		public void setEquipAppraiseId(Long equipAppraiseId) {
			this.equipAppraiseId = equipAppraiseId;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public Long getEquipAbnormalReportId() {
			return equipAbnormalReportId;
		}
		public void setEquipAbnormalReportId(Long equipAbnormalReportId) {
			this.equipAbnormalReportId = equipAbnormalReportId;
		}
		public Long getEquipAbnormalId() {
			return equipAbnormalId;
		}
		public void setEquipAbnormalId(Long equipAbnormalId) {
			this.equipAbnormalId = equipAbnormalId;
		}
		public String getEquipCode() {
			return equipCode;
		}
		public void setEquipCode(String equipCode) {
			this.equipCode = equipCode;
		}
		public String getEquipName() {
			return equipName;
		}
		public void setEquipName(String equipName) {
			this.equipName = equipName;
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
		public String getUpdateUserId(){
			return updateUserId;
		}
		public void setUpdateUserId(String updateUserId){
			this.updateUserId = updateUserId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
}