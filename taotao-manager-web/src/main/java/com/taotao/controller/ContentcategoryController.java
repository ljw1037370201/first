package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentcategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentcategoryController {
	
	@Autowired
	private ContentcategoryService contentcategoryServicel;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentcategoryById(@RequestParam(value="id",defaultValue="0") long parentId){
		List<EasyUITreeNode> result = contentcategoryServicel.getContentcategoryById(parentId);
		return result;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createCategory(long parentId ,String name){
		TaotaoResult result = contentcategoryServicel.addContentcategory(parentId, name);
		return result;
	}
}
