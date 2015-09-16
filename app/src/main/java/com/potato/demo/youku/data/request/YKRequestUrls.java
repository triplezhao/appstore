package com.potato.demo.youku.data.request;

import com.potato.chips.base.BaseRequestUrls;

/**
 * 这里通常放置一些常量
 * 
 */
public class YKRequestUrls extends BaseRequestUrls{

	//获取用户发布的视频. 命名，取api接口的后两位单词
	public static final String videos_by_user = BaseYoukuURL
			+ "/videos/by_user.json";


}
