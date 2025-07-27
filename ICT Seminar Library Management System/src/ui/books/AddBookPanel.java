package ui.books;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddBookPanel extends JPanel {

    public AddBookPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // Title Label
        JLabel titleLabel = new JLabel("Add New Book");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);

        // Common font for labels and fields
        Font labelFont = new Font("SansSerif", Font.BOLD, 20);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 18);

        // Form Labels and Fields
        JLabel nameLabel = new JLabel("Book Title:");
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(Color.WHITE);
        JTextField nameField = new JTextField(22);
        nameField.setFont(fieldFont);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setFont(labelFont);
        authorLabel.setForeground(Color.WHITE);
        JTextField authorField = new JTextField(22);
        authorField.setFont(fieldFont);

        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setFont(labelFont);
        subjectLabel.setForeground(Color.WHITE);
        JTextField subjectField = new JTextField(22);
        subjectField.setFont(fieldFont);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(labelFont);
        quantityLabel.setForeground(Color.WHITE);
        JTextField quantityField = new JTextField(10);
        quantityField.setFont(fieldFont);

        JButton addButton = new JButton("Add Book");
        addButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ✅ Add Button Action
        addButton.addActionListener((ActionEvent e) -> {
            String title = nameField.getText().trim();
            String author = authorField.getText().trim();
            String subject = subjectField.getText().trim();
            String quantityText = quantityField.getText().trim();

            if (title.isEmpty() || author.isEmpty() || subject.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);

                Connection conn = DBConnection.getConnection();
                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String sql = "INSERT INTO books (title, author, subject, quantity) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.setString(3, subject);
                stmt.setInt(4, quantity);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                    authorField.setText("");
                    subjectField.setText("");
                    quantityField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add book.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                conn.close();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while adding the book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add components with layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(authorLabel, gbc);
        gbc.gridx = 1;
        add(authorField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(subjectLabel, gbc);
        gbc.gridx = 1;
        add(subjectField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(quantityLabel, gbc);
        gbc.gridx = 1;
        add(quantityField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(addButton, gbc);
    }
}
