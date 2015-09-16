package com.potato.library.net;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/** 
* @ClassName: RequestUtil 
* @Description: TODO(请求工具类) 
* @author ztw tianwuzhao@cyou-inc.com
* @date 2015-4-8 下午7:18:51  
*/
public class RequestUtil {
   
    /** 
    * @Description: TODO(去除请求中的的某些参数，比如时间ts、vts等，缓存请求时候需要忽略这些参数) 
    * @param request
    * @param unParamedKeys
    * @return String    返回类型
    * @date: 2015-4-8 下午7:25:55
    */
    public static String getParamedUrl(Request request,
            Set<String> unParamedKeys) {
        StringBuilder paramedUrl = new StringBuilder(request.url);
        Map<String, Object> params = request.params;
        if (params != null && params.size() > 0) {
            paramedUrl.append('?');
            Iterator<Map.Entry<String, Object>> iterator = params
                    .entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
                        .next();

                if (unParamedKeys != null
                        && unParamedKeys.contains(entry.getKey())) {
                    continue;
                }

                paramedUrl.append(entry.getKey());
                paramedUrl.append('=');
                paramedUrl.append(entry.getValue());

                if (iterator.hasNext()) {
                    paramedUrl.append('&');
                }
            }
        }
        return paramedUrl.toString();
    }

    /** 
    * @Description: TODO(缓存时候，需要忽略的参数列表) 
    * @return Set<String>    返回类型
    * @date: 2015-4-8 下午7:28:50
    */
    public static Set<String> getCommonFilteredParams() {
        Set<String> filteredParams = new HashSet<String>();
        filteredParams.add("mts");
        filteredParams.add("cts");
        filteredParams.add("ccts");
        filteredParams.add("vts");
        filteredParams.add("appRecTs");
        return filteredParams;
    }
    
    /** 
    * @Description: TODO(这个请求的在缓存表中对应的key) 
    * @param request
    * @return String    返回类型
    * @date: 2015-4-8 下午7:29:07
    */
    public static String getCacheSelection(Request request) {
        String selection = RequestCacheProvider.Columns.requestStr + " = '"
                + request.getCacheKey()+"'";
        return selection;
    }

    public static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    
    
}
