package com.potato.demo.jiongtu.data.request;

import com.potato.chips.base.BaseRequestUrls;

/**
 * 这里通常放置一些常量
 * 
 */
public class JiongtuRequestUrls extends BaseRequestUrls{

	//测试地址
	public static final String URL_TEST = BaseURL
			+ "/rest/collection/collectGameInfo"; //给服务器发送包名信息
	public static final String URL_JIONG_SECTION_LIST = BaseJIONGURL
			+ "/api/section/list"; // 获取囧图栏目列表
	public static final String URL_JIONG_ALBUM_LIST = BaseJIONGURL
			+ "/api/photos/list"; // 获取囧图某栏目下图册列表
	public static final String URL_JIONG_PHOTO_LIST = BaseJIONGURL
			+ "/api/photos/detail"; // 获取囧图某图册下图片列表

}
