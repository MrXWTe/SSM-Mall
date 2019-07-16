package com.xwt.onlinemall.commonpojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 搜索后的查询结果
 * @Author: MrXu
 * @date: 2019/7/16
 */
public class SearchResult implements Serializable {
    private long recourdCount;
    private int totalPages;
    private List<SearchItem> itemList;

    public long getRecourdCount() {
        return recourdCount;
    }

    public void setRecourdCount(long recourdCount) {
        this.recourdCount = recourdCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
