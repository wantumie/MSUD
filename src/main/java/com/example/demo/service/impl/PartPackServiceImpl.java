package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.PartPackDao;
import com.example.demo.entity.ProductInfoMap;
import com.example.demo.entity.ProductionOrderInfo;
import com.example.demo.entity.R;
import com.example.demo.service.PartPackService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PartPackServiceImpl implements PartPackService {


    @Autowired
    PartPackDao partPackDao;

    public PageInfo<ProductInfoMap> queryPartPackInfoList(String packid, int pageNo, int pageSize){
        PageHelper.startPage(pageNo, pageSize);

        List<ProductInfoMap> productInfolist = partPackDao.queryPartPackList(packid);
        PageInfo<ProductInfoMap> list = new PageInfo<>(productInfolist);
//        JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
//        JSONObject jsonObject = (JSONObject) JSON.toJSON(list);
//        return array.toJSONString();
        return list;
    }


    public String queryPartPackInfo(String packId){
        ProductInfoMap productInfoMap = partPackDao.queryPartPack(packId);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(productInfoMap);
        return jsonObject.toJSONString();
    }

    @Override
    public void insertPartinfo(ProductInfoMap productInfoMap) {
        partPackDao.insertPartPack(productInfoMap);
    }

    @Override
    public void updatePartinfo(ProductInfoMap productInfoMap) {
        partPackDao.updatePartPack(productInfoMap);
    }


    /**
     * 生产工单明细表
     * @param segNo
     * @param productionOrderCode
     * @return
     */
    //http://10.30.91.76/:8088/gmsc-new-service/services/GmscWebService?wsdl
    public String queryDetail(String segNo, String productionOrderCode) {
//        R r = new R();
        ProductInfoMap productInfoMap = new ProductInfoMap();
        ProductionOrderInfo productionOrderInfo = new ProductionOrderInfo();
        productionOrderInfo.setSegNo(segNo);
        productionOrderInfo.setProductionOrderCode(productionOrderCode);

        JSONObject jsonProductionOrderInfo = (JSONObject) JSONObject.toJSON(productionOrderInfo);

        JSONObject jsonObjectSend = new JSONObject();
        jsonObjectSend.put("innerUri","JKProductionOrderService");
        jsonObjectSend.put("method", "queryDetail");
        jsonObjectSend.put("parameter",jsonProductionOrderInfo);
//        String productionOrderInfoObject = JSONObject.toJSONString(productionOrderInfo);
        System.out.println("发送数据："+jsonObjectSend.toJSONString());
        log.info("queryDetail请求数据："+jsonObjectSend.toJSONString());
        ////////////////////////////////////////////////////////////
        Object[] objects = new Object[0];
        JSONObject jsonObject = new JSONObject();
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient("http://10.30.91.76:8088/gmsc-new-service/services/GmscWebService?wsdl");
            // invoke("方法名",参数1,参数2,参数3....);
//            objects = client.invoke("webService", "传递的参数");
            objects = client.invoke("analyticalParm", jsonObjectSend.toJSONString());
            jsonObject = JSONObject.parseObject(objects[0].toString());
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            log.info("queryProduct请求失败！");
        }

        ////////////////////////////////////////////////////////////

//        String jsonStr = "{\"rmPartPackList\":[],\"cmpPackList\":[],\"dataInfo\":[{\"orderSpec\":\"1.6*520*C(生产数量:4,生产重量:21.712977吨)\",\"productionDemandSubId\":27435,\"productionOrderId\":25443,\"endDate\":\"2020-11-20 15:00:00\",\"businessName\":\"来料加工\",\"workingShift\":\"M\",\"remainWeight\":0,\"machineName\":\"\",\"businessTypeName\":\"\",\"productionOrderCode\":\"50Z201118009\",\"iecStatusName\":\"\",\"ifFirst\":\"1\",\"processSubnumber\":5,\"providerName\":\"\",\"productionDemandId\":21052,\"canReturnMaterial\":\"0\",\"preProductionDemandSubCode\":\"\",\"taxRate\":0.13,\"machineId\":\"Z1\",\"machPriceType\":\"\",\"machPriceAt\":0,\"modiDate\":\"2020-11-18 14:34:00\",\"prepareTime\":32,\"combineNo\":\"\",\"ifSettle\":\"\",\"iecStatus\":\"25\",\"orderFrom\":\"\",\"productionDemandCode\":\"50R201118029\",\"startDate\":\"2020-11-20 14:00:00\",\"status\":\"20\",\"numberNew\":\"Z12011205\",\"finStartDate\":\"\",\"modiPerson\":\"CDYG102289\",\"machPrice\":0,\"materialRate\":0,\"productProcessId\":\"Z\",\"agreementSubid\":\"\",\"deliveryTime\":0,\"ifMaterail\":\"1\",\"productProcessName\":\"纵切\",\"earchivesNo\":\"50SL01\",\"processNumber\":\"Z1201120\",\"preProductionDemandSubId\":0,\"remark\":\"1.客户：雅虎   2.交货期：11-22  .包装要求：单包重量不能超过8吨，单卷重量不超过5吨，请用钢带打包严实，每母卷取一张600*300mm的样板（请在生产实绩里标明样板所在捆包号并备注并包信息），附在成品上，卷料内径：480-510（mm），外径小于1400mm。只贴一张外标签，不贴内标签。\",\"rowKnifeId\":\"\",\"ifRoll\":\"J\",\"batchId\":\"20111815435138629\",\"afterPOrderId\":\"\",\"providerId\":\"\",\"agreementId\":\"\",\"statusName\":\"待执行\",\"deliveryDate\":\"2020-11-22 14:14:01\",\"productionLock\":\"\",\"auditDate\":\"\",\"createDate\":\"2020-11-18 14:14:16\",\"productionDemandSubCode\":\"50R201118029001\",\"finEndDate\":\"\",\"transferTime\":0,\"ifArrangeOnly\":\"1\",\"priceType\":\"\",\"recipeNo\":\"\",\"createPerson\":\"CDYG102289\",\"userName\":\"王霞\",\"suitVoucherNum\":\"\",\"ifSettleFinish\":\"\",\"lockFlag\":0,\"remainQty\":0,\"segNo\":\"00112\",\"teamId\":\"10\",\"wproviderName\":\"\",\"auditPerson\":\"\",\"businessType\":\"30\",\"productionTime\":27,\"processRate\":1}],\"fmPartList\":[{\"productionDemandSubId\":27435,\"productionOrderId\":25443,\"eachQty\":1,\"remainWeight\":0,\"spec\":\"1.6*520*C\",\"packingTypeId\":\"17D\",\"productDemandCode\":\"\",\"remarkInfo\":\"最终成品规格:1.6*520*C 厚:  0.00/  0.00 宽: -0.15/  0.15 长:  0.00/  0.00,镰弯刀:,对角线：,毛刺：,平坦度：,计量方式：实际计重,技术标准：,好面朝向：, 质量等级：正品,特殊说明：无\",\"productionOrderCode\":\"50Z201118009\",\"productTypeName\":\"冷轧板卷\",\"demandWeight\":0,\"outputOrderId\":31799,\"contractSubid\":\"X201100031001\",\"processUseWeight\":0,\"insideDiameter\":508,\"productionDemandId\":21052,\"modiDate\":\"2020-11-18 14:15:20\",\"demandQty\":0,\"qtyUnit\":\"20\",\"productionDemandCode\":\"50R201118029\",\"pOutput\":2,\"status\":\"05\",\"weightUnit\":\"10\",\"productDemandId\":0,\"modiPerson\":\"CDYG102289\",\"partId\":\"LJ18092700001\",\"demandUseQty\":0,\"remark\":\"MBOM手工生成\",\"shopsign\":\"HC420/780DP\",\"customerId\":\"T01492\",\"outputQty\":4,\"createDate\":\"2020-11-18 14:14:16\",\"processUseQty\":0,\"productionDemandSubCode\":\"50R201118029001\",\"eachWeight\":0,\"demandUseWeight\":0,\"createPerson\":\"CDYG102289\",\"stockUseQty\":0,\"productDeciPlace\":\"3\",\"sample\":\"\",\"suitVoucherNum\":\"\",\"productTypeId\":\"TL11\",\"outputWeight\":21.712977,\"packingTypeName\":\"简包装4（卧卷，防锈纸全封闭，周向钢带捆扎，内圈衬纸防护）\",\"remainQty\":0,\"segNo\":\"00112\",\"lackProductionId\":\"\",\"contractId\":\"X201100031\",\"eachPackWeight\":0,\"stockUseWeight\":0}],\"msgDetail\":\"查询成功\",\"rmQualityDescList\":[{\"modiPerson\":\"\",\"productionDemandSubId\":27435,\"unusedPlateCount\":\"\",\"productionOrderId\":25443,\"partId\":\"LJ16032900001\",\"chargePlat\":\"\",\"qualityGradeName\":\"\",\"remark\":\"指定卷料封锁捆包\",\"shopsign\":\"HC420/780DP\",\"putinQty\":\"\",\"voucherNum\":\"\",\"spec\":\"1.6*1045*C\",\"materialLock\":\"10\",\"productionOrderCode\":\"50Z201118009\",\"packStatusName\":\"\",\"productTypeName\":\"\",\"unPackWeightKg\":\"\",\"materailOrderId\":59143,\"createDate\":\"2020-11-18 14:15:20\",\"productionDemandSubCode\":\"50R201118029001\",\"productId\":\"TL11201000146\",\"packWeight\":10.52,\"createPerson\":\"CDYG102289\",\"qualityDesc\":\"质量相关：无,质 量描述\",\"packWeightKg\":\"\",\"putinWeight\":\"\",\"productDeciPlace\":\"\",\"productTypeId\":\"TL11\",\"productionDemandId\":21052,\"packId\":\"10546850802\",\"segNo\":\"00112\",\"modiDate\":\"\",\"packQty\":1,\"factoryProductId\":\"\",\"productionDemandCode\":\"50R201118029\",\"mwrapId\":\"\",\"lableId\":\"\",\"status\":\"10\"},{\"modiPerson\":\"\",\"productionDemandSubId\":27435,\"unusedPlateCount\":\"\",\"productionOrderId\":25443,\"partId\":\"LJ16032900001\",\"chargePlat\":\"\",\"qualityGradeName\":\"\",\"remark\":\"指定卷料封锁捆包\",\"shopsign\":\"HC420/780DP\",\"putinQty\":\"\",\"voucherNum\":\"\",\"spec\":\"1.6*1045*C\",\"materialLock\":\"10\",\"productionOrderCode\":\"50Z201118009\",\"packStatusName\":\"\",\"productTypeName\":\"\",\"unPackWeightKg\":\"\",\"materailOrderId\":59144,\"createDate\":\"2020-11-18 14:15:20\",\"productionDemandSubCode\":\"50R201118029001\",\"productId\":\"TL11201001052\",\"packWeight\":11.36,\"createPerson\":\"CDYG102289\",\"qualityDesc\":\"质量相关：无,质量描述\",\"packWeightKg\":\"\",\"putinWeight\":\"\",\"productDeciPlace\":\"\",\"productTypeId\":\"TL11\",\"productionDemandId\":21052,\"packId\":\"10547490311\",\"segNo\":\"00112\",\"modiDate\":\"\",\"packQty\":1,\"factoryProductId\":\"\",\"productionDemandCode\":\"50R201118029\",\"mwrapId\":\"\",\"lableId\":\"\",\"status\":\"10\"}],\"rmPartList\":[{\"modiPerson\":\"\",\"productionDemandSubId\":27435,\"inputQty\":2,\"eachWeight\":0,\"productionOrderId\":25443,\"partId\":\"LJ16032900001\",\"createPerson\":\"CDYG102289\",\"remark\":\"MBOM手工生成\",\"shopsign\":\"HC420/780DP\",\"suitVoucherNum\":\"\",\"productTypeId\":\"TL11\",\"productionDemandId\":21052,\"spec\":\"1.6*1048*C\",\"segNo\":\"00112\",\"modiDate\":\"2020-11-18 14:15:20\",\"productionOrderCode\":\"50Z201118009\",\"productTypeName\":\"冷轧板卷\",\"productionDemandCode\":\"50R201118029\",\"rinput\":1,\"inputWeight\":21.88,\"createDate\":\"2020-11-18 14:14:16\",\"productionDemandSubCode\":\"50R201118029001\",\"status\":\"05\"}],\"msgCode\":\"1\",\"sumInfo\":{\"lockWeightTotal\":\"21880.000\",\"residualWeightTotal\":\"0.000\",\"diffWeightTotal\":\"0.000\",\"scrapWeightTotal\":\"0.000\",\"productionWeightTotal\":\"0.000\",\"inputWeightTotal\":\"0.000\",\"returnWeightTotal\":\"0.000\"}}";
//        JSONObject jsonObject1 = JSONObject.parseObject(jsonStr);
        log.info("生产工单明细表--接口返回数据："+JSONObject.toJSONString(jsonObject));

        JSONObject resultJSONObject = jsonObject;
        String packid = null;
        String partId = null;
        String factoryProductId = null;
        String spec = null;
        String productTypeName = null;
        String shopsign = null;
        String mwrapid = null;
        String stoveNum = null;
        String qualityGradeName = null;
        if (resultJSONObject.size() != 0 && !"0".equals(resultJSONObject.get("msgCode"))){
//            JSONArray rmPartPackListJson = resultJSONObject.getJSONArray("rmPartPackList");
//            JSONObject rmPartPackList = new JSONObject();
//            if(rmPartPackListJson.size() > 0){
//                rmPartPackList = rmPartPackListJson.getJSONObject(0);
//                        JSONObject rmPartPackList = resultJSONObject.getJSONObject("rmPartPackList");//投料捆包信息
//                log.info("投料捆包信息--rmPartPackList："+rmPartPackList);
//                packid = rmPartPackList.getString("packid");
//                partId = rmPartPackList.getString("partId");
//                factoryProductId = rmPartPackList.getString("factoryProductId");
//                spec = rmPartPackList.getString("spec");//规格
//                productTypeName = rmPartPackList.getString("productTypeName");
//                shopsign = rmPartPackList.getString("shopsign");
//                mwrapid = rmPartPackList.getString("mwrapid");//母卷号
//                stoveNum = rmPartPackList.getString("stoveNum");//炉号
//                qualityGradeName = rmPartPackList.getString("qualityGradeName");//炉号
//            }

            //dataInfo
//            JSONArray dataInfoJson = resultJSONObject.getJSONArray("dataInfo");
//            JSONObject dataInfo = new JSONObject();
            String machineId = null;
            String modiDate = null;
            String modiTime = null;
            String modiPerson = null;
//            if(dataInfoJson.size() > 0){
//                dataInfo = dataInfoJson.getJSONObject(0);
//
//                String modiDateTime = dataInfo.getString("modiDate");//确认时间

//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                modiDate = String.format("%tF", sdf.parse(modiDateTime));
//                modiTime = String.format("%tT", sdf.parse(modiDateTime));
//                modiPerson = dataInfo.getString("modiPerson");//检验员（确认人）
//                machineId = dataInfo.getString("machineId");//机组代码

//            }

            //fmPartList
//            JSONArray fmPartListJson = resultJSONObject.getJSONArray("fmPartList");
//            JSONObject fmPartList = new JSONObject();
            String innerDiameter = null;
            String outWidth = null;
            String consigneeId = null;
            String consigneeName = null;
            String consigneeRemark = null;
            String outDiameter = null;
//            if(fmPartListJson.size() > 0){
//                fmPartList = fmPartListJson.getJSONObject(0);
//
//                innerDiameter = fmPartList.getString("innerDiameter");//卷内径
//                outWidth = fmPartList.getString("outWidth");//卷宽度(立放时的宽度)
//                consigneeId = fmPartList.getString("consigneeId");//收货单位编码
//                consigneeName = fmPartList.getString("consigneeName");//收货单位名称
//                consigneeRemark = fmPartList.getString("consigneeRemark");//收货单位名称
//                outDiameter = fmPartList.getString("outDiameter");//卷外径
//            }
        JSONArray rmQualityDescJson = resultJSONObject.getJSONArray("rmQualityDescList");

        try {
            for (int i = 0; i < rmQualityDescJson.size(); i++) {
                JSONObject rmQualityDesc = rmQualityDescJson.getJSONObject(i);
                if (rmQualityDescJson.size() > 0){
                    String modiDateTime = rmQualityDesc.getString("createDate");//确认时间
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    modiDate = String.format("%tF", sdf.parse(modiDateTime));
                    modiTime = String.format("%tT", sdf.parse(modiDateTime));

                    packid = rmQualityDesc.getString("packId");
                    productInfoMap.setPackId(rmQualityDesc.getString("packId"));
                    productInfoMap.setPartId(rmQualityDesc.getString("partId"));
                    productInfoMap.setFactoryProductid(rmQualityDesc.getString("factoryProductId"));
                    productInfoMap.setSpec(rmQualityDesc.getString("spec"));
                    productInfoMap.setProductTypeName(rmQualityDesc.getString("productTypeName"));
                    productInfoMap.setShopsign(rmQualityDesc.getString("shopsign"));
                    productInfoMap.setMwrapid(rmQualityDesc.getString("mwrapid"));
                    productInfoMap.setModiDate(rmQualityDesc.getString("modiDate"));
                    productInfoMap.setModiPerson(rmQualityDesc.getString("modiPerson"));
                    productInfoMap.setProductTypeId(rmQualityDesc.getString("productTypeId"));
                    productInfoMap.setProductTypeName(rmQualityDesc.getString("productTypeName"));
                    productInfoMap.setPutinQty(rmQualityDesc.getString("putinQty"));
                    productInfoMap.setModiDate(modiDate);
                    productInfoMap.setModiTime(modiTime);
                    partPackDao.insertPartPack(productInfoMap);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            packid = null;
        }


//            ProductInfoMap productInfoMap = new ProductInfoMap();
//            productInfoMap.setPackId(packid);
//            productInfoMap.setPartId(partId);
//            productInfoMap.setFactoryProductid(factoryProductId);
//            productInfoMap.setSpec(spec);
//            productInfoMap.setProductTypeName(productTypeName);
//            productInfoMap.setShopsign(shopsign);
//            productInfoMap.setMwrapid(mwrapid);
//            productInfoMap.setModiDate(modiDate);
//            productInfoMap.setModiPerson(modiPerson);
//            productInfoMap.setStoveNum(stoveNum);
//            productInfoMap.setInnerDiameter(innerDiameter);
//            productInfoMap.setOutDiameter(outDiameter);
//            productInfoMap.setOutWidth(outWidth);
//            productInfoMap.setConsigneeId(consigneeId);
//            productInfoMap.setConsigneeName(consigneeName);
//            productInfoMap.setConsigneeRemark(consigneeRemark);
//            productInfoMap.setMachineId(machineId);
//            productInfoMap.setQualityGradeName(qualityGradeName);

//            if (StringUtils.isEmpty(packid)){
//                partPackDao.insertPartPack(productInfoMap);
//            }

        }
        return packid;
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
        JSONObject jsonProductionOrderInfo = (JSONObject) JSONObject.toJSON(productionOrderInfo);
        JSONObject jsonObjectSend = new JSONObject();
        jsonObjectSend.put("innerUri","JKProductionOrderService");
        jsonObjectSend.put("method", "queryProduct");
        jsonObjectSend.put("parameter",jsonProductionOrderInfo);
        log.info("queryProduct请求数据："+jsonObjectSend.toJSONString());
       ////////////////////////////////////////////////////////////
         // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        Object[] objects = new Object[0];
        JSONObject jsonObject = new JSONObject();
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient("http://10.30.91.76:8088/gmsc-new-service/services/GmscWebService?wsdl");
            // invoke("方法名",参数1,参数2,参数3....);
//            objects = client.invoke("webService", "传递的参数");
            objects = client.invoke("analyticalParm", jsonObjectSend.toJSONString());
            System.out.println("返回数据:" + objects[0]);
            jsonObject = JSONObject.parseObject(objects[0].toString());
        } catch (java.lang.Exception e) {
//            e.printStackTrace();
            log.info("queryProduct请求失败！");
        }
        ////////////////////////////////////////////////////////////
        log.info("生产工单产出信息表--接口返回数据："+JSONObject.toJSONString(jsonObject));
        JSONObject resultJSONObject = jsonObject;
        String packid = null;
        if (resultJSONObject.size() != 0 && !"0".equals(resultJSONObject.get("msgCode"))){
            JSONObject productInfo = resultJSONObject.getJSONObject("productInfo");
            packid = productInfo.getString("packid");
//            String partid = productInfo.getString("partid");
            String quantity = productInfo.getString("quantity");
//            String qualityGradeName = productInfo.getString("qualityGradeName");
            String unitedPackid = productInfo.getString("unitedPackid");//并包号

            ProductInfoMap productInfoMap = new ProductInfoMap();
            productInfoMap.setPackId(packid);
//            productInfoMap.setPartId(partid);
            productInfoMap.setQuantity(quantity);
//            productInfoMap.setQualityGradeName(qualityGradeName);
            productInfoMap.setUnitedPackid(unitedPackid);
            partPackDao.updatePartPack(productInfoMap);
        }
        return packid;
    }
}
