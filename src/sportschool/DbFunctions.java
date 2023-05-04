package sportschool;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DbFunctions {
public Connection connect_to_db (String dbname, String user, String password) {
	Connection conn = null;
	try {
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, password);
		
		
	}catch(Exception e) {
		System.out.println(e);
	}
	return conn;
}

public String Return_pass () throws Exception {
    String key = "mysecretkey12345"; // Ключ шифрования (16 символов для AES-128, 24 символа для AES-192, 32 символа для AES-256)
    
    // Шифрование
    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");

    // Расшифровка
    byte[] encryptedFromFile = Files.readAllBytes(Paths.get("encrypted.txt"));
    Cipher cipher2 = Cipher.getInstance("AES");
    cipher2.init(Cipher.DECRYPT_MODE, aesKey);
    byte[] decrypted = cipher2.doFinal(encryptedFromFile);
    
    // Сохранение расшифрованного текста в переменную pass
    String pass = new String(decrypted);
    return pass;
}

public ResultSet execute_query(String query) throws Exception {
    Statement statement = null;
    ResultSet resultSet = null;
    try {
        Connection conn = connect_to_db("Sports school", "postgres", Return_pass());
        statement = conn.createStatement();
        resultSet = statement.executeQuery(query);
    } catch (Exception e) {
        throw e;
    }
    return resultSet;
}
}



