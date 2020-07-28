package com.nineclient.constant;

public enum VocReplayState {

    待回复(0,"待回复"),已回复(1,"已回复"),不回复(2,"不回复");
	String name;
	int id;
	
	private VocReplayState(int id,String name){
		this.id=id;
		this.name=name;
	}
	public static VocReplayState  getEnum(int id){
		VocReplayState vv = null;
		for(VocReplayState vs:VocReplayState.values()){
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
