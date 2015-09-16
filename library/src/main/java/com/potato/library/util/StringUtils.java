package com.potato.library.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class StringUtils {

    public static String converStreamToString(InputStream is) {

        if (is == null)
            return null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();

        int count = 0;
        while (count < 3) {

            try {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                break;
            } catch (IOException e) {
                count++;
            }
        }
        return buffer.toString();
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertSecondsToYYMMDDString(long milliseconds) {
        SimpleDateFormat fn = new SimpleDateFormat("yyyy-MM-dd");
        String str = fn.format(new Date(milliseconds));
        return str;
    }

    public static String convertSecondsToYYYYString(long milliseconds) {
        SimpleDateFormat fn = new SimpleDateFormat("yyyy年");
        String str = fn.format(new Date(milliseconds));
        return str;
    }

    public static String convertSecondsToMMDDString(long milliseconds) {
        SimpleDateFormat fn = new SimpleDateFormat("MM月dd日");
        String str = fn.format(new Date(milliseconds));
        return str;
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertSecondsToMMDDHHMMString(long milliseconds) {
        SimpleDateFormat fn = new SimpleDateFormat("MM-dd HH:mm");
        String str = fn.format(new Date(milliseconds));
        return str;
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertSecondsToHHMMString(long milliseconds) {
        SimpleDateFormat fn = new SimpleDateFormat("HH:mm");
        String str = fn.format(new Date(milliseconds));
        return str;
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertSecondsToYYMMDDStringHHmm(long milliseconds) {
        SimpleDateFormat fn = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str = fn.format(new Date(milliseconds));
        return str;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatCommentTime(long milliseconds) {
        SimpleDateFormat fn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = fn.format(new Date(milliseconds));
        return str;
    }
    
    @SuppressLint("SimpleDateFormat")
    public static String convertSecondsTommss(long milliseconds) {
        SimpleDateFormat fn = new SimpleDateFormat("mm:ss");
        String str = fn.format(new Date(milliseconds));
        return str;
    }
    
    @SuppressLint("SimpleDateFormat")
    public static String splitTommss(long milliseconds) {
        String time = convertSecondsTommss(milliseconds);
    	String min = time.split(":")[0]; 
    	String s = time.split(":")[1];
    	String str = "";
    	if(min.substring(min.length()-1).equals("0")){
    		str = s + "s";
    	}else{
    		str = min.substring(min.length()-1) + "min" + s + "s";
    	}
    	return str;
    }

    @SuppressLint("SimpleDateFormat")
    public static long convertYYMMDDtoSeconds(String time) {
        String sDt = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt2 = null;
        long lTime = 0;
        try {
            dt2 = sdf.parse(sDt);
            lTime = dt2.getTime() / 1000;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return lTime;
    }

    public static String convert2Time(long start, long end) {
        long between = (end - start) / 1000;// 除以1000是为了转换成秒

        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60 / 60;

        return ("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒");
    }

    /**
     * 获取当前日期时间串,格式如2013-03-12 16:50
     * 
     * @return
     */
    public static String getCurrentDateAndTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }

    /**
     * 创建JSON字符串.值接受基础数据类型
     * 
     * @param cv
     *            键值对
     */
    public static String createJSONStr(ContentValues cv) {
        JSONObject jo = new JSONObject();
        Iterator<Entry<String, Object>> iterator = cv.valueSet().iterator();
        try {
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                Object value = entry.getValue();
                if (value instanceof String) {
                    jo.put(entry.getKey(), value.toString());
                } else if (value instanceof Integer) {
                    jo.put(entry.getKey(), ((Integer) value).intValue());
                } else if (value instanceof Long) {
                    jo.put(entry.getKey(), ((Long) value).longValue());
                } else if (value instanceof Boolean) {
                    jo.put(entry.getKey(), ((Boolean) value).booleanValue());
                }
            }
        } catch (JSONException e) {
            L.e("StringUtils", e.getMessage());
        }
        return jo.toString();
    }

    public static Boolean isBlank(String str) {

        if (TextUtils.isEmpty(str)) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * 功能：获取arr1中下标从startIndexInclusive到endIndexExclusive的子数组，不包括endIndexExclusive
     */

    public static byte[] subarray(byte[] array, int startIndexInclusive,
            int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        Class type = array.getClass().getComponentType();
        if (newSize <= 0) {
            return (byte[]) Array.newInstance(type, 0);
        }
        byte[] subarray = (byte[]) Array.newInstance(type, newSize);
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /*
     * 
     * 功能：将arr2追加到arr1尾部：
     */
    public static byte[] addAll(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return array2;
        } else if (array2 == null) {
            return array1;
        }
        byte[] joinedArray = (byte[]) Array.newInstance(array1.getClass()
                .getComponentType(), array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * @param str
     * @return 删除某个字符串 第5个 逗号 以后的字符串（包括这个逗号） 如 "1,2,3,4,5  , 6,7,8" 返回
     *         “1,2,3,4,5”
     */
    public static String delLastHistory(String str) {
        Pattern p = Pattern.compile(",");
        Matcher m = p.matcher(str);
        int i = 0;
        while (m.find()) {
            i++;
            if (i == 5) {
                System.out.println("" + m.end());
                return str.substring(0, m.end() - 1);
            }
        }
        return str;
    }

    public static String getFormatedPnum(int pnum) {
        if (pnum < 10000) {
            return String.valueOf(pnum);
        } else {
            DecimalFormat df = new DecimalFormat("#0.0");
            String result = df.format(pnum / 10000.0);
            if (result.endsWith(".0")) {
                result = result.substring(0, result.length() - 2);
            }
            result = result + "万";
            return result;
        }
    }

    public static String convertCacheTime(long milliseconds) {
        // L.i("convertCacheTime,chachetime",
        // convertSecondsToYYMMDDStringHHmm(milliseconds));
        // L.i("convertCacheTime,chachetime",
        // convertSecondsToYYMMDDStringHHmm(System.currentTimeMillis()));
        // Date taday= new Date(System.currentTimeMillis());
        // Date cacheDay=new Date(milliseconds);
        // if(taday.getYear()==cacheDay.getYear()){
        // if(taday.getMonth()==cacheDay.getMonth()){
        // if(taday.getDate()==cacheDay.getDate()){
        // return convertSecondsToHHMMString(milliseconds);
        // }
        // }
        // return convertSecondsToMMDDHHMMString(milliseconds);
        // }
        // return convertSecondsToYYMMDDStringHHmm(milliseconds);
        return convertNewsTime(milliseconds);
    }

    public static String convertNewsTime(long milliseconds) {
        // L.i("convertCacheTime,chachetime",
        // convertSecondsToYYMMDDStringHHmm(milliseconds));
        // L.i("convertCacheTime,chachetime",
        // convertSecondsToYYMMDDStringHHmm(System.currentTimeMillis()));
        long subtime = System.currentTimeMillis() - milliseconds;
        // if(0<=subtime&&subtime<60*1000){
        // return "刚刚";
        // }else if(1L*60*1000<=subtime&&subtime<1L*60*60*1000 ){
        // return subtime/(60*1000)+"分钟前";
        // }else if(1L*60*60*1000<=subtime&&subtime<1L*24*60*60*1000 ){
        // return subtime/(60*60*1000)+"小时前";
        // }else if(((1L*24*60*60*1000<=subtime)&&subtime<1L*365*24*60*60*1000)
        // || 0>subtime){
        // return convertSecondsToMMDDString(milliseconds);//x月x日
        // }else {//(subtime>365*24*60*60*1000)
        // return convertSecondsToYYYYString(milliseconds);//xxxx年
        // }

        Date taday = new Date(System.currentTimeMillis());
        Date cacheDay = new Date(milliseconds);
        if (cacheDay.after(taday))
            return formatCommentTime(milliseconds);// 如果手机时间调整为之前时间
                                                            // 则显示上传时间
        if (taday.getYear() == cacheDay.getYear()) {
            if (taday.getMonth() == cacheDay.getMonth()
                    && taday.getDate() == cacheDay.getDate()) {

                if (0 <= subtime && subtime < 60 * 1000) {
                    return "刚刚";
                } else if (1L * 60 * 1000 <= subtime
                        && subtime < 1L * 60 * 60 * 1000) {
                    return subtime / (60 * 1000) + "分钟前";
                } else if (1L * 60 * 60 * 1000 <= subtime
                        && subtime < 1L * 24 * 60 * 60 * 1000) {
                    return subtime / (60 * 60 * 1000) + "小时前";
                }
                // if(taday.getHours() == cacheDay.getHours()){
                // if(taday.getMinutes() == cacheDay.getMinutes()){
                // return "刚刚";
                // }
                // return ""+(taday.getMinutes() -
                // cacheDay.getMinutes())+"分钟前";//分钟减分钟为xx分钟之前
                // }
                // return ""+(taday.getHours() -
                // cacheDay.getHours())+"小时前";//几小时前

            }
            return convertSecondsToMMDDString(milliseconds);// x月x日
        }
        return convertSecondsToYYYYString(milliseconds);// xxxx年
    }

    public static String emptyNotPrintChar(String str) {

        if (TextUtils.isEmpty(str)) {
            return "";
        }

        byte[] bts = str.getBytes();
        int btsLength = bts.length;
        byte[] newBytes = new byte[btsLength];
        for (int i = 0; i < btsLength; i++) {

            byte b = bts[i];
            if ((b >= 0 && b <= 31) || b == 127) {
                b = 32;
            }

            newBytes[i] = b;
        }

        return new String(newBytes);
    }

    public static final String getChineseOrEnglishOrNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuffer sbf = new StringBuffer();
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            // Java判断一个字符串是否有中文是利用Unicode编码来判断，因为中文的编码区间为：0x4e00--0x9fbb
            if ((charArray[i] >= 0x4e00 && charArray[i] <= 0x9fbb)// 中文
                    || (str.charAt(i) >= 0x00 && str.charAt(i) <= 0x7f)// ascii值
                    || (str.charAt(i) >= 0xff01 && str.charAt(i) <= 0xff5d)// 全角字符
                    || (str.charAt(i) >= 0x214e && str.charAt(i) <= 0x218f)// 罗马字符
            ) {
                sbf.append(charArray[i]);
            }
        }
        return sbf.toString();
    }

    /**
     * 
     * @Title: split
     * @Description: TODO(分割字符串)
     * @param: @param string
     * @param: @param delimiters
     * @param: @return 设定文件
     * @return: String[] 返回类型
     * @date: 2013-9-29 下午5:36:42
     */
    public static String[] split(String string, String delimiters) {
        StringTokenizer st = new StringTokenizer(string, delimiters);
        String[] fields = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++) {
            String token = st.nextToken();
            fields[i] = token;
        }

        return fields;
    }
    
    /**
     * 
     * @Title: split
     * @Description: TODO(分割字符串)
     * @param: @param string
     * @param: @param delimiters
     * @param: @return 设定文件
     * @return: String[] 返回类型
     * @date: 2013-9-29 下午5:36:42
     */
    public static List<String> split2List(String string, String delimiters) {
        StringTokenizer st = new StringTokenizer(string, delimiters);
        List<String> fields = new ArrayList<String>();
        for (int i = 0; st.hasMoreTokens(); i++) {
            String token = st.nextToken();
            fields.add(token);
        }

        return fields;
    }

    /**
     * 检查是否是手机号码（检查长度和首字母）
     * @param number 待判断的字符串
     */
    public static boolean isValidMobilePhoneNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }
        return number.matches("^1\\d{10}$");
    }
    
    
    /**
     * @description 统计数的格式化
     * @param number 待格式化的字符串
     * @author qiangding
     * @date: 2014-10-29 
     */
    public static String getStatisticalFormat(long number) {
        String numberStr = String.valueOf(number);
        DecimalFormat df = new DecimalFormat("#.0");
        if(numberStr.length() >= 5 && numberStr.length() < 9) {
            float heartBeatCountNum = Math.round(number / 100 / 10f) / 10f;
            boolean isDecimal = String.valueOf(heartBeatCountNum).split("\\.")[1].equals("0") ? false : true;
            if(!isDecimal)
                numberStr = (long)heartBeatCountNum + "万";
            else 
                numberStr = df.format(heartBeatCountNum) + "万";
        } else if(numberStr.length() >= 9) {
            float heartBeatCountNum = Math.round(number / 1000000 / 10f) / 10f;
            boolean isDecimal = String.valueOf(heartBeatCountNum).split("\\.")[1].equals("0") ? false : true;
            if(!isDecimal)
                numberStr = (long)heartBeatCountNum + "亿";
            else 
                numberStr = df.format(heartBeatCountNum) + "亿";
        } else {
            numberStr = String.valueOf((long)number);
        }
        return numberStr;
    }
}