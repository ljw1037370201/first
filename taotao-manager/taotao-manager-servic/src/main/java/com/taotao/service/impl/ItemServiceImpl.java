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
import com.taotao.mapper.TbitemparamMapper;
import com.taotao.mapper.TbitemparamitemMapper;
import com.taotao.pojo.Tbitem;
import com.taotao.pojo.Tbitemdesc;
import com.taotao.pojo.Tbitemparam;
import com.taotao.pojo.Tbitemparamitem;
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
import java.util.Map;

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
	@Autowired
	private TbitemparamMapper tbitemparamMapper;
	@Autowired
	private TbitemparamitemMapper tbitemparamitemMapper;
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
	public TaotaoResult addItem(Tbitem tbitem,String desc,String itemParams) {
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
		//存入规格参数
		Tbitemparamitem tbitemparamitem = new Tbitemparamitem();
		tbitemparamitem.setItemId(itemId);
		tbitemparamitem.setParamData(itemParams);
		tbitemparamitem.setCreated(date);
		tbitemparamitem.setUpdated(date);
		tbitemparamitemMapper.insertItemparamitem(tbitemparamitem);
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

		}
		return tbitemdesc;
	}

	@Override
	public String  getTbitemparamById(long itemId) {
		Tbitemparamitem itemparamitem = tbitemparamitemMapper.getItemparamitemById(itemId);
		String paramData = itemparamitem.getParamData();
		List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("    <tbody>\n");
		for(Map m1:jsonList) {
			sb.append("        <tr>\n");
			sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
			sb.append("        </tr>\n");
			List<Map> list2 = (List<Map>) m1.get("params");
			for(Map m2:list2) {
				sb.append("        <tr>\n");
				sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
				sb.append("            <td>"+m2.get("v")+"</td>\n");
				sb.append("        </tr>\n");
			}
		}
		sb.append("    </tbody>\n");
		sb.append("</table>");
		return sb.toString();
	}

}
