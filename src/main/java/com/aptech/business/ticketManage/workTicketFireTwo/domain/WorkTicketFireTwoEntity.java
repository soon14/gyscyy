package com.aptech.business.ticketManage.workTicketFireTwo.domain;

import java.util.Date;

import jodd.util.StringUtil;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.IdentifyEnum;
import com.aptech.business.component.dictionary.IssetEnum;

import com.aptech.business.component.dictionary.WorkFireStatusEnum;
import com.aptech.business.component.dictionary.WorkFireTwoStatusEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;

import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 工作票应用实体类
 *
 * @author 
 * @created 2017-06-02 11:50:39
 * @lastModified 
 * @history
 *
 */
@Alias("workTicketFireTwoEntity")
public class WorkTicketFireTwoEntity extends BaseEntity{
		
	private static final long serialVersionUID = -7951470398960804382L;
		
		/**
		 * 安全措施附页数量
		 */
    	private Long appendid;
		/**
		 * 工作负责人监护人ID
		 */
    	private Long guarderId;
		/**
		 * 工作负责人ID-终结
		 */
    	private Long endPicId;
		/**
		 * 单位名称
		 */
    	private String unitNameId;
    	/**
		 * 单位名称
		 */
    	private String unitName;
    	
		/**
		 * 工作票类型
		 */
    	private Long type;
		/**
		 * 工作票终结时间
		 */
    	private Date endTime;
		/**
		 * 主键
		 */
    	private Long id;
		/**
		 * 工作许可人确认日期
		 */
    	private Date changeAllowDate;
		/**
		 * 计划开始时间
		 */
    	private Date plandateStart;
		/**
		 * 创建时间
		 */
    	private Date createDate;
		/**
		 * 工作负责人(监护人)
		 */
    	private String guarderName;
		/**
		 * 创建人
		 */
    	private String createUserId;
		/**
		 * 工作班人数
		 */
    	private Long workClassPeople;
		/**
		 * 工作班成员
		 */
    	private String workClassMember;
		/**
		 * 是否被设置为典型票
		 */
    	private Long isSet;
		/**
		 * 是否删除
		 */
    	private Long status;
		/**
		 * 缺陷单编号
		 */
    	private String flawCode;
		/**
		 * 工作许可人名字-终结
		 */
    	private String endAllowName;
		/**
		 * 编码
		 */
    	private String code;
		/**
		 * 典型票，还是普通票
		 */
    	private Long istypical;
		/**
		 * 修改时间
		 */
    	private Date updateDate;
		/**
		 * 工作内容
		 */
    	private String content;
		/**
		 * 班组id
		 */
    	private Long groupId;
		/**
		 * 设备名称
		 */
    	private String equipmentName;
		/**
		 * 附件
		 */
    	private String annexId;
		/**
		 * 工作地点
		 */
    	private String address;
		/**
		 * 工作许可人ID-终结
		 */
    	private Long endAllowId;
		/**
		 * 工作负责人名字-终结
		 */
    	private String endPicName;
		/**
		 * 设备编号
		 */
    	private String equipmentCode;
		/**
		 * 工作许可人ID
		 */
    	private Long changeAllowId;
		/**
		 * 工作许可人名字
		 */
    	private String changeAllowName;
		/**
		 * 工作票状态
		 */
    	private Long workStatus;
		/**
		 * 计划终了时间
		 */
    	private Date plandateEnd;
    	
    	
    	private String groupName;
    	
    	private String invalidDate;
    	
    	private String invalidId;
    	
    	private String invalidContent;
    	/**
		 * 鉴定结果
		 */
    	private String identify;
    	/**
		 * 补充说明
		 */
    	private String cardSortDescription;
    	/**
		 * 作业类别
		 */
    	private String cardSort;
    	/**
		 * 作业类别
		 */
    	private String cardSortTwo;
    	/**
		 * 作业类别
		 */
    	private String cardSortThree;
    	/**
		 * 作业类别
		 */
    	private String cardSortFour;
    	
    	/**
		 * 指定专责监护人
		 */
    	private String remarkGuarderName;
    	/**
		 * 负责监护(地点及具体工作)
		 */
    	private String remarkResponsibleName;
    	/**
		 * 其他事项（备注）
		 */
    	private String remarkOther;
    	/**
		 * uuid   
		 */
    	private String uuidWorkTicket;
    	/**
		 * 从表的id   
		 */
    	private String electricId;
    	
    	/**
		 * 保存还是提交   
		 */
    	private String saveOrSubmit;
    	/**
		 * selectUser   审批人
		 */
    	private String selectUser;
    	
    	private String plandateStartString;
    	
    	private String plandateEndString;
    	
    	private String typicalTicketId;//典型票的id
    	
    	private String safeFlag;
    	
    	private String safeNum;
    	
