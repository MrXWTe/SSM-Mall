package com.xwt.onlinemall.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwt.onlinemall.commonpojo.EasyUIDataGridResult;
import com.xwt.onlinemall.jedis.JedisClient;
import com.xwt.onlinemall.mapper.TbItemDescMapper;
import com.xwt.onlinemall.mapper.TbItemMapper;
import com.xwt.onlinemall.pojo.TbItem;
import com.xwt.onlinemall.pojo.TbItemDesc;
import com.xwt.onlinemall.pojo.TbItemExample;
import com.xwt.onlinemall.service.ItemService;
import com.xwt.onlinemall.utils.E3Result;
import com.xwt.onlinemall.utils.IDUtils;
import com.xwt.onlinemall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * @Description: 商品服务
 * @Author: MrXu
 * @date: 2019/7/11
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;


    @Autowired
    private TbItemDescMapper itemDescMapper;


    @Autowired
    private JmsTemplate jmsTemplate;


    @Resource
    private Destination topicDestination;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE;
    @Value("${ITEM_CACHE_EXPIRE}")
    private Integer ITEM_CACHE_EXPIRE;


    @Override
    public TbItem getItemById(long id) {

        // 查询缓存
        try{
            String s = jedisClient.get(REDIS_ITEM_PRE + id + ":" + ":BASE");
            if(StringUtils.isNoneBlank(s)){
                TbItem tbItem = JsonUtils.jsonToPojo(s, TbItem.class);
                return tbItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // 缓存中没有，查询数据库
        TbItem item = itemMapper.selectByPrimaryKey(id);

        // 从数据库中查询到结果后，将结果添加进缓存
        try{
            jedisClient.set(REDIS_ITEM_PRE + id + ":" + ":BASE", JsonUtils.objectToJson(item));
            // 设置过期时间
            jedisClient.expire(REDIS_ITEM_PRE + id + ":" + ":BASE", ITEM_CACHE_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }

        return item;
    }

    /**
     * 返回商品列表封装后的 EasyUIDataGridResult 对象
     * @param page 一共右多少页
     * @param rows 每页显示的行数
     * @return EasyUIDataGridResult 对象
     */
    @Override
    public EasyUIDataGridResult getItemList(int page, int rows){
        PageHelper.startPage(page, rows);

        // 执行查询
        TbItemExample example = new TbItemExample();
        List list = itemMapper.selectByExample(example);

        // 获取分页信息
        PageInfo<TbItem> info = new PageInfo<>(list);
        long total = info.getTotal();

        // 封装结果对象
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult(total, list);
        return easyUIDataGridResult;
    }

    /**
     * 添加商品
     * @param item 待添加的商品
     * @param desc 待添加商品的详细信息
     * @return E3Result
     */
    @Override
    public E3Result addItem(TbItem item, String desc) {
        // 生成商品ID
        final long itemId = IDUtils.genItemId();
        // 补全Item属性
        item.setId(itemId);
        // 向商品表插入数据
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        // 创建商品描述表对应的pojo对象
        itemMapper.insert(item);

        // 向商品描述表插入数据
        TbItemDesc itemDesc = new TbItemDesc();
        // 补全属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        // 向商品描述表插入数据
        itemDescMapper.insert(itemDesc);


        // 添加完商品后，发送消息通知ActiveMQ将商品添加进索引库
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(itemId+"");
                return textMessage;
            }
        });


        // 返回成功
        return E3Result.ok();
    }


    /**
     * 通过商品ID查询商品详细信息
     * 在点击商品详细页面时使用
     * @param itemId 商品ID
     * @return 查询的商品描述信息
     */
    @Override
    public TbItemDesc getItemDescById(long itemId) {
        // 查询缓存
        try{
            String s = jedisClient.get(REDIS_ITEM_PRE + itemId + ":" + ":BASE");
            if(StringUtils.isNoneBlank(s)){
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(s, TbItemDesc.class);
                return tbItemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        // 从数据库中查询到结果后，将结果添加进缓存
        try{
            jedisClient.set(REDIS_ITEM_PRE + itemId + ":" + ":DESC", JsonUtils.objectToJson(itemDesc));
            // 设置过期时间
            jedisClient.expire(REDIS_ITEM_PRE + itemId + ":" + ":DESC", ITEM_CACHE_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemDesc;
    }
}

