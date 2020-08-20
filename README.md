# benchmarks
A personal benchmark test project, recorded several method comparisons.


## route match 路由匹配方法对比
理论上在路由匹配上，前缀树数据结构比list数据结构更好，从时间复杂度、匹配效率、空间复杂度上分析，前缀树数据结构更适合路由匹配的场景。              
      
* 第一种   
```
list数据结构存储的路由信息，进行路由匹配。
```

* 第二种
```
前缀树数据结构存储的路由信息，进行路由匹配。
（这里用的比较简单直观的树形结构）
树结构代码如下：
```

```java
 class TreeNode {
        private String nodePath;

        TreeNode(String nodePath) {
            this.nodePath = nodePath;
        }

        private List<TreeNode> sons = new ArrayList<>();

    }
```

> 
- 结论
1. 前缀树数据结构在数据量越大时，匹配效率表现的越优秀，另外在匹配不存在路由时的效率超出list几个数量级。
2. 在更新路由的操作上会明显劣于list数据结构。
3. 在基准测试结果中达到百万级别及以上的路由匹配效率差异会比较明显，但在真实场景中，很少有路由数据量特别大的场景，在百万级别及以上的路由匹配，建议采用前缀树，在百万级别以下，建议采用list数据结构。

## snowflake id generator 雪花ID生成器
在雪花算法中会频繁用到当前时间戳来计算，有人提出在高并发场景下，System.currentTimeMillis()会因为阻塞而影响雪花ID生产速度。

* 第一种
```
通过System.currentTimeMillis()获取当前时间戳
```

* 第二种
```
启动守护线程来维护一个毫秒级别的时钟，用volatile long存储时间戳，这样就通过守护线程来保证每毫秒仅调用一次System.currentTimeMillis()
```

> 
- 结论
1. 无论是多线程竞争单个雪花ID生成器还是多线程竞争多个雪花ID生成器的场景，两者比较差异都不明显。
2. 在线程数较小时，第二种方式表现的稍好，在线程数较大时，第一种方式表现的更好。（分析可能是单机性能有限，线程切换影响较明显）
3. 真实场景中，雪花ID生成器的实例处理能力有限，即使在高并发场景下，System.currentTimeMillis()对雪花ID的生产效率也不会造成较大影响。

