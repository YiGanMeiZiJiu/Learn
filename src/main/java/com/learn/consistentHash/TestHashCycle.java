package com.learn.consistentHash;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 小王
 * Date: 3/11/22
 * Time: 1:32 PM
 * Description:
 */
public class TestHashCycle {

    // 机器节点IP前缀
    private static final String IP_PREFIX = "192.168.0.";

    public static void main(String[] args) {
        // 每台真实机器节点上保存的记录条数
        Map<String, Integer> map = new HashMap<String, Integer>();

        // 真实机器节点，模拟15台
        List<Node<String>> nodes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            // 初始化节点记录
            map.put(IP_PREFIX + i, 0);
            Node<String> node = new Node<>(IP_PREFIX + i, "node" + i);
            nodes.add(node);
        }

        IHashService hashService = new HashServiceImpl();
        // 每台真实机器引入100个虚拟节点
        ConsistentHash<Node<String>> consistentHash = new ConsistentHash<>(hashService, 500, nodes);

        // 将10000条记录尽可能均匀的散落到15台机器上
        for (int i = 0; i < 10000; i++) {
            // 产生一个随机字符串做模拟对象
            String data = UUID.randomUUID().toString() + i;
            // 通过hash记录找到真实节点
            Node<String> node = consistentHash.get(data);
            // 在这里可以通过其他工具把记录存储到真实节点上，例如redis等
            // ...
            // 每台真实机器节点上保存的记录数量+1；
            map.put(node.getIp(), map.get(node.getIp())+1);

            // 模拟一下机器掉线
            if (i == 5000) {
                consistentHash.remove(nodes.get(0));
            }
        }

        // 打印每台真实机器节点保存的记录条数
        for (int i = 1; i <= 10; i++) {
            System.out.println(IP_PREFIX + i + "节点记录条数：" + map.get(IP_PREFIX + i));
        }
    }

}
