import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    // gameStart is to make sure that person is only asked which AI once
    boolean gameStart;
    public static String[][] board;
    public static String[] listOfAI = {random_ai(), winOnly(), winAndBlockLose()};
    int playerOne;
    int playerTwo;
    public static String[] endgameArray = new String[2];
    public String playerChar = "O";
    public String comChar = "X";
    // create one scanner and use as opening and closing new scanners spawns Exceptions
    public Scanner scann = new Scanner(System.in);
    public Main() {
        boardCreator();
        gameStart = true;
        // assignChars
        while((endgameCheck(board))[0] == "false"){
            askOnceRunMultipleAI();
            prettyPrint(board);
            winAndBlockLose();
            prettyPrint(board);
            if((endgameCheck(board))[0] == "true"){
                break;
            }
            System.out.println("---------");
            System.out.println("player moves");
            askPlayerMove();
            prettyPrint(board);
            endgameCheck(board);
        }
    }

    public void winAndBlockLose() {
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
        System.out.println(move_ArrayList);
        String moveToUse = "";
        String moveToBlock = "";
        String moveToUseToGetTie = "";
        for (String move : move_ArrayList){
            // create a copy of the board, everytime you want to see what a move does to a board
            String[][] boardCopy = new String[3][3];
            String[][] boardCopyAlt = new String[3][3];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                   boardCopy[i][j] = board[i][j];
                   boardCopyAlt[i][j] = board[i][j];
                }
            }

            String[] outcomeArray = endgameCheck(changeBoard(boardCopy, move, true));
            // play as the player to find which pos to block
            String[] outcomeArrayAlt = endgameCheck(changeBoard(boardCopyAlt, move, false));
            System.out.println("Where com can win: "+ Arrays.toString(outcomeArray));
            System.out.println("Where player can win: "+ Arrays.toString(outcomeArrayAlt));
            // if the computer is winning,
            if(outcomeArray[0].equals("true") && outcomeArray[1].equals(comChar)){
                moveToUse = move;
            } else if (outcomeArray[1].equals("tie")){
                moveToUseToGetTie = move; 
            }
            // if the computer is losing,
            if(outcomeArrayAlt[0].equals("true") && outcomeArrayAlt[1].equals(playerChar)){
                moveToBlock = move;
            }
        }
        // order : win,block loss, random(tie), random
        // if no winning move was found, use any move that blocks losing
        if(moveToUse.length()==0){
            moveToUse = moveToBlock;
        } 
        if(moveToUse.length()==0){
            // use the move that results in tie
            moveToUse = moveToUseToGetTie;
        }
        if (moveToUse.length()==0){
            moveToUse = move_ArrayList.get((int)(Math.random() * move_ArrayList.size())); 
            System.out.println("random move is produced"); 
        }
        
        System.out.println("the move to use is...");
        System.out.println(moveToUse);
        changeBoard(board, moveToUse, true);
        
    }
    public void random_ai() {
        ArrayList<String> move_ArrayList = new ArrayList<>();
        // if there is a blank, record it
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j].equals(" ")){
                    // convert to String b4 concat
                    String index = Integer.toString(i)+ Integer.toString(j);
                    move_ArrayList.add(index);
                }
            }
        }
        String position = move_ArrayList.get((int)(Math.random() * move_ArrayList.size()));
        changeBoard(board, position, true);
        
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
    // get the number assigned to the ai and determine who that belongs to(using an array)
    public void askOnceRunMultipleAI(){
        // will only run per game
        String choice;
        if(gameStart == true){
            System.out.println("Here lies a list of AI to choose to face each other in a spirited battle of TTT");
            System.out.println("1. random_ai");
            System.out.println("2. choose winning spot ai");
            System.out.println("3. choose winning spot and block losing spot ai");
            System.out.println("Write the 2 numbers consecutively, the first digit is player 1 and second digit is player 2.");
            System.out.println("--------------------------------");
            choice = scann.nextLine();
            gameStart = false;
        }
        // figure out the choice and subsequently p1 and p2
        playerOne = Integer.parseInt(choice.substring(0, 1)); 
        playerTwo = Integer.parseInt(choice.substring(1)); 
        switch (playerOne){
            case 1:
                random_ai(); 
                break;
            case 2:
                winOnly();
                break;
            case 3:
                winAndBlockLose();
                break;
        }
        switch (playerTwo){
            case 1:
                random_ai(); 
                break;
            case 2:
                winOnly();
                break;
            case 3:
                winAndBlockLose();
                break;
        }

    }

    // 3. ask p1 to make a move 
    public void askPlayerMove() {
        String choicePos = "";
        boolean whetherInt = true;
        do{
            System.out.println("You can choose one of the blank spaces, choose the row and column.");
            System.out.println("Example if you want to choose the middle spot, write 11.");
            choicePos = scann.nextLine();
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
    public String[][] changeBoard(String[][] boardToBeChanged, String position, boolean compTurn){
        int rowPosition = Integer.parseInt(position.substring(0, 1));
        int colPosition = Integer.parseInt(position.substring(1));

        if(boardToBeChanged[rowPosition][colPosition].equals(" ")){
            if(compTurn){
                System.out.println("pos is blank and compTurn");
                boardToBeChanged[rowPosition][colPosition] = comChar;
                return boardToBeChanged;
            }
            boardToBeChanged[rowPosition][colPosition] = playerChar;
            return boardToBeChanged;
        } else {
            System.out.println("The position at "+ position+ " is...");
            System.out.println(boardToBeChanged[rowPosition][colPosition]);
            System.out.println("Position "+ position +" already filled, try again!");
            askPlayerMove();
        }
        
        return new String[0][0];
    }

    public void prettyPrint(String[][] board){
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
                System.out.println(board[i][1] + " wins");
                return new String[] {"true", board[i][1]};
                
            }
            // ver
            if((board[0][i].equals(board[1][i])) && (board[1][i].equals(board[2][i]))){
                // ignore if all blanks
                if(board[0][i].equals(" ")){
                    continue;
                }
                System.out.println(board[0][i] + " wins");
                return new String[] {"true", board[0][i]};
            }
        }
        // diag
        if(((board[0][0].equals(board[1][1])) && (board[1][1].equals(board[2][2]))) || 
        ((board[2][0].equals(board[1][1])) && (board[1][1].equals(board[0][2])))){
                // ignore if all blanks
                if(!board[1][1].equals(" ")){
                    System.out.println(board[1][1] + " wins");
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
