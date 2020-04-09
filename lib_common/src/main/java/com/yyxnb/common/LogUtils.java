package com.yyxnb.common;

import android.util.Log;

public class LogUtils {

    public static void setDebugMode(boolean debugMode) {
        LogUtils.debugMode = debugMode;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    static boolean debugMode = true;


    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数


    private static String createLog(String log) {

        StringBuffer buffer = new StringBuffer();

        buffer.append(methodName);

        buffer.append("(").append(className).append(":").append(lineNumber).append(")");

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public static void logMe(String message) {


        if (debugMode) {

            Log.e("666", "|                                         |");

            logVeryLongLoge("666", "|" + message);

            getMethodNames(new Throwable().getStackTrace());
            Log.e("666", "|" + createLog(message));
            Log.e("666", "|                                         |");
            Log.e("666", "-----------------------------------------------------------------------");
            Log.e("666", "|                                         |");
        }
    }


    public static void logMe999(String message) {


        if (debugMode) {

            Log.e("999", "|                                         |");
            Log.e("999", "|" + message);
            getMethodNames(new Throwable().getStackTrace());
            Log.e("999", "|" + createLog(message));
            Log.e("999", "|                                         |");
            Log.e("999", "-----------------------------------------------------------------------");
            Log.e("999", "|                                         |");
        }
    }


    /**
     * 截断输出日志
     *
     * @param msg
     */
    public static void logVeryLongLoge(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0) {
            return;
        }

        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            Log.e(tag, msg);
        } else {
            while (msg.length() > segmentSize) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.e(tag, logContent);
            }
            Log.e(tag, msg);// 打印剩余日志
        }
    }
}