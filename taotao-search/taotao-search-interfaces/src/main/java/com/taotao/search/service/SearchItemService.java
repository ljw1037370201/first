package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

public interface SearchItemService {
	/**
	 * 查询数据库并且导入到索引库里面去
	 * @return
	 */
	public TaotaoResult importAllItems();

	/**
	 * 根据商品id查询数据库
	 * @param itemId
	 * @return TaotaoResult
	 */
	public TaotaoResult addDocument(long itemId);
}
