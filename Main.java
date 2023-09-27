import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static String[][] board;
    public static boolean whetherFirstTurn = true;
    public Scanner scann = new Scanner(System.in);
    // init function
    public Main() {
        boardCreator();
        // create one scanner and use as opening and closing new scanners produces Exceptions
        whetherFirstTurn = whetherFirstTurn();
        // there is some kind of loop here so that player and com keep on answering until the game ends
        // need a check to know if the game ends
        for (String[] row : board) {
            System.out.println(Arrays.toString(row));
        }
        changeBoard(askPlayerMove());
    }
    // 1. make a board
    public String[][] boardCreator(){
        board = new String[3][3];
        return board;
    }
    // 2. assign players X or O
    public boolean whetherFirstTurn(){
        System.out.println("Do you want to play first? Type y or n");
        String choice = scann.nextLine();
        if(choice == "n"){
            System.out.println("You will play second your symbol is 'O'");
            return false;
        }
        System.out.println("You will play first your symbol is 'X'");
        // trigger the player making move function
        return true;
    }

    // 3. ask p1 to make a move 
    public String askPlayerMove() {
        String choicePos = "";
        boolean whetherInt = true;
        do{
            System.out.println("You can choose one of the blank spaces, choose the row and column.");
            System.out.println("Example if you want to choose the middle spot, write 11.");
            choicePos = scann.nextLine();
            System.out.println("Your move is: " + choicePos);
            // keep asking until player inputs right format(is a number and 2 digits)
            try{
                int testInt = Integer.parseInt(choicePos);
            } catch (Exception e){
                whetherInt = false;
            }
        }while(whetherInt && choicePos.length()!=2);

        return choicePos;
    }
    // 4. change the board and print it
    public String[][] changeBoard(String position){
        
        int rowPosition = Integer.parseInt(position.substring(0, 1));
        int colPosition = Integer.parseInt(position.substring(1));

        String inputValue = "";
        if (whetherFirstTurn){
            inputValue = "X";
        } else {
            inputValue = "O";
        }
        board[rowPosition][colPosition] = inputValue;

        for (String[] row : board) {
            System.out.println(Arrays.toString(row));
        }
        return new String[0][0];
    }

    public static void main(String[] args) {
       Main game = new Main(); 
    }
}