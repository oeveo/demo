package com.springboot.demo.util;

import lombok.Data;

@Data
public class Mail {

    private String recipient;//邮件接收人

    private String subject; //邮件主题

    private String content; //邮件内容

    public Mail(String recipient, String subject, String content) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }
}
