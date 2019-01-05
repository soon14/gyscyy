package com.aptech.business.run.joinLand.domain;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.JoinLandTypeEnum;
import com.aptech.business.component.dictionary.PowerTypeEnum;
import com.aptech.framework.orm.BaseEntity;

/**
 * 
 * 接地线（刀闸）情况实体类
 *
 * @author 
 * @created 2017-06-06 09:48:08
 * @lastModified 
 * @history
 *
 */
@Alias("joinLandEntity")
public class JoinLandEntity extends BaseEntity{
		/**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = -6134814121416922437L;
        /**
		 * 已封接地线位置及组数
		 */
    	private String closedPosition;
		/**
		 * 现存放接地线组数
		 */
    	private String depositGroup;
		/**
		 * 运行日志ID
		 */
    	private Integer rlId;
		/**
		 * 电站ID
		 */
    	private Integer unitId;
		/**
		 * 已合接地刀闸名称
		 */
    	private String closedName;
    	 /**
         * 运行方式
         */
        private String unitName;
        /**
         * 设备ID
         */
        private Integer deviceId;
        /**
         * 设备类型
         */
        private String deviceType;
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
         * 是否隐藏
         */
        private String[] state;
        /**
         * 设备类型名称
         */
        private String [] code;
        private String closedNameText;
        /**
         * 刀闸状态
         */
        private String swordbrake_status;
        /**
         * 设备名称(存储从设备表关联的设备名称)
         */
        private String equipName;
        public String getEquipName() {
			return equipName;
		}
		public void setEquipName(String equipName) {
			this.equipName = equipName;
		}
		public String getSwordbrake_status() {
			return swordbrake_status;
		}
		public void setSwordbrake_status(String swordbrake_status) {
			this.swordbrake_status = swordbrake_status;
		}
		public String getClosedNameText() {
            for (JoinLandTypeEnum joinLandTypeEnum : JoinLandTypeEnum.values()) {
                if (joinLandTypeEnum.getCode().equals(this.closedName)) {
                    return joinLandTypeEnum.getName();
                }
            }
            return null;
        }
        public void setClosedNameText(String closedNameText) {
            this.closedNameText = closedNameText;
        }
		public String[] getState()
        {
            return state;
        }
        public void setState(String[] state)
        {
            this.state = state;
        }
        public String[] getCode()
        {
            return code;
        }
        public void setCode(String[] code)
        {
            this.code = code;
        }
        public String getDeviceName()
        {
            return deviceName;
        }
        public void setDeviceName(String deviceName)
        {
            this.deviceName = deviceName;
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
        public String getDeviceTypeName()
        {
            return deviceTypeName;
        }
        public void setDeviceTypeName(String deviceTypeName)
        {
            this.deviceTypeName = deviceTypeName;
        }
        public Integer getDeviceId()
        {
            return deviceId;
        }
        public void setDeviceId(Integer deviceId)
        {
            this.deviceId = deviceId;
        }
        public String getDeviceType()
        {
            return deviceType;
        }
        public void setDeviceType(String deviceType)
        {
            this.deviceType = deviceType;
        }
        public String getUnitName()
        {
            return unitName;
        }
        public void setUnitName(String unitName)
        {
            this.unitName = unitName;
        }
        public static long getSerialversionuid()
        {
            return serialVersionUID;
        }
        public String getClosedPosition(){
			return closedPosition;
		}
		public void setClosedPosition(String closedPosition){
			this.closedPosition = closedPosition;
		}
		public String getDepositGroup(){
			return depositGroup;
		}
		public void setDepositGroup(String depositGroup){
			this.depositGroup = depositGroup;
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
		public String getClosedName(){
			return closedName;
		}
		public void setClosedName(String closedName){
			this.closedName = closedName;
		}
}