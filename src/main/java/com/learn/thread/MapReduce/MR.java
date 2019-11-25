package com.learn.thread.MapReduce;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class MR extends RecursiveTask<Map<String, Long>> {

    private String[] fc;
    private int start, end;

    public MR(String[] fc, int start, int end) {
        this.fc = fc;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Map<String, Long> compute() {
        if (end - start == 1) {
            return cacl(fc[start]);
        } else {
            int mid = (start + end) / 2;
            MR mr1 = new MR(fc, start, mid);
            mr1.fork();
            MR mr2 = new MR(fc, mid, end);
            // 计算子任务，返回合并的结果
            return merge(mr2.compute(), mr1.join());
        }
    }

    // 合并结果
    private Map<String, Long> merge(Map<String, Long> m1, Map<String, Long> m2) {
        Map<String, Long> result = new HashMap<>();
        result.putAll(m1);
        // 合并结果
        m2.forEach((k, v) -> {
            Long c = result.get(k);
            if (c != null)
                result.put(k, c + v);
            else
                result.put(k, v);
        });
        return result;
    }

    // 统计单词数量
    private Map<String, Long> cacl(String line) {
        Map<String, Long> result = new HashMap<>();
        // 分割单词
        String[] words = line.split("\\s+");
        // 统计单词数量
        for (String word : words) {
            Long v = result.get(word);
            if (v != null)
                result.put(word, v + 1);
            else
                result.put(word, 1L);
        }
        return result;
    }

}
