package com.aptech.business.equip.equipTree.domain;
/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年8月8日 下午6:07:38 
 */
public class EquipTreeNode {

	private Long id;
	
	private String code;
	
	private String name;
	
	private String icon;
	
	private String iconOpen;
	
	private String iconClose;
	
	private Long parentId;

	private String pathCode;
	
	private Long equipId;
	
	private Long unitId;
	
	private boolean open;
	/**
	 * 是否风场
	 */
	private String treeNodelevel;


	public String getTreeNodelevel() {
		return treeNodelevel;
	}

	public void setTreeNodelevel(String treeNodelevel) {
		this.treeNodelevel = treeNodelevel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getIconClose() {
		return iconClose;
	}

	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}
	
	public String getPathCode() {
		return pathCode;
	}

	public void setPathCode(String pathCode) {
		this.pathCode = pathCode;
	}

	public Long getEquipId() {
		return equipId;
	}

	public void setEquipId(Long equipId) {
		this.equipId = equipId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	
}
