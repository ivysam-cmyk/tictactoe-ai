import java.util.Scanner;

public class Main {
    public static String[][] board;
    // placeholder
    public static boolean gameEnd = true;
    public static boolean whetherFirstTurn = true;
    // create one scanner and use as opening and closing new scanners produces Exceptions
    public Scanner scann = new Scanner(System.in);
    public Main() {
        boardCreator();
        whetherFirstTurn = whetherFirstTurn();
        // there is some kind of loop here so that player and com keep on answering until the game ends
        // need a check to know if the game ends
        while(gameEnd == true){
            prettyPrint();
            askPlayerMove();
            prettyPrint();
            // com moves 
            prettyPrint();

        }
    }
    // 1. make a board
    public String[][] boardCreator(){
        board = new String[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
               board[i][j] = " "; 
            }
        } 
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
    public void askPlayerMove() {
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
            // to catch the exception if it is not a num
            } catch (Exception e){
                System.out.println("Enter a number of length 2!");
                whetherInt = false;
            }
        // keep asking until it is a number and length 2
        }while(whetherInt && choicePos.length()!=2);
        changeBoard(choicePos);
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
        if(board[rowPosition][colPosition].equals(" ")){
            board[rowPosition][colPosition] = inputValue;
            return board;
        } else {
            System.out.println("Position "+ position +" already filled, try again!");
            askPlayerMove();
        }
        
        return new String[0][0];
    }

    public void prettyPrint(){
        // takes the board and prints it in a better way
        String columns = "  0 1 2"; 
        String seperator = "--------";

        String firstRow = "0|"+board[0][0]+" "+board[0][1]+" "+board[0][2]+"|";
        String secondRow = "1|"+board[1][0]+" "+board[1][1]+" "+board[1][2]+"|";
        String thirdRow = "2|"+board[2][0]+" "+board[2][1]+" "+board[2][2]+"|";

        System.out.println(columns);
        System.out.println(seperator);
        System.out.println(firstRow);
        System.out.println(secondRow);
        System.out.println(thirdRow);
        System.out.println(seperator);
        
    }
    public String getWinner(String[][] board) {
       /* returns either X or O on who wins */ 
        for (int i = 0; i < board.length; i++) {
            // hor
            if((board[i][0].equals(board[i][1])) && (board[i][1].equals(board[i][2]))){
                return board[0][1];
            }
            // ver
            if((board[0][i].equals(board[1][i])) && (board[1][i].equals(board[2][i]))){
                return board[0][1];
            }
        }
            // diag
            if(((board[0][0].equals(board[1][1])) && (board[1][1].equals(board[2][2]))) || 
            ((board[2][0].equals(board[1][1])) && (board[1][1].equals(board[0][2])))){
                return board[1][1];
            }
        return "";
    }
    public static void main(String[] args) {
       Main game = new Main(); 
    }
}