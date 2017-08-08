package com.yisingle.driver.app.base;

/**
 * Created by jikun on 17/5/23.
 */

public class Constant {


    private static final String WS = "ws://";
    private static final String HTPP = "http://";
    private static final String IP = "119.23.51.14";//阿里云IP
    private static final String LOCAL_IP = "192.168.2.3";
    private static final String PORT = "8080";

    //192.168.2.3
    public static String getBaseUrl() {

        return HTPP + IP + ":" + PORT + "/";
    }

    public static String getWsBaseUrl() {
        return WS + IP + ":" + PORT + "/";
    }
}
