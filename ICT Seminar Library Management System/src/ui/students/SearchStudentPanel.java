package ui.students;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchStudentPanel extends JPanel {

    private JTextField searchField;
    private JButton searchButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public SearchStudentPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel titleLabel = new JLabel("Search Student (by Roll or Name)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Container panel for search and table
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Search panel with bigger text field
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        searchField = new JTextField(30);  // increased width
        searchButton = new JButton("Search");
        topPanel.add(searchField);
        topPanel.add(searchButton);

        mainPanel.add(topPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // vertical gap

        // Table setup
        String[] columns = {"ID", "Roll", "Name", "Department"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setPreferredSize(new Dimension(700, 400)); // optional size limit

        mainPanel.add(scrollPane);

        add(mainPanel, BorderLayout.CENTER);

        searchButton.addActionListener(e -> performSearch());
    }

    private void performSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Roll or Name to search.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM students WHERE roll LIKE ? OR name LIKE ? ORDER BY id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String likeKeyword = "%" + keyword + "%";
            pstmt.setString(1, likeKeyword);
            pstmt.setString(2, likeKeyword);

            ResultSet rs = pstmt.executeQuery();

            tableModel.setRowCount(0); // Clear table

            boolean found = false;
            while (rs.next()) {
                found = true;
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("roll"),
                        rs.getString("name"),
                        rs.getString("department")
                };
                tableModel.addRow(row);
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No matching students found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching students.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
