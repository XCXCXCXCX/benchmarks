package org.hzero.jmh.snowflake_id;

/**
 * @author XCXCXCXCX
 * @date 2020/8/20 11:02 上午
 */
public class OptimizedSnowFlakeIdGenerator extends SnowFlakeIdGenerator {

    public OptimizedSnowFlakeIdGenerator(long startTimeStamp, long dataCenterId, long workerId) {
        super(startTimeStamp, dataCenterId, workerId);
    }

    @Override
    public long now() {
        return SystemClock.now();
    }
}
