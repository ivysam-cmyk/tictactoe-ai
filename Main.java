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
        // trigger the player making move function
        return true;
    }

    // 3. ask p1 to make a move 
    public String askPlayerMove() {
        String choice = "";
        do{
            Scanner playerMove = new Scanner(System.in);
            System.out.println("You can choose one of the blank spaces, choose the row and column.");
            System.out.println("Example if you want to choose the middle spot, write 11.");
            choice = playerMove.nextLine();
            playerMove.close();
            System.out.println("Your move is: " + choice);
            // keep asking until player inputs right format and blank space
        }while(choice.length() !=2);

        return choice;
    }
    // 4. change the board and print it
    public String[][] changeBoard(String position){
        
        int rowPosition = Integer.parseInt(position.substring(0, 1));
        int colPosition = Integer.parseInt(position.substring(1));

        return new String[0][0];
    }

    public static void main(String[] args) {
        
    }
}