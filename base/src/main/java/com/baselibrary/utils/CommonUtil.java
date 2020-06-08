package com.baselibrary.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.baselibrary.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CommonUtil {

    public static List<String> getVideoListString() {
        String url1 = "http://api.lgdama.com:10001/storage/video/ff8545ed50474b0ea68cc2d1622e8a08.mp4";
        String url2 = "http://api.lgdama.com:10001/storage/video/f274e63c595449aea8b0da7c03e55fbf.mp4";
        String url3 = "http://api.lgdama.com:10001/storage/video/cf08eebebbba4464abf3d05f9c85dd83.mp4";
        String url4 = "http://api.lgdama.com:10001/storage/video/e31e0cd72f8a4f2786111e02a3275cf2.mp4";
        return new ArrayList<String>(Arrays.asList(url1, url2, url3, url4, url1, url2, url3, url4, url1, url2, url3, url4));
    }

    public static List<String> getVideoCoverListString() {
        String url1 = "http://enjoy.fengyunguoyuan.com/upload/20200606052833CLfTm.jpg";
        String url2 = "http://www.udiandou.com/upload/20200603124338REaoe.jpg";
        String url3 = "http://www.udiandou.com/upload/20200603124338y2SST.jpg";
        String url4 = "http://www.udiandou.com/upload/20200603124337NLGcd.jpg";
        return new ArrayList<String>(Arrays.asList(url1, url2, url3, url4, url1, url2, url3, url4, url1, url2, url3, url4));
    }

    public static List<String> getImageListString() {
        String url1 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2836123484,3030068744&fm=26&gp=0.jpg";
        String url2 = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1789569952,4029204763&fm=26&gp=0.jpg";
        String url3 = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3580978037,2393601368&fm=26&gp=0.jpg";
        String url4 = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1556616674,3087626701&fm=26&gp=0.jpg";
        String url5 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4001861342,3804929732&fm=26&gp=0.jpg";
        String url6 = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3600014478,753553017&fm=26&gp=0.jpg";
        String url7 = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2024758634,1516103747&fm=26&gp=0.jpg";
        String url8 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2334656353,474671771&fm=26&gp=0.jpg";
        String url9 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2334656353,474671771&fm=26&gp=0.jpg";
        String url10 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2334656353,474671771&fm=26&gp=0.jpg";
        String url11 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2334656353,474671771&fm=26&gp=0.jpg";
        String url12 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2334656353,474671771&fm=26&gp=0.jpg";
        String url13 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2334656353,474671771&fm=26&gp=0.jpg";
        String url14 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2334656353,474671771&fm=26&gp=0.jpg";
        String url15 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2334656353,474671771&fm=26&gp=0.jpg";
        String url16 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2334656353,474671771&fm=26&gp=0.jpg";
        return new ArrayList<String>(Arrays.asList(url1, url2, url3, url4, url5, url6, url7, url8, url9, url10, url11, url12, url13, url14, url15, url16));
    }

    public static List<String> getTextTabListString() {
        return new ArrayList<String>(Arrays.asList("热门", "音乐", "棋类", "故事", "运动", "美术", "书法", "曲艺", "方言", "国学", "心声", "街舞"));
    }

    public static List<String> getTextListString() {
        return new ArrayList<String>(Arrays.asList("这里是话题标题这里是话题标题这里是话题标题", "事做不做得到", "说了不一定有机会，但不说一定没机会", "让你的员工为共同的目标工作"));
    }

    public static String getStringToDate(String time) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(time);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    public static String getDateToString(String time) {
        long lcc = Long.valueOf(time);
        Date d = new Date(lcc);
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdr.format(d);
    }

    public static String getDuration(Context context, String rel_time, String now_time) {

        if (TextUtils.isEmpty(now_time)) {
            if (!TextUtils.isEmpty(rel_time)) {
                String showTime = rel_time.substring(0, rel_time.lastIndexOf(":"));

                return showTime;
            }

            return "时间错误";
        }

        String backStr = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(rel_time);
            d2 = format.parse(now_time);
            // 毫秒ms
            long diff = d2.getTime() - d1.getTime();

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffDays != 0) {
                if (diffDays < 30) {
                    if (1 < diffDays && diffDays < 2) {
                        backStr = context.getString(R.string.yesterday);
                    } else if (1 < diffDays && diffDays < 2) {
                        backStr = context.getString(R.string.The_day_before_yesterday);
                    } else {
                        backStr = String.valueOf(diffDays) + context.getString(R.string.Days_ago);
                    }
                } else {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date chatTime = null;
                    try {
                        chatTime = df.parse(rel_time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    backStr = df.format(chatTime);
//                    backStr = context.getString(R.string.long_long_ago);
                }
            } else if (diffHours != 0) {
                backStr = String.valueOf(diffHours) + context.getString(R.string.An_hour_ago);

            } else if (diffMinutes != 0) {
                backStr = String.valueOf(diffMinutes) + context.getString(R.string.minutes_ago);

            } else {
                backStr = context.getString(R.string.just);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return backStr;

    }

    /**
     * 判断定位服务是否开启
     *
     * @param
     * @return true 表示开启
     */
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    /**
     * 提示用户去开启定位服务
     **/
    public static void toOpenGPS(final Activity activity, final int requestCode) {
        new AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage("手机定位服务未开启，无法获取到您的准确位置信息，是否前往开启？")
                .setNegativeButton("取消", null)
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivityForResult(intent, requestCode);
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    /**
     * wifi是否连接
     *
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiInfo.isConnected();
        } else {
            return false;
        }
    }

    /**
     * 获取wifi信息
     */
    public static String obtainWifiInfo(Context context) {
        //显示扫描到的所有wifi信息
        WifiManager wm = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();

        int strength = wi.getRssi();
        int speed = wi.getLinkSpeed();
        String designation = wi.getSSID();

        String addr = wi.getBSSID();
        String unit = WifiInfo.LINK_SPEED_UNITS;

        if (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            StringBuilder listinfo = new StringBuilder();
            //搜索到的wifi列表信息
            List<ScanResult> scanResults = wm.getScanResults();
//            LogUtil.i(TAG, "obtainListInfo: scanResults.size() " + scanResults.size());
            for (ScanResult sr : scanResults) {
                listinfo.append("wifi网络ID：");
                listinfo.append(sr.SSID);
                listinfo.append("\nwifi MAC地址：");
                listinfo.append(sr.BSSID);
                listinfo.append("\nwifi信号强度：");
                listinfo.append(sr.level + "\n\n");
            }

            String curr_connected_wifi = "wifi网络ID ：" + designation +
                    "\nwifi信号强度: " + strength +
                    "\nwifi MAC地址: " + addr +
                    "\nwifi网速: " + speed + " " + unit;
            return designation;
//            getWifiListener(designation);
//            wifiInfo.setText("当前wifi信息:\n\n" + curr_connected_wifi + "\n\n\nwifiList:\n\n" + listinfo.toString());
        }
        return null;
    }

    public static void hideSoftInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        IBinder b = null;
        if (view != null) b = view.getWindowToken();
        imm.hideSoftInputFromWindow(b, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourcesId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourcesId);
        return height;
    }

    public static boolean isBlank(String s) {
        return (s == null || s.equals("") || s.equals("null"));
    }

    public static boolean isBlank(Object o) {
        return (o == null);
    }

    //解决小米手机上获取图片路径为null的情况
    public static Uri getPictureUri(android.content.Intent intent, Activity activity) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = activity.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static class Formatter {

        public static String formatTime(int ms) {
            int totalSeconds = (ms + 500) / 1000;//四舍五入
            int seconds = totalSeconds % 60;
            int minutes = totalSeconds / 60 % 60;
            int hours = totalSeconds / 60 / 60;
            String timeStr = "";
            if (hours > 9) {
                timeStr += hours + ":";
            } else if (hours > 0) {
                timeStr += "0" + hours + ":";
            }
            if (minutes > 9) {
                timeStr += minutes + ":";
            } else if (minutes > 0) {
                timeStr += "0" + minutes + ":";
            } else {
                timeStr += "00:";
            }
            if (seconds > 9) {
                timeStr += seconds;
            } else if (seconds > 0) {
                timeStr += "0" + seconds;
            } else {
                timeStr += "00";
            }

            return timeStr;
        }


        public String formatDate(long seconds) {
            String finalStr = "";
            long mills = seconds * 1000;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            finalStr += (hour < 10 ? "0" + hour : hour) + ":";
            int minute = calendar.get(Calendar.MINUTE);
            finalStr += (minute < 10 ? "0" + minute : minute) + ":";
            int second = calendar.get(Calendar.SECOND);
            finalStr += (second < 10 ? "0" + second : second);

            return finalStr;

        }
    }

}