    	private String changeAllowIdea;
    	private Date createTime;
		
		
		
		@JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getCreateTime() {
			return createTime;
		}
		@JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
    	
    	public String getTypicalTicketId() {
			return typicalTicketId;
		}

		public void setTypicalTicketId(String typicalTicketId) {
			this.typicalTicketId = typicalTicketId;
		}

		public String getPlandateStartString() {
			return plandateStartString;
		}

		public void setPlandateStartString(String plandateStartString) {
			this.plandateStartString = plandateStartString;
		}

		public String getPlandateEndString() {
			return plandateEndString;
		}

		public void setPlandateEndString(String plandateEndString) {
			this.plandateEndString = plandateEndString;
		}

		public String getSaveOrSubmit() {
			return saveOrSubmit;
		}

		public void setSaveOrSubmit(String saveOrSubmit) {
			this.saveOrSubmit = saveOrSubmit;
		}

		public String getSelectUser() {
			return selectUser;
		}

		public void setSelectUser(String selectUser) {
			this.selectUser = selectUser;
		}

		public String getElectricId() {
			return electricId;
		}

		public void setElectricId(String electricId) {
			this.electricId = electricId;
		}

		public String getUuidWorkTicket() {
			return uuidWorkTicket;
		}

		public void setUuidWorkTicket(String uuidWorkTicket) {
			this.uuidWorkTicket = uuidWorkTicket;
		}

		public String getCardSortDescription() {
			return cardSortDescription;
		}

		public void setCardSortDescription(String cardSortDescription) {
			this.cardSortDescription = cardSortDescription;
		}

		public String getCardSort() {
			return cardSort;
		}

		public void setCardSort(String cardSort) {
			this.cardSort = cardSort;
		}

		public String getCardSortTwo() {
			return cardSortTwo;
		}

		public void setCardSortTwo(String cardSortTwo) {
			this.cardSortTwo = cardSortTwo;
		}

		public String getCardSortThree() {
			return cardSortThree;
		}

		public void setCardSortThree(String cardSortThree) {
			this.cardSortThree = cardSortThree;
		}

		public String getCardSortFour() {
			return cardSortFour;
		}

		public void setCardSortFour(String cardSortFour) {
			this.cardSortFour = cardSortFour;
		}

		public String getInvalidDate() {
			return invalidDate;
		}

		public void setInvalidDate(String invalidDate) {
			this.invalidDate = invalidDate;
		}

		public String getInvalidId() {
			return invalidId;
		}

		public void setInvalidId(String invalidId) {
			this.invalidId = invalidId;
		}

		public String getInvalidContent() {
			return invalidContent;
		}

		public void setInvalidContent(String invalidContent) {
			this.invalidContent = invalidContent;
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}


		/**
		 * 是否是典型票 汉字
		 */
		public String getIsSetName() {
			for (IssetEnum issetEnum : IssetEnum.values()) {
				if (issetEnum.getCode().equals(this.isSet.toString())) {
					return issetEnum.getName();
				}
			}
			return null;
		}
		
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		/**
		 * 工作票状态名字
		 */
		public String getWorkStatusName() {
			for (WorkFireTwoStatusEnum workStatusEnum : WorkFireTwoStatusEnum.values()) {
				if (workStatusEnum.getCode().equals(this.workStatus.toString())) {
					return workStatusEnum.getName();
				}
			}
			return null;
		}
		
