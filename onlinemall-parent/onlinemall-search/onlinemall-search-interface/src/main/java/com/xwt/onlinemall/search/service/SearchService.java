package com.xwt.onlinemall.search.service;

import com.xwt.onlinemall.commonpojo.SearchResult;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/16
 */
public interface SearchService {
    SearchResult search(String keyWord, int page, int rows) throws Exception;
}
