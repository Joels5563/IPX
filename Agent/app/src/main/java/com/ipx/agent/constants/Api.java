package com.ipx.agent.constants;

/**
 * http请求路径
 */
public interface Api {
    /**
     * 基本路径
     */
    String BASE = "http://agent-dev.ipx.net/agent";
    //请求路径
    String LOGIN = BASE + "/login";
    String PROJECT_LIST = BASE + "/project/list/1/10";
    String PROJECT_DETAIL = BASE + "/project/";
}
