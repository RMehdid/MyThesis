import Auth.AuthFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AuthFrame frame = new AuthFrame();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(10, 10, 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}
