package com.aptech.business.run.runWay.domain;

import org.apache.ibatis.type.Alias;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 运行方式实体类
 *
 * @author 
 * @created 2017-06-20 09:26:27
 * @lastModified 
 * @history
 *
 */
@Alias("RunWayEntity")
public class RunWayEntity extends BaseEntity{
		/**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = 2139119565754558201L;
        /**
		 * 运行日志ID
		 */
    	private Integer rlId;
		/**
		 * 单位ID
		 */
    	private Integer unitId;
		/**
		 * 设备ID
		 */
    	private Integer deviceId;
		/**
		 * 设备类型
		 */
    	private String deviceType;
		/**
		 * 运行方式
		 */
    	private String runWay;
    	/**
         * 运行方式名称
         */
        private String runWayName;
        /**
         * 单位
         */
        private String unitName;
        /**
         * 设备名称
         */
        private String deviceName;
        /**
         * 合并数
         */
        private String rowspanNum;
        /**
         * 是否隐藏
         */
        private String tdHide;
        /**
         * 设备类型名称
         */
        private String deviceTypeName;
        /**
         * 设备CODE
         */
        private String equipCode;
        
		public String getEquipCode() {
			return equipCode;
		}
		public void setEquipCode(String equipCode) {
			this.equipCode = equipCode;
		}
		public String getDeviceTypeName()
        {
            return deviceTypeName;
        }
        public void setDeviceTypeName(String deviceTypeName)
        {
            this.deviceTypeName = deviceTypeName;
        }
        public String getRowspanNum()
        {
            return rowspanNum;
        }
        public void setRowspanNum(String rowspanNum)
        {
            this.rowspanNum = rowspanNum;
        }
        public String getTdHide()
        {
            return tdHide;
        }
        public void setTdHide(String tdHide)
        {
            this.tdHide = tdHide;
        }
        public String getDeviceName()
        {
            return deviceName;
        }
        public void setDeviceName(String deviceName)
        {
            this.deviceName = deviceName;
        }
        public String getUnitName()
        {
            return unitName;
        }
        public void setUnitName(String unitName)
        {
            this.unitName = unitName;
        }
        public String getRunWayName()
        {
            return runWayName;
        }
        public void setRunWayName(String runWayName)
        {
            this.runWayName = runWayName;
        }
        public static long getSerialversionuid()
        {
            return serialVersionUID;
        }
        public Integer getRlId(){
			return rlId;
		}
		public void setRlId(Integer rlId){
			this.rlId = rlId;
		}
		public Integer getUnitId(){
			return unitId;
		}
		public void setUnitId(Integer unitId){
			this.unitId = unitId;
		}
		public Integer getDeviceId(){
			return deviceId;
		}
		public void setDeviceId(Integer deviceId){
			this.deviceId = deviceId;
		}
		public String getDeviceType(){
			return deviceType;
		}
		public void setDeviceType(String deviceType){
			this.deviceType = deviceType;
		}
		public String getRunWay(){
			return runWay;
		}
		public void setRunWay(String runWay){
			this.runWay = runWay;
		}
}