package ui.issue;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class IssueBookPanel extends JPanel {

    public IssueBookPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel titleLabel = new JLabel("Issue Book to Student");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);

        JLabel rollLabel = new JLabel("Student Roll:");
        JTextField rollField = new JTextField(20);
        JLabel bookIdLabel = new JLabel("Book ID:");
        JTextField bookIdField = new JTextField(20);
        JButton issueButton = new JButton("Issue Book");

        Font labelFont = new Font("SansSerif", Font.BOLD, 18);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 16);

        rollLabel.setFont(labelFont);
        rollLabel.setForeground(Color.WHITE);
        rollField.setFont(fieldFont);
        bookIdLabel.setFont(labelFont);
        bookIdLabel.setForeground(Color.WHITE);
        bookIdField.setFont(fieldFont);
        issueButton.setFont(labelFont);
        issueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST; gbc.gridy++;
        gbc.gridx = 0;
        add(rollLabel, gbc);
        gbc.gridx = 1;
        add(rollField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(bookIdLabel, gbc);
        gbc.gridx = 1;
        add(bookIdField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(issueButton, gbc);

        // Action Listener with improved DB logic
        issueButton.addActionListener(e -> {
            String roll = rollField.getText().trim();
            String bookId = bookIdField.getText().trim();

            if (roll.isEmpty() || bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/seminar_library", "root", "nusrat")) {

                // Step 1: Check if book exists and quantity > 0
                String checkQuery = "SELECT quantity FROM books WHERE id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                checkStmt.setString(1, bookId);
                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Book ID does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int quantity = rs.getInt("quantity");
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Book not available (Out of stock).", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Step 2: Issue book to student
                String issueQuery = "INSERT INTO issued_books (student_roll, book_id) VALUES (?, ?)";
                PreparedStatement issueStmt = conn.prepareStatement(issueQuery);
                issueStmt.setString(1, roll);
                issueStmt.setString(2, bookId);
                int rows = issueStmt.executeUpdate();

                if (rows > 0) {
                    // Step 3: Update quantity in books table
                    String updateQtyQuery = "UPDATE books SET quantity = quantity - 1 WHERE id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQtyQuery);
                    updateStmt.setString(1, bookId);
                    updateStmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Book issued successfully!");
                    rollField.setText("");
                    bookIdField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to issue book.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
