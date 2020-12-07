package com.daobili.service;

import javax.mail.MessagingException;
import java.io.File;

/**
 *
 * @Author bamaw
 * @Date 2020-12-07  23:56
 * @Description 封装发送邮件接口
 */
public interface MailService {

    /**
     * 发送文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendTextMail(String to, String subject, String content);


    /**
     * 发送Html邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendHtmlMail(String to, String subject, String content);


    /**
     * 发送html+单个img邮件(正文显示)
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param contentId 文件对应id
     * @param file 文件
     */
    void sendHtmlAndImg(String to, String subject, String content, String contentId, File file);

    /**
     * 发送带附件邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param fileName 文件名
     * @param file 文件
     */
    void sendAttachmentMail(String to, String subject, String content, String fileName, File file);


    /**
     * 发送使用 Freemarker 作邮件模板
     * @param to 收件人
     * @param subject 主题
     * @param templatesAddress 模版位置
     * @param templatesName 模版名称
     */
    void sendFreemarkMail(String to, String subject, String templatesAddress, String templatesName) throws MessagingException;

    /**
     * 发送使用 Thymeleaf 作邮件模板
     * @param to
     * @param subject
     * @param templatesName
     */
    void sendThymeleafMail(String to, String subject,String templatesName);

}
