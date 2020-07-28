package com.nineclient.constant;

/**
 * 公司服务状态
 * @author 9client
 *
 */
public enum UmpCompanyServiceStatus {
	试用(0,"试用"),到期(1,"到期"),开通(2,"开通"),预到期(3,"预到期"),停止(4,"停止"),关闭(5,"关闭");
	String name;
	int id;
	
	private UmpCompanyServiceStatus(int id,String name){
		this.id=id;
		this.name=name;
	}
	public static UmpCompanyServiceStatus  getEnum(int id){
		UmpCompanyServiceStatus vv = null;
		for(UmpCompanyServiceStatus vs:UmpCompanyServiceStatus.values()){
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
