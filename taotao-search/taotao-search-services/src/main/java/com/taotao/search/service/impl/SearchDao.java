package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	/**
	 * 根据业务层传入的条件，查询文档库
	 * @param query 查询条件
	 * @return SearchResult包含：商品结果集，总记录条数，总页数
	
	 */
	public SearchResult search(SolrQuery query) {
		SearchResult result = new SearchResult();
		try {
			//返回queryResponse结果集
			QueryResponse queryResponse = solrServer.query(query);
			//返回真正的结果集
			SolrDocumentList documentList = queryResponse.getResults();
			//得到总记录条数
			result.setRecordCount(documentList.getNumFound());
			//返回给页面的商品集合
			List<SearchItem> itemList = new ArrayList<SearchItem>();
			for (SolrDocument solrDocument : documentList) {
				//创建商品对象
				SearchItem searchItem = new SearchItem();
				searchItem.setId((String)solrDocument.get("id"));
				searchItem.setCategoryName((String)solrDocument.get("item_category_name"));
				searchItem.setPrice((long)solrDocument.get("item_price"));
				searchItem.setImage((String)solrDocument.get("item_image"));
				searchItem.setSellPoint((String)solrDocument.get("item_sell_point"));
				//高亮
				Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
				List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
				String itemTitle = "";
				if(list != null && list.size() > 0){
					itemTitle = list.get(0);
				}else{
					itemTitle = (String)solrDocument.get("item_title");
				}
				searchItem.setTitle(itemTitle);
				itemList.add(searchItem);
			}
			//得到文档中所有商品信息加入到集合
			result.setItemList(itemList);
			return result;
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
