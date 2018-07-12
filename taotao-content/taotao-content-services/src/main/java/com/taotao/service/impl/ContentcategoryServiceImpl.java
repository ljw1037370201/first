package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbcontentcategoryMapper;
import com.taotao.pojo.Tbcontentcategory;
import com.taotao.service.ContentcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentcategoryServiceImpl implements ContentcategoryService {
	
	@Autowired
	private TbcontentcategoryMapper tbcontentcategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentcategoryById(long parentId) {
		List<Tbcontentcategory> contentcategorys = tbcontentcategoryMapper.getContentcategoryById(parentId);
		List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
		for (Tbcontentcategory contentcategory : contentcategorys) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentcategory.getId());
			node.setText(contentcategory.getName());
			node.setState(contentcategory.getIsParent()?"closed":"open");
			result.add(node);
		}
		return result;
	}

	@Override
	public TaotaoResult addContentcategory(long parentId, String name) {
		//1.接收两个参数
		//2.向Tbcontentcategory表插入数据
		Tbcontentcategory tbcontentcategory = new Tbcontentcategory();
		tbcontentcategory.setParentId(parentId);
		tbcontentcategory.setName(name);
		tbcontentcategory.setIsParent(false);//设置没有父目录
		tbcontentcategory.setSortOrder(1);
		tbcontentcategory.setStatus(1);
		tbcontentcategory.setCreated(new Date());
		tbcontentcategory.setUpdated(new Date());
		tbcontentcategoryMapper.insertContentcategory(tbcontentcategory);
		//3.判断父节点是否为true，不是true改为true
		Tbcontentcategory category = tbcontentcategoryMapper.selectByPrimaryKey(parentId);
		if(!category.getIsParent()){
			category.setIsParent(true);
			tbcontentcategoryMapper.updateByPrimaryKey(category);
		}
		return TaotaoResult.ok(tbcontentcategory);
	}

}
