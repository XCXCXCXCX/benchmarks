package org.hzero.jmh.snowflake_id;

/**
 * @author XCXCXCXCX
 * @date 2020/8/20 11:01 上午
 */
public class SystemSnowFlakeIdGenerator extends SnowFlakeIdGenerator {

    public SystemSnowFlakeIdGenerator(long startTimeStamp, long dataCenterId, long workerId) {
        super(startTimeStamp, dataCenterId, workerId);
    }

    @Override
    public long now() {
        return System.currentTimeMillis();
    }
}
