package com.xwt.onlinemall.search.service.impl;

import com.xwt.onlinemall.commonpojo.SearchItem;
import com.xwt.onlinemall.search.mapper.ItemMapper;
import com.xwt.onlinemall.search.service.SearchItemService;
import com.xwt.onlinemall.utils.E3Result;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 索引库维护service
 * @Author: MrXu
 * @date: 2019/7/16
 */
@Service("searchItemServiceImpl")
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public E3Result importAllItems() {
        List<SearchItem> itemList = itemMapper.getItemList();

        try{
            for (SearchItem searchItem : itemList) {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSellPoint());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImages());
                document.addField("item_category_name", searchItem.getCategoryName());
                solrServer.add(document);
                solrServer.commit();
            }
        }catch (Exception e){
            e.printStackTrace();
            return E3Result.build(500, "数据导入时发生异常");
        }
        return E3Result.ok();
    }
}
