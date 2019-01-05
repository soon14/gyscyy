package com.aptech.business.ticketManage.workFireTwo.domain;

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
@Alias("workFireTwoEntity")
public class WorkFireTwoEntity extends BaseEntity{
    /**
     * 安全措施附页数量
     */
    private Long workticketId;
    /**
     * 工作负责人监护人ID
     */
    private Long otherExecutorId;
    /**
     * 工作负责人ID-终结
     */
    private Long otherApproveFireId;
    /**
     * 单位名称
     */
    private Long otherApproveSafeId;
    /**
     * 单位名称
     */
    private Long signerId;
    
    /**
     * 工作票类型
     */
    private Long dutyMonitorId;
    /**
     * 工作票终结时间
     */
    private Date signerDate;
    /**
     * 主键
     */
    private Long otherExecutorSignId;
    /**
     * 工作许可人确认日期
     */
    private Date approveStarttime;
    /**
     * 计划开始时间
     */
    private Date approveEndtime;

    /**
     * 工作负责人(监护人)
     */
    private String otherExecutor;
    /**
     * 创建人
     */
    private String otherCode;
    /**
     * 工作班人数
     */
    private Long otherFireSignId;
    /**
     * 工作班成员
     */
    private String otherExecutorTwo;
    /**
     * 是否被设置为典型票
     */
    private Long otherPicSignId;
    /**
     * 是否删除
     */
    private Long otherGroupSignId;
    /**
     * 缺陷单编号
     */
    private String otherCodeTwo;
    /**
     * 工作许可人名字-终结
     */
    private String otherExecutorThree;
    /**
     * 编码
     */
    private String otherCodeThree;
    /**
     * 典型票，还是普通票
     */
    private Long otherSafeSignId;

    /**
     * 工作内容
     */
    private String otherStyle;
    /**
     * 班组id
     */
    private Long otherLederSignId;
    /**
     * 设备名称
     */
    private String otherStyleOther;
    /**
     * 附件
     */
    private String otherApproveFire;
    /**
     * 工作地点
     */
    private String otherApproveSafe;
    /**
     * 工作许可人ID-终结
     */
    private Long otherendExecutorSignId;
    /**
     * 工作负责人名字-终结
     */
    private String signerName;
    /**
     * 运行值班人
     */
    private String dutyMonitorName;
    
    private Date dutyMonitorDate;
    /**
     * 工作许可人ID
     */
    private Long otherendFireSignId;
    /**
     * 工作许可人名字
     */
    private String otherInstrument;


    
    
    private String otherExecutorSign;
    
    private String otherFireSign;//消防监护人确认措施
    
    private String otherPicSign;
    
    private String otherGroupSign;
    /**
     * 鉴定结果
     */
    private String otherSafeSign;
    /**
     * 补充说明
     */
    private String otherLederSign;
    /**
     * 动火执行人(终结)
     */
    private String otherendExecutorSign;
    /**
     * 作业类别
     */
    private String otherendFireSign;
    /**
     * 作业类别
     */
    private String remark;
    /**
     * 作业类别
     */
    private String other;
    /**
     * 审批按钮的标示
     */
    private String  spFlag;
    
    //（审批意见）
    private String approveIdea;
    
    private String selectUser;
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
	
    /**
     * 允许动火时间
     */
    private Date otherAllowDate;
    private Date endTimeZhu;
    private  String otherStyleName;
    
    
	private Long fireUserId;//动火负责人
	private String fireUserName;
	
	private Long meetSignId; //会签人
	private String meetSignName;
	private Date meetSignDate;
	
	private Date otherFireSignDate;//消防监护人时间
	private Date otherApproveFireDate;//审批时消防监护时间
	private Date otherApproveSafeDate; //安监部门负责人时间
	
	private Long runFireSignId;
	private String runFireSignName;//许可—— 消防监护人
	
	private Long endStand;
	private String endStandIndex;
	private String endHand;
	private String workDisclosure;//工作交底
    
	private String fileid;
	
	/**
	 * 工作负责人名字（许可人）
	 */
	private String allowPicPersonName;
	
	/**
	 * 工作负责人ID（许可人）
	 */
	private Long allowPicPersonId;
	
	public String getAllowPicPersonName() {
		return allowPicPersonName;
	}

