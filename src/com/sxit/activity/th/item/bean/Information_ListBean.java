package com.sxit.activity.th.item.bean;

import java.io.Serializable;

import com.sxit.entity.Adviser;
import com.sxit.entity.UserInfo;

/**
 * info Bean
 * 
 * @author huanyu 类名称：Information_ListBean 创建时间:2014-10-27 上午12:09:51
 */
public class Information_ListBean implements Serializable {
	private int _id;
	private String title;// 标题
	private String imgUrl;// 图片url
	private String content;// 内容
	private String date;// 时间
	private String analyst;// 分析师
	private String time;// 关注次数
	private int notifType;// 消息类型 0：新闻 1：顾问 2：首席顾问
	private Adviser adviser;
	private UserInfo userinfo;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAnalyst() {
		return analyst;
	}

	public void setAnalyst(String analyst) {
		this.analyst = analyst;
	}

	public int getNotifType() {
		return notifType;
	}

	public void setNotifType(int notifType) {
		this.notifType = notifType;
	}

	public Adviser getAdviser() {
		return adviser;
	}

	public void setAdviser(Adviser adviser) {
		this.adviser = adviser;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

}
