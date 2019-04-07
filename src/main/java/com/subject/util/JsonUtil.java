package com.subject.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
/**
 * @author liangqin
 * @date 2019/3/27 14:47
 */
public class JsonUtil<T>{

    public static JSONObject mapToJson(Map<String, Object> map) {
        String data = JSON.toJSONString(map);
        return JSON.parseObject(data);
    }
    /**
     * map中取key对应的value
     * @param map
     * @param key
     * @return
     */
    public String mapToString(Map<String, Object> map, String key) {
        JSONObject jsonObject = mapToJson(map);
        return jsonObject.getString(key);
    }
    /**
     * map中取类对象
     * @param map
     * @param clazz
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz, String key) {
        T t = null;
        JSONObject jsonObject = mapToJson(map);
        JSONObject object = jsonObject.getJSONObject(key);
        t = object.toJavaObject(clazz);
        return t;
    }
    /**
     * map中取list
     * @param map
     * @param clazz
     * @param key
     * @return
     */
    public  List<T> mapToList(Map<String, Object> map, Class<T> clazz, String key) {
        List<T> t = null;
        JSONObject jsonObject = mapToJson(map);
        JSONArray array = jsonObject.getJSONArray(key);
        t = array.toJavaList(clazz);
        return t;
    }

    public static JSONArray getTeacher() throws IOException {

        File file = ResourceUtils.getFile("classpath:json/teacher.json");
        String content = FileUtils.readFileToString(file);
        JSONObject jsonObject = JSON.parseObject(content);
        JSONArray jsonArray = jsonObject.getJSONArray("teacher");
        return  jsonArray;
    }
}
