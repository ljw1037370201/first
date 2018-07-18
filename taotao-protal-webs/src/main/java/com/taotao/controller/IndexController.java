package com.taotao.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.Tbcontent;
import com.taotao.pojo.Tbitemcat;
import com.taotao.protal.pojo.Ad1Node;
import com.taotao.protal.pojo.ItemCat;
import com.taotao.protal.pojo.ItemCatResult;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;
	@Value("${AD1_CID}")
	private Long AD1_CID;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;

	
	@RequestMapping("/index")
	public String showIndex(Model model){
		List<Tbcontent> contentList = contentService.getContentList(AD1_CID);
		List<Ad1Node> result = new ArrayList<Ad1Node>();
		for (Tbcontent content : contentList) {
			Ad1Node node = new Ad1Node();
			node.setAlt(content.getTitle());//设置提示，图片的描述
			node.setHeight(AD1_HEIGHT);//设置小图高度
			node.setHeightB(AD1_HEIGHT_B);//设置大图高度
			node.setWidth(AD1_WIDTH);//设置小图宽度
			node.setWidthB(AD1_WIDTH_B);//设置大图宽度
			node.setHref(content.getUrl());//设置跳转的连接
			node.setSrc(content.getPic());//设置小图
			node.setSrcB(content.getPic2());//设置大图
			result.add(node);
		}
		model.addAttribute("ad1", JsonUtils.objectToJson(result));
		return "index";
	}

	// http://localhost:8082/item/cat/itemcat/all.html
	@RequestMapping("/item/cat/itemcat/all")
	@ResponseBody
	public ItemCatResult queryAll(String callback){
		ItemCatResult result = new ItemCatResult();
		result.setData(getItemCatList(0));
		return result;
	}

	/**
	 * 查询数据库得到想要的分类
	 */
	private List<?> getItemCatList(long parentId){
		List<Tbitemcat> list = contentService.getItemCatAll(parentId);
		List data = new ArrayList();
		int count = 0;
		for (Tbitemcat item : list) {
			ItemCat itemCat = new ItemCat();
			//判断是否是父节点
			if (item.getIsParent()){
				itemCat.setUrl("/products/"+item.getId()+".html");
				if (parentId == 0){
					//注意 只有第一级目录才是这样 第二级是字符串
					itemCat.setName("<a href='/products/"+item.getId()+".html'>"+item.getName()+"</a>");
				}else {
					//第二级目录
					itemCat.setName(item.getName());
				}
				count ++;
				//递归
				itemCat.setItem(getItemCatList(item.getId()));
				//查询数据库以后得到的一二级目录存放到一个list集合里面去
				data.add(itemCat);
				if(parentId == 0 && count >= 14){
					break;
				}
			}else {
				//最底层的子目录 存放的最下级目录
				data.add("/products/"+item.getId()+".html|"+item.getName());
			}
		}
		return data;
	}
}
