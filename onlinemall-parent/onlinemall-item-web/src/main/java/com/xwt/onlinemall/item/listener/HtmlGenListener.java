package com.xwt.onlinemall.item.listener;

import com.xwt.onlinemall.item.pojo.Item;
import com.xwt.onlinemall.pojo.TbItem;
import com.xwt.onlinemall.pojo.TbItemDesc;
import com.xwt.onlinemall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 监听商品添加消息，生成对应的静态页面
 * @Author: MrXu
 * @date: 2019/7/17
 */
public class HtmlGenListener implements MessageListener {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Value("${HTML_GEN_PATH}")
    private String HTML_GEN_PATH;

    @Override
    public void onMessage(Message message) {
        try {
            // 从消息中取出商品
            TextMessage textMessage = (TextMessage) message;
            long itemId = Long.parseLong(textMessage.getText());

            // 等待事务提交
            Thread.sleep(1000);

            // 根据商品ID查询商品信息，商品基本信息和商品描述
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            // 取商品描述
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            // 创建一个数据集，把商品数据封装
            Map data = new HashMap();
            data.put("item", item);
            data.put("itemDesc", itemDesc);

            // 加载模板对象
            Configuration configuration = freeMarkerConfig.getConfiguration();
            Template template = configuration.getTemplate("item.flt");
            Writer out = new FileWriter(HTML_GEN_PATH + item + ".html");

            // 生成静态页面
            template.createProcessingEnvironment(data, out);

            // 关闭流
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
