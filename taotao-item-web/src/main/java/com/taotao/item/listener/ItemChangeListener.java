package com.taotao.item.listener;

import com.taotao.pojo.Tbitem;
import com.taotao.pojo.Tbitemdesc;
import com.taotao.service.ItemParamItemService;
import com.taotao.service.ItemService;
import com.taotao.service.ItemparamService;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ItemChangeListener implements MessageListener {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemParamItemService itemParamItemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;

    private Writer writer ;
    /**
     * 数据 传过来的id 查询数据库 得到商品信息
     * 调用solrJ的代码存入索引库中
     *
     */
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage){
            try {
                //取商品id
                TextMessage textMessage = (TextMessage)message;
                String itemId = textMessage.getText();
                //商品信息
                Tbitem item = itemService.getItemById(Long.valueOf(itemId));
                //商品描述
                Tbitemdesc tbitemdesc = itemService.getTbitemdescById(Long.valueOf(itemId));
                //规格参数
                String tbitemparam = itemParamItemService.getTbitemparamById(Long.valueOf(itemId));
                //获得configuration对象 不需要配置版本
                Configuration configuration = freeMarkerConfigurer.getConfiguration();
                Template template = configuration.getTemplate("item.ftl");
                Map map = new HashMap();
                map.put("item",item);
                map.put("tbitemdesc",tbitemdesc);
                map.put("tbitemparam",tbitemparam);
                writer = new FileWriter(new File(HTML_OUT_PATH+itemId+".html"));
                template.process(map,writer);
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (MalformedTemplateNameException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (TemplateNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }finally {
                if (writer != null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
