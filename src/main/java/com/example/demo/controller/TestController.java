package com.example.demo.controller;

import com.example.demo.entity.ColumnTitleMap;
import com.example.demo.entity.ProductInfoMap;
import com.example.demo.service.ExportDataService;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
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
    public ModelAndView test(String id){
        ModelAndView mv = new ModelAndView();
        mv.addObject("newText","你好，Thymeleaf！");
        mv.addObject("gender","1");
        String name = testService.testService(id);
        mv.setViewName("/test.html");
        return mv;
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
    public ModelAndView queryAllList(String partId){
        ModelAndView mv=new ModelAndView();
        List<Map> list = testService.queryPartList(partId);
        mv.addObject("newText","你好，Thymeleaf！");
        mv.addObject("gender","1");
        mv.addObject("productList",list);
//        if(list!=null) {
//            mv.addObject("loginUser",list);
//        }
        mv.setViewName("/list");
        return mv;

    }

    @RequestMapping("/queryInfo")
    public List<Map> queryInfo(String id){
        List<Map> list = testService.queryPartinfo(id);
        return list;
    }

    @RequestMapping("/insertInfo")
    public String insertInfo(String partId, String spec){
        String code = "0000";
        try {
            testService.insertPartinfo(partId, spec);
            code = "200";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("更新报错");
        }

        return code;
    }
    @RequestMapping("/updateInfo")
    public String updateInfo(String partId, String spec){
        String code = "0000";
        try {
            testService.updatePartinfo(partId, spec);
            code = "200";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("更新报错");
        }

        return code;
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

    @RequestMapping("/getversion")
    public String getversion(){


        return "20201101";
    }

    @RequestMapping("/index")
    public ModelAndView index(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/index");

        return mv;
    }
}
