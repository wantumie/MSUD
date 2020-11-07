package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.PartPackDao;
import com.example.demo.dao.TestDao;
import com.example.demo.entity.ProductInfoMap;
import com.example.demo.entity.ProductionOrderInfo;
import com.example.demo.entity.R;
import com.example.demo.entity.User;
import com.example.demo.service.TestService;
import com.example.demo.util.ExportExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
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
    public List<Map> queryPartList(String partId) {
        List<Map> list = testDao.queryPartList(partId);
        return list;
    }

    @Override
    public List<Map> queryPartinfo(String partId) {
        List<Map> list = testDao.queryPartinfo(partId);
        return list;
    }

    @Override
    public void updatePartinfo(String partId, String spec) {
        ProductInfoMap productInfoMap = new ProductInfoMap();
        productInfoMap.setPartId(partId);
        productInfoMap.setSpec(spec);
        testDao.updatePartinfo(productInfoMap);
    }

    @Override
    public void insertPartinfo(String partId, String spec) {
        ProductInfoMap productInfoMap = new ProductInfoMap();
        productInfoMap.setPartId(partId);
        productInfoMap.setSpec(spec);
//        testDao.insertPartinfo(productInfoMap);
        partPackDao.insertPartPack(productInfoMap);
    }

    @Override
    public String wsService() {

        ////////////////////////////////////////////////////////////
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://127.0.0.1:8088/greet?wsdl");
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
//            objects = client.invoke("webService", "传递的参数");
            objects = client.invoke("greeting", "productionOrderInfoObject");
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

        ////////////////////////////////////////////////////////////



        return null;
    }


    public String sendWsdl(Object obj) {
        log.info("--------调用webservice接口begin-------");
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();

        //对方的wsdl地址
        Client client = dcf.createClient("http://127.0.0.1:8088/greet?wsdl");
        String json = null;
        try {

//            QName qName = new QName("http://xx.zygxsq.cn/", "getAlarmWs");                                                //*原文章链接：https://blog.csdn.net/qq_27471405/article/details/105275657     * 其他均为盗版，公众号：灵儿的笔记(zygxsq)
            Object[] objects1= client.invoke("greeting", "aaa"); //参数1，参数2，参数3......按顺序放就看可以

            json = JSONObject.toJSONString(objects1[0]);
            System.out.println("返回数据:" + json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            log.info("服务器断开连接，请稍后再试");
        }
        log.info("--------调用webservice接口end-------");
        return json;


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


//http://127.0.0.1:8088/greet?wsdl

    public String testJson(){
        String jsonStr = "{\"rmPartPackList\":[],\"cmpPackList\":[],\"dataInfo\":[{\"afterPOrderId\":\"\",\"agreementId\":\"MA20003\",\"agreementSubid\":\"MA20003001\",\"auditDate\":\"\",\"auditPerson\":\"\",\"batchId\":\"\",\"businessName\":\"来料加工\"," +
                "\"businessType\":\"30\",\"businessTypeName\":\"\",\"canReturnMaterial\":\"0\",\"combineNo\":\"\",\"createDate\":\"2020-10-29 10:56:08\",\"createPerson\":\"admin\",\"deliveryDate\":\"2020-10-29 10:50:00\",\"deliveryTime\":0,\"earchivesNo\":\"\"," +
                "\"endDate\":\"2020-10-29 11:07:03\",\"finEndDate\":\"\",\"finStartDate\":\"\",\"iecStatus\":\"25\",\"iecStatusName\":\"\",\"ifArrangeOnly\":\"\",\"ifFirst\":\"0\",\"ifMaterail\":\"\",\"ifRoll\":\"J\",\"ifSettle\":\"1\",\"ifSettleFinish\":\"\"," +
                "\"lockFlag\":1,\"machPrice\":21.551724,\"machPriceAt\":25,\"machPriceType\":\"10\",\"machineId\":\"WW-LHWJ\",\"machineName\":\"\",\"materialRate\":0,\"modiDate\":\"2020-10-29 11:00:33\",\"modiPerson\":\"admin\",\"numberNew\":\"\",\"orderFrom\":\"\"," +
                "\"orderSpec\":\"1.2*450*800(生产数量:2654,生产重量:8.999714吨)\",\"preProductionDemandSubCode\":\"\",\"preProductionDemandSubId\":0,\"prepareTime\":0,\"priceType\":\"10\",\"processNumber\":\"\",\"processRate\":1,\"processSubnumber\":0," +
                "\"productProcessId\":\"H\",\"productProcessName\":\"横切\",\"productionDemandCode\":\"34R201029001\",\"productionDemandId\":38601,\"productionDemandSubCode\":\"34R201029001001\",\"productionDemandSubId\":37841,\"productionLock\":\"\"," +
                "\"productionOrderCode\":\"34H201029001\",\"productionOrderId\":27701,\"productionTime\":0,\"providerId\":\"P80805\",\"providerName\":\"东莞市蓝海五金科技有限公司\",\"recipeNo\":\"\",\"remainQty\":0,\"remainWeight\":0,\"remark\":\"MBOM手工生成\"," +
                "\"rowKnifeId\":\"\",\"segNo\":\"00118\",\"startDate\":\"2020-10-29 11:07:03\",\"status\":\"20\",\"statusName\":\"待执行\",\"suitVoucherNum\":\"\",\"taxRate\":0.16,\"teamId\":\"10\",\"transferTime\":0,\"userName\":\"\",\"workingShift\":\"M\"," +
                "\"wproviderName\":\"\"}],\"fmPartList\":[{\"contractId\":\"X201000001\",\"contractSubid\":\"X2010000010001\",\"createDate\":\"2020-10-29 10:56:08\",\"createPerson\":\"admin\",\"customerId\":\"G0480\",\"demandQty\":0,\"demandUseQty\":0," +
                "\"demandUseWeight\":0,\"demandWeight\":0,\"eachPackWeight\":0,\"eachQty\":0,\"eachWeight\":0.003391,\"insideDiameter\":0,\"lackProductionId\":\"\",\"modiDate\":\"2020-10-29 10:56:30\",\"modiPerson\":\"admin\",\"outputOrderId\":35361," +
                "\"outputQty\":2654,\"outputWeight\":8.999714,\"pOutput\":1,\"packingTypeId\":\"32\",\"packingTypeName\":\"精包G,卷,纵向捆扎2道+防锈纸+薄膜（东莞茂森）\",\"partId\":\"LJ20102300003\",\"processUseQty\":0,\"processUseWeight\":0,\"productDeciPlace\":\"6\"," +
                "\"productDemandCode\":\"\",\"productDemandId\":0,\"productTypeId\":\"TL11\",\"productTypeName\":\"冷轧板卷\",\"productionDemandCode\":\"34R201029001\",\"productionDemandId\":38601,\"productionDemandSubCode\":\"34R201029001001\"," +
                "\"productionDemandSubId\":37841,\"productionOrderCode\":\"34H201029001\",\"productionOrderId\":27701,\"qtyUnit\":\"K0\",\"remainQty\":0,\"remainWeight\":0,\"remark\":\"MBOM手工生成\"," +
                "\"remarkInfo\":\"最终成品规格:1.2*450*800 厚:  0.00/  0.00 宽:  0.00/  0.00 长:  0.00/  0.00,镰弯刀:,对角线：,毛刺：,平坦度：,计量方式：实际计重,技术标准：,好面朝向：,质量等级：正品,特殊说明：无\",\"sample\":\"\",\"segNo\":\"00118\",\"shopsign\":\"CR210BH\"," +
                "\"spec\":\"1.2*450*800\",\"status\":\"05\",\"stockUseQty\":0,\"stockUseWeight\":0,\"suitVoucherNum\":\"\",\"weightUnit\":\"10\"}],\"msgDetail\":\"查询成功\",\"rmQualityDescList\":[],\"rmPartList\":[{\"createDate\":\"2020-10-29 10:56:08\"," +
                "\"createPerson\":\"admin\",\"eachWeight\":0,\"inputQty\":2,\"inputWeight\":9,\"modiDate\":\"2020-10-29 10:56:30\",\"modiPerson\":\"\",\"partId\":\"LJ20102300002\",\"productTypeId\":\"TL11\",\"productTypeName\":\"冷轧板卷\"," +
                "\"productionDemandCode\":\"34R201029001\",\"productionDemandId\":38601,\"productionDemandSubCode\":\"34R201029001001\",\"productionDemandSubId\":37841,\"productionOrderCode\":\"34H201029001\",\"productionOrderId\":27701," +
                "\"remark\":\"MBOM手工生成\",\"rinput\":0.003391,\"segNo\":\"00118\",\"shopsign\":\"CR210BH\",\"spec\":\"1.2*450*C\",\"status\":\"05\",\"suitVoucherNum\":\"\"}],\"confirmList\":null,\"msgCode\":\"1\",\"sumInfo\":\"\"}";

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        JSONArray fmPartList = jsonObject.getJSONArray("fmPartList");
        if(fmPartList.size() > 0){
            for (int i = 0; i < fmPartList.size(); i++) {
                JSONObject jsonObjectFMPart = fmPartList.getJSONObject(i);
                System.out.println("遍历jsonArray,获取数组中的name属性值："+jsonObjectFMPart.get("remarkInfo"));
            }
        }
        System.out.println(fmPartList.toJSONString());

//        JSONArray jsonArray = new JSONArray(json);
//        System.out.println("String转JSONArray: "+jsonArray);
        System.out.println();
        return  fmPartList.toJSONString();
    }


}
