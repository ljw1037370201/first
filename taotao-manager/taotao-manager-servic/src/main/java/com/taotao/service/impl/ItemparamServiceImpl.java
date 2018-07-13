package com.taotao.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbitemparamMapper;
import com.taotao.pojo.Tbitemparam;
import com.taotao.service.ItemparamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ItemparamServiceImpl implements ItemparamService {

    @Autowired
    private TbitemparamMapper tbitemparamMapper;
    @Override
    public TaotaoResult getItemparamByCid(long itemCatId) {
        Tbitemparam itemparam = tbitemparamMapper.getItemparamByCid(itemCatId);
        if (itemparam != null){
            return TaotaoResult.ok(itemparam);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult insertItemParam(Tbitemparam tbitemparam) {
        try {
            Date date = new Date();
            tbitemparam.setCreated(date);
            tbitemparam.setUpdated(date);
            tbitemparamMapper.insertTbitemparam(tbitemparam);
            return TaotaoResult.ok(tbitemparam);
        }catch (Exception e){
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }
}
