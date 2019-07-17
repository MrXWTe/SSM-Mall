package com.xwt.onlinemall.search.message;

import com.xwt.onlinemall.search.service.impl.SearchItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/17
 */
public class ItemChangeListener implements MessageListener {

    @Autowired
    private SearchItemServiceImpl searchItemServiceImpl;

    @Override
    public void onMessage(Message message) {
        // 判断接受的message是否是TextMessage类型
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                long itemId = Long.parseLong(textMessage.getText());
                searchItemServiceImpl.importOneItem(itemId);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
