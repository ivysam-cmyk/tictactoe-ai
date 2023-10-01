import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static String[][] board;
    public static String[][] boardCopy;
    // placeholder
    public static String[] endgameArray = new String[2];
    public static boolean whetherFirstTurn = true;
    public String playerChar = "O";
    public String comChar = "X";
    // create one scanner and use as opening and closing new scanners spawns Exceptions
    public Scanner scann = new Scanner(System.in);
    public Main() {
        boardCreator();
        whetherFirstTurn = whetherFirstTurn();
        if(whetherFirstTurn){
            playerChar = "X";
            comChar = "O";
        }
        while((endgameCheck(board))[0] == "false"){
            prettyPrint();
            askPlayerMove();
            prettyPrint();
            System.out.println("---------");
            System.out.println("com moves");
            comMove();
            prettyPrint();
            endgameCheck(board);
        }
    }
    public void comMove() {
        ArrayList<String> move_ArrayList = new ArrayList<>();
        // first get all the possible moves and then for everyone of them
        // check if endgame produces false for player/ true for com and use that move
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j].equals(" ")){
                    // can't concat int, so make it string first
                    String index = Integer.toString(i)+ Integer.toString(j);
                    move_ArrayList.add(index);
                }
            }
        }
        String moveToUse = "";
        String moveToUseToGetTie = "";
        for (String move : move_ArrayList){
            // create a copy of the board, everytime you want to see what a move does to a board
            boardCopy = board.clone();
            String[] outcomeArray = endgameCheck(changeBoard(boardCopy, move, true));
            if(outcomeArray[0].equals("true") && outcomeArray[1].equals(comChar)){
                moveToUse = move;
            } else if (outcomeArray[1].equals("tie")){
                moveToUseToGetTie = move; 
            }
        }
        // if no winning move was found, use any random
        if(moveToUse.length() == 0 && moveToUseToGetTie.length() != 0){
            // use the move that results in tie
            moveToUse = moveToUseToGetTie;
        } else if (moveToUse.length() == 0 && moveToUseToGetTie.length() ==0){
           moveToUse = move_ArrayList.get((int)(Math.random() * move_ArrayList.size())); 
        }
        
        changeBoard(board, moveToUse, true);
        
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
        changeBoard(board, choicePos, false);
    }
    // 4. change the board and print it
    public String[][] changeBoard(String[][] board, String position, boolean compTurn){
        int rowPosition = Integer.parseInt(position.substring(0, 1));
        int colPosition = Integer.parseInt(position.substring(1));
            // com move

            // return board
        if(board[rowPosition][colPosition].equals(" ")){
            if(compTurn){
                board[rowPosition][colPosition] = comChar;
                return board;
            }
            board[rowPosition][colPosition] = playerChar;
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
    public String[] endgameCheck(String[][] board) {
        // first check if any one wins, 
        // then check if not all spaces are filled, then game hasn't ended
        // if finally, all spaces are filled and no one wins, it is a tie
        //*returns [whether game has ended, and who won, tie, incomplete]
        for (int i = 0; i < board.length; i++) {
            // hor
            if((board[i][0].equals(board[i][1])) && (board[i][1].equals(board[i][2]))){
                // ignore if all blanks and check the other rows
                if(board[i][0].equals(" ")){
                    continue;
                }
                System.out.println(board[0][1] + " wins");
                return new String[] {"true", board[0][1]};
                
            }
            // ver
            if((board[0][i].equals(board[1][i])) && (board[1][i].equals(board[2][i]))){
                // ignore if all blanks
                if(board[0][i].equals(" ")){
                    continue;
                }
                System.out.println(board[0][1] + " wins");
                return new String[] {"true", board[0][1]};
            }
        }
        // diag
        if(((board[0][0].equals(board[1][1])) && (board[1][1].equals(board[2][2]))) || 
        ((board[2][0].equals(board[1][1])) && (board[1][1].equals(board[0][2])))){
            System.out.println(board[1][1] + "wins");
                // ignore if all blanks
                if(board[1][1].equals(" ")){
                    // do nothing
                }
                else{
                    return new String[] {"true", board[1][1]};
                }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j].equals(" ")){
                    System.out.println("game not over yet");
                    return new String[] {"false", "incomplete"};
                }
            } 
        }
        
        System.out.println("Tie");
        return new String[] {"true", "tie"};
    }
    public static void main(String[] args) {
       Main game = new Main(); 
    }
}
