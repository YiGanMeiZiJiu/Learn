package com.learn.consistentHash;

/**
 * Created with IntelliJ IDEA.
 * User: 小王
 * Date: 3/11/22
 * Time: 10:50 AM
 * Description: 模拟机器节点
 */
public class Node<T> {

    private String ip;
    private String name;

    public Node(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 使用IP当做hash的Key
     *
     * @return String
     */
    @Override
    public String toString() {
        return ip;
    }
}
