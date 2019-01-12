package multimode;

import client.server.remote.interfaces.UserAccountHandler;
import client.server.remote.interfaces.UserModel;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import utils.MyControoler;
import utils.Utils;

public class MultiModeController implements Initializable {

    UserAccountHandler accountHandler = null;

    @FXML
    private Label lable1;

    @FXML
    private Label lable4;

    @FXML
    private Label lable7;

    @FXML
    private Label lable2;

    @FXML
    private Label lable5;

    @FXML
    private Label lable8;

    @FXML
    private Label lable3;

    @FXML
    private Label lable6;

    @FXML
    private Label lable9;

    @FXML
    private Label user1;
    @FXML
    private Label user2;
    @FXML
    private Button startgame;
    @FXML
    private Button exit;
    @FXML
    private Button btnLogout;
    @FXML
    private Button record;
    @FXML
    private Button back;
    @FXML
    private GridPane myGridPane;
    @FXML
    private JFXListView<UserModel> listView;
    Utils util = new Utils();
    public ObservableList<UserModel> mylistview;
    UserAccountHandler accountHandler1;

    public MultiModeController() {
        try {
            accountHandler1 = Utils.establishConnection();
        } catch (RemoteException ex) {
            util.missingConnection();
        } catch (NotBoundException ex) {
            util.missingConnection();
        }
    }

    class Cell extends ListCell<UserModel> {

        HBox hbox = new HBox();
        Button btn = new Button("Delete");
        Label label = new Label("");
        Pane pane = new Pane();
        //  Image profile = new Image("images/tic-tac-toe.png");
        // ImageView img = new ImageView(profile);

        public Cell() {
            super();
            new MultiModeController();
            hbox.getChildren().addAll(label, pane, btn);
            hbox.setHgrow(pane, Priority.ALWAYS);
            listView.setOnMouseClicked(event -> {
                try {
                    MyControoler.requestGame(listView.getSelectionModel().getSelectedItem());
                } catch (RemoteException ex) {
                    util.missingConnection();
                } catch (NotBoundException ex) {
                    util.missingConnection();
                }
                 catch (NullPointerException ex) {
                    util.missingConnection();
                }
                

            }
            );

        }

        @Override
        protected void updateItem(UserModel item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);
            if (item != null && !empty) {
                label.setText(item.getUserName());
                setGraphic(hbox);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            UserAccountHandler accountHandler1 = Utils.establishConnection();
            List<UserModel> list = accountHandler1.getOnlinePlayer();

            mylistview = FXCollections.observableArrayList(list);

        } catch (RemoteException | NotBoundException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
        listView.setItems(mylistview);
        GridPane pane = new GridPane();
        Label name = new Label("gg");
        Button btn = new Button("dd");
        pane.add(name, 0, 0);
        pane.add(btn, 0, 1);
        listView.setCellFactory(param -> new Cell());

    }

    @FXML
    private void startGameAction(ActionEvent event) {
    }

    @FXML
    private void exitAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void recordAction(ActionEvent event) {

    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        exit.getScene().getWindow().hide();

        Utils.switchWindow(FXMLLoader.load(getClass().getResource("/choosemode/SelectMode.fxml")));

    }

    @FXML
    void logOutAction(ActionEvent event) throws RemoteException, IOException, NotBoundException {
        try {
            
            if (MyControoler.logOut()) {
                Utils.switchWindow(FXMLLoader.load(getClass().getResource("/choosemode/SelectMode.fxml")));
            }
            else
                 util.missingConnection();
        }  catch (RemoteException | NotBoundException ex) {
            util.missingConnection();
        } 
    }

}