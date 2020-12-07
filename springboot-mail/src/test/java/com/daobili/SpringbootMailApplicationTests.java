package com.daobili;

import com.daobili.service.MailService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.File;

@SpringBootTest
@RunWith(SpringRunner.class)
class SpringbootMailApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MailService mailService;

    /**
     * 发送普通文本邮件
     */
    @Test
    public void sendTextMail() {
        mailService.sendTextMail("tbamaw@163.com", "主题：巴莫，牛肉汤配方。", "内容：国家保密配方");
    }

    /**
     * 发送html邮件
     */
    @Test
    public void sendHtmlMail() {
        String content = "<h1>内容：第一封html邮件-保密配方，值得拥有</h1>";
        mailService.sendHtmlMail("tbamaw@163.com", "主题：巴莫，牛肉汤配方 html邮件",content);
    }

    /**
     * 发送html+图片
     */
    @Test
    public void sendHtmlAndImgMail() {
        String content = "<p>hello 大家好，这是一封牛肉汤配方+图片邮件，这封邮件包含图片，分别如下</p><p>图片：</p><img src='cid:cattle'/>";
        File file = new File("cattle.png");
        mailService.sendHtmlAndImg("tbamaw@163.com", "主题：巴莫，牛肉汤配方 html+图片的邮件", content, "cattle", file);
    }

    /**
     * 发送附件
     */
    @Test
    public void sendAttachmentMail() {
        File file = new File("cattle.png");
        mailService.sendAttachmentMail("tbamaw@163.com", "主题：巴莫，牛肉汤配方 附件发送邮件", "内容：国家保密配方+附件发送", "cattle-new", file);
    }

    /**
     * 发送Freemark邮件
     * @throws MessagingException
     */
    @Test
    public void sendFreemarkMail() throws MessagingException {
        mailService.sendFreemarkMail("tbamaw@163.com", "主题：巴莫，牛肉汤配方 发送Freemarker邮件", "templates","freemarker-mail.ftl");
    }

    /**
     * 发送thymeleaf邮件
     */
    @Test
    public void sendThymeleafMail() {
        mailService.sendThymeleafMail("tbamaw@163.com", "主题：巴莫，牛肉汤配方 发送Freemarker邮件", "thymeleaf-mail.html");
    }


}
