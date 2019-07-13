package com.xwt.onlinemall.commonpojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 这个类主要用来匹配类目结点
 * @Author: MrXu
 * @date: 2019/7/13
 */
@Data
public class EasyUITreeNode implements Serializable {
    private long id;
    private String text;
    private String state;
}
