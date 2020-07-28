package com.nineclient.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import flexjson.JSONSerializer;

public class TreeModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private List<TreeModel> children;
	private boolean open;
	private Boolean isParent;
	/**是否叶子节点**/
	private Boolean isLeaf;
	/**是否是默认节点**/
	private Boolean isDefault;

	/**
	 * @return the isLeaf
	 */
	public Boolean getIsLeaf() {
		return isLeaf;
	}


	/**
	 * @param isLeaf the isLeaf to set
	 */
	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}


	/**
	 * @return the isDefault
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}


	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}


	public Boolean getIsParent() {
		return isParent;
	}


	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the children
	 */
	public List<TreeModel> getChildren() {
		return children;
	}


	/**
	 * @param children the children to set
	 */
	public void setChildren(List<TreeModel> children) {
		this.children = children;
	}


	public boolean isOpen() {
		return open;
	}


	public void setOpen(boolean open) {
		this.open = open;
	}
	public static String toJsonArray(Collection<TreeModel> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
	public static String toJsonArray(Collection<TreeModel> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
}
