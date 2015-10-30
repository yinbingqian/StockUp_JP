package com.sxit.activity.th.item.bean;

import java.io.Serializable;

/**
 * analyst bean
 * @author huanyu	 
 * 类名称：Analyst_ListBean   
 * 创建时间:2014-10-27 下午6:37:53
 */
public class Analyst_ListBean implements Serializable{
	private String name;//
	private int imgUrl;//
	private String analystTeam;// 分析师团队

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(int imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getAnalystTeam() {
		return analystTeam;
	}

	public void setAnalystTeam(String analystTeam) {
		this.analystTeam = analystTeam;
	}

}
