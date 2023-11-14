package sportschool;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

    public static void Send() throws MessagingException, IOException {
    	final String username = "tagirov2004@bk.ru";
        final String password = "g7NxypTD0y7a6B1gPRzp";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("tagirov2004@bk.ru"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("tagirov2004@bk.ru"));
        message.setSubject("Subject of the Email");

        // Создаем Multipart объект
        Multipart multipart = new MimeMultipart();

        // Добавляем текстовую часть письма
        BodyPart textPart = new MimeBodyPart();
        textPart.setText("Hello, this is the text of the email.");
        multipart.addBodyPart(textPart);

        // Добавляем вложение
        BodyPart attachmentPart = new MimeBodyPart();
        ((MimeBodyPart) attachmentPart).attachFile("C:\\Users\\User\\Desktop\\exported_data.xml");
        multipart.addBodyPart(attachmentPart);

        // Устанавливаем Multipart как контент сообщения
        message.setContent(multipart);

        try (Transport transport = session.getTransport("smtp")) {
            transport.connect(username, password);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        System.out.println("Email sent successfully.");
    }
}
