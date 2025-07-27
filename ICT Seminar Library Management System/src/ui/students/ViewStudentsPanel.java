package ui.students;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewStudentsPanel extends JPanel {

    private JTable studentTable;
    private DefaultTableModel tableModel;

    public ViewStudentsPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel titleLabel = new JLabel("View All Students", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Table columns
        String[] columns = {"ID", "Roll", "Name", "Department"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        loadStudents();
    }

    private void loadStudents() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students ORDER BY id ASC")) {

            tableModel.setRowCount(0);  // Clear existing rows

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("roll"),
                        rs.getString("name"),
                        rs.getString("department")
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading students data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
