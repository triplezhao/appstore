package com.potato.demo.youku.data.parser;

import com.potato.chips.base.BaseParser;

import org.json.JSONException;

/**
 * @author ztw 这个类只提供基础的解析方法，每个接口对应的解析方法在.parse.api包下面。
 */
public  class YKBaseParser extends BaseParser {
    // 错误信息

    public YKBaseParser(String jsonStr) {
        super(jsonStr);

    }

    @Override
    public String getCode() {
        if (!succ){
            try {
                return root.getJSONObject("error").optString("code");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "0";
    }

    @Override
    public String getMsg() {
        if (!succ){
            try {
                return root.getJSONObject("error").optString("description");
            } catch (JSONException e) {
                return "fail";
            }
        }
        return "cucc";
    }

    @Override
    public boolean isSucc() {
        if(root!=null){
            if(!root.has("error")){
                return true;
            }
        }
        return false;
    }


}
