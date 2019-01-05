package com.aptech.business.run.runLog.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.aptech.framework.util.DateFormatUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 运行日志实体类
 *
 * @author 
 * @created 2017-06-05 10:52:45
 * @lastModified 
 * @history
 *
 */
@Alias("runLogEntity")
public class RunLogEntity extends BaseEntity{
		/**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = 6725410983727605531L;
        /**
		 * 人员ID串
		 */
    	private String personsIds;
		/**
		 * 班前会人员ID
		 */
    	private String btmPersonsIds;
		/**
		 * 负责人ID
		 */
    	private Integer chargeId;
		/**
		 * 交班人员ID串
		 */
    	private String givePersonsIds;
		/**
		 * 交班值次ID
		 */
    	private Integer giveDutyId;
		/**
		 * 班前会内容
		 */
    	private String btmContent;
		/**
		 * 交接班巡视完成状态
		 */
    	private Integer jfState;
		/**
		 * 编号
		 */
    	private String code;
		/**
		 * 值班日期
		 */
    	private Date date;
    	private String dateString;
		/**
		 * 交班密码
		 */
    	private String givePassword;
		/**
		 * 班次ID
		 */
    	private Integer teamId;
		/**
		 * 交班日期
		 */
    	private Date giveDate;
    	
    	private String giveDateString;
    	/**
		 * 创建日期
		 */
    	private Date createDate;
    	/**
		 * 创建日期
		 */
    	private String createDateString;
		/**
		 * 交班负责人ID
		 */
    	private Integer giveChargeId;
		/**
		 * 班后会人员ID
		 */
    	private String atmPersonsIds;
		/**
		 * 接班密码
		 */
    	private String receivePassword;
		/**
		 * 交班班次ID
		 */
    	private Integer giveTeamId;
		/**
		 * 班后会内容
		 */
    	private String atmContent;
		/**
		 * 值次ID
		 */
    	private Integer dutyId;
		/**
		 * 交接班状态
		 */
    	private String grState;
    	/**
         * 接班负责人姓名
         */
        private String chargeName;
        /**
         * 交班负责人姓名
         */
        private String giveChargeName;
        /**
         * 交接班状态名称
         */
        private String grStateName;
        /**
         * 交接班状态名称
         */
        private String teamName;
        /**
         * 交接班状态名称
         */
        private String dutyName;
        /**
         * 交接班状态名称
         */
        private String remark;
        /**
         * 交接班状态名称
         */
        private String fileId;
       
        private Integer check;
        
        private String checkName;
        private int number;
        //运行检查专有的检测状态
        private String checkForRun;
        private String checkForRunName;
        
        
        
        
        
