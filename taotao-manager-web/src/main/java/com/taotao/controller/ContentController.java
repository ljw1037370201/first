package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.Tbcontent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIDataGridResult findAllById(long categoryId){
		EasyUIDataGridResult result = contentService.findAll(categoryId);
		return result;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult addContent(Tbcontent tbcontent){
		TaotaoResult result = contentService.addContent(tbcontent);
		return result;
	}
}
