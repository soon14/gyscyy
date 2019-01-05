package com.aptech.business.planManage.planDetail.domain;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.business.util.JsonDateDeserializer;
import com.aptech.business.util.JsonDateSerializer;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 详细计划实体类
 *
 * @author 
 * @created 2017-11-13 15:10:26
 * @lastModified 
 * @history
 *
 */
@Alias("PlanDetailEntity")
public class PlanDetailEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = 7395741313662183701L;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 整体计划id
		 */
    	private Long planWholeId;
		/**
		 * 序号
		 */
    	private String num;
		/**
		 * 项目名称
		 */
    	private String projectName;
		/**
		 * 具体项目明细
		 */
    	private String projectDetail;
		/**
		 * 方案、措施
		 */
    	private String step;
		/**
		 * 计划开工时间
		 */
    	private Date stratTime;
		/**
		 * 计划完成时间
		 */
    	private Date endTime;
		/**
		 * 计划总投资（万元）
		 */
    	private String planTotal;
		/**
		 * 完成状态
		 */
    	private Long finish;
    	
		/**
		 * 备注
		 */
    	private String remark;
		/**
		 * 附件
		 */
    	private String fileId;
    	
    	private String  uuidWhole;
    	
		public String getUuidWhole() {
			return uuidWhole;
		}
		public void setUuidWhole(String uuidWhole) {
			this.uuidWhole = uuidWhole;
		}
		public String getFinishName() {
			Map<String, SysDictionaryVO> typeMap  =  DictionaryUtil.getDictionaries("FINISH_TYPE");
			for(String key :  typeMap.keySet()){
				SysDictionaryVO sysDictionaryVO = typeMap.get(key);
				if(finish!=null&&sysDictionaryVO.getCode().equals(String.valueOf(finish))){
					 return  sysDictionaryVO.getName();
				}
			}
			return "";
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public Long getPlanWholeId(){
			return planWholeId;
		}
		public void setPlanWholeId(Long planWholeId){
			this.planWholeId = planWholeId;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getProjectName(){
			return projectName;
		}
		public void setProjectName(String projectName){
			this.projectName = projectName;
		}
		public String getProjectDetail(){
			return projectDetail;
		}
		public void setProjectDetail(String projectDetail){
			this.projectDetail = projectDetail;
		}
		public String getStep(){
			return step;
		}
		public void setStep(String step){
			this.step = step;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getStratTime(){
			return stratTime;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setStratTime(Date stratTime){
			this.stratTime = stratTime;
		}
	    @JsonSerialize(using = JsonDateSerializer.class)
		public Date getEndTime(){
			return endTime;
		}
	    @JsonDeserialize(using = JsonDateDeserializer.class)
		public void setEndTime(Date endTime){
			this.endTime = endTime;
		}
		public String getPlanTotal(){
			return planTotal;
		}
		public void setPlanTotal(String planTotal){
			this.planTotal = planTotal;
		}
		public Long getFinish(){
			return finish;
		}
		public void setFinish(Long finish){
			this.finish = finish;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark = remark;
		}
		public String getFileId(){
			return fileId;
		}
		public void setFileId(String fileId){
			this.fileId = fileId;
		}
}