package com.aptech.business.safeManage.accident.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.SgglTypeEnum;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 事故管理实体类
 *
 * @author 
 * @created 2018-04-02 09:02:22
 * @lastModified 
 * @history
 *
 */
@Alias("accidentEntity")
public class AccidentEntity extends BaseEntity{
		/**
	*
	* @author ly
	* @date 2018年9月25日 下午6:02:58 
	*/
	private static final long serialVersionUID = 1L;

		/**
		 * 主键
		 */
    	private Long id;
    	
    	private String unitName;
    	
    	private String timeString;
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
		 * 事故类别
		 */
    	private String sglb;
		/**
		 * 单位id
		 */
    	private String unitId;
		/**
		 * 事故时间
		 */
    	private Date time;
		/**
		 * 调查报告
		 */
    	private String dcbg;
		/**
		 * 附件id
		 */
    	private String fileid;
		/**
		 * 备用字段1
		 */
    	private String byong;
		/**
		 * 备用字段2
		 */
    	private String bytwo;
		/**
		 * 备用字段3
		 */
    	private String bythree;
    	
    	private String status;
    	
    	private String sglbName;
    	/**
    	 * 导出excel表格序号
    	 */
    	private Integer number;
    	
    	private String userName;
    	

		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
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
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getSglb(){
			return sglb;
		}
		public void setSglb(String sglb){
			this.sglb = sglb;
		}
		public String getUnitId(){
			return unitId;
		}
		public void setUnitId(String unitId){
			this.unitId = unitId;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getTime(){
			return time;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setTime(Date time){
			this.time = time;
		}
		public String getDcbg(){
			return dcbg;
		}
		public void setDcbg(String dcbg){
			this.dcbg = dcbg;
		}
		public String getFileid(){
			return fileid;
		}
		public void setFileid(String fileid){
			this.fileid = fileid;
		}
		public String getByong(){
			return byong;
		}
		public void setByong(String byong){
			this.byong = byong;
		}
		public String getBytwo(){
			return bytwo;
		}
		public void setBytwo(String bytwo){
			this.bytwo = bytwo;
		}
		public String getBythree(){
			return bythree;
		}
		public void setBythree(String bythree){
			this.bythree = bythree;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getSglbName() {
			for (SgglTypeEnum  worktypeenum: SgglTypeEnum.values()) {
				if (worktypeenum.getCode().equals(String.valueOf(this.getSglb()))) {
					sglbName =  worktypeenum.getName();
				}
			}
			return sglbName;
		}
		public String getTimeString() {
			return timeString;
		}
		public void setTimeString(String timeString) {
			this.timeString = timeString;
		}
		
}