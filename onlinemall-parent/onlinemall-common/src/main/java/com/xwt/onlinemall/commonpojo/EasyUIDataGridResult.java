package com.xwt.onlinemall.commonpojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasyUIDataGridResult implements Serializable {
    private long total;
    private List rows;
}
