package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Controller
public class SearchController {
	@Value("${ITEM_ROWS}")
	private Integer ITEM_ROWS;
	
	@Autowired
	private SearchService searchService;


	@RequestMapping("/search")
	public String  search(@RequestParam("q")String q,
			@RequestParam(defaultValue="1") Integer page,Model model) throws Exception{
		try {
			q = q == null ? "":new String(q.getBytes("iso-8859-1"),"utf-8");
			SearchResult result = searchService.search(q, page, ITEM_ROWS);
			model.addAttribute("totalPages", result.getPageCount());
			model.addAttribute("query", q);
			model.addAttribute("itemList", result.getItemList());
			model.addAttribute("page", page);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "search";
	}
}