        public String getCheckForRun() {
			return checkForRun;
		}
		public void setCheckForRun(String checkForRun) {
			this.checkForRun = checkForRun;
		}
		public String getCheckForRunName() {
			Map<String, SysDictionaryVO> runCheckTypeMap  =  DictionaryUtil.getDictionaries("RUN_CHECK");
	         if(checkForRun!=null){
	        	 for(String key :  runCheckTypeMap.keySet()){
		             SysDictionaryVO sysDictionaryVO = runCheckTypeMap.get(key);
		             if(checkForRun!=null&&sysDictionaryVO.getCode().toString().equals(checkForRun.toString())){
		            	 return  sysDictionaryVO.getName();
		             };
		         } 
	         }else{
	        	 for(String key :  runCheckTypeMap.keySet()){
		             SysDictionaryVO sysDictionaryVO = runCheckTypeMap.get(key);
		             if(sysDictionaryVO.getCode().toString().equals("0")){
		            	 return  sysDictionaryVO.getName();
		             };
		         } 
	         }
	         
	         return "";
		}
		public void setCheckForRunName(String checkForRunName) {
			this.checkForRunName = checkForRunName;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public Integer getUnit() {
			return unit;
		}
		public void setUnit(Integer unit) {
			this.unit = unit;
		}
		private Integer unit;
        
		public String getCreateDateString() {
			if(createDateString !=null && createDateString !=""){
				return createDateString;
			}
			if(this.createDate ==null){
				return createDateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			return df.format(this.createDate);
		}
		public void setCreateDateString(String createDateString) {
			this.createDateString = createDateString;
		}
		public Integer getCheck() {
			return check;
		}
		public void setCheck(Integer check) {
			this.check = check;
		}
		public String getCheckName() {
			Map<String, SysDictionaryVO> runCheckTypeMap  =  DictionaryUtil.getDictionaries("RUN_CHECK");
	         
	         for(String key :  runCheckTypeMap.keySet()){
	             SysDictionaryVO sysDictionaryVO = runCheckTypeMap.get(key);
	             if(check!=null&&sysDictionaryVO.getCode().toString().equals(check.toString())){
	            	 return  sysDictionaryVO.getName();
	             };
	         }
	         return "";
		}
		public void setCheckName(String checkName) {
			this.checkName = checkName;
		}
		public String getDateString() {
			if(dateString !=null && dateString !=""){
				return dateString;
			}
			if(this.date ==null){
				return dateString;
			}
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			return df.format(this.date);
		}
		public void setDateString(String dateString) {
			this.dateString = dateString;
		}
		public String getGiveDateString() {
			return giveDateString;
		}
		public void setGiveDateString(String giveDateString) {
			this.giveDateString = giveDateString;
		}
		public String getRemark()
        {
            return remark;
        }
        public void setRemark(String remark)
        {
            this.remark = remark;
        }
        public String getFileId()
        {
            return fileId;
        }
        public void setFileId(String fileId)
        {
            this.fileId = fileId;
        }
        public String getDutyName()
        {
            return dutyName;
        }
        public void setDutyName(String dutyName)
        {
            this.dutyName = dutyName;
        }
        public String getTeamName()
        {
            return teamName;
        }
        public void setTeamName(String teamName)
        {
            this.teamName = teamName;
        }
        public String getGivePassword()
        {
            return givePassword;
        }
        public void setGivePassword(String givePassword)
        {
            this.givePassword = givePassword;
        }
        public String getGrStateName()
        {
            return grStateName;
        }
        public void setGrStateName(String grStateName)
        {
            this.grStateName = grStateName;
        }
        public String getChargeName()
        {
            return chargeName;
        }
        public void setChargeName(String chargeName)
        {
            this.chargeName = chargeName;
        }
        public String getGiveChargeName()
        {
            return giveChargeName;
        }
        public void setGiveChargeName(String giveChargeName)
        {
            this.giveChargeName = giveChargeName;
        }
        public static long getSerialversionuid()
        {
            return serialVersionUID;
        }
        public String getPersonsIds()
        {
            return personsIds;
        }
        public void setPersonsIds(String personsIds)
        {
            this.personsIds = personsIds;
        }
        public String getBtmPersonsIds(){
			return btmPersonsIds;
		}
		public void setBtmPersonsIds(String btmPersonsIds){
			this.btmPersonsIds = btmPersonsIds;
		}
		public Integer getChargeId(){
			return chargeId;
		}
		public void setChargeId(Integer chargeId){
			this.chargeId = chargeId;
		}
		public String getGivePersonsIds(){
			return givePersonsIds;
		}
		public void setGivePersonsIds(String givePersonsIds){
			this.givePersonsIds = givePersonsIds;
		}
		public Integer getGiveDutyId(){
			return giveDutyId;
		}
		public void setGiveDutyId(Integer giveDutyId){
			this.giveDutyId = giveDutyId;
		}
		public String getBtmContent(){
			return btmContent;
		}
		public void setBtmContent(String btmContent){
			this.btmContent = btmContent;
		}
		public Integer getJfState(){
			return jfState;
		}
		public void setJfState(Integer jfState){
			this.jfState = jfState;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}


		public Integer getTeamId(){
			return teamId;
		}
		public void setTeamId(Integer teamId){
			this.teamId = teamId;
		}
        @JsonSerialize(using = JsonDateTime1Serializer.class)	
        public Date getDate()
        {
            return date;
        }
        @JsonDeserialize(using = JsonDateTime1Deserializer.class)
        public void setDate(Date date)
        {
            this.date = date;
        }
        @JsonSerialize(using = JsonDateTime1Serializer.class)   
        public Date getGiveDate()
        {
            return giveDate;
        }
        @JsonDeserialize(using = JsonDateTime1Deserializer.class)
        public void setGiveDate(Date giveDate)
        {
            this.giveDate = giveDate;
        }
        @JsonSerialize(using = JsonDateTimeSerializer.class)   
        public Date getCreateDate()
        {
            return createDate;
        }
        @JsonDeserialize(using = JsonDateTimeDeserializer.class)
        public void setCreateDate(Date createDate)
        {
            this.createDate = createDate;
        }
    	public String getCreateDateDay() {
    		if(createDate!=null){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			return  sdf.format(createDate);
    		}
    		return "";
    	}
        public Integer getGiveChargeId(){
			return giveChargeId;
		}
		public void setGiveChargeId(Integer giveChargeId){
			this.giveChargeId = giveChargeId;
		}
		public String getAtmPersonsIds(){
			return atmPersonsIds;
		}
		public void setAtmPersonsIds(String atmPersonsIds){
			this.atmPersonsIds = atmPersonsIds;
		}

		public String getReceivePassword(){
			return receivePassword;
		}
		public void setReceivePassword(String receivePassword){
			this.receivePassword = receivePassword;
		}
		public Integer getGiveTeamId(){
			return giveTeamId;
		}
		public void setGiveTeamId(Integer giveTeamId){
			this.giveTeamId = giveTeamId;
		}
		public String getAtmContent(){
			return atmContent;
		}
		public void setAtmContent(String atmContent){
			this.atmContent = atmContent;
		}
		public Integer getDutyId(){
			return dutyId;
		}
		public void setDutyId(Integer dutyId){
			this.dutyId = dutyId;
		}
        public String getGrState()
        {
            return grState;
        }
        public void setGrState(String grState)
        {
            this.grState = grState;
        }

}