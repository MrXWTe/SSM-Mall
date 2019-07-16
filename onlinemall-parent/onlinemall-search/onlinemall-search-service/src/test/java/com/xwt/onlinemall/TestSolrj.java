package com.xwt.onlinemall;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/16
 */
public class TestSolrj {

    /**
     * 添加文档
     * @throws Exception
     */
    @Test
    public void addDocument() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://218.197.194.82:8080/solr/collection1");
        // 创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        // 向文档中添加域
        document.addField("id", "doc01");
        document.addField("item_title", "测试商品01");
        document.addField("item_price", 1000);
        // 把文档写入索引库
        solrServer.add(document);
        solrServer.commit();
    }

    /**
     * 删除文档
     * @throws Exception
     */
    @Test
    public void deleteDocument() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://218.197.194.82:8080/solr/collection1");
        // 删除文档
        solrServer.deleteById("doc01");
        solrServer.commit();
    }

    /**
     * 查询索引库（简单查询）
     * @throws Exception
     */
    @Test
    public void queryIndex() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://218.197.194.82:8080/solr/collection1");
        SolrQuery query = new SolrQuery();
        query.set("q", "*:*");
        QueryResponse queryResponse = solrServer.query(query);
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("查询结果记录数" + results.getNumFound());
        for (SolrDocument result : results) {
            System.out.println(result.get("id"));
        }
    }

    /**
     * 复杂查询
     * @throws Exception
     */
    @Test
    public void queryDocument() throws Exception {
        //创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://218.197.194.82:8080/solr");
        //创建一个查询对象，可以参考solr的后台的查询功能设置条件
        SolrQuery query = new SolrQuery();
        //设置查询条件
        //query.setQuery("阿尔卡特");
        query.set("q", "阿尔卡特");
        //设置分页条件
        query.setStart(1);
        query.setRows(2);
        //开启高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        //设置默认搜索域
        query.set("df", "item_title");
        //执行查询，得到一个QueryResponse对象。
        QueryResponse queryResponse = solrServer.query(query);
        //取查询结果总记录数
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("查询结果总记录数：" + solrDocumentList.getNumFound());
        //取查询结果
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            //取高亮后的结果
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size() > 0) {
                //取高亮后的结果
                title = list.get(0);
            } else {
                title = (String) solrDocument.get("item_title");
            }
            System.out.println(title);
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
        }
    }
}
