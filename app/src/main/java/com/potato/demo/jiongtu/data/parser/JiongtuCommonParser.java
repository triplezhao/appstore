package com.potato.demo.jiongtu.data.parser;

import org.json.JSONObject;

public class JiongtuCommonParser extends JiongtuBaseParser {


    public JiongtuCommonParser(String jsonStr) {
        super(jsonStr);
        try {
            if (root.optJSONObject("obj") != null) {
                JSONObject obj = root.optJSONObject("obj");
            }
            JSONObject minfo = root.optJSONObject("user");
            if (minfo != null) {
//                showUserBean = showUserBean.createFromJSON(minfo);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
