package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.PartPackDao;
import com.example.demo.dao.TestDao;
import com.example.demo.entity.ProductInfoMap;
import com.example.demo.entity.ProductionOrderInfo;
import com.example.demo.entity.User;
import com.example.demo.service.TestService;
import com.example.demo.util.ExportExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

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


    @Autowired
    PartPackDao partPackDao;

    @Override
    public String testService(String id) {
        log.info("接口实现类！");
//        log.info("ID="+id);

        String name = testDao.queryName(id);
        return name;
    }

    @Override
    public String getUserName(String id) throws Exception {
        Map map = new HashMap();
        map.put("id",id);
        User user = new User();
        user.setId("1");

        JSONObject jsonId = new JSONObject(map);
//        String name = testDao.queryName(id);
        log.info(jsonId.toJSONString());
        String jsonObject = restTemplate.getForObject("http://127.0.0.1:8088/get?id=1", String.class);

        //===========
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
//        String dataStr = df.format(new Date());
        //410222195802221955
//        String phoneNo = "18839470006";
//        String cardNo = "410222195802221955";
//        String key="wekcv34234fg5i3d122023rd";
//        String key="5e6c3b3a3b9325f114693uat";

//        Random random = new Random();
//        int i = random.nextInt(9999)%10000;
//        String type = "VCMP";
//        String flowNumber = "VCMP"+dataStr+i;
//        System.out.println(flowNumber);
//        String enPhoneNo = RSAutil.encrypt(key, phoneNo);
//        String enCardNo = RSAutil.encrypt(key, cardNo);
//        String couponTypeId= "mj39";
//        String userid = "14289401";
//        String userid = "00000090587069";


        //couponTypeId=mj28&drawDate=20200319135710&certNo=360981198612111656&certType=01
//        String dateStr = "couponTypeId=" +couponTypeId + "&drawDate=" + dataStr + "&certType=01&certNo=" + cardNo;

//        String endateStr = RSAutil.encrypt(key,dateStr);
//        String data_en = URLEncoder.encode(endateStr, "utf-8");
//        String data_en_en = URLEncoder.encode(data_en,"utf-8");
//        System.out.println("http://115.29.143.43/receive/spdbinterface/SendCouponForQD?telePhone=" + enPhoneNo +
//                "&certType=01&certNo=" + enCardNo + "&type=" + type + "&flowNumber=" + flowNumber + "&userid" + userid +
//                "&data=" + data_en_en);



//        String jsonObject = restTemplate.getForObject("http://115.29.143.43/receive/spdbinterface/SendCouponForQD?telePhone=" + enPhoneNo + "&certType=01&certNo=" + enCardNo + "&type=" + type + "&flowNumber=" + flowNumber + "&userid=" + userid +
//                "&data=" + data_en_en, String.class);

        //==========
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

    @Override
    public List<Map> queryAllList() {
        List<Map> list = testDao.queryAllList();
        return list;
    }

    @Override
    public List<Map> queryPartList() {
        List<Map> list = testDao.queryPartList();
        return list;
    }

    @Autowired
    ExportExcelUtil exportExcelUtil;

    /**
     * 导出用户数据表
     * @param response
     * @param titleKeyList
     * @param titleMap
     * @param src_list
     */
    public void exportDataToEx(HttpServletResponse response, ArrayList<String> titleKeyList, Map<String, String> titleMap, List<Map> src_list) {
        try {
            exportExcelUtil.expoerDataExcel(response, titleKeyList, titleMap, src_list);
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
        }
    }

    /**
     * 生产工单明细表
     * @param segNo
     * @param productionOrderCode
     * @return
     */
    //http://10.30.91.76/:8088/gmsc-new-service/services/GmscWebService?wsdl
    public String queryDetail(String segNo, String productionOrderCode){
        ProductionOrderInfo productionOrderInfo = new ProductionOrderInfo();
        productionOrderInfo.setSegNo(segNo);
        productionOrderInfo.setProductionOrderCode(productionOrderCode);
        String productionOrderInfoObject = JSONObject.toJSONString(productionOrderInfo);
        String jsonObject = restTemplate.postForObject("http://10.30.184.236:80/LINKS/queryDetail", productionOrderInfoObject, String.class);
        log.info("生产工单明细表--接口返回数据："+jsonObject);

        JSONObject resultJSONObject = JSONObject.parseObject(jsonObject);
        JSONObject rmPartPackList = resultJSONObject.getJSONObject("rmPartPackList");//投料捆包信息
        log.info("投料捆包信息--rmPartPackList："+rmPartPackList);
        String packid = rmPartPackList.getString("packid");
        String factoryProductId = rmPartPackList.getString("factoryProductId");
        String spec = rmPartPackList.getString("spec");//规格
        String productTypeName = rmPartPackList.getString("productTypeName");
        String shopsign = rmPartPackList.getString("shopsign");
        String mwrapid = rmPartPackList.getString("mwrapid");//母卷号
        String stoveNum = rmPartPackList.getString("stoveNum");//炉号


        //confirmList
        JSONObject confirmList = resultJSONObject.getJSONObject("confirmList");
        String confirmDate = confirmList.getString("confirmDate");
        String confirmPerson = confirmList.getString("confirmPerson");//检验员（确认人）

        //dataInfo
        JSONObject dataInfo = resultJSONObject.getJSONObject("dataInfo");
        String machineId = dataInfo.getString("machineId");//机组代码

        //fmPartList
        JSONObject fmPartList = resultJSONObject.getJSONObject("fmPartList");
        String innerDiameter = fmPartList.getString("innerDiameter");//卷内径
        String outWidth = fmPartList.getString("outWidth");//卷宽度(立放时的宽度)
        String consigneeId = fmPartList.getString("consigneeId");//收货单位编码
        String consigneeName = fmPartList.getString("consigneeName");//收货单位名称
        String consigneeRemark = fmPartList.getString("consigneeRemark");//收货单位名称
        String outDiameter = fmPartList.getString("outDiameter");//卷外径

        ProductInfoMap productInfoMap = new ProductInfoMap();
        productInfoMap.setPackid(packid);
        productInfoMap.setFactoryProductid(factoryProductId);
        productInfoMap.setSpec(spec);
        productInfoMap.setProductTypeName(productTypeName);
        productInfoMap.setShopsign(shopsign);
        productInfoMap.setMwrapid(mwrapid);
        productInfoMap.setConfirmDate(confirmDate);
        productInfoMap.setConfirmPerson(confirmPerson);
        productInfoMap.setStoveNum(stoveNum);
        productInfoMap.setInnerDiameter(innerDiameter);
        productInfoMap.setOutDiameter(outDiameter);
        productInfoMap.setOutWidth(outWidth);
        productInfoMap.setConsigneeId(consigneeId);
        productInfoMap.setConsigneeName(consigneeName);
        productInfoMap.setConsigneeRemark(consigneeRemark);

        partPackDao.insertPartPack(productInfoMap);



        return jsonObject;
    }

    /**
     * 生产工单产出信息
     * @param segNo
     * @param productionOrderCode
     * @param fProductId
     * @param fPackId
     * @return
     */
    public  String queryProduct(String segNo, String productionOrderCode, String fProductId, String fPackId){

        ProductionOrderInfo productionOrderInfo = new ProductionOrderInfo();
        productionOrderInfo.setSegNo(segNo);
        productionOrderInfo.setProductionOrderCode(productionOrderCode);
        productionOrderInfo.setFProductId(fProductId);
        productionOrderInfo.setFPackId(fPackId);
        String productionOrderInfoObject = JSONObject.toJSONString(productionOrderInfo);
        String jsonObject = restTemplate.postForObject("http://10.30.184.236:80/LINKS/queryProduct", productionOrderInfoObject, String.class);

        JSONObject resultJSONObject = JSONObject.parseObject(jsonObject);
        JSONObject productInfo = resultJSONObject.getJSONObject("productInfo");
        String packid = productInfo.getString("packid");
        String partid = productInfo.getString("partid");
        String quantity = productInfo.getString("quantity");
        String qualityGradeName = productInfo.getString("qualityGradeName");
        String unitedPackid = productInfo.getString("unitedPackid");//并包号

        ProductInfoMap productInfoMap = new ProductInfoMap();
        productInfoMap.setPackid(packid);
        productInfoMap.setPartid(partid);
        productInfoMap.setQuantity(quantity);
        productInfoMap.setQualityGradeName(qualityGradeName);
        productInfoMap.setUnitedPackid(unitedPackid);
        partPackDao.updatePartPack(productInfoMap);
        //productInfo
        return jsonObject;
    }


    public String queryPartPackInfo(String packId){
        ProductInfoMap productInfoMap = partPackDao.queryPartPack(packId);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(productInfoMap);
        return jsonObject.toJSONString();
    }

    public String quaryPackagingData(String segNo, String productionOrderCode, String fProductId, String fPackId){
        ProductionOrderInfo productionOrderInfo = new ProductionOrderInfo();
        productionOrderInfo.setSegNo(segNo);
        productionOrderInfo.setProductionOrderCode(productionOrderCode);
        productionOrderInfo.setFProductId(fProductId);
        productionOrderInfo.setFPackId(fPackId);
        String productionOrderInfoObject = JSONObject.toJSONString(productionOrderInfo);
        String jsonObject = restTemplate.postForObject("http://127.0.0.1:8088/quaryPackagingData", productionOrderInfoObject, String.class);

        JSONObject resultJSONObject = JSONObject.parseObject(jsonObject);
        return jsonObject;
    }


    public List<ProductInfoMap> queryPartPackInfoList(String packId){
        List<ProductInfoMap> list = partPackDao.queryPartPackList(packId);
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
//        JSONObject jsonObject = (JSONObject) JSON.toJSON(list);
//        return array.toJSONString();
        return list;
    }
}
