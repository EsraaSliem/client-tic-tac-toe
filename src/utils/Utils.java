package utils;

import client.server.remote.interfaces.UserAccountHandler;
import client.server.remote.interfaces.UserModel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.regex.Matcher;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javax.swing.JOptionPane;

public class Utils {

    private static String symbol = "x";
    public static boolean isMyTurn;
    public static boolean logout;
    
    public static void setSymbol(String symbol) {
        Utils.symbol = symbol;
    }

    public static String getSymbol() {
        return symbol;
    }

    static UserAccountHandler userAccountHandler;
    private static UserModel model = new UserModel();
    private static UserModel player2 = new UserModel();

    private static boolean b = false;
    public static boolean isPlaying = false;

    public static void setIsPlaying(boolean b) {
        isPlaying=b;
    }
    public static boolean getIsPlying() {
        return isPlaying;
        
    }
    SceneHandler handler = SceneHandler.getInstance();

    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static void switchWindow(Parent root) throws IOException {
        Stage login = new Stage();
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.initStyle(StageStyle.UNIFIED);
        login.show();
    }

    public static boolean showRequestDialouge(String playerName) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {

                        Alert alert = new Alert(AlertType.CONFIRMATION, playerName + "request to play with you ", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            b = true;
                            System.out.println("button yes clicked");

                        } else if (alert.getResult() == ButtonType.NO) {
                            System.out.println("button no clicked");
                            b = false;
                        }
                    }
                };

                // UI update is run on the Application thread
                Platform.runLater(updater);
            }

        });
        thread.setDaemon(true);
        thread.start();
        return b;

    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static void switchWindow(Stage window, Application app) {
        try {
            app.start(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean validateName(String emailStr) {
        Matcher matcher = Constants.VALID_Name_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static UserAccountHandler establishConnection() throws RemoteException, NotBoundException {
        if (userAccountHandler == null) {
            // java.security.AllPermission;
            Registry registry = LocateRegistry.getRegistry(Constants.SERVER_IP_ADDRESS, Constants.PORT);
            userAccountHandler = (UserAccountHandler) registry.lookup(Constants.ACCOUNT_SERVICE);
        }

        return userAccountHandler;
    }

    public static String getIpAddress() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        // System.out.println("IP Address:- " + inetAddress.getHostAddress());
        return inetAddress.getHostAddress();
    }

    public static void setCurrentUser(UserModel currentUser) {

        model = currentUser;
    }

    public static UserModel getCurrentUser() {
        return model;
    }

    public void missingConnection() {
        JOptionPane.showMessageDialog(null, "Connection lost");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login/login.fxml"));
            Utils.switchWindow(root);
            handler.setScene("/login/login.fxml", "login", 500, 500, true);

        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setPlayer(UserModel currentUser) {

        player2 = currentUser;
    }

    public static UserModel getlPayer() {

        return player2;

    }

    public static String getCurrentDate() {
        Date date = new Date();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String currentdate = dateFormat.format(date);
        return currentdate;
    }

}
