package com.yyxnb.amazing.vm;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.yyxnb.amazing.api.IService;
import com.yyxnb.amazing.bean.BaseData;
import com.yyxnb.amazing.bean.StateData;
import com.yyxnb.amazing.bean.TikTokBean;
import com.yyxnb.amazing.data.Http;
import com.yyxnb.common.AppConfig;
import com.yyxnb.http.BaseViewModel;

import java.util.HashMap;
import java.util.Map;


public class NetWorkViewModel extends BaseViewModel {

//    private NetWorkRespository mRepository = new NetWorkRespository();
//    private NetWorkService mApi = RetrofitManager.INSTANCE.createApi(NetWorkService.class);
//    private IService mApi = RetrofitFactory.getInstance().create(IService.class);
    private IService mApi = Http.getInstance().create(IService.class);

    public MutableLiveData<StateData<TikTokBean>> result = new MutableLiveData();
    public MediatorLiveData<StateData<TikTokBean>> result2 = new MediatorLiveData<>();

    public MutableLiveData<BaseData<StateData<TikTokBean>>> getList(){
        Map<String,String> map = new HashMap<>();
        return request(mApi.getVideoList3(map)).get();
    }

    public void getList3(){
//        event.setValue("loading");
        Map<String,String> map = new HashMap<>();

        result2.addSource(request(mApi.getVideoList4(map)),data ->{
            result2.setValue(data.getResult());
        });
    }

    public void reqTeam2(){
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


    }

}
