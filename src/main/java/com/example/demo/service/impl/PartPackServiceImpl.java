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
    public String queryDetail(String segNo, String productionOrderCode){
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




            //confirmList
            JSONArray confirmListJson = resultJSONObject.getJSONArray("confirmList");
            JSONObject confirmList = new JSONObject();
            String confirmDate = null;
            String confirmPerson = null;
            if(confirmListJson.size() > 0){
                confirmList = confirmListJson.getJSONObject(0);

                confirmDate = confirmList.getString("confirmDate");
                confirmPerson = confirmList.getString("confirmPerson");//检验员（确认人）
            }


            //dataInfo
            JSONArray dataInfoJson = resultJSONObject.getJSONArray("dataInfo");
            JSONObject dataInfo = new JSONObject();
            String machineId = null;
            if(confirmListJson.size() > 0){
                dataInfo = dataInfoJson.getJSONObject(0);

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
            productInfoMap.setConfirmDate(confirmDate);
            productInfoMap.setConfirmPerson(confirmPerson);
            productInfoMap.setStoveNum(stoveNum);
            productInfoMap.setInnerDiameter(innerDiameter);
            productInfoMap.setOutDiameter(outDiameter);
            productInfoMap.setOutWidth(outWidth);
            productInfoMap.setConsigneeId(consigneeId);
            productInfoMap.setConsigneeName(consigneeName);
            productInfoMap.setConsigneeRemark(consigneeRemark);
            productInfoMap.setMachineId(machineId);
            productInfoMap.setQualityGradeName(qualityGradeName);

            partPackDao.insertPartPack(productInfoMap);
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
