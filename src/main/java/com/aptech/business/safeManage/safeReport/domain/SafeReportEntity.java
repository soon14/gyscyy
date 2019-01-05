package com.aptech.business.safeManage.safeReport.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 安全月报实体类
 *
 * @author 
 * @created 2018-03-28 11:18:00
 * @lastModified 
 * @history
 *
 */
@Alias("SafeReportEntity")
public class SafeReportEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 月报名称
		 */
    	private String name;
		/**
		 * 年号
		 */
    	private Date yearNum;
		/**
		 * 时间
		 */
    	private Date time;
    	private String fileId;
    	private int number;
    	private String userName;
    	private String userId;
    	private String unitId;
    	private String unitName;
    	
    	
    	
    	public String getUnitId() {
			return unitId;
		}
		public void setUnitId(String unitId) {
			this.unitId = unitId;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getYearNumString() {
    		if(yearNum!=null){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    			return  sdf.format(yearNum);
    		}
    		return "";
    	}
    	public String getTimeString() {
    		if(time!=null){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			return  sdf.format(time);
    		}
    		return "";
    	}
		public String getFileId() {
			return fileId;
		}
		public void setFileId(String fileId) {
			this.fileId = fileId;
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
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getYearNum(){
			return yearNum;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setYearNum(Date yearNum){
			this.yearNum = yearNum;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getTime(){
			return time;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setTime(Date time){
			this.time = time;
		}
}