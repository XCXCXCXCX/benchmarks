package com.xcxcxcxcx.jmh.route_match;

import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XCXCXCXCX
 * @date 2020/8/19 2:28 下午
 */
public class ListRouteMatcher implements RouteMatcher {

    private List<String> routes = new ArrayList<>();
    private AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public void setRoutes(List<Route> routes) {
        this.routes = routes.stream().map(Route::getPath).collect(Collectors.toList());
    }

    @Override
    public Route match(String path) {
        for (String route : routes) {
            if (matcher.match(route, path)) {
                return new Route(route);
            }
        }
        return null;
    }
}
