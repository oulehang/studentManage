package com.student.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2019/3/5.
 */
@Controller
@RequestMapping("/test")
public class AdminController {

    @RequestMapping("faceRecord")
    public void faceRecord(HttpServletRequest request) throws IOException {
        JSONObject jsonObject1 = getJSONObject(request);

    }

    public JSONObject getJSONObject(HttpServletRequest request){
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
