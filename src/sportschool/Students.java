package sportschool;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Students extends JFrame {

	private JPanel contentPane;
	private JTable table;

	public Students() throws Exception {
		DbFunctions db = new DbFunctions();
		db.connect_to_db("Sports school", "postgres", db.Return_pass());
		setTitle("Ученики");
		setBounds(100, 100, 575, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Назад");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(10, 11, 125, 44);
		contentPane.add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 66, 539, 334);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		// Получение данных из таблицы Students
		String query = "SELECT CONCAT_WS(' ', student_surname, student_name, student_patronymic) AS \"ФИО\", student_birth AS \"Дата рождения\", student_contact AS \"Телефон\" FROM public.\"Students\"";
		ResultSet rs = db.execute_query(query);

		// Получение метаданных (названия столбцов)
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		String[] columnNames = new String[columnCount];
		for (int i = 1; i <= columnCount; i++) {
			columnNames[i - 1] = rsmd.getColumnName(i);
		}

		// Получение данных (значения ячеек)
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		while (rs.next()) {
			Object[] row = new Object[columnCount];
			for (int i = 1; i <= columnCount; i++) {
				row[i - 1] = rs.getObject(i);
			}
			model.addRow(row);
		}

		// Отображение данных в таблице
		table.setModel(model);

		JButton btnNewButton_1 = new JButton("Удалить");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.setBounds(424, 12, 125, 44);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Добавить");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnterStudents frame1;
				try {
					frame1 = new EnterStudents();
					frame1.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_2.setBounds(289, 11, 125, 44);
		contentPane.add(btnNewButton_2);

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deleteSelectedRow();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			private void deleteSelectedRow() throws Exception {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					String fio = (String) model.getValueAt(selectedRow, 0);
					String birthDate = (String) model.getValueAt(selectedRow, 1);
					String contact = (String) model.getValueAt(selectedRow, 2);

					try {
						// Prepare the delete statement
						String query = "DELETE FROM public.\"Students\" WHERE CONCAT_WS(' ', student_surname, student_name, student_patronymic) AS \"ФИО\", student_birth AS \"Дата рождения\", student_contact AS \"Телефон\" FROM public.\"Students\"";
						PreparedStatement statement = db.connect_to_db("Sports school", "postgres", db.Return_pass())
								.prepareStatement(query);
						statement.setString(1, fio);
						statement.setString(2, birthDate);
						statement.setString(3, contact);

						// Execute the delete statement
						int affectedRows = statement.executeUpdate();
						if (affectedRows > 0) {
							// Remove the row from the table
							model.removeRow(selectedRow);
							JOptionPane.showMessageDialog(null, "Запись удалена.");
						} else {
							JOptionPane.showMessageDialog(null, "Не удалось удалить запись.");
						}

						statement.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "Ошибка при удалении записи.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Выберите запись для удаления.");
				}
			}
		});
	}
}
