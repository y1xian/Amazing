package com.yyxnb.arch.delegate;

import android.os.Bundle;

public interface IActivity extends IView{


    default void initWidows(){}

    default boolean initArgs(Bundle bundle){
        return true;
    }


}
