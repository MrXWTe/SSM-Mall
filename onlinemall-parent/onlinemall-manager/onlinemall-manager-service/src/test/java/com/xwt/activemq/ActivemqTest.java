package com.xwt.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/17
 */
public class ActivemqTest {

    /**
     * 使用ActiveMQ发送消息; queue：点对点通信，发布消息，如果没有接受则保存在队列
     * @throws JMSException JMS异常
     */
    @Test
    public void testQueueProducer() throws JMSException{
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://218.197.194.82:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test-Queue");
        MessageProducer producer = session.createProducer(queue);
        TextMessage testMessage = session.createTextMessage("Hello ActiveMQ");
        producer.send(testMessage);

        // 关闭连接
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 使用ActiveMQ接受消息; queue：点对点通信，接受消息，如果没有接受则保存在队列
     * @throws JMSException JMS异常
     * @throws IOException IO异常
     */
    @Test
    public void testQueueConsumer() throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://218.197.194.82:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test-Queue");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage messageReceived = (TextMessage)message;
                try {
                    System.out.println(messageReceived.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }


    /**
     * 使用ActiveMQ发送消息 topic:广播形式，发布消息，如果没有接受则丢失
     * @throws JMSException JMS异常
     */
    @Test
    public void testTopicProducer() throws JMSException{
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://218.197.194.82:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test-topic");
        MessageProducer producer = session.createProducer(topic);
        TextMessage testMessage = session.createTextMessage("Hello ActiveMQ, this test is topic");
        producer.send(testMessage);

        // 关闭连接
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 使用ActiveMQ接受消息 topic:广播形式，接受消息，如果没有接受则丢失
     * @throws JMSException
     * @throws IOException
     */
    @Test
    public void testTopicConsumer() throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://218.197.194.82:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test-topic");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage messageReceived = (TextMessage)message;
                try {
                    System.out.println(messageReceived.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
