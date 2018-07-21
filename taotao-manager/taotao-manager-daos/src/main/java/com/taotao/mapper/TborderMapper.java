package com.taotao.mapper;


import com.taotao.pojo.Tborder;

public interface TborderMapper {
    /**
     * 插入订单数据到订单表
     * @param tborder 需要插入的订单数据
     */
    void insertOrder(Tborder tborder);
}