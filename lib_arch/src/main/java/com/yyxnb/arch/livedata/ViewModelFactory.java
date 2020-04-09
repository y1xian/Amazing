package com.yyxnb.arch.livedata;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.yyxnb.arch.base.BaseViewModel;
import com.yyxnb.arch.base.IView;
import com.yyxnb.common.AppConfig;

import java.io.Serializable;
import java.lang.reflect.Field;

public class ViewModelFactory implements Serializable {

    /**
     * 创建 对应的 ViewModel, 并且 添加 通用 (LiveData) 到 ViewModel中
     */
    public static BaseViewModel createViewModel(Fragment fragment, Field field) {
        Class<BaseViewModel> viewModelClass = AppConfig.getInstance().getFiledClazz(field);
        BaseViewModel viewModel = ViewModelProviders.of(fragment).get(viewModelClass);
//        initSharedData(fragment as IFragment, viewModel)
        return viewModel;
    }

    /**
     * 创建 对应的 ViewModel, 并且 添加 通用 (LiveData) 到 ViewModel中
     */
    public static BaseViewModel createViewModel(FragmentActivity activity, Field field) {
        Class<BaseViewModel> viewModelClass = AppConfig.getInstance().getFiledClazz(field);
        BaseViewModel viewModel = ViewModelProviders.of(activity).get(viewModelClass);
//        initSharedData(fragment as IFragment, viewModel)
        return viewModel;
    }

//    private fun initSharedData(view:IView, viewModel: BaseViewModel) {
//        viewModel.defUI.toastEvent.observe(view as LifecycleOwner, Observer {
//            AppConfig.toast(it.toString())
//        })
//        // 订阅通用 observer
//        viewModel.defUI.msgEvent.observe(view as LifecycleOwner, Observer {
//            view.handleEvent(it)
//        })
//    }
}
