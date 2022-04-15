package com.rgr.storeApp.service.email;

import com.rgr.storeApp.exceptions.api.NotPrivilege;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

@Service
public class EmailBuilderService {


    final
    Configuration configuration;

    @Autowired
    public EmailBuilderService(@Qualifier("freeMarkerConfiguration") Configuration configuration) {
        this.configuration = configuration;
    }


    public String getEmail(Map model, String name){
        StringBuffer stringBuffer = new StringBuffer();
        try{
            stringBuffer.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    configuration.getTemplate(String.format("%s.ftl", name)), model));
        }catch (Exception e){
            e.printStackTrace();
            throw new NotPrivilege("Email error");
        }
        return stringBuffer.toString();
    }

}
