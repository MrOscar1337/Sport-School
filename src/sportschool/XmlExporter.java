package sportschool;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlExporter {
    public void exportToXml() {
        try {
            // Путь к файлу XML
            String xmlFilePath = "C:\\Users\\User\\Desktop\\exported_data.xml";

            // Создаем новый документ XML
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Создаем корневой элемент
            Element rootElement = document.createElement("TimetableEntry");
            document.appendChild(rootElement);

            // Выполняем SQL-запрос и обрабатываем результат
            String query = "SELECT group_name, coach_surname, coach_name, sport_name " +
                    "FROM public.\"Groups\", public.\"Coaches\", public.\"Sports\" " +
                    "WHERE public.\"Sports\".\"sport_id\" = public.\"Coaches\".\"coach_sport_id\" " +
                    "AND public.\"Groups\".\"sport_id\" = public.\"Coaches\".\"coach_sport_id\"";

            DbFunctions db = new DbFunctions();
            try (Connection conn = db.connect_to_db("Sports school", "postgres", db.Return_pass());
                 Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                // Переменные для отслеживания текущего вида спорта и группы
                String currentSport = null;
                String currentGroup = null;
                Element sportElement = null;
                Element groupElement = null;

                while (resultSet.next()) {
                    String sport = resultSet.getString("sport_name");
                    String group = resultSet.getString("group_name");
                    String coachName = resultSet.getString("coach_name");
                    String coachSurname = resultSet.getString("coach_surname");

                    // Если вид спорта изменился, создаем новый элемент Sport
                    if (!sport.equals(currentSport)) {
                        currentSport = sport;
                        sportElement = document.createElement("Sport");
                        sportElement.setAttribute("Type", sport);
                        rootElement.appendChild(sportElement);
                    }

                    // Если группа изменилась, создаем новый элемент Group
                    if (!group.equals(currentGroup)) {
                        currentGroup = group;
                        groupElement = document.createElement("Group");
                        groupElement.setAttribute("Name", group);
                        sportElement.appendChild(groupElement);
                    }

                    // Создаем элемент Coach и добавляем его в элемент Group
                    Element coachElement = document.createElement("Coach");
                    coachElement.setAttribute("Name", coachName);
                    coachElement.setAttribute("Surname", coachSurname);
                    groupElement.appendChild(coachElement);
                }
            }

            // Используем Transformer для записи документа в файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Данные успешно выгружены в XML файл.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
