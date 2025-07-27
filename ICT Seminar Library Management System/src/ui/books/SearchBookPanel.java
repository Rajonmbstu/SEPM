package ui.books;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchBookPanel extends JPanel {

    private JTextField searchField;
    private JTable resultsTable;
    private DefaultTableModel tableModel;

    public SearchBookPanel() {
        setLayout(new BorderLayout(0, 10)); // উপরে ও নীচে একটু গ্যাপ দিবো
        setOpaque(false);

        // Heading + Search Panel একসাথে রাখতে একটি panel ব্যবহার করবো
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); // উপরে-বামে-নিচে-ডানে প্যাডিং

        JLabel headingLabel = new JLabel("Search Book by Title", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Serif", Font.BOLD, 32));
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally in BoxLayout

        // Search bar panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Enter Book Title:");
        searchLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        searchLabel.setForeground(Color.WHITE);

        searchField = new JTextField(25);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchButton.addActionListener(e -> searchBooks());

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // topPanel এ heading এবং searchPanel যোগ করা
        topPanel.add(headingLabel);
        topPanel.add(searchPanel);

        add(topPanel, BorderLayout.NORTH);

        // Table for results
        String[] columns = {"Book Title", "Author", "Subject", "Quantity"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        resultsTable = new JTable(tableModel);
        resultsTable.setFont(new Font("SansSerif", Font.PLAIN, 16));
        resultsTable.setRowHeight(24);
        resultsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
        resultsTable.getTableHeader().setBackground(Color.DARK_GRAY);
        resultsTable.getTableHeader().setForeground(Color.WHITE);
        resultsTable.setBackground(new Color(30, 30, 30));
        resultsTable.setForeground(Color.WHITE);
        resultsTable.setGridColor(Color.GRAY);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // টেবিলের চারপাশে সামান্য স্পেস

        add(scrollPane, BorderLayout.CENTER);
    }

    private void searchBooks() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a book title to search", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM books WHERE title LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + searchTerm + "%");

            ResultSet rs = pstmt.executeQuery();

            tableModel.setRowCount(0); // clear previous results

            boolean found = false;
            while (rs.next()) {
                found = true;
                String title = rs.getString("title");
                String author = rs.getString("author");
                String subject = rs.getString("subject");
                int quantity = rs.getInt("quantity");
                tableModel.addRow(new Object[]{title, author, subject, quantity});
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No books found matching \"" + searchTerm + "\"", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching books", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
