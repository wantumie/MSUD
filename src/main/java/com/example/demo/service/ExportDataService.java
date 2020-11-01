package com.example.demo.service;

import com.example.demo.util.ExportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * describe
 *
 * @Auther Mr.Garfield
 * @Date 2020/6/2
 */

@Service
public class ExportDataService {
    @Autowired
    ExportExcelUtil exportExcelUtil;

    /*导出用户数据表*/
    public void exportDataToEx(HttpServletResponse response, ArrayList<String> titleKeyList, Map<String, String> titleMap, List<Map> src_list) {
        try {
            exportExcelUtil.expoerDataExcel(response, titleKeyList, titleMap, src_list);
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
        }
    }
}
