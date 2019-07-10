package com.khmerlabs.mypulltorefresh.common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Common {

    //    public static final String APP_ID = "d61dba5f839d0f84062a029759581b1b";
    public static final String APP_ID = "89f106a64a4e88d84a7e6670aec2dee4";
    public static final String MOSCOW = "Moscow, ru";
    public static final String SPETERSBURG = "Saint Petersburg, ru";
    public static final String CUR_LOC_TITLE = "CURRENT LOCATION";
    public static final String MOSCOW_TITLE = "MOSCOW";
    public static final String SPETERSBURG_TITLE = "SAINT PETERSBURG";
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String timeZone = "Europe/Moscow";
    public static Location currentLocation = null;

    public static String convertUnixToDate(String dt, String tzone) {
        long unixSeconds = Long.valueOf(dt);
        long timeZone = Long.valueOf(tzone);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date((unixSeconds + timeZone) * 1000L);
        return sdf.format(date);
    }

    public static String getCurrentTimeUsingDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(date);
    }
}
