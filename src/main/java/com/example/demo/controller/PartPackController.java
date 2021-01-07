package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.ProductInfoMap;
import com.example.demo.entity.ProductionOrderInfo;
import com.example.demo.entity.R;
import com.example.demo.service.PartPackService;
import com.example.demo.service.TestService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public ModelAndView index(String packId, @RequestParam(value="pageNo",defaultValue="1")Integer pageNo, @RequestParam(value="pageSize",defaultValue="10")Integer pageSize){
        ModelAndView mv = new ModelAndView();
        if (pageNo <= 0){
            pageNo = 1;
        }
        if (pageSize <= 0){
            pageSize = 10;
        }
        if ("null".equals(packId)){
            packId = null;
        }
        PageInfo<ProductInfoMap> productPage = partPackService.queryPartPackInfoList(packId, pageNo, pageSize);
//        mv.addObject("newText","你好，Thymeleaf！");
        mv.addObject("newText","你好，景林包装！");
        mv.addObject("gender","1");
        mv.addObject("pager",productPage);
        mv.setViewName("/index");
        return mv;
    }
    @RequestMapping("/updatepartpackInfo")
    public String updateInfo(@RequestBody ProductInfoMap productInfoMap){
        String code = "0000";
        try {
            code = partPackService.updatePartinfo(productInfoMap);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("更新报错");
        }
        return code;
    }

    @RequestMapping("/insertpartpackInfo")
    public String insertInfo(@RequestBody ProductInfoMap productInfoMap){
        String code = "0000";
        try {
            code = partPackService.insertPartinfo(productInfoMap);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("新增报错");
        }
        return code;
    }
    @RequestMapping("/queryAndInsertData")
    public R queryAndInsertData(String segNo, String productionOrderCode, String fProductId,  String fPackId) throws ParseException {
//    public R queryAndInsertData(@RequestBody ProductionOrderInfo productionOrderInfo){
//    public R queryAndInsertData(){
        segNo = "00112";

        R r = new R();
//        String segNo = productionOrderInfo.getSegNo();
//        String productionOrderCode = productionOrderInfo.getProductionOrderCode();
//        String fProductId = productionOrderInfo.getFProductId();
//        String fPackId = productionOrderInfo.getFPackId();
//        String segNo = "1";
//        String productionOrderCode = "2";
//        String fProductId = "3";
//        String fPackId = "4";
        if (StringUtils.isEmpty(segNo) || StringUtils.isEmpty(productionOrderCode)){
            r.setResultCode(9001);
            r.setResultMsg("输入参数为空，请重新输入");
            return r;
        }
        String packIdForDetail = partPackService.queryDetail(segNo, productionOrderCode);
//        String packIdForDetail = "1";
//        String packIdForProduct = partPackService.queryProduct(segNo, productionOrderCode, fProductId, fPackId);
        if (StringUtils.isEmpty(packIdForDetail)){
            r.setResultCode(9002);
            r.setResultMsg("无法获取捆包号，数据获取失败！");
        }
//        else if (StringUtils.isEmpty(packIdForProduct)){
//            r.setResultCode(9003);
//            r.setResultMsg("生产工单产出信息表没有对应数据");
//        }
        else {
            r.setResultCode(200);
            r.setResultMsg("查询成功！");
        }
//        String jsonStr = partPackService.queryPartPackInfo(packIdForDetail);
        r.setResultInfo(packIdForDetail);
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

    @RequestMapping("/getArmNum")
    public List<String> getArmNum(){
        List<String> list = new ArrayList<>();
        list.add("01");
        list.add("02");
        list.add("03");
        list.add("04");

        List<String> listSerialNum = partPackService.getArmNum();
        list.removeAll(listSerialNum);

//        List<Map> listmap = new ArrayList<>();
//        Map map = new HashMap();
//        for (int i = 1; i <= list.size(); i++) {
//            map.put("0"+list.get(i-1), "0"+list.get(i-1));
//            listmap.add(map);
//        }
        return list;
    }

    @RequestMapping("/getSerialNum")
    public List<String> getSerialNum(String armNum, String packId){
        List<String> listAllSerialNum = new ArrayList<>();
        listAllSerialNum.add("01");
        listAllSerialNum.add("02");
        listAllSerialNum.add("03");
        listAllSerialNum.add("04");
        listAllSerialNum.add("05");
        listAllSerialNum.add("06");
        listAllSerialNum.add("07");
        listAllSerialNum.add("08");
        listAllSerialNum.add("09");
        listAllSerialNum.add("10");
        listAllSerialNum.add("11");
        listAllSerialNum.add("12");
        List<String> listSerialNum = partPackService.getSerialNum(armNum);
        ProductInfoMap productInfoMap = partPackService.queryPartPackInfo(packId);
        listSerialNum.remove(productInfoMap.getSerialNum());
        listAllSerialNum.removeAll(listSerialNum);
//        listAllSerialNum.add(productInfoMap.getSerialNum());
        return listAllSerialNum;
    }
}
