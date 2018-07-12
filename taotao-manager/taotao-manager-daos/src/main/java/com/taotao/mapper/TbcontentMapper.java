package com.taotao.mapper;

import com.taotao.pojo.Tbcontent;

import java.util.List;


public interface TbcontentMapper {
	/**
	 * 根据分类id
	 * @param categoryId 分类id
	 * @return Tbcontent集合
	 */
    public List<Tbcontent> findAllTbcontentById(long categoryId);
    /**
     * 添加内容
     * @param tbcontent 对象
     */
    public void insertTbcontent(Tbcontent tbcontent);
}