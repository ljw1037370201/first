package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.Tbitemparam;

public interface ItemparamService {
    /**
     * 根据分类id查询指定分类下面是否有规格参数模板存在
     * @param itemCatId 分类id
     * @return TaotaoResult 200表示该分类有规格参数模板否则返回ok
     */
    public TaotaoResult getItemparamByCid(long itemCatId);

    /**
     * 保存模板json数据到数据库中的模板表中
     * @param tbitemparam 需要保存的模板json数据
     * @return TaotaoResult 200表示成功 ，否则返回ok
     */
    public TaotaoResult insertItemParam(Tbitemparam tbitemparam);
}
