package ui.reports;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class TotalIssuedPanel extends JPanel {
    public TotalIssuedPanel() {
        setLayout(new BorderLayout(10, 10));
        setOpaque(false);

        JLabel label = new JLabel("Total Issued Books", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 28));
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.NORTH);

        String[] cols = {"ID", "Book ID", "Student Roll", "Issue Date"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/seminar_library", "root", "nusrat");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM issued_books")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getString("student_roll"),
                        rs.getDate("issue_date")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JTable table = new JTable(model);
        styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
        table.getTableHeader().setBackground(new Color(60, 60, 60));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(180, 220, 240));
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer(center);
    }
}
