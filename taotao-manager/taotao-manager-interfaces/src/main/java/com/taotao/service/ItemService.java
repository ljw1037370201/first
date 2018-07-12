package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.Tbitem;

public interface ItemService {
	/**
	 * 根据id查询指定的商品信息
	 * @param id 商品id
	 * @return 指定商品的信息
	 */
	public Tbitem getItemById(long id);
	/**
	 * 分页显示商品信息
	 * @param page 当前页
	 * @param rows 每页显示的条数
	 * @return 返回当前页的记录条数 ，注意必须是json数据
	 */
	public EasyUIDataGridResult getItemList(int page, int rows);
	/**
	 * 添加商品信息到数据库，注意有商品信息没有传递过来需要我们手动添加 比如商品id
	 * @param tbitem 传入tbitem对象
	 * @return 返回自定义TaotaoResult对象 里面包含了状态码，消息，数据 ，以jeson数据格式返回
	 */
	public TaotaoResult addItem(Tbitem tbitem, String desc);
}
