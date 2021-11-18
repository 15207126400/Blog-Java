package com.ivan.blog.constants;

/**
 * @author Ivan
 * @date 2021/10/26 14:26
 * @Description
 */
public interface BlogConstants {

    /**通用**/
    Integer SUCCESS = 0;
    Integer FAIL = 1;
    String ROOT = "/";

    /**登录**/
    String SHIRO_LOGIN_COUNT = "shiro-login-count";//用户登录次数计数
    String SHIRO_LOGIN_LEFTCOUNT = "shiro-login-left-count";//用户登录剩余次数
    String SHIRO_IS_LOCK = "shiro-is-lock";//用户登录是否被锁定

    /**Minio**/
    String MINIO_MAIN_PATH = "http://1.117.251.254:9000/";
    String MINIO_ACCOUNT_BUCKET = "account";
    String MINIO_MAIN_BUCKET = "blog";
    String MINIO_RICH_TEXT_BUCKET = "rich";

}
