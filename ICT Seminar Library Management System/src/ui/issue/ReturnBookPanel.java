package ui.issue;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;

public class ReturnBookPanel extends JPanel {

    public ReturnBookPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel titleLabel = new JLabel("Return Book");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);

        JLabel rollLabel = new JLabel("Student Roll:");
        JTextField rollField = new JTextField(20);
        JLabel bookIdLabel = new JLabel("Book ID:");
        JTextField bookIdField = new JTextField(20);
        JButton returnButton = new JButton("Return Book");

        Font labelFont = new Font("SansSerif", Font.BOLD, 18);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 16);

        rollLabel.setFont(labelFont);
        rollLabel.setForeground(Color.WHITE);
        rollField.setFont(fieldFont);
        bookIdLabel.setFont(labelFont);
        bookIdLabel.setForeground(Color.WHITE);
        bookIdField.setFont(fieldFont);
        returnButton.setFont(labelFont);
        returnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST; gbc.gridy++;
        add(rollLabel, gbc);
        gbc.gridx = 1;
        add(rollField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(bookIdLabel, gbc);
        gbc.gridx = 1;
        add(bookIdField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(returnButton, gbc);

        returnButton.addActionListener(e -> {
            String roll = rollField.getText().trim();
            String bookId = bookIdField.getText().trim();

            if (roll.isEmpty() || bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/seminar_library", "root", "nusrat")) {

                // Step 1: Check if this book was issued to this student
                String selectQuery = "SELECT * FROM issued_books WHERE student_roll = ? AND book_id = ?";
                PreparedStatement psSelect = conn.prepareStatement(selectQuery);
                psSelect.setString(1, roll);
                psSelect.setString(2, bookId);
                ResultSet rs = psSelect.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "No matching issued book found!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Timestamp issueDate = rs.getTimestamp("issue_date");

                // Step 2: Insert into returned_books
                String insertQuery = "INSERT INTO returned_books (student_roll, book_id, issue_date, return_date) VALUES (?, ?, ?, ?)";
                PreparedStatement psInsert = conn.prepareStatement(insertQuery);
                psInsert.setString(1, roll);
                psInsert.setString(2, bookId);
                psInsert.setTimestamp(3, issueDate);
                psInsert.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                psInsert.executeUpdate();

                // Step 3: Delete from issued_books
                String deleteQuery = "DELETE FROM issued_books WHERE student_roll = ? AND book_id = ?";
                PreparedStatement psDelete = conn.prepareStatement(deleteQuery);
                psDelete.setString(1, roll);
                psDelete.setString(2, bookId);
                psDelete.executeUpdate();

                // ✅ Step 4: Update book quantity (increment by 1)
                String updateQtyQuery = "UPDATE books SET quantity = quantity + 1 WHERE id = ?";
                PreparedStatement psUpdateQty = conn.prepareStatement(updateQtyQuery);
                psUpdateQty.setString(1, bookId);
                psUpdateQty.executeUpdate();

                // ✅ Done
                JOptionPane.showMessageDialog(this, "Book returned successfully!");
                rollField.setText("");
                bookIdField.setText("");

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
