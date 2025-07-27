package ui.login;

import db.DBConnection;
import ui.dashboard.DashboardPage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Image backgroundImage;

    public LoginPage() {
        setTitle("Admin Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            backgroundImage = ImageIO.read(new File("src/assets/img/hg.jpg")); // Centered, not zoomed image
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(new BackgroundPanel());
        setLayout(new GridBagLayout()); // Centered layout

        initComponents();

        setVisible(true);
    }

    private void initComponents() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false); // Transparent login panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel titleLabel = new JLabel("Admin Login");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);

        // Username Label
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.BLACK);
        userLabel.setFont(new Font("Serif", Font.BOLD, 24));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(userLabel, gbc);

        // Username Field
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Serif", Font.PLAIN, 22));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(usernameField, gbc);

        // Password Label
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.BLACK);
        passLabel.setFont(new Font("Serif", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(passLabel, gbc);

        // Password Field
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Serif", Font.PLAIN, 22));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passwordField, gbc);

        // Login + Cancel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Serif", Font.BOLD, 22));
        loginButton.setBackground(new Color(0, 128, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        buttonPanel.add(loginButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Serif", Font.BOLD, 22));
        cancelButton.setBackground(new Color(128, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(buttonPanel, gbc);

        // Add to main layout
        add(loginPanel);

        // Button actions
        loginButton.addActionListener(e -> doLogin());
        cancelButton.addActionListener(e -> System.exit(0));
    }

    private void doLogin() {
        String user = usernameField.getText();
        String pass = String.valueOf(passwordField.getPassword());

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?")) {
            pst.setString(1, user);
            pst.setString(2, pass);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    dispose(); // Close login window
                    new DashboardPage(); // Open dashboard
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password!");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Background Panel to draw image at original size and center
    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                int imgWidth = backgroundImage.getWidth(null);
                int imgHeight = backgroundImage.getHeight(null);
                int x = (getWidth() - imgWidth) / 2;
                int y = (getHeight() - imgHeight) / 2;
                g.drawImage(backgroundImage, x, y, this);
            }
        }
    }
}
