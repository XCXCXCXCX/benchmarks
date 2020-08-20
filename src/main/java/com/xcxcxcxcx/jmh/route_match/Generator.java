package com.xcxcxcxcx.jmh.route_match;

import java.util.Random;

/**
 * @author XCXCXCXCX
 * @date 2020/8/19 3:39 下午
 */
public class Generator {

    private int[] indexes;
    private final int charSize;
    private Random random = new Random();

    public Generator() {
        this(10, 26);
    }

    public Generator(int level, int charSize) {
        this.indexes = new int[level];
        this.charSize = charSize;
    }

    public String generatePath() {
        StringBuilder sb = new StringBuilder("/");
        for (int i = 0; i < indexes.length; i++) {
            sb.append((char)('a' + indexes[i])).append("/");
        }
        sb.append("/**");
        index(indexes.length - 1);
        return sb.toString();
    }

    public String randomPath() {
        StringBuilder sb = new StringBuilder("/");
        for (int i = 0; i < indexes.length; i++) {
            sb.append((char)('a' + random.nextInt(charSize))).append("/");
        }
        return sb.toString();
    }

    private void index(int i) {
        if (i < 0) {
            return;
        }
        if (++indexes[i] >= charSize) {
            indexes[i] = 0;
            index(i - 1);
        }
    }
}
