package com.aptech.business.technical.technicalPlandetail.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.PlanCycleEnum;
import com.aptech.business.component.dictionary.TechnicalWcztEnum;
import com.aptech.business.util.JsonDateDeserializer;
import com.aptech.business.util.JsonDateSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 技术监督详细计划实体类
 *
 * @author 
 * @created 2017-11-13 16:16:12
 * @lastModified 
 * @history
 *
 */
@Alias("TechnicalPlandetailEntity")
public class TechnicalPlandetailEntity extends BaseEntity{
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 序号
		 */
    	private String orderSeq;
		/**
		 * 项目名称
		 */
    	private String planName;
		/**
		 * 定检周期
		 */
    	private String djzq;
		/**
		 * 本年计划时间
		 */
    	private Date nowTime;
		/**
		 * 实际完成时间
		 */
    	private Date wcTime;
		/**
		 * 完成状态
		 */
    	private Long wcStatus;
		/**
		 * 完成情况
		 */
    	private String wcqk;
		/**
		 * 风险和问题
		 */
    	private String danger;
		/**
		 * 附件id
		 */
    	private String fileid;
		/**
		 * 逻辑编码
		 */
    	private String uuidCode;
		/**
		 * 主表id
		 */
    	private String technicalWorkid;
		/**
		 * 是否删除
		 */
    	private Long status;
    	/**
		 * 技术监督表ID
		 */
    	private Long technicalId;

    	private String djzqStr;
    	
    	private String wcStatusStr;
    	
    	private String orderSeqWork;
    	
		public String getDjzqStr() {
			for (PlanCycleEnum  worktypeenum: PlanCycleEnum.values()) {
				if (worktypeenum.getCode().equals(String.valueOf(this.getDjzq()))) {
					djzqStr =  worktypeenum.getName();
				}
			}
			return djzqStr;
		}
		public void setDjzqStr(String djzqStr) {
			this.djzqStr = djzqStr;
		}
		public String getWcStatusStr() {
			for (TechnicalWcztEnum  worktypeenum: TechnicalWcztEnum.values()) {
				if (worktypeenum.getCode().equals(String.valueOf(this.getWcStatus()))) {
					wcStatusStr =  worktypeenum.getName();
				}
			}
			return wcStatusStr;
		}
		public void setWcStatusStr(String wcStatusStr) {
			this.wcStatusStr = wcStatusStr;
		}
		public String getOrderSeqWork() {
			return orderSeqWork;
		}
		public void setOrderSeqWork(String orderSeqWork) {
			this.orderSeqWork = orderSeqWork;
		}
		public Long getTechnicalId() {
			return technicalId;
		}
		public void setTechnicalId(Long technicalId) {
			this.technicalId = technicalId;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public String getOrderSeq(){
			return orderSeq;
		}
		public void setOrderSeq(String orderSeq){
			this.orderSeq = orderSeq;
		}
		public String getPlanName(){
			return planName;
		}
		public void setPlanName(String planName){
			this.planName = planName;
		}
		public String getDjzq(){
			return djzq;
		}
		public void setDjzq(String djzq){
			this.djzq = djzq;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getNowTime(){
			return nowTime;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setNowTime(Date nowTime){
			this.nowTime = nowTime;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getWcTime(){
			return wcTime;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setWcTime(Date wcTime){
			this.wcTime = wcTime;
		}
		public Long getWcStatus(){
			return wcStatus;
		}
		public void setWcStatus(Long wcStatus){
			this.wcStatus = wcStatus;
		}
		public String getWcqk(){
			return wcqk;
		}
		public void setWcqk(String wcqk){
			this.wcqk = wcqk;
		}
		public String getDanger(){
			return danger;
		}
		public void setDanger(String danger){
			this.danger = danger;
		}
		public String getFileid(){
			return fileid;
		}
		public void setFileid(String fileid){
			this.fileid = fileid;
		}
		public String getUuidCode(){
			return uuidCode;
		}
		public void setUuidCode(String uuidCode){
			this.uuidCode = uuidCode;
		}
		public String getTechnicalWorkid(){
			return technicalWorkid;
		}
		public void setTechnicalWorkid(String technicalWorkid){
			this.technicalWorkid = technicalWorkid;
		}
		public Long getStatus(){
			return status;
		}
		public void setStatus(Long status){
			this.status = status;
		}
}