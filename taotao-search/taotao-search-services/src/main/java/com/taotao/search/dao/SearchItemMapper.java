package com.taotao.search.dao;

import com.taotao.common.pojo.SearchItem;

import java.util.List;

public interface SearchItemMapper {
	/**
	 * 查询数据库中的三张表tbitem，tbitemcat，tbitemdesc
	 * @return SearchItem包含：id，title，sellPoint，price，image，categoryName，itemDesc
	 */
	public List<SearchItem> getItemList();

	/**
	 * 根据id查询SearchItem信息
	 * @param itemId
	 * @return SearchItem对象
	 */
	public SearchItem getItemById(long itemId);
}
