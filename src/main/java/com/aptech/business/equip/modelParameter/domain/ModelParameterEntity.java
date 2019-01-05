package com.aptech.business.equip.modelParameter.domain;

import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 模版参数实体类
 *
 * @author 
 * @created 2017-06-20 15:59:01
 * @lastModified 
 * @history
 *
 */
@Alias("ModelParameterEntity")
public class ModelParameterEntity extends BaseEntity{
		/**
		 * 参数
		 */
    	private String parameter;
		/**
		 * 默认值
		 */
    	private String defaultValue;
		/**
		 * 参数类型
		 */
    	private Integer parameterType;
		/**
		 * 模版
		 */
    	private long modelId;
    	/**
    	 * 状态
    	 */
    	private Integer status;

		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public String getParameter(){
			return parameter;
		}
		public void setParameter(String parameter){
			this.parameter = parameter;
		}
		public String getDefaultValue(){
			return defaultValue;
		}
		public void setDefaultValue(String defaultValue){
			this.defaultValue = defaultValue;
		}
		public Integer getParameterType(){
			return parameterType;
		}
		public void setParameterType(Integer parameterType){
			this.parameterType = parameterType;
		}
		public Long getModelId(){
			return modelId;
		}
		public void setModelId(Long modelId){
			this.modelId = modelId;
		}
}