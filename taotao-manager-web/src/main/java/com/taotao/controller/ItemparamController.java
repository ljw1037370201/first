package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.Tbitemparam;
import com.taotao.service.ItemparamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
public class ItemparamController {
    @Autowired
    private ItemparamService itemparamService;
    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemparamByCid(@PathVariable long itemCatId){
        TaotaoResult result = itemparamService.getItemparamByCid(itemCatId);
        return result;
    }
    @RequestMapping("/save/{itemCatId}")
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable long itemCatId, String paramData){
        Tbitemparam tbitemparam = new Tbitemparam();
        tbitemparam.setItemCatId(itemCatId);
        tbitemparam.setParamData(paramData);
        TaotaoResult result = itemparamService.insertItemParam(tbitemparam);
        return result;
    }
}
