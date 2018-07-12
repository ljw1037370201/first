package com.taotao.manager.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

public class MyTesst {
    /**
     * 点对点案例 多个发布者对应一个 接收者
     *  一个发布者对应一个接收者
     *  特点：默认缓存到服务器
     */
    @Test
    public void demo1() throws Exception {
        //创建 连接工厂对象 brokerURL:表示连接的地址 以TCP开头
       ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.168:61616");
        //通过连接工厂对象得到连接对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //通过连接对象得到一个会话对象 1.是否开启分布式任务2.代表消息应答模式（1.自动应答（默认）2.手动应答）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建点对点对象 取一个名字是随意的
        Queue queue = session.createQueue("test-queue");
        //创建一个发布者,用于发布消息 接受一个destination参数 得到一个消息发布者对象
        MessageProducer producer = session.createProducer(queue);
        //封装发送消息的对象
        TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("point test...");
        //调用消息发布者发送消息
        producer.send(textMessage);
        //关闭资源 从下往上关
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 用于接收发布者发送的消息
     * 发布者的名字 test-queue
     * 内容 point test...
     * 类型 String
     */
    @Test
    public void demo2() throws Exception {
        //创建 连接工厂对象 brokerURL:表示连接的地址 以TCP开头
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.168:61616");
        //通过连接工厂对象得到连接对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //通过连接对象得到一个会话对象 1.是否开启分布式任务2.代表消息应答模式（1.自动应答（默认）2.手动应答）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //定义接受那个发布者
        Queue queue = session.createQueue("test-queue");
        //消息接收者对象
        MessageConsumer messageConsumer = session.createConsumer(queue);
        //通过一个MessageListener监听器来接收数据  监听者回调 只有名字叫test-queue 才会执行下面的代码
        messageConsumer.setMessageListener(new MessageListener() {//匿名内部类写法
            @Override
            public void onMessage(Message message) {
                //判断它是什么类型
                if (message instanceof TextMessage){
                    //这样判断防止强转出错
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        String text = textMessage.getText();
                        System.out.println(text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        //注意 这里有一个消息的等待 他会一直阻塞，等待有人发送到控制台  然后代码才会继续往下走
        System.in.read();

        //关闭资源 从下往上关
        messageConsumer.close();
        session.close();
        connection.close();
    }

    /**
     * 消息发布与订阅默认的情况下是不缓存的
     * 有多个发布者并且可以有多个订阅者
     */
    @Test
    public void demo3() throws Exception {
        //创建 连接工厂对象 brokerURL:表示连接的地址 以TCP开头
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.168:61616");
        //通过连接工厂对象得到连接对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //通过连接对象得到一个会话对象 1.是否开启分布式任务2.代表消息应答模式（1.自动应答（默认）2.手动应答）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建点对点对象 取一个名字是随意的
        Topic topic = session.createTopic("test-topic");
        //创建一个发布者,用于发布消息 接受一个destination参数 得到一个消息发布者对象
        MessageProducer producer = session.createProducer(topic);
        //封装发送消息的对象
        TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("point test1...");
        //调用消息发布者发送消息
        producer.send(textMessage);
        //关闭资源 从下往上关
        producer.close();
        session.close();
        connection.close();
    }


    @Test
    public void demo4() throws Exception {
        //创建 连接工厂对象 brokerURL:表示连接的地址 以TCP开头
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.168:61616");
        //通过连接工厂对象得到连接对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //通过连接对象得到一个会话对象 1.是否开启分布式任务2.代表消息应答模式（1.自动应答（默认）2.手动应答）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建点对点对象 取一个名字是随意的
        Topic topic = session.createTopic("test-topic");
        MessageConsumer messageConsumer = session.createConsumer(topic);
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        String text = textMessage.getText();
                        System.out.println(text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        System.out.println("等待接收数据1");
        System.in.read();
        //关闭资源 从下往上关
        messageConsumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void demo6(){
        //初始化spring
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //从容器中获得JmsTemplate对象
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        //从容器中得到Destination对象
        Destination destination = (Destination) applicationContext.getBean("Destination");
        jmsTemplate.send(destination, new MessageCreator() {
            //通过session发送数据
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("test message queue");
                return textMessage;
            }
        });
    }
}
