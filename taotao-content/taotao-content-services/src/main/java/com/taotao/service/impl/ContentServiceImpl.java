package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbcontentMapper;
import com.taotao.pojo.Tbcontent;
import com.taotao.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbcontentMapper tbcontentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	@Override
	public EasyUIDataGridResult findAll(long categoryId) {
		List<Tbcontent> tbcontents = tbcontentMapper.findAllTbcontentById(categoryId);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(tbcontents.size());
		result.setRows(tbcontents);
		return result;
	}



	@Override
	public TaotaoResult addContent(Tbcontent tbcontent) {
		//补全属性
		Date date = new Date();
		tbcontent.setCreated(date);
		tbcontent.setUpdated(date);
		tbcontentMapper.insertTbcontent(tbcontent);
		jedisClient.hdel(CONTENT_KEY,Long.toString(tbcontent.getCategoryId()));
		return TaotaoResult.ok();
	}



	@Override
	public List<Tbcontent> getContentList(long categoryId) {
		//这里去缓存
		try {
			String json = jedisClient.hget(CONTENT_KEY, categoryId+"");
			if(StringUtils.isNotBlank(json)){//判断json是否为空
				List<Tbcontent> jsonToList = JsonUtils.jsonToList(json, Tbcontent.class);
				return jsonToList;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/**
		 * 第一次请求时，由于没有缓存，所以直接查询数据库，在查询完数据库后直接，在return之前把数据库
		 * 里面数据直接存到redis缓存中
		 * 第二次请求时，由于先走上面的代码先从缓存里去，直接return 没有取到，代码往下走
		 */
		List<Tbcontent> result = tbcontentMapper.findAllTbcontentById(categoryId);
		//这里加缓存
		try {
			jedisClient.hset(CONTENT_KEY, categoryId+"", JsonUtils.objectToJson(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
}
