package com.kevin.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaomi.ai.api.Sys;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonPost {
    public static String url = "http://speech-preview.ai.srv/internal/speech/v1.0/tts";
    public static String textPath = "d:\\text2.txt";
    public static String models = "[{\n" +
            "\t\t\t\"name\": \"baoqiu2\",\n" +
            "\t\t\t\"vendor\": \"ShenSheng\",\n" +
            "\t\t\t\"speaker\": \"zh-CN-male-s16_mi633779948603056129_wbx07sEZFz\",\n" +
            "\t\t\t\"status\": \"Done\",\n" +
            "\t\t\t\"ownership\": \"Owner\",\n" +
            "\t\t\t\"vendor_id\": \"633779948603056128\",\n" +
            "\t\t\t\"gender\": \"male\",\n" +
            "\t\t\t\"image_file\": \"\",\n" +
            "\t\t\t\"image_url\": \"\",\n" +
            "\t\t\t\"remaining\": 0,\n" +
            "\t\t\t\"timestamp\": 1602682514984\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"hangjun\",\n" +
            "\t\t\t\"vendor\": \"ShenSheng\",\n" +
            "\t\t\t\"speaker\": \"zh-CN-male-s16_mi633774072014052353_0Y8V9i9qzf\",\n" +
            "\t\t\t\"status\": \"Done\",\n" +
            "\t\t\t\"ownership\": \"Owner\",\n" +
            "\t\t\t\"vendor_id\": \"633774072014052352\",\n" +
            "\t\t\t\"gender\": \"male\",\n" +
            "\t\t\t\"image_file\": \"\",\n" +
            "\t\t\t\"image_url\": \"\",\n" +
            "\t\t\t\"remaining\": 0,\n" +
            "\t\t\t\"timestamp\": 1602681113879\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"chuqiao\",\n" +
            "\t\t\t\"vendor\": \"ShenSheng\",\n" +
            "\t\t\t\"speaker\": \"zh-CN-female-s16_mi633771910324296705_LnotIvre0S\",\n" +
            "\t\t\t\"status\": \"Done\",\n" +
            "\t\t\t\"ownership\": \"Owner\",\n" +
            "\t\t\t\"vendor_id\": \"633771910324296704\",\n" +
            "\t\t\t\"gender\": \"female\",\n" +
            "\t\t\t\"image_file\": \"\",\n" +
            "\t\t\t\"image_url\": \"\",\n" +
            "\t\t\t\"remaining\": 0,\n" +
            "\t\t\t\"timestamp\": 1602680598705\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"junyu\",\n" +
            "\t\t\t\"vendor\": \"ShenSheng\",\n" +
            "\t\t\t\"speaker\": \"zh-CN-male-s16_mi633772350717829121_ashzNKPBIO\",\n" +
            "\t\t\t\"status\": \"Done\",\n" +
            "\t\t\t\"ownership\": \"Owner\",\n" +
            "\t\t\t\"vendor_id\": \"633772350717829120\",\n" +
            "\t\t\t\"gender\": \"male\",\n" +
            "\t\t\t\"image_file\": \"\",\n" +
            "\t\t\t\"image_url\": \"\",\n" +
            "\t\t\t\"remaining\": 0,\n" +
            "\t\t\t\"timestamp\": 1602680703871\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gangge\",\n" +
            "\t\t\t\"vendor\": \"ShenSheng\",\n" +
            "\t\t\t\"speaker\": \"zh-CN-male-s16_mi633771375479233537_3Uh8HSzM2B\",\n" +
            "\t\t\t\"status\": \"Done\",\n" +
            "\t\t\t\"ownership\": \"Owner\",\n" +
            "\t\t\t\"vendor_id\": \"633771375479233536\",\n" +
            "\t\t\t\"gender\": \"male\",\n" +
            "\t\t\t\"image_file\": \"\",\n" +
            "\t\t\t\"image_url\": \"\",\n" +
            "\t\t\t\"remaining\": 0,\n" +
            "\t\t\t\"timestamp\": 1602680471102\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"shangkun\",\n" +
            "\t\t\t\"vendor\": \"ShenSheng\",\n" +
            "\t\t\t\"speaker\": \"zh-CN-female-s16_mi633768418721405953_ENJlkGOQvz\",\n" +
            "\t\t\t\"status\": \"Done\",\n" +
            "\t\t\t\"ownership\": \"Owner\",\n" +
            "\t\t\t\"vendor_id\": \"633768418721405952\",\n" +
            "\t\t\t\"gender\": \"female\",\n" +
            "\t\t\t\"image_file\": \"\",\n" +
            "\t\t\t\"image_url\": \"\",\n" +
            "\t\t\t\"remaining\": 0,\n" +
            "\t\t\t\"timestamp\": 1602679766199\n" +
            "\t\t}]";
    public static JSONObject body = JSON.parseObject("{\n" +
            "  \"app_id\": \"326813440150602752\",\n" +
            "  \"to_speak\": \"亲爱的，哈哈哈你fafadf！\",\n" +
            "  \"lang\": \"zh-CN\",\n" +
            "  \"request_id\": \"ysm001111121234567\",\n" +
            "  \"vendor\": \"ShenSheng\",\n" +
            "  \"speaker\": \"zh-CN-male-s16_mi633779948603056129_wbx07sEZFz\",\n" +
            "  \"volume\": 50\n" +
            "}");
    public static String outputPath = "d:\\ptts";

    public static CloseableHttpClient httpClient = HttpClients.createDefault();
    public static void main(String[] args)throws Exception {

        File file = new File(outputPath+"\\"+"forty");
        if(!file.exists()){
            file.createNewFile();
        }
        long start = System.currentTimeMillis();
        List<String> res = getStrsFromFile(textPath);
        JSONArray ms = JSON.parseArray(models);
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        for(int j=0;j<res.size();j++) {
            body.put("to_speak", res.get(j));
            //body.put("speaker",model.getString("speaker"));
            //System.out.println(body.toString());
            String response = HttpPostWithJson(url,body.toString());
            System.out.println(response);
            out.write(res.get(j)+"    ");
            out.write(JSON.parseObject(response).getString("file_url"));
            out.newLine();
            out.flush();
        }

        out.close();

        long end = System.currentTimeMillis();
        System.out.println("cost: " + (end-start));

       // System.out.println(JsonPost.HttpPostWithJson(url, json));

    }

    public static List<String> getStrsFromFile(String path) {
        List<String> res = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = null;
            while((line = reader.readLine())!=null){
                res.add(line);
            }
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static String HttpPostWithJson(String url, String json) {
        String returnValue = "这是默认返回值，接口调用失败";
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);

            //第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json,"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "text/plain;charset=utf-8");
            httpPost.setEntity(requestEntity);

            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //第五步：处理返回值
        return returnValue;
    }



}
