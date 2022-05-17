package com.rgr.storeApp.service.email;

import com.rgr.storeApp.config.ConfigStrings;
import com.rgr.storeApp.dto.product.ProductLiteResponse;
import com.rgr.storeApp.exceptions.api.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EmailService implements EmailSender{

    private final JavaMailSender javaMailSender;
    private final EmailBuilderService emailBuilderService;
    private static String ngrokUrl = ConfigStrings.ngrokUrl;
    private String urlTmp = ngrokUrl + "/api/accept/%s";


    @Autowired
    public EmailService(JavaMailSender javaMailSender, EmailBuilderService emailBuilderService) {
        this.javaMailSender = javaMailSender;
        this.emailBuilderService = emailBuilderService;
    }



    @Override
    @Async
    public void sendVerification(String token, String to, String username) {
        try {
            String url = String.format(urlTmp, token);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            Map model = new HashMap();
            model.put("username", username);
            model.put("url", url);
            helper.setText(emailBuilderService.getEmail(model, "email"), true);
            helper.setSubject("Подтверждение электронной почты ");
            helper.setTo("maksim.shmakoff.03@yandex.ru");
            mimeMessage.setFrom("the.secretshop@yandex.ru");
            javaMailSender.send(helper.getMimeMessage());
        }catch (Exception e){
            throw new NotFound("ERROR EMAIL");
        }
    }


    @Async
    public void sendCheck(String to, List<ProductLiteResponse> products) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            Map model = new HashMap();
            System.out.println(products);
            model.put("products", products);
            model.put("sum", 1000);
            helper.setText(emailBuilderService.getEmail(model, "check"), true);
            helper.setSubject("Чек");
            helper.setTo("maksim.shmakoff.03@yandex.ru");
            mimeMessage.setFrom("the.secretshop@yandex.ru");
            javaMailSender.send(helper.getMimeMessage());
        }catch (Exception e){
            throw new NotFound("ERROR EMAIL");
        }
    }
}
