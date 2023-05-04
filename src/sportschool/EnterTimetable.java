package sportschool;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EnterTimetable extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblNewLabel_1;
	private JTextField textField_2;
	private JLabel lblNewLabel_2;
	private JTextField textField_3;
	private JLabel lblNewLabel_3;
	private JTextField textField_4;
	private JLabel lblNewLabel_4;
	private JButton btnNewButton;
	/**
	 * Create the frame.
	 */
	public EnterTimetable() {
		setTitle("Ввод занятия");
		setBounds(100, 100, 360, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setText("");
		textField.setBounds(152, 36, 170, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Группа");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(27, 35, 60, 19);
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(152, 67, 170, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Преподаватель");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(27, 66, 115, 19);
		contentPane.add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(152, 98, 170, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		lblNewLabel_2 = new JLabel("Зал");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(27, 97, 46, 19);
		contentPane.add(lblNewLabel_2);
		
		textField_3 = new JTextField();
		textField_3.setBounds(152, 129, 170, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Начало");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(27, 128, 60, 19);
		contentPane.add(lblNewLabel_3);
		
		textField_4 = new JTextField();
		textField_4.setBounds(152, 160, 170, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		lblNewLabel_4 = new JLabel("Окончание");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(27, 158, 94, 19);
		contentPane.add(lblNewLabel_4);
		
		btnNewButton = new JButton("Ок");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(152, 202, 89, 23);
		contentPane.add(btnNewButton);
	}
}
