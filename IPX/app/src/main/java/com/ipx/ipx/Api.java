package com.ipx.ipx;

/**
 * http请求路径
 */
public interface Api {
    String BASE = "http://agent-dev.ipx.net/agent";
    String LOGIN = BASE + "/login";
    String PROJECT_LIST = BASE + "/project/list/1/10";
    String PROJECT_DETAIL = BASE + "/project/";
}
