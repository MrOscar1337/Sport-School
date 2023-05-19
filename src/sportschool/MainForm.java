package sportschool;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MainForm extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {

		DbFunctions db = new DbFunctions();
		db.connect_to_db("Sports school", "postgres", db.Return_pass());

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
		setBounds(100, 100, 580, 450);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox<String> comboBox = new JComboBox<>(new String[] { "Группы", "Ученики", "Преподаватели" });
		comboBox.setToolTipText("Справочники");
		comboBox.setBounds(213, 1, 212, 44);
		contentPane.add(comboBox);

		comboBox.addActionListener(e -> {
			String selectedItem = (String) comboBox.getSelectedItem();
			if (selectedItem.equals("Ученики")) {
				Students frame1;
				try {
					frame1 = new Students();
					frame1.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (selectedItem.equals("Преподаватели")) {
				Coaches frame1;
				try {
					frame1 = new Coaches();
					frame1.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				Groups frame1;
				try {
					frame1 = new Groups();
					frame1.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JButton btnNewButton = new JButton("О программе");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Эта программа создана для автоматизации учебного процесса");
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(426, 0, 133, 46);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Расписание занятий");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Timetable frame1;
				try {
					frame1 = new Timetable();
					frame1.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.setBounds(0, 0, 212, 46);
		contentPane.add(btnNewButton_1);

		JLabel lblNewLabel = new JLabel("Новости школы");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(200, 69, 156, 23);
		contentPane.add(lblNewLabel);

		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setFont(new Font("Tahoma", Font.BOLD, 12));
		textArea.setText(
				"  Спортсмены из группы Ф-21 \r\n  приняли участие в региональном \r\n  турнире по футболу и заняли \r\n  почетное второе место. \r\n  Теперь они готовятся к \r\n  следующим соревнованиям, \r\n  чтобы побороться за первое место.");
		textArea.setBounds(24, 105, 240, 114);
		contentPane.add(textArea);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		textArea_1.setText(
				"  В нашей школе прошли \r\n  соревнования по легкой атлетике, \r\n  в которых приняли участие более \r\n  50 спортсменов из разных \r\n  регионов. Победители получили \r\n  ценные призы и дипломы.");
		textArea_1.setBackground(Color.WHITE);
		textArea_1.setBounds(297, 104, 245, 114);
		contentPane.add(textArea_1);

		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBackground(Color.WHITE);
		textArea_2.setText(
				"  Наши спортсмены приняли \r\n  участие в благотворительном \r\n  марафоне, собрав деньги \r\n  для помощи детскому дому. \r\n");
		textArea_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		textArea_2.setBounds(24, 256, 240, 84);
		contentPane.add(textArea_2);

		JTextArea textArea_3 = new JTextArea();
		textArea_3.setText(
				"  Мы стали партнером крупного\r\n  турнира по теннису, который \r\n  пройдет в следующем месяце. \r\n  Мы готовимся к этому \r\n  событию и приглашаем всех \r\n  желающих посмотреть наших \r\n  лучших спортсменов в действии.");
		textArea_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		textArea_3.setBounds(297, 260, 240, 114);
		contentPane.add(textArea_3);
	}
}
