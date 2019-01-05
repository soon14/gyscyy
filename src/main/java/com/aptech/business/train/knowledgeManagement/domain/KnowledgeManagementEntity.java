package com.aptech.business.train.knowledgeManagement.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.TrainPlanStatusEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateDeserializer;
import com.aptech.framework.json.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 知识库管理实体类
 *
 * @author 
 * @created 2018-03-26 13:43:00
 * @lastModified 
 * @history
 *
 */
@Alias("KnowledgeManagementEntity")
public class KnowledgeManagementEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 组织单位id
		 */
    	private String unitId;
		/**
		 * 类型
		 */
    	private String knowledgeStyle;
		/**
		 * 主题
		 */
    	private String titleName;
		/**
		 * 关键字
		 */
    	private String keyword;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 下载次数
		 */
    	private String downloadCount;
		/**
		 * 文献资料
		 */
    	private String document;
		/**
		 * 创建人id
		 */
    	private String createPeopleId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
    	/**
    	 * 类型中文名称
    	 */
    	private String knowledgeStyleName;
    	/**
    	 * 单位中文名称
    	 */
    	private String unitName;
    	/**
    	 * 创建人中文名称
    	 */
    	private String createPeopleName;
    	/**
    	 * 导出序号（用于导出excel）
    	 */
    	private int number;
    	/**
    	 * 创建日期字符串格式（用于导出excel）
    	 */
    	private String exportDate;
    	/**
    	 * 创建日期字符串格式（用于页面展示）
    	 */
    	private String showDate;
		
		public String getExportDate() {
			return exportDate;
		}
		public void setExportDate(String exportDate) {
			this.exportDate = exportDate;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
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
		public String getKnowledgeStyle(){
			return knowledgeStyle;
		}
		public void setKnowledgeStyle(String knowledgeStyle){
			this.knowledgeStyle = knowledgeStyle;
		}
		public String getTitleName(){
			return titleName;
		}
		public void setTitleName(String titleName){
			this.titleName = titleName;
		}
		public String getKeyword(){
			return keyword;
		}
		public void setKeyword(String keyword){
			this.keyword = keyword;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getDownloadCount(){
			return downloadCount;
		}
		public void setDownloadCount(String downloadCount){
			this.downloadCount = downloadCount;
		}
		public String getDocument(){
			return document;
		}
		public void setDocument(String document){
			this.document = document;
		}
		public String getCreatePeopleId(){
			return createPeopleId;
		}
		public void setCreatePeopleId(String createPeopleId){
			this.createPeopleId = createPeopleId;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getKnowledgeStyleName() {
			if (this.knowledgeStyle!=null) {
				Map<String, String> knowledgeEnumMap = new HashMap<String, String>();
				Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("KNOWLEDGE_TYPE");
		        for(String key :  codeDateTypeMap.keySet()){
		        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
		        	knowledgeEnumMap.put(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		        }
				return knowledgeEnumMap.get(this.knowledgeStyle);
			}
			return null;
		}
		public void setKnowledgeStyleName(String knowledgeStyleName) {
			this.knowledgeStyleName = knowledgeStyleName;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getCreatePeopleName() {
			return createPeopleName;
		}
		public void setCreatePeopleName(String createPeopleName) {
			this.createPeopleName = createPeopleName;
		}
		public String getShowDate() {
			DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd");
			if(this.createDate!=null){
				return this.showDate = format.format(this.createDate);
			}else{
				return null;
			}
			
		}
		public void setShowDate(String showDate) {
			this.showDate = showDate;
		}
	    
}