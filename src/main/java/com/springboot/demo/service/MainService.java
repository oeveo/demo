package com.springboot.demo.service;

import com.springboot.demo.util.Mail;
import com.springboot.demo.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    @Autowired
    private MailUtils mailUtils;

    @Async
    public void sendMail(String recipient,String subject,String content){
        mailUtils.sendMail(new Mail(recipient,subject,content));
    }
}
