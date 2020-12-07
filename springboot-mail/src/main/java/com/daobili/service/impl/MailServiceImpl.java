package com.daobili.service.impl;

import com.daobili.domain.Product;
import com.daobili.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.StringWriter;
import java.util.Date;

/**
 *
 * @Author bamaw
 * @Date 2020-12-07  23:55
 * @Description 封装发送邮件接口实现类
 */
@Service
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private TemplateEngine templateEngine;


    @Value("${spring.mail.username}")
    private String from;


    @Override
    public void sendTextMail(String to, String subject, String content) {
        // 创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 邮件发送人
        message.setFrom(from);
        // 邮件接收人
        message.setTo(to);
        // 邮件主题
        message.setSubject(subject);
        // 邮件内容
        message.setText(content);
        // 邮件发送日期
        message.setSentDate(new Date());
        // 执行发送
        javaMailSender.send(message);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {

        // 获取MimeMessage对象
        MimeMessage message = javaMailSender.createMimeMessage();
        // 邮件配置的辅助工具类
        MimeMessageHelper messageHelper;

        try {
            messageHelper = new MimeMessageHelper(message, true);
            // 邮件发送人
            messageHelper.setFrom(from);
            // 邮件接收人
            messageHelper.setTo(to);
            // 邮件主题
            messageHelper.setSubject(subject);
            // 邮件内容, html格式
            messageHelper.setText(content, true);
            // 邮件发送日期
            messageHelper.setSentDate(new Date());
            // 执行发送
            javaMailSender.send(message);
            logger.info("From:{}->To:{}邮件发送成功",from,to);
        } catch (MessagingException e) {
            logger.error("邮件发送异常", e);
            e.printStackTrace();
        }
    }

    @Override
    public void sendHtmlAndImg(String to, String subject, String content, String contentId, File file) {
        // 获取MimeMessage对象
        MimeMessage message = javaMailSender.createMimeMessage();
        // 邮件配置的辅助工具类
        MimeMessageHelper messageHelper;

        try {
            messageHelper = new MimeMessageHelper(message, true);
            // 邮件发送人
            messageHelper.setFrom(from);
            // 邮件接收人
            messageHelper.setTo(to);
            // 邮件主题
            messageHelper.setSubject(subject);
            // 邮件内容, html格式
            messageHelper.setText(content, true);
            // 邮件发送日期
            messageHelper.setSentDate(new Date());
            messageHelper.setText(content);
            messageHelper.addInline(contentId, new FileSystemResource(file));
            // 执行发送
            javaMailSender.send(message);
            logger.info("From:{}->To:{}邮件发送成功",from,to);
        } catch (MessagingException e) {
            logger.error("邮件发送异常", e);
            e.printStackTrace();
        }
    }

    @Override
    public void sendAttachmentMail(String to, String subject, String content, String fileName, File file) {
        // 获取MimeMessage对象
        MimeMessage message = javaMailSender.createMimeMessage();
        // 邮件配置的辅助工具类
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            // 邮件发送人
            messageHelper.setFrom(from);
            // 邮件接收人
            messageHelper.setTo(to);
            // 邮件主题
            messageHelper.setSubject(subject);
            // 邮件内容, html格式
            messageHelper.setText(content, true);
            // 邮件发送日期
            messageHelper.setSentDate(new Date());

            messageHelper.addAttachment(fileName, file);
            // 执行发送
            javaMailSender.send(message);
            logger.info("From:{}->To:{}邮件发送成功",from,to);
        } catch (MessagingException e) {
            logger.error("邮件发送异常", e);
            e.printStackTrace();
        }
    }

    @Override
    public void sendFreemarkMail(String to, String subject, String templatesAddress, String templatesName){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage,true);
            // 邮件发送人
            messageHelper.setFrom(from);
            // 邮件接收人
            messageHelper.setTo(to);
            // 邮件主题
            messageHelper.setSubject(subject);
            // 邮件发送日期
            messageHelper.setSentDate(new Date());
            // 构建Freemarker的基本配置
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
            // 配置模版位置
            ClassLoader loader = MailServiceImpl.class.getClassLoader();
            configuration.setClassLoaderForTemplateLoading(loader, "templates");
            // 加载模版
            Template template = configuration.getTemplate("freemarker-mail.ftl");
            Product product = new Product();
            product.setName("牛肉粉");
            product.setTaste("酸辣味");
            product.setBatching("牛肉及粉");
            product.setSupplement("无添加剂");
            product.setAddress("杭州");
            StringWriter out = new StringWriter();
            // 模板渲染，渲染的结果将被保存到 out 中 ，将out 中的 html 字符串对应发送即可
            template.process(product, out);
            messageHelper.setText(out.toString(),true);
            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendThymeleafMail(String to, String subject, String templatesName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage,true);
            // 邮件发送人
            messageHelper.setFrom(from);
            // 邮件接收人
            messageHelper.setTo(to);
            // 邮件主题
            messageHelper.setSubject(subject);
            // 邮件发送日期
            messageHelper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name", "牛肉粉");
            context.setVariable("taste", "麻辣味");
            context.setVariable("batching", "牛肉及粉");
            context.setVariable("supplement", "无添加剂");
            context.setVariable("address", "杭州");
            String process = templateEngine.process("thymeleaf-mail.html", context);
            messageHelper.setText(process,true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
