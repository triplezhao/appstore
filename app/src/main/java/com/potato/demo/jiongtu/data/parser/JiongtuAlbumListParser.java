package com.potato.demo.jiongtu.data.parser;

import com.potato.demo.jiongtu.data.bean.JiongtuAlbum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 囧图-图册列表数据解析器
 * 
 * @author zhaobingfeng
 * 
 */
public class JiongtuAlbumListParser {

	public int code;
	public long minPublicDate ;//最近的时间
//	public long maxPublicDate=System.currentTimeMillis();//最远的时间
	public long maxPublicDate=Long.MAX_VALUE;//最远的时间
	public long total;    //数据总条数
	

	public ArrayList<JiongtuAlbum> parseToAlbumList(String jsonStr) {
		if (jsonStr == null) {
			return new ArrayList<JiongtuAlbum>();
		}
		ArrayList<JiongtuAlbum> list = new ArrayList<JiongtuAlbum>();
		try {
			JSONObject obj = new JSONObject(jsonStr);
			code = obj.optInt("Code");
			if (!obj.isNull("Data")) {//Data不为null时
				JSONObject objData = obj.getJSONObject("Data");
				JSONArray array = objData.getJSONArray("List");
				total = objData.optLong("Total");
				if (array != null) {
					int count = array.length();
//					L.e("计算开始：minPublicDate="+minPublicDate+",maxPublicDate="+maxPublicDate);
					for (int i = 0; i < count; i++) {
						JSONObject jsonObj = array.optJSONObject(i);
						if(jsonObj.has("AD")&&jsonObj.getBoolean("AD")){//如果AD字段为true,忽略该图册
							continue;
						}
						JiongtuAlbum entity = JiongtuAlbum.createFromJSON(jsonObj);
						list.add(entity);
						long publicDate = entity.getPublicDate();
//						L.d("publicDate="+publicDate);
						if(publicDate>minPublicDate){//最近的时间
							minPublicDate = publicDate;
						}
						if(publicDate<maxPublicDate){//最远的时间
							maxPublicDate = publicDate;
						}
					}
//					L.e("计算结束：minPublicDate="+minPublicDate+",maxPublicDate="+maxPublicDate);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<JiongtuAlbum> parseAlbumList(String listKey, JSONObject job) {
	    ArrayList<JiongtuAlbum> albums = new ArrayList<JiongtuAlbum>();
	    try {
            JSONArray array = job.optJSONArray(listKey);
            if (array != null) {
                int length = array.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObj = array.optJSONObject(i);
                    JiongtuAlbum entity = JiongtuAlbum.createFromJSON(jsonObj);
                    albums.add(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return albums;
	}
	
	public void reset(){
		minPublicDate = 0l;
		maxPublicDate = Long.MAX_VALUE;
	}
}
