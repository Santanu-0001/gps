package com.gps_3;

public class alertRecord {
	private int id;
	private String jobid;
	private String message;
	private int acc;
	private String dt;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String val) {
		this.jobid = val;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String val) {
		this.message = val;
	}

	public int getAcc() {
		return acc;
	}

	public void setAcc(int val) {
		this.acc = val;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String val) {
		this.dt = val;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return id + "/" + jobid + "/" + message + "/" + acc + "/";
	}


}
