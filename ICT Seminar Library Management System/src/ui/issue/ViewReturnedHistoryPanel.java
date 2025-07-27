package ui.issue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewReturnedHistoryPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public ViewReturnedHistoryPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel title = new JLabel("Returned History", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        model = new DefaultTableModel(new String[]{"Student Roll", "Book ID", "Issue Date", "Return Date"}, 0);
        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);

        add(title, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        loadReturnedData(); // Call DB loader method
    }

    private void loadReturnedData() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/seminar_library", "root", "nusrat")) {

            String query = "SELECT student_roll, book_id, issue_date, return_date FROM returned_books ORDER BY return_date DESC";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String roll = rs.getString("student_roll");
                String bookId = rs.getString("book_id");
                Timestamp issueDate = rs.getTimestamp("issue_date");
                Timestamp returnDate = rs.getTimestamp("return_date");

                model.addRow(new Object[]{roll, bookId, issueDate.toString(), returnDate.toString()});
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load returned history.\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
