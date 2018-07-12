package com.taotao.controller;

import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.Tbitem;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/{id}")
	@ResponseBody            //发送json数据
	public Tbitem getTbitemById(@PathVariable long id){//
		Tbitem tbitem = itemService.getItemById(id);
		return tbitem;
	}
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(int page,int rows){
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult saveItem(Tbitem tbitem,String desc){
		TaotaoResult taotaoResult = itemService.addItem(tbitem, desc);
		return taotaoResult;
	}
}
