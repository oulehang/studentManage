package com.student.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2019/3/22.
 */
public class getJSONObject{

    public JSONObject getJsonObject(HttpServletRequest request) throws IOException {
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String result = new String(outSteam.toByteArray(), "utf-8");
//            result = ToUTF8.toUTF8(result);
        JSONObject object = JSON.parseObject(result);
        return object;
    }
}
