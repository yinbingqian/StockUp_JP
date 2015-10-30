package com.sxit.entity;

import java.io.Serializable;

public class AnalystVideo implements Serializable{
	private String Id;
	private String Title;
	private String Picture;
	private String Content;
	private String Analystid;
	private String Crtime;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getPicture() {
		return Picture;
	}
	public void setPicture(String picture) {
		Picture = picture;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getAnalystid() {
		return Analystid;
	}
	public void setAnalystid(String analystid) {
		Analystid = analystid;
	}
	public String getCrtime() {
		return Crtime;
	}
	public void setCrtime(String crtime) {
		Crtime = crtime;
	}
	
	
}
