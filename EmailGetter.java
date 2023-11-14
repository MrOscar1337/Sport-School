import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.activation.*;

public class EmailSender {
    public static void main(String[] args) {
        final String username = "tagirov@bk.ru";
        final String password = "secretpass";

        Properties props = new Properties();
        props.put("mail.imap.host", "imap.mail.ru");
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");

        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore("imap");
            store.connect(username, password);

            Folder inbox = store.getFolder("tomyself");
            inbox.open(Folder.READ_ONLY);

            // Получаем все сообщения в папке "tomyself"
            Message[] messages = inbox.getMessages();

            for (Message message : messages) {
                // Проверяем, есть ли вложения
                if (message.getContent() instanceof Multipart) {
                    Multipart multipart = (Multipart) message.getContent();
                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart bodyPart = multipart.getBodyPart(i);

                        // Проверяем, является ли часть вложением
                        if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                            // Получаем имя вложенного файла
                            String fileName = bodyPart.getFileName();

                            // Скачиваем вложение
                            bodyPart.saveFile("C:\\Users\\User\\Desktop\\" + fileName);

                            System.out.println("Attachment downloaded: " + fileName);
                        }
                    }
                }
            }


            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
