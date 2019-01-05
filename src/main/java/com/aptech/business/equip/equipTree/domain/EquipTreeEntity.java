package com.aptech.business.equip.equipTree.domain;

import java.util.Date;
import org.apache.ibatis.type.Alias;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 设备节点表实体类
 *
 * @author 
 * @created 2017-06-26 15:26:43
 * @lastModified 
 * @history
 *
 */
@Alias("EquipTreeEntity")
public class EquipTreeEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = -4754903905411711472L;
		/**
		 * 设备名称
		 */
		private String name;
		/**
		 * 父结点
		 */
    	private Long parentId;
		/**
		 * 节点类型
		 */
    	private int treeType;
		/**
		 * 设备
		 */
    	private Long equipId;
		/**
		 * 创建人
		 */
    	private Long createUserId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 删除人
		 */
    	private Long updateUserId;
    	/**
    	 * 删除时间
    	 */
    	private Date updateDate;
    	/**
    	 * 组织机构ID
    	 */
    	private Long unitId ;
		/**
    	 * 编码
    	 */
    	private String code ;
    	/**
    	 * 备注
    	 */
    	private String remark ;
    	/**
    	 * 删除标志
    	 */
    	private int status ;
    	/**
    	 * 组织级别
    	 */
    	private String level;
		/**
    	 * 顶级ID
    	 */
    	private String topId;
    	/**
    	 * 顶级NAME
    	 */
    	private String topName;
    	public String getTopId() {
			return topId;
		}
		public void setTopId(String topId) {
			this.topId = topId;
		}
		public String getTopName() {
			return topName;
		}
		public void setTopName(String topName) {
			this.topName = topName;
		}
    	public String getLevel() {
			return level;
		}
		public void setLevel(String level) {
			this.level = level;
		}
		/**
    	 * 该字段在SQl使用LIKE语法用来查询当前节点的所有叶子节点
    	 */
		private String pathCode ;
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
    	public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		public String getPathCode() {
			return pathCode;
		}
		public void setPathCode(String pathCode) {
			this.pathCode = pathCode;
		}
		public Long getParentId(){
			return parentId;
		}
		public void setParentId(Long parentId){
			this.parentId = parentId;
		}
		public Long getEquipId(){
			return equipId;
		}
		public void setEquipId(Long equipId){
			this.equipId = equipId;
		}
		public Long getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(Long createUserId){
			this.createUserId = createUserId;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate() {
			return createDate;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		public Long getUpdateUserId() {
			return updateUserId;
		}
		public void setUpdateUserId(Long updateUserId) {
			this.updateUserId = updateUserId;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getUpdateDate() {
			return updateDate;
		}
		@JsonSerialize(using = JsonDateTimeSerializer.class)
		public void setUpdateDate(Date updateDate) {
			this.updateDate = updateDate;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public Long getUnitId() {
			return unitId;
		}
		public void setUnitId(Long unitId) {
			this.unitId = unitId;
		}
		public int getTreeType() {
			return treeType;
		}
		public void setTreeType(int treeType) {
			this.treeType = treeType;
		}
}