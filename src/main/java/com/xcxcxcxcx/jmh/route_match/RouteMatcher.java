package com.xcxcxcxcx.jmh.route_match;

import java.util.List;

/**
 * @author XCXCXCXCX
 * @date 2020/8/19 1:42 下午
 */
public interface RouteMatcher {

    void setRoutes(List<Route> routes);

    Route match(String path);

}
