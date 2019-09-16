package com.springboot.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class MailUtils {

    @Value("${spring.mail.username}")
    private String MAIL_SENDER; //邮件发送者

    @Autowired
    private JavaMailSender javaMailSender;

    private Logger logger = LoggerFactory.getLogger(MailUtils.class);

    public void sendMail(Mail mail){
        MimeMessage mimeMailMessage = null;
        try{
            mimeMailMessage = javaMailSender.createMimeMessage();
            //true 表示需要创建一个multipart message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);

            // 发送人
            mimeMessageHelper.setFrom(MAIL_SENDER);
            // 接受者
            mimeMessageHelper.setTo(mail.getRecipient());
            // 主题
            mimeMessageHelper.setSubject(mail.getSubject());

            //邮件抄送
            //mimeMessageHelper.addCc("抄送人");
            // 内容
            mimeMessageHelper.setText(mail.getContent(), true);
            // 发送邮件
            javaMailSender.send(mimeMailMessage);
        }catch (Exception e){
            logger.error("邮件发送失败", e.getMessage());
        }
    }
}
