import javax.swing.*;
import java.awt.*;

public class LoginOwner extends JFrame {

    private JPasswordField txtPassword;

    public LoginOwner() {

        setTitle("Login Owner");
        setSize(400,300);
        setLocationRelativeTo(null);

        ImageIcon icon =
                new ImageIcon("src/background.jpg");

        Image img =
                icon.getImage().getScaledInstance(
                        400,300,
                        Image.SCALE_SMOOTH);

        JLabel background =
                new JLabel(new ImageIcon(img));

        background.setLayout(null);

        JLabel title =
                new JLabel("LOGIN OWNER");

        title.setBounds(140,40,200,30);

        title.setFont(new Font(
                "Arial",Font.BOLD,18));

        title.setForeground(Color.WHITE);

        background.add(title);

        JLabel lblPass =
                new JLabel("Password");

        lblPass.setBounds(80,100,100,30);

        lblPass.setForeground(Color.WHITE);

        background.add(lblPass);

        txtPassword =
                new JPasswordField();

        txtPassword.setBounds(160,100,140,30);

        background.add(txtPassword);

        JButton btnLogin =
                new JButton("Login");

        btnLogin.setBounds(140,160,100,35);

        btnLogin.setBackground(
                new Color(255,105,180));

        btnLogin.setForeground(Color.WHITE);

        background.add(btnLogin);

        add(background);

        btnLogin.addActionListener(e -> login());
    }

    private void login() {

        String pass =
                new String(txtPassword.getPassword());

        if(pass.equals("owner123")) {

            new Owner().setVisible(true);

            dispose();

        }

        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Password salah!");

        }
    }

    public static void main(String[] args) {

        new LoginOwner().setVisible(true);

    }
}