package multimode;

import client.server.remote.interfaces.Step;
import client.server.remote.interfaces.UserAccountHandler;
import client.server.remote.interfaces.UserModel;
import java.rmi.ConnectException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import utils.Utils;

public class MyControoler {

    Utils util = new Utils();
    static UserAccountHandler accountHandler = null;
    private Step step;
    FXMLLoader fxmlLoader = new FXMLLoader();
    Pane p;
    MultiModeController multiModeController;

    public static boolean logOut() {
        try {
            accountHandler = Utils.establishConnection();
            return accountHandler.logOut(Utils.getCurrentUser().getEmailAddress());
        } catch (RemoteException ex) {
            Logger.getLogger(MyControoler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(MyControoler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(MyControoler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public MyControoler() {

    }

    static void transmitMove(int i, String x, UserModel model) {
        try {

            accountHandler = Utils.establishConnection();
            accountHandler.transmitMove(new Step(model.getEmailAddress(), Utils.getCurrentUser().getEmailAddress(), i, x));
        } catch (RemoteException ex) {
            Logger.getLogger(MyControoler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(MyControoler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setStep(Step step) {
        this.step = step;

        System.out.println(step.draw);
        System.out.println(step.position);
        System.out.println(step.player);
    }

    public Step getStep() {
        return step;
    }

    public void startGame() {
        Utils.isPlaying = true;

    }

    boolean accept = false;

    public static void requestGame(UserModel selectedItem) throws RemoteException, NotBoundException {
 
        Thread thread;
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater;
                updater = new Runnable() {

                    @Override
                    public void run() {

                        try {

                            if ((accountHandler = Utils.establishConnection()) != null && selectedItem != null) {
                                try {
                                    if (accountHandler.requestGame(Utils.getCurrentUser(), selectedItem)) {

                                    } else {
                                        MultiModeController.getInstance().showrefusedMessahe();
                                    }
                                } catch (NullPointerException ex) {
                                    System.out.println("NullPointerException");
                                    JOptionPane.showMessageDialog(null, " some thing wrong");
                                } catch (ConnectException ex) {
                                    System.out.println("connection exception");

                                    JOptionPane.showMessageDialog(null, "ConnectException");
                                } catch (RemoteException ex) {
                                    System.out.println("connection exception");
                                    JOptionPane.showMessageDialog(null, "ConnectException");

                                }

                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                };
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }

                // UI update is run on the Application thread
                Platform.runLater(updater);

            }
        }
        );
        thread.setDaemon(
                true);
        thread.start();

    }

}
