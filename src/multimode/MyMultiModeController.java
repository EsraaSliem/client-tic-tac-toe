package multimode;

import client.server.remote.interfaces.Step;
import client.server.remote.interfaces.UserAccountHandler;
import client.server.remote.interfaces.UserModel;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import utils.Utils;

public class MyMultiModeController {

    Utils util = new Utils();
    static UserAccountHandler accountHandler = null;
    private Step step;
    FXMLLoader fxmlLoader = new FXMLLoader();
    Pane p;
    MultiModeView multiModeController;

    public static boolean logOut() {
        try {
            accountHandler = Utils.establishConnection();
            return accountHandler.logOut(Utils.getCurrentUser().getEmailAddress());
        } catch (RemoteException ex) {
            Logger.getLogger(MyMultiModeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(MyMultiModeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(MyMultiModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception ex)
        {
            Logger.getLogger(MyMultiModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public MyMultiModeController() {

    }

    static void transmitMove(int i, String x, UserModel model) {
        try {

            accountHandler = Utils.establishConnection();
            accountHandler.transmitMove(new Step(model.getEmailAddress(), Utils.getCurrentUser().getEmailAddress(), i, x));
        } catch (RemoteException ex) {
            Logger.getLogger(MyMultiModeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(MyMultiModeController.class.getName()).log(Level.SEVERE, null, ex);
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
                                    int reqResult=accountHandler.requestGame(Utils.getCurrentUser(), selectedItem);
                                    if (reqResult==0) {                                        
                                        MultiModeView.getInstance().showBusyMessage();
                                    } else if(reqResult==2) {
                                        MultiModeView.getInstance().showRefusedMessahe();
                                    }
                                } catch (NullPointerException ex) {
                                    System.out.println("NullPointerException");
                                    JOptionPane.showMessageDialog(null, " some thing wrong","TicTacToe",JOptionPane.INFORMATION_MESSAGE);
                                } catch (ConnectException ex) {
                                    System.out.println("connection exception");

                                    JOptionPane.showMessageDialog(null, "ConnectException","TicTacToe",JOptionPane.INFORMATION_MESSAGE);
                                } catch (RemoteException ex) {
                                    System.out.println("connection exception");
                                    JOptionPane.showMessageDialog(null, "ConnectException","TicTacToe",JOptionPane.INFORMATION_MESSAGE);
                                }
                                catch(IllegalStateException ex)
                                {
                                    System.out.println("IllegalStateException()");
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
        thread.setDaemon(true);
        thread.start();

    }

}
