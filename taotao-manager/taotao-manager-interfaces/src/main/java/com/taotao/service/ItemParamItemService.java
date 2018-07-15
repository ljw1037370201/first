package com.taotao.service;

public interface ItemParamItemService {
    /**
     * 	根据商品id查询商品规格参数模板信息
     * @param itemId 商品id
     * @return String
     */
    public String getTbitemparamById(long itemId);
}
