package com.aptech.business.technical.technical.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.TechnicalStatusEnum;
import com.aptech.business.component.dictionary.TechnicalWcztEnum;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 技术监督实体类
 *
 * @author 
 * @created 2017-11-14 10:51:31
 * @lastModified 
 * @history
 *
 */
@Alias("TechnicalEntity")
public class TechnicalEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 监督专业id
		 */
    	private Long jdzyId;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 序号
		 */
    	private String seq;
		/**
		 * 时间
		 */
    	private String time;
		/**
		 * 工作总结
		 */
    	private String gzzj;
		/**
		 * 完成状态
		 */
    	private Long wczt;
		/**
		 * 是否删除
		 */
    	private Long status;
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 附件ID
		 */
    	private String fileid;
		/**
		 * 单位名称id
		 */
    	private Long unitId;
		/**
		 * 填报人id
		 */
    	private Long fillId;
		/**
		 * 填报人名字
		 */
    	private String fillName;
		/**
		 * 检修专工id
		 */
    	private Long jxzgId;
		/**
		 * 检修专工名字
		 */
    	private String jxzgName;
		/**
		 * 检修专工时间
		 */
    	private Date jxzgDate;
		/**
		 * 检修主任id
		 */
    	private Long jxzrId;
		/**
		 * 检修主任名字
		 */
    	private String jxzrName;
		/**
		 * 检修主任时间
		 */
    	private Date jxzrDate;
		/**
		 * 生技部主任id
		 */
    	private Long sjbzrId;
		/**
		 * 生技部主任名字
		 */
    	private String sjbzrName;
		/**
		 * 生技部主任时间
		 */
    	private Date sjbzrDate;
		/**
		 * 总工id
		 */
    	private Long zgId;
		/**
		 * 总工名字
		 */
    	private String zgName;
		/**
		 * 总工时间
		 */
    	private Date zgDate;
    	
    	private String unitName;
    	
    	private String jdzyName;
    	
    	private String wcztName;
    	
    	private String spStatus;
    	
    	private String spStatusName;
    	
    	private String uuid;
    	
    	/**
		 * 保存还是提交   
		 */
    	private String saveOrSubmit;
    	/**
		 * selectUser   审批人
		 */
    	private String selectUser;
    	
    	
    	private String approveIdea;
    	
    	private String spFlag;
    	
    	private String gzfzrtxWcqk;
    	
    	//计划名称
    	private String planName;
    	//序号
    	private int number;
    	
    	
    	
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getPlanName() {
			return planName;
		}
		public void setPlanName(String planName) {
			this.planName = planName;
		}
		public String getGzfzrtxWcqk() {
			return gzfzrtxWcqk;
		}
		public void setGzfzrtxWcqk(String gzfzrtxWcqk) {
			this.gzfzrtxWcqk = gzfzrtxWcqk;
		}
		public String getSpFlag() {
			return spFlag;
		}
		public void setSpFlag(String spFlag) {
			this.spFlag = spFlag;
		}
		public String getApproveIdea() {
			return approveIdea;
		}
		public void setApproveIdea(String approveIdea) {
			this.approveIdea = approveIdea;
		}
		public String getSaveOrSubmit() {
			return saveOrSubmit;
		}
		public void setSaveOrSubmit(String saveOrSubmit) {
			this.saveOrSubmit = saveOrSubmit;
		}
		public String getSelectUser() {
			return selectUser;
		}
		public void setSelectUser(String selectUser) {
			this.selectUser = selectUser;
		}
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getSpStatusName() {
			for (TechnicalStatusEnum  worktypeenum: TechnicalStatusEnum.values()) {
				if (worktypeenum.getCode().equals(String.valueOf(this.getSpStatus()))) {
					spStatusName =  worktypeenum.getName();
				}
			}
			return spStatusName;
		}
		public String getSpStatus() {
			return spStatus;
		}
		public void setSpStatus(String spStatus) {
			this.spStatus = spStatus;
		}
		public String getWcztName() {
			for (TechnicalWcztEnum  worktypeenum: TechnicalWcztEnum.values()) {
				if (worktypeenum.getCode().equals(String.valueOf(this.getWczt()))) {
					wcztName =  worktypeenum.getName();
				}
			}
			return wcztName;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		public String getJdzyName() {
			return jdzyName;
		}
		public void setJdzyName(String jdzyName) {
			this.jdzyName = jdzyName;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public Long getJdzyId(){
			return jdzyId;
		}
		public void setJdzyId(Long jdzyId){
			this.jdzyId = jdzyId;
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
		public String getSeq(){
			return seq;
		}
		public void setSeq(String seq){
			this.seq = seq;
		}
		public String getTime(){
			return time;
		}
		public void setTime(String time){
			this.time = time;
		}
		public String getGzzj(){
			return gzzj;
		}
		public void setGzzj(String gzzj){
			this.gzzj = gzzj;
		}
		public Long getWczt(){
			return wczt;
		}
		public void setWczt(Long wczt){
			this.wczt = wczt;
		}
		public Long getStatus(){
			return status;
		}
		public void setStatus(Long status){
			this.status = status;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getFileid(){
			return fileid;
		}
		public void setFileid(String fileid){
			this.fileid = fileid;
		}
		public Long getUnitId(){
			return unitId;
		}
		public void setUnitId(Long unitId){
			this.unitId = unitId;
		}
		public Long getFillId(){
			return fillId;
		}
		public void setFillId(Long fillId){
			this.fillId = fillId;
		}
		public String getFillName(){
			return fillName;
		}
		
		public void setFillName(String fillName){
			this.fillName = fillName;
		}
		public Long getJxzgId(){
			return jxzgId;
		}
		public void setJxzgId(Long jxzgId){
			this.jxzgId = jxzgId;
		}
		public String getJxzgName(){
			return jxzgName;
		}
		public void setJxzgName(String jxzgName){
			this.jxzgName = jxzgName;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getJxzgDate(){
			return jxzgDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setJxzgDate(Date jxzgDate){
			this.jxzgDate = jxzgDate;
		}
		public Long getJxzrId(){
			return jxzrId;
		}
		public void setJxzrId(Long jxzrId){
			this.jxzrId = jxzrId;
		}
		public String getJxzrName(){
			return jxzrName;
		}
		public void setJxzrName(String jxzrName){
			this.jxzrName = jxzrName;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getJxzrDate(){
			return jxzrDate;
		}
	    public void setJxzrDate(Date jxzrDate) {
			this.jxzrDate = jxzrDate;
		}
		@JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void Sjbzr(Date jxzrDate){
			this.jxzrDate = jxzrDate;
		}
		public Long getSjbzrId(){
			return sjbzrId;
		}
		public void setSjbzrId(Long sjbzrId){
			this.sjbzrId = sjbzrId;
		}
		public String getSjbzrName(){
			return sjbzrName;
		}
		public void setSjbzrName(String sjbzrName){
			this.sjbzrName = sjbzrName;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getSjbzrDate(){
			return sjbzrDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setSjbzrDate(Date sjbzrDate){
			this.sjbzrDate = sjbzrDate;
		}
		public Long getZgId(){
			return zgId;
		}
		public void setZgId(Long zgId){
			this.zgId = zgId;
		}
		public String getZgName(){
			return zgName;
		}
		public void setZgName(String zgName){
			this.zgName = zgName;
		}
	    @JsonSerialize(using = JsonDateTimeSerializer.class)
		public Date getZgDate(){
			return zgDate;
		}
	    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
		public void setZgDate(Date zgDate){
			this.zgDate = zgDate;
		}
	    public void setWcztName(String wcztName) {
			this.wcztName = wcztName;
		}
		public void setSpStatusName(String spStatusName) {
			this.spStatusName = spStatusName;
		}
}