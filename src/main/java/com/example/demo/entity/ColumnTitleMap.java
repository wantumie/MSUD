package com.example.demo.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * describe
 *
 * @Auther Mr.Garfield
 * @Date 2020/6/2
 */

public class ColumnTitleMap {
    private Map<String, String> columnTitleMap = new HashMap<String, String>();
    private ArrayList<String> titleKeyList = new ArrayList<String> ();

    public ColumnTitleMap(String datatype) {
        switch (datatype) {
            case "userinfo":
                initUserInfoColu();
                initUserInfoTitleKeyList();
                break;
            default:
                break;
        }

    }
    /**
     * mysql用户表需要导出字段--显示名称对应集合
     */
    private void initUserInfoColu() {
        columnTitleMap.put("id", "ID");
        columnTitleMap.put("partId", "零件号");
        columnTitleMap.put("chargePlat", "料台号");
        columnTitleMap.put("packId", "捆包号");
        columnTitleMap.put("packQty", "捆包数量");
        columnTitleMap.put("putinQty", "数量");
        columnTitleMap.put("putinWeight", "净重");
        columnTitleMap.put("mwrapId", "母卷号");
        columnTitleMap.put("factoryProductId", "钢厂资源号");
        columnTitleMap.put("qualityGradeName", "质量等级");
        columnTitleMap.put("shopsign", "牌号");
    }

    /**
     * mysql用户表需要导出字段集
     */
    private void initUserInfoTitleKeyList() {
        titleKeyList.add("id");
        titleKeyList.add("partId");
        titleKeyList.add("chargePlat");
        titleKeyList.add("packId");
        titleKeyList.add("packQty");
        titleKeyList.add("putinQty");
        titleKeyList.add("putinWeight");
        titleKeyList.add("mwrapId");
        titleKeyList.add("factoryProductId");
        titleKeyList.add("qualityGradeName");
        titleKeyList.add("shopsign");
    }

    public Map<String, String> getColumnTitleMap() {
        return columnTitleMap;
    }

    public ArrayList<String> getTitleKeyList() {
        return titleKeyList;
    }
}

