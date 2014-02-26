package com.sap.ndb.studio.pal.ui;

public class SelectedPalInfo {
	private String[] parameter;
	private String[] columnname;
	private String[] columntype;
	private String[] columnlength;
	private String palmodel;
	public SelectedPalInfo(String[] para,String[] name,String[] type,String[] length,String model){
		parameter = para;
		columnname =name;
		columntype = type;
		columnlength = length;
		palmodel = model;
	}
	public String getPalmodel() {
		return palmodel;
	}
	public void setPalmodel(String palmodel) {
		this.palmodel = palmodel;
	}
	public String[] getParameter() {
		return parameter;
	}
	public void setParameter(String[] parameter) {
		this.parameter = parameter;
	}
	public String[] getColumnname() {
		return columnname;
	}
	public void setColumnname(String[] columnname) {
		this.columnname = columnname;
	}
	public String[] getColumntype() {
		return columntype;
	}
	public void setColumntype(String[] columntype) {
		this.columntype = columntype;
	}
	public String[] getColumnlength() {
		return columnlength;
	}
	public void setColumnlength(String[] columnlength) {
		this.columnlength = columnlength;
	}
}
