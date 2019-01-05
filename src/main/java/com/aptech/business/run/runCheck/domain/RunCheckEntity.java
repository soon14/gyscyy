package com.aptech.business.run.runCheck.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.IssetEnum;
import com.aptech.business.component.dictionary.RunCheckResultEnum;
import com.aptech.framework.json.JsonDateTime1Deserializer;
import com.aptech.framework.json.JsonDateTime1Serializer;
import com.aptech.framework.json.JsonDateTimeDeserializer;
import com.aptech.framework.json.JsonDateTimeSerializer;
import com.aptech.framework.orm.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 运行检查实体类
 *
 * @author
 * @created 2017-09-08 15:00:42
 * @lastModified
 * @history
 *
 */
@Alias("RunCheckEntity")
public class RunCheckEntity extends BaseEntity {
	/**
	 * 是否隐藏
	 */
	private String tdHide;
	/**
	 * 合并数
	 */
	private String rowspanNum;
	/**
	 * 运行方式code
	 */
	private Integer runTypeCode;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 名称
	 */
	private String typeName;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date updateDate;
	/**
	 * 创建人
	 */
	private String createUserId;
	/**
	 * 修改人
	 */
	private String updateUserId;
	/**
	 * 检查时间
	 */
	private Date checkDate;
	/**
	 * 检查人
	 */
	private Integer checkPerson;
	/**
	 * 意见和建议
	 */
	private String suggest;
	/**
	 * 导出专用
	 */
	private String checkDateString;
	/**
	 * 工作记录类型
	 */
	private Integer type;
	/**
	 * 运行日志ID
	 */
	private Integer rlId;
	/**
	 * 职务
	 */
	private String duties;
	/**
	 * 检查人姓名
	 */
	private String checkPersonName;
	/**以下字段为检查统计专用**/
	/**
	 * 检查结果(本月)
	 */
	private String checkResult;
	/**
	 * 记录次数(本月)
	 */
	private String recordCount;
	/**
	 * 检查数量(本月)
	 */
	private String checkRecordCount;

	/**
	 * 检查次数(本月)
	 */
	private String checkCount;

	/**
	 * 未检查次数(本月)
	 */
	private String unCheckCount;
	
	/**
	 * 合格数量(本月)
	 */
	private String qualiFiedCount;
	/**
	 * 不合格数量(本月)
	 */
	private String unQualiFiedCount;
	/**
	 * 检查率(本月)
	 */
	private String checkRate;
	/**
	 * 未检查率(本月)
	 */
	private String unCheckRate;
	/**
	 * 合格率(本月)
	 */
	private String qualiFiedRate;
	/**
	 * 未合格率(本月)
	 */
	private String unQualiFiedRate;
	/**
	 * 检查结果(1月-本月)
	 */
	private String oTcCheckResult;
	/**
	 * 记录次数(1月-本月)
	 */
	private String oTcRecordCount;
	/**
	 * 记录次数(1月-本月)
	 */
	private String oTcCheckRecordCount;
	/**
	 * 检查次数(1月-本月)
	 */
	private String oTcCheckCount;
	/**
	 * 未检查次数(1月-本月)
	 */
	private String oTcUnCheckCount;
	/**
	 * 合格数量(1月-本月)
	 */
	private String oTcQualiFiedCount;
	/**
	 * 不合格数量(1月-本月)
	 */
	private String oTcUnQualiFiedCount;
	/**
	 * 检查率(1月-本月)
	 */
	private String oTcCheckRate;
	/**
	 * 未检查率(1月-本月)
	 */
	private String oTcUnCheckRate;
	/**
	 * 合格率(1月-本月)
	 */
	private String oTcQualiFiedRate;
	/**
	 * 未合格率(1月-本月)
	 */
	private String oTcUnQualiFiedRate;
	/**
	 * 当前查询月份
	 */
	private String curMonth;
	/**
     * 1月份
	 */
	private String  january;
	/**
	 * 运行检查人单位
	 */
	private long userUnit;
	
	public long getUserUnit() {
		return userUnit;
	}

	public void setUserUnit(long userUnit) {
		this.userUnit = userUnit;
	}

	public Integer getRlId() {
		return rlId;
	}

	public void setRlId(Integer rlId) {
		this.rlId = rlId;
	}
	public String getCurMonth() {
		return curMonth;
	}

	public void setCurMonth(String curMonth) {
		this.curMonth = curMonth;
	}
	public String getJanuary() {
		return january;
	}
	
	public void setJanuary(String january) {
		this.january = january;
	}
	/**以上字段为检查统计专用**/
	public Integer getRunTypeCode() {
		return runTypeCode;
	}

	public void setRunTypeCode(Integer runTypeCode) {
		this.runTypeCode = runTypeCode;
	}
	public String getCheckRate() {
		return checkRate;
	}

	public void setCheckRate(String checkRate) {
		this.checkRate = checkRate;
	}

	public String getUnCheckRate() {
		return unCheckRate;
	}

	public void setUnCheckRate(String unCheckRate) {
		this.unCheckRate = unCheckRate;
	}

	public String getQualiFiedRate() {
		return qualiFiedRate;
	}

