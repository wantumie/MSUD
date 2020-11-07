package com.example.demo.controller;

import com.example.demo.entity.ProductInfoMap;
import com.example.demo.entity.R;
import com.example.demo.service.PartPackService;
import com.example.demo.service.TestService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

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
    PartPackService partPackService;

    @RequestMapping("/index")
    public ModelAndView index(String partId){

        ModelAndView mv = new ModelAndView();
        PageInfo<ProductInfoMap> productPage = partPackService.queryPartPackInfoList(partId, 1, 10);

//        mv.addObject("newText","你好，Thymeleaf！");
        mv.addObject("newText","你好，景林包装！");
        mv.addObject("gender","1");
        mv.addObject("productPage",productPage);
        mv.setViewName("/index");

        return mv;
    }
    @RequestMapping("/updatepartpackInfo")
    public String updateInfo(String packId, String spec){
        String code = "0000";
        try {
            partPackService.updatePartinfo(packId, spec);
            code = "200";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("更新报错");
        }

        return code;
    }

    @RequestMapping("/queryAndInsertData")
    public R queryAndInsertData(String segNo, String productionOrderCode, String fProductId, String fPackId,
                                String packId){

        R r = new R();
        if (StringUtils.isEmpty(segNo) || StringUtils.isEmpty(productionOrderCode) || StringUtils.isEmpty(fProductId) || StringUtils.isEmpty(fPackId)){
            r.setResultCode(500);
            r.setResultMsg("输入参数为空，请重新输入");
            return r;
        }
        partPackService.queryDetail(segNo, productionOrderCode);
        partPackService.queryProduct(segNo, productionOrderCode, fProductId, fPackId);
        String jsonStr = partPackService.queryPartPackInfo(packId);

        r.setResultCode(200);
        r.setResultMsg("查询成功！");
        r.setResultInfo(jsonStr);

        return r;
    }

    @RequestMapping("/querypartpacklist")
    public R queryList (String packid) {
        R r = new R();

        ModelAndView mv = new ModelAndView();

        PageInfo<ProductInfoMap> productInfoList = partPackService.queryPartPackInfoList(packid,1,10);
        r.setResultCode(200);
        r.setResultMsg("查询成功！");
        r.setResultInfo(productInfoList);
//        mv.addObject("productInfoList", productInfoList);
//        mv.setViewName("/index.html");

        return r;
    }

    @RequestMapping("/querypartpackinfo")
    public PageInfo<ProductInfoMap> queryInfo (String packid) {


        PageInfo<ProductInfoMap> productInfoList = partPackService.queryPartPackInfoList(packid,1,10);

//        mv.addObject("productInfoList", productInfoList);
//        mv.setViewName("/index.html");

        return productInfoList;
    }


}
