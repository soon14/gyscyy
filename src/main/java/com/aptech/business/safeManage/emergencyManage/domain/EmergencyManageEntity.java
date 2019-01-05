package com.aptech.business.safeManage.emergencyManage.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 应急管理实体类
 *
 * @author 
 * @created 2018-03-28 16:27:06
 * @lastModified 
 * @history
 *
 */
@Alias("EmergencyManageEntity")
public class EmergencyManageEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 单位Id
		 */
    	private String unitId;
		/**
		 * 用户Id
		 */
    	private String userId;
		/**
		 * 类别
		 */
    	private String type;
		/**
		 * 相关资料
		 */
    	private String fileId;
		/**
		 * 隐患排查名称
		 */
    	private String name;
		/**
		 * 时间
		 */
    	private Date time;
    	private String information;
    	private String unitName;
    	private String userName;
    	private Date yearNum;
    	private int number;
    	
    	
    	
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getInformation() {
			return information;
		}
		public void setInformation(String information) {
			this.information = information;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
		public String getUserId(){
			return userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
		public String getType(){
			return type;
		}
		public void setType(String type){
			this.type = type;
		}
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getTime(){
			return time;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setTime(Date time){
			this.time = time;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getYearNum() {
			return yearNum;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setYearNum(Date yearNum) {
			this.yearNum = yearNum;
		}
		public String getTypeName() {
			Map<String, SysDictionaryVO> typeMap  =  DictionaryUtil.getDictionaries("EMERGENCYTYPE");
			Map<String,String> typeEnumMap = new HashMap<String, String>();
			for(String key : typeMap.keySet()){
				SysDictionaryVO sysDictionaryVO = typeMap.get(key);
				typeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
			}
			return typeEnumMap.get(this.type);
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
}