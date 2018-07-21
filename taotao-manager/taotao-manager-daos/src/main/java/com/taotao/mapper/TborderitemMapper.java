package com.taotao.mapper;


import com.taotao.pojo.Tborderitem;

public interface TborderitemMapper {
    /**
     * 往数据库里面插入对应的商品
     * @param tborderitem 某一个订单对应的商品
     */
    void inertOrderitem(Tborderitem tborderitem);
}