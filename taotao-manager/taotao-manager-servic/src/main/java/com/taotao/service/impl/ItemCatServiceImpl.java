package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbitemcatMapper;
import com.taotao.pojo.Tbitemcat;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbitemcatMapper tbitemcatMapper;
	@Override
	public List<EasyUITreeNode> getCatList(long parentId) {
		//根据父级id查询商品分类信息 把它装入到一个集合里面
		 List<Tbitemcat> catList = tbitemcatMapper.getCatList(parentId);
		//定义返回结果集
		List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
		for (Tbitemcat cat : catList) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(cat.getId());
			node.setText(cat.getName());
			//1.代表有节点 true
			node.setState(cat.getIsParent()?"closed":"open");
			result.add(node);
		}
		return result;
	}

}
