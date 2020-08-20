package com.xcxcxcxcx.jmh.snowflake_id;

/**
 * @author XCXCXCXCX
 * @date 2020/8/20 10:47 上午
 */
public abstract class SnowFlakeIdGenerator {
    /**
     * 序列位数
     */
    private static final int BIT_SEQUENCE = 12;
    /**
     * 数据中心位数
     */
    private static final int BIT_DATA_CENTER = 5;
    /**
     * 工作机器位数
     */
    private static final int BIT_WORKER = 5;

    /**
     * 最大序列值
     */
    private static final long MAX_SEQUENCE = ~(-1L << BIT_SEQUENCE);
    /**
     * 最大数据中心值
     */
    private static final long MAX_DATA_CENTER = ~(-1L << BIT_DATA_CENTER);
    /**
     * 最大工作机器值
     */
    private static final long MAX_WORKER = ~(-1L << BIT_WORKER);

    /**
     * 时间戳左移位数
     */
    private static final long LEFT_TIMESTAMP = BIT_DATA_CENTER + BIT_WORKER + BIT_SEQUENCE;
    /**
     * 数据中心左移位数
     */
    private static final long LEFT_DATA_CENTER = BIT_WORKER + BIT_SEQUENCE;
    /**
     * 工作机器左移位数
     */
    private static final long LEFT_WORKER = BIT_SEQUENCE;

    /**
     * 雪花ID开始时间，可以从起始时间开始使用 69 年
     */
    private final long START_TIMESTAMP;
    /**
     * 数据中心ID
     */
    private final long DATA_CENTER_ID;
    /**
     * 工作机器ID
     */
    private final long WORKER_ID;
    /**
     * 上一次的时间戳
     */
    private long latestTimestamp = 0L;
    /**
     * 序列
     */
    private long sequence = 0L;

    public SnowFlakeIdGenerator(long startTimeStamp, long dataCenterId, long workerId) {
        if (dataCenterId < 0 || dataCenterId > MAX_DATA_CENTER) {
            throw new IllegalArgumentException("Data center id must in 0 ~ " + MAX_DATA_CENTER);
        }
        if (workerId < 0 || workerId > MAX_WORKER) {
            System.out.println(workerId);
            throw new IllegalArgumentException("Worker id must in 0 ~ " + MAX_WORKER);
        }
        this.START_TIMESTAMP = startTimeStamp;
        this.DATA_CENTER_ID = dataCenterId;
        this.WORKER_ID = workerId;
    }

    public synchronized long next() {
        long now = now();
        if (now < latestTimestamp) {
            throw new RuntimeException("Snowflake ID clock abnormal.");
        }
        if (now == latestTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0L) {
                now = nextTime();
            }
        } else {
            sequence = 0L;
        }
        latestTimestamp = now;
        // 时间戳位
        return (now - START_TIMESTAMP) << LEFT_TIMESTAMP
                // 数据中心位
                | DATA_CENTER_ID << LEFT_DATA_CENTER
                // 工作机器位
                | WORKER_ID << LEFT_WORKER
                // 序列位
                | sequence;
    }

    private long nextTime() {
        long now = now();
        while (now <= latestTimestamp) {
            now = now();
        }
        return now;
    }

    public abstract long now();
}
