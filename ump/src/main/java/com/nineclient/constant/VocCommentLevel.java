package com.nineclient.constant;

public enum VocCommentLevel {

    好评(0,"好评"),中评(1,"中评"),差评(2,"差评");
    
	String name;
	int id;
	
	private VocCommentLevel(int id,String name){
		this.id=id;
		this.name=name;
	}
	public static VocCommentLevel  getEnum(int id){
		VocCommentLevel vv = null;
		for(VocCommentLevel vs:VocCommentLevel.values()){
			if(vs.ordinal() == id){
				vv =  vs;
				break;
			}
		}
		return vv;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
