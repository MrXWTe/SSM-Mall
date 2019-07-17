package com.xwt.activemq;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * @Description: 测试ActiveMQ与Spring整合
 * @Author: MrXu
 * @date: 2019/7/17
 */
public class ActivcemqSpring {

    private JmsTemplate jmsTemplate;
    private Destination destination;

    /**
     * 初始化方法
     */
    @Before
    public void init(){
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext-activemq.xml");
        jmsTemplate = ac.getBean(JmsTemplate.class);
        destination = ac.getBean(Queue.class);
    }

    /**
     * 测试发送消息方法
     */
    @Test
    public void testSendMessage() throws JMSException {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("Hello, JMSTemplate");
            }
        });
    }
}
