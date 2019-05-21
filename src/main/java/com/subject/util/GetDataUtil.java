package com.subject.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

/**
 * @author liangqin
 * @date 2019/5/12 15:45
 */
public class GetDataUtil<T> {

    /**
     * 获取string对象
     * @param url
     * @return
     */
    public static String getData(String url){
        RestTemplate rest = new RestTemplate();
        String jsonData = rest.getForObject(url, String.class);
        return jsonData;
    }

    /**
     * 获取jsonObject对象
     * @param url
     * @return
     */
    public static JSONObject getJSONObject(String url){
        RestTemplate rest = new RestTemplate();
        JSONObject jsonData = rest.getForObject(url, JSONObject.class);
        JSONObject data = jsonData.getJSONObject("data");
        return data;
    }

    /**
     * 获取string对象
     * @param url
     * @return
     */
    public static String getName(String url){
        RestTemplate rest = new RestTemplate();
        JSONObject jsonData = rest.getForObject(url, JSONObject.class);
        String data = jsonData.getString("data");
        return data;
    }

    /**
     * 获取object
     * @param url
     * @param clazz
     * @return
     */
    public  T getObject(String url, Class<T> clazz){
        RestTemplate rest = new RestTemplate();
        T jsonData = rest.getForObject(url, clazz);
        return jsonData;
    }

    /**
     * 获取教师tname、tpost、tdeep
     * @param turl
     * @return
     */
    public static String getTeacherName(String turl) {
        JSONObject teacherObject = GetDataUtil.getJSONObject(turl);
        String tid = teacherObject.getString("tno");
        String tname = teacherObject.getString("tname");
        String tposTdegree = teacherObject.getString("tpost") + "-" + teacherObject.getString("tdegree");
        if(tname != null && tposTdegree != null && tid != null) {
            return  tid + " " + tname + " " + tposTdegree;
        }
        return null;
    }

    /**
     * 获取教师tpost、tdeep
     * @param tid
     * @return
     */
    public static String getTpostTdegree(String tid) {
        String url = "http://localhost:8089/teacher/getteabytno?tno=" + tid;
        JSONObject teacherObject = GetDataUtil.getJSONObject(url);
        String tposTdegree = teacherObject.getString("tpost") + "-" + teacherObject.getString("tdegree");
        if(tposTdegree != null) {
            return  tposTdegree;
        }
        return null;
    }

    /**
     * 获取教师tname
     * @param tid
     * @return
     */
    public static String getTeacherNameByTid(String tid) {
        String url = "http://localhost:8089/teacher/gettnamebytno?tno=" + tid;
        String tname = GetDataUtil.getName(url);
        if(tname != null) {
            return  tname;
        }
        return null;
    }

    /**
     * 获取教师tnotname
     * @return
     */
    public static JSONArray getTidTname() {
        String url = "http://localhost:8089/teacher/gettnotname";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject jsonObject = restTemplate.getForObject(url, JSONObject.class);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        return jsonArray;
    }

    /**
     * 获取学生
     * @return
     */
    public static JSONArray getStudent() {
        String url = "http://localhost:8089/student/getallstudent";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject jsonObject = restTemplate.getForObject(url, JSONObject.class);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        return jsonArray;
    }

    /**
     * 获取specname
     * @param specid
     * @return
     */
    public static String getSpecNameBySpceId(String specid) {
        String url = "http://localhost:8089/speciality/getspecnamebyspecid?specid=" + specid;
        String specname = GetDataUtil.getName(url);
        if(specname != null) {
            return  specname;
        }
        return null;
    }

}
