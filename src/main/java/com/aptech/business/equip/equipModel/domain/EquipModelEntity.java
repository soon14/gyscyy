package com.aptech.business.equip.equipModel.domain;
import java.util.Map;
import org.apache.ibatis.type.Alias;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 *  设备模版实体类
 *
 * @author 
 * @created 2017-06-12 14:04:41
 * @lastModified 
 * @history
 *
 */
@Alias("EquipModelEntity")
public class EquipModelEntity extends BaseEntity{
		/**
		 * 设备名称
		 */
    	private String equipName;
		/**
		 * 是否删除
		 */
    	private Integer status = 1;
		/**
		 * 规格型号
		 */
    	private String specificationModel;
		/**
		 * 制造商
		 */
    	private String manuFacturer;
		/**
		 * 模版编号
		 */
    	private String modelNumber;
    	/**
    	 * 设备类型
    	 */
    	private Long equipType;
    	/**
    	 * 设备类型名称
    	 */
		public String getEquipTypeName() {
			Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("DEVICE_TYPE");
			String equipTypeName = "";
			if(codeDateTypeMap.keySet().size()>0){
				equipTypeName = codeDateTypeMap.get(String.valueOf(this.equipType)).getName();
			}
	        return equipTypeName;
		}
		public String getEquipName(){
			return equipName;
		}
		public void setEquipName(String equipName){
			this.equipName = equipName;
		}
		public Integer getStatus(){
			return status;
		}
		public void setStatus(Integer status){
			this.status = status;
		}
		public String getSpecificationModel(){
			return specificationModel;
		}
		public void setSpecificationModel(String specificationModel){
			this.specificationModel = specificationModel;
		}
		public String getManuFacturer(){
			return manuFacturer;
		}
		public void setManuFacturer(String manuFacturer){
			this.manuFacturer = manuFacturer;
		}
		public String getModelNumber(){
			return modelNumber;
		}
		public void setModelNumber(String modelNumber){
			this.modelNumber = modelNumber;
		}
		public Long getEquipType(){
			return equipType;
		}
		public void setEquipType(Long equipType){
			this.equipType = equipType;
		}
}