package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbitemMapper;
import com.taotao.mapper.TbitemdescMapper;
import com.taotao.pojo.Tbitem;
import com.taotao.pojo.Tbitemdesc;
import com.taotao.service.ItemService;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;
	@Value("${BASE}")
	private String BASE;
	@Value("${DESC}")
	private String DESC;
	@Value("${PARAM}")
	private String PARAM;
	@Value("${ITEM_INFO_EXPIRE}")
	private Integer ITEM_INFO_EXPIRE;
	@Autowired
	private TbitemMapper tbitemMapper;
	@Autowired
	private TbitemdescMapper tbitemdescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private ActiveMQTopic topicDestination;
	@Autowired
	private JedisClient jedisClient;
	@Override
	public Tbitem getItemById(long id) {
		String json = jedisClient.get(ITEM_INFO + ":" + id + BASE);
		if (StringUtils.isNotBlank(json)){
			Tbitem tbitem1 = JsonUtils.jsonToPojo(json, Tbitem.class);
			return tbitem1;
		}
		Tbitem tbitem = tbitemMapper.getItemById(id);
		try {
			jedisClient.set(ITEM_INFO + ":" + id + BASE, JsonUtils.objectToJson(tbitem));
			jedisClient.expire(ITEM_INFO + ":" + id + BASE,ITEM_INFO_EXPIRE);
		}catch (Exception e){
			e.printStackTrace();
		}
		return tbitem;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//使用分页插件显示分页
		PageHelper.startPage(page,rows);
		List<Tbitem> items = tbitemMapper.getItem();
		System.out.println(items.size());

		//通过商品代码查询的到所有商品信息 把它作为参数丢到mybatis的分页插件对象里 自动分页


		PageInfo<Tbitem> info = new PageInfo<Tbitem>(items);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(info.getTotal());

		result.setRows(items);
		return result;
	}


	@Override
	public TaotaoResult addItem(Tbitem tbitem,String desc) {
		final long itemId = IDUtils.genItemId();
		tbitem.setId(itemId);
		tbitem.setStatus((byte)1);
		Date date = new Date();
		tbitem.setCreated(date);
		tbitem.setUpdated(date);
		tbitemMapper.insertTbitem(tbitem);
		Tbitemdesc tbitemdesc = new Tbitemdesc();
		tbitemdesc.setItemId(itemId);
		tbitemdesc.setItemDesc(desc);
		tbitemdesc.setCreated(date);
		tbitemdesc.setUpdated(date);
		tbitemdescMapper.insertTbitemdesc(tbitemdesc);
		/**
		 * 这里发布消息 更新缓存
		 * 1.用点对点还是订阅与发布
		 * 2.发送过去的数据是是什么类型数据 格式是什么？
		 *         只发送id   发送json
		 */
		jmsTemplate.send(topicDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage( itemId+"");
				return textMessage;
			}
		});
		return TaotaoResult.ok();
	}

	@Override
	public Tbitemdesc getTbitemdescById(long itemId) {
		try {
			String json= jedisClient.get(ITEM_INFO + ":" + itemId + DESC);
			if (StringUtils.isNotBlank(json)){
				Tbitemdesc tbitemdesc1 = JsonUtils.jsonToPojo(json, Tbitemdesc.class);
				return tbitemdesc1;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		Tbitemdesc tbitemdesc = tbitemdescMapper.getItemDescById(itemId);
		try {
			jedisClient.set(ITEM_INFO+":"+itemId+DESC,JsonUtils.objectToJson(tbitemdesc));
			jedisClient.expire(ITEM_INFO+":"+itemId+DESC,ITEM_INFO_EXPIRE);
		}catch (Exception e){
			e.printStackTrace();
		}
		return tbitemdesc;
	}

}
