package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

public interface ContentcategoryService {
	/**
	 * 根据分类父级目录id查询分类信息
	 * @param parentId 父级目录id
	 * @return 返回EasyUITreeNode的集合，包含id，text，state
	 */
	public List<EasyUITreeNode> getContentcategoryById(long parentId);
	/**
	 * 添加分类信息
	 * @param parentId 父级目录id
	 * @param name 分类的名称
	 * @return TaotaoResult
	 */
	public TaotaoResult addContentcategory(long parentId, String name);
}
