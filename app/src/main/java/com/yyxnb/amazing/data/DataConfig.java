package com.yyxnb.amazing.data;

import com.yyxnb.amazing.bean.MainBean;
import com.yyxnb.common.AppConfig;
import com.yyxnb.http.utils.GsonUtils;
import com.yyxnb.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class DataConfig {

    //    public static final String BASE_URL = "http://192.168.8.103:7879/";
//    public static final String BASE_URL = "http://192.168.0.98:7879/";
    public static final String BASE_URL = " http://www.mocky.io/";


    private volatile static List<MainBean> mainBeans;

    /**
     * 首页数据
     * @return
     */
    public static List<MainBean> getMainBeans() {
        if (mainBeans == null) {
            String content = FileUtils.parseFile(AppConfig.getInstance().getContext(), "main_data.json");
            mainBeans = GsonUtils.jsonToList(content, MainBean.class);
        }
        return mainBeans;
    }

    public static List<String> getDialogList(){
        List<String> list = new ArrayList<>();
        list.add("loading");
        list.add("提示");
        list.add("输入框");
        list.add("中间列表");
        list.add("中间列表 带选中");
        list.add("底部列表");
        list.add("底部列表 带选中");
        list.add("全屏");
        list.add("底部弹框 注册");
        list.add("评论列表");
        list.add("底部 + vp");
        return list;
    }
}
