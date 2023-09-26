import java.util.Scanner;

public class Main {
    public static String[][] board;
    // 1. make a board
    public String[][] boardCreator(){
        board = new String[3][3];
        return board;
    }
    // 2. assign players X or O
    public boolean whetherFirstTurn(){
        Scanner whetherFirstTurn = new Scanner(System.in);
        System.out.println("Do you want to play first? Type y or n");
        String choice = whetherFirstTurn.nextLine();
        whetherFirstTurn.close();
        if(choice == "n"){
            System.out.println("You will play second your symbol is 'O'");
            return false;
        }
        System.out.println("You will play first your symbol is 'X'");
        return true;
    }

    public static void main(String[] args) {
        
    }
}