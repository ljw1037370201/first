package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
	/**
	 * 调用数据库查询指定父级的商品分类信息并且把它装到EasyUITreeNode对象里面
	 * @param parentId 父级目录的id
	 * @return 返回EasyUITreeNode对象，包含：id，text，state
	 */
	public List<EasyUITreeNode> getCatList(long parentId);
}
