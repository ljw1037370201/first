package com.taotao.protal.pojo;

public class Ad1Node {
	private String srcB;//大图
	private Integer height;//小图高度
	private String alt;//设置提示，图片的描述
	private Integer width;//小图宽度
	private String src;//小图
	private Integer widthB;//大图宽度
	private String href;//跳转的连接
	private Integer heightB;//大图高度
	public String getSrcB() {
		return srcB;
	}
	public void setSrcB(String srcB) {
		this.srcB = srcB;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public Integer getWidthB() {
		return widthB;
	}
	public void setWidthB(Integer widthB) {
		this.widthB = widthB;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Integer getHeightB() {
		return heightB;
	}
	public void setHeightB(Integer heightB) {
		this.heightB = heightB;
	}
	@Override
	public String toString() {
		return "Ad1Node [srcB=" + srcB + ", height=" + height + ", alt=" + alt
				+ ", width=" + width + ", src=" + src + ", widthB=" + widthB
				+ ", href=" + href + ", heightB=" + heightB + "]";
	}
	

}
