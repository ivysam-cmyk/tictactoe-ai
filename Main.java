import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    // gameStart is to make sure that person is only asked which AI once
    boolean gameStart;
    int playerOne;
    int playerTwo;
    static int random_aiWins = 0;
    static int winOnlyWins = 0;
    static int winAndBlockLoseWins = 0;
    public static String[][] board;
    public static String[] endgameArray = new String[2];
    public static String[] AIArray = {"random_ai", "winOnly", "winAndBlockLose"};
    public String playerOneChar = "X";
    public String playerTwoChar = "O";
    // create one scanner and use as opening and closing new scanners spawns Exceptions
    public Scanner scann = new Scanner(System.in);
    public Main() {
        System.out.println("Enter the number of times you wish to run the game");
        int repeatNum = Integer.parseInt(scann.nextLine());
        for(int i=0 ; i<repeatNum; i++){
            boardCreator();
            gameStart = true;
            // assignChars
            while((endgameCheck(board))[0] == "false"){
                askOnceRunMultipleAI();
                prettyPrint(board);
            }
    
            String gameEndOutcome = endgameCheck(board)[1];
            if(gameEndOutcome == "X"){
                String winner = AIArray[playerOne-1];
                switch(playerOne){
                    case 1:
                        random_aiWins++;
                        break;
                    case 2:
                        winOnlyWins++;
                        break;
                    case 3:
                        winAndBlockLoseWins++;
                }
            }else if (gameEndOutcome == "O"){
                String winner = AIArray[playerTwo-1];
                switch(playerTwo){
                    case 1:
                        random_aiWins++;
                        break;
                    case 2:
                        winOnlyWins++;
                        break;
                    case 3:
                        winAndBlockLoseWins++;
                }
            }
        }
        float random_aiWinsPercentage = (random_aiWins/repeatNum)*100;
        float winOnlyWinsPercentage = (winOnlyWins/repeatNum)*100;
        float winAndBlockLoseWinsPercentage = (winAndBlockLoseWins/repeatNum)*100;
        System.out.println("Percentage of wins by random_ai: " + random_aiWinsPercentage);
        System.out.println("Percentage of wins by winsOnly AI: "+ winOnlyWinsPercentage);
        System.out.println("Percentage of wins by WinAndBlockLose AI: "+ winAndBlockLoseWinsPercentage);
    }
    
    public void random_ai(String comChar) {
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
        changeBoard(board, position, true, comChar);
    }

    public void winAndBlockLose(String comChar) {
            // figure out the chars for both AIs
            String playerChar = "";
            switch (comChar){
                case "X":
                    playerChar = "O";
                    break;
                case "O":
                    playerChar = "X";
                    break;
            }
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

            String[] outcomeArray = endgameCheck(changeBoard(boardCopy, move, true, comChar));
            // play as the player to find which pos to block
            String[] outcomeArrayAlt = endgameCheck(changeBoard(boardCopyAlt, move, false,playerChar));
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
        changeBoard(board, moveToUse, true, comChar);
        
    }
    
    public void winOnly(String comChar) {
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
        String moveToUseToGetTie = "";
        for (String move : move_ArrayList){
            // create a copy of the board, everytime you want to see what a move does to a board
            String[][] boardCopy = new String[3][3];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                   boardCopy[i][j] = board[i][j];
                }
            }

            String[] outcomeArray = endgameCheck(changeBoard(boardCopy, move, true, comChar));
            System.out.println("after using changeBoard, board changes something happens in above line");
            // if the computer is winning,
            if(outcomeArray[0].equals("true") && outcomeArray[1].equals(comChar)){
                moveToUse = move;
            } else if (outcomeArray[1].equals("tie")){
                moveToUseToGetTie = move; 
            }
        }
        System.out.println("done going through all the legal moves");
        // if no winning move was found, use any random
        if(moveToUse.length() == 0 && moveToUseToGetTie.length() != 0){
            // use the move that results in tie
            moveToUse = moveToUseToGetTie;
        } else if (moveToUse.length() == 0 && moveToUseToGetTie.length() ==0){
           moveToUse = move_ArrayList.get((int)(Math.random() * move_ArrayList.size())); 
        }
        
        System.out.println("the move to use is...");
        System.out.println(moveToUse);
        changeBoard(board, moveToUse, true, comChar);
        
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
        while(gameStart == true || playerOne == playerTwo){
            System.out.println("Here lies a list of AI to choose to face each other in a spirited battle of TTT");
            System.out.println("1. random_ai");
            System.out.println("2. choose winning spot ai");
            System.out.println("3. choose winning spot and block losing spot ai");
            System.out.println("Write the 2 numbers consecutively, the first digit is player 1 and second digit is player 2.");
            System.out.println("--------------------------------");
            choice = scann.nextLine();
            gameStart = false;
            playerOne = Integer.parseInt(choice.substring(0, 1)); 
            playerTwo = Integer.parseInt(choice.substring(1)); 
        }
        // figure out the choice and subsequently p1 and p2
        switch (playerOne){
            case 1:
                random_ai("X"); 
                break;
            case 2:
                winOnly("X");
                break;
            case 3:
                winAndBlockLose("X");
        }

        switch (playerTwo){
            case 1:
                random_ai("O"); 
                break;
            case 2:
                winOnly("O");
                break;
            case 3:
                winAndBlockLose("O");
                break;
        }

    }

    // 4. change the board and print it
    public String[][] changeBoard(String[][] boardToBeChanged, String position, boolean compTurn, String comChar){
        int rowPosition = Integer.parseInt(position.substring(0, 1));
        int colPosition = Integer.parseInt(position.substring(1));

        if(boardToBeChanged[rowPosition][colPosition].equals(" ")){
            if(compTurn){
                System.out.println("pos is blank and compTurn");
                boardToBeChanged[rowPosition][colPosition] = comChar;
                return boardToBeChanged;
            }
            String playerChar = "";
            switch (comChar){
                case "X":
                    playerChar = "O";
                    break;
                case "O":
                    playerChar = "X";
                    break;
            }
            boardToBeChanged[rowPosition][colPosition] = playerChar;
            return boardToBeChanged;
        } else {
            System.out.println("The position at "+ position+ " is...");
            System.out.println(boardToBeChanged[rowPosition][colPosition]);
            System.out.println("Position "+ position +" already filled, try again!");
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
                return new String[] {"true", board[i][1]};
                
            }
            // ver
            if((board[0][i].equals(board[1][i])) && (board[1][i].equals(board[2][i]))){
                // ignore if all blanks
                if(board[0][i].equals(" ")){
                    continue;
                }
                return new String[] {"true", board[0][i]};
            }
        }
        // diag
        if(((board[0][0].equals(board[1][1])) && (board[1][1].equals(board[2][2]))) || 
        ((board[2][0].equals(board[1][1])) && (board[1][1].equals(board[0][2])))){
                // ignore if all blanks
                if(!board[1][1].equals(" ")){
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
