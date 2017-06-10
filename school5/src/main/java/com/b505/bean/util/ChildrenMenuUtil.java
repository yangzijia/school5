package com.b505.bean.util;

public class ChildrenMenuUtil {
	private Integer id;
	private String childrenmenuName;
	private String url;
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChildrenmenuName() {
		return childrenmenuName;
	}
	public void setChildrenmenuName(String childrenmenuName) {
		this.childrenmenuName = childrenmenuName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public ChildrenMenuUtil(Integer id, String childrenmenuName, String url,
			String remark) {
		super();
		this.id = id;
		this.childrenmenuName = childrenmenuName;
		this.url = url;
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "ChildrenMenuUtil [id=" + id + ", childrenmenuName="
				+ childrenmenuName + ", url=" + url + ", remark=" + remark
				+ "]";
	}
	
}
