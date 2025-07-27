package ui.reports;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class TotalBooksPanel extends JPanel {
    public TotalBooksPanel() {
        setLayout(new BorderLayout(10, 10));
        setOpaque(false);

        JLabel label = new JLabel("Total Books Available", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 28));
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.NORTH);

        String[] cols = {"ID", "Title", "Author", "Subject", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/seminar_library", "root", "nusrat");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("subject"),
                        rs.getInt("quantity")
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