package com.nineclient.constant;

public enum VocDispatchState {
   未分配(0,"未分配"),已分配(1,"已分配");
	String name;
	int id;
	
	private VocDispatchState(int id,String name){
		this.id=id;
		this.name=name;
	}
	public static VocDispatchState  getEnum(int id){
		VocDispatchState vv = null;
		for(VocDispatchState vs:VocDispatchState.values()){
			if(vs.ordinal() == id){
				vv =  vs;
				break;
			}
		}
		return vv;
	}
}
