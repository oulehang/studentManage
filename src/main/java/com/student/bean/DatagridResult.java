package com.student.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */
public class DatagridResult implements Serializable {
    private long total;// 记录总数
    private List rows; // 记录集合

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Object> getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}