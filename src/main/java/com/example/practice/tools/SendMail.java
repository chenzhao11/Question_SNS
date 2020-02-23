package com.example.practice.tools;

import com.sun.mail.util.MimeUtil;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

@Service
@Component
public class SendMail implements InitializingBean {
    private JavaMailSenderImpl mailSender;
    private static final Logger logger = LoggerFactory.getLogger(SendMail.class);
    VelocityEngine velocityEngine = new VelocityEngine();


    public boolean sendHtmlMail(String to, String subject, String template, Map<String, Object> model) {
        try {
            String nick = MimeUtility.encodeText("1318814182");
            InternetAddress from = new InternetAddress(nick + "<1318814182@qq.com>");
            //mailsender创建mimemessage内容     help对象帮忙设置mime内容
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "utf-8", model);
           //true重要不然不解析HTML标签
            mimeMessageHelper.setText(text,true);
            //创建mime对象建立好的在使用mialsender  来发送
            mailSender.send(mimeMessage);
            return true;


        } catch (Exception e) {
            logger.error("邮件发送失败！" + e.getMessage());
        }

        return false;
    }

    //配置mailsender
    @Override
    public void afterPropertiesSet() throws Exception {

        /*   重要  不然找不到文件*/
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER,"classpath" );
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("1318814182@qq.com");
        mailSender.setPassword("akjqyiakqmlmjhci");
        mailSender.setHost("smtp.qq.com");
        //mailSender.setHost("smtp.qq.com");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        //javaMailProperties.put("mail.smtp.auth", true);
        //javaMailProperties.put("mail.smtp.starttls.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
