package com.xcxcxcxcx.jmh.route_match;

import java.util.Map;

/**
 * @author XCXCXCXCX
 * @date 2020/8/19 1:43 下午
 */
public class Route {

    private String path;
    private Map<String, Object> config;

    public Route(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "Route{" +
                "path='" + path + '\'' +
                ", config=" + config +
                '}';
    }
}
