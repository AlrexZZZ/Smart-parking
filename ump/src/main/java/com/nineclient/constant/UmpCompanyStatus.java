package com.nineclient.constant;

public enum UmpCompanyStatus {

    待审核(0,"待审核"), 审核通过(1,"审核通过"),审核不通过(2,"审核不通过");
    String name;
	int id;
	
	private UmpCompanyStatus(int id,String name){
		this.id=id;
		this.name=name;
	}
	
	public static UmpCompanyStatus  getEnum(int id){
		UmpCompanyStatus vv = null;
		for(UmpCompanyStatus vs:UmpCompanyStatus.values()){
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
