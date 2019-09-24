package com.demo.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dawn
 * @ClassName com.demo.excelListener.easyexcel.util.ExcelListenerUtil
 * @Description
 * @date 2019/9/9 16:26
 */


public class ExcelListenerUtil<T> extends AnalysisEventListener<T> {

    private final Logger LOGGER = LoggerFactory.getLogger(ExcelListenerUtil.class);

    private static final int BATCH_COUNT = 5;

    private final List<T> rows = new ArrayList<>();

    private final List<T> rowsList = new ArrayList<>();


    @Override
    public void invoke(T object, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(object));
        rows.add(object);
        rowsList.add(object);
        if (rows.size() >= BATCH_COUNT) {
            rows.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("所有数据解析完成！");
        LOGGER.info("开始存储数据库！");
    }

    public List<T> getRows(){
        return rowsList;
    }

    /**
     * 加上存储数据库
     */
    public void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", rows.size());
        LOGGER.info("存储数据库成功！");
    }
}
