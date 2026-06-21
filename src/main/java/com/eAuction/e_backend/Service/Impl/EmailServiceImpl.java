package com.eAuction.e_backend.Service.Impl;

import com.eAuction.e_backend.Service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendOtpEmail(String toEmail, String name, String otp) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        Template template = freemarkerConfig.getTemplate("otp-email.ftl");
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        model.put("otp", otp);

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        helper.setTo(toEmail);
        helper.setFrom(fromEmail, "Vendue Auctions");
        helper.setSubject("Verify Your Email Address - Vendue");
        helper.setText(html, true);

        mailSender.send(message);
    }
}
