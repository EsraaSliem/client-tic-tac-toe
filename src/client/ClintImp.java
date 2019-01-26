package client;

import client.server.remote.interfaces.ClientInterface;
import client.server.remote.interfaces.Step;
import client.server.remote.interfaces.UserAccountHandler;
import client.server.remote.interfaces.UserModel;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import multimode.MultiModeView;
import multimode.MyMultiModeController;
import org.controlsfx.control.Notifications;
import utils.SceneHandler;

import utils.Utils;

public class ClintImp extends UnicastRemoteObject implements ClientInterface {

    UserAccountHandler accountHandler;
    MyMultiModeController controoler = new MyMultiModeController();
    public static boolean isReceving;
    private SceneHandler sceneHandler;
    private String message = "";

    public ClintImp() throws RemoteException, NotBoundException {
        accountHandler = Utils.establishConnection();
        sceneHandler = SceneHandler.getInstance();
    }

    @Override
    public boolean requestGame(UserModel model1, UserModel player2) throws RemoteException {
        int x = JOptionPane.showConfirmDialog(null, "player " + model1.getUserName() + " wants to play with you ","TicTacToe",JOptionPane.INFORMATION_MESSAGE);

        if (x == 0) {
            Utils.setPlayer(model1);
            Utils.setSymbol("o");
            Utils.isMyTurn = false;
            return true;

        }
        else
        {
            return false;
        }
    }

    @Override
    public void startGame(UserModel player1, UserModel player2) throws RemoteException {
        controoler.startGame();
        MultiModeView.getInstance().startgame();
    }

    @Override
    public void drawMove(Step step) throws RemoteException {
        //  controoler.drawMove(step);
//        controoler.dra(step);
        controoler.setStep(step);
        isReceving = true;
        MultiModeView.getInstance().receive(step);
    }

    @Override
    public void receiverMessage(UserModel player1, String message) throws RemoteException {
        MultiModeView.getInstance().print(player1, message);
    }

    @Override
    public void closeGame() throws RemoteException {

        System.err.println("entered fun 2");
        Utils.logout = true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                
                try {
                    Utils.isPlaying=false;
                    sceneHandler.setScene("/multimode/MultiMode.fxml", "Multi Mode", 800, 800, true);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });
    }

    @Override
    public void serverLogOut() throws RemoteException {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Utils.isPlaying=false;
                    JOptionPane.showMessageDialog(null, "server is shut down ", "TicTacToe", JOptionPane.INFORMATION_MESSAGE);
                    sceneHandler.setScene("/login/login.fxml", " login ", 800, 800, true);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });
        System.out.println("client.ClintImp.serverLogOut()");
    }

    @Override
    public void refreshOnlineUsersList(UserModel user, boolean isLoggedIn) throws RemoteException {

        MultiModeView multiModeController = MultiModeView.getInstance();
        if (multiModeController.onlineUsersList != null) {
            if (isLoggedIn) {
                multiModeController.onlineUsersList.add(user);
                message = " Just logged in";
            } else if (!isLoggedIn) {
                message = " Just logged out";
                for (int i = 0; i < multiModeController.onlineUsersList.size(); i++) {
                    if (multiModeController.onlineUsersList.get(i).getEmailAddress().equals(user.getEmailAddress())) {
                        multiModeController.onlineUsersList.remove(i);
                    }

                }

            }

            Platform.runLater(() -> {
                try{
                multiModeController.refreshListt();
                Notifications notificationBuilder = Notifications.create()
                        .title("Online Player")
                        .text(user.getUserName() + message)
                        .darkStyle()
                        .graphic(null)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                AudioClip note = new AudioClip(getClass().getResource("/images/definite.mp3").toString());
                note.play();
                notificationBuilder.showInformation();
                }catch(NoClassDefFoundError ex)
                {
                    System.err.println("ex catch");
                }
            });
           

        }

        // Platform.runLater(runnable);
    }
}
