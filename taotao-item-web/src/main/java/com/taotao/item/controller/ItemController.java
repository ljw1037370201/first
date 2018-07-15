package com.taotao.item.controller;

import com.taotao.pojo.Item;
import com.taotao.pojo.Tbitem;
import com.taotao.pojo.Tbitemdesc;
import com.taotao.pojo.Tbitemparam;
import com.taotao.service.ItemParamItemService;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemParamItemService itemParamItemService;
    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable long itemId,Model model){
        Tbitem tbitem = itemService.getItemById(itemId);
        Item item = new Item(tbitem);
        model.addAttribute("item",item);
        return "item";
    }
    @RequestMapping("/item/desc/{itemId}")
    @ResponseBody
    public String showItemdescInfo(@PathVariable long itemId,Model model){
        Tbitemdesc tbitemdesc = itemService.getTbitemdescById(itemId);
        return tbitemdesc.getItemDesc();
    }
    @RequestMapping("/item/param/{itemId}")
    @ResponseBody
    public String showItemparamInfo(@PathVariable long itemId,Model model){
        String tbitemparam = itemParamItemService.getTbitemparamById(itemId);
        return tbitemparam;
    }
}
