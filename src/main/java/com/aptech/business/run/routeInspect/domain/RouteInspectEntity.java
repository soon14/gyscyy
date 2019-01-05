package com.aptech.business.run.routeInspect.domain;


import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 巡检记录实体类
 *
 * @author 
 * @created 2018-03-13 17:34:53
 * @lastModified 
 * @history
 *
 */
@Alias("RouteInspectEntity")
public class RouteInspectEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 记录时间
		 */
    	private Date recordTime;
		/**
		 * 附件名称
		 */
    	private String attachmentName;
		/**
		 * 单位id
		 */
    	private Long unitId;
		/**
		 * 附件URL
		 */
    	private String attachmentUrl;
		/**
		 * 运行日志id
		 */
    	private Long rlId;
		/**
		 * 记录内容
		 */
    	private String recordContent;
		/**
		 * 负责人
		 */
    	private String fzr;
		/**
		 * 类型
		 */
    	private String recordType;
    	/**
    	 * 单位名称
    	 */
    	private String unitName;
    	/**
    	 * 类型名称
    	 */
    	private String recordTypeName;
    	
    	private String recordTimeString;
    	private int number;
    	private String findQuestion;
    	
    	
    	
		public String getFindQuestion() {
			return findQuestion;
		}
		public void setFindQuestion(String findQuestion) {
			this.findQuestion = findQuestion;
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
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getRecordTime(){
			return recordTime;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setRecordTime(Date recordTime){
			this.recordTime = recordTime;
		}
		public String getAttachmentName(){
			return attachmentName;
		}
		public void setAttachmentName(String attachmentName){
			this.attachmentName = attachmentName;
		}
		public Long getUnitId(){
			return unitId;
		}
		public void setUnitId(Long unitId){
			this.unitId = unitId;
		}
		public String getAttachmentUrl(){
			return attachmentUrl;
		}
		public void setAttachmentUrl(String attachmentUrl){
			this.attachmentUrl = attachmentUrl;
		}
		public Long getRlId(){
			return rlId;
		}
		public void setRlId(Long rlId){
			this.rlId = rlId;
		}
		public String getRecordContent(){
			return recordContent;
		}
		public void setRecordContent(String recordContent){
			this.recordContent = recordContent;
		}
		public String getFzr(){
			return fzr;
		}
		public void setFzr(String fzr){
			this.fzr = fzr;
		}
		public String getRecordType(){
			return recordType;
		}
		public void setRecordType(String recordType){
			this.recordType = recordType;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getRecordTypeName() {
			return recordTypeName;
		}
		public void setRecordTypeName(String recordTypeName) {
			this.recordTypeName = recordTypeName;
		}
		public String getRecordTimeString() {
        	if(recordTimeString !=null && recordTimeString !=""){
				return recordTimeString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
			if(this.recordTime ==null){
				return recordTimeString;
			}
			return df.format(this.recordTime);
		}
		public void setRecordTimeString(String recordTimeString) {
			this.recordTimeString = recordTimeString;
		}
		@Override
		public String toString() {
			return "RouteInspectEntity [id=" + id + ", recordTime="
					+ recordTime + ", attachmentName=" + attachmentName
					+ ", unitId=" + unitId + ", attachmentUrl=" + attachmentUrl
					+ ", rlId=" + rlId + ", recordContent=" + recordContent
					+ ", fzr=" + fzr + ", recordType=" + recordType
					+ ", unitName=" + unitName + ", recordTypeName="
					+ recordTypeName + "]";
		}
		
}