package client;

import client.server.remote.interfaces.UserModel;
import demo.MoveContent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import main.XMLRecord;
import singlemode.SingleModeController;
import utils.SceneHandler;

public class TicTacTocGame {

    private SceneHandler handler = SceneHandler.getInstance();

    public String getMySymbol() {
        return MySymbol;
    }

    /**
     * @param MySymbol the MySymbol to set
     */
    public void setMySymbol(String MySymbol) {
        this.MySymbol = MySymbol;
    }

//game variable
    private String current_player;
    private String MySymbol;
    private boolean start_game_flag;
    private int game_arr[];
    private int counter;
    private boolean winFlag;
    SingleModeController singlemodeController;
    private UserModel user2;
    private static int Score = 0;
    private static int ComputerScore;
    private static int gameNum = 0;
    private Stage primaryStage;
    XMLRecord recordObj;

    public TicTacTocGame(SingleModeController tc) {
        current_player = "X";
        start_game_flag = false;
        game_arr = new int[9];
        counter = 0;
        singlemodeController = tc;
        recordObj = new XMLRecord();
        recordObj.unmarchal();

        user2 = null;
        MySymbol = "X";
    }

    public UserModel getUser2() {

        return user2;
    }

    public void setUser2(UserModel user2) {
        this.user2 = user2;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public int getComputerScore() {
        return ComputerScore;
    }

    public void setComputerScore(int ComputerScore) {
        this.ComputerScore = ComputerScore;
    }

    public int getGameNum() {
        return gameNum;
    }

    public void setGameNum(int gameNum) {
        this.gameNum = gameNum;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int[] getGame_arr() {

        return game_arr;
    }

    public boolean setGame_arr(int move) {
        if (game_arr[move] == 0) {
            if (getCurrent_player().equals("X")) {
                game_arr[move] = 1;
                recordObj.addMove(move, getCurrent_player());
            }
            if (getCurrent_player().equals("O")) {
                game_arr[move] = 2;
                recordObj.addMove(move, getCurrent_player());
            }

            setCounter(getCounter() + 1);
            return true;
        }
        return false;
    }

    public String getCurrent_player() {
        return current_player;
    }

    public void setCurrent_player(String current_player) {
        this.current_player = current_player;
    }

    public boolean getStart_game_flag() {
        return start_game_flag;
    }

    public void setStart_game_flag(boolean start_game_flag) {
        this.start_game_flag = start_game_flag;
    }

    //check x or o win
    /*public String checkWiner(int[] game_arr) {
            int x = IntStream.of(game_arr).filter(value -> value == 1).sum();

            if (x % 2 == 1) {
                return "X";
            } else {
                return "O";
            }

        }
     */
    //check if any one win or not
    public boolean checkWining() {
        if ((game_arr[0] == 1 && game_arr[1] == 1 && game_arr[2] == 1)
                || (game_arr[3] == 1 && game_arr[4] == 1 && game_arr[5] == 1)
                || (game_arr[6] == 1 && game_arr[7] == 1 && game_arr[8] == 1)
                || (game_arr[0] == 1 && game_arr[4] == 1 && game_arr[8] == 1)
                || (game_arr[2] == 1 && game_arr[4] == 1 && game_arr[6] == 1)
                || (game_arr[0] == 1 && game_arr[3] == 1 && game_arr[6] == 1)
                || (game_arr[1] == 1 && game_arr[4] == 1 && game_arr[7] == 1)
                || (game_arr[2] == 1 && game_arr[5] == 1 && game_arr[8] == 1)) {
            System.out.println("X is Winning");
            Score += 10;

            winFlag = true;
            gameNum++;
            singlemodeController.userScoreLbl.setText(Score + "");
            singlemodeController.numMatch.setText(gameNum + "");
            newGame("congratulation, you win! ");
            return true;
        } else {
            if ((game_arr[0] == 2 && game_arr[1] == 2 && game_arr[2] == 2)
                    || (game_arr[3] == 2 && game_arr[4] == 2 && game_arr[5] == 2)
                    || (game_arr[6] == 2 && game_arr[7] == 2 && game_arr[8] == 2)
                    || (game_arr[0] == 2 && game_arr[4] == 2 && game_arr[8] == 2)
                    || (game_arr[2] == 2 && game_arr[4] == 2 && game_arr[6] == 2)
                    || (game_arr[0] == 2 && game_arr[3] == 2 && game_arr[6] == 2)
                    || (game_arr[1] == 2 && game_arr[4] == 2 && game_arr[7] == 2)
                    || (game_arr[2] == 2 && game_arr[5] == 2 && game_arr[8] == 2)) {
                System.out.println("O is Winning");
                winFlag = true;
                ComputerScore += 10;
                gameNum++;
                singlemodeController.userScoreLbl.setText(Score + "");
                singlemodeController.numMatch.setText(gameNum + "");
                newGame("You lose! ");
                return true;
            }
        }
        return false;
    }

    //calculate movement of computer
    //Ramadan
    public int checkWiningCell() {
        if (game_arr[0] == 2 && game_arr[1] == 2 && game_arr[2] == 0) {
            return 2;
        } else if (game_arr[3] == 2 && game_arr[4] == 2 && game_arr[5] == 0) {
            return 5;
        } else if (game_arr[6] == 2 && game_arr[7] == 2 && game_arr[8] == 0) {
            return 8;
        } else if (game_arr[8] == 2 && game_arr[7] == 2 && game_arr[6] == 0) {
            return 6;
        } else if (game_arr[5] == 2 && game_arr[4] == 2 && game_arr[3] == 0) {
            return 3;
        } else if (game_arr[2] == 2 && game_arr[1] == 2 && game_arr[0] == 0) {
            return 0;
        } else if (game_arr[7] == 2 && game_arr[4] == 2 && game_arr[1] == 0) {
            return 1;
        } else if (game_arr[1] == 2 && game_arr[4] == 2 && game_arr[7] == 0) {
            return 7;
        } else if (game_arr[2] == 2 && game_arr[4] == 2 && game_arr[6] == 0) {
            return 6;
        } else if (game_arr[8] == 2 && game_arr[4] == 2 && game_arr[0] == 0) {
            return 0;
        } else if (game_arr[0] == 2 && game_arr[4] == 2 && game_arr[8] == 0) {
            return 8;
        } else if (game_arr[6] == 2 && game_arr[4] == 2 && game_arr[2] == 0) {
            return 2;
        } else if (game_arr[2] == 2 && game_arr[8] == 2 && game_arr[5] == 0) {
            return 5;
        } else if (game_arr[0] == 2 && game_arr[6] == 2 && game_arr[3] == 0) {
            return 3;
        } else if (game_arr[0] == 2 && game_arr[2] == 2 && game_arr[1] == 0) {
            return 1;
        } else if (game_arr[6] == 2 && game_arr[8] == 2 && game_arr[7] == 0) {
            return 7;
        } else if (game_arr[3] == 2 && game_arr[5] == 2 && game_arr[4] == 0) {
            return 4;
        } else if (game_arr[2] == 1 && game_arr[5] == 1 && game_arr[8] == 0) {
            return 8;
        } else if (game_arr[1] == 2 && game_arr[7] == 2 && game_arr[4] == 0) {
            return 4;
        } else {
            return -1;
        }
    }

    public int checkLostedCell() {
        if (game_arr[0] == 1 && game_arr[1] == 1 && game_arr[1] == 0) {
            return 2;
        } else if (game_arr[3] == 1 && game_arr[4] == 1 && game_arr[5] == 0) {
            return 5;
        } else if (game_arr[6] == 1 && game_arr[7] == 1 && game_arr[8] == 0) {
            return 8;
        } else if (game_arr[8] == 1 && game_arr[7] == 1 && game_arr[6] == 0) {
            return 6;
        } else if (game_arr[5] == 1 && game_arr[4] == 1 && game_arr[3] == 0) {
            return 3;
        } else if (game_arr[2] == 1 && game_arr[1] == 1 && game_arr[0] == 0) {
            return 0;
        } else if (game_arr[7] == 1 && game_arr[4] == 1 && game_arr[1] == 0) {
            return 1;
        } else if (game_arr[1] == 1 && game_arr[4] == 1 && game_arr[7] == 0) {
            return 7;
        } else if (game_arr[2] == 1 && game_arr[4] == 1 && game_arr[6] == 0) {
            return 6;
        } else if (game_arr[8] == 1 && game_arr[4] == 1 && game_arr[0] == 0) {
            return 0;
        } else if (game_arr[0] == 1 && game_arr[4] == 1 && game_arr[8] == 0) {
            return 8;
        } else if (game_arr[6] == 1 && game_arr[4] == 1 && game_arr[2] == 0) {
            return 2;
        } else if (game_arr[2] == 1 && game_arr[8] == 1 && game_arr[5] == 0) {
            return 5;
        } else if (game_arr[0] == 1 && game_arr[6] == 1 && game_arr[3] == 0) {
            return 3;
        } else if (game_arr[0] == 1 && game_arr[2] == 1 && game_arr[1] == 0) {
            return 1;
        } else if (game_arr[6] == 1 && game_arr[8] == 1 && game_arr[7] == 0) {
            return 7;
        } else if (game_arr[3] == 1 && game_arr[5] == 1 && game_arr[4] == 0) {
            return 4;
        } else if (game_arr[1] == 1 && game_arr[7] == 1 && game_arr[4] == 0) {
            return 4;
        } else if (game_arr[2] == 1 && game_arr[5] == 1 && game_arr[8] == 0) {
            return 8;
        } else {
            return -1;
        }
    }

    public boolean checkCell(int move) {
        if (game_arr[move] == 0) {
            if (getCurrent_player().equals("X")) {

                recordObj.addMove(move, getCurrent_player());
            }
            if (getCurrent_player().equals("O")) {

                recordObj.addMove(move, getCurrent_player());
            }

            setCounter(getCounter() + 1);
            return true;
        }
        return false;
    }

    public int positionsOfTwoPlayers() {
        if (checkLostedCell() != -1) {
            int x = checkLostedCell();
            setGame_arr(checkLostedCell());
            return x;
        } else if (checkWiningCell() != -1) {
            int x = checkWiningCell();
            setGame_arr(checkWiningCell());
            return x;
        } else if (game_arr[4] == 0) {

            setGame_arr(4);
            return 4;

        } else if (game_arr[0] == 0) {

            setGame_arr(0);
            return 0;

        } else if (game_arr[2] == 0) {

            setGame_arr(2);
            return 2;

        } else if (game_arr[6] == 0) {

            setGame_arr(6);
            return 6;

        } else if (game_arr[8] == 0) {

            setGame_arr(8);
            return 8;

        } else {
            // setGame_arr(getRandom());
            return getRandom();
        }

    }

    //*********
    public int getRandom() {
        int move;
        boolean move_flag = false;
        do {
            move = ((int) Math.floor(Math.random() * 10)) % 9;
            System.out.println(move);
            if (setGame_arr(move)) {
                {
                    move_flag = true;

                    return move;
                }

            }
        } while (move_flag == false);
        return -1;
    }

//get who play and switch player
    public void switchPlayerSymbol() {

        if (current_player.equals("X")) {
            current_player = "O";
        } else {
            current_player = "X";
        }

    }

    //start game
    public boolean play(int move, Label l) {

        if (getCurrent_player().equals("X")) {
            if (setGame_arr(move)) {
                l.setText(current_player);
                switchPlayerSymbol();
                return true;
            } else {
                return false;
            }
        }

        return false;

    }

    public void computerAction(int x) {

        System.out.println(x);

        switchPlayerSymbol();
        switch (x) {
            case 0:
                singlemodeController.lblCell1.setText("O");
                break;
            case 1:

                singlemodeController.lblCell2.setText("O");
                break;
            case 2:

                singlemodeController.lblCell3.setText("O");
                break;
            case 3:

                singlemodeController.lblCell4.setText("O");
                break;
            case 4:

                singlemodeController.lblCell5.setText("O");
                break;
            case 5:

                singlemodeController.lblCell6.setText("O");
                break;
            case 6:

                singlemodeController.lblCell7.setText("O");
                break;
            case 7:

                singlemodeController.lblCell8.setText("O");
                break;
            case 8:

                singlemodeController.lblCell9.setText("O");
                break;
        }

    }

    public void playRecord(int x, String symbol) {

        System.out.println(x);

        switchPlayerSymbol();
        switch (x) {
            case 0:
                singlemodeController.lblCell1.setText(symbol);
                break;
            case 1:

                singlemodeController.lblCell2.setText(symbol);
                break;
            case 2:

                singlemodeController.lblCell3.setText(symbol);
                break;
            case 3:

                singlemodeController.lblCell4.setText(symbol);
                break;
            case 4:

                singlemodeController.lblCell5.setText(symbol);
                break;
            case 5:

                singlemodeController.lblCell6.setText(symbol);
                break;
            case 6:

                singlemodeController.lblCell7.setText(symbol);
                break;
            case 7:

                singlemodeController.lblCell8.setText(symbol);
                break;
            case 8:

                singlemodeController.lblCell9.setText(symbol);
                break;
        }

    }

    public void gameStartSingleMode(Label l, int move) {

        if (!winFlag) {
            if (MySymbol.equals(getCurrent_player())) {
                if (play(move, l)) {
                    checkWining();

                    if (!winFlag) {
                        if (getCounter() < 9) {
                            //computerAction(getRandom());
                            computerAction(getRandom());
                            checkWining();
                        } else {
                            if (counter == 9) {
                                gameNum++;
                                recordObj.marchal();
                                newGame("No one win! ");

                            }
                        }

                    }
                }
            }
        }
    }

    //*****************************ramadan
    public void gameHardLevelSIngleMode(Label l, int move) {

        if (!winFlag) {
            if (MySymbol.equals(getCurrent_player())) {
                if (play(move, l)) {
                    checkWining();

                    if (!winFlag) {
                        if (getCounter() < 9) {
                            //computerAction(getRandom());
                            System.out.println("client");
                            computerAction(positionsOfTwoPlayers());
                            checkWining();
                        } else {
                            if (counter == 9) {
                                gameNum++;
                                recordObj.marchal();
                                newGame("No one win! ");

                            }
                        }

                    }
                }
            }
        }
    }

    //*******************************
    public void newGame(String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TicTacTocGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        recordObj.marchal();
                        int recordResult = JOptionPane.showConfirmDialog(null,msg+ "replay the last game ?","TicTacToe",JOptionPane.INFORMATION_MESSAGE);
                        if (recordResult == 0) {
                            displayRecord();
                            singlemodeController.play.setVisible(true);

                        } else {

                            try {
                                handler.setScene("/singlemode/SingleMode.fxml", " Single Mode", 800, 800, true);
                            } catch (IOException ex) {
                                Logger.getLogger(TicTacTocGame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }
                });
            }
        }).start();
    }

    public void displayRecord() {
        recordObj.unmarchal();
        final ArrayList<MoveContent> playrecord = recordObj.playRecord();
        singlemodeController.lblCell1.setText("");
        singlemodeController.lblCell2.setText("");
        singlemodeController.lblCell3.setText("");
        singlemodeController.lblCell4.setText("");
        singlemodeController.lblCell5.setText("");
        singlemodeController.lblCell6.setText("");
        singlemodeController.lblCell7.setText("");
        singlemodeController.lblCell8.setText("");
        singlemodeController.lblCell9.setText("");
        new AnimationTimer() {
            int i = 0;
            long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 700_000_000) {
                    playRecord(recordObj.playRecord().get(i).getPosition(), playrecord.get(i).getDraw());
                    i++;
                    if (i >= playrecord.size()) {
                        stop();
                    }
                    lastUpdate = now;
                }
            }
        }.start();
    }
}
