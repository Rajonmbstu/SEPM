package ui.issue;

import ui.books.ViewBooksPanel;
import ui.dashboard.DashboardPage;

import javax.swing.*;
import java.awt.*;

public class IssueReturnManagementPage extends JFrame {

    private CardLayout cardLayout;
    private JPanel rightPanel;

    public IssueReturnManagementPage() {
        setTitle("Issue / Return Management");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Background image
        Image backgroundImage = new ImageIcon("src/assets/img/Orange.jpg").getImage();

        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Right side buttons
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setOpaque(false);
        rightButtonPanel.setLayout(new BoxLayout(rightButtonPanel, BoxLayout.Y_AXIS));
        rightButtonPanel.setBorder(BorderFactory.createEmptyBorder(100, 30, 30, 30));

        String[] buttons = {
                "Issue Book to Student",
                "Return Book",
                "View Issued Books",
                "View Returned History",
                "View All Books",
                "Back to Dashboard"
        };

        Font buttonFont = new Font("SansSerif", Font.BOLD, 20);

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setFont(buttonFont);
            button.setMaximumSize(new Dimension(280, 50));
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            rightButtonPanel.add(Box.createVerticalStrut(20));
            rightButtonPanel.add(button);

            button.addActionListener(e -> {
                switch (text) {
                    case "Issue Book to Student":
                        cardLayout.show(rightPanel, "issue");
                        break;
                    case "Return Book":
                        cardLayout.show(rightPanel, "return");
                        break;
                    case "View Issued Books":
                        cardLayout.show(rightPanel, "issued");
                        break;
                    case "View Returned History":
                        cardLayout.show(rightPanel, "returned");
                        break;
                    case "View All Books":
                        cardLayout.show(rightPanel, "viewBooks");
                        break;
                    case "Back to Dashboard":
                        dispose();
                        new DashboardPage();
                        break;
                }
            });
        }

        // Left side dynamic content panel
        rightPanel = new JPanel();
        cardLayout = new CardLayout();
        rightPanel.setLayout(cardLayout);
        rightPanel.setOpaque(false);

        // Add all cards
        rightPanel.add(new IssueBookPanel(), "issue");
        rightPanel.add(new ReturnBookPanel(), "return");
        rightPanel.add(new ViewIssuedBooksPanel(), "issued");
        rightPanel.add(new ViewReturnedHistoryPanel(), "returned");
        rightPanel.add(new ViewBooksPanel(), "viewBooks"); // ✅ Existing panel used here

        backgroundPanel.add(rightPanel, BorderLayout.CENTER);
        backgroundPanel.add(rightButtonPanel, BorderLayout.EAST);

        setContentPane(backgroundPanel);
        setVisible(true);
    }
}