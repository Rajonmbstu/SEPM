package ui.issue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewIssuedBooksPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public ViewIssuedBooksPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel title = new JLabel("View Issued Books", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        model = new DefaultTableModel(new String[]{"Student Roll", "Book ID", "Issue Date"}, 0);
        table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(24);

        JScrollPane scroll = new JScrollPane(table);

        add(title, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        loadIssuedBooksFromDatabase();
    }

    private void loadIssuedBooksFromDatabase() {
        model.setRowCount(0); // Clear existing data before loading new

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/seminar_library", "root", "nusrat");
             Statement stmt = conn.createStatement()) {

            String query = "SELECT student_roll, book_id, issue_date FROM issued_books ORDER BY issue_date DESC";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String roll = rs.getString("student_roll");
                String bookId = rs.getString("book_id");
                Timestamp issueDate = rs.getTimestamp("issue_date");

                model.addRow(new Object[]{
                        roll,
                        bookId,
                        issueDate != null ? issueDate.toString() : "N/A"
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load issued books!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
