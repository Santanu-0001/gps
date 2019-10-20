package com.gps_3;

public class record {
	private int id;
	private String lati;
	private String longi;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLatitude() {
		return lati;
	}

	public void setLatitude(String val) {
		this.lati = val;
	}

	public String getLongtude() {
		return longi;
	}

	public void setLongitude(String val) {
		this.longi = val;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return id + "/" + lati + "/" + longi + "/";
	}

}

