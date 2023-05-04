package sportschool;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Groups extends JFrame {

	private JPanel contentPane;
	private JTable table;

	public Groups() throws Exception {
		DbFunctions db = new DbFunctions();
		db.connect_to_db("Sports school", "postgres", db.Return_pass());
		setTitle("Группы");
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
				String query = "SELECT group_name AS \"Группа\" FROM public.\"Groups\"";
			    ResultSet rs = db.execute_query(query);

				// Получение метаданных (названия столбцов)
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				String[] columnNames = new String[columnCount];
				for (int i = 1; i <= columnCount; i++) {
				    columnNames[i-1] = rsmd.getColumnName(i);
				}

				// Получение данных (значения ячеек)
				DefaultTableModel model = new DefaultTableModel();
				model.setColumnIdentifiers(columnNames);
				while (rs.next()) {
				    Object[] row = new Object[columnCount];
				    for (int i = 1; i <= columnCount; i++) {
				        row[i-1] = rs.getObject(i);
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
				EnterGroups frame1;
				try {
					frame1 = new EnterGroups();
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
	}

}
