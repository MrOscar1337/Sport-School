package sportschool;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class MainForm extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
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
          
		DbFunctions db = new DbFunctions();
		db.connect_to_db("Sports school", "postgres", pass);
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainForm() {
		setTitle("Sport School");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 575, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Главная страница");
		lblNewLabel.setBackground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 21));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(200, 11, 179, 57);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Расписание");
		btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton_1.setBounds(50, 180, 145, 51);
		contentPane.add(btnNewButton_1);
		
		JButton button = new JButton("Преподаватели");
		button.setFont(new Font("Times New Roman", Font.BOLD, 14));
		button.setBounds(362, 82, 145, 50);
		contentPane.add(button);
		
		JButton btnNewButton_2 = new JButton("Дисциплины");
		btnNewButton_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton_2.setBounds(362, 180, 145, 51);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton = new JButton("Группы");
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(50, 82, 145, 50);
		contentPane.add(btnNewButton);
	}
}
