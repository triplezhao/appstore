package com.potato.demo.jiongtu.data.parser;

import com.potato.chips.base.BaseParser;

/**
 * @author ztw 这个类只提供基础的解析方法，每个接口对应的解析方法在.parse.api包下面。
 */
public class JiongtuBaseParser extends BaseParser {
    // 错误信息

    public JiongtuBaseParser(String jsonStr) {
        super(jsonStr);

    }

    @Override
    public String getCode() {
        return "0";
    }

    @Override
    public String getMsg() {
        return "cucc";
    }

    @Override
    public boolean isSucc() {
        if (root != null) {
            return true;
        }
        return false;
    }

}
