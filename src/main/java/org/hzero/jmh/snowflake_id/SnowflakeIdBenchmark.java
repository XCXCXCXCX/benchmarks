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

package org.hzero.jmh.snowflake_id;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
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
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class SnowflakeIdBenchmark {

    private static final int NEXT_COUNT = 1000000;
    private static final int CONCURRENT_THREAD = 40;

    private static List<SystemSnowFlakeIdGenerator> generators = new ArrayList<>();
    private static List<OptimizedSnowFlakeIdGenerator> o_generators = new ArrayList<>();

    static{
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                generators.add(new SystemSnowFlakeIdGenerator(0, i, j));
                o_generators.add(new OptimizedSnowFlakeIdGenerator(0, i, j));
            }
        }
    }

    @Benchmark
    @GroupThreads(CONCURRENT_THREAD * 5)
    @Group("test_1_snowflake")
    @OperationsPerInvocation(NEXT_COUNT)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SingleShotTime})
    public void test_1_snowflake() {
        generators.get(0).next();
    }

    @Benchmark
    @GroupThreads(CONCURRENT_THREAD * 5)
    @Group("test_1_optimized_snowflake")
    @OperationsPerInvocation(NEXT_COUNT)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SingleShotTime})
    public void test_1_optimized_snowflake() {
        o_generators.get(0).next();
    }

    @Benchmark
    @GroupThreads(CONCURRENT_THREAD)
    @Group("snowflake_concurrent")
    @OperationsPerInvocation(NEXT_COUNT)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SingleShotTime})
    public void test_2_snowflake_concurrent_1() {
        generators.get(0).next();
    }

    @Benchmark
    @GroupThreads(CONCURRENT_THREAD)
    @Group("snowflake_concurrent")
    @OperationsPerInvocation(NEXT_COUNT)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SingleShotTime})
    public void test_2_snowflake_concurrent_2() {
        generators.get(1).next();
    }

    @Benchmark
    @GroupThreads(CONCURRENT_THREAD)
    @Group("snowflake_concurrent")
    @OperationsPerInvocation(NEXT_COUNT)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SingleShotTime})
    public void test_2_snowflake_concurrent_3() {
        generators.get(2).next();
    }

    @Benchmark
    @GroupThreads(CONCURRENT_THREAD)
    @Group("snowflake_concurrent")
    @OperationsPerInvocation(NEXT_COUNT)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SingleShotTime})
    public void test_2_snowflake_concurrent_4() {
        generators.get(3).next();
    }

    @Benchmark
    @GroupThreads(CONCURRENT_THREAD)
    @Group("snowflake_concurrent")
    @OperationsPerInvocation(NEXT_COUNT)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SingleShotTime})
    public void test_2_snowflake_concurrent_5() {
        generators.get(4).next();
    }

    @Benchmark
    @GroupThreads(CONCURRENT_THREAD)
    @Group("optimized_snowflake_concurrent")
    @OperationsPerInvocation(NEXT_COUNT)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SingleShotTime})
    public void test_2_optimized_snowflake_concurrent_1() {
        o_generators.get(0).next();
    }

    @Benchmark
    @GroupThreads(CONCURRENT_THREAD)
    @Group("optimized_snowflake_concurrent")
    @OperationsPerInvocation(NEXT_COUNT)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SingleShotTime})
    public void test_2_optimized_snowflake_concurrent_2() {
        o_generators.get(1).next();
    }

    @Benchmark
    @GroupThreads(CONCURRENT_THREAD)
    @Group("optimized_snowflake_concurrent")
    @OperationsPerInvocation(NEXT_COUNT)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SingleShotTime})
    public void test_2_optimized_snowflake_concurrent_3() {
        o_generators.get(2).next();
    }

    @Benchmark
    @GroupThreads(CONCURRENT_THREAD)
    @Group("optimized_snowflake_concurrent")
    @OperationsPerInvocation(NEXT_COUNT)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SingleShotTime})
    public void test_2_optimized_snowflake_concurrent_4() {
        o_generators.get(3).next();
    }

    @Benchmark
    @GroupThreads(CONCURRENT_THREAD)
    @Group("optimized_snowflake_concurrent")
    @OperationsPerInvocation(NEXT_COUNT)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SingleShotTime})
    public void test_2_optimized_snowflake_concurrent_5() {
        o_generators.get(4).next();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SnowflakeIdBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
