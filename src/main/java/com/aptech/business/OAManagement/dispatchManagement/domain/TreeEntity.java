package com.aptech.business.OAManagement.dispatchManagement.domain;

import org.apache.ibatis.type.Alias;

/**
 * <p>标题:树实体类 </p>
 * @autho admin
 * @time 2016年12月27日 下午1:39:07
*/
@Alias("treeEntity")
public class TreeEntity {
	
	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 父级ID
	 */
	private String parentId;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 父节点名称
	 */
	private String parentName;
	/**
	 * 祖先节点Code
	 */
	private String ancestorCode;
	
	/**
	 * 祖先节点Code
	 */
	private String ancestorName;
	
	/**
	 * 节点类型，1是组织，2是个人
	 */
	private String nodeType;

	/** 
	 * 取得 ID
	 *  
	 * @return ID 
	 */
	public String getId() {
		return id;
	}

	/** 
	 * 设定 ID
	 *  
	 * @param id ID 
	 */
	
	public void setId(String id) {
		this.id = id;
	}

	/** 
	 * 取得 编码
	 *  
	 * @return 编码 
	 */
	public String getCode() {
		return code;
	}

	/** 
	 * 设定 编码
	 *  
	 * @param code 编码 
	 */
	
	public void setCode(String code) {
		this.code = code;
	}

	/** 
	 * 取得 名称
	 *  
	 * @return 名称 
	 */
	public String getName() {
		return name;
	}

	/** 
	 * 设定 名称
	 *  
	 * @param name 名称 
	 */
	
	public void setName(String name) {
		this.name = name;
	}

	/** 
	 * 取得 父级ID
	 *  
	 * @return 父级ID 
	 */
	public String getParentId() {
		return parentId;
	}

	/** 
	 * 设定 父级ID
	 *  
	 * @param parentId 父级ID 
	 */
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/** 
	 * 取得 描述
	 *  
	 * @return 描述 
	 */
	public String getDescription() {
		return description;
	}

	/** 
	 * 设定 描述
	 *  
	 * @param description 描述 
	 */
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getAncestorCode() {
		return ancestorCode;
	}

	public void setAncestorCode(String ancestorCode) {
		this.ancestorCode = ancestorCode;
	}

	public String getAncestorName() {
		return ancestorName;
	}

	public void setAncestorName(String ancestorName) {
		this.ancestorName = ancestorName;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

}
