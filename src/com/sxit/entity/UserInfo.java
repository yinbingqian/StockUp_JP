package com.sxit.entity;

import java.io.Serializable;

public class UserInfo implements Serializable{
	private String userid;
	private String username;
	private String userheader;
	
	public UserInfo(){
		
	}
	public UserInfo(String userid,String username,String userheader){
		this.userheader = userheader;
		this.userid = userid;
		this.username = username;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserheader() {
		return userheader;
	}
	public void setUserheader(String userheader) {
		this.userheader = userheader;
	}


}
