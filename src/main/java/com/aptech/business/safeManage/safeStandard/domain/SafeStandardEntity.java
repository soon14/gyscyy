package com.aptech.business.safeManage.safeStandard.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 安全标准化建设实体类
 *
 * @author 
 * @created 2018-04-02 13:58:10
 * @lastModified 
 * @history
 *
 */
@Alias("SafeStandardEntity")
public class SafeStandardEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 标准化名称
		 */
    	private String standardName;
		/**
		 * 类别
		 */
    	private String category;
		/**
		 * 时间
		 */
    	private Date time;
		/**
		 * 年号
		 */
    	private String yearNum;
		/**
		 * 相关资料名称
		 */
    	private String fileName;
		/**
		 * 相关资料地址
		 */
    	private String fileAddress;
    	/**
    	 * 导出序号（用于导出excel）
    	 */
    	private int number;
    	/**
    	 * 检查时间字符串格式（用于导出excel）
    	 */
    	private String exportDate;
    	/**
		 * 创建人id
		 */
    	private String createPeopleId;
    	/**
		 * 创建时间
		 */
    	private Date createDate;
    	
    	private String userName;
    	
    	private String unitName;
    	
    	private Long unitId;
    	
    	
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public Long getUnitId() {
			return unitId;
		}
		public void setUnitId(Long unitId) {
			this.unitId = unitId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getStandardName(){
			return standardName;
		}
		public void setStandardName(String standardName){
			this.standardName = standardName;
		}
		public String getCategory(){
			return category;
		}
		public void setCategory(String category){
			this.category = category;
		}
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getTime(){
			return time;
		}
		@JsonDeserialize(using = JsonDateDeserializer.class)
		public void setTime(Date time){
			this.time = time;
		}
		public String getYearNum(){
			return yearNum;
		}
		public void setYearNum(String yearNum){
			this.yearNum = yearNum;
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
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getExportDate() {
			DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.time!=null){
				return this.exportDate = format.format(this.time);
			}else{
				return null;
			}
		}
		public void setExportDate(String exportDate) {
			this.exportDate = exportDate;
		}
		public String getStandardStyleName() {
			if (this.category!=null) {
				Map<String, String> knowledgeEnumMap = new HashMap<String, String>();
				Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("SAFESTANDARD_TYPE");
		        for(String key :  codeDateTypeMap.keySet()){
		        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
		        	knowledgeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		        }
				return knowledgeEnumMap.get(this.category);
			}
			return null;
		}
		public String getCreatePeopleId() {
			return createPeopleId;
		}
		public void setCreatePeopleId(String createPeopleId) {
			this.createPeopleId = createPeopleId;
		}
		public Date getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
}