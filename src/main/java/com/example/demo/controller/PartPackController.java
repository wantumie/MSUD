package com.example.demo.controller;

import com.example.demo.entity.R;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * describe
 *
 * @Auther Mr.Garfield
 * @Date 2020/10/3
 */

@RestController
@RequestMapping
public class PartPackController {

    @Autowired
    TestService testService;


    @RequestMapping("/queryAndInsertData")
    public R queryAndInsertData(String segNo, String productionOrderCode, String fProductId, String fPackId,
                                String packId){

        R r = new R();
        if (StringUtils.isEmpty(segNo) || StringUtils.isEmpty(productionOrderCode) || StringUtils.isEmpty(fProductId) || StringUtils.isEmpty(fPackId)){
            r.setResultCode(500);
            r.setResultMsg("输入参数为空，请重新输入");
            return r;
        }
        testService.queryDetail(segNo, productionOrderCode);
        testService.queryProduct(segNo, productionOrderCode, fProductId, fPackId);
        String jsonStr = testService.queryPartPackInfo(packId);

        r.setResultCode(200);
        r.setResultMsg("查询成功！");
        r.setResultInfo(jsonStr);

        return r;
    }

    @RequestMapping("/querypartpacklist")
    public R queryList () {
        R r = new R();

        String jsonStr = testService.queryPartPackInfoList();
        r.setResultCode(200);
        r.setResultMsg("查询成功！");
        r.setResultInfo(jsonStr);

        return r;
    }


}
