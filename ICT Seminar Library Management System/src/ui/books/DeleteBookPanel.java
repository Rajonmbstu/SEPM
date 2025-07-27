package ui.books;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteBookPanel extends JPanel {

    private JTextField titleField;

    public DeleteBookPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel headingLabel = new JLabel("Delete Book");
        headingLabel.setFont(new Font("Serif", Font.BOLD, 32));
        headingLabel.setForeground(Color.WHITE);

        Font labelFont = new Font("SansSerif", Font.BOLD, 20);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 18);

        JLabel titleLabel = new JLabel("Book Title to Delete:");
        titleLabel.setFont(labelFont);
        titleLabel.setForeground(Color.WHITE);

        titleField = new JTextField(22);
        titleField.setFont(fieldFont);

        JButton deleteButton = new JButton("Delete Book");
        deleteButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText().trim();

                if (title.isEmpty()) {
                    JOptionPane.showMessageDialog(DeleteBookPanel.this, "Please enter a book title", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(DeleteBookPanel.this,
                        "Are you sure you want to delete the book \"" + title + "\"?", "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    deleteBook(title);
                }
            }
        });

        // Add components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(headingLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        add(titleLabel, gbc);

        gbc.gridx = 1;
        add(titleField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(deleteButton, gbc);
    }

    private void deleteBook(String title) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM books WHERE title = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Book deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                titleField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Book not found!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting book", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
