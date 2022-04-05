package com.rgr.storeApp.service.email;

import com.rgr.storeApp.exceptions.api.NotPrivilege;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;

@Service
public class EmailBuilderService {


    @Qualifier("freeMarkerConfiguration")
    @Autowired
    Configuration configuration;


    public String getVerificationEmail(Map model){

        StringBuffer stringBuffer = new StringBuffer();
        try{
            stringBuffer.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    configuration.getTemplate("email.ftl"), model));
        }catch (Exception e){
            e.printStackTrace();
            throw new NotPrivilege("Email errorr");
        }



        return stringBuffer.toString();
    }

}
