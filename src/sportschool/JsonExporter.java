package sportschool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JsonExporter {
	 public void exportToJson() {
         try {
             // Выполняем SQL-запрос и обрабатываем результат
             String query = "SELECT group_name, coach_surname, coach_name, sport_name " +
                     "FROM public.\"Groups\", public.\"Coaches\", public.\"Sports\" " +
                     "WHERE public.\"Sports\".\"sport_id\" = public.\"Coaches\".\"coach_sport_id\" " +
                     "AND public.\"Groups\".\"sport_id\" = public.\"Coaches\".\"coach_sport_id\"";

             DbFunctions db = new DbFunctions();

             try (Connection conn = db.connect_to_db("Sports school", "postgres", db.Return_pass());
                  Statement statement = conn.createStatement();
                  ResultSet resultSet = statement.executeQuery(query)) {

                 // Создаем экземпляр JsonExporter
                 JsonExporter exporter = new JsonExporter();

                 // Вызываем метод exportToJson, передавая результат SQL-запроса
                 exporter.exportToJson(resultSet);

                 // Перед тем, как выйти, закрываем соединение с базой данных
                 conn.close();
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     private void exportToJson(ResultSet resultSet) {
         try {
             // Создаем корневой JSON объект
             JSONObject timetableJson = new JSONObject();
             JSONObject timetableEntryJson = new JSONObject();

             // Создаем массив Sports внутри TimetableEntry
             JSONArray sportsArray = new JSONArray();

             while (resultSet.next()) {
                 String sportType = resultSet.getString("sport_name");
                 String groupName = resultSet.getString("group_name");
                 String coachName = resultSet.getString("coach_name");
                 String coachSurname = resultSet.getString("coach_surname");

                 // Проверяем, существует ли уже Sport с таким Type
                 JSONObject sportJson = findSportJson(sportsArray, sportType);

                 if (sportJson == null) {
                     // Если нет, создаем новый Sport
                     sportJson = new JSONObject();
                     sportJson.put("Type", sportType);

                     // Создаем массив Groups внутри Sport
                     JSONArray groupsArray = new JSONArray();
                     sportJson.put("Groups", groupsArray);

                     // Добавляем Sport в массив Sports
                     sportsArray.put(sportJson);
                 }

                 // Создаем объект Group
                 JSONObject groupJson = new JSONObject();
                 groupJson.put("Name", groupName);

                 // Создаем массив Coaches внутри Group
                 JSONArray coachesArray = new JSONArray();
                 JSONObject coachJson = new JSONObject();
                 coachJson.put("Name", coachName);
                 coachJson.put("Surname", coachSurname);
                 coachesArray.put(coachJson);

                 // Добавляем Group в массив Groups
                 groupJson.put("Coaches", coachesArray);

                 // Добавляем Group в массив Groups внутри Sport
                 sportJson.getJSONArray("Groups").put(groupJson);
             }

             // Добавляем массив Sports внутри TimetableEntry
             timetableEntryJson.put("Sports", sportsArray);

             // Добавляем TimetableEntry в корневой JSON объект
             timetableJson.put("TimetableEntry", timetableEntryJson);

             // Преобразуем JSON в строку
             String jsonString = timetableJson.toString(2);
             
          // Путь к файлу JSON
             String jsonFilePath = "C:\\Users\\User\\Desktop\\exported_data.json";

             // Записываем строку JSON в файл
             try (FileWriter jsonFile = new FileWriter(jsonFilePath)) {
                 jsonFile.write(jsonString);
                 System.out.println("Данные успешно выгружены в JSON файл.");
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     private JSONObject findSportJson(JSONArray sportsArray, String sportType) throws JSONException {
         for (int i = 0; i < sportsArray.length(); i++) {
             JSONObject sportJson = sportsArray.getJSONObject(i);
             if (sportJson.getString("Type").equals(sportType)) {
                 return sportJson;
             }
         }
         return null;
     }
	}