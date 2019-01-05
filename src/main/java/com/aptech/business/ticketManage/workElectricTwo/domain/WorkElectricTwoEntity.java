package com.aptech.business.ticketManage.workElectricTwo.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 电气工作票实体类
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Alias("workElectricTwoEntity")
public class WorkElectricTwoEntity extends BaseEntity{
		/**
	 * 
	 */
	private static final long serialVersionUID = -311418022093056155L;
		/**
		 * 批准工作时间开始(批准人）
		 */
    	private Date approveStarttime;
		/**
		 * 收到工作票时间（收票）
		 */
    	private Date getticketTime;
		/**
		 * 延期值班长签名ID
		 */
    	private Long delayDutyMonitorId;
		/**
		 * 工作负责人确认时间(终结）
		 */
    	private Date endPicDate;
		/**
		 * 延期工作负责人确认日期
		 */
    	private Date delayPicSureDate;
		/**
		 * 原工作负责人名字
		 */
    	private String changeOldPicName;
    	
    	private String changeAllowIdea;
		/**
		 * 工作票签发人ID（签发）
		 */
    	private Long signerId;
		/**
		 * 延期许可人确认日期
		 */
    	private Date delayAllowSureDate;
		/**
		 * 延期值班长签名
		 */
    	private String delayDutyMonitorName;
		/**
		 * 值长名字（许可人）
		 */
    	private String allowMonitorName;
		/**
		 * 工作票表id
		 */
    	private Long workticketId;
    	
