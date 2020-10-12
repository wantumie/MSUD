package com.example.demo.entity;

import lombok.Data;

/**
 * describe
 *
 * @Auther Mr.Garfield
 * @Date 2020/10/3
 */

@Data
public class R {
    private int resultCode;
    private String resultMsg;
    private Object resultInfo;
}
