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
    public String queryDetail(String segNo, String productionOrderCode) throws ParseException {
//        R r = new R();
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
//        try {
//            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//            Client client = dcf.createClient("http://10.30.91.76:8088/gmsc-new-service/services/GmscWebService?wsdl");
//            // invoke("方法名",参数1,参数2,参数3....);
////            objects = client.invoke("webService", "传递的参数");
//            objects = client.invoke("analyticalParm", jsonObjectSend.toJSONString());
//            jsonObject = JSONObject.parseObject(objects[0].toString());
//        } catch (java.lang.Exception e) {
//            e.printStackTrace();
//            log.info("queryProduct请求失败！");
//        }

        ////////////////////////////////////////////////////////////

//        String jsonStr = "{\"rmPartPackList\":[],\"cmpPackList\":[],\"dataInfo\":[{\"orderSpec\":\"0.5*60*C(生产数量:10,生产重量:0.576000吨)\",\"productionDemandSubId\":27343,\"productionOrderId\":25351,\"endDate\":\"2020-11-18 19:50:00\",\"businessName\":\"来料加工\",\"workingShift\":\"M\",\"remainWeight\":0,\"machineName\":\"\",\"businessTypeName\":\"\",\"productionOrderCode\":\"50Z201117002\",\"iecStatusName\":\"\",\"ifFirst\":\"1\",\"processSubnumber\":9,\"providerName\":\"\",\"productionDemandId\":20963,\"canReturnMaterial\":\"0\",\"preProductionDemandSubCode\":\"\",\"taxRate\":0.13,\"machineId\":\"Z1\",\"machPriceType\":\"\",\"machPriceAt\":0,\"modiDate\":\"2020-11-17 09:52:38\",\"prepareTime\":22,\"combineNo\":\"\",\"ifSettle\":\"\",\"iecStatus\":\"25\",\"orderFrom\":\"\",\"productionDemandCode\":\"50R201117003\",\"startDate\":\"2020-11-18 19:00:00\",\"status\":\"20\",\"numberNew\":\"Z12011189\",\"finStartDate\":\"\",\"modiPerson\":\"CDYG153\",\"machPrice\":0,\"materialRate\":0,\"productProcessId\":\"Z\",\"agreementSubid\":\"\",\"deliveryTime\":0,\"ifMaterail\":\"1\",\"productProcessName\":\"纵切\",\"earchivesNo\":\"50SL01\",\"processNumber\":\"Z1201118\",\"preProductionDemandSubId\":0,\"remark\":\"1、客户：宝 钰；2、交货期：11-18-09：00；3、包装方式：5条打一包，立卷，防锈纸+木架+钢带；4、全重交货，尾料返还，尾料单独打包放一边，要做金属平衡。注意：公差-0.1mm,。\",\"rowKnifeId\":\"\",\"ifRoll\":\"J\",\"batchId\":\"20111714094152350\",\"afterPOrderId\":\"\",\"providerId\":\"\",\"agreementId\":\"\",\"statusName\":\"待执行\",\"deliveryDate\":\"2020-11-18 09:24:24\",\"productionLock\":\"\",\"auditDate\":\"\",\"createDate\":\"2020-11-17 09:28:42\",\"productionDemandSubCode\":\"50R201117003001\",\"finEndDate\":\"\",\"transferTime\":0,\"ifArrangeOnly\":\"1\",\"priceType\":\"\",\"recipeNo\":\"\",\"createPerson\":\"CDYG153\",\"userName\":\"龙张燕\",\"suitVoucherNum\":\"\",\"ifSettleFinish\":\"\",\"lockFlag\":0,\"remainQty\":0,\"segNo\":\"00112\",\"teamId\":\"10\",\"wproviderName\":\"\",\"auditPerson\":\"\",\"businessType\":\"30\",\"productionTime\":21,\"processRate\":1}],\"fmPartList\":[{\"productionDemandSubId\":27343,\"productionOrderId\":25351,\"eachQty\":1,\"remainWeight\":0,\"spec\":\"0.5*60*C\",\"packingTypeId\":\"17G\",\"productDemandCode\":\"\",\"remarkInfo\":\"最终成品规格:0.5*60*C 厚:  0.00/  0.00 宽: -0.15/  0.15 长:  0.00/  0.00,镰弯刀:,对角线：,毛刺：,平坦度：,计量方式：实际计重,技术标准：,好面朝向：,质量等级：正品,特殊说明：无\",\"productionOrderCode\":\"50Z201117002\",\"productTypeName\":\"冷轧板卷\",\"demandWeight\":0,\"outputOrderId\":31707,\"contractSubid\":\"X200900077001\",\"processUseWeight\":0,\"insideDiameter\":508,\"productionDemandId\":20963,\"modiDate\":\"2020-11-17 09:29:16\",\"demandQty\":0,\"qtyUnit\":\"20\",\"productionDemandCode\":\"50R201117003\",\"pOutput\":10,\"status\":\"05\",\"weightUnit\":\"10\",\"productDemandId\":0,\"modiPerson\":\"CDYG153\",\"partId\":\"LJ20072300020\",\"demandUseQty\":0,\"remark\":\"MBOM手工生成\",\"shopsign\":\"DC04\",\"customerId\":\"T02663\",\"outputQty\":10,\"createDate\":\"2020-11-17 09:28:42\",\"processUseQty\":0,\"productionDemandSubCode\":\"50R201117003001\",\"eachWeight\":0,\"demandUseWeight\":0,\"createPerson\":\"CDYG153\",\"stockUseQty\":0,\"productDeciPlace\":\"3\",\"sample\":\"\",\"suitVoucherNum\":\"\",\"productTypeId\":\"TL11\",\"outputWeight\":0.576,\"packingTypeName\":\"简包装7（立卷，防锈纸全封闭，周向、径向捆带捆扎，内圈衬纸防护，底座“井”字木架）\",\"remainQty\":0,\"segNo\":\"00112\",\"lackProductionId\":\"\",\"contractId\":\"X200900077\",\"eachPackWeight\":0,\"stockUseWeight\":0}],\"msgDetail\":\"查 询成功\",\"rmQualityDescList\":[{\"modiPerson\":\"\",\"productionDemandSubId\":27343,\"unusedPlateCount\":\"\",\"productionOrderId\":25351,\"partId\":\"LJ20072300021\",\"chargePlat\":\"\",\"qualityGradeName\":\"\",\"remark\":\"指定卷料封锁捆包\",\"shopsign\":\"DC04\",\"putinQty\":\"\",\"voucherNum\":\"\",\"spec\":\"0.5*625*C\",\"materialLock\":\"10\",\"productionOrderCode\":\"50Z201117002\",\"packStatusName\":\"\",\"productTypeName\":\"\",\"unPackWeightKg\":\"\",\"materailOrderId\":58938,\"createDate\":\"2020-11-17 09:29:16\",\"productionDemandSubCode\":\"50R201117003001\",\"productId\":\"TL11200800039\",\"packWeight\":0.6,\"createPerson\":\"CDYG153\",\"qualityDesc\":\"质量相关：无,质量描述\",\"packWeightKg\":\"\",\"putinWeight\":\"\",\"productDeciPlace\":\"\",\"productTypeId\":\"TL11\",\"productionDemandId\":20963,\"packId\":\"PSE20707034\",\"segNo\":\"00112\",\"modiDate\":\"\",\"packQty\":1,\"factoryProductId\":\"\",\"productionDemandCode\":\"50R201117003\",\"mwrapId\":\"\",\"lableId\":\"\",\"status\":\"10\"}],\"rmPartList\":[{\"modiPerson\":\"\",\"productionDemandSubId\":27343,\"inputQty\":1,\"eachWeight\":0,\"productionOrderId\":25351,\"partId\":\"LJ20072300021\",\"createPerson\":\"CDYG153\",\"remark\":\"MBOM手工生成\",\"shopsign\":\"DC04\",\"suitVoucherNum\":\"\",\"productTypeId\":\"TL11\",\"productionDemandId\":20963,\"spec\":\"0.5*625*C\",\"segNo\":\"00112\",\"modiDate\":\"2020-11-17 09:29:16\",\"productionOrderCode\":\"50Z201117002\",\"productTypeName\":\"冷轧板卷\",\"productionDemandCode\":\"50R201117003\",\"rinput\":1,\"inputWeight\":0.6,\"createDate\":\"2020-11-17 09:28:42\",\"productionDemandSubCode\":\"50R201117003001\",\"status\":\"05\"}],\"msgCode\":\"1\",\"sumInfo\":{\"lockWeightTotal\":\"600.000\",\"residualWeightTotal\":\"0.000\",\"diffWeightTotal\":\"0.000\",\"scrapWeightTotal\":\"0.000\",\"productionWeightTotal\":\"0.000\",\"inputWeightTotal\":\"0.000\",\"returnWeightTotal\":\"0.000\"}}";
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
            JSONArray rmPartPackListJson = resultJSONObject.getJSONArray("rmPartPackList");
            JSONObject rmPartPackList = new JSONObject();
            if(rmPartPackListJson.size() > 0){
                rmPartPackList = rmPartPackListJson.getJSONObject(0);
                //        JSONObject rmPartPackList = resultJSONObject.getJSONObject("rmPartPackList");//投料捆包信息
                log.info("投料捆包信息--rmPartPackList："+rmPartPackList);
                packid = rmPartPackList.getString("packid");
                partId = rmPartPackList.getString("partId");
                factoryProductId = rmPartPackList.getString("factoryProductId");
                spec = rmPartPackList.getString("spec");//规格
                productTypeName = rmPartPackList.getString("productTypeName");
                shopsign = rmPartPackList.getString("shopsign");
                mwrapid = rmPartPackList.getString("mwrapid");//母卷号
                stoveNum = rmPartPackList.getString("stoveNum");//炉号
                qualityGradeName = rmPartPackList.getString("qualityGradeName");//炉号
            }





            //dataInfo
            JSONArray dataInfoJson = resultJSONObject.getJSONArray("dataInfo");
            JSONObject dataInfo = new JSONObject();
            String machineId = null;
            String modiDate = null;
            String modiTime = null;
            String modiPerson = null;
            if(dataInfoJson.size() > 0){
                dataInfo = dataInfoJson.getJSONObject(0);

                String modiDateTime = dataInfo.getString("modiDate");//确认时间
                //"2020-11-18 19:50:00"
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
//                modiDate = String.valueOf(sdf.parse(modiDateTime));
                modiDate = String.format("%tF", sdf.parse(modiDateTime));
//                modiTime = String.valueOf(sdf1.parse(modiDateTime));
                modiTime = String.format("%tT", sdf.parse(modiDateTime));
                modiPerson = dataInfo.getString("modiPerson");//检验员（确认人）
                machineId = dataInfo.getString("machineId");//机组代码

            }

            //fmPartList
            JSONArray fmPartListJson = resultJSONObject.getJSONArray("fmPartList");
            JSONObject fmPartList = new JSONObject();
            String innerDiameter = null;
            String outWidth = null;
            String consigneeId = null;
            String consigneeName = null;
            String consigneeRemark = null;
            String outDiameter = null;
            if(fmPartListJson.size() > 0){
                fmPartList = fmPartListJson.getJSONObject(0);

                innerDiameter = fmPartList.getString("innerDiameter");//卷内径
                outWidth = fmPartList.getString("outWidth");//卷宽度(立放时的宽度)
                consigneeId = fmPartList.getString("consigneeId");//收货单位编码
                consigneeName = fmPartList.getString("consigneeName");//收货单位名称
                consigneeRemark = fmPartList.getString("consigneeRemark");//收货单位名称
                outDiameter = fmPartList.getString("outDiameter");//卷外径
            }

            ProductInfoMap productInfoMap = new ProductInfoMap();
            productInfoMap.setPackId(packid);
            productInfoMap.setPartId(partId);
            productInfoMap.setFactoryProductid(factoryProductId);
            productInfoMap.setSpec(spec);
            productInfoMap.setProductTypeName(productTypeName);
            productInfoMap.setShopsign(shopsign);
            productInfoMap.setMwrapid(mwrapid);
            productInfoMap.setModiDate(modiDate);
            productInfoMap.setModiPerson(modiPerson);
            productInfoMap.setStoveNum(stoveNum);
            productInfoMap.setInnerDiameter(innerDiameter);
            productInfoMap.setOutDiameter(outDiameter);
            productInfoMap.setOutWidth(outWidth);
            productInfoMap.setConsigneeId(consigneeId);
            productInfoMap.setConsigneeName(consigneeName);
            productInfoMap.setConsigneeRemark(consigneeRemark);
            productInfoMap.setMachineId(machineId);
            productInfoMap.setQualityGradeName(qualityGradeName);

            if (StringUtils.isEmpty(packid)){
                partPackDao.insertPartPack(productInfoMap);
            }

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
