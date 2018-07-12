package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.Tbcontent;

import java.util.List;

public interface ContentService {
	/**
	 * 根据分类id查询分类信息
	 * @param categoryId 分类id
	 * @return EasyUIDataGridResult
	 */
	public EasyUIDataGridResult findAll(long categoryId);
	/**
	 * 添加内容,注意属性不全
	 * @param tbcontent 对象
	 * @return TaotaoResult
	 */
	public TaotaoResult addContent(Tbcontent tbcontent);
	/**
	 * 根据父类id查询所有信息
	 * @param categoryId 父类id
	 * @return Tbcontent集合
	 */
	public List<Tbcontent> getContentList(long categoryId);
}
