package com.ivan.blog.utils;

/*
 *  @Author: Ivan
 *  @Description:   redis - key设计
 *  @Date: 2019/12/16 20:27
 */
public class RedisUtil {
    /**
     * redis的key
     * 形式为：通过方法名
     * 方法名
     *
     * @param methodName 方法名
     * @return
     */
    public static String getKeyWithMethod(String methodName){
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        return buffer.toString();
    }

    /**
     * redis的key
     * 形式为：通过方法名与参数
     * 方法名:参数值
     *
     * @param methodName 方法名
     * @param param      参数
     * @return
     */
    public static String getKeyWithMethodAndParam(String methodName , Integer param){
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName).append(":");
        buffer.append(param);
        return buffer.toString();
    }

    /**
     * redis的key
     * 形式为： 通过表名与主键值
     * 表名:主键值
     *
     * @param tableName 表名
     * @param majorKeyValue 主键值
     * @return
     */
    public static String getKey(String tableName,String majorKeyValue){
        StringBuffer buffer = new StringBuffer();
        buffer.append(tableName).append(":");
        buffer.append(majorKeyValue);
        return buffer.toString();
    }

    /**
     * redis的key
     * 形式为：通过表名与主键值,字段名
     * 表名:主键值:列名
     *
     * @param tableName 表名
     * @param majorKeyValue 主键值
     * @param column 列名
     * @return
     */
    public static String getKeyWithColumn(String tableName,String majorKeyValue,String column){
        StringBuffer buffer = new StringBuffer();
        buffer.append(tableName).append(":");
        buffer.append(majorKeyValue).append(":");
        buffer.append(column);
        return buffer.toString();
    }
}