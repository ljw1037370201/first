package com.taotao.mapper;

import com.taotao.pojo.Tbcontentcategory;

import java.util.List;


public interface TbcontentcategoryMapper {
	/**
	 * 根据分类父级目录的id查询分类信息
	 * @param parentId 父级目录的id
	 * @return Tbcontentcategory的集合
	 */
    public List<Tbcontentcategory> getContentcategoryById(long parentId);
    /**
     * 添加分类信息
     * @param tbcontentcategory
     */
    public void insertContentcategory(Tbcontentcategory tbcontentcategory);
    /**
     * 根据父级目录的id查询分类信息
     * @param id 父级目录的id
     * @return Tbcontentcategory对象
     */
    public Tbcontentcategory selectByPrimaryKey(long id);
    /**
     * 修改分类信息
     * @param tbcontentcategory
     */
    public void updateByPrimaryKey(Tbcontentcategory tbcontentcategory);
}