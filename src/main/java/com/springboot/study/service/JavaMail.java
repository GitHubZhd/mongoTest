package com.springboot.study.service;

/**
 * Created by ps on 2017/9/15.
 */
public class JavaMail {

    //    @Async
//    public String sendEmail(String message) {
//
//        try {
//            Properties props = new Properties();                    // 参数配置
//            props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
//            props.setProperty("mail.smtp.host", smtpHost);          // 发件人的邮箱的 SMTP 服务器地址
//            props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
//
//            //开启SSL加密
//            MailSSLSocketFactory sf=new MailSSLSocketFactory();
//            sf.setTrustAllHosts(true);
//            props.put("mail.smtp.ssl.enable","true");
//            props.put("mail.smtp.ssl.socketFactory",sf);
//
//            // 2. 根据配置创建会话对象, 用于和邮件服务器交互
//            Session session = Session.getDefaultInstance(props);
//            session.setDebug(true);
//
//            // 3. 创建一封邮件
//            MimeMessage mess = createMimeMessage(session, from, to,message);
//            // 4. 根据 Session 获取邮件传输对象
//            Transport transport = session.getTransport();
//            transport.connect(from, password);
//            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
//            transport.sendMessage(mess, mess.getAllRecipients());
//            // 7. 关闭连接
//            transport.close();
//            return null;
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 创建一封只包含文本的简单邮件
//     * @param session 和服务器交互的会话
//     * @param sendMail 发件人邮箱
//     * @param receiveMail 收件人邮箱
//     * @return
//     * @throws Exception
//     */
//    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String content) throws Exception {
//        // 1. 创建一封邮件
//        MimeMessage message = new MimeMessage(session);
//        // 2. From: 发件人
//        message.setFrom(new InternetAddress(sendMail));
//        // 3. To: 收件人
//        String[] recArr=receiveMail.split(",");
//        Address[] addressesArr=new Address[recArr.length];
//        for (int i=0;i<addressesArr.length;i++){
//            addressesArr[i]=new InternetAddress(recArr[i]);
//        }
//        message.setRecipients(MimeMessage.RecipientType.TO, addressesArr);
//        // 4. Subject: 邮件主题
//        message.setSubject("OP刷新警报", "UTF-8");
//        // 5. Content: 邮件正文
//        message.setContent(content, "text/html;charset=UTF-8");
//        // 6. 设置发件时间
//        message.setSentDate(new Date());
//        // 7. 保存设置
//        message.saveChanges();
//        return message;
//    }
}
