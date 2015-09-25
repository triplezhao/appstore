package com.potato.appstore.store.data.parser;

import com.potato.appstore.store.data.bean.ApkInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 囧图-图册列表数据解析器
 * 
 * @author zhaobingfeng
 * 
 */
public class ApkInfoListParser extends AppStoreBaseParser{

	ArrayList<ApkInfo> list;
	
	public ApkInfoListParser(String jsonStr) {
		super(jsonStr);
	}


	public static ArrayList<ApkInfo> parseList(String listKey, JSONObject job) {
	    ArrayList<ApkInfo> albums = new ArrayList<ApkInfo>();
	    try {
            JSONArray array = job.optJSONArray(listKey);
            if (array != null) {
                int length = array.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObj = array.optJSONObject(i);
                    ApkInfo entity = ApkInfo.createFromJSON(jsonObj);
                    albums.add(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return albums;
	}
	
}
