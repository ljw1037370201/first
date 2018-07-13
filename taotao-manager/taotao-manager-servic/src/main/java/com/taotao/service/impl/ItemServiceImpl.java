package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbitemMapper;
import com.taotao.mapper.TbitemdescMapper;
import com.taotao.pojo.Tbitem;
import com.taotao.pojo.Tbitemdesc;
import com.taotao.service.ItemService;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private TbitemMapper tbitemMapper;
	@Autowired
	private TbitemdescMapper tbitemdescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private ActiveMQTopic topicDestination;
	@Override
	public Tbitem getItemById(long id) {
		Tbitem tbitem = tbitemMapper.getItemById(id);
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
		Tbitemdesc tbitemdesc = tbitemdescMapper.getItemDescById(itemId);
		return tbitemdesc;
	}

}
