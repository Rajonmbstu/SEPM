package ui.students;

import ui.dashboard.DashboardPage;

import javax.swing.*;
import java.awt.*;

public class

StudentManagementPage extends JFrame {

    private CardLayout cardLayout;
    private JPanel rightPanel;

    public StudentManagementPage() {
        setTitle("Student / User Management");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Load background image
        Image backgroundImage = new ImageIcon("src/assets/img/Yellow.jpg").getImage();

        // Background panel with image
        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Button panel (Right side)
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 30, 100, 30));

        String[] buttons = {
                "Add New Student",
                "View All Students",
                "Search Student",
                "⬅ Back to Dashboard"
        };

        Font buttonFont = new Font("SansSerif", Font.BOLD, 20);

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(buttonFont);
            button.setBackground(new Color(30, 30, 30));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setOpaque(true);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setIconTextGap(10);

            // Hover effect
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(60, 60, 60));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(30, 30, 30));
                }
            });

            // Action listeners
            button.addActionListener(e -> {
                switch (text) {
                    case "Add New Student":
                        cardLayout.show(rightPanel, "add");
                        break;
                    case "View All Students":
                        cardLayout.show(rightPanel, "view");
                        break;
                    case "Search Student":
                        cardLayout.show(rightPanel, "search");
                        break;
                    case "⬅ Back to Dashboard":
                        dispose();
                        new DashboardPage(); // back to main dashboard
                        break;
                }
            });

            buttonPanel.add(button);
        }

        // Main content panel (center-left)
        rightPanel = new JPanel();
        cardLayout = new CardLayout();
        rightPanel.setLayout(cardLayout);
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(80, 150, 80, 300)); // adjust for middle-left

        rightPanel.add(new AddStudentPanel(), "add");
        rightPanel.add(new ViewStudentsPanel(), "view");
        rightPanel.add(new SearchStudentPanel(), "search");

        backgroundPanel.add(buttonPanel, BorderLayout.EAST);      // Buttons to right
        backgroundPanel.add(rightPanel, BorderLayout.CENTER);     // Panels in center-left

        setContentPane(backgroundPanel);
        setVisible(true);
    }
}