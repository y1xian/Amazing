package com.yyxnb.arch.common;

import com.jeremyliao.liveeventbus.LiveEventBus;

public class Bus {

    public static void post(MsgEvent msgEvent){
        LiveEventBus.get("").post(msgEvent);
    }
}
