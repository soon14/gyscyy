package com.aptech.business.equip.equiplParameter.domain;

import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 设备参数实体类
 *
 * @author 
 * @created 2017-06-12 14:04:46
 * @lastModified 
 * @history
 *
 */
@Alias("EquipParameterEntity")
public class EquipParameterEntity extends BaseEntity{
	private static final long serialVersionUID = -4904007473553881827L;
		/**
    	 * 参数
    	 */
    	private String parameter;
		/**
    	 * 默认值
    	 */
    	private String defaultValue;
    	/**
    	 * 
    	 */
    	private int parameterType;
    	/**
    	 * 设备ID
    	 */
    	private long equipLedgerId;
    	/**
    	 * 状态y
    	 */
    	private Integer status;
    	public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public String getParameter() {
    		return parameter;
    	}
    	public void setParameter(String parameter) {
    		this.parameter = parameter;
    	}
    	public String getDefaultValue() {
    		return defaultValue;
    	}
    	public void setDefaultValue(String defaultValue) {
    		this.defaultValue = defaultValue;
    	}
    	public int getParameterType() {
    		return parameterType;
    	}
    	public void setParameterType(int parameterType) {
    		this.parameterType = parameterType;
    	}
    	public long getEquipLedgerId() {
    		return equipLedgerId;
    	}
    	public void setEquipLedgerId(long equipLedgerId) {
    		this.equipLedgerId = equipLedgerId;
    	}
}