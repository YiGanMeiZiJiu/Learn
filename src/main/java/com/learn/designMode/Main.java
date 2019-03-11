package com.learn.designMode;

import com.learn.designMode.factory.Provider;
import com.learn.designMode.factory.SendMailFactory;
import com.learn.designMode.factory.Sender;
import com.learn.designMode.factory.SenderFactory;
import com.learn.designMode.proxy.Proxy;
import com.learn.designMode.proxy.RealSubject;

public class Main {
    public static void main(String[] args) {
        /**
         普通工厂模式
         */
        Sender sender = SenderFactory.produceSms();
        sender.Send();

        /**
         抽象工厂模式
         */
        Provider provider = new SendMailFactory();
        sender = provider.produce();
        sender.Send();

        /**
         * 代理模式
         */
        RealSubject realSubject = new RealSubject();
        Proxy proxy = new Proxy(realSubject);
        proxy.request();
    }
}
