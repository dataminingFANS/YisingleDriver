package com.yisingle.driver.app.map.utils;

import android.content.Context;
import android.os.Build;

import com.amap.api.location.AMapLocation;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * Created by liangchao_suxun on 17/1/19.
 */


public class WifiAutoCloseDelegate implements IWifiAutoCloseDelegate {

    /**
     * 请根据后台数据自行添加。此处只针对小米手机
     *
     * @param context  context
     * @return 是否成功
     */
    @Override
    public boolean isUseful(Context context) {
        String manName = getManufacture(context);
        Pattern pattern = compile("xiaomi", CASE_INSENSITIVE);
        Matcher m = pattern.matcher(manName);
        return m.find();
    }

    @Override
    public void initOnServiceStarted(Context context) {
        LocationStatusManager.getInstance().initStateFromPreference(context);
    }

    @Override
    public void onLocateSuccess(Context context, boolean isScreenOn, boolean isMobileable) {
        LocationStatusManager.getInstance().onLocationSuccess(context, isScreenOn, isMobileable);
    }

    @Override
    public void onLocateFail(Context context, int errorCode, boolean isScreenOn, boolean isWifiable) {

        //如果屏幕点亮情况下，因为断网失败，则表示不是屏幕点亮造成的断网失败，并修改参照值
        if (isScreenOn && errorCode == AMapLocation.ERROR_CODE_FAILURE_CONNECTION && !isWifiable) {
           LocationStatusManager.getInstance().resetToInit(context);
            return;
        }

        if (!LocationStatusManager.getInstance().isFailOnScreenOff(context, errorCode, isScreenOn, isWifiable)) {
            return;
        }
        PowerManagerUtil.getInstance().wakeUpScreen(context);
    }

    public static String getManufacture(Context context) {
        return Build.MANUFACTURER;
    }
}
