package com.zqskate.schedule.service;

/**
 * @author CHAN
 * @date 2020/06/22
 */
public interface MailService {

    /**
     * 发送简单邮件
     *
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     *
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件路径
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 发送 html 邮件
     *
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送图片邮件
     *
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param rscPath 地址
     * @param rscId id
     */
    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);

}
