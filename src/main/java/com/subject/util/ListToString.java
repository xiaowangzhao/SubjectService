package com.subject.util;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author liangqin
 * @date 2019/3/26 14:55
 */
public class ListToString<T> {

    public String listToString(List<T> list) {
        StringBuffer sb = new StringBuffer();
        if(list.size() == 0 ) {
            return null;
        }
        Class<?> classType = list.get(0).getClass();
        Field[] fields = classType.getDeclaredFields();
        for(int i = 0; i < list.size(); i++) {
            for(Field field : fields) {
                field.setAccessible(true);
                Object fieldVaule;
                try {
                    fieldVaule = field.get(list.get(i));
                    sb.append(fieldVaule).append(":");
                }catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            sb.append(":");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }
}
