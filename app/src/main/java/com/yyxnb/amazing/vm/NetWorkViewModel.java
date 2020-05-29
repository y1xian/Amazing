package com.yyxnb.amazing.vm;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.yyxnb.amazing.api.IService;
import com.yyxnb.amazing.bean.BaseData;
import com.yyxnb.amazing.bean.StateData;
import com.yyxnb.amazing.bean.TikTokBean;
import com.yyxnb.amazing.data.Http;
import com.yyxnb.common.AppConfig;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.http.BaseViewModel;

import java.util.HashMap;
import java.util.Map;


public class NetWorkViewModel extends BaseViewModel {

//    private NetWorkRespository mRepository = new NetWorkRespository();
//    private NetWorkService mApi = RetrofitManager.INSTANCE.createApi(NetWorkService.class);
//    private IService mApi = RetrofitFactory.getInstance().create(IService.class);
    private IService mApi = Http.getInstance().create(IService.class);

    private MutableLiveData<Map<String, String>> reqTeam = new MutableLiveData();
    private MutableLiveData<Map<String, String>> reqTeam2 = new MutableLiveData();


    public MutableLiveData<StateData<TikTokBean>> result = new MutableLiveData();
    public MediatorLiveData<StateData<TikTokBean>> result1 = new MediatorLiveData<>();
    public MediatorLiveData<StateData<TikTokBean>> result2 = new MediatorLiveData<>();

    public MutableLiveData<BaseData<StateData<TikTokBean>>> getList(){
        Map<String,String> map = new HashMap<>();
        return request(mApi.getVideoList3(map)).get();
    }

    public void getList2(){
        Map<String,String> map = new HashMap<>();

        result1.addSource(request(mApi.getVideoList3(map)).get(),data ->{
            LogUtils.e("ccccc");
            result1.setValue(data.getResult());
        });
    }

    public void getList3(){
        Map<String,String> map = new HashMap<>();

        result2.addSource(request2(mApi.getVideoList4(map)),data ->{
            LogUtils.e("ddddddd");
            result2.setValue(data.getResult());
        });
    }

    public void reqList(){
        Map<String,String> map = new HashMap<>();

//        result2.setValue(request(mApi.getVideoList3(map)).get());
    }


    public void reqTeam(){
        reqTeam.setValue(new HashMap<>());
    }

    public void reqTeam2(){
//        reqTeam2.setValue(new HashMap<>());

        LogUtils.e("----rrr---");
        Map<String,String> map = new HashMap<>();

        launchOnlyResult(mApi.getVideoList4(map), new OnHandleException<BaseData<StateData<TikTokBean>>>() {
            @Override
            public void success(BaseData<StateData<TikTokBean>> data) {
                result.postValue(data.getResult());
            }

            @Override
            public void error(String msg) {
                AppConfig.getInstance().log(msg);
            }
        });

//        launchOnlyresult(mApi.getVideoList2(map), new OnHandleException<BaseData<StateData<TikTokBean>>>() {
//            @Override
//            public void success(BaseData<StateData<TikTokBean>> data) {
//                AppConfig.getInstance().log(" ----data --- " + data.toString());
////                result.postValue(data.getResult());
//            }
//
//            @Override
//            public void error(String msg) {
//                AppConfig.getInstance().log(" ----msg --- " + msg);
//            }
//        });


    }

}
