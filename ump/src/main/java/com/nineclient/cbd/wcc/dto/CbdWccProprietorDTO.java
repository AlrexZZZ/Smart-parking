package com.nineclient.cbd.wcc.dto;

import com.nineclient.cbd.wcc.model.WccAppartment;




public class CbdWccProprietorDTO {

	
	private Long id ;
	private String tempname;

    private String tempphone;
	
	private String tempdoorplate;

	private WccAppartment tempappartment;

	public String getTempname() {
		return tempname;
	}

	public void setTempname(String tempname) {
		this.tempname = tempname;
	}

	public String getTempphone() {
		return tempphone;
	}

	public void setTempphone(String tempphone) {
		this.tempphone = tempphone;
	}

	public String getTempdoorplate() {
		return tempdoorplate;
	}

	public void setTempdoorplate(String tempdoorplate) {
		this.tempdoorplate = tempdoorplate;
	}

	public WccAppartment getTempappartment() {
		return tempappartment;
	}

	public void setTempappartment(WccAppartment tempappartment) {
		this.tempappartment = tempappartment;
	}

	public CbdWccProprietorDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CbdWccProprietorDTO(Long id, String tempname, String tempphone,
			String tempdoorplate, WccAppartment tempappartment) {
		super();
		this.id = id;
		this.tempname = tempname;
		this.tempphone = tempphone;
		this.tempdoorplate = tempdoorplate;
		this.tempappartment = tempappartment;
	}

	

} 

