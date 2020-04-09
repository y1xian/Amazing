package com.yyxnb.emmm.data;

import com.yyxnb.emmm.bean.MainBean;

import java.util.ArrayList;
import java.util.List;

public class DataConfig {

    public static List<MainBean> getDataMain() {
        List<MainBean> list = new ArrayList<>();

        list.add(new MainBean(1145905694, "----- title -----", "home/f/title"));
        list.add(new MainBean(135884164, "----- 网络请求 -----", "home/f/http"));
        list.add(new MainBean(1231750126, "----- fragment -----", "home/f/fragment"));
        list.add(new MainBean(474114952, "----- adapter -----", "home/f/adapter"));
        list.add(new MainBean(288680808, "----- 自定义 behavior -----", "home/f/behavior"));
        list.add(new MainBean(1260165028, "----- 标签 -----", "home/f/tag"));
        list.add(new MainBean(819827592, "----- 弹框 -----", "home/f/popup"));

        return list;
    }
}