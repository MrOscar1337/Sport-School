import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.activation.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

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
		
		String filePath = "C:\\Users\\User\\Desktop\\exported_data.xml";

        try {
            printXmlFileContent(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	    public static void printXmlFileContent(String filePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Считываем документ из файла
        Document document = builder.parse(new File(filePath));

        // Выводим содержимое документа
        printNode(document, "");
    }

    private static void printNode(Node node, String indent) {
        // Выводим информацию о текущем узле
        System.out.print(indent + node.getNodeName());

        if (node.getNodeValue() != null && !node.getNodeValue().trim().isEmpty()) {
            System.out.print(": " + node.getNodeValue().trim());
        }

        System.out.println();

        // Рекурсивно обходим дочерние узлы
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                printNode(childNode, indent + "  ");
            }
        }
    }
}
