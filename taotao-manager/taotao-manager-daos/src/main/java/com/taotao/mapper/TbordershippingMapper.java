package com.taotao.mapper;


import com.taotao.pojo.Tbordershipping;

public interface TbordershippingMapper {
    /**
     * 往数据库插入指定订单中的用户地址信息
     * @param tbordershipping 某一个订单的用户地址信息
     */
   void insertOrdershipping(Tbordershipping tbordershipping);
}