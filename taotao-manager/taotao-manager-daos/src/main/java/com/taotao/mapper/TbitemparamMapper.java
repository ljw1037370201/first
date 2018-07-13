package com.taotao.mapper;


import com.taotao.pojo.Tbitemparam;

public interface TbitemparamMapper {
    /**
     * 根据分类id查询数据库中规格参数模板表
     * @param itemCatId 分类id
     * @return 当前分类id所对应的模板
     */
    public Tbitemparam getItemparamByCid(long itemCatId);

    /**
     * 把商品规格参数模板对象添加到数据库
     * @param tbitemparam 商品规格参数模板对象
     */
    public void insertTbitemparam(Tbitemparam tbitemparam);
}