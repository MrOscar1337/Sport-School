package sportschool;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;


public class Timetable extends JFrame {

	private JPanel contentPane;
	private JTable table_1;

	public Timetable() throws Exception {
		DbFunctions db = new DbFunctions();
		db.connect_to_db("Sports school", "postgres", db.Return_pass());

		setTitle("Расписание занятий");
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
		btnNewButton.setBounds(10, 13, 73, 44);
		contentPane.add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 539, 332);
		contentPane.add(scrollPane);

		table_1 = new JTable();
		scrollPane.setViewportView(table_1);

		// Получение данных из таблицы Students
		String query = "SELECT group_name AS\"Группа\", coach_surname || ' ' || coach_name AS \"Преподаватель\", gym_number AS \"Зал\", start_datetime AS \"Начало\", "
				+ "end_datetime AS \"Окончание\" FROM public.\"Timetable\", public.\"Groups\", public.\"Coaches\", public.\"Gym\" "
				+ "WHERE public.\"Groups\".\"sport_id\" = public.\"Coaches\".\"coach_sport_id\"";
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
		table_1.setModel(model);

		JButton btnNewButton_1 = new JButton("Добавить");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnterTimetable frame1;
				try {
					frame1 = new EnterTimetable();
					frame1.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.setBounds(349, 13, 95, 44);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Удалить");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_2.setBounds(454, 13, 95, 44);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_export_csv = new JButton("Экспорт .csv");
		btnNewButton_export_csv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DbFunctions db = new DbFunctions();
					db.connect_to_db("Sports school", "postgres", db.Return_pass());
					Statement statement = null;
			        ResultSet resultSet = null;
		           

		            // Выполнение SQL-запроса для получения данных
		            String query = "SELECT group_name, coach_surname, coach_name, sport_name " +
		                    "FROM public.\"Groups\", public.\"Coaches\", public.\"Sports\" " +
		                    "WHERE public.\"Sports\".\"sport_id\" = public.\"Coaches\".\"coach_sport_id\" " +
		                    "AND public.\"Groups\".\"sport_id\" = public.\"Coaches\".\"coach_sport_id\"";
		            statement = db.connect_to_db("Sports school", "postgres", db.Return_pass()).createStatement();
		            resultSet = statement.executeQuery(query);

		            // Создание CSV-файла для записи данных
		            FileWriter csvFile = new FileWriter("C:\\Users\\User\\Desktop\\exported_data.csv");

		            int columnCount = resultSet.getMetaData().getColumnCount();
		            
		            // Запись данных из результирующего набора в CSV
		            while (resultSet.next()) {
		                for (int i = 1; i <= columnCount; i++) {
		                    String columnValue = resultSet.getString(i);
		                    csvFile.append(columnValue);
		                    if (i < columnCount) {
		                        csvFile.append(",");
		                    } else {
		                        csvFile.append("\n");
		                    }
		                }
		            }

		            // Закрытие CSV-файла и ресурсов базы данных
		            csvFile.close();
		            resultSet.close();
		            statement.close();
		           

		            System.out.println("Данные успешно выгружены в CSV файл.");
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
			}
		});
		btnNewButton_export_csv.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_export_csv.setBounds(93, 13, 117, 44);
		contentPane.add(btnNewButton_export_csv);
		
		JButton btnNewButton_export_txt = new JButton("Экспорт .txt");
		btnNewButton_export_txt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            DbFunctions db = new DbFunctions();
		            db.connect_to_db("Sports school", "postgres", db.Return_pass());
		            Statement statement = null;
		            ResultSet resultSet = null;

		            // Выполнение SQL-запроса для получения данных
		            String query = "SELECT group_name, coach_surname, coach_name, sport_name " +
		                    "FROM public.\"Groups\", public.\"Coaches\", public.\"Sports\" " +
		                    "WHERE public.\"Sports\".\"sport_id\" = public.\"Coaches\".\"coach_sport_id\" " +
		                    "AND public.\"Groups\".\"sport_id\" = public.\"Coaches\".\"coach_sport_id\"";
		            statement = db.connect_to_db("Sports school", "postgres", db.Return_pass()).createStatement();
		            resultSet = statement.executeQuery(query);

		            // Создание TXT-файла для записи данных
		            FileWriter txtFile = new FileWriter("C:\\Users\\User\\Desktop\\exported_data.txt");

		            int columnCount = resultSet.getMetaData().getColumnCount();

		            // Запись данных из результирующего набора в TXT
		            while (resultSet.next()) {
		                for (int i = 1; i <= columnCount; i++) {
		                    String columnValue = resultSet.getString(i);
		                    txtFile.append(columnValue);
		                    if (i < columnCount) {
		                        txtFile.append("|");
		                    } else {
		                        txtFile.append("\n");
		                    }
		                }
		            }

		            // Закрытие TXT-файла и ресурсов базы данных
		            txtFile.close();
		            resultSet.close();
		            statement.close();

		            System.out.println("Данные успешно выгружены в TXT файл.");
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
			}
		});
		btnNewButton_export_txt.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_export_txt.setBounds(220, 13, 119, 44);
		contentPane.add(btnNewButton_export_txt);

		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_1.getSelectedRow();
				if (selectedRow != -1) {
					DefaultTableModel model = (DefaultTableModel) table_1.getModel();
					String group = model.getValueAt(selectedRow, 0).toString();
					String coach = model.getValueAt(selectedRow, 1).toString();
					String gym = model.getValueAt(selectedRow, 2).toString();
					String start = model.getValueAt(selectedRow, 3).toString();
					String end = model.getValueAt(selectedRow, 4).toString();
					deleteTimetableEntry(group, coach, gym, start, end);
					model.removeRow(selectedRow);
				}
			}
		});
	}

	private void deleteTimetableEntry(String group, String coach, String gym, String start, String end) {
		try {
			DbFunctions db = new DbFunctions();
			db.connect_to_db("Sports school", "postgres", db.Return_pass());
			String query = "DELETE FROM public.\"Timetable\" WHERE group_name = ? AND coach_surname = ? AND gym_number = ? AND start_datetime = ? AND end_datetime = ?";
			PreparedStatement statement = db.connect_to_db("Sports school", "postgres", db.Return_pass())
					.prepareStatement(query);
			statement.setString(1, group);
			statement.setString(2, coach);
			statement.setString(3, gym);
			statement.setString(4, start);
			statement.setString(5, end);
			statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