		public Long getAppendid(){
			return appendid;
		}
		public void setAppendid(Long appendid){
			this.appendid = appendid;
		}
		public Long getGuarderId(){
			return guarderId;
		}
		public void setGuarderId(Long guarderId){
			this.guarderId = guarderId;
		}
		public Long getEndPicId(){
			return endPicId;
		}
		public void setEndPicId(Long endPicId){
			this.endPicId = endPicId;
		}
		public String getUnitNameId(){
			return unitNameId;
		}
		public void setUnitNameId(String unitNameId){
			this.unitNameId = unitNameId;
		}
		public Long getType(){
			return type;
		}
		public void setType(Long type){
			this.type = type;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getEndTime(){
			return endTime;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setEndTime(Date endTime){
			this.endTime = endTime;
		}
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id = id;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getChangeAllowDate(){
			return changeAllowDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setChangeAllowDate(Date changeAllowDate){
			this.changeAllowDate = changeAllowDate;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getPlandateStart(){
			return plandateStart;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setPlandateStart(Date plandateStart){
			this.plandateStart = plandateStart;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getCreateDate(){
			return createDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setCreateDate(Date createDate){
			this.createDate = createDate;
		}
		public String getGuarderName(){
			return guarderName;
		}
		public void setGuarderName(String guarderName){
			this.guarderName = guarderName;
		}
		public String getCreateUserId(){
			return createUserId;
		}
		public void setCreateUserId(String createUserId){
			this.createUserId = createUserId;
		}
		public Long getWorkClassPeople(){
			return workClassPeople;
		}
		public void setWorkClassPeople(Long workClassPeople){
			this.workClassPeople = workClassPeople;
		}
		public String getWorkClassMember(){
			return workClassMember;
		}
		public void setWorkClassMember(String workClassMember){
			this.workClassMember = workClassMember;
		}
		public Long getIsSet(){
			return isSet;
		}
		public void setIsSet(Long isSet){
			this.isSet = isSet;
		}
		public Long getStatus(){
			return status;
		}
		public void setStatus(Long status){
			this.status = status;
		}
		public String getFlawCode(){
			return flawCode;
		}
		public void setFlawCode(String flawCode){
			this.flawCode = flawCode;
		}
		public String getEndAllowName(){
			return endAllowName;
		}
		public void setEndAllowName(String endAllowName){
			this.endAllowName = endAllowName;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code = code;
		}
		public Long getIstypical(){
			return istypical;
		}
		public void setIstypical(Long istypical){
			this.istypical = istypical;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getUpdateDate(){
			return updateDate;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setUpdateDate(Date updateDate){
			this.updateDate = updateDate;
		}
		public String getContent(){
			return content;
		}
		public void setContent(String content){
			this.content = content;
		}
		public Long getGroupId(){
			return groupId;
		}
		public void setGroupId(Long groupId){
			this.groupId = groupId;
		}
		public String getEquipmentName(){
			return equipmentName;
		}
		public void setEquipmentName(String equipmentName){
			this.equipmentName = equipmentName;
		}
		public String getAnnexId(){
			return annexId;
		}
		public void setAnnexId(String annexId){
			this.annexId = annexId;
		}
		public String getAddress(){
			return address;
		}
		public void setAddress(String address){
			this.address = address;
		}
		public Long getEndAllowId(){
			return endAllowId;
		}
		public void setEndAllowId(Long endAllowId){
			this.endAllowId = endAllowId;
		}
		public String getEndPicName(){
			return endPicName;
		}
		public void setEndPicName(String endPicName){
			this.endPicName = endPicName;
		}
		public String getEquipmentCode(){
			return equipmentCode;
		}
		public void setEquipmentCode(String equipmentCode){
			this.equipmentCode = equipmentCode;
		}
		public Long getChangeAllowId(){
			return changeAllowId;
		}
		public void setChangeAllowId(Long changeAllowId){
			this.changeAllowId = changeAllowId;
		}
		public String getChangeAllowName(){
			return changeAllowName;
		}
		public void setChangeAllowName(String changeAllowName){
			this.changeAllowName = changeAllowName;
		}
		public Long getWorkStatus(){
			return workStatus;
		}
		public void setWorkStatus(Long workStatus){
			this.workStatus = workStatus;
		}
	    @JsonSerialize(using = JsonDateTime1Serializer.class)
		public Date getPlandateEnd(){
			return plandateEnd;
		}
	    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
		public void setPlandateEnd(Date plandateEnd){
			this.plandateEnd = plandateEnd;
		}

		public String getIdentify() {
			return identify;
		}

		public void setIdentify(String identify) {
			this.identify = identify;
		}
		
		public String getIdentifyName() {
			for (IdentifyEnum identifyEnumEnum : IdentifyEnum.values()) {
				if(this.identify!=null){
					if (identifyEnumEnum.getCode().equals(this.identify.toString())) {
						return identifyEnumEnum.getName();
					}
				}else{
					return "";
				}
			}
			return null;
		}

		public String getRemarkGuarderName() {
			return remarkGuarderName;
		}

		public void setRemarkGuarderName(String remarkGuarderName) {
			this.remarkGuarderName = remarkGuarderName;
		}

		public String getRemarkResponsibleName() {
			return remarkResponsibleName;
		}

		public void setRemarkResponsibleName(String remarkResponsibleName) {
			this.remarkResponsibleName = remarkResponsibleName;
		}

		public String getRemarkOther() {
			return remarkOther;
		}

		public void setRemarkOther(String remarkOther) {
			this.remarkOther = remarkOther;
		}

		public String getSafeFlag() {
			return safeFlag;
		}

		public void setSafeFlag(String safeFlag) {
			this.safeFlag = safeFlag;
		}

		public String getSafeNum() {
			return safeNum;
		}

		public void setSafeNum(String safeNum) {
			this.safeNum = safeNum;
		}

		public String getChangeAllowIdea() {
			if(StringUtil.isEmpty(this.changeAllowIdea)){
				return "[]";
			}
			return changeAllowIdea;
		}

		public void setChangeAllowIdea(String changeAllowIdea) {
			this.changeAllowIdea = changeAllowIdea;
		}
		
		
		
}