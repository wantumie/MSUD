package com.example.demo.entity;

import lombok.Data;

@Data
public class Page {
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPage;
    private Long totalSize;

    public Page(Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}
