package sample;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.layout.Region;

import java.io.File;
import java.io.PrintWriter;
import java.lang.Integer;

public class Controller {

    private static final String wel= "Welcome to SudokuKiller!$:_\n";
    @FXML
    public AnchorPane base;
    @FXML
    public Button bt00, bt01, bt02, bt03;
    @FXML
    public GridPane gp00;
    @FXML
    public Label lb00;
    @FXML
    public void initialize(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                TextField cur = new TextField();
                cur.setText("");
                cur.setMaxHeight(33);
                cur.setMaxWidth(33);
                gp00.add(cur, j, i);
            }
        }
    }


    public void clearBoard(){
        for (Node node: gp00.getChildren()){
            TextField cur = new TextField();
            cur.setMaxHeight(33);
            cur.setMaxWidth(33);
            if (node instanceof TextField){
                ((TextField) node).setText("");
            }
        }
        lb00.setText(wel+"Please input the puzzle");
    }

    public void getBoard(){
        int[][] board = new int[9][9];
        int count = 0;
        int fl = 0;
        for (Node node: gp00.getChildren()){
            if (node instanceof TextField){
                String toParse = ((TextField) node).getText();
                if (toParse.length()>0){
                    if (toParse.length() > 1 || toParse.charAt(0)-'0' > 9){

                        clearBoard();
                        lb00.setText("Error! Invalid input detected at: ("+(count/9)+", "+(count%9)+
                                ") With input of: \""+toParse+"\"\nPlease input again\n");
                        fl = 1;
                        break;
                    }

                    board[count/9][count%9] = Integer.parseInt(toParse);
                }
                else{
                    board[count/9][count%9] = 0;
                }
                count++;
            }
        }
        if (fl == 0){
            lb00.setText("Puzzle syntax correct!\nStart solving puzzle...\n");
            int[][] ans = NewSudoku.solve(board);
            if (ans == null){
                lb00.setText("Sorry, the app could not solve this puzzle\nMaybe you can help it by inputting more info?\n");
            }
            else{
                count = 0;
                for (Node node: gp00.getChildren()){
                    if (node instanceof TextField){
                        ((TextField) node).setText(ans[count/9][count%9]+"");
                        count++;
                    }
                }
                lb00.setText("Congratulations!\n The sudoku puzzle you inputted was solved!\n");
            }
        }
    }

    public void save(){
        int[][] toSave = new int[9][9];
        int count = 0;
        for (Node node: gp00.getChildren()){
            if (node instanceof TextField){
            String toParse = ((TextField) node).getText();
                if (toParse.length()>0){
                    if (toParse.length() > 1 || toParse.charAt(0)-'0' > 9){
                        lb00.setText("Error! Invalid input detected at: ("+(count/9)+", "+(count%9)+
                            ") With input of: \""+toParse+"\"\nSave failed!\n");
                        return;
                    }

                    toSave[count/9][count%9] = Integer.parseInt(toParse);
                }
                else{
                   toSave[count/9][count%9] = 0;
                }
                count++;
            }
        }
        try{
            int index = 0;
            boolean exi = true;
            do{
                File toCheck = new File("savedBoard_"+index);

                exi = toCheck.exists();
                if (exi){
                    index++;

                }
            }while(exi);

            PrintWriter toWrite = new PrintWriter("savedBoard_"+index);
            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++){
                    toWrite.print(toSave[j][i]+" ");
                }
                toWrite.println();
            }
            toWrite.close();
            lb00.setText("Writing successful!\nThe saved file name is:\n\"savedBoard_"+index+"\"\n");
        }
        catch (Exception e){
            lb00.setText("Error! Writing to system failed!\n But you can still go on with you puzzle");
            return;
        }
    }

    public void close(){
        System.exit(0);
    }


    /*public Controller(){}
    @FXML
    public void initialize(){}*/
}
