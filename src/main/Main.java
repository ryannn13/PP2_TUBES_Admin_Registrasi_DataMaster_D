package main;

import view.DashboardMasyarakat;
import view.LoginView;
import model.User;

public class Main {
    public static void main(String[] args) {
        User masyarakatUser = new User();
        masyarakatUser.setUserId(1);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }
}
