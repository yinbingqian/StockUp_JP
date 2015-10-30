package com.sxit.activity.th.item.adapter;



public class AnaltstData {

	private String RealName;
	private String Paidmark;
	private String Specialty;
	private String TagName;
	private String TagCss;
	private String HeadPic;

	public AnaltstData(String RealName, String Paidmark, String Specialty,
			String TagName, String TagCss, String HeadPic) {
		this.RealName = RealName;
		this.Paidmark = Paidmark;
		this.Specialty = Specialty;
		this.TagName = TagName;
		this.TagCss = TagCss;
		this.HeadPic = HeadPic;
	}

	public String getRealName() {
		return RealName;
	}

	public void setRealName(String realName) {
		RealName = realName;
	}

	public String getPaidmark() {
		return Paidmark;
	}

	public void setPaidmark(String paidmark) {
		Paidmark = paidmark;
	}

	public String getSpecialty() {
		return Specialty;
	}

	public void setSpecialty(String specialty) {
		Specialty = specialty;
	}

	public String getTagName() {
		return TagName;
	}

	public void setTagName(String tagName) {
		TagName = tagName;
	}

	public String getTagCss() {
		return TagCss;
	}

	public void setTagCss(String tagCss) {
		TagCss = tagCss;
	}

	public String getHeadPic() {
		return HeadPic;
	}

	public void setHeadPic(String headPic) {
		HeadPic = headPic;
	}


}
