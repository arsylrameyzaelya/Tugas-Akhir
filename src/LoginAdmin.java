import javax.swing.*;
import java.awt.*;

public class LoginAdmin extends JFrame {

    private JPasswordField txtPass;

    public LoginAdmin() {
        setTitle("Login Admin");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // BACKGROUND
        ImageIcon icon = new ImageIcon("src/background.jpg");
        Image img = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel panel = new JLabel(new ImageIcon(img));
        panel.setLayout(null);

        // TITLE
        JLabel title = new JLabel("LOGIN ADMIN");
        title.setBounds(110, 30, 200, 30);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        panel.add(title);

        // PASSWORD
        JLabel lPass = new JLabel("Password:");
        lPass.setBounds(50, 100, 100, 30);
        lPass.setForeground(Color.WHITE);
        panel.add(lPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(150, 100, 180, 30);
        panel.add(txtPass);

        // BUTTON
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(130, 160, 120, 35);
        btnLogin.setBackground(new Color(255,105,180));
        btnLogin.setForeground(Color.WHITE);
        panel.add(btnLogin);

        add(panel);

        // ACTION
        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String pass = new String(txtPass.getPassword());

        if (pass.equals("admin123")) {
            JOptionPane.showMessageDialog(this, "Login berhasil!");

            new Admin().setVisible(true); // buka admin
            dispose(); // tutup login

        } else {
            JOptionPane.showMessageDialog(this, "Password salah!");
        }
    }

    public static void main(String[] args) {
        new LoginAdmin().setVisible(true);
    }
}