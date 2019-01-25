/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singlemode;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import client.TicTacTocGame;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;
import utils.SceneHandler;
import utils.Utils;

public class SingleModeController implements Initializable {

    @FXML
    private Button goOnline;
    @FXML
    public Button play;

    @FXML
    public Label lblCell1;

    @FXML
    public Label lblCell4;

    @FXML
    public Label lblCell7;

    @FXML
    public Label lblCell2;

    @FXML
    public Label lblCell5;

    @FXML
    public Label lblCell8;

    @FXML
    public Label lblCell3;

    @FXML
    public Label lblCell6;

    @FXML
    public Label lblCell9;

    @FXML
    private Label username;
    public static String userName = "";
    @FXML
    public Label numMatch;

    @FXML
    private RadioButton RadioButtonHard;

    @FXML
    public Label userScoreLbl;
    TicTacTocGame game;
    SceneHandler handler = SceneHandler.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        game = new TicTacTocGame(this);
        try {TextInputDialog dialog = new TextInputDialog();
            while (!(Utils.validateName(userName))) {    
                dialog.setTitle("TicTacToe");
                dialog.setHeaderText("Welcom To TicTacToe Game");
                dialog.setContentText("Please enter your name:");
                dialog.showAndWait();
                
                int x = dialog.getEditor().getText().length();
                if (x < 9) {
                    userName = dialog.getEditor().getText();// JOptionPane.showInputDialog("please enter your name : ");
                } else {
                    userName = dialog.getEditor().getText().substring(0, 9) + "..";
                }
            }
        } catch (NullPointerException ex) {

        }
        username.setText(userName);
        lblCell1.setOnMouseClicked((event) -> {
            if (!RadioButtonHard.isSelected()) {
                game.gameStartSingleMode(lblCell1, 0);
            } else {
                game.gameHardLevelSIngleMode(lblCell1, 0);

            }
        });
        lblCell2.setOnMouseClicked((event) -> {
            if (!RadioButtonHard.isSelected()) {
                game.gameStartSingleMode(lblCell2, 1);
            } else {
                game.gameHardLevelSIngleMode(lblCell2, 1);
            }

        });
        lblCell3.setOnMouseClicked((event) -> {
            if (!RadioButtonHard.isSelected()) {
                game.gameStartSingleMode(lblCell3, 2);
            } else {
                game.gameHardLevelSIngleMode(lblCell3, 2);
            }

        });

        lblCell4.setOnMouseClicked((event) -> {
            if (!RadioButtonHard.isSelected()) {
                game.gameStartSingleMode(lblCell4, 3);
            } else {
                game.gameHardLevelSIngleMode(lblCell4, 3);
            }

        });
        lblCell5.setOnMouseClicked((event) -> {
            if (!RadioButtonHard.isSelected()) {
                game.gameStartSingleMode(lblCell5, 4);
            } else {
                game.gameHardLevelSIngleMode(lblCell5, 4);
            }

        });
        lblCell6.setOnMouseClicked((event) -> {
            if (!RadioButtonHard.isSelected()) {
                game.gameStartSingleMode(lblCell6, 5);
            } else {
                game.gameHardLevelSIngleMode(lblCell6, 5);
            }

        });
        lblCell7.setOnMouseClicked((event) -> {
            if (!RadioButtonHard.isSelected()) {
                game.gameStartSingleMode(lblCell7, 6);
            } else {
                game.gameHardLevelSIngleMode(lblCell7, 6);
            }

        });
        lblCell8.setOnMouseClicked((event) -> {
            if (!RadioButtonHard.isSelected()) {
                game.gameStartSingleMode(lblCell8, 7);
            } else {
                game.gameHardLevelSIngleMode(lblCell8, 7);
            }

        });
        lblCell9.setOnMouseClicked((event) -> {
            if (!RadioButtonHard.isSelected()) {
                game.gameStartSingleMode(lblCell9, 8);
            } else {
                game.gameHardLevelSIngleMode(lblCell9, 8);
            }

        });
        numMatch.setText(game.getGameNum() + "");
        userScoreLbl.setText(game.getScore() + "");
        play.setVisible(false);

    }

    @FXML
    private void goOnlineAction(ActionEvent event) throws IOException {
        try {
            userName = "";
            game.setScore(0);
            game.setGameNum(0);
            handler.setScene("/login/login.fxml", " Single Mode", 800, 800, true);
        } catch (IOException ex) {
            Logger.getLogger(SingleModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void playAction(ActionEvent event) {
        try {
            handler.setScene("/singlemode/SingleMode.fxml", " Single Mode", 800, 800, true);
        } catch (IOException ex) {
            Logger.getLogger(SingleModeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void playHard(ActionEvent event) {
        
    }

    public void clearGrid() {
        lblCell1.setText("");
        lblCell2.setText("");
        lblCell3.setText("");
        lblCell4.setText("");
        lblCell5.setText("");
        lblCell6.setText("");
        lblCell7.setText("");
        lblCell8.setText("");
        lblCell9.setText("");

    }

}
