package ui.reports;

import ui.dashboard.DashboardPage;

import javax.swing.*;
import java.awt.*;

public class ReportsPage extends JFrame {

    private CardLayout cardLayout;
    private JPanel centerPanel;

    public ReportsPage() {
        setTitle("Reports Panel");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Background Image
        Image backgroundImage = new ImageIcon("src/assets/img/D.jpg").getImage();

        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Right-side button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 30, 30, 30));

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);
        String[] buttons = {
                "Total Books",
                "Total Issued Books",
                "Total Returned Books",
                "Student-wise Issued Report",
                "Back to Dashboard"
        };

        for (String label : buttons) {
            JButton btn = new JButton(label);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFont(buttonFont);
            btn.setMaximumSize(new Dimension(250, 45));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setFocusPainted(false);
            buttonPanel.add(Box.createVerticalStrut(20));
            buttonPanel.add(btn);

            btn.addActionListener(e -> {
                switch (label) {
                    case "Total Books":
                        cardLayout.show(centerPanel, "totalBooks");
                        break;
                    case "Total Issued Books":
                        cardLayout.show(centerPanel, "issuedBooks");
                        break;
                    case "Total Returned Books":
                        cardLayout.show(centerPanel, "returnedBooks");
                        break;
                    case "Student-wise Issued Report":
                        cardLayout.show(centerPanel, "studentReport");
                        break;
                    case "Back to Dashboard":
                        dispose();
                        new DashboardPage();
                        break;
                }
            });
        }

        // Center panel with card layout
        centerPanel = new JPanel();
        cardLayout = new CardLayout();
        centerPanel.setLayout(cardLayout);
        centerPanel.setOpaque(false); // transparent background

        // Initially add empty panel
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        centerPanel.add(emptyPanel, "empty");
        cardLayout.show(centerPanel, "empty");

        // Report panels (all transparent)
        TotalBooksPanel totalBooksPanel = new TotalBooksPanel();
        totalBooksPanel.setOpaque(false);
        centerPanel.add(totalBooksPanel, "totalBooks");

        TotalIssuedPanel totalIssuedPanel = new TotalIssuedPanel();
        totalIssuedPanel.setOpaque(false);
        centerPanel.add(totalIssuedPanel, "issuedBooks");

        TotalReturnedPanel totalReturnedPanel = new TotalReturnedPanel();
        totalReturnedPanel.setOpaque(false);
        centerPanel.add(totalReturnedPanel, "returnedBooks");

        StudentWiseReportPanel studentReportPanel = new StudentWiseReportPanel();
        studentReportPanel.setOpaque(false);
        centerPanel.add(studentReportPanel, "studentReport");

        backgroundPanel.add(buttonPanel, BorderLayout.EAST);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(backgroundPanel);
        setVisible(true);
    }
}
