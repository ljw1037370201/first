package com.taotao.search.test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class MyTest {
	@Test
	public void demo() throws Exception{//向solr里面添加索引库和文档库
		//创建solr
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.154:8080/solr");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "001");
		document.addField("item_title", "商品1");
		document.addField("item_price", "200");
		solrServer.add(document);
		solrServer.commit();
	}
	@Test
	public void demo1() throws Exception{//向solr里面添加索引库和文档库
		//创建solr
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.154:8080/solr");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "001");
		document.addField("item_title", "一叶子面膜");
		document.addField("item_price", "220");
		solrServer.add(document);
		solrServer.commit();
	}
	@Test
	public void demo3() throws Exception{//向solr里面添加索引库和文档库
		//创建solr
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.154:8080/solr");
		solrServer.deleteById("001");
		solrServer.commit();
	}
	@Test
	public void demo4() throws Exception{//向solr里面添加索引库和文档库
		//创建solr
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.154:8080/solr");
		solrServer.deleteByQuery("item_title:一叶子面膜");//根据查询条件来删
		solrServer.commit();
	}


	@Test
	public void demo5() throws Exception{//向solr里面添加索引库和文档库
		//创建solr
		CloudSolrServer solrServer1 = new CloudSolrServer("192.168.25.154:2181,192.168.25.154:2182,192.168.25.154:2183");
		solrServer1.setDefaultCollection("collection2");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "001");
		document.addField("item_title", "商品1");
		document.addField("item_price", "200");
		solrServer1.add(document);
		solrServer1.commit();
	}


}
