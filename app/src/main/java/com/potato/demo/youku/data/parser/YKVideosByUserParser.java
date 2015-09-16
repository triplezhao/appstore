package com.potato.demo.youku.data.parser;

import com.potato.demo.youku.data.bean.YKVideo;

import org.json.JSONArray;

import java.util.ArrayList;

public class YKVideosByUserParser extends YKBaseParser {

    public String total;
    public String page;
    public String count;
    public String last_item;
    public ArrayList<YKVideo> list = new ArrayList<YKVideo>();

    public YKVideosByUserParser(String jsonStr) {
        super(jsonStr);
        try {
            total = root.optString("total");
            page = root.optString("page");
            count = root.optString("count");
            last_item = root.optString("last_item");
            JSONArray array = root.getJSONArray("videos");
            list = YKVideo.createFromJSONArray(array);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
