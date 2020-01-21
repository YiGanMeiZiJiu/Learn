package com.learn.thread.Fiber;

import com.sun.xml.internal.ws.api.pipe.Fiber;

import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author 王🍊 2020-01-20
 */
public class Echo {

    public static void main(String[] args) {

        try {
            final ServerSocketChannel ssc = ServerSocketChannel.open().bind(new InetSocketAddress(8080));
            while (true) {
                // 接收socket请求
                final SocketChannel sc = ssc.accept();
//                Fiber.scheduled
            }
        } catch (Exception e) {
            System.out.println("Catch IOException`");
        }

    }

}
