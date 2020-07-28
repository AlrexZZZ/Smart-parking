package com.nineclient.constant;

/**
 * 工单升级优先级
 * @author 9client
 *
 */
public enum VocWorkOrderEmergency {

    一般(0,"一般"),紧急(1,"紧急");
    
	String name;
	int id;
	
	private VocWorkOrderEmergency(int id,String name){
		this.id=id;
		this.name=name;
	}
	
	public static VocWorkOrderEmergency  getEnum(int id){
		VocWorkOrderEmergency vv = null;
		for(VocWorkOrderEmergency vs:VocWorkOrderEmergency.values()){
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
