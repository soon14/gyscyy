package com.aptech.business.safeManage.infoSubmit.domain;

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
 * 信息报送实体类
 *
 * @author 
 * @created 2018-03-28 18:05:15
 * @lastModified 
 * @history
 *
 */
@Alias("InfoSubmitEntity")
public class InfoSubmitEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 名称
		 */
    	private String name;
		/**
		 * 报送单位
		 */
    	private String unitId;
		/**
		 * 报送时间
		 */
    	private Date date;
		/**
		 * 相关资料名称
		 */
    	private String fileName;
		/**
		 * 相关资料地址
		 */
    	private String fileAddress;
		/**
		 * 备注
		 */
    	private String comment;
    	/**
    	 * 报送单位 显示名称
    	 */
    	private String unitName;
    	/**
    	 * 报表导出  报送时间字符串
    	 */
    	private String dateStr;
    	/**
    	 * 报表导出 序号
    	 */
    	private Integer number;
    	
    	/**
    	 * 创建人ID
    	 */
    	private String creatorId;
    	/**
    	 * 创建时间
    	 */
    	private Date createTime;
    	
    	private String userName;
    	

		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getCreatorId() {
			return creatorId;
		}
		public void setCreatorId(String creatorId) {
			this.creatorId = creatorId;
		}
		
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public String getDateStr() {
			DateFormatUtil dfu = DateFormatUtil.getInstance("yyyy-MM-dd");
			if(date.equals(null)){
				return dfu.format(date);
			}else{
				return dfu.format(date);
			}
		}
		public void setDateStr(String dateStr) {
			this.dateStr = dateStr;
		}
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}

		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
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
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getDate(){
			return date;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setDate(Date date){
			this.date = date;
		}
		public String getFileName(){
			return fileName;
		}
		public void setFileName(String fileName){
			this.fileName = fileName;
		}
		public String getFileAddress(){
			return fileAddress;
		}
		public void setFileAddress(String fileAddress){
			this.fileAddress = fileAddress;
		}
		public String getComment(){
			return comment;
		}
		public void setComment(String comment){
			this.comment = comment;
		}
		@Override
		public String toString() {
			return "InfoSubmitEntity [id=" + id + ", name=" + name
					+ ", unitId=" + unitId + ", date=" + date + ", fileName="
					+ fileName + ", fileAddress=" + fileAddress + ", comment="
					+ comment + ", unitName=" + unitName + ", dateStr="
					+ dateStr + ", number=" + number + "]";
		}
		
}