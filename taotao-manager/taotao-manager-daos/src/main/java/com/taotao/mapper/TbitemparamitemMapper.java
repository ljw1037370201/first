package com.taotao.mapper;


import com.taotao.pojo.Tbitemparamitem;

public interface TbitemparamitemMapper {
    /**
     * 添加规格参数到数据库
     * @param tbitemparamitem 规格参数表
     */
   public void insertItemparamitem(Tbitemparamitem tbitemparamitem);

    /**
     * 根据商品id查询数据库中规格参数值表
     * @param itemId 商品id
     * @return Tbitemparamitem 对象
     */
   public Tbitemparamitem getItemparamitemById(long itemId);

}