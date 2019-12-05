package com.gsft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MasterTable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int masterid;
	private String assetname;
	private float gsftrate;
	private float gsftltv;
	private int gsfttenure;
	private float kvbrate;
	private float kvbltv;
	private int kvbtenure;
	public MasterTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getMasterid() {
		return masterid;
	}
	public void setMasterid(int masterid) {
		this.masterid = masterid;
	}
	public String getAssetname() {
		return assetname;
	}
	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}
	public float getGsftrate() {
		return gsftrate;
	}
	public void setGsftrate(float gsftrate) {
		this.gsftrate = gsftrate;
	}
	public float getGsftltv() {
		return gsftltv;
	}
	public void setGsftltv(float gsftltv) {
		this.gsftltv = gsftltv;
	}
	public int getGsfttenure() {
		return gsfttenure;
	}
	public void setGsfttenure(int gsfttenure) {
		this.gsfttenure = gsfttenure;
	}
	public float getKvbrate() {
		return kvbrate;
	}
	public void setKvbrate(float kvbrate) {
		this.kvbrate = kvbrate;
	}
	public float getKvbltv() {
		return kvbltv;
	}
	public void setKvbltv(float kvbltv) {
		this.kvbltv = kvbltv;
	}
	public int getKvbtenure() {
		return kvbtenure;
	}
	public void setKvbtenure(int kvbtenure) {
		this.kvbtenure = kvbtenure;
	}
	@Override
	public String toString() {
		return "MasterTable [masterid=" + masterid + ", assetname=" + assetname + ", gsftrate=" + gsftrate
				+ ", gsftltv=" + gsftltv + ", gsfttenure=" + gsfttenure + ", kvbrate=" + kvbrate + ", kvbltv=" + kvbltv
				+ ", kvbtenure=" + kvbtenure + "]";
	}
	
	
	
}
