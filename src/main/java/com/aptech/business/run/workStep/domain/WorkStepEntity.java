package com.aptech.business.run.workStep.domain;

import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 工作步骤实体类
 *
 * @author 
 * @created 2017-06-02 11:28:16
 * @lastModified 
 * @history
 *
 */
@Alias("workStepEntity")
public class WorkStepEntity extends BaseEntity{
		/**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = 3338463459527818705L;
        /**
		 * 工作记录ID
		 */
    	private Integer wrId;
		/**
		 * 工作步骤内容
		 */
    	private String stepContent;


		public Integer getWrId(){
			return wrId;
		}
		public void setWrId(Integer wrId){
			this.wrId = wrId;
		}
		public String getStepContent(){
			return stepContent;
		}
		public void setStepContent(String stepContent){
			this.stepContent = stepContent;
		}
}