package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.TestDao;
import com.example.demo.entity.User;
import com.example.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * describe
 *
 * @Auther Mr.Garfield
 * @Date 2020/06/2020/6/1
 */

@Slf4j
@Service
public class TestServiceImpl implements TestService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    TestDao testDao;

    @Override
    public String testService(String id) {
        log.info("接口实现类！");
//        log.info("ID="+id);

        String name = testDao.queryName(id);
        return name;
    }

    @Override
    public String getUserName(String id) {
        Map map = new HashMap();
        map.put("id",id);
        User user = new User();
        user.setId("1");

        JSONObject jsonId = new JSONObject(map);
//        String name = testDao.queryName(id);
        log.info(jsonId.toJSONString());
        String jsonObject = restTemplate.getForObject("http://127.0.0.1:8088/get?id=1", String.class);
        return jsonObject;
    }

    @Override
    public String postUserName(String id) {

        User user = new User();
        user.setId("1");
//        String name = testDao.queryName(id);

        String jsonObject1 = JSONObject.toJSONString(user);
        String jsonObject = restTemplate.postForObject("http://127.0.0.1:8088/post",jsonObject1, String.class);
        return jsonObject;
    }
}