    	private String fileid;
		/**
		 * 延期工作负责人名字
		 */
    	private String delayPicName;
		/**
		 * 指定专责监护人ID
		 */
    	private Long remarkGuarderId;
		/**
		 * 终结台数编号
		 */
    	private String endStandIndex;
		/**
		 * 工作人员变动，工作负责人名字
		 */
    	private String workPersonPicName;
		/**
		 * 指定专责监护人名字
		 */
    	private String remarkGuarderName;
		/**
		 * 热控工作负责人ID
		 */
    	private Long heatPersonId;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 电气工作票负责人ID
		 */
    	private Long electricPersonId;
		/**
		 * 运行后工作负责人ID
		 */
    	private Long stopPicId;
		/**
		 * 运行后工作负责人名字
		 */
    	private String stopPicName;
		/**
		 * 工作票签发人名字（签发）
		 */
    	private String signerName;
		/**
		 * 延期许可人ID
		 */
    	private Long delayAllowId;
		/**
		 * 运行值班人员ID（收票)
		 */
    	private Long ondutyId;
		/**
		 * 工作人员变动，工作班组人员
		 */
    	private String workPersonGroup;
		/**
		 * 许可人确认时间(终结）
		 */
    	private Date endAllowDate;
		/**
		 * 变更后工作负责人名字
		 */
    	private String changeNewPicName;
		/**
		 * 延期许可人名字
		 */
    	private String delayAllowName;
		/**
		 * 工作人员变动，工作负责人ID
		 */
    	private Long workPersonPicId;
		/**
		 * 热控工作票编号
		 */
    	private String heatCode;
		/**
		 * 值班长ID(批准人）
		 */
    	private Long dutyMonitorId;
		/**
		 * 工作许可人ID
		 */
    	private Long changeAllowId;
		/**
		 * 机械工作票编号
		 */
    	private String machineCode;
		/**
		 * 签发日期（签发）
		 */
    	private Date signerDate;
		/**
		 * 延期时间
		 */
    	private Date delayDate;
		/**
		 * 终结台数
		 */
    	private Long endStand;
		/**
		 * 工作票签发人名字
		 */
    	private String changeSignerName;
		/**
		 * 机械工作票负责人ID
		 */
    	private Long machinePersonId;
		/**
		 * 电气工作票负责人名字
		 */
    	private String electricPersonName;
		/**
		 * 变更后工作负责人ID
		 */
    	private Long changeNewPicId;
		/**
		 * 运行工作许可人ID
		 */
    	private Long runAllowId;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 原工作负责人ID
		 */
    	private Long changeOldPicId;
		/**
		 * 运行后工作许可人ID
		 */
    	private Long stopAllowId;
		/**
		 * 工作负责人名字（许可人）
		 */
    	private String allowPicPersonName;
		/**
		 * 运行值班人员名字（收票）
		 */
    	private String ondutyName;
		/**
		 * 运行工作许可人名字
		 */
    	private String runAllowName;
		/**
		 * 工作许可人确认日期
		 */
    	private Date changeAllowDate;
		/**
		 * 终结组编号
		 */
    	private String endGroupIndex;
		/**
		 * 签发人确认日期
		 */
    	private Date changeSignerDate;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 批准工作时间结束(批准人）
		 */
    	private Date approveEndtime;
		/**
		 * 值班长姓名(批准人）
		 */
    	private String dutyMonitorName;
		/**
		 * 热控工作负责人名字
		 */
    	private String heatPersonName;
		/**
		 * 修改时间
		 */
    	private Date updateDate;
		/**
		 * 工作负责人ID（许可人）
		 */
    	private Long allowPicPersonId;
		/**
		 * 运行后工作许可人名字
		 */
    	private String stopAllowName;
		/**
		 * 延期工作负责人ID
		 */
    	private Long delayPicId;
		/**
		 * 运行工作负责人名字
		 */
    	private String runPicName;
		/**
		 * 值长ID（许可人）
		 */
    	private Long allowMonitorId;
		/**
		 * 其他工作票收回
		 */
    	private String recoverOther;
		/**
		 * 终结组
		 */
    	private Long endGroup;
		/**
		 * 其他事项（备注）
		 */
    	private String remarkOther;
		/**
		 * 工作许可人名字
		 */
    	private String changeAllowName;
		/**
		 * 电气工作票编号
		 */
    	private String electricCode;
		/**
		 * 运行确认时间
		 */
    	private Date runSureDate;
		/**
		 * 运行后确认时间
		 */
    	private Date stopSureDate;
		/**
		 * 运行工作负责人ID
		 */
    	private Long runPicId;
		/**
		 * 负责监护(地点及具体工作)
		 */
    	private String remarkResponsibleName;
		/**
		 * 机械工作票负责人名字
		 */
    	private String machinePersonName;
		/**
		 * 工作票签发人ID
		 */
    	private Long changeSignerId;
    	/**
		 * 工作条件
		 */
    	private String workCondition;
    	/**
		 * 工作条件
		 */
    	private String workConditionTwo;
		/**
		 * 
		 */
    	private Long runManagerId;
    	
    	/**
		 * 
		 */
    	private String runManagerName;
    	
    	/**
		 * 
		 */
    	private Long stopManagerId;
    	
    	/**
		 * 
		 */
    	private String stopManagerName;
    	/**
		 * 许可时间  为主表服务
		 */
    	private Date qksjZhu;
    	/**
		 * 终结的负责人和许可人  为主表服务
		 */
    	private Long endPicIdZhu;
    	private String endPicNameZhu;
    	private Long endAllowIdZhu;
    	private String endAllowNameZhu;
    	private Date endTimeZhu;
    	/**
		 * 审批按钮的标示
		 */
    	private String  spFlag;
    	
    	//（审批意见）
    	private String approveIdea;
    	
    	private String selectUser;
    	
    	private String otherSafe ;
    	
    	private String workDisclosure;
    	
    	private String handFlag;
    	
    	private String hand;
    	
    	private String wireway;
    	
    	private String quarantine;
    	
    	private String other;
    	
    	public String getOtherSafe() {
			return otherSafe;
		}
		public void setOtherSafe(String otherSafe) {
			this.otherSafe = otherSafe;
		}
		public String getWorkDisclosure() {
			return workDisclosure;
		}
		public void setWorkDisclosure(String workDisclosure) {
			this.workDisclosure = workDisclosure;
		}
		public String getHandFlag() {
			return handFlag;
		}
		public void setHandFlag(String handFlag) {
			this.handFlag = handFlag;
		}
		public String getHand() {
			return hand;
		}
		public void setHand(String hand) {
			this.hand = hand;
		}
		public String getWireway() {
			return wireway;
		}
		public void setWireway(String wireway) {
			this.wireway = wireway;
		}
		public String getQuarantine() {
			return quarantine;
		}
		public void setQuarantine(String quarantine) {
			this.quarantine = quarantine;
		}
		public String getOther() {
			return other;
		}
		public void setOther(String other) {
			this.other = other;
		}
		public String getSelectUser() {
			return selectUser;
		}
		public void setSelectUser(String selectUser) {
			this.selectUser = selectUser;
		}
		@JsonSerialize(using = JsonDateTime1Serializer.class)
	    public Date getEndTimeZhu() {
			return endTimeZhu;
		}
    	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setEndTimeZhu(Date endTimeZhu) {
			this.endTimeZhu = endTimeZhu;
		}
		public String getEndPicNameZhu() {
			return endPicNameZhu;
		}
		public void setEndPicNameZhu(String endPicNameZhu) {
			this.endPicNameZhu = endPicNameZhu;
		}
		
		public Long getEndPicIdZhu() {
			return endPicIdZhu;
		}
		public void setEndPicIdZhu(Long endPicIdZhu) {
			this.endPicIdZhu = endPicIdZhu;
		}
		public Long getEndAllowIdZhu() {
			return endAllowIdZhu;
		}
		public void setEndAllowIdZhu(Long endAllowIdZhu) {
			this.endAllowIdZhu = endAllowIdZhu;
		}
		public String getEndAllowNameZhu() {
			return endAllowNameZhu;
		}
		public void setEndAllowNameZhu(String endAllowNameZhu) {
			this.endAllowNameZhu = endAllowNameZhu;
		}
		public Long getRunManagerId() {
			return runManagerId;
		}
		public void setRunManagerId(Long runManagerId) {
			this.runManagerId = runManagerId;
		}
		public String getRunManagerName() {
			return runManagerName;
		}
		public void setRunManagerName(String runManagerName) {
			this.runManagerName = runManagerName;
		}
		public Long getStopManagerId() {
			return stopManagerId;
		}
		public void setStopManagerId(Long stopManagerId) {
			this.stopManagerId = stopManagerId;
		}
		public String getStopManagerName() {
			return stopManagerName;
		}
		public void setStopManagerName(String stopManagerName) {
			this.stopManagerName = stopManagerName;
		}
		public String getSpFlag() {
			return spFlag;
		}
		public void setSpFlag(String spFlag) {
			this.spFlag = spFlag;
		}
		@JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getApproveStarttime(){
			return approveStarttime;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setApproveStarttime(Date approveStarttime){
			this.approveStarttime = approveStarttime;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getGetticketTime(){
			return getticketTime;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setGetticketTime(Date getticketTime){
			this.getticketTime = getticketTime;
		}
		public Long getDelayDutyMonitorId(){
			return delayDutyMonitorId;
		}
		public void setDelayDutyMonitorId(Long delayDutyMonitorId){
			this.delayDutyMonitorId = delayDutyMonitorId;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getEndPicDate(){
			return endPicDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setEndPicDate(Date endPicDate){
			this.endPicDate = endPicDate;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getDelayPicSureDate(){
			return delayPicSureDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setDelayPicSureDate(Date delayPicSureDate){
			this.delayPicSureDate = delayPicSureDate;
		}
		public String getChangeOldPicName(){
			return changeOldPicName;
		}
		public void setChangeOldPicName(String changeOldPicName){
			this.changeOldPicName = changeOldPicName;
		}
		public Long getSignerId(){
			return signerId;
		}
		public void setSignerId(Long signerId){
			this.signerId = signerId;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getDelayAllowSureDate(){
			return delayAllowSureDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setDelayAllowSureDate(Date delayAllowSureDate){
			this.delayAllowSureDate = delayAllowSureDate;
		}
		public String getDelayDutyMonitorName(){
			return delayDutyMonitorName;
		}
		public void setDelayDutyMonitorName(String delayDutyMonitorName){
			this.delayDutyMonitorName = delayDutyMonitorName;
		}
		public String getAllowMonitorName(){
			return allowMonitorName;
		}
		public void setAllowMonitorName(String allowMonitorName){
			this.allowMonitorName = allowMonitorName;
		}
		public Long getWorkticketId(){
			return workticketId;
		}
		public void setWorkticketId(Long workticketId){
			this.workticketId = workticketId;
		}
		public String getDelayPicName(){
			return delayPicName;
		}
		public void setDelayPicName(String delayPicName){
			this.delayPicName = delayPicName;
		}
		public Long getRemarkGuarderId(){
			return remarkGuarderId;
		}
		public void setRemarkGuarderId(Long remarkGuarderId){
			this.remarkGuarderId = remarkGuarderId;
		}
		public String getEndStandIndex(){
			return endStandIndex;
		}
		public void setEndStandIndex(String endStandIndex){
			this.endStandIndex = endStandIndex;
		}
		public String getWorkPersonPicName(){
			return workPersonPicName;
		}
		public void setWorkPersonPicName(String workPersonPicName){
			this.workPersonPicName = workPersonPicName;
		}
		public String getRemarkGuarderName(){
			return remarkGuarderName;
		}
		public void setRemarkGuarderName(String remarkGuarderName){
			this.remarkGuarderName = remarkGuarderName;
		}
		public Long getHeatPersonId(){
			return heatPersonId;
		}
		public void setHeatPersonId(Long heatPersonId){
			this.heatPersonId = heatPersonId;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
		public Long getElectricPersonId(){
			return electricPersonId;
		}
		public void setElectricPersonId(Long electricPersonId){
			this.electricPersonId = electricPersonId;
		}
		public Long getStopPicId(){
			return stopPicId;
		}
		public void setStopPicId(Long stopPicId){
			this.stopPicId = stopPicId;
		}
		public String getStopPicName(){
			return stopPicName;
		}
		public void setStopPicName(String stopPicName){
			this.stopPicName = stopPicName;
		}
		public String getSignerName(){
			return signerName;
		}
		public void setSignerName(String signerName){
			this.signerName = signerName;
		}
		public Long getDelayAllowId(){
			return delayAllowId;
		}
		public void setDelayAllowId(Long delayAllowId){
			this.delayAllowId = delayAllowId;
		}
		public Long getOndutyId(){
			return ondutyId;
		}
		public void setOndutyId(Long ondutyId){
			this.ondutyId = ondutyId;
		}
		public String getWorkPersonGroup(){
			return workPersonGroup;
		}
		public void setWorkPersonGroup(String workPersonGroup){
			this.workPersonGroup = workPersonGroup;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getEndAllowDate(){
			return endAllowDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setEndAllowDate(Date endAllowDate){
			this.endAllowDate = endAllowDate;
		}
		public String getChangeNewPicName(){
			return changeNewPicName;
		}
		public void setChangeNewPicName(String changeNewPicName){
			this.changeNewPicName = changeNewPicName;
		}
		public String getDelayAllowName(){
			return delayAllowName;
		}
		public void setDelayAllowName(String delayAllowName){
			this.delayAllowName = delayAllowName;
		}
		public Long getWorkPersonPicId(){
			return workPersonPicId;
		}
		public void setWorkPersonPicId(Long workPersonPicId){
			this.workPersonPicId = workPersonPicId;
		}
		public String getHeatCode(){
			return heatCode;
		}
		public void setHeatCode(String heatCode){
			this.heatCode = heatCode;
		}
		public Long getDutyMonitorId(){
			return dutyMonitorId;
		}
		public void setDutyMonitorId(Long dutyMonitorId){
			this.dutyMonitorId = dutyMonitorId;
		}
		public Long getChangeAllowId(){
			return changeAllowId;
		}
		public void setChangeAllowId(Long changeAllowId){
			this.changeAllowId = changeAllowId;
		}
		public String getMachineCode(){
			return machineCode;
		}
		public void setMachineCode(String machineCode){
			this.machineCode = machineCode;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getSignerDate(){
			return signerDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setSignerDate(Date signerDate){
			this.signerDate = signerDate;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getDelayDate(){
			return delayDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setDelayDate(Date delayDate){
			this.delayDate = delayDate;
		}
		public Long getEndStand(){
			return endStand;
		}
		public void setEndStand(Long endStand){
			this.endStand = endStand;
		}
		public String getChangeSignerName(){
			return changeSignerName;
		}
		public void setChangeSignerName(String changeSignerName){
			this.changeSignerName = changeSignerName;
		}
		public Long getMachinePersonId(){
			return machinePersonId;
		}
		public void setMachinePersonId(Long machinePersonId){
			this.machinePersonId = machinePersonId;
		}
		public String getElectricPersonName(){
			return electricPersonName;
		}
		public void setElectricPersonName(String electricPersonName){
			this.electricPersonName = electricPersonName;
		}
		public Long getChangeNewPicId(){
			return changeNewPicId;
		}
		public void setChangeNewPicId(Long changeNewPicId){
			this.changeNewPicId = changeNewPicId;
		}
		public Long getRunAllowId(){
			return runAllowId;
		}
		public void setRunAllowId(Long runAllowId){
			this.runAllowId = runAllowId;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
		public Long getChangeOldPicId(){
			return changeOldPicId;
		}
		public void setChangeOldPicId(Long changeOldPicId){
			this.changeOldPicId = changeOldPicId;
		}
		public Long getStopAllowId(){
			return stopAllowId;
		}
		public void setStopAllowId(Long stopAllowId){
			this.stopAllowId = stopAllowId;
		}
		public String getAllowPicPersonName(){
			return allowPicPersonName;
		}
		public void setAllowPicPersonName(String allowPicPersonName){
			this.allowPicPersonName = allowPicPersonName;
		}
		public String getOndutyName(){
			return ondutyName;
		}
		public void setOndutyName(String ondutyName){
			this.ondutyName = ondutyName;
		}
		public String getRunAllowName(){
			return runAllowName;
		}
		public void setRunAllowName(String runAllowName){
			this.runAllowName = runAllowName;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getChangeAllowDate(){
			return changeAllowDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setChangeAllowDate(Date changeAllowDate){
			this.changeAllowDate = changeAllowDate;
		}
		public String getEndGroupIndex(){
			return endGroupIndex;
		}
		public void setEndGroupIndex(String endGroupIndex){
			this.endGroupIndex = endGroupIndex;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getChangeSignerDate(){
			return changeSignerDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setChangeSignerDate(Date changeSignerDate){
			this.changeSignerDate = changeSignerDate;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getApproveEndtime(){
			return approveEndtime;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setApproveEndtime(Date approveEndtime){
			this.approveEndtime = approveEndtime;
		}
		public String getDutyMonitorName(){
			return dutyMonitorName;
		}
		public void setDutyMonitorName(String dutyMonitorName){
			this.dutyMonitorName = dutyMonitorName;
		}
		public String getHeatPersonName(){
			return heatPersonName;
		}
		public void setHeatPersonName(String heatPersonName){
			this.heatPersonName = heatPersonName;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
		public Long getAllowPicPersonId(){
			return allowPicPersonId;
		}
		public void setAllowPicPersonId(Long allowPicPersonId){
			this.allowPicPersonId = allowPicPersonId;
		}
		public String getStopAllowName(){
			return stopAllowName;
		}
		public void setStopAllowName(String stopAllowName){
			this.stopAllowName = stopAllowName;
		}
		public Long getDelayPicId(){
			return delayPicId;
		}
		public void setDelayPicId(Long delayPicId){
			this.delayPicId = delayPicId;
		}
		public String getRunPicName(){
			return runPicName;
		}
		public void setRunPicName(String runPicName){
			this.runPicName = runPicName;
		}
		public Long getAllowMonitorId(){
			return allowMonitorId;
		}
		public void setAllowMonitorId(Long allowMonitorId){
			this.allowMonitorId = allowMonitorId;
		}
		public String getRecoverOther(){
			return recoverOther;
		}
		public void setRecoverOther(String recoverOther){
			this.recoverOther = recoverOther;
		}
		public Long getEndGroup(){
			return endGroup;
		}
		public void setEndGroup(Long endGroup){
			this.endGroup = endGroup;
		}
		public String getRemarkOther(){
			return remarkOther;
		}
		public void setRemarkOther(String remarkOther){
			this.remarkOther = remarkOther;
		}
		public String getChangeAllowName(){
			return changeAllowName;
		}
		public void setChangeAllowName(String changeAllowName){
			this.changeAllowName = changeAllowName;
		}
		public String getElectricCode(){
			return electricCode;
		}
		public void setElectricCode(String electricCode){
			this.electricCode = electricCode;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getRunSureDate(){
			return runSureDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setRunSureDate(Date runSureDate){
			this.runSureDate = runSureDate;
		}
	    
	    
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getStopSureDate(){
			return stopSureDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setStopSureDate(Date stopSureDate){
			this.stopSureDate = stopSureDate;
		}
		
		public Long getRunPicId(){
			return runPicId;
		}
		public void setRunPicId(Long runPicId){
			this.runPicId = runPicId;
		}
		public String getRemarkResponsibleName(){
			return remarkResponsibleName;
		}
		public void setRemarkResponsibleName(String remarkResponsibleName){
			this.remarkResponsibleName = remarkResponsibleName;
		}
		public String getMachinePersonName(){
			return machinePersonName;
		}
		public void setMachinePersonName(String machinePersonName){
			this.machinePersonName = machinePersonName;
		}
		public Long getChangeSignerId(){
			return changeSignerId;
		}
		public void setChangeSignerId(Long changeSignerId){
			this.changeSignerId = changeSignerId;
		}
		@JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getQksjZhu() {
			return qksjZhu;
		}
		@JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setQksjZhu(Date qksjZhu) {
			this.qksjZhu = qksjZhu;
		}
		
		public String getApproveIdea() {
			return approveIdea;
		}
		public void setApproveIdea(String approveIdea) {
			this.approveIdea = approveIdea;
		}
		public String getWorkCondition() {
			return workCondition;
		}
		public void setWorkCondition(String workCondition) {
			this.workCondition = workCondition;
		}
		public String getWorkConditionTwo() {
			return workConditionTwo;
		}
		public void setWorkConditionTwo(String workConditionTwo) {
			this.workConditionTwo = workConditionTwo;
		}
		public String getFileid() {
			return fileid;
		}
		public void setFileid(String fileid) {
			this.fileid = fileid;
		}
		public String getChangeAllowIdea() {
			return changeAllowIdea;
		}
		public void setChangeAllowIdea(String changeAllowIdea) {
			this.changeAllowIdea = changeAllowIdea;
		}
		
}