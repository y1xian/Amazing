package com.yyxnb.http.interceptor;

import com.yyxnb.common.AppConfig;
import com.yyxnb.http.utils.JsonUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.logging.HttpLoggingInterceptor;

public class LoggingInterceptor implements HttpLoggingInterceptor.Logger {

    private StringBuilder mMessage = new StringBuilder();

    @SuppressWarnings("NullableProblems")
    @Override
    public void log(String text) {
        try {
            String message = URLDecoder.decode(text, "utf-8").replace("\\", "");
//            StringEscapeUtils.unescapeJavaScript(jsDataStr)

            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
                mMessage.append(" ");
                mMessage.append("\r\n");
            }
            if (message.startsWith("--> GET")) {
                mMessage.setLength(0);
                mMessage.append(" ");
                mMessage.append("\r\n");
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if (message.startsWith("{") && message.endsWith("}") || message.startsWith("[") && message.endsWith("]")) {
                message = JsonUtils.formatJson(message);
            }
            mMessage.append(message).append("\r\n");
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                AppConfig.getInstance().log("Http", mMessage.toString());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}