	public void setQualiFiedRate(String qualiFiedRate) {
		this.qualiFiedRate = qualiFiedRate;
	}

	public String getUnQualiFiedRate() {
		return unQualiFiedRate;
	}

	public void setUnQualiFiedRate(String unQualiFiedRate) {
		this.unQualiFiedRate = unQualiFiedRate;
	}
	
	public String getCheckRecordCount() {
		return checkRecordCount;
	}

	public void setCheckRecordCount(String checkRecordCount) {
		this.checkRecordCount = checkRecordCount;
	}

	public String getCheckPersonName() {
		return checkPersonName;
	}

	public void setCheckPersonName(String checkPersonName) {
		this.checkPersonName = checkPersonName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}

	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getUpdateDate() {
		return updateDate;
	}

	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	/**
	 * 是否是典型票 汉字
	 */
	public String getCheckResultName() {
		for (RunCheckResultEnum runCheckResultEnum : RunCheckResultEnum
				.values()) {
			if (runCheckResultEnum.getCode()
					.equals(this.checkResult.toString())) {
				return runCheckResultEnum.getName();
			}
		}
		return null;
	}

	@JsonSerialize(using = JsonDateTime1Serializer.class)
	public Date getCheckDate() {
		return checkDate;
	}

	@JsonDeserialize(using = JsonDateTime1Deserializer.class)
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Integer getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(Integer checkPerson) {
		this.checkPerson = checkPerson;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}
	public String getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}

	public String getCheckCount() {
		return checkCount;
	}

	public void setCheckCount(String checkCount) {
		this.checkCount = checkCount;
	}

	public String getUnCheckCount() {
		return unCheckCount;
	}

	public void setUnCheckCount(String unCheckCount) {
		this.unCheckCount = unCheckCount;
	}

	public String getQualiFiedCount() {
		return qualiFiedCount;
	}

	public void setQualiFiedCount(String qualiFiedCount) {
		this.qualiFiedCount = qualiFiedCount;
	}

	public String getUnQualiFiedCount() {
		return unQualiFiedCount;
	}

	public void setUnQualiFiedCount(String unQualiFiedCount) {
		this.unQualiFiedCount = unQualiFiedCount;
	}
	
	public String getoTcCheckResult() {
		return oTcCheckResult;
	}

	public void setoTcCheckResult(String oTcCheckResult) {
		this.oTcCheckResult = oTcCheckResult;
	}

	public String getoTcRecordCount() {
		return oTcRecordCount;
	}

	public void setoTcRecordCount(String oTcRecordCount) {
		this.oTcRecordCount = oTcRecordCount;
	}

	public String getoTcCheckCount() {
		return oTcCheckCount;
	}

	public void setoTcCheckCount(String oTcCheckCount) {
		this.oTcCheckCount = oTcCheckCount;
	}

	public String getoTcUnCheckCount() {
		return oTcUnCheckCount;
	}

	public void setoTcUnCheckCount(String oTcUnCheckCount) {
		this.oTcUnCheckCount = oTcUnCheckCount;
	}

	public String getoTcQualiFiedCount() {
		return oTcQualiFiedCount;
	}

	public void setoTcQualiFiedCount(String oTcQualiFiedCount) {
		this.oTcQualiFiedCount = oTcQualiFiedCount;
	}

	public String getoTcUnQualiFiedCount() {
		return oTcUnQualiFiedCount;
	}

	public void setoTcUnQualiFiedCount(String oTcUnQualiFiedCount) {
		this.oTcUnQualiFiedCount = oTcUnQualiFiedCount;
	}

	public String getoTcCheckRate() {
		return oTcCheckRate;
	}

	public void setoTcCheckRate(String oTcCheckRate) {
		this.oTcCheckRate = oTcCheckRate;
	}

	public String getoTcUnCheckRate() {
		return oTcUnCheckRate;
	}

	public void setoTcUnCheckRate(String oTcUnCheckRate) {
		this.oTcUnCheckRate = oTcUnCheckRate;
	}

	public String getoTcQualiFiedRate() {
		return oTcQualiFiedRate;
	}

	public void setoTcQualiFiedRate(String oTcQualiFiedRate) {
		this.oTcQualiFiedRate = oTcQualiFiedRate;
	}

	public String getoTcUnQualiFiedRate() {
		return oTcUnQualiFiedRate;
	}

	public void setoTcUnQualiFiedRate(String oTcUnQualiFiedRate) {
		this.oTcUnQualiFiedRate = oTcUnQualiFiedRate;
	}
	
	public String getoTcCheckRecordCount() {
		return oTcCheckRecordCount;
	}

	public void setoTcCheckRecordCount(String oTcCheckRecordCount) {
		this.oTcCheckRecordCount = oTcCheckRecordCount;
	}
	public String getCheckDateString() {
		return checkDateString;
	}

	public void setCheckDateString(String checkDateString) {
		this.checkDateString = checkDateString;
	}
	public String getRowspanNum() {
		return rowspanNum;
	}
	public void setRowspanNum(String rowspanNum) {
		this.rowspanNum = rowspanNum;
	}
	public String getTdHide() {
		return tdHide;
	}
	public void setTdHide(String tdHide) {
		this.tdHide = tdHide;
	}
}