package com.taotao.mapper;

import com.taotao.pojo.Tbitem;

import java.util.List;


public interface TbitemMapper {
	/**
	 * 根据id查询指定的商品信息
	 * @param id 商品id
	 * @return 指定商品的信息
	 */
    public Tbitem getItemById(long id);
    /**
     * 分页查询商品信息
     * @return 返回当前页面的记录条数的信息
     */
    public List<Tbitem> getItem();
    /**
     * 添加商品到数据库
     * @param  tbitem 传入一个Tbitem对象
     */
    public void insertTbitem(Tbitem tbitem);

}