	public void setAllowPicPersonName(String allowPicPersonName) {
		this.allowPicPersonName = allowPicPersonName;
	}

	public Long getAllowPicPersonId() {
		return allowPicPersonId;
	}

	public void setAllowPicPersonId(Long allowPicPersonId) {
		this.allowPicPersonId = allowPicPersonId;
	}

	public Long getEndPicIdZhu() {
		return endPicIdZhu;
	}

	public void setEndPicIdZhu(Long endPicIdZhu) {
		this.endPicIdZhu = endPicIdZhu;
	}

	public String getEndPicNameZhu() {
		return endPicNameZhu;
	}

	public void setEndPicNameZhu(String endPicNameZhu) {
		this.endPicNameZhu = endPicNameZhu;
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
    
	public String getWorkDisclosure() {
		return workDisclosure;
	}

	public void setWorkDisclosure(String workDisclosure) {
		this.workDisclosure = workDisclosure;
	}

	public Date getDutyMonitorDate() {
		return dutyMonitorDate;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setDutyMonitorDate(Date dutyMonitorDate) {
		this.dutyMonitorDate = dutyMonitorDate;
	}

	public Long getFireUserId() {
		return fireUserId;
	}

	public void setFireUserId(Long fireUserId) {
		this.fireUserId = fireUserId;
	}

	public String getFireUserName() {
		return fireUserName;
	}

	public void setFireUserName(String fireUserName) {
		this.fireUserName = fireUserName;
	}

	public Long getMeetSignId() {
		return meetSignId;
	}

	public void setMeetSignId(Long meetSignId) {
		this.meetSignId = meetSignId;
	}

	public String getMeetSignName() {
		return meetSignName;
	}

	public void setMeetSignName(String meetSignName) {
		this.meetSignName = meetSignName;
	}

	public Date getMeetSignDate() {
		return meetSignDate;
	}
	 @JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setMeetSignDate(Date meetSignDate) {
		this.meetSignDate = meetSignDate;
	}

	public Date getOtherFireSignDate() {
		return otherFireSignDate;
	}
	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setOtherFireSignDate(Date otherFireSignDate) {
		this.otherFireSignDate = otherFireSignDate;
	}

	public Date getOtherApproveFireDate() {
		return otherApproveFireDate;
	}
	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setOtherApproveFireDate(Date otherApproveFireDate) {
		this.otherApproveFireDate = otherApproveFireDate;
	}

	public Date getOtherApproveSafeDate() {
		return otherApproveSafeDate;
	}
	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setOtherApproveSafeDate(Date otherApproveSafeDate) {
		this.otherApproveSafeDate = otherApproveSafeDate;
	}

	public Long getRunFireSignId() {
		return runFireSignId;
	}

	public void setRunFireSignId(Long runFireSignId) {
		this.runFireSignId = runFireSignId;
	}

	public String getRunFireSignName() {
		return runFireSignName;
	}

	public void setRunFireSignName(String runFireSignName) {
		this.runFireSignName = runFireSignName;
	}

	public Long getEndStand() {
		return endStand;
	}

	public void setEndStand(Long endStand) {
		this.endStand = endStand;
	}

	public String getEndStandIndex() {
		return endStandIndex;
	}

	public void setEndStandIndex(String endStandIndex) {
		this.endStandIndex = endStandIndex;
	}

	public String getEndHand() {
		return endHand;
	}

	public void setEndHand(String endHand) {
		this.endHand = endHand;
	}

	public String getOtherStyleName() {
		return otherStyleName;
	}

	public void setOtherStyleName(String otherStyleName) {
		this.otherStyleName = otherStyleName;
	}

    public String getSpFlag()
    {
        return spFlag;
    }
    public void setSpFlag(String spFlag)
    {
        this.spFlag = spFlag;
    }
    public String getApproveIdea()
    {
        return approveIdea;
    }
    public void setApproveIdea(String approveIdea)
    {
        this.approveIdea = approveIdea;
    }
    public String getSelectUser()
    {
        return selectUser;
    }
    public void setSelectUser(String selectUser)
    {
        this.selectUser = selectUser;
    }
    public Long getWorkticketId()
    {
        return workticketId;
    }
    public void setWorkticketId(Long workticketId)
    {
        this.workticketId = workticketId;
    }
    public Long getOtherExecutorId()
    {
        return otherExecutorId;
    }
    public void setOtherExecutorId(Long otherExecutorId)
    {
        this.otherExecutorId = otherExecutorId;
    }
    public Long getOtherApproveFireId()
    {
        return otherApproveFireId;
    }
    public void setOtherApproveFireId(Long otherApproveFireId)
    {
        this.otherApproveFireId = otherApproveFireId;
    }
    public Long getOtherApproveSafeId()
    {
        return otherApproveSafeId;
    }
    public void setOtherApproveSafeId(Long otherApproveSafeId)
    {
        this.otherApproveSafeId = otherApproveSafeId;
    }
    public Long getSignerId()
    {
        return signerId;
    }
    public void setSignerId(Long signerId)
    {
        this.signerId = signerId;
    }
    public Long getDutyMonitorId()
    {
        return dutyMonitorId;
    }
    public void setDutyMonitorId(Long dutyMonitorId)
    {
        this.dutyMonitorId = dutyMonitorId;
    }
    public Date getSignerDate()
    {
        return signerDate;
    }
    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
    public void setSignerDate(Date signerDate)
    {
        this.signerDate = signerDate;
    }
    public Long getOtherExecutorSignId()
    {
        return otherExecutorSignId;
    }
    public void setOtherExecutorSignId(Long otherExecutorSignId)
    {
        this.otherExecutorSignId = otherExecutorSignId;
    }
    public Date getApproveStarttime()
    {
        return approveStarttime;
    }
    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
    public void setApproveStarttime(Date approveStarttime)
    {
        this.approveStarttime = approveStarttime;
    }
    public Date getApproveEndtime()
    {
        return approveEndtime;
    }
    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
    public void setApproveEndtime(Date approveEndtime)
    {
        this.approveEndtime = approveEndtime;
    }
    public String getOtherExecutor()
    {
        return otherExecutor;
    }
    public void setOtherExecutor(String otherExecutor)
    {
        this.otherExecutor = otherExecutor;
    }
    public String getOtherCode()
    {
        return otherCode;
    }
    public void setOtherCode(String otherCode)
    {
        this.otherCode = otherCode;
    }
    public Long getOtherFireSignId()
    {
        return otherFireSignId;
    }
    public void setOtherFireSignId(Long otherFireSignId)
    {
        this.otherFireSignId = otherFireSignId;
    }
    public String getOtherExecutorTwo()
    {
        return otherExecutorTwo;
    }
    public void setOtherExecutorTwo(String otherExecutorTwo)
    {
        this.otherExecutorTwo = otherExecutorTwo;
    }
    public Long getOtherPicSignId()
    {
        return otherPicSignId;
    }
    public void setOtherPicSignId(Long otherPicSignId)
    {
        this.otherPicSignId = otherPicSignId;
    }
    public Long getOtherGroupSignId()
    {
        return otherGroupSignId;
    }
    public void setOtherGroupSignId(Long otherGroupSignId)
    {
        this.otherGroupSignId = otherGroupSignId;
    }
    public String getOtherCodeTwo()
    {
        return otherCodeTwo;
    }
    public void setOtherCodeTwo(String otherCodeTwo)
    {
        this.otherCodeTwo = otherCodeTwo;
    }
    public String getOtherExecutorThree()
    {
        return otherExecutorThree;
    }
    public void setOtherExecutorThree(String otherExecutorThree)
    {
        this.otherExecutorThree = otherExecutorThree;
    }
    public String getOtherCodeThree()
    {
        return otherCodeThree;
    }
    public void setOtherCodeThree(String otherCodeThree)
    {
        this.otherCodeThree = otherCodeThree;
    }
    public Long getOtherSafeSignId()
    {
        return otherSafeSignId;
    }
    public void setOtherSafeSignId(Long otherSafeSignId)
    {
        this.otherSafeSignId = otherSafeSignId;
    }
    public String getOtherStyle()
    {
        return otherStyle;
    }
    public void setOtherStyle(String otherStyle)
    {
        this.otherStyle = otherStyle;
    }
    public Long getOtherLederSignId()
    {
        return otherLederSignId;
    }
    public void setOtherLederSignId(Long otherLederSignId)
    {
        this.otherLederSignId = otherLederSignId;
    }
    public String getOtherStyleOther()
    {
        return otherStyleOther;
    }
    public void setOtherStyleOther(String otherStyleOther)
    {
        this.otherStyleOther = otherStyleOther;
    }
    public String getOtherApproveFire()
    {
        return otherApproveFire;
    }
    public void setOtherApproveFire(String otherApproveFire)
    {
        this.otherApproveFire = otherApproveFire;
    }
    public String getOtherApproveSafe()
    {
        return otherApproveSafe;
    }
    public void setOtherApproveSafe(String otherApproveSafe)
    {
        this.otherApproveSafe = otherApproveSafe;
    }
    public Long getOtherendExecutorSignId()
    {
        return otherendExecutorSignId;
    }
    public void setOtherendExecutorSignId(Long otherendExecutorSignId)
    {
        this.otherendExecutorSignId = otherendExecutorSignId;
    }
    public String getSignerName()
    {
        return signerName;
    }
    public void setSignerName(String signerName)
    {
        this.signerName = signerName;
    }
    public String getDutyMonitorName()
    {
        return dutyMonitorName;
    }
    public void setDutyMonitorName(String dutyMonitorName)
    {
        this.dutyMonitorName = dutyMonitorName;
    }
    public Long getOtherendFireSignId()
    {
        return otherendFireSignId;
    }
    public void setOtherendFireSignId(Long otherendFireSignId)
    {
        this.otherendFireSignId = otherendFireSignId;
    }
    public String getOtherInstrument()
    {
        return otherInstrument;
    }
    public void setOtherInstrument(String otherInstrument)
    {
        this.otherInstrument = otherInstrument;
    }
    public String getOtherExecutorSign()
    {
        return otherExecutorSign;
    }
    public void setOtherExecutorSign(String otherExecutorSign)
    {
        this.otherExecutorSign = otherExecutorSign;
    }
    public String getOtherFireSign()
    {
        return otherFireSign;
    }
    public void setOtherFireSign(String otherFireSign)
    {
        this.otherFireSign = otherFireSign;
    }
    public String getOtherPicSign()
    {
        return otherPicSign;
    }
    public void setOtherPicSign(String otherPicSign)
    {
        this.otherPicSign = otherPicSign;
    }
    public String getOtherGroupSign()
    {
        return otherGroupSign;
    }
    public void setOtherGroupSign(String otherGroupSign)
    {
        this.otherGroupSign = otherGroupSign;
    }
    public String getOtherSafeSign()
    {
        return otherSafeSign;
    }
    public void setOtherSafeSign(String otherSafeSign)
    {
        this.otherSafeSign = otherSafeSign;
    }
    public String getOtherLederSign()
    {
        return otherLederSign;
    }
    public void setOtherLederSign(String otherLederSign)
    {
        this.otherLederSign = otherLederSign;
    }
    public String getOtherendExecutorSign()
    {
        return otherendExecutorSign;
    }
    public void setOtherendExecutorSign(String otherendExecutorSign)
    {
        this.otherendExecutorSign = otherendExecutorSign;
    }
    public String getOtherendFireSign()
    {
        return otherendFireSign;
    }
    public void setOtherendFireSign(String otherendFireSign)
    {
        this.otherendFireSign = otherendFireSign;
    }
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    public String getOther()
    {
        return other;
    }
    public void setOther(String other)
    {
        this.other = other;
    }
   
    @JsonSerialize(using = JsonDateTime1Serializer.class)
    public Date getQksjZhu() {
        return qksjZhu;
    }
    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
    public void setQksjZhu(Date qksjZhu) {
        this.qksjZhu = qksjZhu;
    }
    public Date getOtherAllowDate()
    {
        return otherAllowDate;
    }
    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
    public void setOtherAllowDate(Date otherAllowDate)
    {
        this.otherAllowDate = otherAllowDate;
    }
    @JsonSerialize(using = JsonDateTime1Serializer.class)
    public Date getEndTimeZhu() {
        return endTimeZhu;
    }
    @JsonDeserialize(using = JsonDateTime1Deserializer.class)
    public void setEndTimeZhu(Date endTimeZhu) {
        this.endTimeZhu = endTimeZhu;
    }

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
    
		
}