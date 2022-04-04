package com.rgr.storeApp.service.email;

import com.rgr.storeApp.exceptions.api.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailService implements EmailSender{

    private final JavaMailSender javaMailSender;


    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    @Async
    public void send(String to, String email) {
        //token;
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setSubject("HELLO WORLD");
            helper.setTo("maksim.shmakoff.03@yandex.ru");
            mimeMessage.setFrom("the.secretshop@yandex.ru");
            javaMailSender.send(helper.getMimeMessage());
        }catch (Exception e){
            throw new NotFound("ERROR EMAIL");
        }

    }


}
