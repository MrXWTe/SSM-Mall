package com.xwt.onlinemall.search.controller;

import com.xwt.onlinemall.commonpojo.SearchResult;
import com.xwt.onlinemall.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/16
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @Value("${SEARCH_RESULT_ROWS}")
    private int SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String search(String keyword,
                         @RequestParam(defaultValue="1") Integer page,
                         Model model) throws Exception {

        //需要转码
        keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
        SearchResult result = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("recourdCount", result.getRecourdCount());
        model.addAttribute("page", page);
        model.addAttribute("itemList", result.getItemList());

        return "search";
    }
}
