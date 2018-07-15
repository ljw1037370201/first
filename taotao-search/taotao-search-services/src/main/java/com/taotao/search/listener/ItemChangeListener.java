package com.taotao.search.listener;



import com.taotao.search.service.impl.SearchItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemChangeListener implements MessageListener {
    @Autowired
   private SearchItemServiceImpl searchItemServiceImpl;
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
                //向索引库添加文档
                searchItemServiceImpl.addDocument(Long.valueOf(itemId));
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }
}
