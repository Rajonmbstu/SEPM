package ui.books;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateBookPanel extends JPanel {

    private JTextField titleField, authorField, subjectField, quantityField;

    public UpdateBookPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel headingLabel = new JLabel("Update Book Details");
        headingLabel.setFont(new Font("Serif", Font.BOLD, 32));
        headingLabel.setForeground(Color.WHITE);

        Font labelFont = new Font("SansSerif", Font.BOLD, 20);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 18);

        // Fields
        JLabel titleLabel = new JLabel("Book Title (Search):");
        titleLabel.setFont(labelFont);
        titleLabel.setForeground(Color.WHITE);
        titleField = new JTextField(22);
        titleField.setFont(fieldFont);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setFont(labelFont);
        authorLabel.setForeground(Color.WHITE);
        authorField = new JTextField(22);
        authorField.setFont(fieldFont);

        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setFont(labelFont);
        subjectLabel.setForeground(Color.WHITE);
        subjectField = new JTextField(22);
        subjectField.setFont(fieldFont);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(labelFont);
        quantityLabel.setForeground(Color.WHITE);
        quantityField = new JTextField(10);
        quantityField.setFont(fieldFont);

        JButton updateButton = new JButton("Update Book");
        updateButton.setFont(new Font("SansSerif", Font.BOLD, 20));

        // Action for Search Button
        searchButton.addActionListener((ActionEvent e) -> {
            String title = titleField.getText().trim();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Book Title to search.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Connection conn = DBConnection.getConnection();
                String sql = "SELECT * FROM books WHERE title = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, title);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    authorField.setText(rs.getString("author"));
                    subjectField.setText(rs.getString("subject"));
                    quantityField.setText(String.valueOf(rs.getInt("quantity")));
                } else {
                    JOptionPane.showMessageDialog(this, "Book not found.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }

                conn.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while searching book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action for Update Button
        updateButton.addActionListener((ActionEvent e) -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String subject = subjectField.getText().trim();
            String qtyText = quantityField.getText().trim();

            if (title.isEmpty() || author.isEmpty() || subject.isEmpty() || qtyText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields before updating.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int quantity = Integer.parseInt(qtyText);
                Connection conn = DBConnection.getConnection();
                String sql = "UPDATE books SET author=?, subject=?, quantity=? WHERE title=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, author);
                stmt.setString(2, subject);
                stmt.setInt(3, quantity);
                stmt.setString(4, title);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Book updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed. Book may not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                conn.close();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while updating book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(headingLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        add(titleLabel, gbc);
        gbc.gridx = 1;
        add(titleField, gbc);

        gbc.gridy++;
        gbc.gridx = 1;
        add(searchButton, gbc);

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
        add(updateButton, gbc);
    }
}
