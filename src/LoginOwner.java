import javax.swing.*;
import java.awt.*;

public class LoginOwner extends JFrame {

    private JPasswordField txtPass;

    public LoginOwner() {
        setTitle("Login Owner");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // BACKGROUND
        ImageIcon icon = new ImageIcon("src/background.jpg");
        Image img = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel panel = new JLabel(new ImageIcon(img));
        panel.setLayout(null);

        JLabel title = new JLabel("LOGIN OWNER");
        title.setBounds(110, 30, 200, 30);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel lPass = new JLabel("Password:");
        lPass.setBounds(50, 100, 100, 30);
        lPass.setForeground(Color.WHITE);
        panel.add(lPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(150, 100, 180, 30);
        panel.add(txtPass);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(130, 160, 120, 35);
        btnLogin.setBackground(new Color(0,150,255));
        btnLogin.setForeground(Color.WHITE);
        panel.add(btnLogin);

        add(panel);

        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String pass = new String(txtPass.getPassword());

        if (pass.equals("owner123")) {
            new Owner().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Password salah!");
        }
    }

    public static void main(String[] args) {
        new LoginOwner().setVisible(true);
    }
}