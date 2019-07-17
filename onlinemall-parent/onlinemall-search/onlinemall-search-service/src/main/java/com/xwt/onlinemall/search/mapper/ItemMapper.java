package com.xwt.onlinemall.search.mapper;

import com.xwt.onlinemall.commonpojo.SearchItem;
import com.xwt.onlinemall.commonpojo.SearchResult;

import java.util.List;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/16
 */

public interface ItemMapper {
    List<SearchItem> getItemList();
    SearchItem getItemById(long itemId);
}
