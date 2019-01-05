package com.aptech.business.assetManage.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.aptech.business.component.dictionary.AssetManageTypeEnum;
import com.aptech.framework.orm.BaseEntity;


@Alias("AssetManagement")
public class AssetManagementEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7506625272766085778L;
	// 姓名
	private String name;
	// 年号
	private String year;
	// 有效期
	private String time;
	// 相关资料
	private String relevantInfo;
	// 附件
	private String appendix;
	// 系统默认类别，6个风电场
	private String defualtType;
	// 系统默认类别名称，6个风电场
	private String defualtTypeName;
	// 资质管理类别，1总部,2生产单位
	private String type;
	// 资质管理类别，1总部,2生产单位
	private String typeName;
	// 创建时间
	private Date createDate;
	// 更新时间
	private Date updateDate;
 	private String unitId;
 	/**
 	 * 删除和编辑按钮显示标识
 	 */
 	private boolean buttonDisplayFlag;

 	/**
 	 *  创建人
 	 */
 	private String creater;
 	/**
 	 *  创建人
 	 */
 	private String createrName;
 	private String code;
 	//名称
 	private String assetName;
 	//证书
 	private String certificate;
 	private String unitName;
 	
 	
 	
 	
 	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

 	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

 	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

 	
	public String getRelevantInfo() {
		return relevantInfo;
	}

	public void setRelevantInfo(String relevantInfo) {
		this.relevantInfo = relevantInfo;
	}

 	
	public String getAppendix() {
		return appendix;
	}

	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}

 	
	public String getDefualtType() {
		return defualtType;
	}

	public void setDefualtType(String defualtType) {
		this.defualtType = defualtType;
	}

 	
	public String getDefualtTypeName() {
		return defualtTypeName;
	}

	public void setDefualtTypeName(String defualtTypeName) {
		this.defualtTypeName = defualtTypeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
 	
	public String getTypeName() {
		//功能名称
		for (AssetManageTypeEnum typeEnum : AssetManageTypeEnum.values()) {
			if (typeEnum.getCode().equals(type.toString())) {
				typeName = typeEnum.getName();
				break;
			}
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

 	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isButtonDisplayFlag() {
		return buttonDisplayFlag;
	}

	public void setButtonDisplayFlag(boolean buttonDisplayFlag) {
		this.buttonDisplayFlag = buttonDisplayFlag;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
	 
}