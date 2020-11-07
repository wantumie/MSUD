package com.example.demo.util;

import com.github.pagehelper.Page;

import java.util.List;

public class PageInfoListResult<T> {
    private List<T> dataList;
    private Page page;

    public PageInfoListResult() {
    }

    public List<T> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Page getPage() {
        return this.page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
