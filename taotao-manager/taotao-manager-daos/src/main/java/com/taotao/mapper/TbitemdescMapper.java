package com.taotao.mapper;

import com.taotao.pojo.Tbitemdesc;

public interface TbitemdescMapper {
	/**
	 * 添加商品描述到数据库
	 * @param tbitemdesc 商品描述对象
	 */
    public void insertTbitemdesc(Tbitemdesc tbitemdesc);

	/**
	 * 根据商品id查询商品描述
	 * @param itemId 商品id
	 * @return  Tbitemdesc
	 */
	public Tbitemdesc getItemDescById(long itemId);
}