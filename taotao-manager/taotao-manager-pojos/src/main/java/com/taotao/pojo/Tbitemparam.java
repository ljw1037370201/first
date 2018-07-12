package com.taotao.pojo;

import java.io.Serializable;
import java.util.Date;

public class Tbitemparam implements Serializable{
    private long id;

    private long itemCatId;

    private Date created;

    private Date updated;

    private String paramData;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getItemCatId() {
		return itemCatId;
	}

	public void setItemCatId(long itemCatId) {
		this.itemCatId = itemCatId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getParamData() {
		return paramData;
	}

	public void setParamData(String paramData) {
		this.paramData = paramData;
	}

  
}