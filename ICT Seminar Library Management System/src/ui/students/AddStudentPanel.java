package ui.students;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddStudentPanel extends JPanel {

    public AddStudentPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Add New Student");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);

        Font labelFont = new Font("SansSerif", Font.BOLD, 20);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 18);

        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setFont(labelFont);
        rollLabel.setForeground(Color.WHITE);
        JTextField rollField = new JTextField(20);
        rollField.setFont(fieldFont);

        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(Color.WHITE);
        JTextField nameField = new JTextField(22);
        nameField.setFont(fieldFont);

        JLabel deptLabel = new JLabel("Department:");
        deptLabel.setFont(labelFont);
        deptLabel.setForeground(Color.WHITE);
        JTextField deptField = new JTextField(20);
        deptField.setFont(fieldFont);

        JButton addButton = new JButton("Add Student");
        addButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roll = rollField.getText().trim();
                String name = nameField.getText().trim();
                String dept = deptField.getText().trim();

                if (roll.isEmpty() || name.isEmpty() || dept.isEmpty()) {
                    JOptionPane.showMessageDialog(AddStudentPanel.this,
                            "Please fill all fields.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "INSERT INTO students (roll, name, department) VALUES (?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, roll);
                    stmt.setString(2, name);
                    stmt.setString(3, dept);

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(AddStudentPanel.this,
                                "Student added successfully!");
                        rollField.setText("");
                        nameField.setText("");
                        deptField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(AddStudentPanel.this,
                                "Failed to add student.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddStudentPanel.this,
                            "Database error: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Layout components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        add(rollLabel, gbc);
        gbc.gridx = 1;
        add(rollField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(deptLabel, gbc);
        gbc.gridx = 1;
        add(deptField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(addButton, gbc);
    }
}
