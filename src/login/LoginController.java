package login;

import client.server.remote.interfaces.UserAccountHandler;
import client.server.remote.interfaces.UserModel;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import client.ClintImp;

import utils.SceneHandler;
import utils.Utils;

public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignup;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Button btnPlayNow;

    private SceneHandler handler = SceneHandler.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleLoginAction(ActionEvent event) throws IOException {

        try {
            UserAccountHandler accountHandler;


            accountHandler = Utils.establishConnection();

            UserModel model = accountHandler.login(new ClintImp(), txtUserName.getText(), txtPassword.getText());

            if (model != null) {
                Utils.setCurrentUser(model);
                handler.setScene("/multimode/MultiMode.fxml", " Multi Mode ", 800, 800, true);
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, btnLogin.getScene().getWindow(), "error", "User is already logged in");
            }

        } catch (RemoteException ex) {
            Utils.showAlert(Alert.AlertType.ERROR, btnLogin.getScene().getWindow(), "server is un available", "Sorry, try again later");
        } catch (IOException ex) {
            Utils.showAlert(Alert.AlertType.ERROR, btnLogin.getScene().getWindow(), "server is un available", "try again later ");
        } catch (NotBoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            Utils.showAlert(Alert.AlertType.ERROR, btnLogin.getScene().getWindow(), "server is un available", "try again later ");

        } catch (NullPointerException ex) {
            Utils.showAlert(Alert.AlertType.ERROR, btnLogin.getScene().getWindow(), "server is un available", "try again later ");
        }
    }

    @FXML
    private void handleSignUpAction(ActionEvent event) throws IOException {
        handler.setScene("/sinup/signup.fxml", "Sign Up", 800, 800, true);
    }

    @FXML
    private void handlePlayNowAction(ActionEvent event) throws IOException {

        handler.setScene("/singlemode/SingleMode.fxml", "Single Mode", 800, 500, true);

    }
}
