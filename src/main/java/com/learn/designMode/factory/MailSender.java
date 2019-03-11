package com.learn.designMode.factory;

public class MailSender implements Sender {

    @Override
    public void Send() {
        System.out.println("发送邮件");
    }
}
