package com.gsft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AssetMasterTable")
public class AssetMaster {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int assetId;
	private long value;
	private float rate;
	private float ltv;
	private int tenure;
	public AssetMaster() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public float getLtv() {
		return ltv;
	}
	public void setLtv(float ltv) {
		this.ltv = ltv;
	}
	public int getTenure() {
		return tenure;
	}
	public void setTenure(int tenure) {
		this.tenure = tenure;
	}
	@Override
	public String toString() {
		return "AssetMaster [assetId=" + assetId + ", value=" + value + ", rate=" + rate + ", ltv=" + ltv + ", tenure="
				+ tenure + "]";
	}
	
	
	
}
