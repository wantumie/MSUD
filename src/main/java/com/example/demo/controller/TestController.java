package com.example.demo.controller;

import com.example.demo.entity.ColumnTitleMap;
import com.example.demo.service.ExportDataService;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * describe
 *
 * @Auther Mr.Garfield
 * @Date 2020/6/1
 */
@RestController
@RequestMapping
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    ExportDataService exportDataService;

    @RequestMapping("/test")
    public String test(String id){
        String name = testService.testService(id);
        return name;
    }

    @RequestMapping("/get")
    public String getUserName(String id) throws Exception {
        String name = testService.getUserName(id);
        return name;
    }

    @RequestMapping("/post")
    public String postUserName(String id){
        String name = testService.postUserName(id);
        return name;
    }
    @RequestMapping("/list")
    public Object queryAllList(){
        List<Map> list = testService.queryAllList();
        return list;
    }

    @GetMapping(value = "/excel")
    public void getUserInfoEx(HttpServletResponse response,
            @RequestParam String date_start,
            @RequestParam String date_end
    ) {
        try {
            List<Map> userList = testService.queryAllList();
            ArrayList<String> titleKeyList = new ColumnTitleMap("userinfo").getTitleKeyList();
            Map<String, String> titleMap = new ColumnTitleMap("userinfo").getColumnTitleMap();
            exportDataService.exportDataToEx(response, titleKeyList, titleMap, userList);
        } catch (Exception e) {
            //
            System.out.println(e.toString());
        }
    }
}
