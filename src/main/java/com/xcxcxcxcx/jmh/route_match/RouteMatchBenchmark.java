/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.xcxcxcxcx.jmh.route_match;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@BenchmarkMode({Mode.All})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class RouteMatchBenchmark {

    private List<Route> routes = new ArrayList<>();
    private RouteMatcher listRouteMatcher = new ListRouteMatcher();
    private RouteMatcher treeRouteMatcher = new TreeRouteMatcher();
    private Generator initGenerator;
    private String randomPath;
    @Param({"1000", "10000", "100000", "1000000"})
    private int size;

    @Setup
    public void init() {
        initGenerator = new Generator();
        randomPath = initGenerator.randomPath();
        for (int i = 0; i < size; i++) {
            routes.add(buildRoute());
        }
        listRouteMatcher.setRoutes(routes);
        treeRouteMatcher.setRoutes(routes);
        System.out.println("randomPath: " + randomPath);
    }

    private Route buildRoute() {
        return new Route(initGenerator.generatePath());
    }

    @Benchmark
    @OperationsPerInvocation(10000)
    public void test_1_ListSet() {
        listRouteMatcher.setRoutes(routes);
    }

    @Benchmark
    @OperationsPerInvocation(10000)
    public void test_1_TreeSet() {
        treeRouteMatcher.setRoutes(routes);
    }

    @Benchmark
    @OperationsPerInvocation(10000)
    public void test_2_ListByRandomPath() {
        listRouteMatcher.match(randomPath);
    }

    @Benchmark
    @OperationsPerInvocation(10000)
    public void test_2_TreeByRandomPath() {
        treeRouteMatcher.match(randomPath);
    }

    private String never = "never";

    @Benchmark
    @OperationsPerInvocation(10000)
    public void test_3_NotFoundList() {
        listRouteMatcher.match(never);
    }

    @Benchmark
    @OperationsPerInvocation(10000)
    public void test_3_NotFoundTree() {
        treeRouteMatcher.match(never);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RouteMatchBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
